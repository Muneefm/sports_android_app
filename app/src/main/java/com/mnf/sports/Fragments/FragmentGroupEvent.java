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
import com.google.gson.Gson;
import com.mnf.sports.Adapters.EventListAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.EventModel.EventListModel;
import com.mnf.sports.R;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONObject;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroupEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroupEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String Url = Config.BASE_URL+Config.EVENT_GROUP;

    EventListModel eventModel;
    RecyclerView evIndiRecycle;
    private LinearLayoutManager mLayoutManager;
    EventListAdapter mAdapter;
    public Gson gson = new Gson();

    Context c;
    private static final String URL = "url";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CircularProgressBar progLogin;

    DilatingDotsProgressBar mDilatingDotsProgressBar;
    public FragmentGroupEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentGroupEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGroupEvent newInstance() {
        FragmentGroupEvent fragment = new FragmentGroupEvent();
        Bundle args = new Bundle();
        //args.putString(URL, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         //   mParam1 = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_group_event, container, false);

        c =getContext();
        evIndiRecycle = (RecyclerView) v.findViewById(R.id.groupRecycle);
        //mDilatingDotsProgressBar = (DilatingDotsProgressBar) v.findViewById(R.id.progressEg);
        progLogin = (CircularProgressBar) v.findViewById(R.id.progGrp);

        mAdapter = new EventListAdapter(c);
        mLayoutManager
                = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        evIndiRecycle.setHasFixedSize(false);
        evIndiRecycle.setLayoutManager(mLayoutManager);
        evIndiRecycle.setAdapter(mAdapter);
        makeNetworkRequest(Url);
    return  v;
    }
    public void makeNetworkRequest(String url){
       // mDilatingDotsProgressBar.showNow();
        progLogin.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).start();
        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded  data");
              //  mDilatingDotsProgressBar.hideNow();
                ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).stop();
                progLogin.setVisibility(View.GONE);
                eventModel = gson.fromJson(response.toString(), EventListModel.class);

                if(eventModel!=null) {
                    if (eventModel.getStatus().equals("success")) {
                        Log.e("tag", "Loaded  succes");

                        mAdapter.addItems(eventModel.getResult());
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //mDilatingDotsProgressBar.hideNow();
                ((CircularProgressDrawable)progLogin.getIndeterminateDrawable()).stop();
                progLogin.setVisibility(View.GONE);

               // if(mDilatingDotsProgressBar!=null) {
                if(getView()!=null) {
                    Log.e("snack","inside getView not null");
                    Snackbar.make(getView(), R.string.network_error, Snackbar.LENGTH_LONG).show();
                }else{
                    Log.e("snack","inside getView null");
                }
                //}
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
