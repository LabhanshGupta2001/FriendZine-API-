package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadStoryModel {

@SerializedName("status")
@Expose
public Integer status;
@SerializedName("data")
@Expose
public List<Object> data = null;
@SerializedName("msg")
@Expose
public String msg;
@SerializedName("base_url")
@Expose
public String baseUrl;
@SerializedName("count")
@Expose
public String count;

    @Override
    public String toString() {
        return "UploadStoryModel{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}