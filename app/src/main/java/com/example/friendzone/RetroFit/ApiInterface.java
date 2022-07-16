package com.example.friendzone.RetroFit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("userSignup")
    Call<SignupModel> signup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile_no") String mobile,
            @Field("password") String password


    );


    @POST("allStoryOuterView")
    Call<GetAllStory> getAllStory(


    );

    @FormUrlEncoded
    @POST("UserLogin")
    Call<LoginModel> login(

            @Field("mobile_no") String mobile,
            @Field("password") String password


    );

    @Multipart
    @POST("post_user")
    Call<UploadPostModel> uploadPost(

            @Part("user_id") RequestBody user_id,
            @Part("media_text") RequestBody media_text,
            @Part("post_text") RequestBody post_text,
            @Part MultipartBody.Part file_attachment


    );

    @Multipart
    @POST("storyUpload")
    Call<UploadStoryModel> UploadStory(

            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part file_attachment


    );

    @GET("post_list")
    Call<GetAllPostModel> getAllPost();


    @FormUrlEncoded
    @POST("allStory")
    Call<GetUserStoryModel> getUserStory(
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("post_like")
    Call<GetLike> doLike(
            @Field("post_id") String postId,
            @Field("like_boolean") int number,
            @Field("user_id") String uderId


    );

    @FormUrlEncoded
    @POST("post_comment")
    Call<GetLike> doComment(
            @Field("post_id") String postId,
            @Field("comment_message") String comment_message,
            @Field("user_id") String uderId


    );

    @FormUrlEncoded
    @POST("post_comments")
    Call<GetComment> getComment(
            @Field("post_id") String postId

    );
}

