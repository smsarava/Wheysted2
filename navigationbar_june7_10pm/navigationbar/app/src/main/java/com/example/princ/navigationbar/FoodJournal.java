package com.example.princ.navigationbar;

import android.os.Bundle;
import android.view.ViewManager;
import android.widget.FrameLayout;

public class FoodJournal extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       ((ViewManager)findViewById(R.id.flContent).getParent()).removeView(findViewById(R.id.flContent)); //Remove the other frame layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.f2Content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_food_journal, contentFrameLayout);

    }

}
