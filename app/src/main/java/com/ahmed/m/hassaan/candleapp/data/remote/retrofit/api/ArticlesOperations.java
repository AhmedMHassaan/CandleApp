package com.ahmed.m.hassaan.candleapp.data.remote.retrofit.api;

import com.ahmed.m.hassaan.candleapp.data.pojo.MindMap;
import com.ahmed.m.hassaan.candleapp.data.pojo.ResponseSchema;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.data.pojo.WordCloud;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArticlesOperations {

    @GET("test")
    Call<ResponseBody> testHost();

    @POST("articles/summary")
    @FormUrlEncoded
    Call<ResponseSchema<SummarizedArticle>> summarizeArticle(@Field("article") String article , @Field("ratio") double ration);

    @POST("articles/generate/wordcloud")
    @FormUrlEncoded
    Call<ResponseSchema<WordCloud>> generateWordCloud(@Field("article") String article);

    @POST("articles/generate/mindMap")
    @FormUrlEncoded
    Call<ResponseSchema<MindMap>> generateMindMap(@Field("article") String summarizedArticle);
}
