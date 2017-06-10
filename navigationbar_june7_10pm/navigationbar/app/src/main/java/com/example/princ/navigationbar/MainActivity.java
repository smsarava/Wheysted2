package com.example.princ.navigationbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import at.grabner.circleprogress.CircleProgressView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleApiClient mGoogleApiClient;
    CircleProgressView mCircleView;
    int protiengoal = 180;
    int currentprotien = 40;
    int percentage= 100*currentprotien/protiengoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Below is all the sidebar code that you need to put in all onCreate methods
        //There are two other functions you will need to copy from below as well as a bunch of xml files such as
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //we created a toolbar so we can acess the sidebar menu
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.flContent); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_main, contentFrameLayout);

        //Below is all the values I set for the protien tracker
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setMaxValue(100);
        //mCircleView.setValue(69);
        mCircleView.setSeekModeEnabled(false);
        mCircleView.setValueAnimated(percentage);
        mCircleView.setUnit("%");
        //  mCircleView.setRimColor(Color.CYAN);
        mCircleView.setTextColor(Color.YELLOW);
        mCircleView.setUnitColor(Color.YELLOW);
        //mCircleView.setBarColor(Color.BLUE,Color.WHITE);
        // mCircleView.setBarColor(Color.BLUE);

        TextView text1a = (TextView) findViewById(R.id.textView1a);
        text1a.setText(""+protiengoal);
        ///text1a.setText(""+protiengoal+" g/ "+currentprotien);

        TextView gram1a = (TextView) findViewById(R.id.gramtext1a);
        gram1a.setText("GRAMS");

        TextView text1b = (TextView) findViewById(R.id.textView1b);
        text1b.setText(""+currentprotien);

        TextView gram1b = (TextView) findViewById(R.id.gramtext1b);
        gram1b.setText("GRAMS");



        TextView activitybar = (TextView) findViewById(R.id.textView2);
        activitybar.setText("  ACTIVITY");

        TextView slash = (TextView) findViewById(R.id.textView7);
        slash.setText("/");

    }


    //This is for handling what to do when we press back while we open the sidebar
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //This controls what we do when we click something from the sidebar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_journal) {
            Intent i = new Intent(getApplicationContext(), FoodJournal.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_recipes) {
            Intent i = new Intent(getApplicationContext(), Recipes.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_recipebox) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_home) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_history) {
            Intent i = new Intent(getApplicationContext(), History.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_logout) {

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                        }
                    });

            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}

