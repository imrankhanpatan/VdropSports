package com.vdrop.vdrop_sports_sdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.vdrop.vdrop_sports_sdk.Campaignplaylist.VDSCampaignActivity;
import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.api.APICompaign;
import com.vdrop.vdrop_sports_sdk.api.APIDiscover;
import com.vdrop.vdrop_sports_sdk.application.App;
import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.callback.PopupCallBack;
import com.vdrop.vdrop_sports_sdk.callback.ProgressCallback;
import com.vdrop.vdrop_sports_sdk.model.Campaign;
import com.vdrop.vdrop_sports_sdk.model.Playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dennis on 29/5/17.
 */

public class VDSActivityManager {


    private Context context;
    private APICompaign compaignAPI = new APICompaign();
    private APIDiscover discoverAPI = new APIDiscover();
    private ArrayList<Playlist> playList = new ArrayList<>();
    private TextView vdsTVUploading;
    private ProgressBar vdsProgressBar;
    private TextView vdsTVClose;
    private ImageView vdsIVDone;
    private Dialog d;
    VDSCampaignActivity vdsCampaignActivity = new VDSCampaignActivity();
    String publishedVideoId = App.getInstance().getPublishVideoId();
    public VDSActivityManager(Context context) {
        this.context = context;
    }


    /**
     * @param
     * @param campaignId
     */
    public void getCampaignPlaylist(String campaignId, final ActivityCallback callback) {
        compaignAPI.getCampaignlist(campaignId, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                callback.onSuccess(success, map);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);

            }
        });
    }

    /**
     * @param campaignId
     * @param callback
     */
    public void getDiscoverThumbnaillist(String campaignId, final ActivityCallback callback) {
        discoverAPI.getDiscoverlist(campaignId, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                callback.onSuccess(success, map);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);

            }
        });
    }

    public void onCreateVideo(String campaignId,final File file,
                              final ActivityCallback callback,
                              final ProgressCallback progressCallback) {
        Log.i("CAMPAIGN_ID_AM",""+campaignId);
        compaignAPI.createVideoForCampaign(campaignId, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                callback.onSuccess(success,map);
              String uploadUrl = ((Playlist)map.get(Constants.RESPONSE)).getUploadUrl();
                String videoId = ((Playlist)map.get(Constants.RESPONSE)).get_id();
                Log.i("CAMPAIGN_UPLOAD_URL",""+uploadUrl);
                Log.i("CAMPAIGN_VIDEO_ID",""+videoId);
                App.getInstance().setPublishVideoId(videoId);
                onUploadVideo(uploadUrl,videoId,file,callback,progressCallback);

            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
    /*
        upload Video
     */

    public void onUploadVideo(final String uploadUrl, final String videoId, final File file,
                              final ActivityCallback callback,
                              final ProgressCallback progressCallback) {
        final String s = "uploading...";
        uploadDialog(s);
        vdsTVUploading.setText(s);

        compaignAPI.uploadVideo(uploadUrl, videoId, file, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                onPublishVideo(videoId, callback);
                Log.i("UPLOAD_VIDEO_RESPONCE",""+success);
                callback.onSuccess(success, map);



            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, new ProgressCallback() {
            @Override
            public void videoProgress(String campaignId, int progress) {
                progressCallback.videoProgress(videoId,progress);
                Log.i("PROGRESS_STATUS",""+progress);
                vdsProgressBar.setProgress(progress);
            }
        });
    }


    /*
        publish Video
     */
    public void onPublishVideo(String createVideoId,final ActivityCallback callback){
        Log.i("PUBLISHED_VIDEO_ID",""+createVideoId);
        compaignAPI.publishVideo(createVideoId, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                callback.onSuccess(success,map);
                Log.i("PUBLISH_VIDEO_RESPONCE",""+success);
                if (success.equals("SUCCESS")){
                    vdsTVUploading.setText("upload complete!");
                    vdsProgressBar.setVisibility(View.GONE);
                    vdsTVClose.setVisibility(View.VISIBLE);
                    vdsIVDone.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(String error) {
                    callback.onError(error);
            }
        });
    }

     private void uploadDialog(String text){
        d = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.vds_layout_upload_dialog);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
         int width = ViewGroup.LayoutParams.MATCH_PARENT;
         d.getWindow().setLayout(width,height);
         d.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat
                 .getColor(context, R.color.colorBlur)));
        vdsTVUploading = (TextView)d.findViewById(R.id.vds_tv_upload);
        vdsTVClose = (TextView)d.findViewById(R.id.vds_tv_upload_close);
        vdsProgressBar =(ProgressBar)d.findViewById(R.id.vds_progress);
         vdsIVDone = (ImageView)d.findViewById(R.id.vds_iv_upload_completed);
         vdsProgressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
         vdsProgressBar.setProgress(0);
             d.show();


         vdsTVClose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 d.dismiss();
             }
         });


    }

}
