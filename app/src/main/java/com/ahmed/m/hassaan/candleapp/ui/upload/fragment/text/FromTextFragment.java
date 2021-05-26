package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.text;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromFileBinding;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromTextBinding;
import com.ahmed.m.hassaan.candleapp.ui.upload.viewmodel.ArticlesViewModel;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class FromTextFragment extends Fragment implements View.OnClickListener {

    FragmentFromTextBinding binding;
    Tools tools;
    ArticlesViewModel articlesViewModel;
    Article article = new Article();


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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModels();

        events();

    }

    private void events() {
        binding.seekbarRatio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.lblRatioHint.setText(getString(R.string.ratio, getFloatValue(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private float getFloatValue(int progress) {
        float ratio =0.1f * progress;
        article.setRatio(ratio);
        return ratio;
    }

    private void observeViewModels() {
        articlesViewModel.getSummarizedLiveData().observe(getViewLifecycleOwner(), schema -> {
            // TODO : hide Progress
            if (schema.isSuccessful()) {
                article.setSummarizedArticle(schema.getResponse());

                // TODO: Show Progress again
                articlesViewModel.generateWordCloud(article.getArticle());

            } else {
                schema.showError(getContext());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tab2) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToFileFragment);
        } else if (v == binding.btnApply) {

            String text = binding.txtInput.getText().toString();
            if (text.isEmpty() || text.length() < 50) {
                Snackbar.make(binding.btnApply, "Please Inter Text To Be Summarized > 100 litters ", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }
            //TODO : DO OPERATION ON TEXT (Text )
            //TODO : Show Progress
            article.setArticle(text);
            articlesViewModel.summarize(text,article.getRatio() );
        }
    }
}