package com.complexgene.eatbud.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.CartAdapter;
import com.complexgene.eatbud.model.Address;
import com.complexgene.eatbud.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by satyabrata on 13/6/18.
 */

public class CartActivity extends AppCompatActivity {

    private TextView tvAddress;
    private Button btnCheckOut;
    //#------OrderDetailsElements--------#



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

        btnCheckOut = findViewById(R.id.btnCheckOut);

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

        Intent intentOrderConfirmation = new Intent(this, OrderConfirmationActivity.class);
        Intent intentErrorInCreateOrder = new Intent(this, OrderConfirmationActivity.class);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createOrder())
                    startActivity(intentOrderConfirmation);
                else
                    startActivity(intentErrorInCreateOrder);
            }
        });
    }

    private boolean createOrder() {return true;
//        AddressDetails addressDetails = new AddressDetails();
//        addressDetails.setHouseNumber();
//        OrderDetails user = new User();
//        user.setUserName(name);
//        user.setMobileNumber(mobileNumber);
//        user.setEmailId(emailAddress);
//        user.setPassWord(password);
//        user.setRewardPoints(0);
//
//        ApiClient.getClient(this).create(ApiInterface.class).createUser(user)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
//                            if(response.code() == 200){
//                                Log.i("ProfileActivity","Registered..");
//                                startActivity(mainActivityIntent);
//                            }
//                        } catch (Exception e) {
//                            Log.i("ProfileActivity","Not Registered..");
//                            System.out.println("Not Registered.." + e);
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {}
//                });
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


