package com.arlen.frame.db.shpref;

/**
 *sharePreference操作类 加个数据库版本
 * 因为不然在数据更新的时候有问题
 */
abstract class BaseShrPrefsDao {

    protected final static String VERSION = "baseVersion";// 版本信息

    protected TinyDB mDb;

    BaseShrPrefsDao(String dbName, int curVersion) {
        mDb = new TinyDB(dbName);
        int oldVersion = mDb.getInt(VERSION);
        if (oldVersion < curVersion) {
            onUpgrade(oldVersion, curVersion);
            mDb.putInt(VERSION, curVersion);
        }
    }

    public TinyDB openDatabase() {
        return mDb;
    }

    protected void onUpgrade(int oldVersion, int newVersion) {
        openDatabase().clear();
    }
}
