package com.ahmed.m.hassaan.candleapp.data.pojo;

import com.google.gson.annotations.SerializedName;

public class WordCloudResponse {

    @SerializedName("imageLink")
    private String imageLink;


    public String getImageLink() {
        return imageLink;
    }

    public WordCloudResponse(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
