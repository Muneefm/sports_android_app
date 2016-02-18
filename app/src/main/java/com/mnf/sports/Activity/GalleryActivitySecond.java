package com.mnf.sports.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mnf.sports.Adapters.GalleryRecycleAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.EventModel.EventListModel;
import com.mnf.sports.Models.GalleryModels.GalleryModel;
import com.mnf.sports.R;

import org.json.JSONObject;

public class GalleryActivitySecond extends AppCompatActivity {
    RecyclerView galleryReycleView;
    public Gson gson = new Gson();
    GalleryRecycleAdapter adapter;
    int page =1;
    int totalPages=1;

    GalleryModel mDat;
    RelativeLayout root;
    boolean weth =true;
    boolean twiceLoad =false;
    String imageUrl = Config.BASE_URL+Config.IMAGE_URL;

    private GridLayoutManager gridLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        galleryReycleView = (RecyclerView) findViewById(R.id.galleryRecycle);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/120);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),columns,GridLayoutManager.VERTICAL,false);

        galleryReycleView.setLayoutManager(gridLayoutManager);
        adapter = new GalleryRecycleAdapter(getApplicationContext());

        galleryReycleView.setAdapter(adapter);

        makeJsonArrayRequest(imageUrl);

        galleryReycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = gridLayoutManager.getChildCount();
                Log.e("TAG", "scroll Visible = " + visibleItemCount);
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage");
                            Log.e("TAGl", "last inside tot & page = " + totalPages + " " + page);
                            page++;
//                            paramsModel.add(new ParamsModel("page", page + ""));

                            makeJsonArrayRequest(imageUrl + "&page=" + page);
                            //  showProgg();
                        }
                    }
                }
            }
        });

    }

    private void makeJsonArrayRequest(String url) {

        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded  data");

                mDat = gson.fromJson(response.toString(), GalleryModel.class);
                loading = true;

                if(mDat!=null) {
                    if (mDat.getStatus().equals("success")) {
                        Log.e("tag", "Loaded  succes");
                        adapter.addItems(mDat.getResult());
                        totalPages = mDat.getTotalPage();

                    }
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

}