package com.itemscan.kantwait.itemscan;

import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
import mexxen.mx5010.barcode.BarcodeManager;

import android.support.v4.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        String string = "手机屏幕分辨率为：" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(string);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getWeithAndHeight() {
        // 这种方式在service中无法使用，
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; // 宽
        int height = dm.heightPixels; // 高
        float density = dm.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        // 在service中也能得到高和宽
            WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();
        height = mWindowManager.getDefaultDisplay().getHeight();

        // 居中显示Toast
        Toast msg = Toast.makeText(this, "宽=" + width + "   高=" + height,
                Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
                msg.getYOffset() / 2);
        msg.show();
        return "(像素)宽:" + width + "\n" + "(像素)高:" + height + "\n"
                + "屏幕密度（0.75 / 1.0 / 1.5）:" + density + "\n"
                + "屏幕密度DPI（120 / 160 / 240）:" + densityDpi + "\n";
        /**
         * A placeholder fragment containing a simple view.
         */
    }

    public static class PlaceholderFragment extends Fragment {
        //private Button button1;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_main, container, false);

            //button1 = (Button) rootView.findViewById(R.id.button1);

            final BarcodeManager bm = new BarcodeManager(getActivity());
            bm.addListener(new BarcodeListener() {
                // ÖØÐ´ barcodeEvent ·½·¨£¬»ñÈ¡ÌõÂëÊÂ¼þ
                @Override
                public void barcodeEvent(BarcodeEvent event) {
                    // µ±ÌõÂëÊÂ¼þµÄÃüÁîÎª¡°SCANNER_READ¡±Ê±£¬½øÐÐ²Ù×÷
                    if (event.getOrder().equals("SCANNER_READ")) {
                        // µ÷ÓÃ getBarcode()·½·¨¶ÁÈ¡ÌõÂëÐÅÏ¢
                        String barcode = bm.getBarcode();

                        Toast.makeText(getActivity(), barcode,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            return rootView;
        }
    }
}
