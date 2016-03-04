package com.mnf.sports.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.Gson;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.GalleryModels.GalleryModel;
import com.mnf.sports.Models.GalleryModels.Result;
import com.mnf.sports.Models.GroupMembersModel.GroupMembersModel;
import com.mnf.sports.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {


    public Gson gson = new Gson();
    String Url = Config.BASE_URL+Config.IMAGE_URL;
    String imageUrl = Config.BASE_URL+Config.IMAGE_RESOURCE_URL;
    GalleryModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        makeNetworkRequest(Url);
    }

    public void makeNetworkRequest(String url){
        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded  data");

                model = gson.fromJson(response.toString(), GalleryModel.class);
              //  loading = true;
                if(model!=null) {
                    if (model.getStatus().equals("success")) {
                        Log.e("tag", "Loaded  succes");

                    //    adapter.addItems(modelGroup.getResult());
                      //  mAdapter.notifyDataSetChanged();;
                      //  totalPages = modelGroup.getTotalPages();
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
