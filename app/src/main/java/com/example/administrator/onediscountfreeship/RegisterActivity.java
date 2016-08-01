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

import utils.DBHelper;
import utils.TextLength;

public class RegisterActivity extends AppCompatActivity {
    //定义控件
    private EditText editText_phone, editText_password, editText_repeat;
    //定义字符串变量
    private String phone, pwd, repeat;
    //定义数据库相关变量
    private DBHelper helper;
    private SQLiteDatabase db, dbdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //获得控件
        editText_phone = (EditText) findViewById(R.id.editText_phone);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_repeat = (EditText) findViewById(R.id.editText_repeat);
        //获得helper
        helper = new DBHelper(this);
        //获得db
        db = helper.getReadableDatabase();
        dbdelete = helper.getReadableDatabase();

    }

    //添加单击事件
    public void onClick_back(View view) {
        //使用intent进行页面跳转
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //执行页面跳转
        startActivity(intent);
    }

    //立即注册，添加单击事件
    public void onClick_register(View view) {
        //获得字符串变量
        phone = editText_phone.getText().toString();
        pwd = editText_password.getText().toString();
        repeat = editText_repeat.getText().toString();

        //if判断语句，判断所填信息是否为空
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repeat)) {
            //判断手机号码长度
            if (TextLength.getNumberCount(phone) == 11) {
                //判断密码长度
                if (TextLength.getStringInfo(pwd) >= 6 && TextLength.getStringInfo(pwd) <= 20) {
                    //判断两次输入密码是否相同
                    if (pwd.equals(repeat)) {

                        //查询语句（在此不指明具体查询什么）
                        String sql = "select * from user where phone=?";
                        /**
                         * Cursor是游标工厂
                         * db.rawQuery(sql,new String[]{...})执行语句
                         */
                        Cursor cursor = db.rawQuery(sql, new String[]{phone});
                        int count = cursor.getCount();
                        //if判断语句。注册前，先从数据库查询，如果已存在所要注册的账号，则不能进行再次注册；否则，可以进行注册
                        if (count == 0) {
                            //获得ContentValues对象values
                            ContentValues values = new ContentValues();
                            //向values里面添加键值对
                            values.put("phone", phone);
                            values.put("pwd", pwd);

                            //定义一个long型的变量，来接收insert()方法的返回值
                            long id = db.insert("user", null, values);

                            //判断insert()方法的返回值是否是-1，如果是，存储不成功；如果不是，存储成功
                            if (id != -1) {
                                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "注册失败", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "该账号已经注册", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "输入密码不一样", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "密码长度不符合", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "手机号码输入错误", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "注册信息为空", Toast.LENGTH_LONG).show();
        }
    }
}
