package com.vdrop.vdrop_sports_sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dennis on 29/5/17.
 */

public class Utils {

    public static String getProperty(String url, Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("vds.properties");
        properties.load(inputStream);
        return properties.getProperty(url);
    }

    public static int deviceDimensions(Context context, int percent, String dimension) {
        String TAG = "deviceDimensions";
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double onePercentWidth;
        double requiredWidth;
        Log.i(TAG, "device_width " + width);
        Log.i(TAG, "device_height" +height);
        Log.i(TAG, "required_height" +(height/100)*percent);

        if (dimension.equalsIgnoreCase("width")) {
            onePercentWidth = width / 100.00;
            requiredWidth = onePercentWidth * (double) percent;
            Log.i(TAG, "requiredWidth double" + requiredWidth
                    + ", requiredWidth int" + (int) Math.ceil(requiredWidth));
            return (int) Math.ceil(requiredWidth);
        } else if (dimension.equalsIgnoreCase("height")) {
            return (height / 100) * percent;
        } else {
            return -1;
        }
    }

    public static String getUrlWithDimension(String url, int width, int height) {
        try {
            String avatarUrl = "";
            for (String part : url.split("/")) {
                if (part.equals("$dimension$")) {
                    part = width + "x" + height;
                }
                avatarUrl += part + "/";
            }
            Log.i("avatar_image", "getUrlWithDimension " + avatarUrl);
            return avatarUrl.substring(0, avatarUrl.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
