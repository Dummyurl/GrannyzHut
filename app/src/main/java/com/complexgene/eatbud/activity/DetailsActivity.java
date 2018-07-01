package com.complexgene.eatbud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.ItemAdapter;
import com.complexgene.eatbud.base.AppClass;
import com.complexgene.eatbud.model.Cart;
import com.complexgene.eatbud.model.MainItem;
import com.complexgene.eatbud.util.AppConstant;
import com.complexgene.eatbud.util.AppUtils;

import java.util.LinkedHashMap;

import static com.complexgene.eatbud.util.AppConstant.ITEM_TAG_THALI;
import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_NON_VEG;
import static com.complexgene.eatbud.util.AppConstant.ITEM_TYPE_VEG;


/**
 * Created by satyabrata on 13/6/18.
 */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,
        AppBarLayout.OnOffsetChangedListener{

    private RecyclerView rvItems;

    private TextView tvStandardPrice,tvQtyStandard,tvSmallPrice,tvQtySmall;
    private Button btnAddStandard,btnAddSmall;



    private static final int PERCENTAGE_TO_SHOW_IMAGE = 40;
    private View fab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;

    private MainItem mainItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mainItem= (MainItem) getIntent().getSerializableExtra("MAIN_ITEM");
        System.out.println("mainItem:"+mainItem);
        if(mainItem==null) onBackPressed();

        init();





//        AppBarLayout appbar =  findViewById(R.id.appbar);
//        appbar.addOnOffsetChangedListener(this);

//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                AppUtils.measuredDen(this,40));
//
//        findViewById(R.id.llExtra).setLayoutParams(parms);

//        fab = findViewById(R.id.fab);

//        findViewById(R.id.btnOrder).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(DetailsActivity.this, CartActivity.class));
//            }
//        });



    }

    private  void init(){
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v->onBackPressed());

        TextView tvName =  findViewById(R.id.tvName);
        TextView tvItemDesc =  findViewById(R.id.tvItemDesc);
        TextView tvStandardPrice =  findViewById(R.id.tvStandardPrice);
        tvQtyStandard =  findViewById(R.id.tvQtyStandard);
        TextView tvSmallPrice =  findViewById(R.id.tvSmallPrice);
        tvQtySmall =  findViewById(R.id.tvQtySmall);


        ImageView imvItem = findViewById(R.id.imvItem);
        ImageView imvMStandard = findViewById(R.id.imvMStandard);
        imvMStandard.setOnClickListener(this);
        ImageView imvPStandard = findViewById(R.id.imvPStandard);
        imvPStandard.setOnClickListener(this);
        ImageView imvMSmall = findViewById(R.id.imvMSmall);
        imvMSmall.setOnClickListener(this);
        ImageView imvPSmall = findViewById(R.id.imvPSmall);
        imvPSmall.setOnClickListener(this);

        btnAddStandard = findViewById(R.id.btnAddStandard);
        btnAddStandard.setOnClickListener(this);
        btnAddSmall = findViewById(R.id.btnAddSmall);
        btnAddSmall.setOnClickListener(this);

        RelativeLayout rlSmall = findViewById(R.id.rlSmall);

        rvItems=findViewById(R.id.rvItems);

        tvName.setText(mainItem.getMainItemName());
        tvItemDesc.setText(mainItem.getMainItemDescriptionLarge());

        Glide.with(this)
                .load(AppUtils.getDrawableImage(this,mainItem.getMainItemImageName()
                        .replace(".jpg","")))
                .into(imvItem);


        switch (mainItem.getMainItemType()){
            case ITEM_TYPE_VEG:
                tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg,0,0,0);
                break;
            case ITEM_TYPE_NON_VEG:
                tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg,0,0,0);
                break;

        }

        tvQtyStandard.setText(""+mainItem.getQtyStandard());
        tvStandardPrice.setText("₹ "+mainItem.getQuantityPriceDetails().get("standard"));

        if(mainItem.getQtyStandard()>0){
            btnAddStandard.setVisibility(View.GONE);
        }

        if(mainItem.getQuantityPriceDetails().size()>1){
            rlSmall.setVisibility(View.VISIBLE);

            tvQtySmall.setText(""+mainItem.getQtySmall());
            tvSmallPrice.setText("₹ "+mainItem.getQuantityPriceDetails().get("small"));

            if(mainItem.getQtySmall()>0){
                btnAddSmall.setVisibility(View.GONE);
            }
        }

        if(mainItem.getMainItemTag()==ITEM_TAG_THALI){
            rvItems.setAdapter(new ItemAdapter(this,mainItem.getItems()));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddStandard:
                mainItem.setQtyStandard(1);
                tvQtyStandard.setText(String.valueOf(mainItem.getQtyStandard()));
                btnAddStandard.setVisibility(View.GONE);
                break;
            case R.id.imvMStandard:
                if(mainItem.getQtyStandard()>0){
                    mainItem.setQtyStandard(mainItem.getQtyStandard()-1);
                    tvQtyStandard.setText(String.valueOf(mainItem.getQtyStandard()));

//                    updateCartStandard(position);
                }else {
                    btnAddStandard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.imvPStandard:
                if(mainItem.getQtyStandard()<99){
                    mainItem.setQtyStandard(mainItem.getQtyStandard()+1);
                    tvQtyStandard.setText(String.valueOf(mainItem.getQtyStandard()));

//                    updateCartStandard(position);
                }
                break;
            case R.id.btnAddSmall:
                mainItem.setQtySmall(1);
                tvQtySmall.setText(String.valueOf(mainItem.getQtySmall()));
                btnAddSmall.setVisibility(View.GONE);

//                updateCartSmall(position);
                break;
            case R.id.imvMSmall:
                if(mainItem.getQtySmall()>0){
                    mainItem.setQtySmall(mainItem.getQtySmall()-1);
                    tvQtySmall.setText(String.valueOf(mainItem.getQtySmall()));

//                    updateCartSmall(position);
                }else {
                    btnAddStandard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.imvPSmall:
                if(mainItem.getQtySmall()<99){
                    mainItem.setQtySmall(mainItem.getQtySmall()+1);
                    tvQtySmall.setText(String.valueOf(mainItem.getQtySmall()));

//                    updateCartSmall(position);
                }
                break;
        }

    }


    private void updateCartStandard(int position){
        updateCart("",
                mainItem.getMainItemName(),
                mainItem.getMainItemImageName(),
                mainItem.getMainItemType(),
                mainItem.getQuantityPriceDetails().get("standard"),
                mainItem.getQtyStandard());
    }


    private void updateCartSmall(int position){
        updateCart("",
                mainItem.getMainItemName(),
                mainItem.getMainItemImageName(),
                mainItem.getMainItemType(),
                mainItem.getQuantityPriceDetails().get("small"),
                mainItem.getQtySmall());
    }


    private void updateCart(String key, String name, String img, String type, Double price, int qty){
        LinkedHashMap<String,Cart> cartMap= AppClass.getInstance().cart;
        if(cartMap==null){
            cartMap=new LinkedHashMap<>();
            cartMap.put(key,new Cart(name,img,type,price,qty));
        }else {
            if(cartMap.containsKey(key)){
                Cart cart=cartMap.get(key);
                cart.setQty(cart.getQty()+qty);
                cart.setPrice(cart.getPrice()+price);
            }
        }
    }




    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                ViewCompat.animate(fab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(fab).scaleY(1).scaleX(1).start();
            }
        }
    }


}
