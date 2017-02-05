package spr.dev.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spr.dev.R;
import spr.dev.adapter.ViewPagerAdapter;
import spr.dev.constant.SP_Constant;
import spr.dev.util.SharedPreferencesUtil;
import spr.dev.view.LoginLoadingView;

/**
 * Created by hanki on 2017/2/5.
 */

public class SignActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {
    private static final String TAG = "SignActivity";
    // 模拟用户信息
    private static String i_Phone = "15354872767";
    private static String i_Password = "123456";
    private static String i_Name = "hankins";
    private static String i_SmsCode = "654321";
    SharedPreferencesUtil sp;
    private RelativeLayout mRelat;
    private Scene mSceneSignIn;
    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;

    private int mTvLoginWidth;
    private int mTvLoginHeight;
    private int mDuration;

    private ViewPager signPager;
    private List<View> viewList;

    private View signInView;
    private View signUpVeiw;

    // 用户登录
    private EditText edt_login_phone;
    private EditText edt_login_password;

    // 用户注册
    private EditText edt_reg_name;
    private EditText edt_reg_phone;
    private EditText edt_reg_smsCode;
    private Button btn_reg_smsCode;
    /**
     * 验证码计时器
     */
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btn_reg_smsCode.setText(millisUntilFinished / 1000 + " s");
        }

        @Override
        public void onFinish() {
            btn_reg_smsCode.setText("SmsCode");
            btn_reg_smsCode.setClickable(true);
            btn_reg_smsCode.setBackgroundColor(Color.parseColor("#2D78B1"));
        }
    };
    private ViewPagerAdapter adapter;
    private TextView[] docts;
    private int[] ids = {R.id.tv_signInTxt, R.id.tv_signUpTxt};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        mRelat = (RelativeLayout) findViewById(R.id.relat_content);
        mDuration = getResources().getInteger(R.integer.duration);

        mSceneSignIn = Scene.getSceneForLayout(mRelat, R.layout.sign_in_pager, this);
        mSceneSignUp = Scene.getSceneForLayout(mRelat, R.layout.sign_up_pager, this);
        mSceneLogging = Scene.getSceneForLayout(mRelat, R.layout.scene_logging, this);
        mSceneMain = Scene.getSceneForLayout(mRelat, R.layout.activity_main, this);

        init();
        initView();
        initData();
        addListener();
        setUpdate();
        adapter = new ViewPagerAdapter(viewList);
        signPager.setAdapter(adapter);

    }

    private void init() {
        Log.e(TAG, "-- first into SceneSignIn --");

        mSceneSignIn.setEnterAction(new Runnable() {
            @Override
            public void run() {
                final LoginLoadingView loadView = (LoginLoadingView)
                        mRelat.findViewById(R.id.signView_btn);

                ViewTreeObserver vto = loadView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        setSize(loadView.getMeasuredWidth(),
                                loadView.getMeasuredHeight());
                    }
                });
                loadView.setOnClickListener(SignActivity.this);
            }
        });
        TransitionManager.go(mSceneSignIn);

    }

    private void initView() {
        signPager = (ViewPager) findViewById(R.id.view_signPager);
        signInView = View.inflate(this, R.layout.sign_in_pager, null);
        signUpVeiw = View.inflate(this, R.layout.sign_up_pager, null);

        sp = new SharedPreferencesUtil(SignActivity.this, SP_Constant.SP_NAME);

        viewList = new ArrayList<View>();
        viewList.add(signInView);
        viewList.add(signUpVeiw);
        signPager.setOnPageChangeListener(this);

        edt_login_phone = (EditText) signInView.findViewById(R.id.edt_login_phone);
        edt_login_password = (EditText) signInView.findViewById(R.id.edt_login_password);

        edt_reg_name = (EditText) signUpVeiw.findViewById(R.id.edt_reg_name);
        edt_reg_phone = (EditText) signUpVeiw.findViewById(R.id.edt_reg_phone);
        edt_reg_smsCode = (EditText) signUpVeiw.findViewById(R.id.edt_reg_smsCode);
        btn_reg_smsCode = (Button) signUpVeiw.findViewById(R.id.btn_reg_smsCode);

    }

    private void initData() {
        docts = new TextView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            docts[i] = (TextView) findViewById(ids[i]);
        }
    }


    private void setUpdate() {

    }

    private void addListener() {
        btn_reg_smsCode.setOnClickListener(this);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                docts[i].setTextColor(Color.parseColor("#2D78B1"));
            } else {
                docts[i].setTextColor(Color.parseColor("#D1D1D1"));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }

    private void setSize(int width, int height) {
        mTvLoginWidth = width;
        mTvLoginHeight = height;
    }
}
