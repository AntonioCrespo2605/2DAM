package com.example.erp.dbControllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    //db info
    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;

    //table names
    static final String SUPPLIER_TABLE="supplier";
    static final String SUPPLIE_TABLE="supplie";
    static final String PRODUCT_TABLE="product";
    static final String PRODUCT_SUPPLIE_TABLE="product_supplie";
    static final String SALE_TABLE="sale";
    static final String PRODUCT_SALE_TABLE="product_sale";
    static final String EMPLOYEE_TABLE="employee";
    static final String SALARY_TABLE="salary";
    static final String CUSTOMER_TABLE="customer";
    static final String SHOPPING_CART="shopping_cart";
    static final String MESSAGE_TABLE="message";

    //Constructor
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    /************************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table supplier
        String queryTable="CREATE TABLE "+SUPPLIER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL, "
                +"address TEXT NOT NULL,"
                +"logo BLOB NOT NULL);";

        db.execSQL(queryTable);

        //Create table supplie
        queryTable="CREATE TABLE "+SUPPLIE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"id_supplier INTEGER NOT NULL REFERENCES "+SUPPLIER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL, "
                +"state INTEGER NOT NULL,"
                +"shipping_costs TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table product
        queryTable="CREATE TABLE "+PRODUCT_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"description TEXT NOT NULL,"
                +"stock INTEGER NOT NULL,"
                +"current_price TEXT NOT NULL,"
                +"photo BLOB);";

        db.execSQL(queryTable);

        //Create table product_supplie
        queryTable="CREATE TABLE "+PRODUCT_SUPPLIE_TABLE+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_supplie INTEGER NOT NULL REFERENCES "+SUPPLIE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"amount INTEGER NOT NULL,"
                +"ind_price TEXT NOT NULL,"
                +"PRIMARY KEY(id_product, id_supplie));";

        db.execSQL(queryTable);

        //Create table employee
        queryTable="CREATE TABLE "+EMPLOYEE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"dni TEXT NOT NULL,"
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"workstation TEXT NOT NULL,"
                +"bank_number TEXT NOT NULL, "
                +"current_salary TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table salary
        queryTable="CREATE TABLE "+SALARY_TABLE+" ("
                +"date TEXT NOT NULL, "
                +"id_employee INTEGER NOT NULL REFERENCES "+EMPLOYEE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "//
                +"salary TEXT NOT NULL, "
                +"PRIMARY KEY(date, id_employee));";

        db.execSQL(queryTable);

        //Create table customer
        queryTable="CREATE TABLE "+CUSTOMER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"email TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table sale
        queryTable="CREATE TABLE "+SALE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"date TEXT NOT NULL,"
                +"shipping_costs TEXT NOT NULL, "
                +"state INTEGER NOT NULL,"
                +"id_employee INTEGER NOT NULL REFERENCES "+EMPLOYEE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_customer INTEGER NOT NULL REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE);";

        db.execSQL(queryTable);

        //Create table product_sale
        queryTable="CREATE TABLE "+PRODUCT_SALE_TABLE+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_sale INTEGER NOT NULL REFERENCES "+SALE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"amount INTEGER NOT NULL,"
                +"ind_price INTEGER NOT NULL,"
                +"PRIMARY KEY(id_sale, id_product));";

        db.execSQL(queryTable);

        //Create table shopping cart
        queryTable="CREATE TABLE "+SHOPPING_CART+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_customer INTEGER NOT NULL REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"amount INTEGER NOT NULL, "
                +"PRIMARY KEY(id_product, id_customer));";

        db.execSQL(queryTable);

        //Create table message
        queryTable="CREATE TABLE "+MESSAGE_TABLE+"("
                +"id_customer INTEGER REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL,"
                +"content TEXT NOT NULL,"
                +"received INTEGER NOT NULL);";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SALARY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SUPPLIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SHOPPING_CART);
        db.execSQL("DROP TABLE IF EXISTS "+MESSAGE_TABLE);

        onCreate(db);
    }

    /************************************************************************/
    //getters && setters




}