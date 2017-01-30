package spr.dev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import spr.dev.util.ShowToast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean isState = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (isState) {
            isState = false;
            ShowToast.ColorToast(MainActivity.this, "再次点击退出程序", 1200);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isState = true;
                }
            }, 1200);
        } else {
            this.finish();
        }
    }
}
