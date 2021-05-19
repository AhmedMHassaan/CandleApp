package com.ahmed.m.hassaan.candleapp.utils.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.databinding.CustomDialogBinding;
import com.ahmed.m.hassaan.candleapp.utils.Tools;

public class CustomDialog extends Dialog {

    CustomDialogBinding binding;
    boolean isCancelable = false;

    private CustomDialog(@NonNull Context context, CustomDialogModel dialogModel) {
        super(context, R.style.Theme_CustomDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding = CustomDialogBinding.bind(LayoutInflater.from(context).inflate(R.layout.custom_dialog, null, false));
        binding.setCustomDialogModel(dialogModel);
        binding.setDialog(this);
        binding.setTools(new Tools(context));

        setContentView(binding.getRoot());
        isCancelable = dialogModel.isCancelable();
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(isCancelable);


    }


    public void changeTitle(String title) {
        binding.lblDialogTitle.setText(title);
    }

    public void changeMessage(String msg) {
        binding.lblDialogMsg.setText(msg);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && isCancelable) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (isCancelable) {
            dismiss();
        }

    }


    public static class Builder {

        CustomDialogModel customDialogModel = new CustomDialogModel();
        Context mContext;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setTitle(String title) {
            customDialogModel.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            customDialogModel.setMessage(message);
            return this;
        }

        public Builder setOkButton(String btnText, @Nullable final OkButtonListener okListener) {
            customDialogModel.setBtnPosText(btnText);
//            if (okListener == null)
//                okListener = Dialog::dismiss;
//            OkButtonListener finalOkListener = okListener;

            OkButtonListener listener = dialog -> {
                dialog.dismiss();
                if (okListener != null)
                    okListener.onPositiveButtonClick(dialog);
            };
            customDialogModel.setOkListener(listener);
            System.gc();
            return this;
        }

        public Builder setNoButton(String btnText, @Nullable final NoButtonListener listener) {
            customDialogModel.setBtnNegText(btnText);
            NoButtonListener temp = dialog -> {
                dialog.dismiss();
                if (listener != null)
                    listener.onNegativeButtonClick(dialog);
            };
            customDialogModel.setNoListener(temp);
            return this;
        }

        public Builder setCancelButton(String btnText, @Nullable final CancelButtonListener listener) {
            customDialogModel.setBtnOtherText(btnText);

            CancelButtonListener tempListener = dialog -> {
                dialog.dismiss();
                if (listener != null)
                    listener.onOtherButtonClick(dialog);
            };
            customDialogModel.setCancelListener(tempListener);

            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            customDialogModel.setCancelable(cancelable);
            return this;
        }

        public Builder setImage(int image) {
            customDialogModel.setImageRes(image);
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(mContext, customDialogModel);
        }


    }

}
