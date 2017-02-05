package spr.dev.activity.login;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import spr.dev.R;
import spr.dev.adapter.CommonAdapter;
import spr.dev.view.LoginLoadingView;

/**
 * Created by hanki on 2017/1/25.
 */

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private static final String i_name = "hankins";

    private RelativeLayout mFrtContent;

    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;

    private EditText edt_sName;

    private int mTvSignUpWidth;
    private int mTvSignUpHeight;
    private int mDuration;

    private View sView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mFrtContent = (RelativeLayout) findViewById(R.id.frt_content);
        mDuration = getResources().getInteger(R.integer.duration);

        sView = View.inflate(this,R.layout.scene_sign_up,null);
        edt_sName = (EditText) sView.findViewById(R.id.edt_sName);

        mSceneSignUp = Scene.getSceneForLayout(mFrtContent, R.layout.scene_sign_up, this);
        mSceneSignUp.setEnterAction(new Runnable() {
            @Override
            public void run() {

                Log.e(TAG, "-- mSceneSignUp --");

                final LoginLoadingView loadingView = (LoginLoadingView)
                        mFrtContent.findViewById(R.id.login_view);

                ViewTreeObserver vto = loadingView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        setSize(loadingView.getMeasuredWidth(),
                                loadingView.getMeasuredHeight());
                    }
                });
                loadingView.setOnClickListener(LoginActivity.this);

            }
        });

        mSceneLogging = Scene.getSceneForLayout(mFrtContent, R.layout.scene_logging, this);
        mSceneLogging.setEnterAction(new Runnable() {
            @Override
            public void run() {

                Log.e(TAG, "-- mSceneLogging --");

                final LoginLoadingView loadingView = (LoginLoadingView)
                        mFrtContent.findViewById(R.id.login_view);
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

        mSceneMain = Scene.getSceneForLayout(mFrtContent, R.layout.scene_new_main, this);
        mSceneMain.setEnterAction(new Runnable() {
            @Override
            public void run() {

                Log.e(TAG, "-- mSceneMain --");

                final ImageView imgMenu = (ImageView) mFrtContent.findViewById(R.id.img_menu);
                final ImageView imgUser = (ImageView) mFrtContent.findViewById(R.id.img_user);

                ValueAnimator animator = ValueAnimator.ofInt(0, 255);
                animator.setDuration(mDuration);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int alpha = (int) animation.getAnimatedValue();
                        imgMenu.setImageAlpha(alpha);
                        imgUser.setImageAlpha(alpha);
                    }
                });
                animator.start();

                final RecyclerView recyclerView = (RecyclerView)
                        mFrtContent.findViewById(R.id.rv_common);

                CommonAdapter adapter = new CommonAdapter(LoginActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(LoginActivity.this, 3));
                recyclerView.setAdapter(adapter);
            }
        });

        TransitionManager.go(mSceneSignUp);
    }

    private void setSize(int width, int height) {
        mTvSignUpWidth = width;
        mTvSignUpHeight = height;
    }

    @Override
    public void onClick(View view) {

        Log.e(TAG, "-- onClick --");

        switch (view.getId()) {

            case R.id.login_view:

                String vName = edt_sName.getText().toString().trim();

                if (!TextUtils.isEmpty(vName)) {
                    Log.e(TAG, "-- vName not empty --" + vName);
                    if (vName.equals(i_name)) {
                        Log.e(TAG, "-- vName -> i_name --" + vName);
                    } else {
                        Log.e(TAG, "-- vName != i_name !=hankins --" + vName);
                    }
                } else {
                    Log.e(TAG, "-- vName is empty --" + vName);
                }


                break;

            default:
                break;

        }

//        Log.e(TAG,"-- onClick --");
//
//        float finalRadius = (float)
//                Math.hypot(mFrtContent.getWidth(), mFrtContent.getHeight());
//
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];
//
//        Animator anim = ViewAnimationUtils
//                .createCircularReveal(mFrtContent,
//                        x + mTvSignUpWidth / 2,
//                        y - mTvSignUpHeight / 2,
//                        100,
//                        finalRadius);
//
//        mFrtContent.setBackgroundColor(getResources().getColor(R.color.colorBg));
//
//        anim.setDuration(mDuration);
//        anim.setInterpolator(new AccelerateDecelerateInterpolator());
//        anim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                Log.e(TAG,"-- onClick -- onAnimationStart ");
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.e(TAG,"-- onClick -- onAnimationEnd ");
//                mFrtContent.setBackgroundColor(Color.TRANSPARENT);
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                Log.e(TAG,"-- onClick -- onAnimationCancel ");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                Log.e(TAG,"-- onClick -- onAnimationRepeat ");
//            }
//        });
//        anim.start();
//
//        TransitionManager.go(mSceneLogging, new ChangeBounds()
//                .setDuration(mDuration)
//                .setInterpolator(new DecelerateInterpolator()));

    }
}
