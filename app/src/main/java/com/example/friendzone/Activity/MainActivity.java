package com.example.friendzone.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.friendzone.R;
import com.example.friendzone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);

        activityLoginBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        activityLoginBinding.cvLogin.setOnClickListener(this);
        activityLoginBinding.cvSignup.setOnClickListener(this);
        if (sharedPreferences.contains("name")) {
            Log.e("name", "onFailure: " + sharedPreferences.getString("name", ""));
            if (sharedPreferences.contains("email")) {
                Log.e("email", "onFailure: " + sharedPreferences.getString("email", ""));
                if (sharedPreferences.contains("mobile")) {
                    Log.e("mobile", "onFailure: " + sharedPreferences.getString("mobile", ""));

                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();


                }
            }

        }

        //  Setting Status Bar Color

        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


    }

    @Override
    public void onClick(View view) {
        if(view==activityLoginBinding.cvLogin){
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }  if(view==activityLoginBinding.cvSignup){
            Intent intent=new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();

        }

    }
}