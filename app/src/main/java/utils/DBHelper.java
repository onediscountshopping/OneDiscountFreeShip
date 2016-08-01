package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/27.
 */
public class DBHelper extends SQLiteOpenHelper {
    //定义变量
    private String sql;

    //构造函数
    public DBHelper(Context context) {
        /**
         * 参数1： 上下文
         * 参数2： 名字
         * 参数3： 工厂
         * 参数4： 版本
         */
        super(context, "user.db", null, 2);
    }

    //创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        //需要执行的语句
        sql = "create table user(_id integer primary key autoincrement,phone varchar(20),pwd varchar(20))";
        //执行语句
        db.execSQL(sql);
    }

    //更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
