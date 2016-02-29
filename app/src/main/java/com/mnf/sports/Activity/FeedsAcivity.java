package com.mnf.sports.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mnf.sports.Adapters.FeedListAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.Feeds.FeedModel;
import com.mnf.sports.Models.Feeds.Result;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONObject;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

public class FeedsAcivity extends AppCompatActivity {
    RecyclerView feedsRecycle;
    FeedListAdapter adapterFeed;
    private LinearLayoutManager mLayoutManager;
    Gson gson = new Gson();
    FeedModel feedModel;
    Context c;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading =true;
    int page =1;
    int totalPages=1;
   String  feedUrl = Config.BASE_URL + Config.FEEDS;
    //DilatingDotsProgressBar mDilatingDotsProgressBar;
    CircularProgressBar progLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        c = getApplicationContext();
        feedsRecycle = (RecyclerView) findViewById(R.id.feedid);
        progLogin = (CircularProgressBar) findViewById(R.id.progFeedback);

       // mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progressFeed);
        adapterFeed = new FeedListAdapter(c);
        mLayoutManager
                = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        feedsRecycle.setHasFixedSize(false);
        feedsRecycle.setLayoutManager(mLayoutManager);
        feedsRecycle.setAdapter(adapterFeed);
        makeFeedRequest(feedUrl);

        feedsRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e("TAGl", "inside onScolled totpages = " + totalPages);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.e("TAGl", "inside loading tot & page = " + totalPages + " " + page);
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage");
                            Log.e("TAGl", "last inside tot & page = " + totalPages + " " + page);
                            page++;
//                            paramsModel.add(new ParamsModel("page", page + ""));

                            makeFeedRequest(feedUrl + "&page=" + page);
                            //  showProgg();
                        }

                    }
                }

            }
        });
    }

    public void makeFeedRequest(String rl){
        //mDilatingDotsProgressBar.showNow();
        progLogin.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).start();
        JsonObjectRequest reqthree = new JsonObjectRequest(com.android.volley.Request.Method.GET, rl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded feed data");
               // mDilatingDotsProgressBar.hideNow();
                ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).stop();
                progLogin.setVisibility(View.GONE);
                int limit = 0;

                feedModel = gson.fromJson(response.toString(), FeedModel.class);
                loading = true;
                if(feedModel!=null) {
                    if (feedModel.getStatus().equals("success")) {



                         adapterFeed.addItems(feedModel.getResult());
                        totalPages = feedModel.getTotalPage();
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
              //  mDilatingDotsProgressBar.hideNow();
             //   if(mDilatingDotsProgressBar!=null) {
                ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).stop();
                progLogin.setVisibility(View.GONE);
                    Snackbar.make(feedsRecycle, R.string.network_error, Snackbar.LENGTH_LONG).show();
               // }
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


}
