package com.example.dm391.dynamiclayout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jay on 01-10-2017.
 */
public class CustomView {

    @SerializedName("viewType")
    @Expose
    private Integer viewType;

    @SerializedName("hint")
    @Expose
    private String hint;

    @SerializedName("isCompleted")
    @Expose
    private boolean isCompleted;

    @SerializedName("isHidden")
    @Expose
    private boolean isHidden;

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @SerializedName("resIdCardView")
    @Expose
    private Integer resIdCardView;

  /*  @SerializedName("resIdEditText")
    @Expose
    private Integer resIdEditText;*/

    @SerializedName("resId")
    @Expose
    private Integer resId;

    @SerializedName("items")
    @Expose
    private List<String> items = null;

    @SerializedName("index")
    @Expose
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    private String userText;

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public CustomView(int resIdCardView, int resId, String hint, boolean isCompleted) {
        this.hint = hint;
        this.resIdCardView = resIdCardView;
        //this.resIdEditText = resIdEditText;
        this.resId = resId;
        this.isCompleted = isCompleted;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getResIdCardView() {
        return resIdCardView;
    }

    public void setResIdCardView(Integer resIdCardView) {
        this.resIdCardView = resIdCardView;
    }

  /*  public Integer getResIdEditText() {
        return resIdEditText;
    }

    public void setResIdEditText(Integer resIdEditText) {
        this.resIdEditText = resIdEditText;
    }*/

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}