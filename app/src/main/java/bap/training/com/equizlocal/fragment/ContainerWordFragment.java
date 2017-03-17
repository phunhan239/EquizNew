package bap.training.com.equizlocal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.adapter.WordPagerAdapter;

/**
 * Created by dvan on 2/14/2017.
 */

public class ContainerWordFragment extends Fragment {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<HashMap<String, String>> mListWords;
    private int mCurrentItemClick;

    public ContainerWordFragment(List<HashMap<String, String>> mLists, int position) {
        this.mListWords = mLists;
        this.mCurrentItemClick = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_container_word, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.containerViewPager);

        mPagerAdapter = new WordPagerAdapter(mListWords);
        mViewPager.setAdapter(mPagerAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(mCurrentItemClick);
    }
}
