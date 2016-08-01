package com.example.administrator.onediscountfreeship;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.DBHelper;
import utils.DBHelperOne;
import utils.DBHelperTwo;

public class PersonalInfoActivity extends AppCompatActivity {
    //声明控件
    private LinearLayout header;
    private TextView textView_nickname;
    private TextView textView_phone;
    //定义数据库相关变量
    private DBHelperOne dbHelperOne;
    private DBHelperTwo dbHelperTwo;
    private SQLiteDatabase dbNickName, dbPhone, dbExitTag, dbExitNickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        //获得数据库相关变量
        dbHelperOne = new DBHelperOne(this);
        dbHelperTwo = new DBHelperTwo(this);
        dbNickName = dbHelperOne.getReadableDatabase();
        dbPhone = dbHelperOne.getReadableDatabase();
        dbExitTag = dbHelperTwo.getReadableDatabase();
        dbExitNickName = dbHelperOne.getReadableDatabase();
        //获得控件
        header = (LinearLayout) findViewById(R.id.header);
        textView_nickname = (TextView) findViewById(R.id.textView_nickname);
        textView_phone = (TextView) findViewById(R.id.textView_phone);
        //将创建过后的上下文菜单注册给控件（注册上下文菜单，绑定控件）
        registerForContextMenu(header);
        //初始化昵称
        initNickName();
        //初始化手机号码
        initPhone();
    }

    //初始化手机号码
    private void initPhone() {
        //定义需要执行的sql语句
        String sql = "select * from nickname1 where phone = phone";
        //执行sql语句
        Cursor cursor = dbPhone.rawQuery(sql, null);
        //进行遍历查询
        while (cursor.moveToNext()) {
            //定义一个String类型变量，来存放值
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Log.e("TAG", "===>phone" + phone);
            //给控件设置手机号码
            textView_phone.setText(phone);
        }
    }

    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        /**
         * 使用菜单填充器创建上下文菜单
         * 参数1：菜单资源文件
         * 参数2：创建之后显示的菜单
         */
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    //初始化昵称
    private void initNickName() {
        //定义需要执行的sql语句
        String sql = "select * from nickname1 where name = name";
        //执行sql语句
        Cursor cursor = dbNickName.rawQuery(sql, null);
        //进行遍历查询
        while (cursor.moveToNext()) {
            //定义一个字符串类型变量，来存放获得的nickname
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Log.e("TAG", "===>nickname" + name);
            //给控件设置昵称
            textView_nickname.setText(name);
        }
    }

    //处理上下文菜单选择事项
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //具体选择
        switch (item.getItemId()) {
            case R.id.phone:
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(PersonalInfoActivity.this, CameraActivity.class);
                //执行页面跳转
                startActivity(intent);
                break;
            case R.id.local:
                Toast.makeText(this, "暂未添加相关操作", Toast.LENGTH_LONG).show();
                //..................................................................................
                break;
        }
        return super.onContextItemSelected(item);
    }

    //添加单击事件
    public void onClick(View view) {
        switch (view.getId()) {
            //修改昵称
            case R.id.nickname:
                //获得一个intent，进行页面跳转
                Intent intent1 = new Intent(PersonalInfoActivity.this, NicknameActivity.class);
                //执行页面跳转
                startActivity(intent1);
                break;
            //修改密码
            case R.id.modified:
                //获得一个intent，进行页面跳转
                Intent intent2 = new Intent(PersonalInfoActivity.this, ModifiedActivity.class);
                //执行页面跳转
                startActivity(intent2);
                break;
            //收货地址
            case R.id.address:
                //获得一个intent，进行页面跳转
                Intent intent3 = new Intent(PersonalInfoActivity.this, AddressActivity.class);
                //执行页面跳转
                startActivity(intent3);
                break;
        }
    }

    //添加单击事件
    public void onClick_back(View view) {
        //获得一个intent，进行页面跳转
        Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
        //执行页面跳转
        startActivity(intent);
    }

    //添加单击事件
    public void onClick_exit(View view) {
        //创建对话框的构建器，可以指定对话框的属性
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //给对话框设置标题
        builder.setTitle("确定退出当前登录？");
        //给对话框设置图标
        builder.setIcon(R.mipmap.ic_launcher);
        //禁用回退键，点击外围区域不取消对话框
        builder.setCancelable(false);
        //给对话框设置按钮（确定按钮）
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获得需要执行的sql语句
                String sqlTag = "delete from tag1";
                String sql1NickName = "delete from nickname1";
                //执行sql语句
                dbExitTag.execSQL(sqlTag);
                dbExitNickName.execSQL(sql1NickName);
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
                //执行页面跳转
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "已退出当前登录", Toast.LENGTH_LONG).show();
            }
        });
        //给对话框设置按钮（取消按钮）
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
