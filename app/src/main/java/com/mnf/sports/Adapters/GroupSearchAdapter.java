package com.mnf.sports.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mnf.sports.Models.GroupSearchModels.Result;
import com.mnf.sports.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muneef on 07/02/16.
 */
public class GroupSearchAdapter extends RecyclerView.Adapter<GroupSearchAdapter.ViewHolder>  {

   List<Result> mData;
    Context c;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View viewi;
        TextView name,year,cls;
        View grpFlag;
        // public final TextView dateR;
        public ViewHolder(View v) {
            super(v);
            viewi = v;
            name = (TextView) v.findViewById(R.id.name_id);
            year = (TextView) v.findViewById(R.id.year);
            cls = (TextView) v.findViewById(R.id.cls);
            grpFlag = (View) v.findViewById(R.id.group_flag);
        }
    }
    public GroupSearchAdapter(Context mContext) {
        this.c = mContext;
        this.mData = new ArrayList<Result>();
    }
    public void addItems(List<Result> newItems) {
        this.mData.addAll(newItems);
        notifyItemRangeInserted(this.mData.size(), newItems.size());
    }
 public void removeItems(){
   //  List<Result> list = new ArrayList<Result>();
    // this.mData = list;

    mData.clear();
     notifyDataSetChanged();
 }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_name_single_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mData.get(position).getGroup()!=null){
            Log.e("tag","grp = "+mData.get(position).getGroup().toString());
            holder.grpFlag.setVisibility(View.VISIBLE);
            switch (mData.get(position).getGroup().toString()){

                case "b":
                    holder.grpFlag.setBackgroundColor(c.getResources().getColor(R.color.blue500));
                    break;
                case "g":
                    holder.grpFlag.setBackgroundColor(c.getResources().getColor(R.color.green));
                    break;
                case "y":
                    holder.grpFlag.setBackgroundColor(c.getResources().getColor(R.color.yellow600));
                    break;
                case "r":
                    holder.grpFlag.setBackgroundColor(c.getResources().getColor(R.color.red500));
                    break;
                default:
                    holder.grpFlag.setBackgroundColor(c.getResources().getColor(R.color.white));
                    break;
            }
        }

        holder.name.setTextColor(c.getResources().getColor(R.color.grey800));
        holder.name.setText(properCase(mData.get(position).getName().toString()));
        holder.year.setText(mData.get(position).getYear()+" year");
        holder.cls.setText(mData.get(position).getCls());

    }
    String properCase (String inputVal) {
        // Empty strings should be returned as-is.

        if (inputVal.length() == 0) return "";

        // Strings with only one character uppercased.

        if (inputVal.length() == 1) return inputVal.toUpperCase();

        // Otherwise uppercase first letter, lowercase the rest.

        return inputVal.substring(0,1).toUpperCase()
                + inputVal.substring(1).toLowerCase();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }


}
