package com.vdrop.vdrop_sports_sdk.Campaignplaylist;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.vdrop.vdrop_sports_sdk.R;
import com.vdrop.vdrop_sports_sdk.api.APICompaign;
import com.vdrop.vdrop_sports_sdk.api.VDSClient;
import com.vdrop.vdrop_sports_sdk.api.VDSService;
import com.vdrop.vdrop_sports_sdk.application.App;
import com.vdrop.vdrop_sports_sdk.callback.ActivityCallback;
import com.vdrop.vdrop_sports_sdk.callback.PopupCallBack;
import com.vdrop.vdrop_sports_sdk.discover.VDSDiscoverActivity;
import com.vdrop.vdrop_sports_sdk.model.Campaign;
import com.vdrop.vdrop_sports_sdk.model.Playlist;
import com.vdrop.vdrop_sports_sdk.utils.VDSActivityManager;
import com.vdrop.vdrop_sports_sdk.utils.Constants;

import java.util.ArrayList;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class VDSCampaignActivity extends AppCompatActivity implements View.OnClickListener,
        PopupCallBack, VDSGestureDetection.SimpleGestureListener {

    private Context context = this;
    private VDSService vdsService;
    private ArrayList<Playlist> leftVideoList;
    private ArrayList<Playlist> videoList;
    int i = 0;
    private VideoView vdsVideoView;
    private ImageView vdsIVclose;
    private ImageView vdsIVlike;
    private LinearLayout vdsLLContainer;
    private DisplayMetrics vdsScreenMetrics;
    private RelativeLayout vdsRLContainer;
    private int screenwidth;
    private int screenheight;
    private FragmentManager fragmentManager;
    private VDSPopupVideoSignupDialog vdsPVSDialog;
    private VDSActivityManager VDSActivityManager;
    private String campaignID = "590ad8a4420e002f85701996";
    private Campaign campaign;
    private int videoIndex = 0;
    private ArrayList<View> indicatorList;
    private APICompaign apiCampaign = new APICompaign();
    private Dialog dialog;
    VDSPopupOptionsDialog profileDialog;
    private int stopPosition;
    private LinearLayout vdsLLLikeContainer;
    private TextView vdsTVLikeCount;
    int counter = 78;
    VDSDiscoverActivity vdsDiscoverActivity;
    private boolean isLike = false;
    VDSGestureDetection detector;
    private ProgressBar vdsProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vds_activity_compaign);
        vdsVideoView = (VideoView) findViewById(R.id.vds_vd_page);
        vdsIVclose = (ImageView) findViewById(R.id.vds_iv_close_video);
        vdsIVlike = (ImageView) findViewById(R.id.vds_iv_like_video);
        vdsLLContainer = (LinearLayout) findViewById(R.id.vds_ll_container);
        vdsRLContainer = (RelativeLayout) findViewById(R.id.vds_rl_video);
        vdsTVLikeCount = (TextView) findViewById(R.id.vds_tv_like_count);
        vdsLLLikeContainer = (LinearLayout) findViewById(R.id.vds_background_like_border);
        vdsProgressBar = (ProgressBar) findViewById(R.id.vds_video_progressbar);
        vdsScreenMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(vdsScreenMetrics);
        screenwidth = vdsScreenMetrics.widthPixels;
        screenheight = vdsScreenMetrics.heightPixels;
        vdsIVclose.setOnClickListener(this);
        vdsIVlike.setOnClickListener(this);
        vdsRLContainer.setOnClickListener(this);
        vdsDiscoverActivity = new VDSDiscoverActivity();
        detector = new VDSGestureDetection(this, this);
        vdsLLLikeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLike) {
                    isLike = false;
                    vdsIVlike.setImageResource(R.drawable.vdrop_action_like);
                    vdsTVLikeCount.setText(String.valueOf(counter));
                    counter++;
                } else {
                    isLike = true;
                    vdsIVlike.setImageResource(R.drawable.vds_on_like);
                    vdsTVLikeCount.setText(String.valueOf(counter));
                    counter--;
                }
            }
        });
        fragmentManager = getFragmentManager();
        VDSActivityManager = new VDSActivityManager(this);
        indicatorList = new ArrayList<>();
        Log.i("Screen Width", "" + screenwidth);
        videoList = new ArrayList<>();
        leftVideoList = new ArrayList<>();
        vdsService = VDSClient.getRetrofit().create(VDSService.class);
        getCampaign(campaignID);
        dialog = new Dialog(this);
        vdsRLContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vdsVideoView.isPlaying()) {
                    Log.d("TOUCH", "OPEN");
                    if (!isDialogOpen) {
                        stopPosition = vdsVideoView.getCurrentPosition();
                        Log.d("VIDEO_CURRENT_POSITION", "" + stopPosition);
                        vdsVideoView.pause();
                        vdsDiscoverActivity.setPosition(stopPosition, vdsVideoView);

                        profileDialog = new VDSPopupOptionsDialog();
                        profileDialog.setVideo(stopPosition, vdsVideoView);
                        profileDialog.show(fragmentManager, "frag");
                    }
                } else {
                    Log.d("TOUCH", "CLOSE");
                    profileDialog.dismiss();
                    Log.d("VIDEO_CLOSE_POSITION", "" + stopPosition);
                    vdsVideoView.seekTo(stopPosition);
                    vdsVideoView.start();
                }
            }
        });

        vdsIVlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    isLike = false;
                    vdsIVlike.setImageResource(R.drawable.vdrop_action_like);
                    vdsTVLikeCount.setText(String.valueOf(counter));
                    counter++;
                } else {
                    isLike = true;
                    vdsIVlike.setImageResource(R.drawable.vds_on_like);
                    vdsTVLikeCount.setText(String.valueOf(counter));
                    counter--;
                }
            }
        });
    }


    private void getCampaign(String campaignID) {
        VDSActivityManager.getCampaignPlaylist(campaignID, new ActivityCallback() {
            @Override
            public void onSuccess(String success, Map map) {
                campaign = (Campaign) map.get(Constants.RESPONSE);
                String name = campaign.getName().replaceAll(" ", "");
                App.getInstance().setCampaign(campaign);
                App.getInstance().setCampaignName(name);
                try {
                    int size = campaign.getPlaylist().size();
                    Log.i("CAMPAIGN_SIZE", "" + size);
                    Log.i("VIDEOLIST_SIZE", "" + videoList.size());
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            if (videoList.size() < 5) {
                                videoList.add(campaign.getPlaylist().get(i));
                            }
                        }
                    }
                    if (videoList.size() != 0) {
                        for (int i = 0; i < videoList.size(); i++) {
                            addIndicator();
                        }
                        playVideo(videoList.get(0).getStream());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }

        });

        vdsVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                int duration = mp.getDuration() / 1000;
                int hours = duration / 3600;
                int minutes = (duration / 60) - (hours * 60);
                int seconds = duration - (hours * 3600) - (minutes * 60);
                String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                Log.i("DURATION", "" + formatted);

                int size = videoList.size();
                vdsLLContainer.getChildAt(videoIndex).setBackgroundColor(getResources()
                        .getColor(R.color.colorLine));
                i++;
                if (i < size) {
                    vdsVideoView.setVideoURI(Uri.parse(videoList.get(i).getStream()));
                    vdsLLContainer.getChildAt(i * 2).setBackgroundColor(Color.WHITE);
                    videoIndex = i * 2;
                    vdsVideoView.start();
                }
            }
        });
    }

    public void addIndicator() {
        LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(
                (screenwidth / videoList.size()), ViewGroup.LayoutParams.MATCH_PARENT);
        View indicator = new View(VDSCampaignActivity.this);
        indicator.setBackgroundColor(getResources().getColor(R.color.colorTransparented));
        vdsLLContainer.addView(indicator, indicatorParams);
        LinearLayout.LayoutParams splitterParams = new LinearLayout.LayoutParams(
                5, ViewGroup.LayoutParams.MATCH_PARENT);
        View splitter = new View(VDSCampaignActivity.this);
        splitter.setBackgroundColor(Color.TRANSPARENT);
        vdsLLContainer.addView(splitter, splitterParams);
        indicatorList.add(indicator);
    }

    public void playVideo(String url) {
        Uri uri = Uri.parse(url);
        vdsVideoView.setVideoURI(uri);
        vdsVideoView.start();

        Log.d("CHILD_COO", "" + vdsLLContainer.getChildCount());
        if (indicatorList.size() != 0) {
            vdsLLContainer.getChildAt(videoIndex).setBackgroundColor(Color.WHITE);
        }
    }

    boolean isDialogOpen = false;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.vds_iv_close_video) {
            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("STATUS", "PAUSE");
        if (!isDialogOpen) {
            stopPosition = vdsVideoView.getCurrentPosition();
            vdsVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("STATUS", "RESUME");
        if (isDialogOpen) {
            vdsVideoView.seekTo(stopPosition);
            onStart();
        } else {
            vdsVideoView.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isDialogOpen) {
            vdsVideoView.pause();
        } else {
            vdsVideoView.seekTo(stopPosition);
            vdsVideoView.start();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onDismiss() {
        if (!isDialogOpen) {
            vdsVideoView.seekTo(stopPosition);
            onStart();
        }
    }

    @Override
    public void onVideoPause() {
        stopPosition = vdsVideoView.getCurrentPosition();
        vdsVideoView.pause();
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";
        Log.i("DIRECTION", "" + direction);
        switch (direction) {
            case VDSGestureDetection.SWIPE_RIGHT:
                if (i < videoList.size() - 1) {
                    i++;
                    showProgress();
                    playSelectedVideo(i);
                }
                break;
            case VDSGestureDetection.SWIPE_LEFT:
                if (i > 0) {
                    i--;
                    showProgress();
                    playSelectedVideo(i);
                }
                break;
        }
    }

    public void playSelectedVideo(int position) {
        vdsLLContainer.getChildAt(videoIndex).setBackgroundColor(getResources()
                .getColor(R.color.colorLine));
        vdsVideoView.setVideoURI(Uri.parse(videoList.get(position).getStream()));
        vdsLLContainer.getChildAt(i * 2).setBackgroundColor(Color.WHITE);
        videoIndex = position * 2;
        vdsVideoView.start();
        hideProgress();
        Log.i("VALUE_RIGHT", "" + position);
    }

    private void showProgress() {
        vdsProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        vdsProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}