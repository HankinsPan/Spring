package spr.dev.activity.login;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import spr.dev.R;
import spr.dev.adapter.CommonAdapter;
import spr.dev.util.SharedPreferencesUtil;
import spr.dev.util.ShowToast;
import spr.dev.util.Tools;
import spr.dev.view.LoginLoadingView;

/**
 * Created by hanki on 2017/1/25.
 */

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    // 模拟用户信息
    private static String i_Phone = "15354872767";
    private static String i_Password = "123456";
    private static String i_Name = "hankins";
    private static String i_SmsCode = "654321";

    private LayoutInflater mInflater;
    private View signView;

    private SharedPreferencesUtil sp;
    private FrameLayout mFrameLayout;
    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;
    private int mTvLoginWidth;
    private int mTvLoginHeight;
    private int mDuration;

    private TextView tv_signIn;
//    private TextView tv_signUp;

    // 用户登录
    private TextView tv_login_phone;
    private EditText edt_login_phone;
    private TextView tv_login_password;
    private EditText edt_login_password;

    // 用户注册
//    private TextView tv_reg_name;
//    private EditText edt_reg_name;
//    private TextView tv_reg_phone;
//    private EditText edt_reg_phone;
//    private TextView tv_reg_smsCode;
//    private EditText edt_reg_smsCode;
//    private Button btn_reg_smsCode;

//    /**
//     * 验证码计时器
//     */
//    CountDownTimer timer = new CountDownTimer(60000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            btn_reg_smsCode.setText(millisUntilFinished / 1000 + " s");
//        }
//
//        @Override
//        public void onFinish() {
//            btn_reg_smsCode.setText("SmsCode");
//            btn_reg_smsCode.setClickable(true);
//            btn_reg_smsCode.setBackgroundColor(Color.parseColor("#2D78B1"));
//        }
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.frt_main_content);
        mDuration = getResources().getInteger(R.integer.duration);

        mSceneSignUp = Scene.getSceneForLayout(mFrameLayout, R.layout.scene_regist, this);
        mSceneLogging = Scene.getSceneForLayout(mFrameLayout, R.layout.scene_logging, this);
        mSceneMain = Scene.getSceneForLayout(mFrameLayout, R.layout.activity_main, this);

        init();
        initView();
//        setUpView();
    }

    private void init() {

        Log.e(TAG, "--- init ---");

        mSceneSignUp.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "-- mSceneSignUp --");

                final LoginLoadingView loadingView = (LoginLoadingView)
                        mFrameLayout.findViewById(R.id.login_view_btn);

                ViewTreeObserver vto = loadingView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        setSize(loadingView.getMeasuredWidth(),
                                loadingView.getMeasuredHeight());
                    }
                });
                loadingView.setOnClickListener(RegisterActivity.this);
            }
        });
        TransitionManager.go(mSceneSignUp);

    }

    private void initView() {

        mInflater = LayoutInflater.from(this);
        signView = mInflater.inflate(R.layout.scene_regist, null);

//        tv_signUp = (TextView) signView.findViewById(R.id.tv_signUp);
        tv_signIn = (TextView) signView.findViewById(R.id.tv_signIn);


        tv_login_phone = (TextView) signView.findViewById(R.id.tv_login_phone);
        edt_login_phone = (EditText) signView.findViewById(R.id.edt_login_phone);
        tv_login_password = (TextView) signView.findViewById(R.id.tv_login_password);
        edt_login_password = (EditText) signView.findViewById(R.id.edt_login_password);

//        tv_reg_name = (TextView) signView.findViewById(R.id.tv_reg_name);
//        edt_reg_name = (EditText) signView.findViewById(R.id.edt_reg_name);
//        tv_reg_phone = (TextView) signView.findViewById(R.id.tv_reg_phone);
//        edt_reg_phone = (EditText) signView.findViewById(R.id.edt_reg_phone);
//        tv_reg_smsCode = (TextView) signView.findViewById(R.id.tv_reg_smsCode);
//        edt_reg_smsCode = (EditText) signView.findViewById(R.id.edt_reg_smsCode);
//        btn_reg_smsCode = (Button) signView.findViewById(R.id.btn_reg_smsCode);

    }


//    private void setUpView() {
//        updateLayout(choose);
//    }


