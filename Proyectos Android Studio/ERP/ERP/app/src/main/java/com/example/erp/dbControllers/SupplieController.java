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

public class SupplieController{
    private DBHelper dbHelper;

    private ArrayList<Supplie>supplies;
    private SupplierController supplierController;
    private ProductController productController;

    /************************************************************************/
    //Constructor
    public SupplieController(Context context){
        dbHelper=new DBHelper(context);
        readSupplies();
        supplierController=new SupplierController(context);
        productController=new ProductController(context);
    }

    /************************************************************************/
    //reads all supplies from db
    private void readSupplies(){
        this.supplies=new ArrayList<Supplie>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.SUPPLIE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                boolean state;
                if(cursor.getInt(3)==0)state=false;
                else state=true;
                Supplie supplie=new Supplie(cursor.getInt(0), supplierController.getSupplierById(cursor.getInt(1)), cursor.getString(2), state, Double.parseDouble(cursor.getString(4)));

                cursor2=db.rawQuery("SELECT * FROM "+DBHelper.PRODUCT_SUPPLIE_TABLE+" WHERE id_supplie="+cursor.getInt(0), null);
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

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", supplie.getId());
        values.put("id_supplier", supplie.getSupplier().getId());
        values.put("date",supplie.getDate());
        int state=0;
        if(supplie.isState())state=1;
        values.put("state",state);
        values.put("shipping_costs", supplie.getShipping_costs()+"");

        db.insert(DBHelper.SUPPLIE_TABLE, null, values);
        db.close();

        //product_supplie
        SQLiteDatabase db2=dbHelper.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for (ProductSale ps:supplie.getLines()){
            values2.put("id_product", ps.getProduct().getId());
            values2.put("id_supplie",supplie.getId());
            values2.put("amount", ps.getAmount());
            values2.put("ind_price",ps.getIndPrice());
            db2.insert(DBHelper.PRODUCT_SUPPLIE_TABLE, null, values);
        }
        db2.close();
        readSupplies();
    }

    public void addProductToSupplie(ProductSale productSale, int id_supplie){
        if(!existsProductInSupplie(productSale.getProduct().getId(), id_supplie)){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", productSale.getProduct().getId());
            values.put("id_supplie", id_supplie);
            values.put("amount",productSale.getAmount());
            values.put("ind_price",productSale.getIndPrice()+"");

            db.insert(DBHelper.PRODUCT_SUPPLIE_TABLE, null, values);
            db.close();
        }
    }
    /************************************************************************/
    //delete methods
    public void deleteSupplie(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.PRODUCT_SUPPLIE_TABLE,"id_supplie="+id,null);
        db.delete(DBHelper.SUPPLIE_TABLE, "id="+id, null);

        readSupplies();
    }

    public void deleteProductSupplie(int id_product, int id_supplie) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+DBHelper.PRODUCT_SUPPLIE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+DBHelper.PRODUCT_SUPPLIE_TABLE+"WHERE id_product="+id_product+" AND id_supplie="+id_supplie+")");
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", supplie.getId());
        values.put("id_supplier",supplie.getSupplier().getId());
        values.put("date", supplie.getDate());
        int state=0;
        if(supplie.isState())state=1;
        values.put("state", state);
        values.put("shipping_costs", supplie.getShipping_costs()+"");

        db.update(DBHelper.SUPPLIE_TABLE, values, "id="+supplie.getId(), null);

        readSupplies();
    }

    public void updateSupplieProduct(ProductSale newLine, int id_supplie){
        SQLiteDatabase db =dbHelper.getWritableDatabase();

        String sql = "UPDATE "+DBHelper.PRODUCT_SUPPLIE_TABLE+" SET amount = ?, ind_price = ? WHERE id_product = ? AND id_supplie = ?";
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

    /************************************************************************/
    //getters && setters

    public ArrayList<Supplie> getSupplies() {
        return supplies;
    }

    public void setSupplies(ArrayList<Supplie> supplies) {
        this.supplies = supplies;
    }
}
