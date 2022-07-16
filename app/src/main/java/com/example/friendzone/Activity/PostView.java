package com.example.friendzone.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.friendzone.Adapter.CommentAdapter;
import com.example.friendzone.Model.CommentModel;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.GetComment;
import com.example.friendzone.RetroFit.GetLike;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.example.friendzone.databinding.ActivityPostViewBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostView extends AppCompatActivity implements View.OnClickListener {
    ActivityPostViewBinding binding;
    ArrayList<CommentModel> commentModelArrayList = new ArrayList<>();
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getComment();
        Intent intent = getIntent();
        String image = intent.getStringExtra("yash");
        String mediaText = intent.getStringExtra("media_Text");

        postId = intent.getStringExtra("postId");
        binding.cvSend.setOnClickListener(this);

        if (mediaText.equals("1")) {
            Picasso.get()
                    .load("http://192.168.1.112/BasicStructure/" + image)
                    .into(binding.Image);
            binding.Image.setVisibility(View.VISIBLE);

            //binding.profile.setImageResource(profile);
        }
        if (mediaText.equals("2")) {

            binding.vvVideo.setVideoPath("http://192.168.1.112/BasicStructure/" + image);
            binding.vvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.vvVideo.start();
                }
            });
            binding.vvVideo.setVisibility(View.VISIBLE);

        }
        Picasso.get()
                .load("http://192.168.1.112/BasicStructure/" + image)
                .into(binding.Image);
        binding.Image.setVisibility(View.VISIBLE);
        //  binding.Image.setImageResource(image);
        binding.cvPost.setOnClickListener(this);

        binding.cvPost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binding.cvPost.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            binding.Image.getLayoutParams().height = 200;


                            return true;
                        }
                        return false;
                    }
                });

                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == binding.cvPost) {
            binding.Image.getLayoutParams().height = 200;

        }
        if (view == binding.cvSend) {
            doComment();
        }

    }

    private void doComment() {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", Context.MODE_PRIVATE);

        String userId = sharedPreferences.getString("userid", "");


        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.e("TAG", "doComment: " + userId);
        Log.e("TAG", "doComment: " + binding.edittext.getText().toString());
        Log.e("TAG", "doComment: " + postId);


        apiInterface.doComment(postId, binding.edittext.getText().toString(), userId).
                enqueue(new Callback<GetLike>() {
                    @Override
                    public void onResponse(Call<GetLike> call, Response<GetLike> response) {
                        Toast.makeText(getApplicationContext(), " " + response.body().msg, Toast.LENGTH_SHORT).show();
                        binding.edittext.setText(" ");
                        getComment();

                    }

                    @Override
                    public void onFailure(Call<GetLike> call, Throwable t) {
                    }
                });


    }

    private void getComment() {
        Intent intent = getIntent();

        String postId = intent.getStringExtra("postId");


        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.e("TAG", "doComment: " + postId);

        apiInterface.getComment(postId).
                enqueue(new Callback<GetComment>() {
                    @Override
                    public void onResponse(Call<GetComment> call, Response<GetComment> response) {
                    if (response.isSuccessful()){
                        binding.rvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.rvComments.setAdapter(new CommentAdapter(getApplicationContext(), response.body().usersPostComment));

                    }
                    }

                    @Override
                    public void onFailure(Call<GetComment> call, Throwable t) {
                    }
                });


    }

}