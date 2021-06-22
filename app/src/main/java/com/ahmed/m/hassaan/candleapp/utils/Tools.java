package com.ahmed.m.hassaan.candleapp.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CancelButtonListener;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialog;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialogModel;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.OkButtonListener;
import com.ahmed.m.hassaan.candleapp.utils.dynamicbox.DynamicBox;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Years;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_ERROR;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_INFO;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_NORMAL;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_SUCCESS;
import static com.ahmed.m.hassaan.candleapp.utils.ToastTags.TOAST_WARNING;
//import static tyrantgit.explosionfield.Utils.createBitmapSafely;


public class Tools {

    private Context mContext;
    private static final Canvas sCanvas = new Canvas();

    public Tools(Context mContext) {
        this.mContext = mContext;
    }

    public Tools() {
        this(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY);
    }

    /*public static class Builder {
        private  Tools instance = null;
        Context context ;
        public Builder setContext(Context context) {
            this.context= context;
            return this;
        }

        public synchronized Tools build() {

            if (instance == null) instance = new Tools(context);
            return instance;
        }
    }*/


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public Typeface getArFont() {
        return ResourcesCompat.getFont(mContext, R.font.font1);
    }

    public static void msg(@NonNull Context mContext, String msg, String toastyType) {

        if (msg == null || msg.isEmpty())
            return;
        Toasty.Config instance = Toasty.Config.getInstance();

        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.font1);
        if (typeface != null)
            instance.setToastTypeface(typeface);

