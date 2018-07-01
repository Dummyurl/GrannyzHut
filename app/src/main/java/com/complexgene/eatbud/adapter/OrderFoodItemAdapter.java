package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.FeedbackActivity;
import com.complexgene.eatbud.model.Order;

import java.util.List;

/**
 * Created by satyabrata on 13/6/18.
 */

public class OrderFoodItemAdapter extends RecyclerView.Adapter<OrderFoodItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<Order> orders;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public OrderFoodItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public OrderFoodItemAdapter(Context mContext, List<Order> orders) {
        this.mContext = mContext;
        this.orders = orders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_order, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, FeedbackActivity.class));
//            }
//        });

    }




    @Override
    public int getItemCount() {
        return 3;
    }


}
