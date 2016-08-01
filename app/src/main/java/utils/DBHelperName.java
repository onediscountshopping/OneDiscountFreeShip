package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DBHelperName extends SQLiteOpenHelper {
    //定义一个字符串变量
    private String sql = "";

    public DBHelperName(Context context) {
        super(context, "name.db", null, 1);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        //获得sql语句
        sql = "create table name(_id integer primary key autoincrement,name varchar(20))";
        //执行sql语句，创建数据库
        db.execSQL(sql);
    }

    //更新版本
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