        instance
                .tintIcon(true)
                .allowQueue(false)
                .setTextSize((int) mContext.getResources().getDimension(R.dimen._5sdp))
                .apply();
        switch (toastyType) {


            case TOAST_ERROR:
                Toasty.error(mContext, msg, Toasty.LENGTH_SHORT, true).show();
                break;
            case TOAST_INFO:
                Toasty.info(mContext, msg, Toasty.LENGTH_SHORT, true).show();
                break;
            case TOAST_NORMAL:
                Toasty.normal(mContext, msg, Toasty.LENGTH_SHORT).show();
                break;
            case TOAST_SUCCESS:
                Toasty.success(mContext, msg, Toasty.LENGTH_SHORT, true).show();
                break;
            case TOAST_WARNING:
                Toasty.warning(mContext, msg, Toasty.LENGTH_SHORT, true).show();
                break;
            default:

        }

    }

   /* public static void msg(@NonNull Context mContext, String msg) {

        Toasty
                .Config
                .getInstance()
                .setToastTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/font1.ttf"))
                .tintIcon(true)
                .allowQueue(false)
                .setTextSize(16)
                .apply();

        Toasty.info(mContext, msg, Toasty.LENGTH_SHORT, true).show();


    }*/

    public static void msg(String msg, String toastType) {
        msg(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getApplicationContext(), msg, toastType);
    }

    public static void error(String msg) {
        msg(msg, TOAST_ERROR);
    }

    public static void error(@StringRes int msg) {
        msg(msg, TOAST_ERROR);
    }

    public static void error() {
        error(R.string.errorHappened);
    }

    public static void msg(@StringRes int msg, String toastType) {
        msg(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getApplicationContext(), com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(msg), toastType);
    }

    public static void msg(String msg) {
        msg(App.mACTIVITY.getApplicationContext(), msg, TOAST_INFO);
    }

    public static void msg(@StringRes int msg) {
        msg(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getApplicationContext(), com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(msg), TOAST_INFO);
    }


    public double arabicToDecimal(String number) {

//        final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

        if (number == null || number.trim().length() == 0) return 0.0;

        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return Double.parseDouble(new String(chars).replace("٫", "."));
    }


    /*public  void showIntro(final Activity mActivity, String title, String text, final int viewId, final int type, FragmentManager manager) {


        if (!isShowHelpCase(mActivity)) {
            openFragment(new FragmentHome(), manager);
            return;
        }

        new GuideView.Builder(mActivity)
                .setTitle(title)
                .setContentText(text)
                .setGravity(GuideView.Gravity.center)
                .setTargetView(mActivity.findViewById(viewId))
                .setContentTypeFace(Typeface.createFromAsset(mActivity.getAssets(), "font1.ttf"))
                .setTitleTypeFace(Typeface.createFromAsset(mActivity.getAssets(), "font1.ttf"))
                .setTitleTextSize(15)//optional
                .setContentTextSize(13)//optional
                .setDismissType(GuideView.DismissType.anywhere) //optional - default dismissible by TargetView
                .setGuideListener(view -> {


                    if (type == 1) {
                        if (user == null)
                            return;
                        if (user instanceof Teacher)
                            showIntro(mActivity, "المجموعات", "المجموعات التي قمت بعملها و اضافتها", R.id.item_group_teacher, 2, manager);
                        else if (user instanceof Student)
                            showIntro(mActivity, "المجموعات", "المجموعات المشترك بها ", R.id.item_group_student, 2, manager);
                        else if (user instanceof Father)
                            showIntro(mActivity, "المجموعات", "المجموعات المشترك بها ابنك ", R.id.item_group_student, 2, manager);

                    } else if (type == 2) {

                        if (user == null)
                            return;

                        if (user instanceof Teacher)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_teacher, 3, manager);
                        else if (user instanceof Student)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_student, 3, manager);
                        else if (user instanceof Father)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_father, 3, manager);


                    } else if (type == 3) {
                        if (user instanceof Teacher)
                            showIntro(mActivity, "أخري", "المزيد من الخيارات", R.id.item_moreOptions_teacher, 0, manager);
                        else if (user instanceof Student)
                            showIntro(mActivity, "أخري", "المزيد من الخيارات", R.id.item_moreOptions_student, 0, manager);
                        else if (user instanceof Father)
                            showIntro(mActivity, "أخري", "المزيد من الخيارات", R.id.item_moreOptions_father, 0, manager);

                    } else if (type == 4) {
                        showIntro(mActivity, "عنا", "عرض معلومات المطورين", R.id.item_about, 5, manager);
                    } else if (type == 5) {
                        showIntro(mActivity, "المواعيد", "تظهر لك مواعيد كل مجموعه و درس", R.id.item_appointment, 6, manager);
                    } else if (type == 6) {
                        showIntro(mActivity, "المتابعة", "متابعة كل ما يحدث للأبناء", R.id.item_following, 7, manager);
                    } else if (type == 7) {

                        if (user == null)
                            return;

                        if (user instanceof Teacher)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_teacher, 0, manager);
                        else if (user instanceof Student)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_student, 0, manager);
                        else if (user instanceof Father)
                            showIntro(mActivity, "الملف الشخصي", "ملفك الشخصي ", R.id.item_profile_father, 0, manager);


                    } else if (type == 8) {
//                        showIntro(mActivity, "المواعيد", "تظهر لك مواعيد كل مجموعه و درس", R.id.item_messages, 0, manager);
                        showIntro(mActivity, "المواعيد", "تظهر لك مواعيد كل مجموعه و درس", R.id.nav_msg, 0, manager);
                    } else if (type == 0) {
                        openFragment(new FragmentHome(), manager);
                        new AlertDialog.Builder(mActivity)
                                .setTitle("عرض هذا في المرة القادمة")
                                .setMessage("هناك المزيد في أيقونة المزيد")
                                .setPositiveButton("نعم", (dialog, which) -> saveShowAgainOrNo(mActivity, true))
                                .setNegativeButton("لا", (dialog, which) -> saveShowAgainOrNo(mActivity, false))
                                .create()
                                .show();
                    }


                })
                .build()
                .show();
    }*/


    /*private  boolean isShowHelpCase(@NonNull Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_showcase_helpActivity), MODE_PRIVATE);
        return sharedPreferences.getBoolean(mContext.getString(R.string.shared_saveLogin), true);
    }*/

   /* public  void openFragment(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager) {


        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.addToBackStack(fragment.getTag()).
                replace(R.id.fragment_container_home, fragment);
        transaction.commit();

    }*/

    /*public  void openFragment(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager, @IdRes int container) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(container, fragment);
        transaction.commit();

    }*/

    public boolean isEmptyEditText(@NonNull EditText txt, String errorText) {
        /*
         *return true if Edit Text is Empty
         * return false if nor
         */
        if (TextUtils.isEmpty(txt.getText().toString().trim())) {
            if (errorText == null || errorText.isEmpty()) {
                errorText = " حقل مطلوب";
            }

            txt.setError(errorText);
            errorAnimation(txt);

            return true;


        }
        return false;
    }

    public boolean isEmptyEditTexts(@NonNull EditText... editTexts) {
        /*
         *return true if Edit Text is Empty
         * return false if nor
         */
        for (EditText txt : editTexts) {
            if (TextUtils.isEmpty(txt.getText().toString())) {

                txt.setError(" مطلوب");
                errorAnimation(txt);

                return true;


            }
        }

        return false;
    }

    public boolean isEmptyEditText(@NonNull EditText txt) {
        /*
         *return true if Edit Text is Empty
         * return false if nor
         */
        if (TextUtils.isEmpty(txt.getText().toString())) {

            txt.setError(" مطلوب");
            errorAnimation(txt);

            return true;


        }
        return false;
    }

    public boolean isTrueLink(@NonNull EditText txtWebsite) {
        String link = txtWebsite.getText().toString();

        if (!link.isEmpty() && !Patterns.WEB_URL.matcher(link).matches()) {
            txtWebsite.setError("يرجي وضع رابط صحيح");
            errorAnimation(txtWebsite);
            return false;
        }
        return true;
    }


    public void errorAnimation(@NonNull View view) {
        view.requestFocus();
        YoYo.
                with(Techniques.Bounce)
                .duration(400)
                .delay(400)
                .repeat(3)
                .playOn(view);
    }

    public boolean isTrueEmail(EditText txtEmail) {
        if (txtEmail == null) return false;
        String username = txtEmail.getText().toString().trim();

        Pattern EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" +
                "gmail" +
                "(" +
                "\\." +
                "com" +
                ")+"
        );

        if (EMAIL_ADDRESS.matcher(username).matches()) {
            return true;
        } else {
            txtEmail.setError("يرجي كتابة الايميل بالصيغة الصحيحةإذا كنت تقوم بنسخ النص فحاول كتابته\n تأكد أنك تقوم بكتابة gmail.com@");
            errorAnimation(txtEmail);

            return false;
        }


    }

    public boolean isTruePasswords(EditText... texts) {
        if (texts.length < 2) {
            msg("يرجي استخدام علي الأقل حقلين لكلمة المرور و تأكيدها");
            return false;
        }
        for (EditText text : texts) {
            if (isEmptyEditText(text)) return false;
        }
        String pass1 = texts[0].getText().toString();
        for (int i = 1; i < texts.length; i++) {
            EditText text = texts[i];
            if (!(pass1.equals(text.getText().toString()))) {
                text.setError("كلمة السر غير متطابقة");
                errorAnimation(text);
                errorAnimation(texts[0]);
                return false;
            }
        }
        return true;

    }

    /*public boolean isTruePhoneNumber(EditText txtPhone) {
        if (txtPhone == null) return false;

        String phone = txtPhone.getText().toString();
        if ("".equals(phone)) {

            errorAnimation(txtPhone);
            txtPhone.setError("يرجي كتابة رقم التليفون بصورة صحيحة");

            return false;
        }
        if (phone.contains(" ")) {
            errorAnimation(txtPhone);
            txtPhone.setError("من المفترض أن لا يحتوي رقم التليفون علي أي مسافات ");
            return false;
        }

        if (phone.length() != 11) {

            errorAnimation(txtPhone);
            txtPhone.setError("يجب أن يتكون من 11 رقم بدلا من  ".concat(phone.length() + ""));

            return false;
        }
        if (!phone.startsWith("01")) {

            errorAnimation(txtPhone);
            txtPhone.setError("يجب أن يبدأ الرقم ب01 بدلا من ".concat(phone.charAt(0) + "" + phone.charAt(1)));

            return false;
        }

        switch (phone.charAt(2)) {
            case '0':
            case '1':
            case '2':
            case '5':
                return true;

            default:
                errorAnimation(txtPhone);
                txtPhone.setError("رقم غير صحيح");

                return false;

        }

    }*/

    public boolean isTruePhoneNumber(EditText txtPhone) {
        if (txtPhone == null) return false;

        String phone = txtPhone.getText().toString();
        boolean isTruePhone = Pattern.matches("01([0125])[0-9]+", phone);
        if (isTruePhone) {
            if (phone.length() == 11) {
                return true;
            } else {
                errorAnimation(txtPhone);
                txtPhone.setError("يجب أن يتكون من 11 رقم بدلا من  ".concat(phone.length() + ""));
                return false;
            }
        } else {
            errorAnimation(txtPhone);
            txtPhone.setError("يرجي كتابة رقم التليفون بصورة صحيحة");
            showExceptionError("يبدأ الرقم ب 011 أو 012 أو 015 أو 010 و من ثم الرقم و يكون مكون من 11 رقم");
            return false;
        }


    }

    public boolean isTruePhoneNumber(String phone) {

        if (phone == null) return false;
        boolean isTruePhone = Pattern.matches("01([0125])[0-9]+", phone);
        if (isTruePhone) {
            if (phone.length() == 11) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }


    public void putImgInto(@NonNull ImageView imgView, @NonNull byte[] img) {
        Glide
                .with(mContext)
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
                .circleCrop()
                .timeout(10000)
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .priority(Priority.HIGH)
                .into(imgView);


    }

    public void putImgInto(@NonNull ImageView imgView, @NonNull File img) {
        Glide
                .with(mContext)
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
                .circleCrop()
                .timeout(10000)
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .priority(Priority.HIGH)
                .into(imgView);


    }


    public void putImgInto(@NonNull ImageView imgView, @NonNull Bitmap img) {
        Glide
                .with(mContext)
                .load(img)
                .into(imgView);
    }

    public void putImgInto(@NonNull ImageView imgView, @NonNull String img) {
        Glide
                .with(mContext)
                .load(img)
                .placeholder(R.drawable.ic_logo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (e != null) {
//                            imgView.setImageResource(R.drawable.logo);
//                            msg(mContext, e.getMessage() + "\t" + isFirstResource , TOAST_ERROR);
//                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .circleCrop()
                .timeout(5000)
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .priority(Priority.HIGH)
                .into(imgView);


    }


    public void putImgInto(@NonNull ImageView imgView, int img) {
        Glide
                .with(mContext)
                .load(img)
                .placeholder(R.drawable.ic_logo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (e != null) {
//                            imgView.setImageResource(R.drawable.logo);
//                            msg(mContext, e.getMessage() + "\t" + isFirstResource , TOAST_ERROR);
//                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .circleCrop()
                .timeout(5000)
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .priority(Priority.HIGH)
//                .skipMemoryCache(false)
//                .signature(new ObjectKey(img))
                .into(imgView);


    }

    public void putImgIntoNoCrop(@NonNull ImageView imgView, @NonNull String img) {
        Glide
                .with(mContext)
                .load(img)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (e != null) {
//                            imgView.setImageResource(R.drawable.ic_logo);
//                            msg(mContext, e.getMessage() + "\t" + isFirstResource , TOAST_ERROR);
//                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .timeout(15000)
                .dontAnimate()
                .priority(Priority.HIGH)
//                .skipMemoryCache(false)
//                .signature(new ObjectKey(img))
                .into(imgView);


    }

    public void putImgIntoNoCrop(@NonNull ImageView imgView, int img) {
        Glide
                .with(mContext)
                .load(img)

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (e != null) {
//                            imgView.setImageResource(R.drawable.logo);
//                            msg(mContext, e.getMessage() + "\t" + isFirstResource , TOAST_ERROR);
//                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .timeout(5000)
                .dontAnimate()
                .priority(Priority.HIGH)
//                .skipMemoryCache(false)
//                .signature(new ObjectKey(img))
                .into(imgView);


    }


    public void putImgIntoNoCrop(@NonNull ImageView imgView, @NonNull byte[] img) {

        putImgIntoNoCrop(imgView, img, false);
    }

    public void putImgIntoBase64String(@NonNull ImageView imgView, @NonNull String base64ImgStr) {
        byte[] img;
        if (base64ImgStr == null || base64ImgStr.isEmpty())
            img = new byte[0];
        else
            img = Base64.decode(base64ImgStr, Base64.DEFAULT);
        putImgIntoNoCrop(imgView, img, false);
    }

    public void putImgIntoNoCrop(@NonNull ImageView imgView, @NonNull byte[] img, boolean fixedSize) {


//        setImageDimen(imgView, bitmap.getWidth(), bitmap.getHeight());
        RequestBuilder<Drawable> glideRequest = Glide
                .with(mContext)
                .load(img)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (e != null) {
//                            imgView.setImageResource(R.drawable.ic_logo);
//                            msg(mContext, e.getMessage() + "\t" + isFirstResource , TOAST_ERROR);
//                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }

                })
                .timeout(5000)
                .dontAnimate()
                .priority(Priority.HIGH);
//                .skipMemoryCache(false)
//                .signature(new ObjectKey(img))


        if (!fixedSize) {
            Bitmap bitmap = convertByteToBitmap(img);
            if (bitmap == null)
                bitmap = convertDrawableToBitmap(ContextCompat.getDrawable(mContext, R.drawable.ic_logo));

            glideRequest = glideRequest.override(bitmap.getWidth(), bitmap.getHeight());
        }
        glideRequest.into(imgView);

    }


    public String getStringFromEditText(@NonNull TextView txt) {
        return txt.getText().toString().trim();
    }

    public int getIntFromEditText(@NonNull TextView txt) {
        int n;
        try {
            n = Integer.parseInt(getStringFromEditText(txt));
            return n;
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }


    public byte[] convertBitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap convertByteToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public Bitmap convertPathImageToBitmap(String path) {
        return BitmapFactory.decodeFile(path, new BitmapFactory.Options());
    }

    public Bitmap convertURI2Bitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            InputStream input = mContext.getContentResolver().openInputStream(uri);
            if (input == null) throw new Exception("Error In Input In Image , Tools.class");
            bitmap = BitmapFactory.decodeStream(input);
            input.close();

        } catch (Exception e) {
            error(R.string.errorHappened);
        }

        return bitmap;
    }


    public Bitmap mark(Bitmap src) {

        Rect rect = new Rect(70, 70, 120, 120);
//        Rect rect = new Rect(0, 0, 100, 100);

        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_logo);
        Canvas canvas = new Canvas(src);
        canvas.drawBitmap(logo, null, rect, null);


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setTextSize(30);
        paint.setAlpha(150);
        paint.setAntiAlias(true);
        paint.setTypeface(ResourcesCompat.getFont(mContext, R.font.font1));


        canvas.drawText(" تطبيق سيستم نقادة", 150, 200, paint);


        return src;

    }


    public Bitmap convertDrawableToBitmap(@NonNull Drawable drawable) {

        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
                return bitmapDrawable.getBitmap();
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_logo);
        else
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;


    }

    /*public  void refreshActivity(Activity mActivity) {
        mActivity.recreate();
    }*/


    public void hideKeyboard(@NonNull SearchView txt) {
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);

        if (manager != null) {
            manager.hideSoftInputFromWindow(txt.getWindowToken(), 0);
        }
    }

    public void hideKeyboard(@NonNull EditText txt) {
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);

        if (manager != null) {
            manager.hideSoftInputFromWindow(txt.getWindowToken(), 0);
        }
        txt.clearFocus();
    }

    public void hideKeyboard(@NonNull Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.clearFocus();
        }
    }

    public void showKeyboard(@NonNull Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
            view.clearFocus();
        }
    }


    /*public  void showSearchHintDialog(@NonNull Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_show_search_hint), MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("showAgain", b).apply();

    }*/

   /* public  boolean showSearchAlert(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_show_search_hint), MODE_PRIVATE);
        return sharedPreferences.getBoolean("showAgain", true);
    }*/

    public String convertAr2EnNumbers(String number) {

        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }




   /* public  void getTimeFromInternet(@NonNull Context mContext, boolean checkAgain) {
        MuTime.enableDiskCaching(mContext);
        new InternetTime(mContext, checkAgain).execute();
    }*/

    /*public  void getTimeFromInternet(@NonNull Context mContext, boolean checkAgain, boolean outApp) {
        MuTime.enableDiskCaching(mContext);
        new InternetTime(mContext, checkAgain, outApp).execute();
    }*/


    public String generateCode(int length) {

        StringBuilder n = new StringBuilder();
        for (int i = 0; i < length; i++) {
            n.append(getRandomBit());
        }


        return n.toString();
    }

    public String getRandomBit() {
        String text = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return text.charAt((int) (0 + (Math.random() * text.length()))) + "";
    }

    public int getRandomIndex(int start, int end) {
        return (int) (start + (Math.random() * end));
    }


    /*public  void saveType(Activity mActivity, String type) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(mActivity.getString(R.string.shared_type), MODE_PRIVATE);
        sharedPreferences.edit().putString("type", type).apply();
    }*/

   /* public  String getType(@NonNull Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_type), MODE_PRIVATE);
        return sharedPreferences.getString("type", "");
    }*/

    /**
     * get tableName in database  based on type saved in shared pref
     * <p>
     * mContext the context or activity of class
     *
     * @return This Method Return  Table Name (Teacher , Father ,Student or null ("") if type don't match
     */
    /*public  String getTableName(Context mContext) {
        String type = getType(mContext);

        switch (type) {
            case Work.RegTypes.TYPE_TEACHER:
                return " TEACHER ";
            case Work.RegTypes.TYPE_STUDENT:
                return " STUDENT ";
            case Work.RegTypes.TYPE_FATHER:
                return " FATHER ";
            default:
                return "";
        }
    }*/
    private void setRefreshColors(@NonNull SwipeRefreshLayout refresh) {

        int[] colors = new int[]{Color.RED,

                Color.BLACK,
                Color.DKGRAY,
                Color.GRAY,
                Color.LTGRAY,
                Color.MAGENTA,
                ContextCompat.getColor(refresh.getContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(refresh.getContext(), R.color.colorPrimary),
                Color.BLUE,
                Color.parseColor("#F73D00"),
                Color.parseColor("#013243"),
                Color.parseColor("#93706C"),
                Color.parseColor("#F8BE98"),
                Color.parseColor("#2F3650"),
                Color.parseColor("#FF3333"),
                Color.parseColor("#F2F2F2"),
                Color.parseColor("#FF715B"),
                Color.parseColor("#4C5454"),
                Color.parseColor("#dddd10"),
                Color.parseColor("#4C5454"),
                Color.parseColor("#dd10dd"),
                Color.parseColor("#dddddd"),
                Color.parseColor("#001010")};

        refresh.setColorSchemeColors(colors);
    }

    /*private  void setRefreshColors(@NonNull SwipyRefreshLayout refresh) {

        int[] colors = new int[]{Color.RED,

                Color.BLACK,
                Color.DKGRAY,
                Color.GRAY,
                Color.LTGRAY,
                Color.MAGENTA,
                ContextCompat.getColor(refresh.getContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(refresh.getContext(), R.color.colorPrimary),
                Color.BLUE,
                Color.parseColor("#F73D00"),
                Color.parseColor("#013243"),
                Color.parseColor("#93706C"),
                Color.parseColor("#F8BE98"),
                Color.parseColor("#2F3650"),
                Color.parseColor("#FF3333"),
                Color.parseColor("#F2F2F2"),
                Color.parseColor("#FF715B"),
                Color.parseColor("#4C5454"),
                Color.parseColor("#dddd10"),
                Color.parseColor("#4C5454"),
                Color.parseColor("#dd10dd"),
                Color.parseColor("#dddddd"),
                Color.parseColor("#001010")};

        refresh.setColorSchemeColors(colors);
    }*/

    public void stopRefreshing(@NonNull SwipeRefreshLayout refreshLayout) {
        refreshLayout.setRefreshing(false);
    }

  /*  public  void stopRefreshing(@NonNull SwipyRefreshLayout refreshLayout) {
        refreshLayout.setRefreshing(false);
    }*/

    public void startRefreshing(@NonNull SwipeRefreshLayout refreshLayout) {

        refreshLayout.setRefreshing(true);
        setRefreshColors(refreshLayout);
    }

  /*  public  void startRefreshing(@NonNull SwipyRefreshLayout refreshLayout) {
        refreshLayout.setRefreshing(true);
        setRefreshColors(refreshLayout);
    }*/


    public boolean isErrorInInternet(String msg) {
        return isErrorInInternet(msg, true);
    }

    public boolean isErrorInInternet(String msg, boolean showMessage) {


        if (msg == null) {
            if (showMessage)
                errorInInternet();
            return true;
        }

        msg = msg.toLowerCase();
        if ("".equals(msg) || msg.contains("unable") || msg.contains("timeout") || msg.contains("validator") || msg.contains("timed") || msg.contains("ssl") || msg.contains("connection")) {
            if (showMessage)
                errorInInternet();
            return true;
        }
        return false;
    }

    public boolean isErrorInInternet(String msg, DynamicBox dynamicBox, View.OnClickListener listener) {
        if (mContext == null) return true;

        if (msg == null) {
            errorInInternet();
            return true;
        }

        msg = msg.toLowerCase();
        if ("".equals(msg)  || msg.toLowerCase().contains("unable") || msg.contains("timeout") || msg.contains("validator") || msg.contains("timed") || msg.contains("failed to connect to")) {
            errorInInternet();
            errorInInternetLayout(dynamicBox, listener);
            return true;
        }
        return false;
    }


    public void errorInInternet() {

        error(R.string.cannot_connect_server);

    }

    /*public  boolean isNotFoundedEmail(Context mContext, String msg) {

        if (msg == null || mContext == null) return false;

        if (msg.contains(mContext.getString(R.string.no_email_founded))) {

            showCustomMessage(mContext, mContext.getString(R.string.not_found_email), false, mContext.getString(R.string.log_out), v -> ic_logout(mContext));

            return true;
        }
        return false;
    }*/






    /*public  void target(Activity activity, View target, String title, String desc, Drawable icon, int n) {

        TapTargetView.showFor(activity,                 // `this` is an Activity
                TapTarget.forView(target, title, desc)
                        // All options below are optional
                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.createFromAsset(activity.getAssets(), "font1.ttf"))  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(icon, true)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                    }
                });
    }*/


    public void showMenuIcons(PopupMenu popupMenu) {


        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = null;
                    if (menuPopupHelper != null) {
                        classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    }
                    Method setForceIcons = classPopupHelper != null ? classPopupHelper.getMethod("setForceShowIcon", boolean.class) : null;
                    Objects.requireNonNull(setForceIcons).invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void notSuccessfulResponse() {
        msg(mContext, mContext.getString(R.string.notSuccessfulResponse), TOAST_ERROR);
    }


    public void exceptionError(Throwable e) {
        if (e == null || "".equals(e.getMessage())) {
//            new AlertDialog.Builder(mActivity).setTitle("رسالة جديدة ! ").setMessage("Empty Exception ").setIcon(R.drawable.ic_logo).create().show();
        } else {
            new AlertDialog.Builder(mContext).setTitle("رسالة جديدة ! ").setMessage(e.getMessage()).setIcon(R.drawable.ic_logo).setPositiveButton("Ok", null).create().show();
        }

    }

    public void exceptionError(String s) {
        exceptionError(new Exception(s));
    }

    public void showExceptionError(String message) {
        if (!isErrorInInternet(message)) {
            exceptionError(message);
        }
    }


    public void showExceptionError(String message, DynamicBox dynamic, View.OnClickListener listener) {
        if (!isErrorInInternet(message, dynamic, listener)) {
            exceptionError(message);
            if (listener == null) listener = v -> dynamic.hideAll();
            dynamic.setOtherException(getResString(R.string.errorHappened), message, "", listener);
        }
    }

    public void showExceptionError(String message, DynamicBox dynamic) {

        dynamic.setOtherButtonText(getResString(R.string.ok));
        if (!isErrorInInternet(message, dynamic, v -> dynamic.hideAll())) {
            exceptionError(message);
            dynamic.setOtherException(getResString(R.string.errorHappened), message, "", v -> dynamic.hideAll());
        } else {
            dynamic.setClickListener(v -> dynamic.hideAll());
        }
    }

    public void startOuterApplication(String packageName) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }


    public String encodeEmail(@Nullable String email) {
        if (email == null || email.isEmpty())
            return "Error In Email ";

        if (email.contains("@")) {
            StringBuilder encodedEmail = new StringBuilder();
            for (int i = 0; i < email.indexOf("@"); i++) {
                if (i < 3) {
                    encodedEmail.append(email.charAt(i));
                } else {
                    encodedEmail.append("X");
                }
            }
            encodedEmail.append("@gmail.com");
            return encodedEmail.toString();
        } else {
            return "Error In Email ";
        }

    }

    public String encodePhone(String phone) {
        if (isTruePhoneNumber(phone)) {
            return phone.substring(0, 3).concat("xxxxxx") + phone.substring(9);
        } else {
            return "رقم غير صحيح";
        }

    }


    /**
     * This Method For prepare date because timestamp in the server < realHour with 2 ;
     * time in server = 12 : 00  , realTime = 14:02
     *
     * @param timestamp time from server
     * @return changedTime to the rralTime ( serverHour +2h +2m )
     */
    public String prepareDateFromServer(String timestamp) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(R.string.timestamp_format), Locale.ENGLISH);
            DateTime dateTimeFromServer = new DateTime(simpleDateFormat.parse(timestamp));
            dateTimeFromServer = dateTimeFromServer.plusHours(2);
            return simpleDateFormat.format(dateTimeFromServer.toDate());
        } catch (Exception e) {
            return "غير معروف";
        }
