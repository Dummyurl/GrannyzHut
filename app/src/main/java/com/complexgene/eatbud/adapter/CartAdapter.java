package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.AddressListActivity;
import com.complexgene.eatbud.model.Address;

import java.util.List;
import java.util.Map;

/**
 * Created by satyabrata on 13/6/18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<Address> addressList;
    int[] icons = new int[]{
            R.drawable.ic_home,
            R.drawable.ic_office,
            R.drawable.ic_pin_drop,};



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tvAddress;
        private ImageView imvType,imvDefault,imvAdd;

        public MyViewHolder(View view) {
            super(view);
            tvType = view.findViewById(R.id.tvType);
            tvAddress = view.findViewById(R.id.tvAddress);
            imvType = view.findViewById(R.id.imvType);
            imvDefault = view.findViewById(R.id.imvDefault);
            imvAdd = view.findViewById(R.id.imvAdd);
        }
    }


    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CartAdapter(Context mContext, List<Address> addressList) {
        this.mContext = mContext;
        this.addressList = addressList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       /* holder.tvType.setText(addressList.get(position).getType());
        holder.imvType.setImageDrawable(ContextCompat.getDrawable(mContext,icons[position]));

        if(addressList.get(position).getAddress()!=null){
            if(!addressList.get(position).getAddress().isEmpty()){

                holder.imvAdd.setVisibility(View.GONE);
                holder.tvAddress.setText(addressList.get(position).getAddress());
            }else {
                holder.imvAdd.setVisibility(View.VISIBLE);
                holder.tvAddress.setText(addressList.get(position).getAddress());
            }
        }else {
            holder.imvAdd.setVisibility(View.VISIBLE);
            holder.tvAddress.setText("");
        }


        if(addressList.get(position).isDefault()){
            holder.imvDefault.setColorFilter(ContextCompat.getColor(mContext, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        }else {
            holder.imvDefault.setColorFilter(ContextCompat.getColor(mContext, R.color.drawable_color), android.graphics.PorterDuff.Mode.SRC_IN);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AddressListActivity)mContext).openMap(position);
//                mContext.startActivity(new Intent(mContext, MapActivity.class));
            }
        });*/

    }




    @Override
    public int getItemCount() {
//        return addressList.size();
        return 5;
    }







}
