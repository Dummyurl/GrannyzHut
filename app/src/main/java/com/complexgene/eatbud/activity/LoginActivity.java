package com.complexgene.eatbud.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.complexgene.eatbud.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;


/**
 * Created by satyabrata on 13/6/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private TextView tvRegNow,tvForgotPwd;
    private ImageButton imbFacebook,imbGoogle;
    private Button btnLogin;



    private static final int RC_SIGN_IN = 001;

    private GoogleApiClient mGoogleApiClient;
//    private CallbackManager callbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        findViewById(R.id.cod_layout).getBackground().setAlpha(255);

        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvRegNow=findViewById(R.id.tvRegNow);
        tvRegNow.setOnClickListener(this);

        tvForgotPwd=findViewById(R.id.tvForgotPwd);
        tvForgotPwd.setOnClickListener(this);

        imbFacebook=findViewById(R.id.imbFacebook);
        imbFacebook.setOnClickListener(this);

        imbGoogle=findViewById(R.id.imbGoogle);
        imbGoogle.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient!=null){
            if(mGoogleApiClient.isConnected()){
                mGoogleApiClient.stopAutoManage(LoginActivity.this);
                mGoogleApiClient.disconnect();
            }
        }
        super.onStop();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
//                startActivity(new Intent(this,));
                break;
            case R.id.tvRegNow:
                startActivity(new Intent(this,ProfileActivity.class));
                break;
            case R.id.tvForgotPwd:
                break;
            case R.id.imbGoogle:
                if(mGoogleApiClient!=null){
                    if(mGoogleApiClient.isConnected()){
                        googleSignIn();
                    }
                }else {
                    setUpGClient();
                }
                break;
            case R.id.imbFacebook:
//                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_birthday"));
                break;
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        googleSignIn();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult");

        switch (requestCode){
            case RC_SIGN_IN:
//                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleGoogleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
                break;
        }
    }

    private synchronized void setUpGClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        //System.out.println("getStatus:"+result.getStatus());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            System.out.println("getEmail:"+acct.getEmail());
            System.out.println("getDisplayName:"+acct.getDisplayName());
            System.out.println("getId:"+acct.getId());
            System.out.println("getPhotoUrl:"+acct.getPhotoUrl());

            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    System.out.println("revokeAccess getStatus:"+status.getStatus());
                }
            });
        }
    }
}
