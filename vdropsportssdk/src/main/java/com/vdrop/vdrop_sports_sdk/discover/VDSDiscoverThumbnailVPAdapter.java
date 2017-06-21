package com.vdrop.vdrop_sports_sdk.discover;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.model.Discover;

import java.util.ArrayList;

/**
 * Created by dennis on 30/5/17.
 */

public class VDSDiscoverThumbnailVPAdapter extends PagerAdapter {
    private Context context;
    private final int count;
    LayoutInflater mLayoutInflater;
    ViewGroup container;
    VDSDiscoverThumbnailItems myLayout;
    private ArrayList<String> campaignIdList;
    private ArrayList<String> urls;
    private ArrayList<String> lastedVideoList = new ArrayList<String>();
    private ArrayList<String> discoverList = new ArrayList<String>();






    public VDSDiscoverThumbnailVPAdapter(Context context, ArrayList<String> urls,
                                         ArrayList<String> campaignIds, int count,
                                         ArrayList<String> discoverName) {
        this.context = context;
        campaignIdList = campaignIds;
        this.urls = urls;
        this.count = count;
        discoverList = discoverName;
        lastedVideoList = new ArrayList<String>();

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        Log.i("COUNT",""+count);
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.vds_layout_discover_viewpager_item,container,false);
        myLayout = (VDSDiscoverThumbnailItems)itemView.findViewById(R.id.layout_view);
        Log.i("INSTantIate",""+position);
        ArrayList<String> imageList = new ArrayList<>();
        ArrayList<String> campaignIds = new ArrayList<>();
        try{
            for (int i = urls.size()-1; i>=0;i--){
                lastedVideoList.add(urls.get(i));
            }
            if (position >0){
                if (position == 1){
                    for (int i =6;i<lastedVideoList.size();i++){
                        if ( i<10) {
                            imageList.add(lastedVideoList.get(i));
                            campaignIds.add(this.campaignIdList.get(i));
                        }
                    }
                }
            }else{
                if (position == 0) {
                    for (int i = 0; i < lastedVideoList.size(); i++) {
                        if (i < 6) {
                            imageList.add(lastedVideoList.get(i));
                            campaignIds.add(this.campaignIdList.get(i));
                        }

                    }
                }
            }
            myLayout.setImages(imageList,campaignIds,discoverList);
            this.container = container;
            container.addView(itemView);

        }catch (Exception e){
            e.printStackTrace();
        }

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
