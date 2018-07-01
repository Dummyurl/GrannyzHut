package com.complexgene.eatbud.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.OrderAdapter;
import com.complexgene.eatbud.model.Album;
import com.complexgene.eatbud.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private OrderAdapter adapter;
    private List<Order> orders;

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


//        initCollapsingToolbar();


        fab = findViewById(R.id.fab);
        fab.hide();

        recyclerView = findViewById(R.id.rv);
        recyclerView.setPadding(recyclerView.getPaddingLeft(), getToolbarHeight(this),
                recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());

        orders = new ArrayList<>();
        adapter = new OrderAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
//                    fab.hide();
                    toolbar.animate().translationY(-getToolbarHeight(OrderListActivity.this)).setInterpolator(new AccelerateInterpolator(2)).start();
//                    toolbar.setVisibility(View.GONE);
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.hide();
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                    toolbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
//                        fab.show();
//                        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        break;
                }
            }
        });

    }


    public static int getToolbarHeight(Context context) {
        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public void showDialog(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }



}
