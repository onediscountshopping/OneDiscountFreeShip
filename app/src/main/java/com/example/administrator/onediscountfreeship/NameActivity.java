package com.example.administrator.onediscountfreeship;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import fragment.PersonalCenterFragment;
import utils.DBHelperName;

public class NameActivity extends AppCompatActivity {
    //声明控件
    private ImageView imageView_gsel, imageView_gnor;
    private ImageView imageView_wsel, imageView_wnor;
    private ImageView imageView_msel, imageView_mnor;
    //定义一个string类型变量
    private String name = "";
    //定义数据库相关的变量
    private DBHelperName dbHelperName;
    private SQLiteDatabase dbQuery, dbInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        //获得控件
        imageView_gsel = (ImageView) findViewById(R.id.imageView_gsel);
        imageView_gnor = (ImageView) findViewById(R.id.imageView_gnor);
        imageView_wsel = (ImageView) findViewById(R.id.imageView_wsel);
        imageView_wnor = (ImageView) findViewById(R.id.imageView_wnor);
        imageView_msel = (ImageView) findViewById(R.id.imageView_msel);
        imageView_mnor = (ImageView) findViewById(R.id.imageView_mnor);
        //获得数据库相关变量
        dbHelperName = new DBHelperName(this);
        dbQuery = dbHelperName.getReadableDatabase();
        dbInsert = dbHelperName.getReadableDatabase();
    }

    //添加单击事件（美女）
    public void onClick_meinv(View view) {
        //对各个控件进行相应设置
        imageView_wsel.setAlpha(0);
        imageView_gnor.setAlpha(0);
        //获得相应的值
        name = "美女";
    }

    //添加单击事件（辣妈）
    public void onClick_lama(View view) {
        //对各个控件进行相应设置
        imageView_wsel.setAlpha(100);
        imageView_gsel.setAlpha(0);
        imageView_gnor.setAlpha(100);
        imageView_msel.setAlpha(0);
        //获得相应的值
        name = "辣妈";
    }

    //添加单击事件（帅哥）
    public void onClick_shuaige(View view) {
        //对各个控件进行相应设置
        imageView_wsel.setAlpha(0);
        imageView_gsel.setAlpha(0);
        imageView_msel.setAlpha(100);
        //获得相应的值
        name = "帅哥";
    }

    //添加单击事件（确定）
    public void onClick_recommend(View view) {
        /**
         * 此处注释省略
         */
        String sqlQuery = "select * from name where name = name";
        Cursor cursorQuery = dbQuery.rawQuery(sqlQuery, null);
        int id = cursorQuery.getCount();
        Log.e("TAG", "hahahaha" + id);

        //..........................................................................................

        //获得一个intent进行页面跳转
        Intent intent = new Intent(NameActivity.this, PersonalCenterFragment.class);
        //向intent中添加键值对
        intent.putExtra("name", name);
        //执行页面跳转
        startActivity(intent);
    }
}
