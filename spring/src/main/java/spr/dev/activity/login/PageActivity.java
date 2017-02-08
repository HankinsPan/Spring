package spr.dev.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spr.dev.R;
import spr.dev.adapter.ViewPagerAdapter;
import spr.dev.constant.SP_Constant;
import spr.dev.util.SharedPreferencesUtil;

/**
 * Created by hanki on 2017/1/25.
 */

public class PageActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener {
    private static final String TAG = "PageActivity";
    SharedPreferencesUtil sp;

    private ViewPager viewPager;
    private List<View> viewList;

    private View view1;
    private View view2;
    private View view3;

    private TextView startApp;
    private ViewPagerAdapter adapter;

    private ImageView[] dots;
    private int[] ids = {R.id.iv0, R.id.iv1, R.id.iv2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_viewpager);
        initView();
        initData();
        adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_viewpager);

        view1 = View.inflate(this, R.layout.view_viewpager01, null);
        view2 = View.inflate(this, R.layout.view_viewpager02, null);
        view3 = View.inflate(this, R.layout.view_viewpager03, null);

        sp = new SharedPreferencesUtil(PageActivity.this, SP_Constant.SP_NAME);

        startApp = (TextView) view3.findViewById(R.id.start_app);
        startApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PageActivity.this, SignActivity.class);

                sp.saveString("isFirstUse", "notFirst");
                Log.e(TAG, "--- Set isFirstUse is ---" + sp.getStringByKey("isFirstUse"));
                startActivity(intent);
                finish();
            }
        });

        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        viewPager.setOnPageChangeListener(this);
    }

    private void initData() {
        dots = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position==i){
                dots[i].setImageResource(R.drawable.point_selected);
            }else {
                dots[i].setImageResource(R.drawable.point_no_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
