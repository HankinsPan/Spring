package spr.dev.activity.login;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;

import spr.dev.R;
import spr.dev.util.SharedPreferencesUtil;
import spr.dev.util.ShowToast;
import spr.dev.util.Tools;
import spr.dev.view.SignView;

/**
 * Created by hanki on 2017/2/5.
 */

public class SignActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "SignActivity";

    // 模拟用户信息
    private static String i_Phone = "15354872767";
    private static String i_SmsCode = "654321";

    SharedPreferencesUtil sp;

    private RelativeLayout mRelat;

    private Scene mSceneLogging;
    private Scene mSceneMain;

    private AutoCompleteTextView aot_phone;
    private AutoCompleteTextView aot_smsCode;
    private Button btn_smsCode;


    /**
     * 验证码计时器
     */
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btn_smsCode.setText(millisUntilFinished / 1000 + " s");
        }

        @Override
        public void onFinish() {
            btn_smsCode.setText("SmsCode");
            btn_smsCode.setClickable(true);
            btn_smsCode.setBackgroundColor(Color.parseColor("#2D78B1"));
        }
    };

    private SignView signViewBtn;
    private int mDuration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        initView();
        addListener();

    }

    private void initView() {
        mRelat = (RelativeLayout) findViewById(R.id.relat_content);
        mDuration = getResources().getInteger(R.integer.duration);

        mSceneLogging = Scene.getSceneForLayout(mRelat, R.layout.scene_logging, this);
        mSceneMain = Scene.getSceneForLayout(mRelat, R.layout.scene_new_main, this);


        aot_phone = (AutoCompleteTextView) findViewById(R.id.aot_phone);
        aot_smsCode = (AutoCompleteTextView) findViewById(R.id.aot_smsCode);
        btn_smsCode = (Button) findViewById(R.id.btn_smsCode);
        signViewBtn = (SignView) findViewById(R.id.signView_btn);

    }

    private void addListener() {
        btn_smsCode.setOnClickListener(this);
        signViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_smsCode:
                String uPhone = aot_phone.getText().toString().trim();

                if (!TextUtils.isEmpty(uPhone)
                        && Tools.validatePhone(uPhone)
                        && uPhone.equals(i_Phone)) {
                    SendSmsCode();
                    ShowToast.ColorToast(SignActivity.this, "SmsCode is Send", 1200);
                } else {
                    ShowToast.ColorToast(SignActivity.this, "phone number is error", 1200);
                    Log.e(TAG, "-- phone number is error --");
                }
                break;

            case R.id.signView_btn:
                String nPhone = aot_phone.getText().toString().trim();
                String nSmsCode = aot_smsCode.getText().toString().trim();

                if (!TextUtils.isEmpty(nPhone)
                        && Tools.validatePhone(nPhone)
                        && nPhone.equals(i_Phone)) {
                    if (!TextUtils.isEmpty(nSmsCode)
                            && Tools.isSMSCodeValid(nSmsCode)
                            && nSmsCode.equals(i_SmsCode)) {
                        RunAnim(signViewBtn);
                    }

                } else {
                    ShowToast.ColorToast(SignActivity.this, "phone number is error", 1200);
                    Log.e(TAG, "-- phone number is error --");
                }
                break;

            default:
                break;


        }

    }

    private void SendSmsCode() {

    }

    private void RunAnim(View view) {
        float finalRadius = (float) Math.hypot(mRelat.getWidth(), mRelat.getHeight());

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        Animator anim = ViewAnimationUtils.createCircularReveal(mRelat,
                x + signViewBtn.getWidth() / 2,
                y - signViewBtn.getHeight() / 2,
                100,
                finalRadius);

        mRelat.setBackgroundColor(getResources().getColor(R.color.colorBg));
        anim.setDuration(mDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();

        TransitionManager.go(mSceneLogging, new ChangeBounds()
                .setDuration(mDuration)
                .setInterpolator(new DecelerateInterpolator()));

    }


}