//        dateTimeFromServer = dateTimeFromServer.plusMinutes(3);

    }


    public DateTime prepareDateFromServer2(String timestamp) throws ParseException {
        if (timestamp == null || timestamp.isEmpty()) {
            error("خطأ في التاريخ المرسل");
            return DateTime.now();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mContext.getString(R.string.timestamp_format), Locale.ENGLISH);
        DateTime dateTimeFromServer = new DateTime(simpleDateFormat.parse(timestamp));
        dateTimeFromServer = dateTimeFromServer.plusHours(2);
        return dateTimeFromServer;
    }

    public String getDateInDetails(String timestamp, boolean inOneLine) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(R.string.timestamp_format), Locale.ENGLISH);

        try {

            timestamp = prepareDateFromServer(timestamp);

            Date datePublish = simpleDateFormat.parse(timestamp);
            Date dateNow = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

            DateTime dateTimePublish = new DateTime(datePublish);

            DateTime dateTimeNow = new DateTime(dateNow);


            int daysInMonth = 30;

            if (datePublish != null) {
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
                int month = Integer.parseInt(monthFormat.format(datePublish));
                int year = Integer.parseInt(yearFormat.format(datePublish));
                DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 0);
                daysInMonth = dateTime.dayOfMonth().getMaximumValue();

//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.MONTH , month);
//                calendar.set(Calendar.YEAR , year);

//                daysInMonth = calendar.getActualMaximum();
            }

            int years = Math.abs(Years.yearsBetween(dateTimeNow, dateTimePublish).getYears());
            int months = Math.abs(Months.monthsBetween(dateTimeNow, dateTimePublish).getMonths() % 12);
            int days = Math.abs(Days.daysBetween(dateTimeNow, dateTimePublish).getDays() % daysInMonth);
            int hours = Math.abs(Hours.hoursBetween(dateTimeNow, dateTimePublish).getHours() % 24);
            int minutes = Math.abs(Minutes.minutesBetween(dateTimeNow, dateTimePublish).getMinutes() % 60);

            if (minutes == 0 && hours == 0 && days == 0 && months == 0 && years == 0)
                return " الآن ";

            if (inOneLine)

                return " منذ " +
                        ((years > 0) ? years + "سنة " + " و " : "") +
                        ((months > 0) ? months + " شهر " + " و " : "") +
                        ((days > 0) ? days + " يوم " + " و  " : "") +
                        ((hours > 0) ? hours + " ساعة و " : "") +
                        ((minutes > 0) ? minutes + " دقيقة" : "")
                        + " تقريبا";
            else
                return " منذ " +
                        ((years > 0) ? years + "سنة\n" + " و " : "") +
                        ((months > 0) ? months + "شهر\n" + " و " : "") +
                        ((days > 0) ? days + " يوم\n" + " و  " : "") +
                        ((hours > 0) ? hours + " ساعة و\n" : "") +
                        ((minutes > 0) ? minutes + " دقيقة" : "")
                        + " تقريبا";


        } catch (Exception e) {
            return "غير معروف ";
        }


    }

    public String getDateInDetails(String timestamp) {
        return getDateInDetails(timestamp, false);
    }


    public String getDateInDetails(String startTimestamp, String endTimestamp) {
        return getDateInDetails(startTimestamp, endTimestamp, false);
    }

    public String getDateInDetails(String greatedTime, String smallerTime, boolean inOneLine) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(R.string.timestamp_format), Locale.ENGLISH);

        try {

            greatedTime = prepareDateFromServer(greatedTime);
            smallerTime = prepareDateFromServer(smallerTime);

            Date smallerDate = simpleDateFormat.parse(smallerTime);
            Date greaterDate = simpleDateFormat.parse(greatedTime);

            DateTime dateTimeGreat = new DateTime(greaterDate);
            DateTime dateTimeSmall = new DateTime(smallerDate);


            int daysInMonth = 30;

            if (smallerDate != null) {
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
                int month = Integer.parseInt(monthFormat.format(smallerDate));
                int year = Integer.parseInt(yearFormat.format(smallerDate));
                DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 0);
                daysInMonth = dateTime.dayOfMonth().getMaximumValue();

//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.MONTH , month);
//                calendar.set(Calendar.YEAR , year);

//                daysInMonth = calendar.getActualMaximum();
            }

            int years = Math.abs(Years.yearsBetween(dateTimeGreat, dateTimeSmall).getYears());
            int months = Math.abs(Months.monthsBetween(dateTimeGreat, dateTimeSmall).getMonths() % 12);
            int days = Math.abs(Days.daysBetween(dateTimeGreat, dateTimeSmall).getDays() % daysInMonth);
            int hours = Math.abs(Hours.hoursBetween(dateTimeGreat, dateTimeSmall).getHours() % 24);
            int minutes = Math.abs(Minutes.minutesBetween(dateTimeGreat, dateTimeSmall).getMinutes() % 60);

            if (minutes == 0 && hours == 0 && days == 0 && months == 0 && years == 0)
                return " الآن ";

            if (inOneLine)

                return
                        ((years > 0) ? years + "سنة " + " و " : "") +
                                ((months > 0) ? months + "شهر " + " و " : "") +
                                ((days > 0) ? days + " يوم " + " و  " : "") +
                                ((hours > 0) ? hours + " ساعة و " : "") +
                                ((minutes > 0) ? minutes + " دقيقة" : "")
                                + " تقريبا";
            else
                return
                        ((years > 0) ? years + "سنة\n" + " و " : "") +
                                ((months > 0) ? months + "شهر\n" + " و " : "") +
                                ((days > 0) ? days + " يوم\n" + " و  " : "") +
                                ((hours > 0) ? hours + " ساعة و\n" : "") +
                                ((minutes > 0) ? minutes + " دقيقة" : "")
                                + " تقريبا";


        } catch (Exception e) {
            return "غير معروف ";
        }


    }


    public void checkAdapterSize(DynamicBox dynamicBox, ArrayList arrayList, View.OnClickListener onClickListener) {
        if (arrayList.isEmpty()) {
            dynamicBox.setOtherException("لا توجد بيانات", "قم بالتحديث او جرب في وقت لاحق", "", onClickListener);
        } else {
            dynamicBox.hideAll();
        }
    }

    public void checkAdapterSize(DynamicBox dynamicBox, String title, String msg, ArrayList arrayList, View.OnClickListener onClickListener) {
        if (arrayList.isEmpty()) {
            dynamicBox.setOtherException(title, msg, null, onClickListener);
        } else {
            dynamicBox.hideAll();
        }
    }

    public void checkAdapterWithAdsSize(DynamicBox dynamicBox, String title, String msg, ArrayList arrayList, View.OnClickListener onClickListener) {
        if (arrayList == null)
            throw new NullPointerException("لا يمكن إرسال قيمة NULL  لل ArrayLisy");
        if (arrayList.size() <= 1)
            dynamicBox.setOtherException(title, msg, null, onClickListener);
        else
            dynamicBox.hideAll();

    }


    public void errorInInternetLayout(DynamicBox dynamicBox, View.OnClickListener onClickListener) {


        dynamicBox.showInternetOffLayout(onClickListener);

    }

    public AlertDialog changeAlertDialogButtonsColor(AlertDialog dialog) {
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.successColor));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.errorColor));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(mContext, R.color.warningColor));
        });
        return dialog;
    }

    public AlertDialog changeAlertDialogButtonsColor(String title, String msg, String posText, DialogInterface.OnClickListener posClick) {

        AlertDialog dialog = createAlertBuilder(title, msg, 0, posText, posClick, mContext.getString(R.string.cancel), null).create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.successColor));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.errorColor));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(mContext, R.color.warningColor));
        });
        return dialog;
    }

    public AlertDialog changeAlertDialogButtonsColor(String title, String msg, String posText, DialogInterface.OnClickListener posClick, String negText, DialogInterface.OnClickListener negListener) {

        AlertDialog dialog = createAlertBuilder(title, msg, 0, posText, posClick, negText, negListener).create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.successColor));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.errorColor));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(mContext, R.color.warningColor));
        });
        return dialog;
    }

    public AlertDialog changeAlertDialogButtonsColor(String msg, String posText, DialogInterface.OnClickListener posClick, String negText, DialogInterface.OnClickListener negListener) {

        AlertDialog dialog = createAlertBuilder(mContext.getString(R.string.alert), msg, 0, posText, posClick, negText, negListener).create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.successColor));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.errorColor));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(mContext, R.color.warningColor));
        });
        return dialog;
    }

    public AlertDialog changeAlertDialogButtonsColor(String title, String msg, int icon, String posText, DialogInterface.OnClickListener posClick) {

        AlertDialog dialog = createAlertBuilder(title, msg, icon, posText, posClick, mContext.getString(R.string.cancel), null).create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.successColor));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.errorColor));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(mContext, R.color.warningColor));
        });
        return dialog;
    }


    public AlertDialog.Builder createAlertBuilder(String title, String msg, int icon, String posText, DialogInterface.OnClickListener postClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle(title)
                .setMessage(msg)
                .setIcon((icon <= 0) ? R.drawable.ic_logo : icon)
                .setPositiveButton(posText, postClick);
        return builder;
    }

    public AlertDialog.Builder createAlertBuilder(String title, String msg, int icon) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle(title)
                .setMessage(msg)
                .setIcon((icon <= 0) ? R.drawable.ic_logo : icon);

        return builder;
    }

    public void createAlertDialog(String title, String msg) {
        changeAlertDialogButtonsColor(title, msg, 0, "حسنا", null).show();
    }


    public AlertDialog.Builder createAlertBuilder(String title, String msg, int icon, String posText, DialogInterface.OnClickListener postClick, String negText, DialogInterface.OnClickListener negClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle(title)
                .setMessage(msg)
                .setIcon((icon == 0) ? R.drawable.ic_logo : icon)
                .setPositiveButton(posText, postClick)
                .setNegativeButton(negText, negClick);

        return builder;
    }

    public void createAlertBuilder(String title, String msg, String posText, DialogInterface.OnClickListener postClick, String negText, DialogInterface.OnClickListener negClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.ic_logo)
                .setPositiveButton(posText, postClick)
                .setNegativeButton(negText, negClick);

        changeAlertDialogButtonsColor(builder.create()).show();
    }

    public AlertDialog.Builder createAlertBuilder(String title, String msg, int icon, String posText, DialogInterface.OnClickListener postClick, String negText, DialogInterface.OnClickListener negClick, String neuText, DialogInterface.OnClickListener neuClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle(title)
                .setMessage(msg)
                .setIcon((icon == 0) ? R.drawable.ic_logo : icon)
                .setPositiveButton(posText, postClick)
                .setNegativeButton(negText, negClick)
                .setNeutralButton(neuText, neuClick);

        return builder;
    }

    public void btnProgressCompletedTask(ActionProcessButton btn) {
        if (btn == null) return;
        btn.setMode(ActionProcessButton.Mode.PROGRESS);
        btn.setProgress(100);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            btn.setMode(ActionProcessButton.Mode.ENDLESS);
            btn.setProgress(0);
            btn.setEnabled(true);

        }, 700);

    }

    public void btnProgressErrorTask(ActionProcessButton btn) {
        if (btn == null) return;
        btn.setMode(ActionProcessButton.Mode.PROGRESS);
        btn.setProgress(-1);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            btn.setMode(ActionProcessButton.Mode.ENDLESS);
            btn.setProgress(0);
            btn.setEnabled(true);
        }, 700);

    }

    public void btnProgressLoadingTask(ActionProcessButton btn) {
        if (btn == null) return;
        btn.setMode(ActionProcessButton.Mode.ENDLESS);
        btn.setEnabled(false);
        btn.setProgress(1);

    }

    public int getPositionInArray(String[] array, String text) {

        for (int i = 0; i < array.length; i++)
            if (array[i].equals(text))
                return i;


        return -1;
    }



    public int getPositionInArray(List<String> array, @NonNull String text) {

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) != null) {
                if (array.get(i) != null) {
                    if (text.equals(array.get(i)))
                        return i;
                }
            }
        }


        return -1;
    }


    public void clearEditTexts(TextView... texts) {
        for (TextView txt : texts) {
            txt.setText("");
            txt.setError(null);
        }
    }

    public void comeInNextVersions() {
        changeAlertDialogButtonsColor(createAlertBuilder("المعذرة", "انتظروا هذا القسم في الإصدارات القادمة بإذن الله ", 0, "إن شاء الله", null).create()).show();
    }

    public String getBirthDateFromSSN(String ssn) {
        if (ssn.length() != 14)
            return "خطأ في الرقم القومي والتاريخ";

        String year;

        try {
            int ssnYear = Integer.parseInt(ssn.charAt(1) + "" + ssn.charAt(2));
            if (ssnYear >= 0 && ssnYear <= 20) {
                year = "20" + ssn.charAt(1) + "" + ssn.charAt(2);
            } else {
                year = "19" + ssn.charAt(1) + "" + ssn.charAt(2);
            }
            int y = Integer.parseInt(year);


            int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(new Date()));
            if (y > currentYear || y < 1960)
                return "خطأ في الرقم القومي والتاريخ";

            int month = Integer.parseInt("" + ssn.charAt(3) + ssn.charAt(4));
            if (month > 12 || month <= 0)
                return "خطأ في الرقم القومي والتاريخ";

            int day = Integer.parseInt("" + ssn.charAt(5) + ssn.charAt(6));
            if (day > 31 || day <= 0) return "خطأ في الرقم القومي والتاريخ";

            return year + "/" + month + "/" + day;


        } catch (Exception e) {
            return "خطأ في الرقم القومي والتاريخ";
        }


    }

    public boolean isTrueSSN(@NonNull EditText txtSSN) {

        boolean isTrue = !isEmptyEditText(txtSSN) && !getBirthDateFromSSN(txtSSN.getText().toString()).contains("خطأ");

        if (!isTrue) {

            if (txtSSN.getParent() instanceof TextInputLayout) {
                ((TextInputLayout) txtSSN.getParent()).setError("هناك خطأ في الرقم القومي");
                errorAnimation((TextInputLayout) txtSSN.getParent());
            } else {
                txtSSN.setError(getBirthDateFromSSN(getStringFromEditText(txtSSN)));
                errorAnimation(txtSSN);
            }

        }

        return isTrue;

    }


    public boolean saveOriginalImage(ImageView imageView, String folderName) {
        try {

            Bitmap bitmap = new Tools(imageView.getContext()).createBitmapFromView(imageView);

//                String root = Environment.getExternalStorageDirectory().toString();
//                File externalFilesDir = mActivity.getExternalFilesDir(null);
            File externalFilesDir = new File("/storage/emulated/0/");
//
            if (!externalFilesDir.exists()) {
                msg("حدث خطأ أثناء الحفظ", TOAST_ERROR);
//                btnProgressErrorTask(btnSave);
                return false;
            }


            File myDir = new File(externalFilesDir.getAbsolutePath() + "/NakadaSystem/NakadaSystemImages/" + folderName);


            if (myDir.mkdirs())
                System.out.println(Tools.class.getName() + " Folder : " + myDir.getName() + " Created ");


            String fName = "Image-" + System.currentTimeMillis() + ".jpg";
            File file = new File(myDir, fName);


            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
//            btnProgressCompletedTask(btnSave);
            msg(mContext.getString(R.string.savedSuccessfully), TOAST_SUCCESS);

            return true;
        } catch (Exception e) {
//            btnProgressErrorTask(btnSave);
//                msg(mActivity, getString(R.string.errorInSavingImage), TOAST_ERROR);
            exceptionError(e);
            return false;
        }
    }


    public boolean saveImageWithMask(ImageView imageView, String folderName) {
        try {
            Bitmap bitmap = mark(new Tools(imageView.getContext()).createBitmapFromView(imageView));

//                String root = Environment.getExternalStorageDirectory().toString();
//                File externalFilesDir = mActivity.getExternalFilesDir(null);
            File externalFilesDir = new File("/storage/emulated/0/");
//
            if (!externalFilesDir.exists()) {
                msg("حدث خطأ أثناء الحفظ", TOAST_ERROR);
//                btnProgressErrorTask(btnSave);
                return false;
            }


            File myDir = new File(externalFilesDir.getAbsolutePath() + "/NakadaSystem/NakadaSystemImages/" + folderName);


            if (myDir.mkdirs())
                System.out.println(Tools.class.getName() + " Folder : " + myDir.getName() + " Created ");


            String fName = "Image-" + System.currentTimeMillis() + ".jpg";
            File file = new File(myDir, fName);


            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
//            btnProgressCompletedTask(btnSave);
            msg(mContext.getString(R.string.savedSuccessfully), TOAST_SUCCESS);

            return true;
        } catch (Exception e) {
//            btnProgressErrorTask(btnSave);
//                msg(mActivity, getString(R.string.errorInSavingImage), TOAST_ERROR);
            exceptionError(e);
            return false;
        }
    }

    public void copyText(String textToCopy) {
        copyText(textToCopy, mContext.getPackageName());
    }

    public void copyText(String textToCopy, String label) {
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, textToCopy);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            msg("تم نسخ النص", TOAST_NORMAL);
        }
    }

    public void openFragment(FragmentManager supportFragmentManager, @IdRes int fragmentContainer, Fragment fragment) {
        supportFragmentManager.beginTransaction().replace(fragmentContainer, fragment).commit();
    }

    public void openFragment(FragmentManager supportFragmentManager, @IdRes int fragmentContainer, Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        supportFragmentManager.beginTransaction().replace(fragmentContainer, fragment).commit();
    }


    public String getResString(@StringRes int stringRes) {
        return com.ahmed.m.hassaan.candleapp.utils.App.mACTIVITY.getString(stringRes);
    }

  /*  public void goToGallery(Fragment fragment, int requestCode) {
        Intent intent = new Intent();
        intent
                .setAction(Intent.ACTION_PICK)
                .setData(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (intent.resolveActivity(mContext.getPackageManager()) != null)
            fragment.startActivityForResult(intent, requestCode);
    }*/

    public void goToGallery(int requestCode) {
        openIntent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI, requestCode);
    }

    private void openIntent(String action, Uri uri, int requestCode) {
        Intent intent = new Intent();
        intent
                .setAction(action)
                .setData(uri);
        if (intent.resolveActivity(mContext.getPackageManager()) != null)
            ((Activity) mContext).startActivityForResult(intent, requestCode);

    }

    public void makeTextReadOnly(EditText... editTexts) {
        for (EditText txt : editTexts) {
            if (txt != null) {
                txt.setKeyListener(null);
            }
        }
    }

    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public Bitmap createBitmapFromView(View view) {

        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;

    }

    private Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }


    public void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    private void setImageDimen(ImageView img, int width, int height) {
        img.getLayoutParams().width = width;
        img.getLayoutParams().height = height;
        img.requestLayout();
    }





    public void shareText(String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "مشاركة");
        intent.putExtra(Intent.EXTRA_TEXT, body);

        mContext.startActivity(Intent.createChooser(intent, "مشاركة الخبر بواسطة"));

    }


    public Uri convertPath2Uri(String path) {
        return Uri.parse(path);
    }

    public int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
