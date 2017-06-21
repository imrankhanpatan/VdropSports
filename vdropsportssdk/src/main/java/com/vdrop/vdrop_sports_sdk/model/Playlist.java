package com.vdrop.vdrop_sports_sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class model contains all the details of PlayList
 */

public class Playlist
{
    @SerializedName("uploadUrl")
    private String uploadUrl;
    @SerializedName("stream")
    private String stream;
    @SerializedName("campaignId")
    private String campaignId;
    @SerializedName("_id")
    private String _id;
    @SerializedName("likes")
    private String likes;
    @SerializedName("created")
    private String created;
    @SerializedName("fileName")
    private String fileName;
    @SerializedName("__v")
    private String __v;
    @SerializedName("thumbnailImageUrl")
    private String thumbnailImageUrl;

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUploadUrl ()
    {
        return uploadUrl;
    }

    public void setUploadUrl (String uploadUrl)
    {
        this.uploadUrl = uploadUrl;
    }

    public String getStream ()
    {
        return stream;
    }

    public void setStream (String stream)
    {
        this.stream = stream;
    }

    public String getCampaignId ()
    {
        return campaignId;
    }

    public void setCampaignId (String campaignId)
    {
        this.campaignId = campaignId;
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

    public String getFileName ()
    {
        return fileName;
    }

    public void setFileName (String fileName)
    {
        this.fileName = fileName;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getThumbnailImageUrl ()
    {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl (String thumbnailImageUrl)
    {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}

