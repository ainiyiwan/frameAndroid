package com.arlen.frame.db.database;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;


/**
 */
public class BaseOperator<T extends AbstractDao, E> {

    public T mTableDao;

    private AsyncSession mAsyncSession;

    private Context mContext;

    public BaseOperator() {
    }

    public BaseOperator(Context context) {

        this.mContext = context;
        mAsyncSession = DaoManager.getInstance(context).getDaoSession().startAsyncSession();
    }



    public List<E> queryAll() {
        return mTableDao.queryBuilder().list();
    }


    public void deleteAll() {
        DeleteQuery<E> mDeleteQuery = mTableDao.queryBuilder().buildDelete();
        mDeleteQuery.executeDeleteWithoutDetachingEntities();
    }
    /**
     *
     * @param dataLs 新的数据集
     * @param isAppend 是否追加
     */
    private void setData(List<E> dataLs, boolean isAppend) {
        if(!isAppend){
            DeleteQuery<E> deleteQuery = mTableDao.queryBuilder().buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
        mTableDao.insertInTx(dataLs);
    }

    public void resetData( List<E> dataLs){
        setData(dataLs, false);
    }

    public void appendData( List<E> dataLs) {
        setData(dataLs, true);
    }



    public void delete(E item) {
        mTableDao.delete(item);
    }

    public void insert(E item) {
        mTableDao.insert(item);
    }

    public void update(E item) {
        mTableDao.update(item);
    }

    public long getCount() {
        return mTableDao.count();
    }

    public T getDao() {
        return mTableDao;
    }

    public AsyncSession getAsyncSession(){
        if (mAsyncSession == null) {
            mAsyncSession = DaoManager.getInstance(mContext).getDaoSession().startAsyncSession();
        }
        return mAsyncSession;
    }


    public void insertAsync(T entity) {
        getAsyncSession().insert(entity);
    }

    public void insertTxAsync(Class<E> entityClass, Iterable<E> entities) {
        getAsyncSession().insertInTx(entityClass, entities);
    }


    public List<E> queryAsync(Query<E> query) {
        return (List<E>) getAsyncSession().queryList(query).getResult();

    }

    public List<E> queryAllAsync() {
        Query<E> query = mTableDao.queryBuilder().build();
        return queryAsync(query);
    }
}
