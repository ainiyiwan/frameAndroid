package com.arlen.frame.db.shpref;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

/**
 * JSON数据存储
 */
abstract public class GsnShrPrefsDao<T> extends BaseShrPrefsDao {

    protected final static String CONTENT = "gsnContent";//保存的GSON串

    private Class<T> mClazz;
    private Gson mGsn;

    GsnShrPrefsDao(Context ctx, String dbName, int curVersion, Class clazz) {
        super(ctx, dbName, curVersion);
        this.mClazz = clazz;
        this.mGsn = new Gson();
    }

    public void save(T content) {
        save(content, CONTENT);
    }

    public T get() {
        return get(CONTENT);
    }

    public void save(T content, String key) {
        if (content == null) {
            return;
        }
        String str = mGsn.toJson(content);
        openDatabase().putString(key, str);
    }

    public T get(String key) {
        String content = openDatabase().getString(key);
        try {
            T result = mGsn.fromJson(content, mClazz);
            return result;
        } catch (com.google.gson.JsonSyntaxException jsex) {
            return null;
        }
    }

    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
