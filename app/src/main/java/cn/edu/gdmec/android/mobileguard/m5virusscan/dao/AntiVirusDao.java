package cn.edu.gdmec.android.mobileguard.m5virusscan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dell on 2017/11/10.
 */
    /**
     * 检查某个md5是否有病毒
     * @param md5
     * @return null 代表扫描安全
     */

public class AntiVirusDao {
    //检查某个md5是否是病毒

    private static Context context;
    private static String dbname;
    public AntiVirusDao(Context context){
        this.context = context;
        dbname = "/data/data/"+context.getPackageName ()+"/files/antivirus.db";
    }
    //使用apk文件的md5值匹配病毒数据库
    public String checkVirus(String md5){
        String desc = null;
        //打开病毒数据库
        //SQLiteDatabase的 openDatabase （字符串路径，SQLiteDatabase.CursorFactory工厂：默认null，诠释标志：控制数据库访问模式）
        //OPEN_READONLY:打开数据库只读
        SQLiteDatabase db = SQLiteDatabase.openDatabase (
                dbname, null,
                SQLiteDatabase.OPEN_READONLY );
        // 调用查找书库代码并返回数据源(Cursor 游标）
        //rawQuery:运行提供的SQL并返回Cursor结果集
        Cursor cursor = db.rawQuery ( "select desc from datable where md5=?",
                new String[] { md5 });
        //public abstract boolean moveToNext ():将光标移到下一行。
        // 如果游标已经超过结果集中的最后一个条目，则此方法将返回false。
        if (cursor.moveToNext ()){
            //目标列的从零开始的索引。
            desc = cursor.getString ( 0 );
        }
        cursor.close ();
        db.close ();
        return desc;
    }
    //模块5
    //获取病毒数据版本
    public String getVirusDbVersion(){
        String dbVersion = null;
        // 打开病毒数据库
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbname, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select major||'.'||minor||'.'||build from version",null);

        if (cursor.moveToNext()) {
            dbVersion = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return dbVersion;
    }
}
