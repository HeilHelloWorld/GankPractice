package com.example.lichu.gankpractice.enity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lichu on 2016/8/29.
 * every type data contains basic data bean
 */
public class GankBean extends Error implements Serializable {
    @SerializedName("results") public ArrayList<BasicGankBean> results;
}
