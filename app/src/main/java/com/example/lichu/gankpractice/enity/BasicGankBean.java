package com.example.lichu.gankpractice.enity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lichu on 2016/8/28.
 *
 * basic data for every item from gank.io
 *
 */
public class BasicGankBean {
    // publisher's name
    @SerializedName("who") public String who;

    // published time
    @SerializedName("publishedAt") public Date publishedAt;

    //title
    @SerializedName("desc") public String desc;

    //type
    @SerializedName("type") public String type;

    //url for bean's content
    @SerializedName("url") public String url;

    //available or not
    @SerializedName("used") public Boolean used;

    //id of object
    @SerializedName("objectId") public String objectId;

    //create time
    @SerializedName("createdAt") public Date createdAt;

    //update time
    @SerializedName("updatedAt") public Date updatedAt;
}
