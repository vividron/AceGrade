package com.example.sgpa;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;

import com.example.sgpa.help.Help;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String ANIMATION_SHOWN = "animation_shown";
    private static final String DOWNLOAD_KEY = "alreadyDownloaded";
    private static final String FIRST_OPEN_KEY = "isFirstOpen";
    Button predict, ktConsultant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean animationShown = prefs.getBoolean(ANIMATION_SHOWN, false);
        ImageView graphityMale = findViewById(R.id.homeScreenImage);
        ImageView graphityFemale = findViewById(R.id.homeScreenImage2);
        predict = findViewById(R.id.predict);
        ktConsultant = findViewById(R.id.kt_consultant);
        LinearLayout helpBtn = findViewById(R.id.help_btn);

        SharedPreferences downloadData = getSharedPreferences(DOWNLOAD_KEY, MODE_PRIVATE);
        boolean isFirstOpen = downloadData.getBoolean(FIRST_OPEN_KEY, true);

        DatabaseReference downloadDatabase = FirebaseDatabase.getInstance().getReference("downloads");

        if (isFirstOpen && isConnectedToInternet()) {
            // If it's the first open, check the "downloads" node in Firebase
            downloadDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // If "downloads" node exists, increment the value
                        Integer downloads = dataSnapshot.getValue(Integer.class);
                        if (downloads != null) {
                            if (downloads != 0) {
                                downloadDatabase.setValue(downloads + 1);
                            } else {
                                downloadDatabase.setValue(1);
                            }
                        } else {
                            downloadDatabase.setValue(1);
                        }
                    } else {
                        downloadDatabase.setValue(1);
                    }

                    SharedPreferences.Editor editor = downloadData.edit();
                    editor.putBoolean(FIRST_OPEN_KEY, false);
                    editor.apply();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                }
            });
        }


        if (!animationShown) {
            // Randomly select one image for initial pop-up animation
            ImageView popupImage = Math.random() > 0.5 ? graphityMale : graphityFemale;
            ImageView nextImage = popupImage == graphityMale ? graphityFemale : graphityMale;
            Animation popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up);
            popUpAnimation.setDuration(500);
            popupImage.setVisibility(View.VISIBLE);
            popupImage.startAnimation(popUpAnimation);

            // Start continuous switching after the pop-up animation ends
            popUpAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    new Handler().postDelayed(() -> {
                        startImageSwitchingAnimation(popupImage, nextImage);
                    }, 5000);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            // Save animation shown flag
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(ANIMATION_SHOWN, true);
            editor.apply();
        } else {
            // Start image switching animation directly on subsequent app launches
            startImageSwitchingAnimation(graphityMale, graphityFemale);
        }


        predict.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, Branch.class);
            startActivity(intent);
        });
        ktConsultant.setOnClickListener(view -> startActivity(new Intent(HomePage.this, KtConsultant.class)));
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, Help.class);
            intent.putExtra("bckConfig" , 0);
            startActivity(intent);
        });
    }

    private void startImageSwitchingAnimation(ImageView image1, ImageView image2) {
        // Initialize fade-in and fade-out animations
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut.setDuration(1000);
        fadeIn.setDuration(1000);

        // Create a handler for continuous animation switching
        final Handler handler = new Handler();
        final Runnable switchImages = new Runnable() {
            boolean showingFirstImage = true;

            @Override
            public void run() {
                if (showingFirstImage) {
                    // Fade out image1 and fade in image2
                    image1.startAnimation(fadeOut);
                    image1.setVisibility(View.GONE);
                    image2.setVisibility(View.VISIBLE);
                    image2.startAnimation(fadeIn);
                } else {
                    // Fade out image2 and fade in image1
                    image2.startAnimation(fadeOut);
                    image2.setVisibility(View.GONE);
                    image1.setVisibility(View.VISIBLE);
                    image1.startAnimation(fadeIn);
                }
                showingFirstImage = !showingFirstImage;

                // Repeat after the animations complete
                handler.postDelayed(this, 5000); // Delay = animation duration + pause (optional)
            }
        };

        // Start the animation loop
        handler.post(switchImages);
    }
    @Override
    public void onBackPressed() {
        saveFlag();
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveFlag();
    }
    private void saveFlag(){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ANIMATION_SHOWN, false);
        editor.apply();
    }
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
