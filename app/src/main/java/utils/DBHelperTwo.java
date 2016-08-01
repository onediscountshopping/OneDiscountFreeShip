package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/28.
 */
public class DBHelperTwo extends SQLiteOpenHelper {
    //定义变量
    private String sql;

    public DBHelperTwo(Context context) {
        /**
         * 参数1： 上下文
         * 参数2： 名字
         * 参数3： 工厂
         * 参数4： 版本
         */
        super(context, "tag1.db", null, 1);
    }

    //创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        //需要执行的语句
        sql = "create table tag1(_id integer primary key autoincrement,tag varchar(20),phone varchar(20))";
        //执行语句
        db.execSQL(sql);
    }

    //更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
