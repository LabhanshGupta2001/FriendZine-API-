package com.example.friendzone.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendzone.Adapter.PostAdapter;
import com.example.friendzone.Adapter.StoryAdapter;
import com.example.friendzone.Model.PostModel;
import com.example.friendzone.Model.Story;
import com.example.friendzone.R;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.GetAllPostModel;
import com.example.friendzone.RetroFit.GetAllStory;
import com.example.friendzone.RetroFit.GetUserStoryModel;
import com.example.friendzone.RetroFit.Post;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.example.friendzone.RetroFit.Story2;
import com.example.friendzone.RetroFit.UploadPostModel;
import com.example.friendzone.RetroFit.UploadStoryModel;
import com.example.friendzone.databinding.ActivityDashBoardBinding;
import com.example.friendzone.databinding.PostBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityDashBoardBinding binding;
    ArrayList<PostModel> postModelArrayList = new ArrayList<>();
    ArrayList<Story> storyArrayList = new ArrayList<>();
    PostBottomSheetBinding sheetBinding;
    RecyclerView recyclerView;
    MaterialCardView et_post;
    ImageView imageView;
    LinearLayout layoutBottomSheet;
    CardView cv_addImage, cv_Post, cv_camera, cv_addVideo;
    ImageView iv_post;
    EditText et_content;
    Bitmap imageBitmap;
    TextView tv_userName;
    VideoView iv_video;
    Bitmap bitmap;
    boolean isImageAdded;
    SharedPreferences sharedPreferences;

    BottomSheetDialog bottomSheetDialog;
    private Uri vodeoUri;
    boolean isVideoAdded = false;
    boolean isStoryAlreadyAdded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);

        binding.cvPost.setOnClickListener(this);
        binding.llStory.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(this);
        binding.btnLogout.setOnClickListener(this);
        binding.cvChats.setOnClickListener(this);
        getAllPosts();
        getAllStory();

/*-------------------------------------=========================================================----------------------------------------------------*/
        //  Setting Status Bar Color

        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


