package com.jkproject.practise.newapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jkproject.practise.newapplication.Adapter.NewsAdapter;
import com.jkproject.practise.newapplication.Entity.NewsData;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.UI.WebNewsActivity;
import com.jkproject.practise.newapplication.Utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Fragment
 *  文件名:   NewsFragment
 *  创建者:   JK
 *  创建时间:  2017/5/8 19:11
 *  描述：    新闻页
 */

public class WechatFragment extends Fragment {

    private ListView listView;
    private List<NewsData> mList = new ArrayList<>();
    private NewsAdapter myAdapter;
    private ImageView imageView;

    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        listView = (ListView) view.findViewById(R.id.listView_news);

        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_LONG).show();
                parsingJson(t);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent webIntent = new Intent(getActivity(),WebNewsActivity.class);
                webIntent.putExtra("title", mListTitle.get(position));
                webIntent.putExtra("url", mListUrl.get(position));
                startActivity(webIntent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonresult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                NewsData data = new NewsData();

                String titlr = json.getString("title");
                String url = json.getString("url");

                data.setTitle(titlr);
                data.setResource(json.getString("source"));
                data.setNewsUrl(json.getString("firstImg"));

                //存储item所需要的内容；
                mList.add(data);

                //标题容器；
                mListTitle.add(titlr);
                //图片地址容器；
                mListUrl.add(url);
            }
            NewsAdapter myAdapter = new NewsAdapter(getActivity(), R.layout.item_news, mList);
            listView.setAdapter(myAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

