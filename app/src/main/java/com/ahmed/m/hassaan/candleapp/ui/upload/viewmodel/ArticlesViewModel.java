package com.ahmed.m.hassaan.candleapp.ui.upload.viewmodel;

import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmed.m.hassaan.candleapp.data.pojo.MindMap;
import com.ahmed.m.hassaan.candleapp.data.pojo.ResponseSchema;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.data.pojo.WordCloud;
import com.ahmed.m.hassaan.candleapp.data.remote.retrofit.RetrofitClient;
import com.ahmed.m.hassaan.candleapp.utils.App;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class ArticlesViewModel extends ViewModel {


    // **************************** LIVE_DATA *****************************************************************

    private final MutableLiveData<ResponseSchema<SummarizedArticle>> summarizedLiveData = new MutableLiveData<>();

    private final MutableLiveData<ResponseSchema<WordCloud>> wordCloudLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseSchema<SummarizedArticle>> getSummarizedLiveData() {
        return summarizedLiveData;
    }

    public MutableLiveData<ResponseSchema<WordCloud>> getWordCloudLiveData() {
        return wordCloudLiveData;
    }

    private final MutableLiveData<ResponseSchema<MindMap>> mindMapLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseSchema<MindMap>> getMindMapLiveData() {
        return mindMapLiveData;
    }


    // **************************** METHODS ****************************************************

    public void summarize(String article, double ratio) {

        Log.d(App.TAG, "summarize: Ratio = " + ratio);
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

                            try {
                                summarizedLiveData.setValue(new ResponseSchema<>(0, "Summarization Says : \n"+response.errorBody().string()));

                            } catch (IOException e) {
                                summarizedLiveData.setValue(new ResponseSchema<>(0, "Summarization Page Not Found !"));
                            }

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
                .enqueue(new Callback<ResponseSchema<WordCloud>>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseSchema<WordCloud>> call, @NonNull Response<ResponseSchema<WordCloud>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            wordCloudLiveData.setValue(response.body());
                        } else {
                            wordCloudLiveData.setValue(new ResponseSchema<>(0, "WordCloud Page Not Found !"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSchema<WordCloud>> call, @NonNull Throwable t) {
                        wordCloudLiveData.setValue(new ResponseSchema<>(t));
                    }
                });
    }


    public void generateMindMap(String summarizedArticle) {

        RetrofitClient
                .getInstance()
                .getArticlesOperation()
                .generateMindMap(summarizedArticle)
                .enqueue(new Callback<ResponseSchema<MindMap>>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseSchema<MindMap>> call, @NonNull Response<ResponseSchema<MindMap>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mindMapLiveData.setValue(response.body());
                        } else {
                            mindMapLiveData.setValue(new ResponseSchema<>(0, "MindMap Page Not Found !"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSchema<MindMap>> call, @NonNull Throwable t) {
                        mindMapLiveData.setValue(new ResponseSchema<>(t));
                    }
                });
    }


}
