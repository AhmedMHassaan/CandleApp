package com.ahmed.m.hassaan.candleapp.data.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SummarizedArticle implements Serializable {

    @SerializedName("mainArticleWordsCount")
    private Integer mainArticleWordsCount ;

    @SerializedName("summarizedWordsCount")
    private Integer summarizedWordsCount;

    @SerializedName("summarizedArticle")
    private String summarizedArticle;


    public SummarizedArticle(Integer mainArticleWordsCount, Integer summarizedWordsCount, String summarizedArticle) {
        this.mainArticleWordsCount = mainArticleWordsCount;
        this.summarizedWordsCount = summarizedWordsCount;
        this.summarizedArticle = summarizedArticle;
    }

    public SummarizedArticle() {
    }

    public Integer getMainArticleWordsCount() {

        return mainArticleWordsCount == null? 0 : mainArticleWordsCount ;
    }

    public void setMainArticleWordsCount(Integer mainArticleWordsCount) {
        this.mainArticleWordsCount = mainArticleWordsCount == null ? 0 : mainArticleWordsCount ;
    }

    public Integer getSummarizedWordsCount() {
        return summarizedWordsCount == null ?0 : summarizedWordsCount;
    }

    public void setSummarizedWordsCount(Integer summarizedWordsCount) {
        this.summarizedWordsCount = summarizedWordsCount == null ? 0: summarizedWordsCount;
    }

    public String getSummarizedArticle() {
        return summarizedArticle;
    }

    public void setSummarizedArticle(String summarizedArticle) {
        this.summarizedArticle = summarizedArticle;
    }
}
