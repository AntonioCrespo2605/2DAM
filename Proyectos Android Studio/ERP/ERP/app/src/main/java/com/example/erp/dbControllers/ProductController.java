package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class ProductController{
    private DBHelper dbHelper;

    private ArrayList<Product>products;

    /************************************************************************/
    //Constructor
    public ProductController(Context context){
        dbHelper=new DBHelper(context);
        readProducts();
    }

    /************************************************************************/
    //reads all products from database
    private void readProducts(){
        this.products=new ArrayList<Product>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.PRODUCT_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                double current_price=Double.parseDouble(cursor.getString(4));
                products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),current_price,ImageCustomized.fromBlobToBitmap(cursor.getBlob(5))));
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

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description",product.getDescription());
        values.put("stock",product.getStock());
        values.put("current_price", product.getCurrent_price()+"");
        values.put("photo", ImageCustomized.fromBitmapToBlob(product.getPhoto()));

        db.insert(DBHelper.PRODUCT_TABLE, null, values);
        db.close();
        readProducts();
    }

    /************************************************************************/
    //delete method
    public void deleteProduct(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.PRODUCT_TABLE, "id="+id, null);

        readProducts();

        //checkEmptySupplies();
        //checkEmptySales();
    }
    /************************************************************************/
    //update method
    public void updateProduct(Product product){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("stock", product.getStock());
        values.put("current_price",product.getCurrent_price()+"");
        values.put("photo", ImageCustomized.fromBitmapToBlob(product.getPhoto()));

        db.update(DBHelper.PRODUCT_TABLE, values, "id="+product.getId(), null);
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

    public int getAllAmountOfProducts(){
        int toret=0;
        for(Product p:products){
            toret+=p.getStock();
        }
        return toret;
    }

    public double getValueOfAllProducts(){
        double toret=0;

        for(Product p:products){
            toret+=(p.getStock()*p.getCurrent_price());
        }
        return toret;
    }

    public int newId(){
        int toret=1;
        while(existsProduct(toret))toret++;
        return toret;
    }

    /************************************************************************/
    //getters && setters

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
