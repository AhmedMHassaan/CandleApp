package com.ahmed.m.hassaan.candleapp.data.pojo;

import java.io.Serializable;

public class Article implements Serializable {

    private String article ;
    private float ratio ; // for summarization
    private SummarizedArticle summarizedArticle;
    private WordCloudResponse wordCloud;

    public Article(String article, SummarizedArticle summarizedArticle, WordCloudResponse wordCloud) {
        this.article = article;
        this.summarizedArticle = summarizedArticle;
        this.wordCloud = wordCloud;
    }

    public Article() {
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public SummarizedArticle getSummarizedArticle() {
        return summarizedArticle;
    }

    public void setSummarizedArticle(SummarizedArticle summarizedArticle) {
        this.summarizedArticle = summarizedArticle;
    }

    public WordCloudResponse getWordCloud() {
        return wordCloud;
    }

    public void setWordCloud(WordCloudResponse wordCloud) {
        this.wordCloud = wordCloud;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
