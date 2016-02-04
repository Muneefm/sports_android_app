package com.mnf.sports.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnf.sports.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragmnet extends Fragment {


    public GroupFragmnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_scrolling, container, false);

        return v;
    }

}
