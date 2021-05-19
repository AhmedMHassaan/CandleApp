package com.ahmed.m.hassaan.candleapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.data.local.SlidesPreferenceManager;
import com.ahmed.m.hassaan.candleapp.databinding.ActivitySplashBinding;
import com.ahmed.m.hassaan.candleapp.utils.Tools;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    SliderAdapter sliderAdapter = new SliderAdapter();
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                error("No Windows Will Be Displayed Please Check Permission ");
//                checkDrawOverlayPermission();
//                return;
//            }
//
//        }

        tools = new Tools(this);
        if (SlidesPreferenceManager.getInstance().showNextPage()) {
            loadHomePage();
            return;
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        makeStatuesBarTransparent();
        binding.viewPager.setAdapter(sliderAdapter);
        createDots(0);
        events();
    }

    /*public void checkDrawOverlayPermission() {
     *//* check if we already  have permission to draw over other apps *//*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                *//* if not construct intent to request permission *//*
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                *//* request permission via start activity for result *//*
                startActivityForResult(intent, 1234);
            }
        }
    }*/

    private void makeStatuesBarTransparent() {


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
//            getWindow().setStatusBarColor(Color.parseColor("#1F2332"));
//        } else {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        }
    }

    private void events() {

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sliderAdapter.setOnBtnStartClicked(new SliderAdapter.OnSlideButtonsClickedListener() {
            @Override
            public void next() {
                int nextPos = binding.viewPager.getCurrentItem() + 1;
                if (nextPos < sliderAdapter.getCount()) {
                    binding.viewPager.setCurrentItem(nextPos);
                }
            }

            @Override
            public void skip() {

                binding.viewPager.setCurrentItem(sliderAdapter.getCount() - 1);
            }

            @Override
            public void getStarted() {
                SlidesPreferenceManager.getInstance().writePreference();
                loadHomePage();
            }


        });

    }

    private void loadHomePage() {

//        if (UserInfoPreference.getShredUserInfoPreference().isLoggedIn())
//            tools.startNewActivity(PreparingAppActivity.class);
//        else
//            tools.startNewActivity(IntroActivity.class);

        // TODO:GO TO UPLOAD PAGE
    }


    private void createDots(int currentPosition) {

        binding.lnrDots.removeAllViews();
        ImageView[] dots = new ImageView[sliderAdapter.getCount()];

        for (int i = 0; i < sliderAdapter.getCount(); i++) {

            dots[i] = new ImageView(this);
            if (currentPosition == i) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            binding.lnrDots.addView(dots[i], params);

        }


    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        *//* check if received result code
         is equal our requested code for draw permission  *//*
        if (requestCode == 1234) {
            *//* if so check once again if we have permission *//*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    msg("Permission Granted ", ToastTags.TOAST_SUCCESS);
                    Intent intent = new Intent(this, this.getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    error("Accept Permission To Ensure That Displaying Windows");
                    finish();
                }
            }
        }
    }*/


}