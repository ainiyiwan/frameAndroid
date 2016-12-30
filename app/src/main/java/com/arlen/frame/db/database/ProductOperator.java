package com.arlen.frame.db.database;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by user on 2015/11/4.
 */
public class ProductOperator extends BaseOperator<ProductDao, ProductTable> {

    private static ProductOperator productOperator;

    public ProductOperator(Context ctx) {
        mTableDao = DaoManager.getInstance(ctx).getProductDao();
    }

    public static synchronized ProductOperator getInstance(Context ctx) {
        if (productOperator == null) {
            productOperator = new ProductOperator(ctx);
        }
        return productOperator;
    }

    public List<ProductTable> queryList(int userId) {
        QueryBuilder<ProductTable> builder = mTableDao.queryBuilder();
        return builder.where(ProductDao.Properties.Id.eq(userId)).orderDesc(ProductDao.Properties.UpdateTime).list();
    }

    public void deleteById(String id, int userId) {
        QueryBuilder<ProductTable> builder = mTableDao.queryBuilder();
        DeleteQuery<ProductTable> query = builder.where(ProductDao.Properties.Id.eq(id),
                ProductDao.Properties.Id.eq(userId)).buildDelete();
        query.executeDeleteWithoutDetachingEntities();
    }

    public boolean isStarred(String id, int userId) {
        QueryBuilder<ProductTable> builder = mTableDao.queryBuilder();
        return builder.where(ProductDao.Properties.Id.eq(id),ProductDao.Properties.Id.eq(userId)).list().size() > 0 ? true : false;
    }

    public ProductTable queryById(String id, int userId) {
        QueryBuilder<ProductTable> builder = mTableDao.queryBuilder();
        List<ProductTable> data = builder.where(ProductDao.Properties.Id.eq(id),ProductDao.Properties.Id.eq(userId)).list();
        if(null != data && data.size() > 0)
            return data.get(0);
        return null;
    }

}
