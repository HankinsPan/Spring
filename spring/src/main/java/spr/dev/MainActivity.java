package spr.dev;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import spr.dev.adapter.ViewMainAdapter;
import spr.dev.util.ShowToast;


public class MainActivity extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "MainActivity";

    private boolean isState = true;

    //    private LinearLayout head_layout;
    private TabLayout toolbar_tab;
    private ViewPager main_vp;
    private CollapsingToolbarLayout mColl;
    private CoordinatorLayout root_layout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private ImageView iv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.ColorToast(MainActivity.this, "click toolbar", 1200);
            }
        });

        toolbar.setOnMenuItemClickListener(this);

        iv_title = (ImageView) findViewById(R.id.iv_title);

        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);
        mColl = (CollapsingToolbarLayout) findViewById(R.id.coll_bar_layout);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -iv_title.getHeight() / 2) {
                    mColl.setTitle("{Spring}");
                } else {
                    mColl.setTitle(" ");
                }
            }
        });

        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp = (ViewPager) findViewById(R.id.main_vp_container);

        ViewMainAdapter vpAdapter = new ViewMainAdapter(getSupportFragmentManager(), this);
        main_vp.setAdapter(vpAdapter);
        main_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp));

    }


    @Override
    public void onBackPressed() {
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_ring:
                msg += "Click ring";
                break;

            case R.id.action_star:
                msg += "Click star";
                break;

            case R.id.action_share:
                msg += "Click share";
                break;

            case R.id.action_setting:
                msg += "Click setting";
                break;
        }

        if (!msg.equals("")) {
            ShowToast.ColorToast(MainActivity.this, msg, 2000);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.base_toolbar_menu,menu);
        return true;
    }
}
