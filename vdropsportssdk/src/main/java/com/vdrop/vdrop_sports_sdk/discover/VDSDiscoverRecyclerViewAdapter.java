package com.vdrop.vdrop_sports_sdk.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.vdrop.vdrop_sports_sdk.model.Discover;

import java.util.ArrayList;

/**
 * Created by dennis on 30/5/17.
 */

public class VDSDiscoverRecyclerViewAdapter extends
        RecyclerView.Adapter<VDSDiscoverRecyclerViewAdapter.MyViewHolder>{

    ArrayList<Discover> discovers;
    Context context;

    public VDSDiscoverRecyclerViewAdapter(Context context) {
    this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(new VDSDiscoverGridRecycleViewItem(context));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item.setAdapter(discovers.get(position));
    }

    public void setData(ArrayList<Discover> discoverList){
        Log.d("DISCOVERLIST",""+discoverList);
        discovers=discoverList;
    }

    @Override
    public int getItemCount() {
        Log.d("DISCOVER_COUNT",""+discovers.size());
        return  discovers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        VDSDiscoverGridRecycleViewItem item;

        public MyViewHolder(View itemView) {
            super(itemView);
            item= (VDSDiscoverGridRecycleViewItem) itemView;
        }
    }
}


