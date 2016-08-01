package com.example.administrator.onediscountfreeship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddressInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);
    }

    //添加单击事件
    public void onClick_back(View view) {
        //获得一个intent，进行页面跳转
        Intent intent = new Intent(AddressInfoActivity.this, AddressActivity.class);
        //执行页面跳转
        startActivity(intent);
    }

    //添加单击事件
    public void onClick_save(View view) {
        //获得一个intent，进行页面跳转
        Intent intent = new Intent(AddressInfoActivity.this, AddressActivity.class);
        //执行页面跳转
        startActivity(intent);
    }
}
