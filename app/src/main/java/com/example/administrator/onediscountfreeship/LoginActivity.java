package com.example.administrator.onediscountfreeship;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fragment.PersonalCenterFragment;
import utils.DBHelper;
import utils.DBHelperOne;
import utils.DBHelperTwo;

public class LoginActivity extends AppCompatActivity {
    //定义控件
    private EditText editText_login_phone, editText_login_password;
    //定义所要执行语句的字符串变量
    private String sql, sqlQuery;
    //定义数据库相关变量
    private DBHelper helper;
    private DBHelperTwo helperTwo;
    private DBHelperOne helperOne;
    private SQLiteDatabase db, dbQuery, dbInsert, dbNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获得控件
        editText_login_phone = (EditText) findViewById(R.id.editText_login_phone);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);

    }

    //添加单击事件
    public void onClick_register(View view) {
        //使用intent进行页面跳转
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        //执行页面跳转，并且发送请求码
        startActivity(intent);
    }

    //添加单击事件，进行登录（注意：在登录之前，先要进行判断）
    public void onClick_login(View view) {
        //获得登录时输入的手机号和密码
        String phone = editText_login_phone.getText().toString();
        String pwd = editText_login_password.getText().toString();
        //if判断语句（首先判断手机号和密码是否为空）
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
            //获得sql语句
            sql = "select * from user where phone = ?";
            //获得DBHelper的一个对象helper
            helper = new DBHelper(this);
            //获得db
            db = helper.getReadableDatabase();
            //执行sql语句
            Cursor cursor = db.rawQuery(sql, new String[]{phone});
            //定义一个整型变量，来接收个数
            int count = cursor.getCount();
            //判断count是否为0，如果为0，则数据库中不存在该手机号，也就是没有注册；反之，已经注册
            if (count != 0) {
                //判断对应的密码是否存在 如果存在返回1，不存在返回0
                sqlQuery = "select * from user where phone = ? and pwd = ?";
                dbQuery = helper.getReadableDatabase();
                Cursor cursorQuery = dbQuery.rawQuery(sqlQuery, new String[]{phone, pwd});
                int countQuery = cursorQuery.getCount();
                //判断语句
                if (countQuery != 0) {
                    //获得一个ContentValues的一个对象values
                    ContentValues values = new ContentValues();
                    ContentValues valuesNickName = new ContentValues();
                    //定义一个字符串变量
                    String login = "login";
                    //向values中添加数据
                    values.put("tag", login);
                    values.put("phone", phone);
                    //向valuesNickName中添加数据
                    valuesNickName.put("phone", phone);
                    //获得helperTwo,helperOne
                    helperTwo = new DBHelperTwo(this);
                    helperOne = new DBHelperOne(this);
                    //获得dbInsert,dbNickName
                    dbInsert = helperTwo.getReadableDatabase();
                    dbNickName = helperOne.getReadableDatabase();
                    //将手机号码存入到数据库
                    dbNickName.insert("nickname1", null, valuesNickName);
                    //存入到数据库，并接受返回的long型值
                    long id = dbInsert.insert("tag1", null, values);
                    Log.e("TAG", "==>Login" + id);
                    //判断语句
                    if (id != -1) {
                        //获得一个intent
                        Intent intent = new Intent();
                        //使用intent进行跳转页面
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        //执行跳转页面
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "输入密码有误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_LONG).show();
        }
    }
}
