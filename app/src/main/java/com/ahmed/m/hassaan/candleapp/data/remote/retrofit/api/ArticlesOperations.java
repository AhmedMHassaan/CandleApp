package com.ahmed.m.hassaan.candleapp.data.remote.retrofit.api;

import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.data.pojo.ResponseSchema;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.data.pojo.WordCloudResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ArticlesOperations {

    @POST("articles/summary")
    @FormUrlEncoded
    Call<ResponseSchema<SummarizedArticle>> summarizeArticle(@Field("article") String article , @Field("ratio") float ration);

    @POST("articles/generate")
    @FormUrlEncoded
    Call<ResponseSchema<WordCloudResponse>> generateWordCloud(@Field("article") String article);

}
