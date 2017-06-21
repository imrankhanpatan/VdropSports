package com.vdrop.vdrop_sports_sdk.api;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.callback.ProgressCallback;
import com.vdrop.vdrop_sports_sdk.model.APIError;
import com.vdrop.vdrop_sports_sdk.model.Campaign;
import com.vdrop.vdrop_sports_sdk.model.Playlist;
import com.vdrop.vdrop_sports_sdk.utils.Constants;
import com.vdrop.vdrop_sports_sdk.utils.ErrorUtils;
import com.vdrop.vdrop_sports_sdk.utils.FileUtils;
import com.vdrop.vdrop_sports_sdk.utils.ProgressRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dennis on 29/5/17.
 */

public class APICompaign {

    private Call<Campaign> compaignCall;
    private APIError error;
    Campaign campaign;
    private Call<ResponseBody> uploadCall;
    private Call<Playlist> playlistCall;
    private Call<String> publishVideoCall;
    private String uploadUrl;
    private String createVideoId;
    private Context context;



    /**
     *
     * get campaign API integration
     *
     * @param campaignId
     * @param callback
     */

    public void getCampaignlist(String campaignId, final ActivityCallback callback) {
        final String TAG = "GET_CAMPAIGN_Playlist";

        compaignCall = VDSClient.getVdSService().getCampaignList(campaignId);

        compaignCall.enqueue(new Callback<Campaign>() {
            @Override
            public void onResponse(Call<Campaign> call, Response<Campaign> response) {
                Log.i(TAG + "Response_code", "" + response.code());
                if (response.isSuccessful()) {
                    Log.i(TAG + Constants.RESPONSE, Constants.EMPTY + response.body().get_id());
                    Map map = new HashMap();
                    map.put(Constants.RESPONSE, response.body());
                    callback.onSuccess(Constants.SUCCESS, map);
                } else {
                    error = ErrorUtils.parseError(response);
                    Log.d(TAG + "Error Message", "" + error.getMessage());
                    callback.onError(TAG + error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Campaign> call, Throwable t) {
                Log.d(TAG + Constants.RESPONSE, Constants.EMPTY + t.getMessage());
                callback.onError(t.getMessage());

            }
        });

    }


    public void createCampaign(Campaign campaign, final ActivityCallback activityCallback) {
        final String TAG = "CREATE_CAMPAIGN";

        compaignCall = VDSClient.getVdSService().createCampaign(campaign);
        compaignCall.enqueue(new Callback<Campaign>() {
            @Override
            public void onResponse(Call<Campaign> call, Response<Campaign> response) {
                Log.i("RESPONSE_CREATE",""+response.isSuccessful());
                if (response.isSuccessful()) {
                    Log.d(TAG + Constants.RESPONSE_CODE, "" + response.code());
                    Log.i("RESPONSE_BODY",""+response.body().get_id());
//                    createVideoForCampaign(response.body().get_id(),activityCallback);
                    Map map = new HashMap();
                    map.put(Constants.RESPONSE, response.body());
                    activityCallback.onSuccess(Constants.SUCCESS, map);
                } else {
                    error = ErrorUtils.parseError(response);
                    Log.d(TAG, "" + error.getMessage());
                    activityCallback.onError(TAG + error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Campaign> call, Throwable t) {
                Log.d(TAG + Constants.RESPONSE, "" + t.getMessage());
                activityCallback.onError(t.getMessage());

            }
        });

    }

   public void createVideoForCampaign(final String campaignId,final ActivityCallback activityCallback) {
       final String TAG = "CREATE_VIDEO_CAMPAIGN";
    playlistCall = VDSClient.getVdSService().createVideoForCampaign(campaignId);
       playlistCall.enqueue(new Callback<Playlist>() {
           @Override
           public void onResponse(Call<Playlist> call, Response<Playlist> response) {
               Log.i("UPLOAD_VIDEO",""+response.isSuccessful());
               Log.i("URL",""+response.body().getUploadUrl());
               uploadUrl = response.body().getUploadUrl();
               createVideoId = response.body().get_id();
               if(response.isSuccessful()) {
                   Map map = new HashMap();
                   map.put(Constants.RESPONSE, response.body());
                   activityCallback.onSuccess(Constants.SUCCESS, map);
                   Log.i("VIDEO_ID", "" + response.body().get_id());
                   Log.i("UPLOAD_URL", "" + uploadUrl + "\n" + "VIDEO_ID" + createVideoId);
               }else {
                   error = ErrorUtils.parseError(response);
                   Log.d(TAG,""+error.getMessage());
                   activityCallback.onError(TAG+error.getMessage());
               }
           }

           @Override
           public void onFailure(Call<Playlist> call, Throwable t) {
               activityCallback.onError(t.getMessage());

           }
       });
   }

    public void uploadVideo(final String uploadUrl, final String createVideoId, final File file,
                            final ActivityCallback activityCallback,
                            final ProgressCallback progressCallback){

        final ProgressRequest fileBody = new ProgressRequest(file,
                new ProgressRequest.UploadCallbacks(){
            @Override
            public void onProgressUpdate(int progress) {
                progressCallback.videoProgress(createVideoId,progress);
                Log.d("ACTIVITY_PROGRESS",""+progress);
            }

            @Override
            public void onError() {
                Log.d("ACTIVITY_ERROR","ERROR");
            }
        });
        final String TAG = "UPLOAD_VIDEO";
        uploadCall = VDSClient.getVdsUploadService().uploadMYVideo(uploadUrl,"video/mp4",fileBody);
        uploadCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("UPLOAD_VIDEO_RESPONSE",""+response.isSuccessful());
                Log.i("UPLOAD_VIDEO_CODE",""+response.code());
                Log.d("VIDEO_ID",""+createVideoId);
                if (response.isSuccessful()){
//                    publishVideo(createVideoId,activityCallback);
                    Map map = new HashMap();
                    map.put(Constants.RESPONSE,response.body());
                    activityCallback.onSuccess(Constants.SUCCESS,map);
                }else {
                    error = ErrorUtils.parseError(response);
                    Log.d(TAG,""+error.getMessage());
                    activityCallback.onError(TAG+error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                activityCallback.onError(t.getMessage());
            }
        });
    }

    public void publishVideo(String createVideoId,final ActivityCallback activityCallback){
        Log.i("PUBLISH_VIDEO_ID",""+createVideoId);
        final String TAG = "PUBLISH_VIDEO";
        publishVideoCall = VDSClient.getVdSService().publishVideo(createVideoId);
        publishVideoCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("PUBLISH_RESPONCE",""+response.isSuccessful());
                Log.i("PUBLISH_RESPONCE_CODE",""+response.code());
                if (response.isSuccessful()){
                    Map map = new HashMap();
                    map.put(Constants.RESPONSE,response.body());
                    activityCallback.onSuccess(Constants.SUCCESS,map);
                }else {
                    error =ErrorUtils.parseError(response);
                    Log.d(TAG,""+error.getMessage());
                    activityCallback.onError(TAG+error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                activityCallback.onError(t.getMessage());
            }
        });
    }
}
