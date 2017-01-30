package spr.dev.activity.login;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import spr.dev.R;
import spr.dev.adapter.CommonAdapter;
import spr.dev.view.LoginLoadingView;

/**
 * Created by hanki on 2017/1/25.
 */

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private FrameLayout mFrtContent;
    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;

    private int mTvSignUpWidth;
    private int mTvSignUpHeight;
    private int mDuration;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mFrtContent = (FrameLayout) findViewById(R.id.frt_content);
        mDuration = getResources().getInteger(R.integer.duration);

        mSceneSignUp = Scene.getSceneForLayout(mFrtContent, R.layout.scene_sign_up, this);
        mSceneSignUp.setEnterAction(new Runnable() {
            @Override
            public void run() {
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

        mSceneMain = Scene.getSceneForLayout(mFrtContent, R.layout.scene_main, this);
        mSceneMain.setEnterAction(new Runnable() {
            @Override
            public void run() {
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
        float finalRadius = (float)
                Math.hypot(mFrtContent.getWidth(), mFrtContent.getHeight());

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        Animator anim = ViewAnimationUtils
                .createCircularReveal(mFrtContent,
                        x + mTvSignUpWidth / 2,
                        y - mTvSignUpHeight / 2,
                        100,
                        finalRadius);

        mFrtContent.setBackgroundColor(getResources().getColor(R.color.colorBg));

        anim.setDuration(mDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFrtContent.setBackgroundColor(Color.TRANSPARENT);
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
