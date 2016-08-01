package com.example.administrator.onediscountfreeship;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import utils.DBHelperOne;
import utils.DBHelperTwo;

public class NicknameActivity extends AppCompatActivity {
    //声明控件
    private EditText editText_nickname;
    //定义数据库相关变量
    private DBHelperOne dbHelperOne;
    private DBHelperTwo dbHelperTwo;
    private SQLiteDatabase db, dbPhone;
    //定义一个string类型变量
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        //获得控件
        editText_nickname = (EditText) findViewById(R.id.editText_nickname);
        //获得dbHelperOne,dbHelperTwo
        dbHelperOne = new DBHelperOne(this);
        dbHelperTwo = new DBHelperTwo(this);
        //获得db,dbPhone
        db = dbHelperOne.getReadableDatabase();
        dbPhone = dbHelperTwo.getReadableDatabase();

//        //删除数据库
//        String sql = "delete from nickname";
//        Cursor cursor = db.rawQuery(sql, null);
//        int count = cursor.getCount();
//        Log.e("TAG", "===>count" + count);
    }

    //添加单击事件
    public void onClick_back(View view) {
        //获得一个intent，进行页面跳转
        Intent intent = new Intent(NicknameActivity.this, PersonalInfoActivity.class);
        //执行页面跳转
        startActivity(intent);
    }

    //添加单击事件
    public void onClick_save(View view) {
        //定义一个字符串变量，来存昵称
        String name = editText_nickname.getText().toString();
        //if判断语句（判断输入是否为空）
        if (!TextUtils.isEmpty(name)) {
            //定义需要执行的sql语句
            String sql = "select * from nickname1 where phone = phone";
            //执行sql语句
            Cursor cursor = db.rawQuery(sql, null);
            //定义一个int型变量，来接收返回值
            int count = cursor.getCount();
            Log.e("TAG", "======NicknameCount" + count);
            //if判断语句（判断返回值是否为零，如果为零，则数据库不存在数据；反之，则存在数据）
            if (count == 0) {
                //获得一个ContentValues对象
                ContentValues values = new ContentValues();
                //向values添加值
                values.put("name", name);
                //存入数据库，并定义一个long型变量来接收返回值
                long id = db.insert("nickname1", null, values);
                //if判断语句（如果返回值是-1，则存入数据库失败；反之，存入数据库成功）
                if (id != -1) {
                    //获得一个intent，进行页面跳转
                    Intent intent = new Intent(NicknameActivity.this, PersonalInfoActivity.class);
                    //执行页面跳转
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_LONG).show();
                }
            } else {
                //定义一个字符串类型的变量，来存放手机号码
                //获得需要执行的sql语句
                String sqlPhone = "select * from tag1 where phone = phone";
                //执行sql语句
                Cursor cursorPhone = dbPhone.rawQuery(sqlPhone, null);
                //对cursorPhone进行遍历查询
                while (cursorPhone.moveToNext()) {
                    //定义一个字符串类型的变量，来存放手机号码
                    phone = cursorPhone.getString(cursorPhone.getColumnIndex("phone"));
                }
                //获得一个ContentValues对象values
                ContentValues values = new ContentValues();
                //向values中添加值
                values.put("name", name);
                //定义where子句
                String whereClause = "phone =?";
                //定义字符串数组
                String[] whereArgs = {phone};
                //定义一个int型的变量，来接收返回值并判断（如果为-1,则修改不成功；反之，则修改成功）
                int id = db.update("nickname1", values, whereClause, whereArgs);
                //if判断语句
                if (id > 0) {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
                    //获得一个intent，进行页面跳转
                    Intent intent = new Intent(NicknameActivity.this, PersonalInfoActivity.class);
                    //执行页面跳转
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_LONG).show();
        }
    }
}