//                mContext.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

//                Log.i("RotateImage", "Exif orientation: " + orientation);
//                Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            msg("Rotation = " + rotate);
        return rotate;
    }

    public void checkImageRotation(ImageView imageView, String path) {
        int rotateImage = getCameraPhotoOrientation(path);
        imageView.setRotation(rotateImage);
    }

    public void goToAppSittings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mContext.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myAppSettings);
    }

    public void customMessage(CustomDialogModel model) {
        new CustomDialog
                .Builder(mContext)
                .setTitle(model.getTitle())
                .setMessage(model.getMessage())
                .setCancelable(model.isCancelable())
                .setOkButton(model.getBtnPosText(), model.getOkListener())
                .setNoButton(model.getBtnNegText(), model.getNoListener())
                .setCancelButton(model.getBtnOtherText(), model.getCancelListener())
                .setImage(model.getImageRes())
                .build().show();
    }

    public void customMessage(String title, String msg, String okBtn, OkButtonListener listener) {
        new CustomDialog
                .Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setOkButton(okBtn, listener)
                .setNoButton(null, null)
                .setCancelButton(null, null)
                .setImage(R.drawable.ic_info_outline_white_24dp)
                .build().show();
    }
public void customMessage(String title, String msg, String okBtn, OkButtonListener listener, String cnclBtn) {
        new CustomDialog
                .Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setOkButton(okBtn, listener)
                .setNoButton(null, null)
                .setCancelButton(cnclBtn, null)
                .setImage(R.drawable.ic_info_outline_white_24dp)
                .build().show();
    }public void customMessage(String title, String msg, String okBtn, OkButtonListener listener, String cnclBtn, CancelButtonListener cnclListener) {
        new CustomDialog
                .Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setOkButton(okBtn, listener)
                .setNoButton(null, null)
                .setCancelButton(cnclBtn, cnclListener)
                .setImage(R.drawable.ic_info_outline_white_24dp)
                .build().show();
    }

    public void customMessage(String msg, String okBtn, OkButtonListener listener) {
        customMessage("رسالة ..!", msg, okBtn, listener);
    }

    public void customMessage(String msg, String okBtn, OkButtonListener listener, String cancelBtn) {
        new CustomDialog
                .Builder(mContext)
                .setTitle("رسالة !")
                .setMessage(msg)
                .setCancelable(false)
                .setOkButton(okBtn, listener)
                .setNoButton(null, null)
                .setCancelButton(cancelBtn, null)
                .setImage(R.drawable.ic_info_outline_white_24dp)
                .build().show();
    }

    public void customMessage(String msg) {
        customMessage(mContext.getString(R.string.newMessage), msg, mContext.getString(R.string.ok), null);
    }

    public void customMessage(String msg, OkButtonListener listener) {
        customMessage(mContext.getString(R.string.newMessage), msg, mContext.getString(R.string.ok), listener);
    }

    public String md5(String txt) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(txt.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean itIsLink(@NonNull String text) {
        if (text.isEmpty()) return false;

        return Pattern.matches(Patterns.WEB_URL.pattern(), text);
    }

    public Float getFloatFromEditText(@NonNull TextView txt) {
        try {
            return Float.parseFloat(txt.getText().toString());
        } catch (NumberFormatException e) {
            return (float) 0;
        }
    }


    /**
     * this method for finishing an current activity and start new activity with new task and clear top of stack
     *
     * @param destinationClass the class of a class to be open
     */
    public void startNewActivity(Class<? extends AppCompatActivity> destinationClass) {

        if (mContext != null) {
            try {

                if (((Activity) mContext).isDestroyed() || ((Activity) mContext).isFinishing()) {
                    return;
                }

                Intent intent = new Intent(mContext, destinationClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            } catch (Exception e) {
                error(e.getMessage());
            }


        }
    }


    /**
     * to do hints and helps
     * @param view the view where hint is put
     * @param title title of hint or help
     * @param content content of hint
     * @param listener listener after hiding cotent
     */
    public void doShowCase( View view, String title, String content, GuideListener listener) {
        new GuideView.Builder(getContext())
                .setTitle(title)
                .setContentText(content)
                .setTargetView(view)
                .setContentTypeFace(ResourcesCompat.getFont(getContext(), R.font.font1))//optional
                .setTitleTypeFace(ResourcesCompat.getFont(getContext(), R.font.font1))//optional
                .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
                .setGravity(Gravity.center)//optional
                .setGuideListener(listener)
                .build()
                .show();
    }

    public static class FoldersName {
        public static final String CHAT_IMAGES = "ChatsImages";
        public static final String ANSWERS_IMAGES = "AnswersImages";
        public static final String NEWS_IMAGES = "NewsImages";


    }

}
