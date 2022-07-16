package com.example.friendzone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.friendzone.R;
import com.example.friendzone.RetroFit.ApiInterface;
import com.example.friendzone.RetroFit.GetUserStoryModel;
import com.example.friendzone.RetroFit.RetrofitClient;
import com.example.friendzone.RetroFit.Story;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    ArrayList<Story> arrayList = new ArrayList<>();

    String userId;

    private void getUserStory(String userid) {
        Log.e("Run", "getUserStory: " + "run");
        Retrofit retrofit = RetrofitClient.RetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getUserStory(userid).
                enqueue(new Callback<GetUserStoryModel>() {
                    @Override
                    public void onResponse(Call<GetUserStoryModel> call, Response<GetUserStoryModel> response) {

                        if (response.body().story != null) {
                            Log.e("RESPONSE", "onResponse: " + response.body());
                            arrayList.addAll(response.body().story);
                            getAll();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetUserStoryModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "OOh Yeah ", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    // on below line we are creating variable for
    // our press time and time limit to display a story.
    long pressTime = 0L;
    long limit = 500L;

    // on below line we are creating variables for
    // our progress bar view and image view .
    private StoriesProgressView storiesProgressView;
    private ImageView image;

    // on below line we are creating a counter
    // for keeping count of our stories.
    private int counter = 0;

    // on below line we are creating a new method for adding touch listener
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // inside on touch method we are
            // getting action on below line.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis();

                    // on below line we are pausing our indicator.
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    long now = System.currentTimeMillis();

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView.resume();

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inside in create method below line is use to make a full screen.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_story);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        userId = getIntent().getStringExtra("userId");
        getUserStory(userId);
        Log.e("userId", "onCreate: " + userId);


        // on below line we are initializing our variables.


        // initializing our image view.
        image = (ImageView) findViewById(R.id.image);

        // on below line we are setting image to our image view.
        // image.setImageResource(resources[counter]);

        // below is the view for going to the previous story.
        // initializing our previous view.
        View reverse = findViewById(R.id.reverse);

        // adding on click listener for our reverse view.
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click we are
                // reversing our progress view.
                storiesProgressView.reverse();
            }
        });

        // on below line we are calling a set on touch
        // listener method to move towards previous image.
        reverse.setOnTouchListener(onTouchListener);

        // on below line we are initializing
        // view to skip a specific story.
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click we are
                // skipping the story progress view.
                storiesProgressView.skip();
            }
        });
        // on below line we are calling a set on touch
        // listener method to move to next story.
        skip.setOnTouchListener(onTouchListener);
    }

   /* private void getImage(ArrayList arrayList) {
        for (int i=0;i<=arrayList.size();i++){
            arrayList.get(i).
        }
    }*/

    @Override
    public void onNext() {
        // this method is called when we move
        // to next progress view of story.
        //   image.setImageResource(resources[++counter]);
        Picasso.get()
                .load("http://192.168.1.112/BasicStructure/" + arrayList.get(++counter).storyMediaPath)
                .into(image);

    }

    @Override
    public void onPrev() {

        // this method id called when we move to previous story.
        // on below line we are decreasing our counter
        if ((counter - 1) < 0) return;

        // on below line we are setting image to image view
        //  image.setImageResource(resources[--counter]);
        Picasso.get()
                .load("http://192.168.1.112/BasicStructure/" + arrayList.get(--counter).storyMediaPath)
                .into(image);

    }

    @Override
    public void onComplete() {
        // when the stories are completed this method is called.
        // in this method we are moving back to initial main activity.
        Intent i = new Intent(StoryActivity.this, DashBoardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        // in on destroy method we are destroying
        // our stories progress view.
        storiesProgressView.destroy();
        super.onDestroy();
    }

    void getAll() {
        // on below line we are setting the total count for our stories.
        storiesProgressView.setStoriesCount(arrayList.size());

        // on below line we are setting story duration for each story.
        storiesProgressView.setStoryDuration(3000L);

        // on below line we are calling a method for set
        // on story listener and passing context to it.
        storiesProgressView.setStoriesListener(this);

        // below line is use to start stories progress bar.
        storiesProgressView.startStories(counter);
        Picasso.get()
                .load("http://192.168.1.112/BasicStructure/" + arrayList.get(counter).storyMediaPath)
                .into(image);


    }
}