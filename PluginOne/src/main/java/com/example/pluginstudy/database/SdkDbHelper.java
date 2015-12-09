package com.example.pluginstudy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库对象
 *
 * @author xuzhenqin
 */
public class SdkDbHelper extends SQLiteOpenHelper {

    // 数据库名称
    private final static String DATABASE_NAME = "notepad";
    // 数据库版本号
    private final static int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "diary";
    public static final String TITLE = "title";

    // 数据表创建SQL语句
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + " (_id integer primary key autoincrement, " + TITLE
            + " text not null);";

    public SdkDbHelper(Context context) {
        // 调用父类构造方法创建数据库
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 执行创建数据库SQL语句
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 判断数据库是否存在
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 在databaseHelper类中,添加以下3个方法,以便于database调用实现数据的增删改。

    /**
     * 向数据库表中插入一条数据
     */
    public long insert(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, Title);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    /**
     * 删除表中符合条件的记录
     */
    public void delete(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        if (title != null) {
            where = TITLE + "= '" + title + "'";
        }
        db.delete(TABLE_NAME, where, null);
    }

    /**
     * 查询全部表记录
     *
     * @return 返回查询的全部表记录
     */
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
                " _id desc");
        return cursor;
    }

    /**
     * 更新数据
     */
    public void update(String Title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, Title);
        db.update(TABLE_NAME, cv, null, null);
    }

}