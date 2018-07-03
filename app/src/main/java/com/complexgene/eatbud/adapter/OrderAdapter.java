package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.OrderListActivity;
import com.complexgene.eatbud.model.OrderDetails;

import java.util.List;

/**
 * Created by satyabrata on 13/6/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderDetails> orders;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public OrderAdapter(Context mContext, List<OrderDetails> orders) {
        this.mContext = mContext;
        this.orders = orders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        OrderDetails orderDetails = orders.get(position);

        holder.tvOrderId.setText(orderDetails.getOrderId());
        holder.tvOrderId.setTypeface(Typeface.DEFAULT_BOLD);
        holder.tvOrderTimeStamp.setText(orderDetails.getDate());
        holder.tvFeedback.setOnClickListener((view)-> ((OrderListActivity)mContext).showDialog());
        switch(orderDetails.getOrderStatus().toUpperCase()) {
            case "DELIVERED": {holder.tvOrderStatus.setText("Delivered");holder.tvOrderStatus.setTextColor(Color.GREEN);break;}
            case "ON THE WAY":{holder.tvOrderStatus.setText("On The Way");holder.tvOrderStatus.setTextColor(Color.YELLOW);break;}
            case "CANCELLED":{holder.tvOrderStatus.setText("Cancelled");holder.tvOrderStatus.setTextColor(Color.RED);break;}
            case "TO BE CONFIRMED":{holder.tvOrderStatus.setText("To Be Confirmed");holder.tvOrderStatus.setTextColor(Color.CYAN);break;}
            case "CONFIRMED":{holder.tvOrderStatus.setText("Confirmed");holder.tvOrderStatus.setTextColor(Color.MAGENTA);break;}
        }
        //holder.tvItemCount.setText(0);
        //holder.tvTotalPrice.setText("0.0");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderId, tvOrderTimeStamp, tvFeedback, tvOrderStatus, tvItemCount, tvTotalPrice;
        public RecyclerView rvOrderedItems;

        public MyViewHolder(View view) {
            super(view);
            tvOrderId = view.findViewById(R.id.tvOrderId);
            tvOrderTimeStamp = view.findViewById(R.id.tvOrderTimeStamp);
            tvFeedback = view.findViewById(R.id.tvFeedback);
            tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
            tvItemCount = view.findViewById(R.id.tvItemCount);
            tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
            rvOrderedItems = view.findViewById(R.id.rvOrderedItems);
        }
    }

}
