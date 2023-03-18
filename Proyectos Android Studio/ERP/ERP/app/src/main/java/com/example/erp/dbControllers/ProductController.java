package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class ProductController extends SQLiteOpenHelper {

    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;
    private static final String PRODUCT_TABLE="product";

    private ArrayList<Product>products;

    private static ImageCustomized ic;

    /************************************************************************/
    //Constructor
    public ProductController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readProducts();
    }

    /************************************************************************/
    //OnCreate & onUpdate
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable="CREATE TABLE "+PRODUCT_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"description TEXT NOT NULL,"
                +"stock INTEGER NOT NULL,"
                +"current_price TEXT NOT NULL,"
                +"photo BLOB);";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE);
        onCreate(db);
    }

    /************************************************************************/
    //reads all products from database
    private void readProducts(){
        this.products=new ArrayList<Product>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+PRODUCT_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                double current_price=Double.parseDouble(cursor.getString(4));
                products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),current_price,ic.fromBlobToBitmap(cursor.getBlob(5))));
            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //add method
    public void addProduct(Product product){
        int id=1;
        while(existsProduct(product.getId())){
            product.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description",product.getDescription());
        values.put("stock",product.getStock());
        values.put("current_price", product.getCurrent_price()+"");
        values.put("photo", ic.fromBitmapToBlob(product.getPhoto()));

        db.insert(PRODUCT_TABLE, null, values);
        db.close();
        readProducts();
    }

    /************************************************************************/
    //delete method
    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(PRODUCT_TABLE, "id="+id, null);

        readProducts();

        //checkEmptySupplies();
        //checkEmptySales();
    }
    /************************************************************************/
    //update method
    public void updateProduct(Product product){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("stock", product.getStock());
        values.put("current_price",product.getCurrent_price()+"");
        values.put("photo", ic.fromBitmapToBlob(product.getPhoto()));

        db.update(PRODUCT_TABLE, values, "id="+product.getId(), null);
        db.close();

        readProducts();
    }
    /************************************************************************/
    //information and filters about products
    private boolean existsProduct(int id){
        for(Product product:products){
            if(product.getId()==id)return true;
        }
        return false;
    }

    public Product getProductById(int id){
        for(Product product:products){
            if(product.getId()==id)return product;
        }
        return null;
    }
}
