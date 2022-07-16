package com.example.friendzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendzone.R;
import com.example.friendzone.RetroFit.UsersPostComment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    Context context;
    ArrayList<UsersPostComment> arrayList;
    int i = 0;


    public CommentAdapter(Context context, ArrayList<UsersPostComment> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsersPostComment postModel = arrayList.get(position);
        holder.name.setText(postModel.name);
        holder.profile.setImageResource(R.drawable.dummy);
        holder.comment.setText(postModel.commentMessage);
        if (holder.getAdapterPosition()==arrayList.size()-1){
            holder.itemView.setPadding(0,0,0,100);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, comment;
        ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            profile = itemView.findViewById(R.id.cv_profile);
            comment = itemView.findViewById(R.id.tv_comment);


        }
    }
}
