package bap.training.com.equizlocal.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.adapter.MainPagerAdapter;

public class LearnActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mLearnViewPager;
    private MainPagerAdapter mMainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_layout);
        getWidget();
        setWidget();
    }

    private void setWidget() {
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mLearnViewPager.setAdapter(mMainPagerAdapter);
        mTabLayout.setupWithViewPager(mLearnViewPager);
    }

    private void getWidget() {
        mLearnViewPager = (ViewPager) findViewById(R.id.learnViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }
}
