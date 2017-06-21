package com.vdrop.vdrop_sports_sdk.api;

import android.util.Log;

import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.model.APIError;
import com.vdrop.vdrop_sports_sdk.model.Discover;
import com.vdrop.vdrop_sports_sdk.utils.Constants;
import com.vdrop.vdrop_sports_sdk.utils.ErrorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dennis on 29/5/17.
 */

public class APIDiscover {
    private Call<List<Discover>> playlistCall;
    private APIError error;

    public void getDiscoverlist(String campaignId, final ActivityCallback callback){
        final String TAG = "GET_DISCOVER_LIST";
        Log.i("CampaignId",campaignId);
        playlistCall = VDSClient.getVdSService().getThumbnaliList(campaignId);
        playlistCall.enqueue(new Callback<List<Discover>>() {
            @Override
            public void onResponse(Call<List<Discover>> call, Response<List<Discover>> response) {
                Log.i(TAG + "Responce_code",""+response.code());
                if (response.isSuccessful()){
                    Log.i(TAG+ Constants.RESPONSE,Constants.EMPTY+response.body());
                    Log.d("RES",""+response.body());
                    Map map = new HashMap<>();
                    map.put(Constants.RESPONSE,response.body());
                    callback.onSuccess(Constants.SUCCESS,map);
                }else {
                    error = ErrorUtils.parseError(response);
                    Log.d(TAG+ "Error Message",""+error.getMessage());
                    callback.onError(TAG+error.getMessage());
                }
            }
            @Override
            public void onFailure(Call<List<Discover>> call, Throwable t) {
                Log.d(TAG+Constants.RESPONSE,Constants.EMPTY + t.getMessage());
                callback.onError(t.getMessage());
            }
        });

    }
}
