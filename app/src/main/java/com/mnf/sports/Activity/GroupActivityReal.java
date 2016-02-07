package com.mnf.sports.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mnf.sports.Fragments.BFragment;
import com.mnf.sports.Fragments.FragmentInstance;
import com.mnf.sports.Fragments.GFragment;
import com.mnf.sports.Fragments.RFragment;
import com.mnf.sports.Fragments.YFragment;
import com.mnf.sports.R;

public class GroupActivityReal extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_real);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        logo = (ImageView) findViewById(R.id.logo_white);
        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(GroupActivityReal.this,SearchActivity.class);
                startActivity(searchIntent);
            }
        });


        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Log.e("TAG", "tab pos = " + position);
                switch (position % 4) {
                    case 0:

                        //return FragmentInstance.getInstance(1);
                        return new BFragment();
                    case 1:

                        return new GFragment();
                    case 2:

                        return new YFragment();
                    case 3:
                        return new RFragment();
                    default:
                        return FragmentInstance.getInstance(1);
                    // return null;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return getString(R.string.blizardians);
                    case 1:
                        return getString(R.string.gravitans);
                    case 2:
                        return getString(R.string.yagorians);
                    case 3:
                        return getString(R.string.racovians);
                }
                return "";
            }
        });

        if(getIntent().hasExtra("gid")){
            String value = getIntent().getExtras().getString("gid");
            switch (value){
                case "b":
                    mViewPager.getViewPager().setCurrentItem(0);
                    break;
                case "g":
                    mViewPager.getViewPager().setCurrentItem(1);
                    break;
                case "y":
                    mViewPager.getViewPager().setCurrentItem(2);
                    break;
                case "r":
                    mViewPager.getViewPager().setCurrentItem(3);
                    break;
            }
        }


        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {


            @Override
            public HeaderDesign getHeaderDesign(int page) {
                Log.e("TAG","header pos = "+page);
                switch (page) {
                    case 0:logo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.b));
                        return  HeaderDesign.fromColorAndUrl(getApplicationContext().getResources().getColor(R.color.blue),  "https://fs01.androidpit.info/a/63/0e/androidl-wallpapers-630ea6-h900.jpg"); /* HeaderDesign.fromColorResAndUrl(
                                R.color.blue,""
                               // "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg"
                        );*/
                    case 1:logo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.g));
                        return HeaderDesign.fromColorAndUrl(getApplicationContext().getResources().getColor(R.color.green), "https://fs01.androidpit.info/a/63/0e/androidl-wallpapers-630ea6-h900.jpg");
                    case 2:logo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.y));
                        return HeaderDesign.fromColorAndUrl(getApplicationContext().getResources().getColor(R.color.holo_yellow_dark),  "https://fs01.androidpit.info/a/63/0e/androidl-wallpapers-630ea6-h900.jpg");
                    case 3:logo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.r));
                        return HeaderDesign.fromColorAndUrl(getApplicationContext().getResources().getColor(R.color.red),  "https://fs01.androidpit.info/a/63/0e/androidl-wallpapers-630ea6-h900.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                  //  Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });





}

}
