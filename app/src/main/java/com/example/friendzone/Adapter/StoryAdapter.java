package com.example.friendzone.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendzone.Activity.StoryActivity;
import com.example.friendzone.R;
import com.example.friendzone.RetroFit.Story2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Story2> arrayList;
    SharedPreferences sharedPreferences;
    boolean find = false;


    public StoryAdapter(Context context, ArrayList<Story2> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Story2 story = arrayList.get(position);

       /* if (sharedPreferences.getString("userid", "").equals(story.userId)) {
            find = true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("yourStoryFound", "true");
            editor.apply();

        } else*/ {

            holder.name.setText(story.name);
            Picasso.get()
                    .load("http://192.168.1.112/BasicStructure/" + story.storyMediaPath)
                    .into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, StoryActivity.class);
                    intent.putExtra("userId", story.userId);


                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        if (find)
            return arrayList.size();
//        else return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedPreferences = context.getSharedPreferences("Detail", MODE_PRIVATE);
            name = itemView.findViewById(R.id.tvuser_name);
            image = itemView.findViewById(R.id.civ_profile);


        }
    }


}