//    private void updateLayout(LoginChoose choose) {
//
//        if (choose == LoginChoose.SIGNIN) {
//            tv_signIn.setTextColor(Color.parseColor("#2D78B1"));
//            tv_signUp.setTextColor(Color.parseColor("#D1D1D1"));
//            diver_left.setBackgroundColor(Color.parseColor("#2D78B1"));
//            diver_right.setBackgroundColor(Color.parseColor("#D1D1D1"));
//
//            tv_login_phone.setVisibility(View.VISIBLE);
//            edt_login_phone.setVisibility(View.VISIBLE);
//            tv_login_password.setVisibility(View.VISIBLE);
//            edt_login_password.setVisibility(View.VISIBLE);
//
//            tv_reg_name.setVisibility(View.GONE);
//            edt_reg_name.setVisibility(View.GONE);
//            tv_reg_phone.setVisibility(View.GONE);
//            edt_reg_phone.setVisibility(View.GONE);
//            tv_reg_smsCode.setVisibility(View.GONE);
//            edt_reg_smsCode.setVisibility(View.GONE);
//            btn_reg_smsCode.setVisibility(View.GONE);
//
//
//        } else {
//            tv_signIn.setTextColor(Color.parseColor("#D1D1D1"));
//            tv_signUp.setTextColor(Color.parseColor("#2D78B1"));
//            diver_left.setBackgroundColor(Color.parseColor("#D1D1D1"));
//            diver_right.setBackgroundColor(Color.parseColor("#2D78B1"));
//
//            tv_login_phone.setVisibility(View.GONE);
//            edt_login_phone.setVisibility(View.GONE);
//            tv_login_password.setVisibility(View.GONE);
//            edt_login_password.setVisibility(View.GONE);
//
//            tv_reg_name.setVisibility(View.VISIBLE);
//            edt_reg_name.setVisibility(View.VISIBLE);
//            tv_reg_phone.setVisibility(View.VISIBLE);
//            edt_reg_phone.setVisibility(View.VISIBLE);
//            tv_reg_smsCode.setVisibility(View.VISIBLE);
//            edt_reg_smsCode.setVisibility(View.VISIBLE);
//            btn_reg_smsCode.setVisibility(View.VISIBLE);
//        }
//    }

//    private void addListener() {
//        tv_signIn.setOnClickListener(this);
//        tv_signUp.setOnClickListener(this);
//        btn_reg_smsCode.setOnClickListener(this);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_signIn:
//                choose = LoginChoose.SIGNIN;
//                updateLayout(choose);
//                break;
//
//            case R.id.tv_signUp:
//                choose = LoginChoose.SIGNUP;
//                updateLayout(choose);
//                break;

//            case R.id.btn_reg_smsCode:
//
//                String nName = edt_reg_name.getText().toString().trim();
//                final String nPhone = edt_reg_phone.getText().toString().trim();
//
//                if (!TextUtils.isEmpty(nName) && Tools.valiUserName(nName)) {
//                    if (!TextUtils.isEmpty(nPhone)
//                            && Tools.validatePhone(nPhone)
//                            && nPhone.length() == 11) {
//                        sendSmsCode();
//                        btn_reg_smsCode.setText("59" + " s");
//                        timer.start();
//                        btn_reg_smsCode.setClickable(false);
//                        btn_reg_smsCode.setBackgroundColor(Color.parseColor("#757575"));
//                    } else {
//                        ShowToast.ColorToast(RegisterActivity.this, "phone number is error", 1200);
//                    }
//                } else {
//                    ShowToast.ColorToast(RegisterActivity.this, "username is  not legal", 1200);
//                }
//                break;

