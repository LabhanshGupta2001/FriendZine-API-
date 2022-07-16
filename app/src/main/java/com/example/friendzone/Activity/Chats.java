package com.example.friendzone.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.friendzone.Adapter.ChatsAdapter;
import com.example.friendzone.Model.ChatsModel;
import com.example.friendzone.R;
import com.example.friendzone.databinding.ActivityChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats extends AppCompatActivity {
    ActivityChatsBinding activityChatsBinding;
    ArrayList<ChatsModel> chatsModels = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatsBinding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(activityChatsBinding.getRoot());
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        userid = sharedPreferences.getString("userid", "");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (!dataSnapshot.getKey().equals(userid)) {

                        ChatsModel model = new ChatsModel();
                        model.key = dataSnapshot.getKey();
                        model.name = dataSnapshot.child("name").getValue(String.class);
                        model.comment = dataSnapshot.child("email").getValue(String.class);
                        model.profile = R.drawable.dummy;
                        chatsModels.add(model);
                    }
                    activityChatsBinding.rvChats.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    activityChatsBinding.rvChats.setAdapter(new ChatsAdapter(getApplicationContext(), chatsModels));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}