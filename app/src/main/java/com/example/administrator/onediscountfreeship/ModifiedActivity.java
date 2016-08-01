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
import utils.DBHelperTwo;

public class ModifiedActivity extends AppCompatActivity {
    //定义数据库相关变量
    private DBHelperTwo dbHelperTwo;
    private DBHelper dbHelper;
    private SQLiteDatabase dbTag, dbUser;
    //声明控件
    private EditText editText_nowPwd, editText_newPwd, editText_verifyPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);
        //获得数据库相关变量
        dbHelperTwo = new DBHelperTwo(this);
        dbHelper = new DBHelper(this);
        dbTag = dbHelperTwo.getReadableDatabase();
        dbUser = dbHelper.getReadableDatabase();
        //获得控件
        editText_nowPwd = (EditText) findViewById(R.id.editText_nowPwd);
        editText_newPwd = (EditText) findViewById(R.id.editText_newPwd);
        editText_verifyPwd = (EditText) findViewById(R.id.editText_verifyPwd);

    }

    //添加单击事件
    public void onClick_back(View view) {
        //获得一个intent，进行页面跳转
        Intent intent = new Intent(ModifiedActivity.this, PersonalInfoActivity.class);
        //执行页面跳转
        startActivity(intent);
    }

    //添加单击事件
    public void onClick_save(View view) {
        //定义字符串类型的变量，来存值
        String phone = "";
        String pwd = "";
        //先获得数据库tag1中的手机号码（也就是登录时存入数据库tag1中的手机号码）
        String sqlTag = "select * from tag1 where phone = phone";
        Cursor cursorTag = dbTag.rawQuery(sqlTag, null);
        while (cursorTag.moveToNext()) {
            phone = cursorTag.getString(cursorTag.getColumnIndex("phone"));
        }
        //再获得数据库user中对应获得的phone的pwd
        String sqlUser = "select pwd from user where phone = ?";
        Cursor cursorUser = dbUser.rawQuery(sqlUser, new String[]{phone});
        while (cursorUser.moveToNext()) {
            pwd = cursorUser.getString(cursorUser.getColumnIndex("pwd"));
        }
        //定义String类型变量，来存输入框输入的值
        String editnow = editText_nowPwd.getText().toString();
        String editnew = editText_newPwd.getText().toString();
        String editverify = editText_verifyPwd.getText().toString();
        //if判断语句
        if (!TextUtils.isEmpty(editnow) && !TextUtils.isEmpty(editnew) && !TextUtils.isEmpty(editverify)) {
            //判断所输入的现在密码是否等于数据库中所对应的密码
            if (editnow.equals(pwd)) {
                //再次判断更改的密码和确认更改的密码是否输入一致
                if (editnew.equals(editverify)) {
                    //获得一个ContentValues对象values
                    ContentValues values = new ContentValues();
                    //向values中添加值
                    values.put("pwd", editnew);
                    //定义where子句
                    String whereClause = "phone = ?";
                    //定义字符串数组
                    String[] whereArgs = {phone};
                    //定义一个int型的变量，来接收返回值并判断（如果为-1,则修改不成功；反之，则修改成功）
                    int id = dbUser.update("user", values, whereClause, whereArgs);
                    //if判断语句
                    if (id > 0) {
                        //获得一个intent，进行页面跳转
                        Intent intent = new Intent(ModifiedActivity.this, PersonalInfoActivity.class);
                        //执行页面跳转
                        startActivity(intent);
                        Toast.makeText(this, "修改密码成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "修改密码失败", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "旧密码错误", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "输入密码为空", Toast.LENGTH_LONG).show();
        }
    }
}
