package com.vdrop.vdrop_sports_sdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by dennis on 5/6/17.
 */

public class VDSPermissionUtils extends Activity {

    private static final int RequestPermissionCode = 1;

    public static void EnableRuntimePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int RequestCode, String permissions[]
            , int[] PermisionResult) {
        switch (RequestCode) {
            case RequestPermissionCode:
                if (PermisionResult.length > 0 && PermisionResult[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted," +
                            " Now your application can access Storage.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Canceled, " +
                            "Now your application cannot access Storage.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public static boolean checkPermissionStatus(Activity activity) {
        int RequestCheckResult = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return RequestCheckResult == PackageManager.PERMISSION_GRANTED;

    }
}
