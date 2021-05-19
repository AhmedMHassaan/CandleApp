package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.file;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromFileBinding;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class FromFileFragment extends Fragment implements View.OnClickListener {

    FragmentFromFileBinding binding;

    public FromFileFragment() {

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
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tab1) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToTextFragment);
        } else if (v == binding.btnChooseFile) {
            // TODO: CHOOSE (TEXT, PDF ,..) FILE AND GET TEXT FROM IT
            binding.btnChooseFile.setText(R.string.changeFile);
        } else if (v == binding.btnApply) {
            //TODO : DO OPERATION ON TEXT (FILE )
            msg("Will Summarize Text and Generate Mind map ");
        }

    }
}