package com.complexgene.eatbud.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.model.User;


/**
 * Created by satyabrata on 13/6/18.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnContinue;
    private EditText etUserName;
    private EditText etMobileNumber;
    private EditText etEmailAddress;
    private EditText etPassword;
    Intent mainActivityIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mainActivityIntent = new Intent(this, MainActivity.class);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        etUserName = findViewById(R.id.etUserName);
        etMobileNumber = findViewById(R.id.etMobNo);
        etEmailAddress = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPwd);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinue:
                readFilledValues();
                break;
        }
    }

    private void readFilledValues() {
        String name = etUserName.getText().toString();
        String mobileNumber = etMobileNumber.getText().toString();
        String emailAddress = etEmailAddress.getText().toString();
        String password = etPassword.getText().toString();
        persistData(name, mobileNumber, emailAddress, password);
    }

    private void persistData(String name, String mobileNumber, String emailAddress, String password) {
        User user = new User();
        user.setUserName(name);
        user.setMobileNumber(mobileNumber);
        user.setEmailId(emailAddress);
        user.setPassWord(password);
        user.setRewardPoints(0);
        startActivity(mainActivityIntent);
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
}
