package com.ahmed.m.hassaan.candleapp.ui.full_screen;


import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.room.CandleDatabase;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.databinding.ActivityScreenTouchImageViewBinding;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FullScreenImageViewActivity extends AppCompatActivity {

    //    public static final String URI_LIST_DATA = "URI_LIST_DATA";
//    public static final String IMAGE_FULL_SCREEN_CURRENT_POS = "IMAGE_FULL_SCREEN_CURRENT_POS";
    private final String IMAGE_INTENT = "IMAGE_INTENT";
    ActivityScreenTouchImageViewBinding binding;
    Tools tools;

    private Article article;
    private final CandleDatabase database = CandleDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_screen_touch_image_view);

        tools = new Tools(this);
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        ViewPager viewPager = findViewById(R.id.view_pager);

//        ArrayList<String> imagePaths = getIntent().getStringArrayListExtra(URI_LIST_DATA);
//        if (imagePaths == null || imagePaths.isEmpty()){
//            Tools.error("No Images Founded");
//            finish();
//        }


//        int currentPos = getIntent().getIntExtra(IMAGE_FULL_SCREEN_CURRENT_POS, 0);
//        Tools.msg("Length of  array is "+imagePaths.size());


        Intent intent = getIntent();
        if (intent != null && intent.getByteArrayExtra(IMAGE_INTENT) != null && intent.getByteArrayExtra(IMAGE_INTENT).length > 0) {
            byte[] image = intent.getByteArrayExtra(IMAGE_INTENT);
//            tools.putImgIntoNoCrop(binding.ivContent, image);

//            Glide.with(this)
//                    .load(image)
//                    .into(binding.ivContent);


            binding.ivContent.setImageBitmap(tools.convertByteToBitmap(image));

            Log.d(App.TAG, "onCreate:  image is "+image.length);
        }else{
            finish();
        }


    }


}