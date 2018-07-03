package com.complexgene.eatbud.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.OrderAdapter;
import com.complexgene.eatbud.model.OrderDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private OrderAdapter adapter;
    private List<OrderDetails> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener((v)-> onBackPressed());

        fab = findViewById(R.id.fab);
        fab.hide();

        recyclerView = findViewById(R.id.rv);
        recyclerView.setPadding(recyclerView.getPaddingLeft(), getToolbarHeight(this),
                recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());

        orders = new ArrayList<>();
        orders = getDummyUserOrders();

        adapter = new OrderAdapter(this, orders);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    toolbar.animate().translationY(-getToolbarHeight(OrderListActivity.this)).setInterpolator(new AccelerateInterpolator(2)).start();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
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

    private List<OrderDetails> getDummyUserOrders() {
        List<OrderDetails> userOrders = new ArrayList<>();
        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId("Order#1");
        orderDetails1.setDate("21-July-18 10:30");
        orderDetails1.setOrderStatus("On The Way");

        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setOrderId("Order#2");
        orderDetails2.setDate("22-July-18 08:20");
        orderDetails2.setOrderStatus("Delivered");

        OrderDetails orderDetails3 = new OrderDetails();
        orderDetails3.setOrderId("Order#3");
        orderDetails3.setDate("23-July-18 09:30");
        orderDetails3.setOrderStatus("Cancelled");

        OrderDetails orderDetails4 = new OrderDetails();
        orderDetails4.setOrderId("Order#4");
        orderDetails4.setDate("24-July-18 14:12");
        orderDetails4.setOrderStatus("Confirmed");

        OrderDetails orderDetails5 = new OrderDetails();
        orderDetails5.setOrderId("Order#5");
        orderDetails5.setDate("24-July-18 19:30");
        orderDetails5.setOrderStatus("To Be Confirmed");

        OrderDetails orderDetails6 = new OrderDetails();
        orderDetails6.setOrderId("Order#6");
        orderDetails6.setDate("25-July-18 16:22");
        orderDetails6.setOrderStatus("Delivered");

        userOrders.addAll(Arrays.asList(orderDetails1,orderDetails2,orderDetails3,orderDetails4,orderDetails5,orderDetails6));
        return userOrders;
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
