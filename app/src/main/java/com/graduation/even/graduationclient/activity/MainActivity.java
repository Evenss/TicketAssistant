package com.graduation.even.graduationclient.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.fragment.HomeFragment;
import com.graduation.even.graduationclient.fragment.MyInfoFragment;
import com.graduation.even.graduationclient.util.PLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView imageView;
    private TextView textView;
    private String[] str = {"首页", "我的"};
    private int[] photoId = {R.mipmap.icon_home_selected, R.mipmap.icon_me};

    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_bottom);
    }

    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        Fragment homeFragment = new HomeFragment();
        Fragment myInfoFragment = new MyInfoFragment();
        mFragmentList.add(homeFragment);
        mFragmentList.add(myInfoFragment);
    }

    @Override
    protected void initEvent() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                PLog.i("onTabSelected position: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.getCustomView().findViewById(R.id.item_picture).setBackgroundResource(R.mipmap.icon_home_selected);
                        break;
                    case 1:
                        tab.getCustomView().findViewById(R.id.item_picture).setBackgroundResource(R.mipmap.icon_me_selected);
                        break;
                }
                TextView view = (TextView) tab.getCustomView().findViewById(R.id.item_text);
                view.setTextColor(getResources().getColor(R.color.mainBottomSelectedText));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                PLog.i("onTabUnselected position: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.getCustomView().findViewById(R.id.item_picture).setBackgroundResource(R.mipmap.icon_home);
                        break;
                    case 1:
                        tab.getCustomView().findViewById(R.id.item_picture).setBackgroundResource(R.mipmap.icon_me);
                        break;
                }
                TextView view = (TextView) tab.getCustomView().findViewById(R.id.item_text);
                view.setTextColor(getResources().getColor(R.color.mainBottomNormalText));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                PLog.i("onTabReselected position: " + tab.getPosition());
            }
        });
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public View getTabView(int position) {
            PLog.i("get tab view position is" + position);
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.bar_bottom_item, null);
            imageView = (ImageView) v.findViewById(R.id.item_picture);
            textView = (TextView) v.findViewById(R.id.item_text);
            imageView.setBackgroundResource(photoId[position]);
            textView.setText(str[position]);
            if (position == 0) {
                textView.setTextColor(v.getResources().getColor(R.color.mainBottomSelectedText));
            }
            return v;
        }
    }
}
