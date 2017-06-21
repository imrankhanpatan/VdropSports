package com.vdrop.vdrop_sports_sdk.discover;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.vdrop.vdrop_sports_sdk.Campaignplaylist.VDSCampaignActivity;
import com.vdrop.vdrop_sports_sdk.Campaignplaylist.VDSPopupOptionsDialog;
import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.application.App;
import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.callback.PopupCallBack;
import com.vdrop.vdrop_sports_sdk.model.Campaign;
import com.vdrop.vdrop_sports_sdk.model.Discover;
import com.vdrop.vdrop_sports_sdk.model.Playlist;
import com.vdrop.vdrop_sports_sdk.utils.VDSActivityManager;
import com.vdrop.vdrop_sports_sdk.utils.Constants;

import java.util.ArrayList;
import java.util.Map;

public class VDSDiscoverActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Campaign> campaigns;
    ArrayList<Discover> discoverList;
    private RecyclerView vdsRVDiscoverList;
    private VDSActivityManager VDSActivityManager;
    RecyclerView.LayoutManager mLayoutManager;
    private String campaignId = "590ad8a4420e002f85701996";
    VDSDiscoverRecyclerViewAdapter vdsDRVAdapter;
    private ImageView vdsIVDiscoverCancel;
    private TextView vdsTVDiscoverTitle;
    Context context = this;
    int stopPosition;
    VideoView videoview;
    VDSCampaignActivity vdsCampaignActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vds_activity_discover);
        Log.d("IN", "DISCOVER");
        VDSActivityManager = new VDSActivityManager(this);
        vdsRVDiscoverList = (RecyclerView) findViewById(R.id.vds_recycle_view);
        vdsIVDiscoverCancel = (ImageView) findViewById(R.id.vds_iv_discover_cancel);
        vdsTVDiscoverTitle = (TextView) findViewById(R.id.vds_tv_discover_title);
        mLayoutManager = new LinearLayoutManager(this);
        vdsRVDiscoverList.setLayoutManager(mLayoutManager);
        vdsDRVAdapter = new VDSDiscoverRecyclerViewAdapter(this);
        getDiscoverlist(campaignId);
         vdsCampaignActivity = new VDSCampaignActivity();
        vdsIVDiscoverCancel.setOnClickListener(this);

    }

    private void getDiscoverlist(String campaignId) {
        VDSActivityManager.getDiscoverThumbnaillist(campaignId, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                Log.i("Get", "COMMING");
                discoverList = (ArrayList<Discover>) map.get(Constants.RESPONSE);
                Log.i("RESPONCE_DISCOVER", "" + discoverList.size());

                vdsTVDiscoverTitle.setText("#"+App.getInstance().getCampaignName());
                vdsDRVAdapter.setData(discoverList);
                vdsRVDiscoverList.setAdapter(vdsDRVAdapter);
                vdsDRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
            }
        });
    }
    public static void openDiscoverActivity(Context context) {
        Log.d("IN", "OPEN");
        Intent intent = new Intent(context, VDSDiscoverActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.vds_iv_discover_cancel) {
            onStop();
        }
    }


    public void setPosition(int stopPosition, VideoView vdsVideoView) {
       this.stopPosition = stopPosition;
        this.videoview = vdsVideoView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        onPause();
    }
}
