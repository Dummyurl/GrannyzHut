package com.complexgene.eatbud.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.adapter.FoodAdapter;
import com.complexgene.eatbud.model.FoodItem;
import com.complexgene.eatbud.model.MainItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pasari on 19/5/17.
 */

public class LunchFragment extends Fragment implements FoodAdapter.OnItemChangeListener{

    private RecyclerView rv;
    List<String> messageArrayList;

    private List<MainItem> lunchList;

    public LunchFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public LunchFragment(List<MainItem> lunchList) {
        this.lunchList=lunchList;
        System.out.println("lunchList:"+lunchList.size());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        rv =  view.findViewById(R.id.rv);


        FoodAdapter adapter = new FoodAdapter(getActivity(), lunchList,this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);

        return view;
    }



    @Override
    public void onItemChanged(String key,String name, String img, String type, Double price, int Qty) {

    }
}