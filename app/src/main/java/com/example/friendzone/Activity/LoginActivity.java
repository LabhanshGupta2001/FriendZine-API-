package com.example.friendzone.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.friendzone.R;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.LoginModel;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.example.friendzone.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.llSignup.setOnClickListener(this);
        binding.cvLogin.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //  Setting Status Bar Color

        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        if (sharedPreferences.contains("name")) {
            Log.e("name", "onFailure: " + sharedPreferences.getString("name", ""));
            if (sharedPreferences.contains("email")) {
                Log.e("email", "onFailure: " + sharedPreferences.getString("email", ""));
                if (sharedPreferences.contains("mobile")) {
                    Log.e("mobile", "onFailure: " + sharedPreferences.getString("mobile", ""));

                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();


                }
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view == binding.llSignup) {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == binding.cvLogin) {

            if (binding.etMobile.getText().toString().isEmpty()) {
                binding.etMobile.requestFocus();
                binding.etMobile.setError("required");
            } else if (binding.etPassword.getText().toString().isEmpty()) {
                binding.etPassword.requestFocus();
                binding.etPassword.setError("required");
            } else {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(binding.etMobile.getText().toString())) {
                            snapshot = snapshot.child(binding.etMobile.getText().toString());
                            if (binding.etPassword.getText().toString().equals(snapshot.child("password").getValue(String.class))) {
                                Toast.makeText(LoginActivity.this, "" + snapshot.getKey(), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", (snapshot.child("email").getValue(String.class)));
                                editor.putString("email", (snapshot.child("mobile").getValue(String.class)));
                                editor.putString("mobile", (snapshot.child("name").getValue(String.class)));
                                editor.putString("userid", snapshot.getKey());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid id password", Toast.LENGTH_SHORT).show();


                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
    }

    void login() {
        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.login(
                        binding.etMobile.getText().toString(), binding.etPassword.getText().toString()).
                enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {


                        if (response.isSuccessful()) {
                            if (response.body().message.equals("Success")) {
                                SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", response.body().userData.name);
                                editor.putString("email", response.body().userData.email);
                                editor.putString("mobile", response.body().userData.mobileNo);
                                editor.putString("userid", response.body().userData.userId);
                                Log.e("TAG", "onResponse: " + response.body().userData.userId);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid Details ", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Details ", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Invalid Details ", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
