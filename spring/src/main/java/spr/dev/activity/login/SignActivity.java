package spr.dev.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import spr.dev.R;
import spr.dev.util.SharedPreferencesUtil;

/**
 * Created by hanki on 2017/2/5.
 */

public class SignActivity extends AppCompatActivity {
    private static final String TAG = "SignActivity";

    SharedPreferencesUtil sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
    }
}
