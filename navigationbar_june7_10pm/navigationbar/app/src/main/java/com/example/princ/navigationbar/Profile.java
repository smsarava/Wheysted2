package com.example.princ.navigationbar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by janisharali on 29/08/16.
 */
public class Profile {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    @SerializedName("time")
    @Expose
    private Integer time;

    @SerializedName("servings")
    @Expose
    private Integer servings;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer age) {
        this.time = time;
    }

    public Integer getServing() {return servings;}

    public void setServing(Integer servings) {
        this.servings = getServing();

        //maybe have something for small descriptions
    }
}
