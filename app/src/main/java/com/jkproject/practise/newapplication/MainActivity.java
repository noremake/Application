package com.jkproject.practise.newapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jkproject.practise.newapplication.Fragment.ButlerFragment;
import com.jkproject.practise.newapplication.Fragment.GirlsFragment;
import com.jkproject.practise.newapplication.Fragment.UserFragment;
import com.jkproject.practise.newapplication.Fragment.WechatFragment;
import com.jkproject.practise.newapplication.UI.Setting;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除阴影；
        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    //初始化数据
    private void initData(){
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.first_title));
        mTitle.add(getString(R.string.second_title));
        mTitle.add(getString(R.string.third_title));
        mTitle.add(getString(R.string.forth_title));

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlsFragment());
        mFragment.add(new UserFragment());
    }

    private void initView(){
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setVisibility(View.GONE);

        fab_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Setting.class));
            }
        });

        mViewPager.setOffscreenPageLimit(mFragment.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    fab_setting.setVisibility(View.GONE);
                }else{
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
