package spr.dev.activity.login;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import spr.dev.MainActivity;
import spr.dev.R;
import spr.dev.constant.SP_Constant;
import spr.dev.util.SharedPreferencesUtil;

/**
 * Created by hanki on 2017/1/20.
 */

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    Context context = StartActivity.this;
    SharedPreferencesUtil sp;
    private TextView tv_start;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        tv_start = (TextView) findViewById(R.id.tv_start);
        startAnim();
    }

    private void startAnim() {
        tv_start.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.setDuration(800);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float alpha = (float) animation.getAnimatedValue();

                        tv_start.setText("{Spring}");
                        tv_start.setAlpha(alpha);
                    }
                });
                animator.start();
            }
        }, 1200);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartApp();
            }
        }, 2300);

    }

    private void StartApp() {
        sp = new SharedPreferencesUtil(StartActivity.this, SP_Constant.SP_NAME);

        String isLogin = sp.getStringByKey("login");
        String isFirstUse = sp.getStringByKey("isFirstUse");

        Log.e(TAG, "-- isLogin is --" + isLogin);
        Log.e(TAG, "-- isFirstUse is --" + isFirstUse);

        if (!isLogin.equals("yes")) {
            Log.e(TAG, "--- USER_ID is empty ---");
            if (isFirstUse.equals("notFirst")) {
                Intent intent = new Intent();


                intent.setClass(StartActivity.this, SignActivity.class);
                Log.e(TAG, "--- USER_ID is empty,To SignActivity ---");

                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, PageActivity.class);
                Log.e(TAG, "--- USER_ID is empty and first use,To PageActivity ---");
                startActivity(intent);
                finish();
            }

        } else {
            Log.e(TAG, "--- USER_ID is not empty ---");
            Intent intent = new Intent();
            intent.setClass(StartActivity.this, MainActivity.class);
            Log.e(TAG, "--- USER_ID is not empty,To MainActivity ---");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}

