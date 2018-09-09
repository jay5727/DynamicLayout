package com.example.dm391.dynamiclayout;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jay on 01-10-2017.
 */
public class RuntimeInflaterAdapter extends RecyclerView.Adapter<RuntimeInflaterAdapter.ViewHolder> {

    View v;
    ViewGroup vg;

    List<CustomView> items;
    //Context context;

    int type;

    public RuntimeInflaterAdapter(int type, List<CustomView> items) {
        this.type = type;
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (type == 0) {
            if (!items.get(position).isCompleted()) {
                holder.cv.setId(items.get(position).getResIdCardView());
                holder.et.setId(items.get(position).getResIdEditText());
                //holder.et.setHint(items.get(position).getHint());
                holder.tl.setHint(items.get(position).getHint());
                holder.et.setText(items.get(position).getUserText());
                holder.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (holder.et.getText().toString().length() > 0 && !hasFocus) {
                            items.get(position).setUserText(holder.et.getText().toString());
                            items.get(position).setCompleted(true);
                            slideToRight(holder.cv);
                        }
                    }
                });
                holder.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            if (holder.et.getText().toString().length() > 0) {
                                items.get(position).setUserText(holder.et.getText().toString());
                                items.get(position).setCompleted(true);
                                slideToRight(holder.cv);
                            }
                        }
                        return false;
                    }
                });
            } else {
                holder.cv.setVisibility(View.GONE);
            }
        } else {
            if (items.get(position).isCompleted()) {
                holder.cv.setId(items.get(position).getResIdCardView());
                holder.et.setId(items.get(position).getResIdEditText());
                //holder.et.setHint(items.get(position).getHint());
                holder.tl.setHint(items.get(position).getHint());
                holder.et.setText(items.get(position).getUserText());
                holder.et.setSelection(holder.et.getText().length());
                holder.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (holder.et.getText().toString().length() == 0 && !hasFocus) {
                            items.get(position).setUserText("");
                            items.get(position).setCompleted(false);
                            slideToLeft(holder.cv);
                        } else //if text was changed by user in completed
                        {
                            items.get(position).setUserText(holder.et.getText().toString());
                        }
                    }
                });
                holder.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            if (holder.et.getText().toString().length() == 0) {
                                items.get(position).setUserText("");
                                items.get(position).setCompleted(false);
                                slideToLeft(holder.cv);
                            }
                        }
                        return false;
                    }
                });
            } else {
                holder.cv.setVisibility(View.GONE);
            }
        }
    }

    TranslateAnimation animate;

    public void slideToRight(final View view) {
        animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void slideToLeft(final View view) {
        animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private EditText et;
        private TextInputLayout tl;
        //LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            et = (EditText) view.findViewById(R.id.et);
            tl = (TextInputLayout) view.findViewById(R.id.tl);
            //linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        }
    }


    /*@Override
    public int getItemViewType(int position) {
        return type;
    }*/
}