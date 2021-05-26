package com.ahmed.m.hassaan.candleapp.ui.upload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.databinding.ActivityUploadArticleBinding;

public class UploadArticleActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityUploadArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_article);
//        Navigation.findNavController(binding.navContainer).navigate(R.id.actionToTextFragment);
        binding.setListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        onClick(binding.btnBack);
    }
}