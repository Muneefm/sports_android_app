package com.mnf.sports.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnf.sports.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroupEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroupEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String URL = "url";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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


    return  v;
    }

}
