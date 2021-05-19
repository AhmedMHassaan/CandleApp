package com.ahmed.m.hassaan.candleapp.ui.splash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.databinding.ItemLandingSliderBinding;
import com.ahmed.m.hassaan.candleapp.utils.App;

public class SliderAdapter extends PagerAdapter {

    LayoutInflater inflater;
    ItemLandingSliderBinding binding;
    OnSlideButtonsClickedListener onBtnStartClicked;


    private final int[] slidesImages = {
            R.drawable.ic_mind_map_gen,
            R.drawable.ic_reading_articles,
            R.drawable.ic_text_summerization,
            R.drawable.ic_logo
    };

    private final String[] titles = {
            App.mACTIVITY.getString(R.string.slide_1_title),
            App.mACTIVITY.getString(R.string.slide_2_title),
            App.mACTIVITY.getString(R.string.slide_3_title),
            "GetStarted"
    };
    private final String[] descs = {
            App.mACTIVITY.getString(R.string.slide_1_description),
            App.mACTIVITY.getString(R.string.slide_2_description),
            App.mACTIVITY.getString(R.string.slide_3_description),
            "GET_STARTED"
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        inflater = LayoutInflater.from(container.getContext());
        binding = ItemLandingSliderBinding.inflate(inflater);

        binding.setImage(slidesImages[position]);
        binding.setTitle(titles[position]);
        binding.setDesc(descs[position]);

        binding.setListener(onBtnStartClicked);
        container.addView(binding.getRoot());

        if (slidesImages[position] == R.drawable.ic_logo) {
            binding.regularLayout.setVisibility(View.GONE);
            binding.startLayout.setVisibility(View.VISIBLE);

        } else {
            binding.regularLayout.setVisibility(View.VISIBLE);
            binding.startLayout.setVisibility(View.GONE);

        }

        return binding.getRoot();


    }


    public void setOnBtnStartClicked(OnSlideButtonsClickedListener onBtnStartClicked) {
        this.onBtnStartClicked = onBtnStartClicked;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    public interface OnSlideButtonsClickedListener {
        void next();

        void skip();

        void getStarted();

//        void checked(CheckBox checkBox);
    }
}
