package com.example.pluginstudy.database;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "won_database.db";
	private static final int DATABASE_VERSION = 1;

	private RuntimeExceptionDao<DataMap, Integer> dataMapRuntimeDao = null;

	private static final AtomicInteger usageCounter = new AtomicInteger(0);
	private static OrmDbHelper helper = null;

	public static synchronized OrmDbHelper getHelper(Context context) {
		if (helper == null) {
			helper = new OrmDbHelper(context);
		}
		usageCounter.incrementAndGet();
		return helper;
	}

	public OrmDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			Log.i(OrmDbHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, DataMap.class);
		} catch (SQLException e) {
			Log.e(OrmDbHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		if (newVersion != oldVersion) {
			try {
				Log.i(OrmDbHelper.class.getName(), "onUpgrade");
				TableUtils.dropTable(connectionSource, DataMap.class, true);
				onCreate(db, connectionSource);
			} catch (SQLException e) {
				Log.e(OrmDbHelper.class.getName(), "Can't drop databases", e);
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our SimpleData class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */

	public RuntimeExceptionDao<DataMap, Integer> getDataMapRuntimeDao() {
		if (dataMapRuntimeDao == null) {
			dataMapRuntimeDao = getRuntimeExceptionDao(DataMap.class);
		}
		return dataMapRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		if (usageCounter.decrementAndGet() <= 0) {
			if (dataMapRuntimeDao != null)
				dataMapRuntimeDao = null;
			helper = null;
			usageCounter.set(0);
			super.close();
		}
	}
}
