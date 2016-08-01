package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/29.
 */
public class DBHelperOne extends SQLiteOpenHelper {
    //定义一个变量
    private String sql = "";

    public DBHelperOne(Context context) {
        super(context, "nickname1.db", null, 1);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "create table nickname1(_id integer primary key autoincrement,name varchar(20),phone varchar(20))";
        //执行语句
        db.execSQL(sql);
    }

    //更新版本
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
