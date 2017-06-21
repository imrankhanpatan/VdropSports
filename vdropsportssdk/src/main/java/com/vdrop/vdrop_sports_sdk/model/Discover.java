package com.vdrop.vdrop_sports_sdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis on 29/5/17.
 */

public class Discover {
    @SerializedName("name")
    private String name;
    @SerializedName("playlist")
    private List<Playlist> playlist= new ArrayList<Playlist>();


    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }
}


