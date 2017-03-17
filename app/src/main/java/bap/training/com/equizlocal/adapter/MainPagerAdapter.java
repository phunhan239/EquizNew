package bap.training.com.equizlocal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bap.training.com.equizlocal.fragment.MainFragment;

/**
 * Created by dvan on 2/15/2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_FRAGMENT = 2;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.sInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch (position) {
            case 0:
                pageTitle = "Từ đơn";
                break;
            case 1:
                pageTitle = "Cụm từ";
                break;
        }
        return pageTitle;
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }
}
