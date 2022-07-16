package com.example.friendzone.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendzone.Activity.PostView;
import com.example.friendzone.R;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.GetLike;
import com.example.friendzone.RetroFit.Post;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context context;
    ArrayList<Post> arrayList;
    int i = 0;


    public PostAdapter(Context context, ArrayList<Post> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post postModel = arrayList.get((arrayList.size() - 1) - position);

        holder.name.setText(postModel.name);
        holder.time.setText(postModel.createdTime);
        if (postModel.mediaText.equals("1")) {
            Picasso.get()
                    .load("http://192.168.1.112/BasicStructure/" + postModel.postMediaPath)
                    .into(holder.image);
            holder.image.setVisibility(View.VISIBLE);

        }
        if (postModel.mediaText.equals("2")) {

            holder.vvvideo.setVideoPath("http://192.168.1.112/BasicStructure/" + postModel.postMediaPath);
            holder.play.setVisibility(View.VISIBLE);
            holder.vvvideo.setVisibility(View.VISIBLE);

        }
        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                if (i == 0) {
                    holder.ll_like.setTextColor(context.getResources().getColor(R.color.blue));
                    holder.iv_thumpsup.setImageResource(R.drawable.ic_thumbs_up__2_);
                    i = 1;
                    doLike(i, postModel.postId);

                } else {
                    holder.ll_like.setTextColor(context.getResources().getColor(R.color.black));
                    holder.iv_thumpsup.setImageResource(R.drawable.ic_thumbs_up__1_);
                    i = 0;
                    doLike(i, postModel.postId);

                }
            }
        });


       /* if (postModel.mediaText.equals("3")) {
            holder.name.setText(postModel.name);
            holder.lllinearLayout.setVisibility(View.VISIBLE);
            holder.time.setText(postModel.createdTime);
            holder.tv_textView.setText(postModel.postText);
            holder.tv_textView.setVisibility(View.VISIBLE);


        }*/
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostView.class);
                // Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();

                intent.putExtra("postId", postModel.postId);
                intent.putExtra("media_Text", postModel.mediaText);
                intent.putExtra("yash", postModel.postMediaPath);
                context.startActivity(intent);
            }
        }); holder.vvvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostView.class);
                // Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();
                intent.putExtra("postId", postModel.postId);
                intent.putExtra("media_Text", postModel.mediaText);
                intent.putExtra("yash", postModel.postMediaPath);
                context.startActivity(intent);
            }
        });
        holder.btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostView.class);
                // Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();
                intent.putExtra("postId", postModel.postId);

                intent.putExtra("media_Text", postModel.mediaText);
                intent.putExtra("yash", postModel.postMediaPath);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, time, comment, ll_like, tv_textView;
        ImageView profile, image, iv_thumpsup, play;
        VideoView vvvideo;
        LinearLayout btn_like, btn_comment;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_textView = itemView.findViewById(R.id.tv_textView);
            name = itemView.findViewById(R.id.tv_name);
            time = itemView.findViewById(R.id.tv_time);
            profile = itemView.findViewById(R.id.cv_profile);
            image = itemView.findViewById(R.id.iv_image);
            ll_like = itemView.findViewById(R.id.ll_like);
            iv_thumpsup = itemView.findViewById(R.id.iv_thumpsup);
            btn_like = itemView.findViewById(R.id.btn_like);
            btn_comment = itemView.findViewById(R.id.btn_comment);
            vvvideo = itemView.findViewById(R.id.vv_video);
            play = itemView.findViewById(R.id.play);


        }
    }

    private void doLike(int like, String postId) {
        Log.e("Run", "getUserStory: " + "run");
        SharedPreferences sharedPreferences = context.getSharedPreferences("Detail", Context.MODE_PRIVATE);

        String userId = sharedPreferences.getString("userid", "");
        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.doLike(postId, like, userId).
                enqueue(new Callback<GetLike>() {
                    @Override
                    public void onResponse(Call<GetLike> call, Response<GetLike> response) {
                        Toast.makeText(context, " "+response.body().msg, Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<GetLike> call, Throwable t) {
                    }
                });


    }

}
