package com.ahmed.m.hassaan.candleapp.data.pojo;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

@Entity(tableName = "articles")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id ;  // for room id

    private double ratio = 0; // for summarization
    private String article;
    private int mainArticleWordsCount ;
    private int summarizedWordsCount;
    private String summarizedArticle;
    private String wordCloud;
    private String mindMap;


    public Article() {
    }

    @Ignore
    public Article(String article, double ratio, int mainArticleWordsCount, int summarizedWordsCount, String summarizedArticle, String wordCloud, String mindMap) {
        this.article = article;
        this.ratio = ratio;
        this.mainArticleWordsCount = mainArticleWordsCount;
        this.summarizedWordsCount = summarizedWordsCount;
        this.summarizedArticle = summarizedArticle;
        this.wordCloud = wordCloud;
        this.mindMap = mindMap;
    }



    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public double getRatio() {
        Log.d(App.TAG, "getRatio: Ratio is " + ratio);
        try {
            ratio = new Tools().arabicToDecimal(new DecimalFormat("#.#").format(ratio));
        } catch (NumberFormatException e) {
            Log.e(App.TAG, "getRatio: Ratio is " + ratio, e);
        }
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getMainArticleWordsCount() {
        return mainArticleWordsCount;
    }

    public void setMainArticleWordsCount(int mainArticleWordsCount) {
        this.mainArticleWordsCount = mainArticleWordsCount;
    }

    public int getSummarizedWordsCount() {
        return summarizedWordsCount;
    }

    public void setSummarizedWordsCount(int summarizedWordsCount) {
        this.summarizedWordsCount = summarizedWordsCount;
    }

    public void setSummarizedArticle(String summarizedArticle) {
        this.summarizedArticle = summarizedArticle;
    }

    public void setWordCloud(String wordCloud) {
        this.wordCloud = wordCloud;
    }

    public String getMindMap() {
        return mindMap;
    }

    public void setMindMap(String mindMap) {
        this.mindMap = mindMap;
    }

    public String getSummarizedArticle() {
        return summarizedArticle;
    }

    public String getWordCloud() {
        return wordCloud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
