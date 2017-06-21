package com.vdrop.vdrop_sports_sdk.Campaignplaylist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.application.App;
import com.vdrop.vdrop_sports_sdk.callback.PopupCallBack;
import com.vdrop.vdrop_sports_sdk.utils.Constants;
import com.vdrop.vdrop_sports_sdk.utils.Utils;
import com.vdrop.vdrop_sports_sdk.utils.VDSActivityManager;

/**
 * Created by dennis on 26/5/17.
 */

public class VDSPopupVideoSignupDialog extends DialogFragment implements View.OnClickListener {

    VDSCampaignActivity activity;
    private TextView vdsTVSubtitle;
    private TextView vdsTVName;
    private TextView vdsTVMembership;
    private ImageView vdsIVVideoadd;
    private Button vdsBtnSignup;
    private TextView vdsBtnContent;
    private TextView vdsTVBinfo;
     FragmentManager fragmentManager;
    private VDSActivityManager VDSActivityManager;
    private VDSPopupImportOptionsDialog vdsPIODialog;
    PopupCallBack popupCallBack;
    Dialog d;

   public VDSPopupVideoSignupDialog(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.VDS_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
        if (d != null) {
            d.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat
                    .getColor(getActivity(), R.color.colorTransparent1)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vds_activity_popup_signup_dialog,container);
        vdsBtnSignup = (Button)view.findViewById(R.id.vds_btn_signup);
        LinearLayout vdsLLContainer = (LinearLayout)view.findViewById(R.id.vds_signup_ll_container);
        vdsTVSubtitle  = (TextView)view.findViewById(R.id.vds_tv_signup_subtitle);
        vdsTVName   = (TextView)view.findViewById(R.id.vds_tv_signup_name);
        TextView vdsTVUploadTitle = (TextView)view.findViewById(R.id.vds_tv_upload_title);
        vdsTVMembership =(TextView)view.findViewById(R.id.vds_tv_signup_membership);
        vdsBtnContent  = (TextView)view.findViewById(R.id.vds_tv_signup_content);
        vdsTVBinfo =(TextView)view.findViewById(R.id.vds_tv_signgup_binfo);
        vdsIVVideoadd  = (ImageView) view.findViewById(R.id.vds_iv_video_add);
        vdsBtnSignup.setOnClickListener(this);
        vdsIVVideoadd.setOnClickListener(this);
        vdsTVSubtitle.setText(App.getInstance().getCampaign().getDescription());
        vdsTVUploadTitle.setText(App.getInstance().getCampaignName());
        fragmentManager = getActivity().getFragmentManager();
        activity = (VDSCampaignActivity)getActivity();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        popupCallBack = (PopupCallBack)getActivity();

        VDSActivityManager = new VDSActivityManager(getActivity());
        vdsLLContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                popupCallBack.onDismiss();
            }
        });
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getDialog().dismiss();
                    popupCallBack.onDismiss();
                }
                return true;

            }
        });

        return view;
    }
    private boolean isDialog = false;


    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.vds_btn_signup){

            vdsTVSubtitle.setVisibility(View.VISIBLE);
            vdsBtnSignup.setVisibility(View.GONE);
            vdsBtnContent.setVisibility(View.GONE);
            vdsTVName.setVisibility(View.GONE);
            vdsTVMembership.setVisibility(View.GONE);
            vdsIVVideoadd.setVisibility(View.VISIBLE);
            vdsTVBinfo.setVisibility(View.VISIBLE);

        }
        if (Id == R.id.vds_iv_video_add){

            vdsPIODialog = new VDSPopupImportOptionsDialog();
            vdsPIODialog.show(fragmentManager,"PIODIALOG");
        }

    }


}
