package com.kd.higit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.bean.Repository;
import com.kd.higit.fragment.ReposAboutFragment;
import com.kd.higit.fragment.SourceCodeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KD on 2016/6/22.
 */
public class ReposDetail extends BaseSwipeActivity implements ReposAboutFragment.GetRepos, SourceCodeFragment.GetReposToSCF{
    private static String TAG = ReposDetail.class.getSimpleName();
    private Repository repos;
    private MyTabPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Override
    protected void setTitle(TextView view) {
        if (repos != null && !TextUtils.isEmpty(repos.getName())) {
            view.setText(repos.getName());
        } else {
            view.setText("");
        }
    }

    @Override
    public Repository getRepos() {
        return repos;
    }

    @Override
    public Repository getReposToSCF() {
        return repos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        repos = intent.getParcelableExtra(ReposListActivity.REPOS);
        setContentView(R.layout.detail_content);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerAdapter = new MyTabPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ReposAboutFragment(), "About");
        pagerAdapter.addFragment(new SourceCodeFragment(), "Code");

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        ////关闭预加载，默认一次只加载一个Fragment
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        //一下两行必须同时设置，标签评分tab
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public class MyTabPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
