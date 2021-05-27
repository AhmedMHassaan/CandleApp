package com.ahmed.m.hassaan.candleapp.ui.showresult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.room.CandleDatabase;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.databinding.ActivityShowResultBinding;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class ShowResultActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityShowResultBinding binding;
    Tools tools;
    private Article article;
    private final CandleDatabase database = CandleDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_result);
        binding.setListener(this);
        binding.setLifecycleOwner(this);
        tools = new Tools(this);


        loadArticle(); // load article from room


    }

    /**
     * load the article saved in room and bind it  into design (binding)
     */
    private void loadArticle() {
        database
                .articlesDao()
                .getArticle()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Article>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Article response) {
                        article = response;
                        binding.setArticle(article);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tools.showExceptionError(e.getMessage());
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnBack) {
            msg("Exiting ..");
            database
                    .articlesDao()
                    .deleteArticles()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            finish();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            error(e.getMessage());
                        }
                    });

        } else if (v == binding.btnSave) {
            // TODO : Save Work in phone:
            msg(R.string.save);
        }

    }

    @Override
    public void onBackPressed() {
        onClick(binding.btnBack);
    }
}