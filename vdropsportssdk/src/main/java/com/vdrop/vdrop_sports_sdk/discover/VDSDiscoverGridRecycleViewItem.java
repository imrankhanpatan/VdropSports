package com.vdrop.vdrop_sports_sdk.discover;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.model.Discover;

import java.util.ArrayList;

/**
 * Created by dennis on 30/5/17.
 */

public class VDSDiscoverGridRecycleViewItem extends LinearLayout {
    private ViewPager viewPager;
    private TextView title;
    private VDSDiscoverThumbnailVPAdapter adapter;
    private Context context;
    Discover discover;
    ArrayList<String> images;
    ArrayList<String> campaignIds;
    int count;
    ArrayList<String>  discoverName = new ArrayList<String>();

    public VDSDiscoverGridRecycleViewItem(Context context) {
        super(context);
        initViews(context, null);
    }

    public VDSDiscoverGridRecycleViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.vds_layout_discover_recycleview_item, this);
        title = (TextView)findViewById(R.id.grid_title);
        viewPager = (ViewPager)findViewById(R.id.grid_viewpager);
       Log.i("VIEW_PAGER_POSITION",""+viewPager.getCurrentItem());

    }


    public void setAdapter(Discover discover) {
        Log.d("DISCOVER_1",""+discover.getName());
         images = new ArrayList<>();
        campaignIds = new ArrayList<>();
        discoverName.add(discover.getName());
        this.discover = discover;
        int size = discover.getPlaylist().size();
        Log.i("DISCOVER_SIZE",""+size);
        if (size > 6) {
            count = 2;
            Log.i("COUNT_VALUE",""+count);
        } else {
            count = 1;
        }

        for (int i = 0; i < size; i++) {
            images.add(discover.getPlaylist().get(i).getThumbnailImageUrl());
            Log.i("images",""+images.size());
            campaignIds.add(discover.getPlaylist().get(i).getCampaignId());
        }
        adapter = new VDSDiscoverThumbnailVPAdapter(context, images, campaignIds, count,discoverName);
        title.setText(discover.getName());
        viewPager.setAdapter(adapter);
    }
}