//            case R.id.login_view_btn:
//                if (choose == LoginChoose.SIGNIN) {
//                    Log.e(TAG, "--- SignIn --- choose is ->" + choose);
//                    String uPhone = edt_login_phone.getText().toString().trim();
//                    String uPassWord = edt_login_password.getText().toString().trim();
//                    if (!TextUtils.isEmpty(uPhone) && Tools.validatePhone(uPhone)) {
//                        if (!TextUtils.isEmpty(uPassWord) && Tools.validateLoginPassWord(uPassWord)) {
//                            SignIn();
//                        } else {
//                            ShowToast.ColorToast(RegisterActivity.this, "password is error", 1200);
//                        }
//                    } else {
//                        ShowToast.ColorToast(RegisterActivity.this, "phone number is error", 1200);
//                    }
//                } else {
//                    Log.e(TAG, "--- SignUp --- choose is ->" + choose);
//                    String rName = edt_reg_name.getText().toString().trim();
//                    String rPhone = edt_reg_phone.getText().toString().trim();
//                    String rSmsCode = edt_reg_smsCode.getText().toString().trim();
//
//                    if (!TextUtils.isEmpty(rName) && Tools.valiUserName(rName)) {
//                        if (!TextUtils.isEmpty(rPhone) && Tools.validatePhone(rPhone)) {
//                            if (!TextUtils.isEmpty(rSmsCode) && Tools.isSMSCodeValid(rSmsCode)) {
//                                SignUp();
//                            } else {
//                                ShowToast.ColorToast(RegisterActivity.this, "smsCode is error", 1200);
//                            }
//                        } else {
//                            ShowToast.ColorToast(RegisterActivity.this, "phone number is error", 1200);
//                        }
//                    } else {
//                        ShowToast.ColorToast(RegisterActivity.this, "userName is error", 1200);
//                    }
//                }

            case R.id.login_view_btn:

                String uPhone = edt_login_phone.getText().toString().trim();
                String uPassWord = edt_login_password.getText().toString().trim();

                if (!TextUtils.isEmpty(uPhone) && Tools.validatePhone(uPhone)) {
                    if (!TextUtils.isEmpty(uPassWord) && Tools.validateLoginPassWord(uPassWord)) {
                        SignIn();
                    } else {
                        ShowToast.ColorToast(RegisterActivity.this, "password is error", 1200);
                    }
                } else {
                    ShowToast.ColorToast(RegisterActivity.this, "phone number is error", 1200);
                }
                break;


            default:
                break;
        }


        float finalRadius = (float)
                Math.hypot(mFrameLayout.getWidth(), mFrameLayout.getHeight());

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        Animator anim = ViewAnimationUtils
                .createCircularReveal(mFrameLayout,
                        x + mTvLoginWidth / 2,
                        y - mTvLoginHeight / 2,
                        100,
                        finalRadius);

        mFrameLayout.setBackgroundColor(getResources().getColor(R.color.colorBg));

        anim.setDuration(mDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "-- onClick -- onAnimationStart ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "-- onClick -- onAnimationEnd ");
                mFrameLayout.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "-- onClick -- onAnimationCancel ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "-- onClick -- onAnimationRepeat ");
            }
        });
        anim.start();

        TransitionManager.go(mSceneLogging, new ChangeBounds()
                .setDuration(mDuration)
                .setInterpolator(new DecelerateInterpolator()));

    }


    private void SignIn() {
        String uPhone = edt_login_phone.getText().toString().trim();
        String uPassWord = edt_login_password.getText().toString().trim();
        if (uPhone == i_Phone && uPassWord == i_Password) {
            AnimforSign();
        } else {
            ShowToast.ColorToast(RegisterActivity.this, "signIn msg error", 1200);
        }
    }

//    private void SignUp() {
//        String rName = edt_reg_name.getText().toString().trim();
//        String rPhone = edt_reg_phone.getText().toString().trim();
//        String rSmsCode = edt_reg_smsCode.getText().toString().trim();
//
//        if (rName == i_Name && rPhone == i_Phone && rSmsCode == i_SmsCode) {
//            AnimforSign();
//        } else {
//            ShowToast.ColorToast(RegisterActivity.this, "signUp msg error", 1200);
//        }
//    }


//    private void sendSmsCode() {
//        ShowToast.ColorToast(RegisterActivity.this, "SmsCode is send", 1200);
//    }

    private void AnimforSign() {

        mSceneLogging.setEnterAction(new Runnable() {
            @Override
            public void run() {
                final LoginLoadingView loadingView = (LoginLoadingView)
                        mFrameLayout.findViewById(R.id.login_view_btn);

                loadingView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setStatus(LoginLoadingView.STATUS_LOGGING);
                    }
                }, mDuration);

                loadingView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setStatus(LoginLoadingView.STATUS_LOGIN_SUCCESS);
                    }
                }, 4000);

                loadingView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.go(mSceneMain, new ChangeBounds()
                                .setDuration(mDuration)
                                .setInterpolator(new DecelerateInterpolator()));
                    }
                }, 6000);
            }
        });

        mSceneMain.setEnterAction(new Runnable() {
            @Override
            public void run() {
                final TextView tv_logo_text = (TextView)
                        mFrameLayout.findViewById(R.id.tv_logo_text);

                final ImageView iv_menu = (ImageView)
                        mFrameLayout.findViewById(R.id.iv_menu);

                final ImageView iv_main_bg = (ImageView)
                        mFrameLayout.findViewById(R.id.iv_main_bg);

                final CollapsingToolbarLayout coll_view = (CollapsingToolbarLayout)
                        mFrameLayout.findViewById(R.id.coll_toolbar);

                ValueAnimator animator = ValueAnimator.ofInt(0, 255);
                animator.setDuration(mDuration);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int alpha = (int) animation.getAnimatedValue();
                        iv_menu.setImageAlpha(alpha);
                        tv_logo_text.setAlpha(alpha);
                        iv_main_bg.setImageAlpha(alpha);
                        coll_view.setAlpha(alpha);
                    }
                });
                animator.start();

                final RecyclerView recycView = (RecyclerView)
                        mFrameLayout.findViewById(R.id.recycview_main);

                CommonAdapter adapter = new CommonAdapter(RegisterActivity.this);
                recycView.setLayoutManager(
                        new GridLayoutManager(RegisterActivity.this, 3));
                recycView.setAdapter(adapter);
            }
        });

        TransitionManager.go(mSceneSignUp);
    }


    private void setSize(int width, int height) {
        mTvLoginWidth = width;
        mTvLoginHeight = height;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private enum LoginChoose {
//        SIGNIN, SIGNUP
//    }
}
