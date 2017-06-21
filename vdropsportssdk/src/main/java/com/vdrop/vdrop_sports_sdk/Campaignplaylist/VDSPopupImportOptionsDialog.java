package com.vdrop.vdrop_sports_sdk.Campaignplaylist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.application.App;
import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.callback.PopupCallBack;
import com.vdrop.vdrop_sports_sdk.callback.ProgressCallback;
import com.vdrop.vdrop_sports_sdk.model.Campaign;
import com.vdrop.vdrop_sports_sdk.utils.Constants;
import com.vdrop.vdrop_sports_sdk.utils.FileUtils;
import com.vdrop.vdrop_sports_sdk.utils.Utils;
import com.vdrop.vdrop_sports_sdk.utils.VDSActivityManager;
import com.vdrop.vdrop_sports_sdk.utils.VDSPermissionUtils;

import java.io.File;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dennis on 26/5/17.
 */

public class VDSPopupImportOptionsDialog extends DialogFragment implements View.OnClickListener {
    private VDSCampaignActivity activity;
    private String videoFormat = "video/*";
    private boolean isCompressing = false;
    private static final int RESULT_LOAD_VIDEO = 200;
    private Uri filePath = Uri.EMPTY;
    private VDSActivityManager VDSActivityManager;
    PopupCallBack popupCallBack;
    private String campaignID = "590ad782420e002f85701991";

    public VDSPopupImportOptionsDialog() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.VDS_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int screenHeight = Utils.deviceDimensions(getActivity(), 100, Constants.WIDTH);
            int halfScreenHeight = screenHeight;
            d.getWindow().setLayout(width, halfScreenHeight);
            d.setCanceledOnTouchOutside(true);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat
                    .getColor(getActivity(), R.color.colorTransparent1)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vds_import_video_dialog, container);
        TextView vdsTVRecord = (TextView) view.findViewById(R.id.vds_tv_record);
        TextView vdsTVImport = (TextView) view.findViewById(R.id.vds_tv_import_photos);
        TextView vdsTVCancel = (TextView) view.findViewById(R.id.vds_tv_cancel);
        vdsTVRecord.setOnClickListener(this);
        vdsTVImport.setOnClickListener(this);
        vdsTVCancel.setOnClickListener(this);
        activity = (VDSCampaignActivity) getActivity();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;
                }
                return false;

            }
        });
        VDSActivityManager = new VDSActivityManager(getActivity());
        popupCallBack = (PopupCallBack)getActivity();
        if (!VDSPermissionUtils.checkPermissionStatus(getActivity())) {
            VDSPermissionUtils.EnableRuntimePermission(getActivity());
        }

        return view;
    }

    @Override
    public void onClick(View v) {

        int Id = v.getId();
        if (Id == R.id.vds_tv_record) {
            Toast.makeText(activity, "Work in Progress", Toast.LENGTH_SHORT).show();
        }
        if (Id == R.id.vds_tv_import_photos) {
            if (VDSPermissionUtils.checkPermissionStatus(getActivity())){
                pickVideoFromGallery();
            }else {
                Toast.makeText(activity, "Permission not enabled", Toast.LENGTH_LONG).show();
            }

        }
        if (Id == R.id.vds_tv_cancel) {
            dismiss();
        }

    }

    public void pickVideoFromGallery() {
        Intent videoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        videoIntent.setType(videoFormat);
        startActivityForResult(videoIntent, RESULT_LOAD_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_VIDEO) {
                try {
                    filePath = data.getData();
                    final File file = FileUtils.getFile(getActivity(),filePath);
                    //String campaignId = App.getInstance().getCampaignId();
                    getDialog().dismiss();
                    createCampaignUser(file, campaignID);
                    Log.i("FILE_PATH", "" + filePath);
                    Log.i("FILE_PATH", "" + campaignID);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createCampaignUser(File filePath, String campaignId) {
        Log.i("CAMPAIGN_ID", "" + campaignId);
        VDSActivityManager.onCreateVideo(campaignId, filePath, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {

            }

            @Override
            public void onError(String error) {

            }
        }, new ProgressCallback() {
            @Override
            public void videoProgress(String campaignId, int progress) {

            }
        });

    }


}
