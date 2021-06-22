package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.text;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.ShowCasePreferences;
import com.ahmed.m.hassaan.candleapp.data.local.room.CandleDatabase;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.data.pojo.MindMap;
import com.ahmed.m.hassaan.candleapp.data.pojo.SummarizedArticle;
import com.ahmed.m.hassaan.candleapp.data.pojo.WordCloud;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromFileBinding;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromTextBinding;
import com.ahmed.m.hassaan.candleapp.ui.showresult.ShowResultActivity;
import com.ahmed.m.hassaan.candleapp.ui.upload.viewmodel.ArticlesViewModel;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.ToastTags;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CancelButtonListener;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialogModel;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.NoButtonListener;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.OkButtonListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class FromTextFragment extends Fragment implements View.OnClickListener {

    FragmentFromTextBinding binding;
    Tools tools;
    ArticlesViewModel articlesViewModel;
    private final Article article = new Article();
    private final CandleDatabase database = CandleDatabase.getInstance();
    private ShowCasePreferences showCasePreferences = ShowCasePreferences.getInstance();

    public FromTextFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        tools = new Tools(getContext());
        initViewModels();
    }

    private void initViewModels() {
        articlesViewModel = new ViewModelProvider(this).get(ArticlesViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFromTextBinding.inflate(inflater, container, false);
        binding.setListener(this);
        article.setRatio(0.7);
        binding.lblRatio.setText(String.valueOf(article.getRatio()));

        startShowCaseHint();  // to show hint and help for using

        return binding.getRoot();
    }

    /**
     * start showing hints and help
     */
    private void startShowCaseHint() {

        if (showCasePreferences.startShowCase()) {
            doShowCase(0, binding.tab1, "Text Operations", "Click Here To Start Text Operations\n start Typing And Editing");
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
                .setTitleTextSize((int) ResourcesCompat.getFloat(getResources(), R.dimen._9ssp))//optional
                .setGravity(Gravity.center)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        // 0    ==>   tab1
                        if (step == 0)
                            doShowCase(1, binding.btnRationPlus, "Ratio", "Change Ratio To Control Of Summarization Size ");
                        else if (step == 1){ // 1  ==> ratio
                            doShowCase(2, binding.btnRationPlus, "Ratio", "Change Ratio To Control Of Summarization Size ");
                        }else if (step == 2){ // hint for ratio
                            doShowCase(3, binding.btnRationPlus, "Ratio", "Change Ratio To Control Of Summarization Size ");
                        }
                        else if (step == 3){ // hint for apply button
                            doShowCase(4, binding.btnRationPlus, "Ratio", "Change Ratio To Control Of Summarization Size ");
                        }

                    }
                })
                .build()
                .show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModels();

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

    @Override
    public void onClick(View v) {
        if (v == binding.tab2) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToFileFragment);
        } else if (v == binding.btnApply) {

            if (binding.progress.getVisibility() == View.VISIBLE) {
                error("Wait Until Process be  Finished");
                return;
            }

            if (article.getRatio() <= .2) {
                Tools.msg("It is small value to ratio", ToastTags.TOAST_WARNING);
            }

            String text = binding.txtInput.getText().toString();
            if (text.isEmpty() || text.length() < 50) {
                Snackbar.make(binding.btnApply, "Please Inter Text To Be Summarized > 100 litters ", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }
            showProgress();
            article.setArticle(text);
            articlesViewModel.summarize(text, article.getRatio());
        } else if (v == binding.btnHelp) {
            tools.customMessage("You Can Control Of Summary Size Using Ratio");
        } else if (v == binding.btnRationPlus) {
            double ratio = article.getRatio();
            if (ratio >= 1f) {
                ratio = 1f;
                return;
            }
            ratio += 0.1f;
            article.setRatio(ratio);
            binding.lblRatio.setText(String.valueOf(article.getRatio()));

        } else if (v == binding.btnRationMinus) {
            double ratio = article.getRatio();
            if (ratio <= 0.1) {
                ratio = 0.1f;
                return;
            }

            ratio -= 0.1f;
            article.setRatio(ratio);
            binding.lblRatio.setText(String.valueOf(article.getRatio()));

        }
    }

    private void showProgress() {
        binding.txtInput.setEnabled(false);
        binding.btnApply.setEnabled(false);
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.txtInput.setEnabled(true);
        binding.btnApply.setEnabled(true);
        binding.progress.setVisibility(View.GONE);
    }

}