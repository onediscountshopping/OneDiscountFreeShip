package com.example.administrator.onediscountfreeship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fragment.FrontPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment, new FrontPageFragment()).commit();
    }
}
