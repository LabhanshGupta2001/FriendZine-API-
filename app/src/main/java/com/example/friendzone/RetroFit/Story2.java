package com.example.friendzone.RetroFit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Story2 {

    @SerializedName("story_id")
    @Expose
    public String storyId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("story_media_path")
    @Expose
    public String storyMediaPath;
    @SerializedName("created_time")
    @Expose
    public String createdTime;
    @SerializedName("expire_time")
    @Expose
    public String expireTime;
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
        return "Story2{" +
                "storyId='" + storyId + '\'' +
                ", userId='" + userId + '\'' +
                ", storyMediaPath='" + storyMediaPath + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
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