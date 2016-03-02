package com.mnf.sports.Adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        TextView evName,status,cls,winnerOne,winnerTwo,winnerThree,comingUp;
        RelativeLayout containerMain,secondContainer,winColorContainer;
        View pos1,pos2,pos3,winView;
        CardView cardView;
        LinearLayout winnerLinear;
        ImageView logoSport;
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
            winColorContainer = (RelativeLayout) v.findViewById(R.id.winColorContainer);
            winnerLinear = (LinearLayout) v.findViewById(R.id.winnerLinear);
            comingUp = (TextView) v.findViewById(R.id.comingUp);
            logoSport = (ImageView) v.findViewById(R.id.logoSport);
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

    public void removeAll() {
        this.mDataset.clear();
        notifyDataSetChanged();
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
    public void onBindViewHolder( ViewHolder holder, int position) {
        Log.e("TAG","position = "+position);
        holder.secondContainer.setVisibility(View.GONE);
        Result item = mDataset.get(position);
        holder.logoSport.setImageResource(getImageDraw(item.getCode().toString().toLowerCase()));
    if(item.getName()!=null){
        holder.evName.setText(item.getName().toString());
    }
        if(item.getOver()!=null){
            if(item.getOver().toString().equals("1")){
                Log.e("TAG","finished event name  = "+item.getName().toString()+" pos = "+position+"");
                holder.status.setText("Finished");
                holder.winnerLinear.setVisibility(View.VISIBLE);
                holder.winColorContainer.setVisibility(View.VISIBLE);
                holder.comingUp.setVisibility(View.GONE);

                   switch(item.getWinnerone().getGroup().toString()){
                        case "b":
                            holder.winView.setBackgroundResource(R.drawable.circle_blue);
                            //(c.getResources().getDrawable(R.drawable.circle_blue));
                            break;
                        case "g":
                            holder.winView.setBackgroundResource(R.drawable.circle_green);
                            break;
                        case "y":
                            holder.winView.setBackgroundResource(R.drawable.circle_yellow);
                            break;
                        case "r":
                            holder.winView.setBackgroundResource(R.drawable.circle_red);
                            break;
                        default:
                            holder.winView.setBackgroundResource(R.drawable.circle_yellow);
                            break;

                    }
               // holder.winView.setBackgroundColor(getColorGroup(item.getWinnerone().getGroup().toString()));
                if(item.getWinnerone().getName()!=null){
                    holder.winnerOne.setText("1. "+item.getWinnerone().getName().toString());
                    holder.pos1.setBackgroundColor(getColorGroup(item.getWinnerone().getGroup().toString()));
                }
                if(item.getWinnertwo().getName()!=null){
                    holder.winnerTwo.setText("2. "+item.getWinnertwo().getName().toString());
                    holder.pos2.setBackgroundColor(getColorGroup(item.getWinnertwo().getGroup().toString()));

                }
                if(item.getWinnerthree().getName()!=null){
                    holder.winnerThree.setText("3. "+item.getWinnerthree().getName().toString());
                    holder.pos3.setBackgroundColor(getColorGroup(item.getWinnerthree().getGroup().toString()));
                }





            }else if(item.getOver().toString().equals("0")){
                Log.e("TAG","not event name  = "+item.getName().toString()+" pos = "+position+"");

                holder.status.setText("Upcoming Event");
                holder.comingUp.setVisibility(View.VISIBLE);
                holder.winnerLinear.setVisibility(View.GONE);
                holder.winColorContainer.setVisibility(View.GONE);
            }
        }


        setUpAnimation(holder);

    }

public void setUpAnimation(final ViewHolder holder){
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


    public int getImageDraw(String id){
        switch (id){
            case "e1":
                return R.mipmap.ic_running;
            case "e2":
                return R.mipmap.ic_running;
            case "e3":
                return R.mipmap.ic_running;
            case "e4":
                return R.mipmap.ic_running;
            case "e5":
                return R.mipmap.ic_running;
            case "e6":
                return R.mipmap.ic_running;
            case "e7":
                return R.mipmap.ic_running;
            case "e8":
                return R.mipmap.ic_running;
            case "e9":
                return R.mipmap.ic_race;
            case "e10":
                return R.mipmap.ic_race;
            case "e11":
                return R.mipmap.ic_racex;
            case "e12":
                return R.mipmap.ic_racex;
            case "e13":
                return R.mipmap.ic_walk;
            case "e14":
                return R.mipmap.ic_walk;

            case "e15":
                return R.mipmap.ic_walk;

            case "e16":
                return R.mipmap.ic_shotput;
            case "e17":
                return R.mipmap.ic_shotput;
            case "e18":
                return R.mipmap.ic_disc;
            case "e19":
                return R.mipmap.ic_disc;
            case "e20":
                return R.mipmap.ic_javelin;
            case "e21":
                return R.mipmap.ic_javelin;
            case "e22":
                return R.mipmap.ic_hammer;
            case "e23":
                return R.mipmap.ic_hammer;
            case "e24":
                return R.mipmap.ic_jumb;
            case "e25":
                return R.mipmap.ic_jumb;
            case "e26":
                return R.mipmap.ic_jumb;
            case "e27":
                return R.mipmap.ic_jumb;
            case "e28":
                return R.mipmap.ic_race;
            case "e29":
                return R.mipmap.ic_militia;
            case "e30":
                return R.mipmap.ic_volley;
            case "e31":
                return R.mipmap.ic_cricket;
            case "e32":
                return R.mipmap.ic_pingpong;
            case "e33":
                return R.mipmap.ic_football;
            case "e34":
                return R.mipmap.ic_tennis;
            case "e35":
                return R.mipmap.ic_tennis;
            case "e36":
                return R.mipmap.ic_relay;
            case "e37":
                return R.mipmap.ic_relay;
            case "e38":
                return R.mipmap.ic_relay;
            case "e39":
                return R.mipmap.ic_relay;
            case "e40":
                return R.mipmap.ic_tennis;
            case "e41":
                return R.mipmap.ic_tennis;
            default:
                return R.mipmap.ic_running;



        }
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
