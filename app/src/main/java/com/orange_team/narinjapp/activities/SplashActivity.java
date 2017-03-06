package com.orange_team.narinjapp.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange_team.narinjapp.R;

public class SplashActivity extends AppCompatActivity {

    Thread thread;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.orange_team.narinjapp.R.layout.activity_splash);
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
        final CheckNetworkConnection checkNetworkConnection = new CheckNetworkConnection();


        if (checkNetworkConnection.isConnectionAvailable(getApplicationContext())) {
            thread = new Thread() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        while (waited < 3500) {
                            sleep(100);
                            waited += 100;
                        }
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        SplashActivity.this.finish();
                    }
                }
            };
            thread.start();
        } else {
            AlertDialog access = alert();
            access.show();
        }

    }

    private AlertDialog alert()
    {
        AlertDialog accessInternet=new AlertDialog.Builder(SplashActivity.this)
                .setMessage(getString(R.string.internet_access))
                .setPositiveButton(R.string.enable_internet, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.disable_internet, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }).create();
        return accessInternet;
    }
}
