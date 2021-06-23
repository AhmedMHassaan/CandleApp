package com.ahmed.m.hassaan.candleapp.ui.showresult;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.PermissionRequest;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.room.CandleDatabase;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.databinding.ActivityShowResultBinding;
import com.ahmed.m.hassaan.candleapp.ui.full_screen.FullScreenImageViewActivity;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.ToastTags;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialogModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

import br.com.onimur.handlepathoz.HandlePathOz;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ahmed.m.hassaan.candleapp.utils.App.TAG;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_SUCCESS;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class ShowResultActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityShowResultBinding binding;
    private final String IMAGE_INTENT = "IMAGE_INTENT";

    Tools tools;
    private Article article;
    private final CandleDatabase database = CandleDatabase.getInstance();
    ActivityResultLauncher<String> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_result);
        binding.setListener(this);
        binding.setLifecycleOwner(this);
        tools = new Tools(this);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            saveFileAsPdf();
                        } else {
                            tools.customMessage(
                                    getString(R.string.required_permission),
                                    getString(R.string.storeage_permission_hints),
                                    getString(R.string.give_permission),
                                    dialog -> {
                                        activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    },
                                    getString(R.string.no)
                            );

                        }
                    }
                });

        loadArticle(); // load article from room

        events();


    }

    private void events() {

        binding.wordCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullImageIntent = new Intent(view.getContext(), FullScreenImageViewActivity.class);
                // uriString is an ArrayList<String> of URI of all images
//                ArrayList<String> list = new ArrayList<>();
//                list.add(article.getWordCloud());
//                list.add(article.getMindMap());
//                fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, list);
                // pos is the position of image will be showned when open
//                fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);
                fullImageIntent.putExtra(IMAGE_INTENT, Base64.decode(article.getWordCloud(), Base64.DEFAULT));
                startActivity(fullImageIntent);
            }
        });
        binding.mindMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullImageIntent = new Intent(view.getContext(), FullScreenImageViewActivity.class);
                // uriString is an ArrayList<String> of URI of all images
//                ArrayList<String> list = new ArrayList<>();
//                list.add(article.getMindMap());
//                list.add(article.getWordCloud());
//                fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, list);
                // pos is the position of image will be showned when open
//                fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);

                fullImageIntent.putExtra(IMAGE_INTENT, Base64.decode(article.getMindMap(), Base64.DEFAULT));
                startActivity(fullImageIntent);
            }
        });
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

//                        Log.d(TAG, "onSuccess: image is "+article.getMindMap());
                        byte[] wordCloud = Base64.decode(article.getWordCloud(), Base64.DEFAULT);
                        tools.putImgIntoNoCrop(binding.wordCloud, wordCloud);

                        byte[] mindMap = Base64.decode(article.getMindMap(), Base64.DEFAULT);
                        tools.putImgIntoNoCrop(binding.mindMap, mindMap);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tools.showExceptionError(e.getMessage());
                    }
                });
    }

    private void saveFileAsPdf() {
        msg("Saving .. ");


//        String mainDir = "storage/emulated/0/";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            String mainDir = Environment.getExternalStorageDirectory() + "/CandleApp";
//            File mainDir = new File(Environment.getExternalStorageDirectory() + "/CandleApp");
            File mainDir = new File(this.getExternalFilesDir(null) + "/CandleApp");

            Log.d(TAG, "saveFileAsPdf:  dir is " + mainDir.getPath());
            if (!mainDir.exists()) {
                boolean b = mainDir.mkdir();
                if (b) {
                    Log.d(TAG, "writeToFile: Dir Created");
                } else {

                    error("Cant Create Folder");
                    return;
                }
            }

            String workDir = mainDir.getPath() + "/" + System.currentTimeMillis();

            File workDirFile = new File(workDir);
            boolean mkdirs = workDirFile.mkdir();
            if (mkdirs) {
                Log.d(TAG, "saveFileAsPdf: Word Dir Created");
            } else {
                Log.d(TAG, "saveFileAsPdf: Work Dir Not Created");
            }


            File mainArticle = new File(workDir, "mainArticle.txt");
            File summarizedArticle = new File(workDir, "summarized.txt");
            try {

                // Save Main Article :
                FileWriter fileWriter = new FileWriter(mainArticle);
                fileWriter.write(article.getArticle());
                fileWriter.flush();
                fileWriter.close();
                mainArticle = null;
                fileWriter = null;

                fileWriter = new FileWriter(summarizedArticle);
                fileWriter.write(article.getSummarizedArticle());
                fileWriter.flush();
                fileWriter.close();
                fileWriter = null;
                summarizedArticle = null;

                System.gc();

                // save WordCloud
                File wordCloudFile = new File(workDir, "wordcloud.png");
                FileOutputStream out = new FileOutputStream(wordCloudFile);
                Bitmap wordCloudBitmap = tools.createBitmapFromView(binding.wordCloud);
                wordCloudBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

                wordCloudBitmap = null;
                System.gc();

                // save mind map
                File mindMapFile = new File(workDir, "mindMap.png");
                FileOutputStream out2 = new FileOutputStream(mindMapFile);
                Bitmap mindBitmap = tools.createBitmapFromView(binding.mindMap);
                mindBitmap.compress(Bitmap.CompressFormat.PNG, 100, out2);
                out2.flush();
                out2.close();

                mindBitmap = null;
                System.gc();

                msg(getString(R.string.savedSuccessfully), TOAST_SUCCESS);

                tools.customMessage(new CustomDialogModel(
                        getString(R.string.savedSuccessfully),
                        "All Work Saved In \n" + workDirFile.getAbsolutePath(),
                        getString(R.string.ok),
                        null
                ));


            } catch (Exception e) {
                tools.customMessage("Error : \n" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            error("No Permissions");
        }


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
            activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onBackPressed() {
        onClick(binding.btnBack);
    }
}