package com.example.dm391.dynamiclayout;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Jay on 01-10-2017.
 */
public class RuntimeInflaterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    View v;
    ViewGroup vg;

    List<CustomView> items;
    Context context;

    int type;

    RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {

        void onClick();
    }

    public RuntimeInflaterAdapter(Context ctx, int type, List<CustomView> items, RecyclerViewClickListener listener) {
        this.context = ctx;
        this.type = type;
        this.items = items;
        this.mListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     /*   View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_edittext, parent, false);
        return new ViewHolderEditText(view);*/
        View view = null;

        switch (viewType) {
            case 1:
                return new ViewHolderEditText(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_edittext, parent, false));
            case 2:
                return new ViewHolderRadioButton(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_radiobutton, parent, false));
            case 3:
                return new ViewHolderSpinner(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_spinner, parent, false));
            case 4:
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user2_again, parent, false);
                break;
        }
        return null;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setPadding(5, 5, 5, 5);
        switch (holder.getItemViewType()) {
            case 1:
                final ViewHolderEditText v1 = (ViewHolderEditText) holder;
                if (type == 0) {
                    if (!items.get(position).isCompleted() && !items.get(position).isHidden()) {
                        v1.cv.setId(items.get(position).getResIdCardView());
                        v1.et.setId(items.get(position).getResId());
                        //holder.et.setHint(items.get(position).getHint());
                        v1.tl.setHint(items.get(position).getHint());
                        v1.et.setText(items.get(position).getUserText());
                        v1.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (v1.et.getText().toString().length() > 0 && !hasFocus) {
                                    items.get(position).setUserText(v1.et.getText().toString());
                                    items.get(position).setCompleted(true);
                                    slideToRight(v1.cv);

                                }
                            }
                        });
                        v1.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    if (v1.et.getText().toString().length() > 0) {
                                        items.get(position).setUserText(v1.et.getText().toString());
                                        items.get(position).setCompleted(true);
                                        slideToRight(v1.cv);

                                    }
                                }
                                return false;
                            }
                        });
                    } else {
                        v1.cv.setVisibility(View.GONE);
                    }
                } else {
                    if (items.get(position).isCompleted()) {
                        v1.cv.setId(items.get(position).getResIdCardView());
                        v1.et.setId(items.get(position).getResId());
                        //holder.et.setHint(items.get(position).getHint());
                        v1.tl.setHint(items.get(position).getHint());
                        v1.et.setText(items.get(position).getUserText());
                        v1.et.setSelection(v1.et.getText().length());
                        v1.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (v1.et.getText().toString().length() == 0 && !hasFocus) {
                                    items.get(position).setUserText("");
                                    items.get(position).setCompleted(false);
                                    slideToLeft(v1.cv);
                                } else //if text was changed by user in completed
                                {
                                    items.get(position).setUserText(v1.et.getText().toString());
                                }
                            }
                        });
                        v1.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    if (v1.et.getText().toString().length() == 0) {
                                        items.get(position).setUserText("");
                                        items.get(position).setCompleted(false);
                                        slideToLeft(v1.cv);
                                    }
                                }
                                return false;
                            }
                        });
                    } else {
                        v1.cv.setVisibility(View.GONE);
                    }
                }
                break;//end of case1

            case 2:
                final ViewHolderRadioButton v2 = (ViewHolderRadioButton) holder;
                if (type == 0) {
                    if (!items.get(position).isCompleted()) {
                        //SET TITLE
                        v2.radioGroupTitle.setText(items.get(position).getHint());
                        v2.rdGroup.setOrientation(RadioGroup.VERTICAL);
                        //create radio buttons based on Array Size
                        for (int i = 0; i < items.get(position).getItems().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(items.get(position).getItems().get(i));
                            radioButton.setId(i);
                            v2.rdGroup.addView(radioButton);
                        }
                        //set listener to radio button group
                        v2.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int checkedRadioButtonId = v2.rdGroup.getCheckedRadioButtonId();
                                RadioButton radioBtn = (RadioButton) v2.rdGroup.findViewById(checkedRadioButtonId);
                                //Toast.makeText(context, radioBtn.getId() + "", Toast.LENGTH_SHORT).show();
                                //also save the index in list
                                items.get(position).setIndex(radioBtn.getId());
                                items.get(position).setCompleted(true);
                                slideToRight(v2.cv);

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isHidden() /*&& items.get(i).getResId()==31 ||
                                            items.get(i).getResId()==32*/) {
                                        items.get(i).setHidden(false);
                                    }
                                }
                                mListener.onClick();
                                //notifyItemChanged(v2.getAdapterPosition());
                                //notifyDataSetChanged();
                            }
                        });

                    } else {
                        //NOT CURRENT WAS COMPLETED
                        v2.cv.setVisibility(View.GONE);
                    }

                }//type= 1
                else {
                    //BIND VALUES BACK
                    if (items.get(position).isCompleted()) {
                        //SET TITLE
                        v2.radioGroupTitle.setText(items.get(position).getHint());
                        v2.rdGroup.setOrientation(RadioGroup.HORIZONTAL);
                        //create radio buttons based on Array Size
                        for (int i = 0; i < items.get(position).getItems().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(items.get(position).getItems().get(i));
                            radioButton.setId(i);
                            v2.rdGroup.addView(radioButton);
                        }
                        //SET To SELECTED
                        ((RadioButton) v2.rdGroup.getChildAt(items.get(position).getIndex())).setChecked(true);

                        //Also make sure to take listener in completed
                        v2.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int checkedRadioButtonId = v2.rdGroup.getCheckedRadioButtonId();
                                RadioButton radioBtn = (RadioButton) v2.rdGroup.findViewById(checkedRadioButtonId);
                                //Toast.makeText(context, radioBtn.getId() + "", Toast.LENGTH_SHORT).show();

                                //also save the index in list
                                items.get(position).setIndex(radioBtn.getId());
                                items.get(position).setCompleted(true);
                            }
                        });
                    } else {
                        //NOT COMPLETED WAS CURRENT
                        v2.cv.setVisibility(View.GONE);
                    }
                }
                break;//end of case2
            case 3:
                final ViewHolderSpinner v3 = (ViewHolderSpinner) holder;
                ArrayAdapter<String> arrayAdapter;
                arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, items.get(position).getItems());
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                //((ViewHolderSpinner) holder).spinner.setAdapter(arrayAdapter);
                v3.spinner.setHint(items.get(position).getHint());
                v3.spinner.setFloatingLabelText("Select " + items.get(position).getHint());
                v3.spinner.setAdapter(arrayAdapter);
                v3.spinner.setOnItemSelectedListener(null);
                switch (type) {
                    case 0:
                        if (!items.get(position).isCompleted()) {

                            v3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i >= 0) {//EMPTY SELECT is -1
                                        String val = v3.spinner.getItemAtPosition(i).toString();
                                        //Toast.makeText(context, i + "", Toast.LENGTH_SHORT).show();
                                        items.get(position).setIndex(i);
                                        items.get(position).setCompleted(true);
                                        //MOVE TO RIGHT
                                        slideToRight(v3.cv);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        } else {
                            //NOT CURRENT WAS COMPLETED
                            v3.cv.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        if (items.get(position).isCompleted()) {
                            v3.spinner.setOnItemSelectedListener(null);
                            v3.spinner.setSelection(items.get(position).getIndex() + 1, false);
                            v3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i >= 0) {//EMPTY SELECT is -1
                                        String val = v3.spinner.getItemAtPosition(i).toString();
                                        //Toast.makeText(context, i + "", Toast.LENGTH_SHORT).show();
                                        items.get(position).setIndex(i);
                                        items.get(position).setCompleted(true);
                                    } else {
                                        items.get(position).setIndex(i);
                                        items.get(position).setCompleted(false);
                                        slideToLeft(v3.cv);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        } else {
                            //NOT COMPLETED WAS CURRENT
                            v3.cv.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }

                break;//end of case3


        }//end of switch
    }//end of onBindViewHolder()

    private TranslateAnimation animate;

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
                view.setPadding(0, 0, 0, 0);
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

    public class ViewHolderEditText extends RecyclerView.ViewHolder {
        private CardView cv;
        private EditText et;
        private TextInputLayout tl;
        //LinearLayout linearLayout;

        public ViewHolderEditText(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            et = (EditText) view.findViewById(R.id.et);
            tl = (TextInputLayout) view.findViewById(R.id.tl);
            //linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        }
    }

    public class ViewHolderRadioButton extends RecyclerView.ViewHolder {
        @BindView(R.id.cv)
        CardView cv;

        @BindView(R.id.rdGroup)
        RadioGroup rdGroup;

        @BindView(R.id.radioGroupTitle)
        TextView radioGroupTitle;

        public ViewHolderRadioButton(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderSpinner extends RecyclerView.ViewHolder {
        @BindView(R.id.cv)
        CardView cv;

        @BindView(R.id.spinner)
        MaterialSpinner spinner;

        public ViewHolderSpinner(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

      /*  @OnClick(R.id.spinner)
        public void onClick() {

        }*/


    }


    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }
}