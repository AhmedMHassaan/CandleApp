package com.ahmed.m.hassaan.candleapp.data.pojo;

import android.content.Context;

import androidx.annotation.Keep;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.utils.App;
import com.ahmed.m.hassaan.candleapp.utils.Tools;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.CustomDialogModel;
import com.ahmed.m.hassaan.candleapp.utils.custom_dialog.OkButtonListener;
import com.google.gson.annotations.SerializedName;

@Keep
public class ResponseSchema<T> {


    @SerializedName("code")
    private int resultCode;  // 0 for failed , 1 for success

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private T response;

    public ResponseSchema(int resultCode, String message, T response) {
        this.resultCode = resultCode;
        this.message = message;
        this.response = response;
    }

    public ResponseSchema(int resultCode, String message) {
        this(resultCode, message, null);
    }

    public ResponseSchema(Throwable t) {
        this(0, t.getMessage());
    }

    public ResponseSchema(int resultCode) {
        this(resultCode, App.mACTIVITY.getString(R.string.notSuccessfulResponse));
    }

    public ResponseSchema() {
        this(0);
    }

    public boolean isSuccessful() {
        return resultCode == 1 && response != null;
    }

    /*public boolean isErrorInInternet(){

        if (message == null) {
            return true;
        }

        message = message.toLowerCase();
        return "".equals(message) || message.contains("unable") || message.contains("timeout") || message.contains("validator") || message.contains("timed") || message.contains("http://ahmedmhassaan.000webhostapp.com");
    }*/

    private int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void showError(Context context) {
        showError(context, null);
    }

    public void showError(Context context, OkButtonListener listener) {

        Tools tools = new Tools(context);
        if (message == null || "".equals(message))
            message = "حدث خطأ بدون وجود أي رسائل";

        if (tools.isErrorInInternet(message, false))
            message = "حدث خطأ أثناء الإتصال بالخادم\nيرجي التحقق من الإنصال بالإنترنت و إعادة المحاولة";

        tools.customMessage(new CustomDialogModel(
                "حدث خطأ",
                message,
                "حسنا فهمت",
                listener
        ));
    }
}
