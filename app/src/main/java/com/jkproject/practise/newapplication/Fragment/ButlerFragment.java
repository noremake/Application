package com.jkproject.practise.newapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkproject.practise.newapplication.R;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Fragment
 *  文件名:   ButlerFragment
 *  创建者:   JK
 *  创建时间:  2017/5/8 19:04
 *  描述：    TODO
 */

public class ButlerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.butler_fragment, null);
        return view;
    }
}
