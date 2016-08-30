package com.example.lichu.gankpractice.enity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lichu on 2016/8/28.
 * Every json contains a error data
 */
public class Error {

    @SerializedName("error") public Boolean error;

    @SerializedName("msg") public String msg;
}
