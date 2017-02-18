package spr.dev.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import spr.dev.R;

/**
 * Created by hanki on 2017/2/18.
 */

public class RollPageAdapter extends StaticPagerAdapter {

    public int[] imgs = {
            R.mipmap.iv_cyc_bg1,
            R.mipmap.iv_cyc_bg2,
            R.mipmap.iv_cyc_bg3,
            R.mipmap.iv_cyc_bg4};

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return view;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
