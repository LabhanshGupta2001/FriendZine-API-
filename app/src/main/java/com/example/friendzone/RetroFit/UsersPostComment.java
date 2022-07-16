package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersPostComment {

@SerializedName("id")
@Expose
public String id;
@SerializedName("post_id")
@Expose
public String postId;
@SerializedName("user_id")
@Expose
public String userId;
@SerializedName("comment_message")
@Expose
public String commentMessage;
@SerializedName("created_date")
@Expose
public String createdDate;
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
        return "UsersPostComment{" +
                "id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", commentMessage='" + commentMessage + '\'' +
                ", createdDate='" + createdDate + '\'' +
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