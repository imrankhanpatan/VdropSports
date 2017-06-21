package com.vdrop.vdrop_sports_sdk.callback;

import java.util.Map;

/**
 * Created by dennis on 29/5/17.
 */

public interface ActivityCallback {
    public void onSuccess(String success, Map map);
    public void onError(String error);
}
