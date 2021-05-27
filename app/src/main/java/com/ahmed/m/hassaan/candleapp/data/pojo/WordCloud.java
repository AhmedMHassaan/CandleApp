package com.ahmed.m.hassaan.candleapp.data.pojo;

import com.google.gson.annotations.SerializedName;

public class WordCloud {

    @SerializedName("imageLink")
    private String imageLink;


    public String getImageLink() {
        return imageLink;
    }

    public WordCloud(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
