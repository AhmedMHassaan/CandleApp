package com.ahmed.m.hassaan.candleapp.utils.dynamicbox;

import android.app.Activity;
import android.content.Context;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ahmed.m.hassaan.candleapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class DynamicBox {

    private View mTargetView;
    private View.OnClickListener mClickListener;
    private Context mContext;
    private LayoutInflater mInflater;
    private RelativeLayout mContainer;
    private ArrayList<View> mCustomViews;
    private ArrayList<View> mDefaultViews;
    private ViewSwitcher mSwitcher;

    // Default Tags
    private final String TAG_INTERNET_OFF = "INTERNET_OFF";
    private final String TAG_LOADING_CONTENT = "LOADING_CONTENT";
    private final String TAG_OTHER_EXCEPTION = "OTHER_EXCEPTION";
    private final String TAG_INPUT = "DYNAMIC_INPUT";

    private final String[] mSupportedAbsListViews = new String[]{"listview", "gridview", "expandablelistview"};
    private final String[] mSupportedViews = new String[]{"linearlayout", "relativelayout", "framelayout", "scrollview", "recyclerview", "viewgroup"};

    public DynamicBox(Context context, View targetView) {
        this.mContext = context;
        this.mInflater = ((Activity) mContext).getLayoutInflater();
        this.mTargetView = targetView;
        this.mContainer = new RelativeLayout(mContext);
        this.mCustomViews = new ArrayList<>();
        this.mDefaultViews = new ArrayList<>();

        Class viewClass = mTargetView.getClass();
        Class superViewClass = viewClass.getSuperclass();
        String viewType = viewClass.getName().substring(viewClass.getName().lastIndexOf('.') + 1).toLowerCase(Locale.getDefault());
        String superViewType = superViewClass.getName().substring(superViewClass.getName().lastIndexOf('.') + 1).toLowerCase(Locale.getDefault());

        if (Arrays.asList(mSupportedAbsListViews).contains(viewType) || Arrays.asList(mSupportedAbsListViews).contains(superViewType))
            initializeAbsListView();
        else if (Arrays.asList(mSupportedViews).contains(viewType) || Arrays.asList(mSupportedViews).contains(superViewType))
            initializeViewContainer();
        else
            throw new IllegalArgumentException("TargetView type [" + superViewType + "] is not supported !");

    }

    public DynamicBox(Context context, int viewID) {
        this.mContext = context;
        this.mInflater = ((Activity) mContext).getLayoutInflater();
        this.mContainer = new RelativeLayout(mContext);
        this.mTargetView = mInflater.inflate(viewID, null, false);
        this.mCustomViews = new ArrayList<View>();
        this.mDefaultViews = new ArrayList<View>();

        Class viewClass = mTargetView.getClass();
        Class superViewClass = viewClass.getSuperclass();
        String viewType = viewClass.getName().substring(viewClass.getName().lastIndexOf('.') + 1).toLowerCase(Locale.getDefault());
        String superViewType = superViewClass.getName().substring(superViewClass.getName().lastIndexOf('.') + 1).toLowerCase(Locale.getDefault());

        if (Arrays.asList(mSupportedAbsListViews).contains(viewType) || Arrays.asList(mSupportedAbsListViews).contains(superViewType))
            initializeAbsListView();
        else if (Arrays.asList(mSupportedViews).contains(viewType) || Arrays.asList(mSupportedViews).contains(superViewType))
            initializeViewContainer();
        else
            throw new IllegalArgumentException("TargetView type [" + superViewType + "] is not supported !");

    }

    private void initializeViewContainer() {

        setDefaultViews();

        mSwitcher = new ViewSwitcher(mContext);
        ViewSwitcher.LayoutParams params = new ViewSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mSwitcher.setLayoutParams(params);

        ViewGroup group = (ViewGroup) mTargetView.getParent();
        int index = 0;
        Clonner target = new Clonner(mTargetView);
        if (group != null) {
            index = group.indexOfChild(mTargetView);
            group.removeView(mTargetView);

        }

        mSwitcher.addView(mContainer, 0);
        mSwitcher.addView(target.getmView(), 1);
        mSwitcher.setDisplayedChild(1);

        if (group != null) {
            group.addView(mSwitcher, index);
        } else {
            ((Activity) mContext).setContentView(mSwitcher);
        }


    }

    private void setDefaultViews() {
        View mLayoutInternetOff = initView(R.layout.exception_no_internet, TAG_INTERNET_OFF);
        View mLayoutLoadingContent = initView(R.layout.exception_loading_content, TAG_LOADING_CONTENT);
        View mLayoutOther = initView(R.layout.exception_failure, TAG_OTHER_EXCEPTION);
        View mInputLayout = initView(R.layout.dynamic_boc_input, TAG_INPUT);

        mDefaultViews.add(0, mLayoutInternetOff);
        mDefaultViews.add(1, mLayoutLoadingContent);
        mDefaultViews.add(2, mLayoutOther);
        mDefaultViews.add(3, mInputLayout);

        // Hide all layouts at first initialization
        mLayoutInternetOff.setVisibility(View.GONE);
        mLayoutLoadingContent.setVisibility(View.GONE);
        mLayoutOther.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.GONE);

        // init Layout params
        RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        containerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        containerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        // init new RelativeLayout Wrapper
        mContainer.setLayoutParams(containerParams);

        // Add default views
        mContainer.addView(mLayoutLoadingContent);
        mContainer.addView(mLayoutInternetOff);
        mContainer.addView(mLayoutOther);
        mContainer.addView(mInputLayout);
    }

    private void initializeAbsListView() {

        setDefaultViews();

        AbsListView abslistview = (AbsListView) mTargetView;
        abslistview.setVisibility(View.GONE);
        ViewGroup parent = (ViewGroup) abslistview.getParent();
        if (mContainer != null) {
            parent.addView(mContainer);
            abslistview.setEmptyView(mContainer);
        } else
            throw new IllegalArgumentException("mContainer is null !");

    }

    public void showLoadingLayout() {
        showLoadingLayout("جار تحليل البيانات");
    }

    public void showLoadingLayout(String message) {
        setLoadingMessage(message);
        show(TAG_LOADING_CONTENT);
    }

    public void showInternetOffLayout(View.OnClickListener listener) {
//        mDefaultViews.get(0).findViewById(R.id.exception_button).setOnClickListener(listener);
        setClickListener(listener);
        show(TAG_INTERNET_OFF);
    }


    public void showCustomView(String tag) {
        show(tag);
    }

    public void hideAll() {
        setOtherButtonText(mContext.getString(R.string.dynamicbox_retry));
        ArrayList<View> views = new ArrayList<>(mDefaultViews);
        views.addAll(mCustomViews);
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
        if (mSwitcher != null) {
            mSwitcher.setDisplayedChild(1);
        }
    }

    private void show(String tag) {
        ArrayList<View> views = new ArrayList<View>(mDefaultViews);
        views.addAll(mCustomViews);
        for (View view : views) {
            if (view.getTag() != null && view.getTag().toString().equals(tag)) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
        if (mSwitcher != null && mSwitcher.getDisplayedChild() != 0) {
            mSwitcher.setDisplayedChild(0);
        }
    }

    /**
     * Return a view based on layout id
     *
     * @param layout Layout Id
     * @param tag    Layout Tag
     * @return View
     */
    private View initView(int layout, String tag) {
        View view = mInflater.inflate(layout, null, false);

        view.setTag(tag);
        view.setVisibility(View.GONE);

        View buttonView = view.findViewById(R.id.exception_button);
        if (buttonView != null)
            buttonView.setOnClickListener(this.mClickListener);

        return view;
    }


    private void setLoadingMessage(String message) {
        ((TextView) mDefaultViews.get(1).findViewById(R.id.exception_message)).setText(message);
    }

  /*  private void setLoadingPageBackgroundColor(int color) {
         mDefaultViews.get(1).setBackgroundColor(color);
    }*/

    public void setInternetOffMessage(String message) {
        ((TextView) mDefaultViews.get(0).findViewById(R.id.exception_message)).setText(message);
    }

    public void setOtherButtonText(String text) {
        if ("".equals(text) || text == null) {
            ((Button) mDefaultViews.get(2).findViewById(R.id.exception_button)).setText(R.string.ok);
        } else {
            ((Button) mDefaultViews.get(2).findViewById(R.id.exception_button)).setText(text);
        }
    }

    public void setOtherButtonText() {
        setOtherButtonText(null);
    }

    public void setOtherException(String title, String message, String btnText, View.OnClickListener listener) {
        if (title == null || title.isEmpty())
            mContext.getString(R.string.newMessage);
        ((TextView) mDefaultViews.get(2).findViewById(R.id.exception_title)).setText(title);

        ((TextView) mDefaultViews.get(2).findViewById(R.id.exception_message)).setText(message);
        Linkify.addLinks(((TextView) mDefaultViews.get(2).findViewById(R.id.exception_message)), Linkify.ALL);
        setOtherButtonText(btnText);
        if (listener == null) listener = v -> hideAll();
        mDefaultViews.get(2).findViewById(R.id.exception_button).setOnClickListener(listener);
        show(TAG_OTHER_EXCEPTION);
    }

    public void setOtherException(String title, String message, View.OnClickListener listener) {
        setOtherException(title, message, null, listener);
    }

    public void setOtherException(String message, View.OnClickListener listener) {
        setOtherException(null, message, null, listener);
    }

    private void showEditText() {
        show(TAG_INPUT);
    }

    public void setOtherException(String message) {
        setOtherException(null, message, null, null);
    }

    public void setInternetOff(String title, String message, View.OnClickListener listener) {
        ((TextView) mDefaultViews.get(0).findViewById(R.id.exception_title)).setText(title);
        ((TextView) mDefaultViews.get(0).findViewById(R.id.exception_message)).setText(message);
        mDefaultViews.get(0).findViewById(R.id.exception_button).setOnClickListener(listener);
    }

    public void setClickListener(View.OnClickListener clickListener) {

        this.mClickListener = clickListener;

        for (View view : mDefaultViews) {
            View buttonView = view.findViewById(R.id.exception_button);
            if (buttonView != null)
                buttonView.setOnClickListener(this.mClickListener);
        }


    }

    public void addCustomView(View customView, String tag) {

        customView.setTag(tag);
        customView.setVisibility(View.GONE);
        mCustomViews.add(customView);
        mContainer.addView(customView);
    }

    private View getInputLayout() {
        return mDefaultViews.get(3);
    }

    private static class Clonner {
        private View mView;

        public Clonner(View view) {
            this.setmView(view);
        }

        public View getmView() {
            return mView;
        }

        public void setmView(View mView) {
            this.mView = mView;
        }
    }

    public static class InputBuilder {
        DynamicBox dynamicBox;
        String layoutHint;
        String buttonText;
        View.OnClickListener btnListener;
        //        TextWatcher textWatcher;
        String text;

        public InputBuilder(DynamicBox dynamicBox) {
            this.dynamicBox = dynamicBox;
        }

        public InputBuilder setLayoutHint(String layoutHint) {
            this.layoutHint = layoutHint;
            return this;
        }


        public InputBuilder setButtonText(String buttonText) {
            this.buttonText = buttonText;
            return this;
        }

        public InputBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public InputBuilder setBtnListener(View.OnClickListener btnListener) {
            this.btnListener = btnListener;
            return this;
        }

        private InputBuilder setTextListener(TextWatcher textListener) {
//            this.textWatcher = textListener;
            return this;
        }

        public void build() {
            if (dynamicBox == null)
                throw new IllegalArgumentException("Dynamicbox cant be null");
            View layout = dynamicBox.getInputLayout();
            if (layout == null)
                throw new IllegalArgumentException("Layout cant be null");
            TextInputLayout inputLayout = layout.findViewById(R.id.txtInputLayout);
            EditText editText = layout.findViewById(R.id.txtInput);
            Button btnInput = layout.findViewById(R.id.btnEnterInput);

            inputLayout.setHint(layoutHint);
            editText.setText(text);
            btnInput.setText(buttonText);
            if (btnListener == null)
                btnListener = v -> dynamicBox.hideAll();
            btnInput.setOnClickListener(btnListener);

            dynamicBox.showEditText();
        }


        public String getEditText() {
            EditText editText = dynamicBox.getInputLayout().findViewById(R.id.txtInput);

            return editText.getText().toString();

        }


    }

}