package com.example.administrator.onediscountfreeship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class CategorySearch_Activity extends AppCompatActivity {

    private TextView filtrate_name;
    private String name;
    private int bc;
    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search_);

        filtrate_name = (TextView) findViewById(R.id.CategorySearch_filtrate_name);
        mListview = (ListView) findViewById(R.id.CategorySearch_listView);

        Bundle bundle = getIntent().getBundleExtra("filtrate");
        name = bundle.getString("name");
        bc = bundle.getInt("position");

        filtrate_name.setText(name);


    }
}
