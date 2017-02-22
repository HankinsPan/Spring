package spr.dev.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import spr.dev.view.framelayout.PageFragment;
import spr.dev.view.framelayout.PublishFragment;

/**
 * Created by hanki on 2017/2/16.
 */

public class ViewMainAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"圈子", "收藏", "我的"};
    private Context context;

    public ViewMainAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
//        return PageFragment.newInstance(position + 1);

        switch (position) {
            case 0:
                return PageFragment.newInstance(1);

            case 1:
            case 2:
                PublishFragment publishFragment = new PublishFragment();
                return publishFragment;

        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
