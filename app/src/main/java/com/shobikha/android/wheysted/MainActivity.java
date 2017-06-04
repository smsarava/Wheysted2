package com.shobikha.android.wheysted;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private Button signOut;
    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions signInOptions;
    private static final int REQ_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //change

        signOut = (Button) findViewById(R.id.signout);
        SignIn = (SignInButton) findViewById(R.id.signin);
        SignIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this)
                            .enableAutoManage(this,this)
                            .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                            .addOnConnectionFailedListener(this)
                            .build();

    }
/*
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        googleApiClient.disconnect();
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                signIn();
                break;
            case R.id.signout:
                signOut();
                break;

        }

    }


    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, REQ_CODE);
    }

    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        if (googleApiClient.isConnected()) {
                            googleApiClient.clearDefaultAccountAndReconnect();
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                            updateUI(false);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    private void handleResult(GoogleSignInResult result)
    {

        if (result.isSuccess()) {
            //Log.d("USER TOKEN: ", result.getSignInAccount().getIdToken().toString());
            //Save all the data for this id... this is a unique id..
            Log.d("ID: ", result.getSignInAccount().getId().toString());
            //To Display Name...
            Log.d("NAME: ", result.getSignInAccount().getDisplayName());
            // Signed in successfully, show authenticated UI.
            //  GoogleSignInAccount acct = result.getSignInAccount();
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin)
    {
        if (isLogin) {
            Log.d("LOGIN--->", "ABOUT TO LOGIN");
            findViewById(R.id.signin).setVisibility(View.GONE);
            findViewById(R.id.signout).setVisibility(View.VISIBLE);
        } else {
            Log.d("LOGOUT--->", "ABOUT TO LOGOUT");
            findViewById(R.id.signin).setVisibility(View.VISIBLE);
            findViewById(R.id.signout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("Result", ""+result.getStatus().getStatusCode());
            handleResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

