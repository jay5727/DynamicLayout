package com.example.dm391.dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RuntimeInflaterAdapter mAdapter;
    Button button1, button2;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        //HomelayoutManager = new GridLayoutManager(this, 1);

        // recyclerView.setLayoutManager(HomelayoutManager);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(false);
        //recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new RuntimeInflaterAdapter(type, returnViews());



        Gson gson = new Gson();
        String json =gson.toJson(returnViews());
        int x= 5;

        recyclerView.setAdapter(new RuntimeInflaterAdapter(type, returnViews()));
        //mAdapter.notifyDataSetChanged();

    }


    private List<CustomView> returnViews() {

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                type = 0;
                recyclerView.setAdapter(new RuntimeInflaterAdapter(type, mAdapter.items));
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.button2:
                type = 1;
                recyclerView.setAdapter(new RuntimeInflaterAdapter(type, mAdapter.items));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
