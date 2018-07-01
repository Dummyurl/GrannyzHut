package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.DetailsActivity;
import com.complexgene.eatbud.model.Item;
import com.complexgene.eatbud.model.MainItem;
import com.complexgene.eatbud.util.AppUtils;
import com.complexgene.eatbud.views.CircularImageView;
import com.complexgene.eatbud.views.ExpandableTextView;

import java.util.List;

import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_NON_VEG;
import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_VEG;

/**
 * Created by satyabrata on 13/6/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<Item> items;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvItemType;
        private ExpandableTextView tvItemDesc;
        private CircularImageView imvItem;

        public MyViewHolder(View view) {
            super(view);
            tvItemName =  view.findViewById(R.id.tvItemName);
            tvItemType =  view.findViewById(R.id.tvItemType);
            tvItemDesc =  view.findViewById(R.id.tvItemDesc);
            tvItemDesc.setInterpolator(new OvershootInterpolator());

            imvItem =  view.findViewById(R.id.imvItem);

        }
    }

    public ItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ItemAdapter(Context mContext, List<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thali_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvItemName.setText(items.get(position).getItemName());
        holder.tvItemDesc.setText(items.get(position).getItemDescriptionLarge());

        Glide.with(mContext)
                .load(AppUtils.getDrawableImage(mContext,items.get(position).getItemImageName()
                        .replace(".jpg","")))
                .into(holder.imvItem);

        switch (items.get(position).getItemType()){
            case ITEM_TYPE_VEG:
                holder.tvItemName.setCompoundDrawables(ContextCompat.
                        getDrawable(mContext,R.drawable.ic_veg),null,null,null);
                break;
            case ITEM_TYPE_NON_VEG:
                holder.tvItemName.setCompoundDrawables(ContextCompat.
                        getDrawable(mContext,R.drawable.ic_non_veg),null,null,null);
                break;

        }

        holder.tvItemDesc.setOnClickListener(new View.OnClickListener() {
//            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View v) {
                holder.tvItemDesc.toggle();
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 5);
    }







}
