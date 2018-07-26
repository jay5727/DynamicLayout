package com.example.dm391.dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linLay);

        LinearLayout llh=new LinearLayout(this);
        //llm.setOrientation(LinearLayout.HORIZONTAL);

       /* Button btn = new Button(this);
        btn.setText("Manual Add");
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));*/
        ll.addView(prepareForm());
    }


    private View prepareForm() {
        LinearLayout l = new LinearLayout(this);
        TextView valueTV = new TextView(this);
        valueTV.setText("hallo hallo");
        valueTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        l.addView(valueTV);

        TextView valueTV2 = new TextView(this);
        valueTV2.setText("hallo2 hallo2");
        valueTV2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        l.addView(valueTV2);

        return l;
    }
}
