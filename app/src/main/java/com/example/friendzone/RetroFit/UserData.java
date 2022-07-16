package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

@SerializedName("user_id")
@Expose
public String userId;
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
        return "UserData{" +
                "userId='" + userId + '\'' +
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