/*------------------------------------=============================================================-------------------------------------------------*/
    }

    @Override
    public void onClick(View view) {
        if (view == binding.cvPost) {
            bottomSheetTask();
        }
        if (view == binding.cvChats) {
            Intent intent = new Intent(DashBoardActivity.this, Chats.class);
            startActivity(intent);
        }
        if (view == binding.llStory) {
            if (sharedPreferences.getString("yourStoryFound", "").equals(true)) {
                getUserStory();
            } else
                bottomSheetTask2();
        }
        if (view == binding.btnLogout) {
            SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getAllStory() {
        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getAllStory().enqueue(new Callback<GetAllStory>() {
            @Override
            public void onResponse(Call<GetAllStory> call, Response<GetAllStory> response) {
                binding.rvStory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
                binding.rvStory.setAdapter(new StoryAdapter(getApplicationContext(), (ArrayList<Story2>) response.body().story));
            }

            @Override
            public void onFailure(Call<GetAllStory> call, Throwable t) {

            }
        });
    }


    private void getUserStory() {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getUserStory(userid).
                enqueue(new Callback<GetUserStoryModel>() {
                    @Override
                    public void onResponse(Call<GetUserStoryModel> call, Response<GetUserStoryModel> response) {


                        if (response.isSuccessful()) {
                            if (response.body().message.equals("Success")) {
                                Intent intent = new Intent(DashBoardActivity.this, StoryActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(DashBoardActivity.this, "Problem2 ", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(DashBoardActivity.this, "Problem ", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<GetUserStoryModel> call, Throwable t) {
                        Toast.makeText(DashBoardActivity.this, "OOh Yeah ", Toast.LENGTH_SHORT).show();
                    }
                });

             /*   Intent intent = new Intent(DashBoardActivity.this, StoryActivity.class);
                startActivity(intent);*/
    }


    private void getAllPosts() {
        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getAllPost().enqueue(new Callback<GetAllPostModel>() {
            @Override
            public void onResponse(Call<GetAllPostModel> call, Response<GetAllPostModel> response) {

                binding.rvPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rvPosts.setAdapter(new PostAdapter(getApplicationContext(), (ArrayList<Post>) response.body().posts));

            }

            @Override
            public void onFailure(Call<GetAllPostModel> call, Throwable t) {
            }
        });

    }

    private void bottomSheetTask() {
        bottomSheetDialog.setContentView(R.layout.post_bottom_sheet);
        bottomSheetDialog.show();
        setupFullHeight(bottomSheetDialog);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        String Name = sharedPreferences.getString("name", "");
        isImageAdded = false;
        cv_addImage = bottomSheetDialog.findViewById(R.id.cv_addImage);
        cv_Post = bottomSheetDialog.findViewById(R.id.cv_Post);
        iv_post = bottomSheetDialog.findViewById(R.id.iv_post);
        et_content = bottomSheetDialog.findViewById(R.id.et_content);
        cv_camera = bottomSheetDialog.findViewById(R.id.cv_camera);
        iv_video = bottomSheetDialog.findViewById(R.id.iv_video);
        cv_addVideo = bottomSheetDialog.findViewById(R.id.cv_addVideo);
        tv_userName = bottomSheetDialog.findViewById(R.id.tv_userName);
        tv_userName.setText(Name);
        cv_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);

            }

        });
        cv_addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Request permission
                    ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            , 1);
                } else {
                    // When permission is granted
                    // Create method
                    selectVideo();
                }
            }

        });


        cv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 200);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }

            }
        });
        cv_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageAdded == true) {
                    String media_text = "1";
                    UploadPost(media_text);


                } else if (isVideoAdded == true) {
                    String media_text = "1";
                    uploadvideo(media_text);


                } else {
                    Toast.makeText(DashBoardActivity.this, "Upload Image,video First", Toast.LENGTH_SHORT).show();
                }


            }
        });
        imageView = bottomSheetDialog.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


    }

    private void bottomSheetTask2() {
        bottomSheetDialog.setContentView(R.layout.story_bottom_sheet);
        bottomSheetDialog.show();

        setupFullHeight(bottomSheetDialog);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        String Name = sharedPreferences.getString("name", "");
        isImageAdded = false;
        cv_addImage = bottomSheetDialog.findViewById(R.id.cv_addImage);
        cv_Post = bottomSheetDialog.findViewById(R.id.cv_Post);
        iv_post = bottomSheetDialog.findViewById(R.id.iv_post);
        et_content = bottomSheetDialog.findViewById(R.id.et_content);
        cv_camera = bottomSheetDialog.findViewById(R.id.cv_camera);
        iv_video = bottomSheetDialog.findViewById(R.id.iv_video);
        cv_addVideo = bottomSheetDialog.findViewById(R.id.cv_addVideo);
        tv_userName = bottomSheetDialog.findViewById(R.id.tv_userName);
        tv_userName.setText(Name);
        cv_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);

            }

        });
        cv_addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Request permission
                    ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            , 1);
                } else {
                    // When permission is granted
                    // Create method
                    selectVideo();
                }
            }

        });


        cv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 200);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }

            }
        });
        cv_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageAdded == true) {
                    UploadStoryPhoto();


                } else if (isVideoAdded == true) {

                    uploadStoryvideo();


                } else {
                    Toast.makeText(DashBoardActivity.this, "Upload Image,video First", Toast.LENGTH_SHORT).show();
                }


            }
        });
        imageView = bottomSheetDialog.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Uri selectedImageUri = data.getData();
                iv_post.setImageURI(selectedImageUri);
                isImageAdded = true;

                //imageBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);

            }
            if (resultCode == Activity.RESULT_OK && requestCode == 200) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                iv_post.setImageBitmap(imageBitmap);
                isImageAdded = true;

            }
            if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
                // When result code is okay
                // Initialize uri
                Uri uri = data.getData();
                // Initialize intent
                iv_video.setVideoURI(uri);
                iv_video.start();
                vodeoUri = uri;
                // Start activity
                isVideoAdded = true;

                iv_post.setVisibility(View.GONE);
                iv_video.setVisibility(View.VISIBLE);
            }
        }
    }

    private void selectVideo() {
        // Initialize intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("video/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent, "Select Video"), 300);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectVideo();
        } else {
            // When permission is denied
            // Display toast
            Toast.makeText(getApplicationContext()
                    , "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        (this).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void UploadPost(String media_text) {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        isImageAdded = false;


        Drawable drawable = iv_post.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        //  Bitmap bitmap1= ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        RequestBody content = RequestBody.create(et_content.getText().toString(), MediaType.parse("text/Plain"));
        RequestBody userid = RequestBody.create(sharedPreferences.getString("userid", ""), MediaType.parse("text/Plain"));
        RequestBody mediatext = RequestBody.create(media_text, MediaType.parse("text/Plain"));
        RequestBody profilePic = RequestBody.create(byteArray, MediaType.parse("Image/*"));

        MultipartBody.Part part = MultipartBody.
                Part.createFormData("file_attachment", "Image.jpeg", profilePic);

        apiInterface.uploadPost(userid, mediatext, content, part).enqueue(new Callback<UploadPostModel>() {

            @Override
            public void onResponse(Call<UploadPostModel> call, Response<UploadPostModel> response) {
                Toast.makeText(getApplicationContext(), "UpDate successful", Toast.LENGTH_SHORT).show();
                getAllPosts();
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UploadPostModel> call, Throwable t) {


            }
        });

    }

    private void UploadStoryPhoto() {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        isStoryAlreadyAdded = true;

        Drawable drawable = iv_post.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        //  Bitmap bitmap1= ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        RequestBody userid = RequestBody.create(sharedPreferences.getString("userid", ""), MediaType.parse("text/Plain"));
        RequestBody profilePic = RequestBody.create(byteArray, MediaType.parse("Image/*"));

        MultipartBody.Part part = MultipartBody.
                Part.createFormData("file_attachment", "Image.jpeg", profilePic);

        apiInterface.UploadStory(userid, part).enqueue(new Callback<UploadStoryModel>() {

            @Override
            public void onResponse(Call<UploadStoryModel> call, Response<UploadStoryModel> response) {
                Toast.makeText(getApplicationContext(), "UpDate successful", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UploadStoryModel> call, Throwable t) {


            }
        });

    }

    private void uploadvideo(String media_text) {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);

        isStoryAlreadyAdded = true;
        byte[] byteArray = getFileDataFromDrawable(getApplicationContext(), vodeoUri);

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        RequestBody content = RequestBody.create(et_content.getText().toString(), MediaType.parse("text/Plain"));
        RequestBody userid = RequestBody.create(sharedPreferences.getString("userid", ""), MediaType.parse("text/Plain"));
        RequestBody mediatext = RequestBody.create(media_text, MediaType.parse("text/Plain"));
        RequestBody profilePic = RequestBody.create(byteArray, MediaType.parse("Video/mp4"));

        MultipartBody.Part part = MultipartBody.
                Part.createFormData("file_attachment", "mp4", profilePic);

        apiInterface.uploadPost(userid, mediatext, content, part).enqueue(new Callback<UploadPostModel>() {

            @Override
            public void onResponse(Call<UploadPostModel> call, Response<UploadPostModel> response) {
                Toast.makeText(getApplicationContext(), "UpDate successful", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();


            }

            @Override
            public void onFailure(Call<UploadPostModel> call, Throwable t) {


            }
        });

    }

    private void uploadStoryvideo() {
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);


        byte[] byteArray = getFileDataFromDrawable(getApplicationContext(), vodeoUri);

        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        RequestBody userid = RequestBody.create(sharedPreferences.getString("userid", ""), MediaType.parse("text/Plain"));
        RequestBody profilePic = RequestBody.create(byteArray, MediaType.parse("Video/mp4"));


        MultipartBody.Part part = MultipartBody.
                Part.createFormData("file_attachment", "mp4", profilePic);

        apiInterface.UploadStory(userid, part).enqueue(new Callback<UploadStoryModel>() {

            @Override
            public void onResponse(Call<UploadStoryModel> call, Response<UploadStoryModel> response) {
                Toast.makeText(getApplicationContext(), "UpDate successful", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();


            }

            @Override
            public void onFailure(Call<UploadStoryModel> call, Throwable t) {


            }
        });

    }

    /*
        public static byte[] convertVideoToBytes(Uri uri) {
            byte[] videoBytes = null;
            try {//  w  w w  . j ava 2s . c  o m
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                FileInputStream fis = new FileInputStream(new File(String.valueOf(uri)));
                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf)))
                    baos.write(buf, 0, n);

                videoBytes = baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return videoBytes;
        }
    */
    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


}