package spr.dev.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StartApp();
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

