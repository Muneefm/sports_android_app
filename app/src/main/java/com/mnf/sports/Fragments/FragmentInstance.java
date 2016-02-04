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
public class FragmentInstance extends Fragment {


    public FragmentInstance() {
        // Required empty public constructor
    }
    public static FragmentInstance getInstance(int pos) {
        String TAG = "logging";


        FragmentInstance fragmentInstance = new FragmentInstance();
        Bundle bundle = new Bundle();
        //Log.e(TAG,"value of pos is "+pos);
        bundle.putInt("position", pos);


        fragmentInstance.setArguments(bundle);

        return  fragmentInstance;

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_fragment_instance, container, false);
    return v;
    }

}
