package com.ahmed.m.hassaan.candleapp.ui.upload.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmed.m.hassaan.candleapp.data.pojo.ResponseSchema;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.data.pojo.WordCloudResponse;
import com.ahmed.m.hassaan.candleapp.data.remote.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesViewModel extends ViewModel {


    private final MutableLiveData<ResponseSchema<SummarizedArticle>> summarizedLiveData = new MutableLiveData<>();

    private final MutableLiveData<ResponseSchema<WordCloudResponse>> wordCloudLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseSchema<SummarizedArticle>> getSummarizedLiveData() {
        return summarizedLiveData;
    }

    public MutableLiveData<ResponseSchema<WordCloudResponse>> getWordCloudLiveData() {
        return wordCloudLiveData;
    }


    public void summarize(String article, float ratio) {

        RetrofitClient
                .getInstance()
                .getArticlesOperation()
                .summarizeArticle(article, ratio)
                .enqueue(new Callback<ResponseSchema<SummarizedArticle>>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseSchema<SummarizedArticle>> call, @NonNull Response<ResponseSchema<SummarizedArticle>> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            summarizedLiveData.setValue(response.body());
                        } else {
                            summarizedLiveData.setValue(new ResponseSchema<>(0, "Page Not Found !"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSchema<SummarizedArticle>> call, @NonNull Throwable t) {
                        summarizedLiveData.setValue(new ResponseSchema<>(t));
                    }
                });
    }

    public void generateWordCloud(String article) {
        RetrofitClient
                .getInstance()
                .getArticlesOperation()
                .generateWordCloud(article)
                .enqueue(new Callback<ResponseSchema<WordCloudResponse>>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseSchema<WordCloudResponse>> call, @NonNull Response<ResponseSchema<WordCloudResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            wordCloudLiveData.setValue(response.body());
                        } else {
                            wordCloudLiveData.setValue(new ResponseSchema<>(0, "Page Not Found !"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSchema<WordCloudResponse>> call, @NonNull Throwable t) {
                        wordCloudLiveData.setValue(new ResponseSchema<>(t));
                    }
                });
    }


}
