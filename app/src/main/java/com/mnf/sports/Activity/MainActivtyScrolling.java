package com.mnf.sports.Activity;

import android.content.Context;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;
import com.mnf.sports.Adapters.FeedListAdapter;
import com.mnf.sports.Adapters.GroupItemAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Fragments.GroupFragmnet;
import com.mnf.sports.Models.Feeds.FeedModel;
import com.mnf.sports.Models.Feeds.Result;
import com.mnf.sports.Models.Score;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONObject;

public class MainActivtyScrolling extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BarChart mBarChart;
    Score scoreModel;
    Gson gson = new Gson();
    Toolbar toolbar;
    ImageView ib,ig,iy,ir;
  //  RecyclerView feedsRecycle;
    FeedListAdapter adapterFeed;
    FeedModel feedModel;
    Context c;
    LinearLayout feedLinear;
    private LinearLayoutManager mLayoutManager;
    String ImageUrl = Config.BASE_URL+Config.IMAGE_FEED_URL;
    DilatingDotsProgressBar mDilatingDotsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activty_scrolling);
        c = getApplicationContext();
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBarChart = (BarChart) findViewById(R.id.barchart);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progressGraph);
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
        feedLinear = (LinearLayout) findViewById(R.id.feedLinear);
      //  feedsRecycle = (RecyclerView) findViewById(R.id.feedid);
        adapterFeed = new FeedListAdapter(c);
        mLayoutManager
                = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
       // feedsRecycle.setHasFixedSize(false);
        //feedsRecycle.setLayoutManager(mLayoutManager);
        //feedsRecycle.setAdapter(adapterFeed);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing", Snackbar.LENGTH_LONG)
                        .show();

            reloadDataReq();
            }
        });

        createNetworkRequestScore(Config.BASE_URL + Config.SCORE_URL);
        makeFeedRequest(Config.BASE_URL+Config.FEEDS);

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

    private void reloadDataReq() {
        createNetworkRequestScore(Config.BASE_URL + Config.SCORE_URL);
        makeFeedRequest(Config.BASE_URL + Config.FEEDS);
    }

    public void createNetworkRequestScore(String url){
        mDilatingDotsProgressBar.showNow();
        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded Meta data");
                mDilatingDotsProgressBar.hideNow();
                scoreModel = gson.fromJson(response.toString(), Score.class);
                if(scoreModel!=null){

                    initGraph(Float(scoreModel.getBlue()),Float(scoreModel.getGreen()),Float(scoreModel.getYellow()),Float(scoreModel.getRed()));
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDilatingDotsProgressBar.hideNow();
                Snackbar.make(feedLinear, R.string.network_error, Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reloadDataReq();
                    }
                })
                        .show();

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



    public void makeFeedRequest(String rl){
        JsonObjectRequest reqthree = new JsonObjectRequest(com.android.volley.Request.Method.GET, rl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded feed data");
                int limit = 0;

                feedModel = gson.fromJson(response.toString(), FeedModel.class);
                if(feedModel!=null) {
                    if (feedModel.getStatus().equals("success")) {
                        Log.e("tag", "Loaded feed succes");
                        if(feedModel.getResult().size()>=10){
                            limit=10;
                        }else{
                            limit=feedModel.getResult().size();
                        }

                        for(int i=0;i<limit;i++){
                            Log.e("tag","feed result loop i = "+i);
                            Result item = feedModel.getResult().get(i);
                            View v = getLayoutInflater().inflate(R.layout.feed_item, null);
                            TextView mainString,subString,authorFeed;
                            CardView cardView;
                            ImageView feedLogo;
                            mainString = (TextView) v.findViewById(R.id.mainString);
                            subString = (TextView) v.findViewById(R.id.subString);
                            authorFeed = (TextView) v.findViewById(R.id.authorFeed);
                            cardView = (CardView) v.findViewById(R.id.cardFeedItem);
                            feedLogo = (ImageView) v.findViewById(R.id.feedImg);

                            feedLogo.setVisibility(View.VISIBLE);
                            if(item.getMainstring()!=null){
                                mainString.setText(item.getMainstring().toString());
                            }
                            if(item.getSubstring()!=null){
                                if(!item.getSubstring().equals("")){
                                    subString.setText(item.getSubstring());
                                }
                            }
                            if(item.getAuthor()!=null){
                                if(!item.getAuthor().equals("")){
                                    authorFeed.setText(item.getAuthor());
                                }
                            }

                            if(item.getImage()!=null){
                                if(!item.getImage().equals("")){
                                    Picasso.with(c).load(ImageUrl + item.getImage()).into(feedLogo);
                                }else{
                                    feedLogo.setVisibility(View.GONE);
                                }
                            }else{
                                feedLogo.setVisibility(View.GONE);
                            }
                            feedLinear.addView(v);
                        }



                       // adapterFeed.addItems(feedModel.getResult());
                        //totalPages = feedModel.getTotalPages();
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Snackbar.make(feedLinear, R.string.network_error, Snackbar.LENGTH_LONG).show();
                if(volleyError.networkResponse!=null) {
                    if (volleyError.networkResponse.statusCode == 401) {
                        // Toast.makeText(getActivity(), "Login Failed Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }
                Log.e("tagmeta", "error is " + volleyError.getLocalizedMessage()+"   \n error details = "+volleyError.getCause());

            }
        });

        AppController.getInstance().addToRequestQueue(reqthree);


    }





    public float Float(String s){
        return Float.parseFloat(s);
    }

    public void initGraph(float b,float g,float y, float r){
        mBarChart.clearChart();

        mBarChart.addBar(new BarModel(getString(R.string.blizardians), b, getApplicationContext().getResources().getColor(R.color.blue500)));
        mBarChart.addBar(new BarModel(getString(R.string.gravitans), g, getApplicationContext().getResources().getColor(R.color.greenA700)));
        mBarChart.addBar(new BarModel(getString(R.string.yagorians), y, getApplicationContext().getResources().getColor(R.color.yellow600)));

        mBarChart.addBar(new BarModel(getString(R.string.racovians), r, getApplicationContext().getResources().getColor(R.color.red)));
        mBarChart.startAnimation();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent activty = new Intent(MainActivtyScrolling.this, MainActivtyScrolling.class);
            startActivity(activty);
        } else if (id == R.id.nav_group) {
            Intent activty = new Intent(MainActivtyScrolling.this, GroupActivityReal.class);
            startActivity(activty);


        } else if (id == R.id.nav_gallery) {
            Intent activty = new Intent(MainActivtyScrolling.this, GalleryActivitySecond.class);
            startActivity(activty);

        } else if (id == R.id.nav_events) {
            Intent activty = new Intent(MainActivtyScrolling.this, EventListActivity.class);
            startActivity(activty);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;    }
}
