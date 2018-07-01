package com.complexgene.eatbud.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.AddressAdapter;
import com.complexgene.eatbud.model.Address;
import com.complexgene.eatbud.model.Album;
import com.complexgene.eatbud.util.AppConstant;
import com.complexgene.eatbud.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddressListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;


    private static final int OPEN_MAP_INTENT=111;
    private int clickedPosition=-1;
    private AddressAdapter adapter;
    private List<Address> addressList;
    private Map<String,Address> addressMap=new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setPadding(recyclerView.getPaddingLeft(), AppUtils.getToolbarHeight(this),
                recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());

//        adapter = new AddressAdapter(this, addressList=new ArrayList<>());
//        recyclerView.setAdapter(new AddressAdapter(this, addressList=new ArrayList<>()));

        prepareAddressList();


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
//                    fab.hide();
//                    toolbar.animate().translationY(-getToolbarHeight(AddressListActivity.this)).setInterpolator(new AccelerateInterpolator(2)).start();
//                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.show();
//                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                switch (newState){
//                    case RecyclerView.SCROLL_STATE_IDLE:
////                        fab.show();
////                        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                        break;
//                }
//            }
//        });


    }




    private void prepareAddressList() {

        String address=getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE)
                .getString(AppConstant.SAVED_ADDRESS,"");

        System.out.println("address:"+address);

        if(!address.isEmpty()){

            addressList = new Gson().fromJson(address, new TypeToken<List<Address>>(){}.getType());

            System.out.println("addressList:"+addressList);

        }else {
            addressList=new ArrayList<>();
            addressList.add(new Address("Home",getString(R.string.click_here_to_add_address),false));
            addressList.add(new Address("Office",getString(R.string.click_here_to_add_address),false));
            addressList.add(new Address("Other",getString(R.string.click_here_to_add_address),false));

            saveAddress();
        }
        recyclerView.setAdapter(new AddressAdapter(this, addressList));
//        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void saveAddress(){

        getSharedPreferences(AppConstant.PREF_NAME,Context.MODE_PRIVATE).edit()
                .putString(AppConstant.SAVED_ADDRESS,new Gson().toJson(addressList)).commit();

    }

    private void prepareAddressMap() {

        addressMap.put("Home",new Address("Home",getString(R.string.add_address),false));
        addressMap.put("Office",new Address("Office",getString(R.string.add_address),false));
        addressMap.put("Other",new Address("Other",getString(R.string.add_address),false));

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void openMap(int position){
        clickedPosition=position;
        startActivityForResult(new Intent(this,MapActivity.class)
                .putExtra("intentAddress", addressList.get(position).getAddress())
                .putExtra("intentLat",addressList.get(position).getLat())
                .putExtra("intentLon",addressList.get(position).getLon()),OPEN_MAP_INTENT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("resultCode:"+resultCode);

        switch (requestCode){
            case OPEN_MAP_INTENT:
                if(Activity.RESULT_OK==resultCode){
                    System.out.println("resultCode:"+data.getStringExtra("intentAddress"));
                    System.out.println("resultCode:"+data.getDoubleExtra("intentLat",0.0));

                    addressList.get(clickedPosition).setAddress(data.getStringExtra("intentAddress"));
                    addressList.get(clickedPosition).setLat(data.getDoubleExtra("intentLat",0.0));
                    addressList.get(clickedPosition).setLon(data.getDoubleExtra("intentLon",0.0));
                    addressList.get(clickedPosition).setDefault(false);

                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {

        saveAddress();

        setResult(Activity.RESULT_OK);

        finish();

//        super.onBackPressed();
    }
}
