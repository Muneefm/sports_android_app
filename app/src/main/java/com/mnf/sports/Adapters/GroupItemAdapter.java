package com.mnf.sports.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mnf.sports.Models.GroupMembersModel.Result;
import com.mnf.sports.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muneef on 05/02/16.
 */
public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.ViewHolder>  {

    List<Result> mDataset;
    Context c;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View viewi;
        TextView name,year,cls;
        // public final TextView dateR;
        public ViewHolder(View v) {
            super(v);
            viewi = v;
            name = (TextView) v.findViewById(R.id.name_id);
            year = (TextView) v.findViewById(R.id.year);
            cls = (TextView) v.findViewById(R.id.cls);
        }
    }

    public GroupItemAdapter(Context mContext) {
        this.c = mContext;
        this.mDataset = new ArrayList<Result>();
    }
    public void addItems(List<Result> newItems) {
        this.mDataset.addAll(newItems);
        notifyItemRangeInserted(this.mDataset.size(), newItems.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_name_single_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface type = Typeface.createFromAsset(c.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        holder.name.setTypeface(type);
            holder.name.setText(properCase(mDataset.get(position).getName().toString()));
        holder.year.setText(mDataset.get(position).getYear().toString()+" year");
        holder.cls.setText(mDataset.get(position).getClass_());
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
        return mDataset.size();
    }

}
