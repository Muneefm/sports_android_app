package com.mnf.sports.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Fragments.GroupFragmnet;
import com.mnf.sports.Models.Score;
import com.mnf.sports.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONObject;

public class MainActivtyScrolling extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BarChart mBarChart;
    Score scoreModel;
    Gson gson = new Gson();
    Toolbar toolbar;
    ImageView ib,ig,iy,ir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activty_scrolling);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBarChart = (BarChart) findViewById(R.id.barchart);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ib = (ImageView) findViewById(R.id.ib);
        ig = (ImageView) findViewById(R.id.ig);
        iy = (ImageView) findViewById(R.id.iy);
        ir = (ImageView) findViewById(R.id.ir);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        createNetworkRequestScore(Config.BASE_URL + Config.SCORE_URL);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grpIntent = new Intent(MainActivtyScrolling.this, GroupActivityReal.class);
                grpIntent.putExtra("gid", "b");
                startActivity(grpIntent);
            }
        });
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grpIntent = new Intent(MainActivtyScrolling.this,GroupActivityReal.class);
                grpIntent.putExtra("gid", "g");
                startActivity(grpIntent);
            }
        });
        iy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grpIntent = new Intent(MainActivtyScrolling.this,GroupActivityReal.class);
                grpIntent.putExtra("gid", "y");
                startActivity(grpIntent);
            }
        });
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grpIntent = new Intent(MainActivtyScrolling.this,GroupActivityReal.class);
                grpIntent.putExtra("gid", "r");
                startActivity(grpIntent);
            }
        });


    }
    public void createNetworkRequestScore(String url){
        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded Meta data");

                scoreModel = gson.fromJson(response.toString(), Score.class);
                if(scoreModel!=null){
                    initGraph(Float(scoreModel.getBlue()),Float(scoreModel.getGreen()),Float(scoreModel.getYellow()),Float(scoreModel.getRed()));
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if(volleyError.networkResponse!=null) {
                    if (volleyError.networkResponse.statusCode == 401) {
                        // Toast.makeText(getActivity(), "Login Failed Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }
                Log.e("tagmeta", "error is " + volleyError.getLocalizedMessage()+"   \n error details = "+volleyError.getCause());

            }
        });

        AppController.getInstance().addToRequestQueue(reqtwo);


    }
    public float Float(String s){
        return Float.parseFloat(s);
    }

    public void initGraph(float b,float g,float y, float r){

        mBarChart.addBar(new BarModel(getString(R.string.blizardians), b, getApplicationContext().getResources().getColor(R.color.blue500)));
        mBarChart.addBar(new BarModel(getString(R.string.gravitans), g, getApplicationContext().getResources().getColor(R.color.greenA700)));
        mBarChart.addBar(new BarModel(getString(R.string.yagorians), y, getApplicationContext().getResources().getColor(R.color.yellow600)));

        mBarChart.addBar(new BarModel(getString(R.string.racovians), r, getApplicationContext().getResources().getColor(R.color.red)));
        mBarChart.startAnimation();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
          //  getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new GroupFragmnet()).commit();
          //  toolbar.setVisibility(View.GONE);
        } else if (id == R.id.nav_gallery) {
            Intent activty = new Intent(MainActivtyScrolling.this, GroupActivityReal.class);
            startActivity(activty);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;    }
}
