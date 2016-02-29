package com.mnf.sports.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.mnf.sports.MainActivity;
import com.mnf.sports.R;
import com.mnf.sports.SplashFiles.activity.AwesomeSplash;
import com.mnf.sports.SplashFiles.cnst.Flags;
import com.mnf.sports.SplashFiles.model.ConfigSplash;


public class SplashActivity extends AwesomeSplash {


    int TIME_OUT = 3000;

    @Override
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(R.color.white); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(0); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logos); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        configSplash.setPathSplash(""); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.accent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.grey500); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("Thegzis 16");
        configSplash.setSubTitleSplash("Powered By Zkifods");
        configSplash.setTitleTextColor(R.color.black);;;
        configSplash.setTitleTextSize(45f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
       // configSplash.setTitleFont("fonts/Capture_it_2.ttf");
        configSplash.setTitleFont("fonts/steel.ttf");

        configSplash.setSubTitleFont("fonts/Roboto-Regular.ttf");



    }

    @Override
    public void animationsFinished() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivtyScrolling.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

}
