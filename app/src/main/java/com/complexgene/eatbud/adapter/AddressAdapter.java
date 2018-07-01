package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.AddressListActivity;
import com.complexgene.eatbud.model.Address;

import java.util.List;
import java.util.Map;

/**
 * Created by satyabrata on 13/6/18.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context mContext;
    private Map<String,Address> addressMap;
    private List<Address> addressList;
    int[] icons = new int[]{
            R.drawable.ic_home,
            R.drawable.ic_office,
            R.drawable.ic_pin_drop,};



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tvAddress;
        private ImageView imvType,imvDefault;

        public MyViewHolder(View view) {
            super(view);
            tvType = view.findViewById(R.id.tvType);
            tvAddress = view.findViewById(R.id.tvAddress);
            imvType = view.findViewById(R.id.imvType);
            imvDefault = view.findViewById(R.id.imvDefault);
//            imvAdd = view.findViewById(R.id.imvAdd);
        }
    }


    public AddressAdapter(Context mContext, List<Address> addressList) {
        this.mContext = mContext;
        this.addressList = addressList;
    }

    public AddressAdapter(Context mContext, Map<String,Address> addressMap) {
        this.mContext = mContext;
        this.addressMap = addressMap;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvType.setText(addressList.get(position).getType());
        holder.imvType.setImageDrawable(ContextCompat.getDrawable(mContext,icons[position]));

        if(addressList.get(position).getAddress()!=null){
            if(!addressList.get(position).getAddress().isEmpty()){

//                holder.imvAdd.setVisibility(View.GONE);
                holder.tvAddress.setText(addressList.get(position).getAddress());
            }else {
//                holder.imvAdd.setVisibility(View.VISIBLE);
                holder.tvAddress.setText(mContext.getString(R.string.click_here_to_add_address));
            }
        }else {
//            holder.imvAdd.setVisibility(View.VISIBLE);
            holder.tvAddress.setText(mContext.getString(R.string.click_here_to_add_address));
        }

        if(addressList.get(position).isDefault()){

                holder.imvDefault.setColorFilter(ContextCompat.getColor(mContext, R.color.green),
                        android.graphics.PorterDuff.Mode.SRC_IN);

        }else {
            holder.imvDefault.setColorFilter(ContextCompat.getColor(mContext, R.color.drawable_color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddressListActivity)mContext).openMap(position);
//                mContext.startActivity(new Intent(mContext, MapActivity.class));
            }
        });


        holder.imvDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.compare(addressList.get(position).getLat(),0.0) !=0 ||
                        Double.compare(addressList.get(position).getLon(),0.0)!=0) {

                    for (int i = 0; i <addressList.size() ; i++) {
                        if(i==position){
                            addressList.get(i).setDefault(true);
                        }else {
                            addressList.get(i).setDefault(false);
                        }
                    }
                    notifyDataSetChanged();

                }else {
                    Toast.makeText(mContext, mContext.getString(R.string.pls_add_address), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    @Override
    public int getItemCount() {
        return addressList.size();
//        return addressMap.size();

    }







}
