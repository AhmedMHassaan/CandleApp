package com.ahmed.m.hassaan.candleapp.utils;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.m.hassaan.candleapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static android.app.Activity.RESULT_OK;

public class BindingAttributes {


    /**
     * for make textview Linkified and choose the color of link, phone, ...
     *
     * @param lbl   textview
     * @param color the color of link
     */
    @BindingAdapter(value = "link_color")
    public static void checkLink(@NonNull TextView lbl, @ColorInt int color) {
        checkLinkDialog(lbl, color);
    }

    /**
     * for make textview Linkified and choose the color of link, phone, ... in my custom dialog
     *
     * @param lbl   textView
     * @param color the color of link
     */
    @BindingAdapter(value = "dialog_link_color")
    public static void checkLinkDialog(@NonNull TextView lbl, @ColorInt int color) {
        final SpannableString s = new SpannableString(lbl.getText());
        lbl.setText(s);
        lbl.setAutoLinkMask(RESULT_OK);
        lbl.setMovementMethod(LinkMovementMethod.getInstance());
        lbl.setLinksClickable(true);
        lbl.setLinkTextColor(color);
        Linkify.addLinks(s, Linkify.ALL);

    }

    /**
     * load image in image view using Glide
     *
     * @param imgView imageview
     * @param img     link of image
     */
    @BindingAdapter("imgBase64String")
    public static void putImgInto(@NonNull ImageView imgView, int img) {
        Glide
                .with(App.mACTIVITY)
                .load(img)
                .placeholder(R.drawable.ic_logo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .timeout(10000)
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .priority(Priority.HIGH)
                .into(imgView);


    }


    @BindingAdapter(value = {"imageLink", "errorDrawable"}, requireAll = false)
    public static void putImgInto(@NonNull ImageView imgView, String link, Drawable drawable) {

        if (drawable == null)
            drawable = ContextCompat.getDrawable(imgView.getContext(), R.drawable.ic_load_image_error);

        Glide
                .with(imgView)
                .load(link)
                .placeholder(R.drawable.ic_image_loading)
                .timeout(10000)
                .dontAnimate()
                .error(drawable)
                .priority(Priority.HIGH)
                .into(imgView);


    }




    @BindingAdapter(value = {"adapter", "layout_manager"})
    public static void initRecView(@NonNull RecyclerView recyclerView, RecyclerView.Adapter<?extends RecyclerView.ViewHolder> adapter, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
