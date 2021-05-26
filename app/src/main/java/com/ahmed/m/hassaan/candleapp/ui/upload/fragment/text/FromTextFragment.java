package com.ahmed.m.hassaan.candleapp.ui.upload.fragment.text;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromFileBinding;
import com.ahmed.m.hassaan.candleapp.databinding.FragmentFromTextBinding;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static com.ahmed.m.hassaan.candleapp.utils.Tools.msg;

public class FromTextFragment extends Fragment implements View.OnClickListener {

    FragmentFromTextBinding binding;

    public FromTextFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        msg("Fragment Created");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFromTextBinding.inflate(inflater, container, false);
        binding.setListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tab2) {
            NavHostFragment.findNavController(this).navigate(R.id.actionToFileFragment);
        } else if (v == binding.btnApply) {

            String text = binding.txtInput.getText().toString();
            if (text.isEmpty() || text.length() < 100) {
                Snackbar.make(binding.btnApply, "Please Inter Text To Be Summarized > 100 litters ", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }
            //TODO : DO OPERATION ON TEXT (FILE )
            msg("Will Summarize Text and Generate Mind map ");
        }
    }
}