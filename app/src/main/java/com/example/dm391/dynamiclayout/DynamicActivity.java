package com.example.dm391.dynamiclayout;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener, RuntimeInflaterAdapter.RecyclerViewClickListener
        //,SwipeLinearLayout.OnSwipeListener
        //View.OnTouchListener
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RuntimeInflaterAdapter mAdapter;
    Button button1, button2;
    RelativeLayout relLay;

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        //findViewById(R.id.l1).setOnTouchListener(new SwipeLinearLayout(this));
        //relLay = (RelativeLayout) findViewById(R.id.relLay);
        //relLay.setOnTouchListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //recyclerView.setOnTouchListener(this);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(false);
        //recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new RuntimeInflaterAdapter(this, type, prepareForm(), this);



       /* Gson gson = new Gson();
        String json =gson.toJson(returnViews());
        Log.i("TAG",json.toString());
        int x= 5;*/

        //recyclerView.setAdapter(new RuntimeInflaterAdapter(type, returnViews()));
        recyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();

    }


   /* private List<CustomView> returnViews() {
        return Arrays.asList(
                new CustomView(1, 1, "Enter 1", false),
                new CustomView(2, 2, "Enter 2", false),
                new CustomView(3, 3, "Enter 3", false),
                new CustomView(4, 4, "Enter 4", false),
                new CustomView(5, 5, "Enter 5", false),
                new CustomView(6, 6, "Enter 6", false),
                new CustomView(7, 7, "Enter 7", false),
                new CustomView(8, 8, "Enter 8", false),
                new CustomView(9, 9, "Enter 9", false),
                new CustomView(10, 10, "Enter 10", false)

        );
    }*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                hideSoftKeyboard(findViewById(R.id.l1));
                type = 0;//CURRENT
                setBackgroundToButtons(type);
                recyclerView.setAdapter(new RuntimeInflaterAdapter(this, type, mAdapter.items, this));
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.button2:
                hideSoftKeyboard(findViewById(R.id.l1));
                type = 1;//COMPLETED
                setBackgroundToButtons(type);
                recyclerView.setAdapter(new RuntimeInflaterAdapter(this, type, mAdapter.items, this));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundToButtons(int val) {
        button1.setBackground
                (ContextCompat.getDrawable(this,
                        (val == 0) ? R.drawable.tab_background_bottom_orange_line : R.color.white));

        button2.setBackground
                (ContextCompat.getDrawable(this,
                        (val == 1) ? R.drawable.tab_background_bottom_orange_line : R.color.white));
    }

    public void hideSoftKeyboard(View v) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public List<CustomView> prepareForm() {
        try {
            List<CustomView> viewList = new ArrayList<>();
            InputStream stream = getAssets().open("data.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String data = builder.toString();
            viewList = new Gson().fromJson(data, new TypeToken<List<CustomView>>() {
            }.getType());
            return viewList;
        } catch (Exception e) {
            //java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
            //dont keep root key...
            e.printStackTrace();
        }
        return null;
    }

    static final int MIN_DISTANCE = 100;// TODO change this runtime based on screen resolution. for 1920x1080 is to small the 100 distance
    private float downX, downY, upX, upY;
    static final String logTag = "ActivitySwipeDetector";

   /* public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                return false; // no swipe horizontally and no swipe vertically
            }// case MotionEvent.ACTION_UP:
        }
        return false;
    }*/

    public void onRightToLeftSwipe() {

    }

/*    public void onLeftToRightSwipe() {
        hideSoftKeyboard(findViewById(R.id.l1));
        type = 0;//CURRENT
        setBackgroundToButtons(type);
        recyclerView.setAdapter(new RuntimeInflaterAdapter(this, type, mAdapter.items));
        mAdapter.notifyDataSetChanged();
    }

    public void onTopToBottomSwipe() {
        hideSoftKeyboard(findViewById(R.id.l1));
        type = 1;//COMPLETED
        setBackgroundToButtons(type);
        recyclerView.setAdapter(new RuntimeInflaterAdapter(this, type, mAdapter.items));
        mAdapter.notifyDataSetChanged();
    }*/

    public void onBottomToTopSwipe() {

    }

    @Override
    public void onClick() {
        recyclerView.setAdapter(new RuntimeInflaterAdapter(this, 0, mAdapter.items, this));
        mAdapter.notifyDataSetChanged();
    }

   /* @Override
    public void onSwipeLeft() {

    }*/
}
