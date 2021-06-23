package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.file;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.ahmed.m.hassaan.candleapp.R;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Permission;

import br.com.onimur.handlepathoz.HandlePathOz;
import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;
import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class File2 extends Fragment implements View.OnClickListener, HandlePathOzListener.SingleUri {

    FragmentFromFileBinding binding;
    ArticlesViewModel articlesViewModel;
    ActivityResultLauncher<Intent> mLauncher;
    //    ActivityResultLauncher<String> permissionLauncher;
    HandlePathOz handlePathOz;

    Tools tools;

    private final Article article = new Article();
    private final CandleDatabase database = CandleDatabase.getInstance();
    boolean errorHappend = false; // is true when reading file got  error

    public File2() {

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        tools = new Tools(getContext());
        handlePathOz = new HandlePathOz(getContext(), this);
        initViewModels();


        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Log.d(App.TAG, "onClick: the result is Ok ");
                if (result.getData() == null || result.getData().getData() == null) {
                    Log.d(App.TAG, "onClick:  data is null or getData is null");
                } else {


                    Uri data = result.getData().getData();
                    handlePathOz.getRealPath(data);


                }
            } else {
                error("No File Chosen");
                errorHappend = true;
            }
        });

        /*permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
//                    onClick(binding.btnChooseFile);
            } else {
                error("Not Good");
                NavHostFragment.findNavController(FromFileFragment.this).navigate(R.id.actionToTextFragment);
            }
        });*/


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
        binding = FragmentFromFileBinding.inflate(inflater, container, false);
        binding.setListener(this);
        article.setRatio(0.7);
        binding.lblRatio.setText(String.valueOf(article.getRatio()));
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModels();

    }


    private void observeViewModels() {


        /*articlesViewModel.getFileContentLiveData().observe(getViewLifecycleOwner(), schema -> {
            enableView(true);
            if (schema.isSuccessful()) {
                binding.lblOutputText.setText(schema.getResponse());
            } else {
                schema.showError(getContext());
            }
        });*/

        articlesViewModel.getSummarizedLiveData().observe(getViewLifecycleOwner(), schema -> {

            enableView(false);

            if (schema.isSuccessful()) {
                SummarizedArticle response = schema.getResponse();
                article.setSummarizedArticle(response.getSummarizedArticle());
                article.setMainArticleWordsCount(response.getMainArticleWordsCount());
                article.setSummarizedWordsCount(response.getSummarizedWordsCount());
                Log.d(App.TAG, "observeViewModels:  Count = " + response.getSummarizedWordsCount() + "\nSummarizedText is : " + response.getSummarizedArticle());
//                msg(schema.getMessage());
                enableView(true);
                articlesViewModel.generateWordCloud(article.getArticle());

            } else {

                errorOccurred(schema.getMessage(), dialog -> {
                            enableView(false);
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
            enableView(true);
            if (schema.isSuccessful()) {
                article.setWordCloud(schema.getResponse().getImageLink());
                Log.d(App.TAG, "observeViewModels:  WordCloud is : " + schema.getResponse().getImageLink());

//                msg(schema.getMessage());
                enableView(false);
                articlesViewModel.generateMindMap(article.getSummarizedArticle());


            } else {

                // to avoid null word cloud in result page we set default value for wordCloud as empty string

                errorOccurred(schema.getMessage(), dialog -> {
                    enableView(false);
                    articlesViewModel.generateWordCloud(article.getArticle());

                }, dialog -> article.setWordCloud(""));

            }
        });


        articlesViewModel.getMindMapLiveData().observe(getViewLifecycleOwner(), schema -> {
            enableView(true);
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


    private void errorOccurred(String message, OkButtonListener retryListener, CancelButtonListener cancelButtonListener) {

        enableView(true);
        tools.customMessage(new CustomDialogModel(
                getString(R.string.newMessage),
                message + "\n" + "Retry Again ?",
                getString(R.string.retry),
                retryListener,
                getString(R.string.cancel),
                cancelButtonListener
        ));

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
    public void onClick(View v) {
        if (v == binding.tab1) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToTextFragment);
        } else if (v == binding.btnChooseFile) {

//            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                enableView(false);
//                articlesViewModel.getFileContent(file);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            mLauncher.launch(Intent.createChooser(intent, "Choose File "));
//            } else {
//                permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//            }




        } else if (v == binding.btnApply) {

            if (errorHappend) {
                tools.customMessage("There are errors happened\nfix it and try again.");
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
            enableView(false);
            article.setArticle(text);
            articlesViewModel.summarize(text, article.getRatio());
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

        } else if (v == binding.btnHelp) {
            tools.customMessage("You Can Control Of Summary Size Using Ratio");
        }


    }

    private void enableView(boolean enalbe) {
        binding.btnChooseFile.setEnabled(enalbe);
        binding.btnRationMinus.setEnabled(enalbe);
        binding.btnRationPlus.setEnabled(enalbe);
        binding.btnApply.setEnabled(enalbe);
        binding.btnHelp.setEnabled(enalbe);


        if (enalbe) {

            binding.progress.setVisibility(View.GONE);
        } else {

            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestHandlePathOz(@NonNull PathOz pathOz, Throwable throwable) {


        if (throwable != null) {
            Log.d(App.TAG, "onRequestHandlePathOz: Error Happnend " + throwable.getMessage());
        }


        Log.d(App.TAG, "onRequestHandlePathOz: File Type is : " + pathOz.getType());
        File file = new File(pathOz.getPath());
        String ex = file.getName().substring(file.getName().lastIndexOf(".")+1).toLowerCase();
        Log.d(App.TAG, "onRequestHandlePathOz: Extension is " + ex);
        if (!("txt".equals(ex) || "pdf".equals(ex))) {
            Log.d(App.TAG, "onRequestHandlePathOz: Con not process " + ex + " Files ");
            error("Con not process " + ex + " Files ");
            return;
        }

        if (file.exists()) {
            Log.d(App.TAG, "onAttach: File Exists ");
        } else {
            Log.d(App.TAG, "onAttach: File Not Exists ");
        }


        try {


            enableView(false); // to show progress and disable the buttons;

            if (getActivity() == null || getActivity().getContentResolver() == null) {
                Log.d(App.TAG, "onRequestHandlePathOz: Error in getActivity Or getContentResolver");
                return;
            }

            InputStream in = getActivity().getContentResolver().openInputStream(Uri.fromFile(file));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            enableView(true);
            binding.lblFileName.setText(file.getName());
            int size = Integer.parseInt(String.valueOf(file.length() / 1024));
            binding.lblFileSize.setText(String.valueOf(size).concat(" KB "));
            binding.lblOutputText.setText(builder.toString());
            errorHappend = false;
            binding.btnChooseFile.setText(R.string.changeFile);
//            binding.lblOutputText.setText("Wait Untel Reading File");


        } catch (@NonNull Exception e) {
            enableView(true);
            binding.lblOutputText.setText("Error !!\n".concat(e.getMessage()));
            errorHappend = true;
        }

    }

    @Override
    public void onDestroy() {
        handlePathOz.onDestroy();
        handlePathOz.deleteTemporaryFiles();
        super.onDestroy();
    }
}
