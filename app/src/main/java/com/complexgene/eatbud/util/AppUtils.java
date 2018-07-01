package com.complexgene.eatbud.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.complexgene.eatbud.R;


/**
 * Created by satyabrata on 5/3/18.
 */

public class AppUtils {

    public static int screenDen(Context context){
        return (int) context.getResources().getDisplayMetrics().density;
    }

    public static int measuredDen(Context context,int param){
        return (int) context.getResources().getDisplayMetrics().density*param;
    }

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static int getToolbarHeight(Context context) {
        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static void hideKeyboard(Activity mActivity){
        try{
            if (mActivity.getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception e){
            System.out.println("hideKeyboard Exception:"+e);
        }
    }

    public static void showKeyboard(Activity mActivity, View v){
        InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

//        linearLayout.getApplicationWindowToken()
    }


    public static int getDrawableImage(Context context,String imageName) {

        int resourceId = context.getResources().getIdentifier(imageName,
                "drawable", context.getPackageName());

        return resourceId;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
