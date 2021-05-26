package com.ahmed.m.hassaan.candleapp.utils.custom_dialog;

import androidx.annotation.DrawableRes;

import com.ahmed.m.hassaan.candleapp.R;

public class CustomDialogModel {

    private String title;
    private String message;
    private String btnPosText;
    private String btnNegText;
    private String btnOtherText;
    private OkButtonListener okListener;
    private NoButtonListener noListener;
    private CancelButtonListener cancelListener;
    private boolean isCancelable;
    private int imageRes;


    public CustomDialogModel() {
    }

    public CustomDialogModel(String title, String message, String btnPosText, OkButtonListener okListener) {
        this(title, message, btnPosText, "", "", okListener, null, null, false, R.drawable.ic_error_outline_white_24dp);
    }

    public CustomDialogModel(String title, String message, String btnPosText, OkButtonListener okListener, String cnclBtn, CancelButtonListener cancelListener) {
        this(title, message, btnPosText, "", cnclBtn, okListener, null, cancelListener, false, R.drawable.ic_error_outline_white_24dp);
    }

    public CustomDialogModel(String title, String message, String btnPosText, OkButtonListener okListener, String cnclBtn, CancelButtonListener cancelListener, @DrawableRes int image) {
        this(title, message, btnPosText, "", cnclBtn, okListener, null, cancelListener, false, image);
    }

    public CustomDialogModel(String title, String message, String btnNegText, NoButtonListener noListener, String cnclBtn, CancelButtonListener cancelListener, @DrawableRes int image) {
        this(title, message, "", btnNegText, cnclBtn, null, noListener, cancelListener, false, image);
    }

    public CustomDialogModel(String title, String message, String btnPosText, String btnNegText, String btnOtherText, OkButtonListener okListener, NoButtonListener noListener, CancelButtonListener cancelListener, boolean isCancelable, int imageRes) {
        this.title = title;
        this.message = message;
        this.btnPosText = btnPosText;
        this.btnNegText = btnNegText;
        this.btnOtherText = btnOtherText;
        this.okListener = okListener;
        this.noListener = noListener;
        this.cancelListener = cancelListener;
        this.isCancelable = isCancelable;
        this.imageRes = imageRes == 0 ? R.drawable.ic_error_outline_white_24dp : imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBtnPosText() {
        return btnPosText;
    }

    public void setBtnPosText(String btnPosText) {
        this.btnPosText = btnPosText;
    }

    public String getBtnNegText() {
        return btnNegText;
    }

    public void setBtnNegText(String btnNegText) {
        this.btnNegText = btnNegText;
    }

    public String getBtnOtherText() {
        return btnOtherText;
    }

    public void setBtnOtherText(String btnOtherText) {
        this.btnOtherText = btnOtherText;
    }

    public OkButtonListener getOkListener() {
        return okListener;
    }

    public void setOkListener(OkButtonListener okListener) {
        this.okListener = okListener;
    }

    public NoButtonListener getNoListener() {
        return noListener;
    }

    public void setNoListener(NoButtonListener noListener) {
        this.noListener = noListener;
    }

    public CancelButtonListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(CancelButtonListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
