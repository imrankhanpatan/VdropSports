package com.vdrop.vdrop_sports_sdk.api;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class VDSClient {
    private static VDSService vdsService;
    private Context context;
    private static VDSUploadService vdsUploadService;
    public static Retrofit retrofit;
//    public static String BASE_URL = "http://vdrop-sports-test-1.fa3kqp2mxi.us-west-2.elasticbeanstalk.com/";
    public static String BASE_URL = "http://vdrop-sports-discover.us-west-2.elasticbeanstalk.com/";
    private VDSClient(Context context) {
        this.context = context;
    }

    public static VDSService getVdSService() {

        if (vdsService == null) {
            vdsService = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(new StringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(VDSService.class);
        }

        return vdsService;
    }

    public static VDSUploadService getVdsUploadService(){
        if (vdsUploadService == null){
            vdsUploadService = new Retrofit.Builder()
                                .baseUrl(BASE_URL).addConverterFactory(new StringConverterFactory())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                .create(VDSUploadService.class);
        }
        return vdsUploadService;
    }


    static class StringConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations
                , Retrofit retrofit) {

            if (TypeToken.get(type).getRawType().isAssignableFrom(String.class)) {
                return new Converter<ResponseBody, String>() {
                    @Override
                    public String convert(ResponseBody value) throws IOException {
                        return value.string();
                    }
                };
            }
            return null;
        }

    }

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
