package com.mnf.sports.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mnf.sports.Activity.ImageViewActivity;
import com.mnf.sports.Config;
import com.mnf.sports.Models.GalleryModels.Result;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Muneef on 16/02/16.
 */
public class GalleryRecycleAdapter extends RecyclerView.Adapter<GalleryRecycleAdapter.ViewHolder> {

    List<Result> mData;
    Context c;
    String imgUrl = Config.BASE_URL+Config.IMAGE_RESOURCE_URL;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View viewi;
        //public final TextView text,desc;//usersC;
        public final ImageView image;
        //  public final CardView cv;
        // public final TextView dateR;
        public ViewHolder(View v) {
            super(v);
            viewi = v;
            image  = (ImageView) v.findViewById(R.id.foto);

        }
    }


    public GalleryRecycleAdapter(Context mContext) {
        this.mData = new ArrayList<Result>();
        this.c = mContext;
    }

    public void addItems(List<Result> newItems) {
        this.mData.addAll(newItems);
        notifyItemRangeInserted(this.mData.size(), newItems.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("tag", "onBindAdapter image pos = " + position + " url =" + imgUrl + mData.get(position).getName());
       // Picasso.with(c).load(imgUrl + mData.get(position).getName()).placeholder(R.mipmap.placeholder).into(holder.image);
        Glide
                .with(c)
                .load(imgUrl + mData.get(position).getName())
                .centerCrop()
                .placeholder(R.mipmap.placeholder)
                .crossFade()
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(c, ImageViewActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("id",imgUrl+mData.get(position).getName());
                c.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
