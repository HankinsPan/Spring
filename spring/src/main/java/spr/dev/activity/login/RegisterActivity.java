package spr.dev.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import spr.dev.R;
import spr.dev.util.SharedPreferencesUtil;

/**
 * Created by hanki on 2017/1/25.
 */

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    LoginChoose choose = LoginChoose.SIGNIN;
    private SharedPreferencesUtil sp;
    private FrameLayout mFrameLayout;
    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;
    private int mTvLoginWidth;
    private int mTvLoginHeight;
    private int mDuration;
    private TextView tv_signIn;
    private TextView tv_signUp;
    private View diver_left;
    private View diver_right;
    // 用户登录
    private TextView tv_login_phone;
    private EditText edt_login_phone;
    private TextView tv_login_password;
    private EditText edt_login_password;
    // 用户注册
    private TextView tv_reg_name;
    private EditText edt_reg_name;
    private TextView tv_reg_phone;
    private EditText edt_reg_phone;
    private TextView tv_reg_smsCode;
    private EditText edt_reg_smsCode;
    private Button btn_reg_smsCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_regist);
        initView();
        init();
        setUpView();
        addListener();
    }

    private void initView() {
        tv_signIn = (TextView) findViewById(R.id.tv_signIn);
        tv_signUp = (TextView) findViewById(R.id.tv_signUp);
        diver_left = findViewById(R.id.diver_left);
        diver_right = findViewById(R.id.diver_right);

        tv_login_phone = (TextView) findViewById(R.id.tv_login_phone);
        edt_login_phone = (EditText) findViewById(R.id.edt_login_phone);
        tv_login_password = (TextView) findViewById(R.id.tv_login_password);
        edt_login_password = (EditText) findViewById(R.id.edt_login_password);

        tv_reg_name = (TextView) findViewById(R.id.tv_reg_name);
        edt_reg_name = (EditText) findViewById(R.id.edt_reg_name);
        tv_reg_phone = (TextView) findViewById(R.id.tv_reg_phone);
        edt_reg_phone = (EditText) findViewById(R.id.edt_reg_phone);
        tv_reg_smsCode = (TextView) findViewById(R.id.tv_reg_smsCode);
        edt_reg_smsCode = (EditText) findViewById(R.id.edt_reg_smsCode);
        btn_reg_smsCode = (Button) findViewById(R.id.btn_reg_smsCode);


    }

    private void init() {

    }

    private void setUpView() {
        updateLayout(choose);
    }

    private void updateLayout(LoginChoose choose) {
        if (choose == LoginChoose.SIGNIN) {
            tv_signIn.setTextColor(Color.parseColor("#2D78B1"));
            tv_signUp.setTextColor(Color.parseColor("#D1D1D1"));
            diver_left.setBackgroundColor(Color.parseColor("#2D78B1"));
            diver_right.setBackgroundColor(Color.parseColor("#D1D1D1"));

            tv_login_phone.setVisibility(View.VISIBLE);
            edt_login_phone.setVisibility(View.VISIBLE);
            tv_login_password.setVisibility(View.VISIBLE);
            edt_login_password.setVisibility(View.VISIBLE);

            tv_reg_name.setVisibility(View.GONE);
            edt_reg_name.setVisibility(View.GONE);
            tv_reg_phone.setVisibility(View.GONE);
            edt_reg_phone.setVisibility(View.GONE);
            tv_reg_smsCode.setVisibility(View.GONE);
            edt_reg_smsCode.setVisibility(View.GONE);
            btn_reg_smsCode.setVisibility(View.GONE);
        } else {
            tv_signIn.setTextColor(Color.parseColor("#D1D1D1"));
            tv_signUp.setTextColor(Color.parseColor("#2D78B1"));
            diver_left.setBackgroundColor(Color.parseColor("#D1D1D1"));
            diver_right.setBackgroundColor(Color.parseColor("#2D78B1"));

            tv_login_phone.setVisibility(View.GONE);
            edt_login_phone.setVisibility(View.GONE);
            tv_login_password.setVisibility(View.GONE);
            edt_login_password.setVisibility(View.GONE);

            tv_reg_name.setVisibility(View.VISIBLE);
            edt_reg_name.setVisibility(View.VISIBLE);
            tv_reg_phone.setVisibility(View.VISIBLE);
            edt_reg_phone.setVisibility(View.VISIBLE);
            tv_reg_smsCode.setVisibility(View.VISIBLE);
            edt_reg_smsCode.setVisibility(View.VISIBLE);
            btn_reg_smsCode.setVisibility(View.VISIBLE);
        }
    }

    private void addListener() {
        tv_signIn.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        btn_reg_smsCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signIn:
                choose = LoginChoose.SIGNIN;
                updateLayout(choose);
                break;
            case R.id.tv_signUp:
                choose = LoginChoose.SIGNUP;
                updateLayout(choose);
                break;
        }

    }

    private enum LoginChoose {
        SIGNIN, SIGNUP
    }

}
