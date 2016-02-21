package com.mnf.sports.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.google.gson.Gson;
import com.mnf.sports.Adapters.GroupItemAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.GroupMembersModel.GroupMembersModel;
import com.mnf.sports.R;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class YFragment extends Fragment {

    Context c;
    Boolean loading=true;
    public Gson gson = new Gson();
    int page =1;
    GroupItemAdapter adapter;
    ObservableRecyclerView lv;
    private LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    GroupMembersModel modelGroup;
    int totalPages=1;
    String Url = Config.BASE_URL+Config.GROUP_MEMBER_Y;

    public YFragment() {
        // Required empty public constructor
    }
    DilatingDotsProgressBar mDilatingDotsProgressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_y, container, false);

        c = getContext();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewy);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) v.findViewById(R.id.progressy);

        adapter = new GroupItemAdapter(c);
        mAdapter = new RecyclerViewMaterialAdapter(adapter);
        mLayoutManager
                = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

                            makeNetworkRequest(Url + "&page=" + page);
                            //  showProgg();
                        }

                    }
                }

            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
        makeNetworkRequest(Url);

        return  v;
    }


    public void makeNetworkRequest(String url){
        mDilatingDotsProgressBar.showNow();

        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded  data");
                mDilatingDotsProgressBar.hideNow();
                modelGroup = gson.fromJson(response.toString(), GroupMembersModel.class);
                loading = true;

                if(modelGroup!=null) {
                    if (modelGroup.getStatus().equals("success")) {
                        Log.e("tag", "Loaded  succes");

                        adapter.addItems(modelGroup.getResult());
                        mAdapter.notifyDataSetChanged();;
                        totalPages = modelGroup.getTotalPages();
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDilatingDotsProgressBar.hideNow();
                Snackbar.make(mRecyclerView, R.string.network_error, Snackbar.LENGTH_LONG).show();
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
