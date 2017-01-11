package com.arlen.frame.common.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by user on 2015/8/14.
 */
public class DaoManager {

    public static DaoManager mManager;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private static DaoSession daoSession;
    private Context mContext;

    public static synchronized DaoManager getInstance(Context context) {
        if (mManager == null) {
            mManager = new DaoManager(context);
        }
        return mManager;
    }

    public DaoManager(Context ctx) {
        this.mContext = ctx;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "arlen-bd-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
    public ProductDao getProductDao() {
        return daoSession.getProductDao();
    }
}

