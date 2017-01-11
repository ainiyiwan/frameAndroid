package com.yaochufa.dao;

import java.io.File;
import java.lang.String;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by user on 2015/8/13.
 */
public class GreenDaoGenerator {

    public static final String mPackageName = "com.arlen.frame.common.db.database";
    public static String path;
    ///////////////////////////////////////////////////////////////////////////
    // 底价和房态 Table
    ///////////////////////////////////////////////////////////////////////////
    private static final String PRODUCTNAME = "productName";
    private static final String ITEMNAME = "itemName";
    public static final String UPDATETIME = "updateTime";
//
//    public static final String PRODUCTID = "productId";
//    public static final String PRODUCTNAME = "productName";

    ///////////////////////////////////////////////////////////////////////////
    // Product Table
    ///////////////////////////////////////////////////////////////////////////

    public static final int mVersion = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(mVersion, mPackageName);// 指定数据库版本号和生成的包名

        generateProductEntity(schema);

        path = System.getProperty("user.dir") + File.separator + "app" + File.separator +  "src" + File.separator + "main" + File.separator + "java";

        new DaoGenerator().generateAll(schema, path);
    }

    private static void generatePriceAndState(Schema schema) {
        Entity holiday = schema.addEntity("PriceTable");
//      holiday.setTableName("HolidayTable");  //设置表名
//      holiday.setClassNameDao("HolidayDao"); //Dao名
        holiday.addIdProperty().primaryKey();
        holiday.addStringProperty(PRODUCTNAME);
        holiday.addStringProperty(ITEMNAME);
        holiday.addStringProperty(UPDATETIME);
    }


    private static void generateProductEntity(Schema schema) {
        Entity product = schema.addEntity("ProductTable");
        product.setClassNameDao("ProductDao");
        product.setTableName("ProductTable");
//        product.setHasKeepSections(true);
        product.addIdProperty().primaryKey();
        product.addStringProperty(PRODUCTNAME);
        product.addStringProperty(UPDATETIME);
    }
}
