package com.jkproject.practise.newapplication.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkproject.practise.newapplication.Entity.NewsData;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.Utils.PicassoUtil;

import java.util.List;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Adapter
 *  文件名:   NewsAdapter
 *  创建者:   JK
 *  创建时间:  2017/5/18 21:46
 *  描述：    TODO
 */

public class NewsAdapter extends ArrayAdapter<NewsData> {

    private Context mContext;
    private int mResource;

    public NewsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NewsData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsData data = getItem(position);
        ViewHolder holder;
        if (convertView ==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_resource = (TextView) convertView.findViewById(R.id.tv_resource);
            holder.img_iv = (ImageView) convertView.findViewById(R.id.img_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(data.getTitle());
        holder.tv_resource.setText(data.getResource());

        //Picasso.with(mContext).load(data.getNewsUrl()).into(holder.img_iv);
        if (!TextUtils.isEmpty(data.getNewsUrl())){
            PicassoUtil.loadImageView(mContext, data.getNewsUrl(), holder.img_iv);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView img_iv;
        private TextView tv_title;
        private TextView tv_resource;
    }
}
