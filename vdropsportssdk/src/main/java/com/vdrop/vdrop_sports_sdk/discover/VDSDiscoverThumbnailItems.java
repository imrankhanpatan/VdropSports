package com.vdrop.vdrop_sports_sdk.discover;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.vdrop.vdrop_sports_sdk.Campaignplaylist.VDSCampaignActivity;
import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.model.Discover;
import com.vdrop.vdrop_sports_sdk.model.Playlist;
import com.vdrop.vdrop_sports_sdk.utils.Utils;

import java.util.ArrayList;

/**
 * Created by dennis on 30/5/17.
 */

public class VDSDiscoverThumbnailItems extends LinearLayout {

    ImageView thumbnail1;
    ImageView thumbnail2;
    ImageView thumbnail3;
    ImageView thumbnail4;
    ImageView thumbnail5;
    ImageView thumbnail6;
    Context context;
    private ArrayList<String> campaignIds;
    private int discoverImageDimensions = 0;
    private int thumbnailPercentage = 33;
    private ImageView[] imageList = {thumbnail1, thumbnail2, thumbnail3, thumbnail4, thumbnail5,
            thumbnail6};
    private ArrayList<String> lastedVideoList = new ArrayList<String>();

    public VDSDiscoverThumbnailItems(Context context) {
        super(context);
    }
    public VDSDiscoverThumbnailItems(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
        this.context = context;
    }

    private void initViews(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.vds_layout_discover_grid_content, this);
        imageList[0] = (ImageView) this.findViewById(R.id.thumbnail1);
        imageList[1] = (ImageView) this.findViewById(R.id.thumbnail2);
        imageList[2] = (ImageView) this.findViewById(R.id.thumbnail3);
        imageList[3] = (ImageView) this.findViewById(R.id.thumbnail4);
        imageList[4] = (ImageView) this.findViewById(R.id.thumbnail5);
        imageList[5] = (ImageView) this.findViewById(R.id.thumbnail6);

    }

    public void setImages(final ArrayList<String> urls, ArrayList<String> campaignIds,
                          final ArrayList<String> discoverList) {
        discoverImageDimensions = Utils.deviceDimensions(context, thumbnailPercentage, "width");
        this.campaignIds = campaignIds;
        lastedVideoList.addAll(urls);
        Log.i("discoverImageDimension", discoverImageDimensions+ "");
        for (int i =0;i<urls.size();i++){
            Log.i("URLS",""+urls.get(i)+"   "+urls.size());
            Log.i("CAMPAIGN_IDS",""+campaignIds.get(i));
            RequestCreator requestCreator = Picasso.with(context).load(Utils.
                    getUrlWithDimension(urls.get(i),discoverImageDimensions,discoverImageDimensions));
            requestCreator.resize(discoverImageDimensions,discoverImageDimensions).into(this.imageList[i]);
            int campaignIdsIndex = i;
            final int finalI = i;
            this.imageList[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   Toast.makeText(context, "select image from "+discoverList.get(0) +" list",
                           Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}