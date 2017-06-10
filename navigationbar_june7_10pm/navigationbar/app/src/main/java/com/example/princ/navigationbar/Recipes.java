package com.example.princ.navigationbar;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import static com.example.princ.navigationbar.Utils.loadProfiles;


public class Recipes extends MainActivity {

    public static SwipePlaceHolderView mSwipeView;
    public static Context mContext;
    public static TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ViewManager)findViewById(R.id.flContent).getParent()).removeView(findViewById(R.id.flContent)); //Remove the other frame layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.f2Content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.tinder_main, contentFrameLayout);

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();
        msg = (TextView)findViewById(R.id.textView);

        int bottomMargin = Utils.dpToPx(160);
        Point windowSize = Utils.getDisplaySize(getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        if (loadProfiles(getApplicationContext()) != null) {
            for (Profile profile : loadProfiles(this.getApplicationContext())) {
                mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
            }
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
//                Toast toast = Toast.makeText(mContext,"nahhhh", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
//                Toast toast = Toast.makeText(mContext,"yum!", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
            }
        });

    }

}
