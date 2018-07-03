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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

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

        handleFacebookSignInResult();

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
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "user_birthday","user_gender"));
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
        callbackManager.onActivityResult(requestCode, resultCode, data);

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

    private void handleFacebookSignInResult() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        //String accessToken = loginResult.getAccessToken().getToken();
                        //String userId = loginResult.getAccessToken().getUserId();

                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                try {

                                    String imageUrl="";
                                    System.out.println("jsonObject:"+jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.optString("email");
                                    String birthday = jsonObject.optString("birthday");/*08/15/1968*/
                                    String gender = jsonObject.optString("gender");

                                    if (jsonObject.has("picture")) {
                                        JSONObject picObj = jsonObject.optJSONObject("picture");
                                        JSONObject dataObj = picObj.optJSONObject("data");
                                        imageUrl = dataObj.optString("url");
                                    }

                                    System.out.println("id:"+id);
                                    System.out.println("name:"+name);
                                    System.out.println("email:"+email);
                                    System.out.println("imageUrl:"+imageUrl);
                                    System.out.println("birthday:"+birthday);
                                    System.out.println("gender:"+gender);

                                    if(birthday!=null){
                                        if(!birthday.isEmpty()){
                                            try {
                                                birthday=new SimpleDateFormat("yyyy-MM-dd")
                                                        .format(new SimpleDateFormat("MM/dd/yyyy")
                                                                .parse(birthday));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    if(gender!=null){
                                        if(!gender.isEmpty()){
                                            gender=gender.substring(0, 1).toUpperCase() + gender.substring(1);
                                        }

                                    }

                                    LoginManager.getInstance().logOut();

//                                    socialLogin(AppConstant.FB_LOGIN,id,name,email,gender,birthday,"");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,name,birthday,gender,picture.type(large)");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();

                        return;
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        return;
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        return;
                    }
                });
    }


}
