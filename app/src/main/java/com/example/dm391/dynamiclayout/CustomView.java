package com.example.dm391.dynamiclayout;

/**
 * Created by Jay on 01-10-2017.
 */
public class CustomView {


    private String hint;
    private int resIdCardView;
    private int resIdEditText;
    private boolean isCompleted;
    private String userText ;

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public CustomView(int resIdCardView, int resIdEditText, String hint, boolean isCompleted) {
        this.hint = hint;
        this.resIdCardView = resIdCardView;
        this.resIdEditText = resIdEditText;
        this.isCompleted = isCompleted;
    }


    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getResIdCardView() {
        return resIdCardView;
    }

    public void setResIdCardView(int resIdCardView) {
        this.resIdCardView = resIdCardView;
    }

    public int getResIdEditText() {
        return resIdEditText;
    }

    public void setResIdEditText(int resIdEditText) {
        this.resIdEditText = resIdEditText;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}