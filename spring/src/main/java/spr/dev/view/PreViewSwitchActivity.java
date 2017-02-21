package spr.dev.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import spr.dev.R;
import spr.dev.util.Bimp;

/**
 * Created by hanki on 2017/2/20.
 */

public class PreViewSwitchActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "PreViewSwitchActivity";
    public List<String> drr = new ArrayList<String>();
    public List<String> del = new ArrayList<String>();
    int type;
    private Intent intent;
    // 返回按钮
    private ImageView back_bt;
    // 发送按钮
    private Button send_bt;
    // 删除按钮
    private Button del_bt;
    // 获取前一个activity传过来的position
    private int position;
    // 当前的位置
    private int location = 0;
    private ArrayList<View> listViews = null;
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;
    private List<Bitmap> mBmpList = new ArrayList<Bitmap>();
    private Context mContext;
    private int mPosition;
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
            isShowOkBt();
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_gallery);
        mContext = this;

        findViews();
        init();
        addListeners();
    }

    public void findViews() {
        back_bt = (ImageView) findViewById(R.id.gallery_back);
        send_bt = (Button) findViewById(R.id.send_button);
        del_bt = (Button) findViewById(R.id.gallery_del);

    }

    public void init() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = Integer.parseInt(intent.getStringExtra("position"));

        // 为发送按钮设置文字
        pager = (ViewPagerFixed) findViewById(R.id.gallery01);
        pager.setOnPageChangeListener(pageChangeListener);


        String[] arr_photo = getIntent().getStringArrayExtra("PHOTOES");

        for (int i = 0; i < arr_photo.length; i++) {
            Log.e(TAG, arr_photo[i]);
            try {
                mBmpList.add(Bimp.revitionImageSize(arr_photo[i]));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        for (int i = 0; i < mBmpList.size(); i++) {
            initListViews(mBmpList.get(i));
        }
        isShowOkBt();
        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        pager.setPageMargin((int) getResources()
                .getDimensionPixelOffset(R.dimen.ui_10_dip));
        mPosition = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(mPosition);

        del_bt.setVisibility(View.GONE);
    }

    public void addListeners() {
        back_bt.setOnClickListener(this);
        send_bt.setOnClickListener(this);
        del_bt.setOnClickListener(this);
    }

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);

        img.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }

    public void isShowOkBt() {
        if (mBmpList.size() > 0) {
            send_bt.setText((location + 1) + "/" + mBmpList.size());
            send_bt.setTextColor(Color.WHITE);
        } else {
            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gallery_back:
                finish();
                break;

            case R.id.send_button:

                break;

            case R.id.gallery_del:

                break;

            default:
                break;
        }

    }


    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;
        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }


}
