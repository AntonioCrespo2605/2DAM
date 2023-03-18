package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Supplie;

import java.util.ArrayList;

public class SupplieController extends SQLiteOpenHelper {
    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;
    private static final String SUPPLIE_TABLE="supplie";
    private static final String PRODUCT_SUPPLIE_TABLE="product_supplie";
    private static final String SUPPLIER_TABLE="supplier";
    private static final String PRODUCT_TABLE="product";

    private ArrayList<Supplie>supplies;
    private SupplierController supplierController;
    private ProductController productController;

    /************************************************************************/
    //Constructor
    public SupplieController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readSupplies();
        supplierController=new SupplierController(context);
        productController=new ProductController(context);
    }
    /************************************************************************/
    //OnCreate & OnUpdate
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table supplie
        String queryTable="CREATE TABLE "+SUPPLIE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"id_supplier INTEGER NOT NULL REFERENCES "+SUPPLIER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL, "
                +"state INTEGER NOT NULL,"
                +"shipping_costs TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table product_supplie
        queryTable="CREATE TABLE "+PRODUCT_SUPPLIE_TABLE+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_supplie INTEGER NOT NULL REFERENCES "+SUPPLIE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"amount INTEGER NOT NULL,"
                +"ind_price TEXT NOT NULL,"
                +"PRIMARY KEY(id_product, id_supplie));";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SUPPLIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIE_TABLE);

        onCreate(db);
    }
    /************************************************************************/
    //reads all supplies from db
    private void readSupplies(){
        this.supplies=new ArrayList<Supplie>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+SUPPLIE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                boolean state;
                if(cursor.getInt(3)==0)state=false;
                else state=true;
                Supplie supplie=new Supplie(cursor.getInt(0), supplierController.getSupplierById(cursor.getInt(1)), cursor.getString(2), state, Double.parseDouble(cursor.getString(4)));

                cursor2=db.rawQuery("SELECT * FROM "+PRODUCT_SUPPLIE_TABLE+" WHERE id_supplie="+cursor.getInt(0), null);
                ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
                if(cursor2.moveToFirst()){
                    do{
                        lines.add(new ProductSale(productController.getProductById(cursor2.getInt(0)), cursor2.getInt(2), Double.parseDouble(cursor2.getString(3))));
                    }while (cursor2.moveToNext());
                }
                supplie.setLines(lines);
                supplies.add(supplie);

            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //add methods
    public void addSupplie(Supplie supplie){
        int id=1;
        while(existsSupplie(supplie.getId())){
            supplie.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", supplie.getId());
        values.put("id_supplier", supplie.getSupplier().getId());
        values.put("date",supplie.getDate());
        int state=0;
        if(supplie.isState())state=1;
        values.put("state",state);
        values.put("shipping_costs", supplie.getShipping_costs()+"");

        db.insert(SUPPLIE_TABLE, null, values);
        db.close();

        //product_supplie
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for (ProductSale ps:supplie.getLines()){
            values2.put("id_product", ps.getProduct().getId());
            values2.put("id_supplie",supplie.getId());
            values2.put("amount", ps.getAmount());
            values2.put("ind_price",ps.getIndPrice());
            db2.insert(PRODUCT_SUPPLIE_TABLE, null, values);
        }
        db2.close();
        readSupplies();
    }

    public void addProductToSupplie(ProductSale productSale, int id_supplie){
        if(!existsProductInSupplie(productSale.getProduct().getId(), id_supplie)){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", productSale.getProduct().getId());
            values.put("id_supplie", id_supplie);
            values.put("amount",productSale.getAmount());
            values.put("ind_price",productSale.getIndPrice()+"");

            db.insert(PRODUCT_SUPPLIE_TABLE, null, values);
            db.close();
        }
    }
    /************************************************************************/
    //delete methods
    public void deleteSupplie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_SUPPLIE_TABLE,"id_supplie="+id,null);
        db.delete(SUPPLIE_TABLE, "id="+id, null);

        readSupplies();
    }

    public void deleteProductSupplie(int id_product, int id_supplie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+PRODUCT_SUPPLIE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+PRODUCT_SUPPLIE_TABLE+"WHERE id_product="+id_product+" AND id_supplie="+id_supplie+")");
        db.close();

        checkEmptySupplies();
    }

    private void checkEmptySupplies(){
        for(Supplie s:supplies){
            if(s.getLines().size()==0){
                deleteSupplie(s.getId());
            }
        }
        readSupplies();
    }
    /************************************************************************/
    //update methods
    public void updateSupplie(Supplie supplie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", supplie.getId());
        values.put("id_supplier",supplie.getSupplier().getId());
        values.put("date", supplie.getDate());
        int state=0;
        if(supplie.isState())state=1;
        values.put("state", state);
        values.put("shipping_costs", supplie.getShipping_costs()+"");

        db.update(SUPPLIE_TABLE, values, "id="+supplie.getId(), null);

        readSupplies();
    }

    public void updateSupplieProduct(ProductSale newLine, int id_supplie){
        SQLiteDatabase db =this.getWritableDatabase();

        String sql = "UPDATE "+PRODUCT_SUPPLIE_TABLE+" SET amount = ?, ind_price = ? WHERE id_product = ? AND id_supplie = ?";
        Object[] params = {newLine.getAmount(), newLine.getIndPrice(), newLine.getProduct().getId(), id_supplie};
        db.execSQL(sql, params);
        db.close();

        readSupplies();
    }
    /************************************************************************/
    //information and filters about supplies
    private boolean existsSupplie(int id){
        for(Supplie supplie:supplies){
            if(supplie.getId()==id)return true;
        }
        return false;
    }

    private boolean existsProductInSupplie(int id_product, int id_supplie){
        for(ProductSale productSale:getSupplieById(id_supplie).getLines()){
            if(productSale.getProduct().getId()==id_product)return true;
        }
        return false;
    }

    private Supplie getSupplieById(int id) {
        for(Supplie supplie:supplies){
            if(supplie.getId()==id)return supplie;
        }
        return null;
    }

    public ArrayList<Supplie>getPendingSuppliesOfSupplier(int id_supplier){
        ArrayList<Supplie>toret=new ArrayList<Supplie>();

        for(Supplie supplie:supplies){
            if(supplie.getSupplier().getId()==id_supplier && !supplie.isState()){
                toret.add(supplie);
            }
        }
        return toret;
    }



}
