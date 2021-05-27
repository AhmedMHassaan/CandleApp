package com.ahmed.m.hassaan.candleapp.data.pojo;

import com.google.gson.annotations.SerializedName;

public class MindMap {

    @SerializedName("imageLink")
    private String imageLink;


    public String getImageLink() {
        return imageLink;
    }

    public MindMap(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
