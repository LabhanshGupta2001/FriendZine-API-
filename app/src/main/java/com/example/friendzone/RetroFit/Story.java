package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Story {

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

    @Override
    public String toString() {
        return "Story{" +
                    "storyId='" + storyId + '\'' +
                ", userId='" + userId + '\'' +
                ", storyMediaPath='" + storyMediaPath + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                '}';
    }


}