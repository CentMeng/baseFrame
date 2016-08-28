/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.beijing.navi.view.scrollviewpager.adbanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;
import com.beijing.navi.utils.ImageUtils;
import com.core.api.entity.Article;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author http://blog.csdn.net/finddreams
 * @Description: 图片适配器
 */
public class ImagePagerAdapter extends BaseAdapter {

    private BaseActivity context;
    private List<Article> bannerList = new ArrayList<Article>();
    private int size;
    private boolean isInfiniteLoop;

    public ImagePagerAdapter(BaseActivity context) {
        this.context = context;
//		this.bannerList = bannerList;
        if (bannerList != null) {
            this.size = bannerList.size();
        }
        isInfiniteLoop = false;

    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : bannerList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup container) {

        View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.fragment_screen_slide_page, null);
            holder = new ViewHolder();

            holder.imageView = (SimpleDraweeView) view.findViewById(R.id.pic);

            view.setTag(holder);

        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Article banner = this.bannerList.get(getPosition(position));

        ImageUtils.setSimpleDrawViewBy1618WithoutFace(holder.imageView, banner.getCover(), 550, 0);

        holder.imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        return view;
    }

    private static class ViewHolder {

        SimpleDraweeView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setBannerList(List<Article> bannerList) {
        this.bannerList = bannerList;
        if (bannerList != null) {
            this.size = bannerList.size();
        }
    }


    public void recycle(){
        context = null;
        bannerList.clear();
        bannerList = null;
        System.gc();
    }

}
