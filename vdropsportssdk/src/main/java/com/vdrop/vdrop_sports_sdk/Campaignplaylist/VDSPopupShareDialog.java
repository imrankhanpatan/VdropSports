package com.vdrop.vdrop_sports_sdk.Campaignplaylist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.utils.Constants;
import com.vdrop.vdrop_sports_sdk.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by dennis on 21/6/17.
 */

public class VDSPopupShareDialog extends DialogFragment implements View.OnClickListener {

    private VDSCampaignActivity activity;
    private BranchUniversalObject twitterUniversalObject,facebookUniversalObject;
    private LinkProperties twitterLinkProperties,facebookLinkProperties;
    ShareDialog shareDialog;

    public VDSPopupShareDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.VDS_DIALOG);
        FacebookSdk.sdkInitialize(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                Log.i("ERROR", "" + error);

                try {
                    JSONObject jsonLink = referringParams.getJSONObject("link");
                    Log.i("JSONARRAY", "" + jsonLink);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },getActivity().getIntent().getData(),getActivity());
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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vds_share_dialog,container);
        TextView tvFacebook = (TextView)view.findViewById(R.id.vds_tv_facebook);
        TextView tvTwitter = (TextView)view.findViewById(R.id.vds_tv_twitter);
        TextView tvShareCancel = (TextView)view.findViewById(R.id.vds_tv_share_cancel);
        tvFacebook.setOnClickListener(this);
        tvTwitter.setOnClickListener(this);
        tvShareCancel.setOnClickListener(this);
        activity = (VDSCampaignActivity)getActivity();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        shareDialog = new ShareDialog(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.vds_tv_facebook){
            facebookUniverselObject();
            facebookLinkProperties = new LinkProperties()
                    .setChannel("facebook")
                    .setFeature("sharing")
                    .addControlParameter("$desktop_url", "http://example.com/home")
                    .addControlParameter("$android_url", "http://example.com/android");
            facebookUniversalObject.generateShortUrl(getActivity(), facebookLinkProperties, new Branch.BranchLinkCreateListener() {
                @Override
                public void onLinkCreate(String url, BranchError error) {

                    if (error == null) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(url))
                                .build();
                        shareDialog.show(linkContent);
                        Log.i("URL", "" + url);

                    }
                }
            });


        }
        if (v.getId() == R.id.vds_tv_twitter){
            twitterUniverselObject();

            twitterLinkProperties = new LinkProperties()
                    .setChannel("twitter")
                    .setFeature("sharing")
                    .addControlParameter("$desktop_url", "http://example.com/home")
                    .addControlParameter("$android_url", "http://example.com/android");

            twitterUniversalObject.generateShortUrl(getActivity(), twitterLinkProperties, new Branch.BranchLinkCreateListener() {
                @Override
                public void onLinkCreate(String url, BranchError error) {
                    Log.i("TWITTER_LINK",""+url);
                    URL link = null;
                    try {
                        link = new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if (error == null) {
                        TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                                .url(link);
                        builder.show();
                    }
                }
            });
        }
        if (v.getId() == R.id.vds_tv_share_cancel){
            getDialog().dismiss();
        }
    }

    private void twitterUniverselObject(){
        twitterUniversalObject = new BranchUniversalObject()
                .setCanonicalIdentifier("item/12345")
                .setTitle("New Post")
                .setContentDescription("My Twitter Description")
                .setContentImageUrl("https://example.com/mycontent-12345.png")
                .addContentMetadata("sourceID", "vdropsports")
                .addContentMetadata("campaignID", "590ad8a4420e002f85701996");
        twitterUniversalObject.registerView();
        twitterUniversalObject.listOnGoogleSearch(getApplicationContext());
    }

    private void facebookUniverselObject(){
        facebookUniversalObject = new BranchUniversalObject()
                .setCanonicalIdentifier("item/12345")
                .setTitle("My Content Title")
                .setContentDescription("My Content Description")
                .setContentImageUrl("https://example.com/mycontent-12345.png")
                .addContentMetadata("sourceID", "vdropsports")
                .addContentMetadata("campaignID", "590ad8a4420e002f85701996");
        facebookUniversalObject.registerView();
        facebookUniversalObject.listOnGoogleSearch(getApplicationContext());
    }

}
