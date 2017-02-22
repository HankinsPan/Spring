package spr.dev;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.Timer;
import java.util.TimerTask;

import spr.dev.activity.publish.PublishActivity;
import spr.dev.adapter.RollPageAdapter;
import spr.dev.adapter.ViewMainAdapter;
import spr.dev.util.ShowToast;


public class MainActivity extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    private static final String TAG = "MainActivity";

    private boolean isState = true;

    private TabLayout toolbar_tab;
    private ViewPager main_vp;
    private CollapsingToolbarLayout mColl;
    private CoordinatorLayout root_layout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton float_btn;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout rlt_image;
    private RollPagerView pagerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initView();
        rollPager();
        addListener();
        initRefresh();

//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words", "Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    Log.e(TAG, "-*- success! -*-");
//                }
//            }
//        });

        setSupportActionBar(toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -rlt_image.getHeight() / 2) {
                    scrollFabhide(verticalOffset);
                } else {
                    mColl.setTitle("");
                }
            }
        });

        ViewMainAdapter vpAdapter = new ViewMainAdapter(getSupportFragmentManager(), this);
        main_vp.setAdapter(vpAdapter);
        main_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp));

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }


    private void scrollFabhide(int verticalOffset) {
        float alpha = 2 - (float) (-verticalOffset / 270.1);
        float_btn.setAlpha(alpha);
        if (alpha <= 0.3) {
            float_btn.setVisibility(View.GONE);
        } else {
            float_btn.setVisibility(View.VISIBLE);
        }
        pagerView.setAlpha(alpha);
    }


    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp = (ViewPager) findViewById(R.id.main_vp_container);

        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);
        mColl = (CollapsingToolbarLayout) findViewById(R.id.coll_bar_layout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        float_btn = (FloatingActionButton) findViewById(R.id.float_btn);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        rlt_image = (RelativeLayout) findViewById(R.id.rlt_image);
        pagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
    }


    private void rollPager() {
        pagerView.setPlayDelay(1500);
        pagerView.setAnimationDurtion(800);
        pagerView.setAdapter(new RollPageAdapter());
        pagerView.setHintView(new ColorPointHintView(this,
                Color.parseColor("#1976d2"), Color.WHITE));
    }

    private void addListener() {
        toolbar.setOnMenuItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        float_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_btn:
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.top_to_bottom_in, R.anim.bottom_to_top_out);
                break;

            default:
                break;

        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_ring:
                msg += "Click ring";
                break;

            case R.id.action_idea:
                msg += "Click idea";
                break;

            case R.id.action_set:
                msg += "Click set";
                break;

            case R.id.action_about:
                msg += "Click about";
                break;

            case R.id.action_out:
                msg += "Click out";
                break;
        }

        if (!msg.equals("")) {
            ShowToast.ColorToast(MainActivity.this, msg, 2000);
        }
        return true;
    }


    private void initRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.theme_blue_eight,
                R.color.red,
                R.color.colorThemeYellow,
                R.color.green);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isState) {
                isState = false;
                ShowToast.ColorToast(MainActivity.this, "再次点击退出程序", 1200);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isState = true;
                    }
                }, 1200);
            } else {
                this.finish();
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String msg = "";

        switch (item.getItemId()) {
            case R.id.nav_github:
                msg += "Github";
                break;

            case R.id.nav_csdn:
                msg += "CSDN";
                break;

            case R.id.nav_jian:
                msg += "简书";
                break;

            case R.id.nav_sgement:
                msg += "segmentfault";
                break;

            case R.id.nav_others:
                msg += "Third Part";
                break;

            case R.id.nav_dev:
                msg += "Developer";
                break;
        }

        if (!msg.equals("")) {
            ShowToast.ColorToast(MainActivity.this, msg, 2000);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RefreshData();
                // TODO: 2017/2/18 adapter notifyDataSetChanged at here
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

    }

    private void RefreshData() {
        // TODO: 2017/2/18 request more data at here
//        ShowToast.ColorToast(MainActivity.this,"Load More Data",1200);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}















