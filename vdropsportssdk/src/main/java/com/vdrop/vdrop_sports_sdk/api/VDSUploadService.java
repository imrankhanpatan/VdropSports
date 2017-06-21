package com.vdrop.vdrop_sports_sdk.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by dennis on 29/5/17.
 */

public interface VDSUploadService {

    @PUT
    Call<ResponseBody> uploadMYVideo(@Url String url,
                                     @Header("Content-Type") String contentType,
                                     @Body RequestBody video);
}
