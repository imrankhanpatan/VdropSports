package com.vdrop.vdrop_sports_sdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This class model contains all the details of Campaign.
 */

public class Campaign
{
    @SerializedName("_id")
    private String _id;
    @SerializedName("created")
    private String created;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("__v")
    private String __v;
    @SerializedName("instruction")
    private String instruction;
    @SerializedName("playlist")
    private List<Playlist> playlist= new ArrayList<Playlist>();
    @SerializedName("smallPrint")
    private String smallPrint;

    public List<Playlist> getPlaylist() {
       return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getInstruction ()
    {
        return instruction;
    }

    public void setInstruction (String instruction)
    {
        this.instruction = instruction;
    }


    public String getSmallPrint ()
    {
        return smallPrint;
    }

    public void setSmallPrint (String smallPrint)
    {
        this.smallPrint = smallPrint;
    }


}

