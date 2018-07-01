package com.complexgene.eatbud.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.DetailsActivity;
import com.complexgene.eatbud.model.FoodItem;
import com.complexgene.eatbud.model.MainItem;
import com.complexgene.eatbud.util.AppUtils;

import java.io.Serializable;
import java.util.List;

import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_NON_VEG;
import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_VEG;

/**
 * Created by satyabrata on 13/6/18.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    private Context mContext;
    private List<MainItem> foodItems;
    private OnItemChangeListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvStandardPrice,tvQtyStandard,tvSmallPrice,tvQtySmall;
        private ImageView thumbnail, imvMStandard,imvPStandard,imvMSmall,imvPSmall;
        private Button btnAddStandard,btnAddSmall;
        private RelativeLayout rlSmall;

        public MyViewHolder(View view) {
            super(view);
            tvTitle =  view.findViewById(R.id.tvTitle);
            tvStandardPrice =  view.findViewById(R.id.tvStandardPrice);
            tvQtyStandard =  view.findViewById(R.id.tvQtyStandard);
            tvSmallPrice =  view.findViewById(R.id.tvSmallPrice);
            tvQtySmall =  view.findViewById(R.id.tvQtySmall);

            thumbnail =  view.findViewById(R.id.thumbnail);
            imvMStandard = view.findViewById(R.id.imvMStandard);
            imvPStandard = view.findViewById(R.id.imvPStandard);
            imvMSmall = view.findViewById(R.id.imvMSmall);
            imvPSmall = view.findViewById(R.id.imvPSmall);

            btnAddStandard = view.findViewById(R.id.btnAddStandard);
            btnAddSmall = view.findViewById(R.id.btnAddSmall);

            rlSmall = view.findViewById(R.id.rlSmall);
        }
    }

    public FoodAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public FoodAdapter(Context mContext, List<MainItem> foodItems) {
        this.mContext = mContext;
        this.foodItems = foodItems;
        this.listener= (OnItemChangeListener) mContext;
    }

    public FoodAdapter(Context mContext, List<MainItem> foodItems,OnItemChangeListener listener) {
        this.mContext = mContext;
        this.foodItems = foodItems;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(foodItems.get(position).getMainItemName());

        switch (foodItems.get(position).getMainItemType()){
            case ITEM_TYPE_VEG:
                holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg,0,0,0);
                break;
            case ITEM_TYPE_NON_VEG:
                holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg,0,0,0);
                break;
        }

        Glide.with(mContext)
                .load(AppUtils.getDrawableImage(mContext,foodItems.get(position).getMainItemImageName()
                        .replace(".jpg","")))
                .into(holder.thumbnail);

        holder.tvQtyStandard.setText(""+foodItems.get(position).getQtyStandard());
        holder.tvStandardPrice.setText("₹ "+foodItems.get(position).getQuantityPriceDetails().get("standard"));

        if(foodItems.get(position).getQtyStandard()>0){
            holder.btnAddStandard.setVisibility(View.GONE);
        }

        if(foodItems.get(position).getQuantityPriceDetails().size()>1){
            holder.rlSmall.setVisibility(View.VISIBLE);

            holder.tvQtySmall.setText(""+foodItems.get(position).getQtySmall());
            holder.tvSmallPrice.setText("₹ "+foodItems.get(position).getQuantityPriceDetails().get("small"));

            if(foodItems.get(position).getQtySmall()>0){
                holder.btnAddSmall.setVisibility(View.GONE);
            }

        }

        holder.itemView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, DetailsActivity.class)
                    .putExtra("MAIN_ITEM",  foodItems.get(position)));
        });

        holder.btnAddStandard.setOnClickListener(view -> {
            foodItems.get(position).setQtyStandard(1);
            holder.tvQtyStandard.setText(String.valueOf(foodItems.get(position).getQtyStandard()));
            holder.btnAddStandard.setVisibility(View.GONE);

           updateCartStandard(position);
        });

        holder.imvMStandard.setOnClickListener(view -> {
            if(foodItems.get(position).getQtyStandard()>0){
                foodItems.get(position).setQtyStandard(foodItems.get(position).getQtyStandard()-1);
                holder.tvQtyStandard.setText(String.valueOf(foodItems.get(position).getQtyStandard()));

                updateCartStandard(position);
            }else {
                holder.btnAddStandard.setVisibility(View.VISIBLE);
            }
        });

        holder.imvPStandard.setOnClickListener(view -> {
            if(foodItems.get(position).getQtyStandard()<99){
                foodItems.get(position).setQtyStandard(foodItems.get(position).getQtyStandard()+1);
                holder.tvQtyStandard.setText(String.valueOf(foodItems.get(position).getQtyStandard()));

                updateCartStandard(position);
            }
        });

        holder.btnAddSmall.setOnClickListener(view -> {
            foodItems.get(position).setQtySmall(1);
            holder.tvQtySmall.setText(String.valueOf(foodItems.get(position).getQtySmall()));
            holder.btnAddSmall.setVisibility(View.GONE);

            updateCartSmall(position);
        });

        holder.imvMSmall.setOnClickListener(view -> {
            if(foodItems.get(position).getQtySmall()>0){
                foodItems.get(position).setQtySmall(foodItems.get(position).getQtySmall()-1);
                holder.tvQtySmall.setText(String.valueOf(foodItems.get(position).getQtySmall()));

                updateCartSmall(position);
            }else {
                holder.btnAddStandard.setVisibility(View.VISIBLE);
            }
        });

        holder.imvPSmall.setOnClickListener(view -> {
            if(foodItems.get(position).getQtySmall()<99){
                foodItems.get(position).setQtySmall(foodItems.get(position).getQtySmall()+1);
                holder.tvQtySmall.setText(String.valueOf(foodItems.get(position).getQtySmall()));

                updateCartSmall(position);
            }
        });






//        holder.imvQtyM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Integer.parseInt(holder.tvQty.getText().toString())>0){
//                    holder.tvQty.setText(String.valueOf(Integer.parseInt(holder.tvQty.getText().toString())-1));
//                }
//            }
//        });

//        holder.imvQtyP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Integer.parseInt(holder.tvQty.getText().toString())<99){
//                    holder.tvQty.setText(String.valueOf(Integer.parseInt(holder.tvQty.getText().toString())+1));
//                }
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return (null != foodItems ? foodItems.size() : 10);
    }


    private void updateCartStandard(int position){
        listener.onItemChanged("",
                foodItems.get(position).getMainItemName(),
                foodItems.get(position).getMainItemImageName(),
                foodItems.get(position).getMainItemType(),
                foodItems.get(position).getQuantityPriceDetails().get("standard"),
                foodItems.get(position).getQtyStandard());
    }

    private void updateCartSmall(int position){
        listener.onItemChanged("",
                foodItems.get(position).getMainItemName(),
                foodItems.get(position).getMainItemImageName(),
                foodItems.get(position).getMainItemType(),
                foodItems.get(position).getQuantityPriceDetails().get("small"),
                foodItems.get(position).getQtySmall());
    }


    public interface OnItemChangeListener{
        public void onItemChanged(String key,String name,String img,String type,Double price,int Qty);
    }


}
