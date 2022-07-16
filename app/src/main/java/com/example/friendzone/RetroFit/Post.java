package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("post_id")
    @Expose
    public String postId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("post_media_path")
    @Expose
    public String postMediaPath;
    @SerializedName("post_text")
    @Expose
    public String postText;
    @SerializedName("media_text")
    @Expose
    public String mediaText;
    @SerializedName("created_time")
    @Expose
    public String createdTime;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("profile_pic")
    @Expose
    public String profilePic;
    @SerializedName("addresss")
    @Expose
    public String addresss;
    @SerializedName("isRegisterSuccessfully")
    @Expose
    public String isRegisterSuccessfully;
    @SerializedName("email")
    @Expose
    public String email;

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", postMediaPath='" + postMediaPath + '\'' +
                ", postText='" + postText + '\'' +
                ", mediaText='" + mediaText + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", addresss='" + addresss + '\'' +
                ", isRegisterSuccessfully='" + isRegisterSuccessfully + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}