package com.example.princ.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private final String LOG_TAG = "LOGIN_ACTIVITY--->";

    private Button signOut;
    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions signInOptions;
    private static final int REQ_CODE = 2000;

    //Facebook Login Stuff
    LoginButton loginButton;
    CallbackManager callBackManager;

    //Volley request Queue
    RequestQueue queue;

    //For Dialog Box
    private View mView;
    private AlertDialog dialog;

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        //The Volley Request quque
        queue = Volley.newRequestQueue(this);


        //Check if the server connection is ok
        checkConnection();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                Log.d("OnClick----->", "CLICKED");
                signIn();
                break;
            case R.id.ok:
                Log.d("OnClick  OK  ----->", "CLICKED");
                hideBox();
                break;

        }

    }


    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, REQ_CODE);
    }


    private void handleResult(GoogleSignInResult result)
    {
        Log.d("IN HANDLE RESULT--> ", "CAME TO FUNCTION");


        if (result.isSuccess()) {
            Log.d("IN HANDLE RESULT--> ", "RESULT SUCCESS!!!");
            //Log.d("USER TOKEN: ", result.getSignInAccount().getIdToken().toString());
            //Save all the data for this id... this is a unique id..
            Log.d("ID: ", result.getSignInAccount().getId().toString());
            //To Display Name...
            userID = result.getSignInAccount().getId().toString();

            //To Display Name...
            String name = result.getSignInAccount().getDisplayName();
            String [] names = name.split(" "); // names[0] = firstname names[1] = lastname
            String fName = names[0];
            String lName = names[1];
            Log.d("NAME: ", result.getSignInAccount().getDisplayName());

            //Send data to the server and check if the user exists
            checkUser(userID);

               /* if(actChoose == 1) {
                    //go to profile activity
                }
                else{
                    Log.e(LOG_TAG, "The activity chosen is: " + actChoose);
                    //go to the home page
                    Toast.makeText(getApplicationContext(),"Logged In!",Toast.LENGTH_SHORT).show();
                    // Signed in successfully, show authenticated UI.
                    //  GoogleSignInAccount acct = result.getSignInAccount();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("Sign", "Google");
                    startActivity(i);
                }*/




         //   updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
           // updateUI(false);
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

        //callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Connection Failed!",Toast.LENGTH_LONG).show();
    }

    private boolean checkUser(final String id){

        // Instantiate the RequestQueue.
        String url = "https://final-project-169218.appspot.com/login";
        String my_url = url + "?userID=" + URLEncoder.encode(id);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        //Check to see if the JSON is like the server
                        try {
                            // Ok, let's disassemble the json object.
                            JSONObject obj = new JSONObject(response.toString());
                            String code = obj.getString("code");
                            if(code.equals("ADD")){
                                addUser(id); // profileActivity = 1
                            }
                            else{
                                requestUser(id);
                                //setActivity(2);// HomeActivity = 2
                            }
                        }
                        catch(Exception E){
                            Log.e(LOG_TAG, "ERROR WHILE PARSING JSON OBJECT!");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });


        queue.add(jsObjRequest);

        return true;
    }

    //This function checks for the connection to the server.
    private void checkConnection(){
        String url = "https://final-project-169218.appspot.com/";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        //Check to see if the JSON is like the server
                        try {
                            // Ok, let's disassemble the json object.
                            JSONObject obj = new JSONObject(response.toString());
                            String code = obj.getString("code");
                            if(code.equals("ok")){
                                Log.d(LOG_TAG, "ALL IS GOOD");
                                Toast.makeText(getApplicationContext(),"CONNECTION SUCCESFUL!",Toast.LENGTH_SHORT).show();
                                goLogin();
                            }
                            else {
                                Log.e(LOG_TAG, "ALL IS NOT GOOD");
                                Toast.makeText(getApplicationContext(),"RESTART APP!",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch(Exception E){
                            Log.e(LOG_TAG, "ERROR WHILE PARSING JSON OBJECT!");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        queue.add(jsObjRequest);
    }

    private void goLogin(){
        //Google Sign In
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

    private void addUser(final String id){
        //go to profile activity
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        mView = getLayoutInflater().inflate(R.layout.welcome_dialog, null);


        Button ok = (Button) findViewById(R.id.ok);
        //I GUESS WE DONT NEED AN ON CLICK LISTENER MHHHHH
        //ok.setOnClickListener(this);
       /* //THIS IS WHERE THE ERROR OCCURS...TAKE IT OUT AND IT WORKS FINE BUT U WONT GO ANYWHEREE
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, profileActivity.class);
                startActivity(i);
            }
        });
*/
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    private void requestUser(final String id){
        String url = "https://final-project-169218.appspot.com/user";
        String my_url = url + "?userID=" + URLEncoder.encode("Mark");
        Log.d(LOG_TAG, "Came to request the user");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        //Check to see if the JSON is like the server
                        try {
                            // Ok, let's disassemble the json object.
                            //JSONObject obj = new JSONObject(response.toString());
                            //String firstName = obj.getString("fName");
                            //String lastName = obj.getString("lName");
                            //String email = obj.getString("email");
                            //String gender = obj.getString("gender");
                            //double weight = Double.parseDouble(obj.getString("weight"));
                            //int age = Integer.parseInt(obj.getString("age"));


                        }
                        catch(Exception E){
                            Log.e(LOG_TAG, "ERROR WHILE PARSING JSON OBJECT!");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });


        //queue.add(jsObjRequest);
    }

    public void hideBox(){
        dialog.hide();
        Intent i = new Intent(LoginActivity.this, profileActivity.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }
}

