package com.complexgene.eatbud.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.AddressAdapter;
import com.complexgene.eatbud.adapter.CartAdapter;
import com.complexgene.eatbud.model.Address;
import com.complexgene.eatbud.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by satyabrata on 13/6/18.
 */

public class CartActivity extends AppCompatActivity {

    private TextView tvAddress;


    private static final int OPEN_ADDRESS_INTENT=101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView rvCart=findViewById(R.id.rvCart);
        rvCart.setAdapter(new CartAdapter(this));
        rvCart.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        findViewById(R.id.llAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CartActivity.this,AddressListActivity.class),
                        OPEN_ADDRESS_INTENT);
            }
        });

        getDefaultAddress();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("@@@@@@@@@@@@ onStart");


    }

    private void getDefaultAddress(){
        String s=getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE)
                .getString(AppConstant.SAVED_ADDRESS,"");

        if(!s.isEmpty()){
            List<Address> addressList = new Gson()
                    .fromJson(s, new TypeToken<List<Address>>(){}.getType());

            for (Address address:addressList){
                System.out.println("@@@@@ isDefault:"+address.isDefault());
                if(address.isDefault()){
                    ((TextView)findViewById(R.id.tvAddress)).setText(address.getType());
                    break;
                }
            }

        }else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode:"+resultCode);
        switch (requestCode){
            case OPEN_ADDRESS_INTENT:
               getDefaultAddress();
                break;

        }
    }


}


