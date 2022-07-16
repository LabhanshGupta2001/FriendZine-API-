package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLike {

@SerializedName("status")
@Expose
public Integer status;
@SerializedName("data")
@Expose
public List<Object> data = null;
@SerializedName("msg")
@Expose
public String msg;

}