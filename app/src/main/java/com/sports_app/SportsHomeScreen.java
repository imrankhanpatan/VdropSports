package com.sports_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vdrop.vdrop_sports_sdk.Campaignplaylist.VDSCampaignActivity;

import sportsapp.com.sportsapp.R;

public class SportsHomeScreen extends AppCompatActivity {


    private LinearLayout vdsHomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_home_screen);

       vdsHomeScreen = (LinearLayout) findViewById(R.id.home_btn_sportsscreen);

        vdsHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), VDSCampaignActivity.class);
//                intent.putExtra("CAMPAIGN_ID","590ad782420e002f85701991");
                startActivity(intent);

            }
        });
    }
}
