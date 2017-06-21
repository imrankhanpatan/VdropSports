package com.vdrop.vdrop_sports_sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dennis on 29/5/17.
 */

public class APIError {

    @SerializedName("message")
    private String message;

    @SerializedName("name")
    private String name;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }
}
