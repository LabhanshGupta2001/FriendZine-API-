package com.example.friendzone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.friendzone.Model.userHelperClass;
import com.example.friendzone.R;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.example.friendzone.RetroFit.SignupModel;
import com.example.friendzone.databinding.ActivitySignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignupBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.llLogin.setOnClickListener(this);
        binding.cvSignup.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //  Setting Status Bar Color

        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.llLogin) {

            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        if (view == binding.cvSignup) {
            if (binding.etName.getText().toString().isEmpty()) {
                binding.etName.requestFocus();
                binding.etName.setError("required");
            } else if (binding.etemail.getText().toString().isEmpty()) {
                binding.etemail.requestFocus();
                binding.etemail.setError("required");
            } else if (binding.etMobile.getText().toString().isEmpty()) {
                binding.etMobile.requestFocus();
                binding.etMobile.setError("required");
            } else if (binding.etPassword.getText().toString().isEmpty()) {
                binding.etPassword.requestFocus();
                binding.etPassword.setError("required");
            } else {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(binding.etMobile.getText().toString())) {
                            Toast.makeText(SignupActivity.this, "Mobile Already exist", Toast.LENGTH_SHORT).show();
                        } else {
                            userHelperClass userHelperClass = new userHelperClass(binding.etName.getText().toString(), binding.etemail.getText().toString(), binding.etMobile.getText().toString(), binding.etPassword.getText().toString());
                            databaseReference.child(binding.etMobile.getText().toString()).setValue((userHelperClass));
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);

                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
    }

    void registerUser() {

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.signup(binding.etName.getText().toString(), binding.etemail.getText().toString(),
                        binding.etMobile.getText().toString(), binding.etPassword.getText().toString()).
                enqueue(new Callback<SignupModel>() {
                    @Override
                    public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                        Log.e("TAG", "onResponse: " + response.body());
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);

                        finish();

                    }

                    @Override
                    public void onFailure(Call<SignupModel> call, Throwable t) {
                        Log.e("Error", "onFailure: " + t.getMessage());
                    }
                });


    }

}