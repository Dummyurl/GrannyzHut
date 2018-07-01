package com.complexgene.eatbud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.activity.PagerActivity;

public class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ImageView img;

    int[] bgs = new int[]{R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
       PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
        TextView tvHeader = (TextView) rootView.findViewById(R.id.tvHeader);
        TextView tvLong = (TextView) rootView.findViewById(R.id.tvLong);
        img = (ImageView) rootView.findViewById(R.id.section_img);

        String[] headerArray = getResources().getStringArray(R.array.header_text);
        String[] longTextArray = getResources().getStringArray(R.array.long_text);



//        tvHeader.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

//        tvHeader.setText(headerArray[getArguments().getInt(ARG_SECTION_NUMBER)-1]);
//        tvLong.setText(longTextArray[getArguments().getInt(ARG_SECTION_NUMBER)-1]);


        img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);


        return rootView;
    }

}
