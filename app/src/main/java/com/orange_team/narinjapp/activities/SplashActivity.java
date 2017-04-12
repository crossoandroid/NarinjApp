package com.orange_team.narinjapp.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange_team.narinjapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.startup);
                mp.start();
        startAnimations();
    }

    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_splash);
        linearLayout.clearAnimation();
        linearLayout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView logoImage = (ImageView) findViewById(R.id.logo_imageView);
        logoImage.clearAnimation();
        logoImage.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
