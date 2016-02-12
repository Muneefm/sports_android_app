package com.mnf.sports.Adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnf.sports.Models.EventModel.Result;
import com.mnf.sports.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muneef on 13/02/16.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>  {

List<Result> mDataset;
Context c;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View viewi;
        TextView evName,status,cls,winnerOne,winnerTwo,winnerThree;
        RelativeLayout containerMain,secondContainer;
        View pos1,pos2,pos3,winView;
        CardView cardView;
        // public final TextView dateR;
        public ViewHolder(View v) {
            super(v);
            viewi = v;
            evName = (TextView) v.findViewById(R.id.eventName);
            status = (TextView) v.findViewById(R.id.statusEv);
            winnerOne = (TextView) v.findViewById(R.id.winnerOne);
            winnerTwo = (TextView) v.findViewById(R.id.winnerTwo);
            winnerThree = (TextView) v.findViewById(R.id.winnerThree);
            containerMain = (RelativeLayout) v.findViewById(R.id.contCards);
            secondContainer = (RelativeLayout) v.findViewById(R.id.winnersCards);
            pos1 = (View) v.findViewById(R.id.pos1);
            pos2 = (View) v.findViewById(R.id.pos2);
            pos3 = (View) v.findViewById(R.id.pos3);
            winView = (View) v.findViewById(R.id.winView);
            cardView = (CardView) v.findViewById(R.id.orderCa);
        }
    }


    public EventListAdapter(Context mContext) {
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
                .inflate(R.layout.order_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    if(mDataset.get(position).getName()!=null){
        holder.evName.setText(mDataset.get(position).getName().toString());
    }
        if(mDataset.get(position).getOver()!=null){
            if(mDataset.get(position).getOver().toString().equals("1")){
                holder.status.setText("Finished");

                holder.winView.setBackgroundColor(getColorGroup(mDataset.get(position).getWinnerone().getGroup().toString()));
                if(mDataset.get(position).getWinnerone().getName()!=null){
                    holder.winnerOne.setText(mDataset.get(position).getWinnerone().getName().toString());
                    holder.pos1.setBackgroundColor(getColorGroup(mDataset.get(position).getWinnerone().getGroup().toString()));
                }
                if(mDataset.get(position).getWinnertwo().getName()!=null){
                    holder.winnerOne.setText(mDataset.get(position).getWinnertwo().getName().toString());
                    holder.pos2.setBackgroundColor(getColorGroup(mDataset.get(position).getWinnerone().getGroup().toString()));

                }
                if(mDataset.get(position).getWinnerthree().getName()!=null){
                    holder.winnerOne.setText(mDataset.get(position).getWinnerthree().getName().toString());
                    holder.pos3.setBackgroundColor(getColorGroup(mDataset.get(position).getWinnerone().getGroup().toString()));
                }





            }else if(mDataset.get(position).getOver().toString().equals("0")){
                holder.status.setText("Upcoming Event");
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.secondContainer.getVisibility() == View.GONE) {
                    holder.secondContainer.setVisibility(View.VISIBLE);
                    final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    holder.secondContainer.measure(widthSpec, heightSpec);
                    ValueAnimator mAnimator = slideAnimator(0, holder.secondContainer.getMeasuredHeight(), holder.secondContainer);
                    mAnimator.start();

                } else {
                    int finalHeight = holder.secondContainer.getHeight();

                    ValueAnimator mAnimator = slideAnimator(finalHeight, 0, holder.secondContainer);
                    mAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            holder.secondContainer.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    mAnimator.start();


                }

            }
        });




    }



    private ValueAnimator slideAnimator(int start, int end, RelativeLayout rltt) {

        final RelativeLayout ltt = rltt;
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ltt.getLayoutParams();
                layoutParams.height = value;
                ltt.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }




    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public int getColorGroup(String grp){
        grp = grp.toLowerCase();
        switch (grp){
            case "b":
                return c.getResources().getColor(R.color.blue500);
            case "g":
                return c.getResources().getColor(R.color.green_500);
            case "y":
                return c.getResources().getColor(R.color.yellow_500);
            case "r":
                return c.getResources().getColor(R.color.red500);
            default:
                return c.getResources().getColor(R.color.white);
        }

    }

}
