package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.file;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.ShowCasePreferences;
import com.ahmed.m.hassaan.candleapp.data.local.room.CandleDatabase;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromFileBinding;
import com.ahmed.m.hassaan.candleapp.ui.showresult.ShowResultActivity;
import com.ahmed.m.hassaan.candleapp.ui.upload.viewmodel.ArticlesViewModel;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.ToastTags;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CancelButtonListener;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialogModel;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.OkButtonListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class FromFileFragment extends Fragment implements View.OnClickListener {

    FragmentFromFileBinding binding;
    private final ShowCasePreferences showCasePreferences = ShowCasePreferences.getInstance();
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> fileChooserLauncher;
    Tools tools;
    private int PICKFILE_REQUEST_CODE = 1;
    boolean isErrorFounded = false;
    ArticlesViewModel articlesViewModel;
    private final Article article = new Article();
    private final CandleDatabase database = CandleDatabase.getInstance();


    public FromFileFragment() {

    }

    private void initViewModels() {
        articlesViewModel = new ViewModelProvider(this).get(ArticlesViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        tools = new Tools(getContext());

        initViewModels();

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {

                getFile();
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.

                tools.customMessage(
                        getString(R.string.required_permission),
                        getString(R.string.storeage_permission_hints),
                        getString(R.string.give_permission),
                        dialog -> {
                            checkPermission();
                        },
                        getString(R.string.no)
                );
            }
        });

        fileChooserLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null && result.getData().getData() != null) {
                        readFile(result.getData().getData());
                        binding.btnChooseFile.setText(R.string.changeFile);
                    } else {
                        error("No File Choosed");
                    }
                }
            }
        });

    }

    private void getFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        fileChooserLauncher.launch(Intent.createChooser(intent, "Choose File"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void observeViewModels() {
        articlesViewModel.getSummarizedLiveData().observe(getViewLifecycleOwner(), schema -> {

            hideProgress();

            if (schema.isSuccessful()) {
                SummarizedArticle response = schema.getResponse();
                article.setSummarizedArticle(response.getSummarizedArticle());
                article.setMainArticleWordsCount(response.getMainArticleWordsCount());
                article.setSummarizedWordsCount(response.getSummarizedWordsCount());
                Log.d(App.TAG, "observeViewModels:  Count = " + response.getSummarizedWordsCount() + "\nSummarizedText is : " + response.getSummarizedArticle());
//                msg(schema.getMessage());
                showProgress();
                articlesViewModel.generateWordCloud(article.getArticle());

            } else {

                errorOccurred(schema.getMessage(), dialog -> {
                            showProgress();
                            articlesViewModel.summarize(article.getArticle(), article.getRatio());
                        },
                        dia -> {
                            article.setSummarizedArticle("");
                            article.setSummarizedWordsCount(0);
                            article.setMainArticleWordsCount(0);
                        });
            }
        });

        articlesViewModel.getWordCloudLiveData().observe(getViewLifecycleOwner(), schema -> {
            hideProgress();
            if (schema.isSuccessful()) {
                article.setWordCloud(schema.getResponse().getImageLink());
                Log.d(App.TAG, "observeViewModels:  WordCloud is : " + schema.getResponse().getImageLink());

//                msg(schema.getMessage());
                showProgress();
                articlesViewModel.generateMindMap(article.getSummarizedArticle());


            } else {

                // to avoid null word cloud in result page we set default value for wordCloud as empty string

                errorOccurred(schema.getMessage(), dialog -> {
                    showProgress();
                    articlesViewModel.generateWordCloud(article.getArticle());

                }, dialog -> article.setWordCloud(""));

            }
        });


        articlesViewModel.getMindMapLiveData().observe(getViewLifecycleOwner(), schema -> {
            hideProgress();
            if (schema.isSuccessful()) {

//                msg(schema.getMessage()); // show message from server side
                article.setMindMap(schema.getResponse().getImageLink());  // assign mindMap to article

                Tools.msg("Process Finished Successfully", ToastTags.TOAST_SUCCESS);
                prepareArticleForShown();

            } else {

                // show the error message and ask for retry

                tools.customMessage(new CustomDialogModel(
                        getString(R.string.newMessage),
                        schema.getMessage() + ".\n" + getString(R.string.show_result_anyway) + "\nOr Retry Again ?",
                        getString(R.string.retry),
                        getString(R.string.show_result),
                        getString(R.string.cancel),
                        dialog -> {
                            // retry the Request
                            articlesViewModel.generateMindMap(article.getSummarizedArticle());
                        },
                        dialog -> {
                            // show the result
                            prepareArticleForShown();
                        },
                        dialog -> {
                            // cancel the request
                            article.setMindMap(""); // set the mind map to not be null in the next page
                        },
                        false,
                        0

                ));


            }
        });
    }

    /**
     * show that the result ready to shown
     */
    private void prepareArticleForShown() {

        tools.customMessage(new CustomDialogModel(
                getString(R.string.newMessage),
                getString(R.string.result_is_ready_to_be_shown),
                getString(R.string.show_result),
                dialog -> {

//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("article", article);
//                            intent.putExtra("bundle", bundle);
                    database
                            .articlesDao()
                            .insertArticle(article)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    goToShowResultActivity();
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    tools.showExceptionError(e.getMessage());
                                }
                            });
                }


        ));


    }

    private void goToShowResultActivity() {
        Intent intent = new Intent(getContext(), ShowResultActivity.class);
        startActivity(intent);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFromFileBinding.inflate(inflater, container, false);
        binding.setListener(this);
        observeViewModels();

        if (showCasePreferences.startShowCase()) {
            startShowCaseHint();
        }

        return binding.getRoot();
    }


    /**
     * start showing hints and help
     */
    private void startShowCaseHint() {

        if (showCasePreferences.startShowCase()) {
            doShowCase(0, binding.btnChooseFile, "Choose File", "Click Here To Choose Text File");
        }
    }

    private void doShowCase(int step, View view, String title, String content) {
        new GuideView.Builder(getContext())
                .setTitle(title)
                .setContentText(content)
                .setTargetView(view)
                .setContentTypeFace(ResourcesCompat.getFont(getContext(), R.font.font1))//optional
                .setTitleTypeFace(ResourcesCompat.getFont(getContext(), R.font.font1))//optional
                .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
                .setGravity(Gravity.center)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        // 0    ==>   tab1
                        if (step == 0)
                            doShowCase(1, binding.btnApply, "Ratio", "Change Ratio To Control Of Summarization Size ");
                        else if (step == 1) {
                            doShowCase(2, binding.lblOutputHint, "Output text", "Here The Text generated from file is shown \n just after read file");
                        } else if (step == 2) {
                            doShowCase(3, binding.btnApply, "Apply Text", "Apply Text To be Uploaded \n summarized \n Mind map generated");
                        } else if (step == 3) { // hint for apply button

                            // show the end of trip message to user
                            tools.customMessage(
                                    getString(R.string.help),
                                    getString(R.string.help_ended),
                                    getString(R.string.show),
                                    dialog -> onClick(binding.tab1),
                                    getString(R.string.dont_show_again),
                                    dialog -> {
                                        showCasePreferences.dontShowAgain();
                                        onClick(binding.tab1);
                                    }
                            );

                        }

                    }
                })
                .build()
                .show();
    }


    @Override
    public void onClick(View v) {
        if (v == binding.tab1) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToTextFragment);
        } else if (v == binding.btnChooseFile) {
            checkPermission();
        } else if (v == binding.btnApply) {

            if (isErrorFounded) {
                tools.customMessage("There is an Error Occurred While Reading file");
                return;
            }

            if (binding.progress.getVisibility() == View.VISIBLE) {
                error("Wait Until Process be  Finished");
                return;
            }


            if (article.getRatio() <= .2) {
                Tools.msg("It is small value to ratio", ToastTags.TOAST_WARNING);
            }

            String text = binding.lblOutputText.getText().toString();
            if (text.isEmpty() || text.length() < 50) {
                Snackbar.make(binding.btnApply, "Please Inter Text To Be Summarized > 100 litters ", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }
            showProgress();
            article.setArticle(text);
            articlesViewModel.summarize(text, article.getRatio());
        }

    }

    private void errorOccurred(String message, OkButtonListener retryListener, CancelButtonListener cancelButtonListener) {

        tools.customMessage(new CustomDialogModel(
                getString(R.string.newMessage),
                message + "\n" + "Retry Again ?",
                getString(R.string.retry),
                retryListener,
                getString(R.string.cancel),
                cancelButtonListener
        ));

    }


    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            getFile();

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            tools.customMessage(
                    getString(R.string.required_permission),
                    getString(R.string.storeage_permission_hints),
                    dialog -> {
                        checkPermission();
                    },
                    getString(R.string.no)
            );

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    private void readFile(Uri data) {
        File file = new File(data.getPath());

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            isErrorFounded = false;
            binding.lblOutputText.setText(text.toString());
        } catch (IOException e) {
            isErrorFounded = true;
            binding.lblOutputText.setText(e.getMessage());

        }

    }

    private void showProgress() {

        binding.btnApply.setEnabled(false);
        binding.btnChooseFile.setEnabled(false);
        binding.btnRationMinus.setEnabled(false);
        binding.btnRationPlus.setEnabled(false);
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.btnApply.setEnabled(true);
        binding.btnChooseFile.setEnabled(true);
        binding.btnRationMinus.setEnabled(true);
        binding.btnRationPlus.setEnabled(true);
        binding.progress.setVisibility(View.GONE);
    }


}