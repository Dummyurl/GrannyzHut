package com.complexgene.eatbud.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.FoodAdapter;
import com.complexgene.eatbud.adapter.SpinnerAdapter;
import com.complexgene.eatbud.adapter.ViewPagerAdapter;
import com.complexgene.eatbud.base.AppClass;
import com.complexgene.eatbud.fragment.DinnerFragment;
import com.complexgene.eatbud.fragment.LunchFragment;
import com.complexgene.eatbud.model.Cart;
import com.complexgene.eatbud.model.Meal;
import com.complexgene.eatbud.model.MenuResponse;
import com.complexgene.eatbud.network.ApiClient;
import com.complexgene.eatbud.network.ApiInterface;
import com.complexgene.eatbud.util.AppConstant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        TabLayout.OnTabSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,FoodAdapter.OnItemChangeListener {

    private Spinner spinner;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;
    private List<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setupNavigationDrawer();
        setUpToolbarSpinner();


        findViewById(R.id.btnOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });


        getItemsFromServer();

//        updateView();

    }

    private void updateView(){
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);

        ViewPager viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpToolbarSpinner(){
        spinner=findViewById(R.id.spinner);
//        spinner.setAdapter(new );
        List<String> mItems=new ArrayList<>();
        mItems.add("Saturday 23/06");
        mItems.add("Sunday 24/06");

        SpinnerAdapter adapter=new SpinnerAdapter(this,mItems);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("position:"+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        spinner.setOnItemSelectedListener(this);
    }

    private void setupNavigationDrawer(){
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                0, 0);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        for(Meal meal: meals){
//            switch (meal.getMealType()){
//                case AppConstant.MEAL_TYPE_LUNCH:
//                    adapter.addFrag(new LunchFragment(meals.get(0).getMainItemList()), LunchFragment.class.getSimpleName());
//                    break;
//                case AppConstant.MEAL_TYPE_DINNER:
//                    adapter.addFrag(new DinnerFragment(meals.get(0).getMainItemList()), LunchFragment.class.getSimpleName());
//                    break;
//            }
//        }

        for(int i=0;i<meals.size();i++){
            switch (i){
                case 0:
                    adapter.addFrag(new LunchFragment(meals.get(0).getMainItemList()), LunchFragment.class.getSimpleName());
                    break;
                case 1:
                    adapter.addFrag(new DinnerFragment(meals.get(0).getMainItemList()), DinnerFragment.class.getSimpleName());
                    break;
            }
        }

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()){
                    case R.id.nav_order:
                        startActivity(new Intent(MainActivity.this, OrderListActivity.class));
                        break;
                    case R.id.nav_address:
//                        startActivity(new Intent(MainActivity.this, AddressListActivity.class));
                        break;
                    case R.id.nav_award:
                        break;
                    case R.id.nav_review:
                        Toast.makeText(MainActivity.this, "Comming soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_refer:
                        break;
                }
            }
        },300);

        return false;
    }

    @Override
    public void onItemChanged(String key, String name, String img, String type, Double price, int qty) {
        LinkedHashMap<String,Cart> cartMap=AppClass.getInstance().cart;
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


    private void getItemsFromServer() {
//        CustomLoader.getInstance(this).show();
        ApiClient.getClient(this).create(ApiInterface.class).getMenuList("07-07-2018")
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//               CustomLoader.dismissLoader();
                try {
                    if(response.code()==200){
                        String responseBody=response.body()==null?"":response.body().string();
                        System.out.println("response:"+responseBody);
                        JSONObject obj=new JSONObject(responseBody);
                        MenuResponse menuResponse=new Gson().fromJson(obj.optString("responseObject"),MenuResponse.class);
                        if(menuResponse.getMeals()!=null){
                            if(menuResponse.getMeals().size()>0){
                                meals=menuResponse.getMeals();
                                updateView();

                            }else {

                            }
                        }else {

                        }
                    }
                } catch (Exception e) {
                    System.out.println("getItemsFromServer Exception:" + e);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        CustomLoader.dismissLoader();
            }
        });
    }


}
