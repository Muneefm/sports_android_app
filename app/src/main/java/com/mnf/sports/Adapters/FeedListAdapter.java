package com.mnf.sports.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnf.sports.Config;
import com.mnf.sports.Models.Feeds.Result;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muneef on 18/02/16.
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
        String ImageUrl = Config.BASE_URL+Config.IMAGE_FEED_URL;

    Context c;
    List<Result> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View viewi;
        TextView mainString,subString,authorFeed,winnerOne,winnerTwo,winnerThree,comingUp;
        RelativeLayout containerMain,secondContainer,winColorContainer;
        CardView cardView;
        ImageView feedLogo;
        // public final TextView dateR;
        public ViewHolder(View v) {
            super(v);
            viewi = v;
            mainString = (TextView) v.findViewById(R.id.mainString);
            subString = (TextView) v.findViewById(R.id.subString);
            authorFeed = (TextView) v.findViewById(R.id.authorFeed);


            cardView = (CardView) v.findViewById(R.id.cardFeedItem);

            feedLogo = (ImageView) v.findViewById(R.id.feedImg);
        }
    }

    public FeedListAdapter(Context mContext) {
        this.c = mContext;
        this.mData = new ArrayList<Result>();
    }
    public void addItems(List<Result> newItems) {
        this.mData.addAll(newItems);
        notifyItemRangeInserted(this.mData.size(), newItems.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    Result item = mData.get(position);
        holder.feedLogo.setVisibility(View.VISIBLE);
        if(item.getMainstring()!=null){
            holder.mainString.setText(item.getMainstring().toString());
        }
        if(item.getSubstring()!=null){
           if(!item.getSubstring().equals("")){
               holder.subString.setText(item.getSubstring());
           }
        }
        if(item.getAuthor()!=null){
            if(!item.getAuthor().equals("")){
                holder.authorFeed.setText(item.getAuthor());
            }
        }

        if(item.getImage()!=null){
            if(!item.getImage().equals("")){
                Picasso.with(c).load(ImageUrl + item.getImage()).into(holder.feedLogo);
            }else{
                holder.feedLogo.setVisibility(View.GONE);
            }
        }else{
            holder.feedLogo.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
