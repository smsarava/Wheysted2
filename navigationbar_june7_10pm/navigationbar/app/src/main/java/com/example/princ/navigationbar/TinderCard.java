package com.example.princ.navigationbar;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.tinder_card_view)
@NonReusable public class TinderCard { //Adding NonReusable ensures that all rejected cards don't show up again

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + " ( " + mProfile.getServing()+ " servings )");
        locationNameTxt.setText("Total time: " +mProfile.getTime()+ " minutes");
    }

    @Click(R.id.profileImageView)
    private void onClick(){
        Log.d("EVENT", "profileImageView click");
        mSwipeView.addView(this);
        //handle when the user clicks the picture
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        //mSwipeView.addView(this);
        //handle card rejection status
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        //handle card acception status
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    } //when i click on swipe out first, it adds empty cards/repeats


}
