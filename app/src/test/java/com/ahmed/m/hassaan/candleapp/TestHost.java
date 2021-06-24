package com.ahmed.m.hassaan.candleapp;

import androidx.annotation.NonNull;

import com.ahmed.m.hassaan.candleapp.data.pojo.ResponseSchema;
import com.ahmed.m.hassaan.candleapp.data.remote.retrofit.RetrofitClient;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

public class TestHost {

    @Test
    public void testHost() {

        RetrofitClient
                .getInstance()
                .getArticlesOperation()
                .testHost()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null){
                            try {
                                assertEquals("test",response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
//        assertEquals(4, 2 + 2);
    }
}
