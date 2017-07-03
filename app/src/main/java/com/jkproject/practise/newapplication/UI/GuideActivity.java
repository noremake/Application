package com.jkproject.practise.newapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jkproject.practise.newapplication.R;

import java.util.ArrayList;
import java.util.List;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   GuideActivity
 *  创建者:   JK
 *  创建时间:  2017/5/12 12:33
 *  描述：    引导页
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private List<View> mList = new ArrayList<>();
    private View view1, view2, view3;
    private ImageView point1, point2, point3;
    private Button button, button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        initView();
    }

    private void initView(){
        button = (Button) findViewById(R.id.button_guide);
        button2 = (Button) findViewById(R.id.button_guide2);
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.GONE);

        viewPager = (ViewPager) findViewById(R.id.viewPager_guide);
        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        setPointImage(true, false, false);

        view1 = View.inflate(this, R.layout.view_one, null);
        view2 = View.inflate(this, R.layout.view_two, null);
        view3 = View.inflate(this, R.layout.view_three, null);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        viewPager.setAdapter(new GuideAdapter());

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    setPointImage(true, false, false);
                }else if (position == 1){
                    setPointImage(false, true, false);
                    button.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.GONE);
                }else {
                    setPointImage(false, false, true);
                    button.setVisibility(View.GONE);
                    button2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_guide:
            case R.id.button_guide2:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }
    }

    class GuideAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void setPointImage(boolean isChecked1, boolean isChecked2, boolean isChecked3){
        if (isChecked1){
            point1.setBackgroundResource(R.drawable.point_green);
        }else{
            point1.setBackgroundResource(R.drawable.point_gray);
        }

        if (isChecked2){
            point2.setBackgroundResource(R.drawable.point_green);
        }else{
            point2.setBackgroundResource(R.drawable.point_gray);
        }

        if (isChecked3){
            point3.setBackgroundResource(R.drawable.point_green);
        }else{
            point3.setBackgroundResource(R.drawable.point_gray);
        }
    }
}
