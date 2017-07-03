package com.jkproject.practise.newapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.jkproject.practise.newapplication.Adapter.GirlsAdapter;
import com.jkproject.practise.newapplication.Entity.GirlsData;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.Utils.PicassoUtil;
import com.jkproject.practise.newapplication.View.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Fragment
 *  文件名:   GirlsFragment
 *  创建者:   JK
 *  创建时间:  2017/5/8 19:12
 *  描述：    TODO
 */

public class GirlsFragment extends Fragment {

    //GridView初始化；
    private GridView mGridView;
    private ImageView girlsImage;
    //
    private List<GirlsData> girlsList = new ArrayList<>();
    //图片地址容器；
    private List<String> girlsUrl = new ArrayList<>();

    private CustomDialog dialog;

    private PhotoViewAttacher mAttacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.girls_fragment, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.gridView_girls);

        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girls,
                R.style.Theme_dialog, Gravity.CENTER);
        girlsImage = (ImageView) dialog.findViewById(R.id.iv_girls);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtil.loadImageView(getActivity(), girlsUrl.get(position), girlsImage);
                //缩放
                mAttacher = new PhotoViewAttacher(girlsImage);
                //刷新
                mAttacher.update();
                dialog.show();

            }
        });
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_LONG).show();
                //解析地址；
                prasingJson(t);
            }
        });
    }

    private void prasingJson(String t) {
        try{
            //转化成jsonObject对象；
            JSONObject jsonObject = new JSONObject(t);
            //将results字段的内容储存到jsonArray中；
            JSONArray jsonList = jsonObject.getJSONArray("results");
            //遍历jsonArray；
            for (int i = 0; i < jsonList.length(); i++){
                JSONObject json = (JSONObject) jsonList.get(i);
                //取出每张图片的url地址；
                String url = json.getString("url");
                //将地址储存到容器中；
                girlsUrl.add(url);

                GirlsData gData = new GirlsData();
                gData.setGirlsUrl(url);
                girlsList.add(gData);
            }
            //连接适配器；
            GirlsAdapter adpater = new GirlsAdapter(getActivity(), girlsList);
            mGridView.setAdapter(adpater);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
