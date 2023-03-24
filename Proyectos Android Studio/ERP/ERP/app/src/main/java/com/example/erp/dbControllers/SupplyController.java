package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Supply;

import java.util.ArrayList;

public class SupplyController {
    private DBHelper dbHelper;

    private ArrayList<Supply>supplies;
    private SupplierController supplierController;
    private ProductController productController;

    /************************************************************************/
    //Constructor
    public SupplyController(Context context){
        dbHelper=new DBHelper(context);
        readSupplies();
        supplierController=new SupplierController(context);
        productController=new ProductController(context);
    }

    /************************************************************************/
    //reads all supplies from db
    private void readSupplies(){
        this.supplies=new ArrayList<Supply>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.SUPPLY_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                boolean state;
                if(cursor.getInt(3)==0)state=false;
                else state=true;
                Supply supply =new Supply(cursor.getInt(0), supplierController.getSupplierById(cursor.getInt(1)), cursor.getString(2), state, Double.parseDouble(cursor.getString(4)));

                cursor2=db.rawQuery("SELECT * FROM "+DBHelper.PRODUCT_SUPPLY_TABLE+" WHERE id_supply="+cursor.getInt(0), null);
                ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
                if(cursor2.moveToFirst()){
                    do{
                        lines.add(new ProductSale(productController.getProductById(cursor2.getInt(0)), cursor2.getInt(2), Double.parseDouble(cursor2.getString(3))));
                    }while (cursor2.moveToNext());
                }
                supply.setLines(lines);
                supplies.add(supply);

            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //add methods
    public void addSupply(Supply supply){
        int id=1;
        while(existsSupply(supply.getId())){
            supply.setId(id);
            id++;
        }

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", supply.getId());
        values.put("id_supplier", supply.getSupplier().getId());
        values.put("date", supply.getDate());
        int state=0;
        if(supply.isState())state=1;
        values.put("state",state);
        values.put("shipping_costs", supply.getShipping_costs()+"");

        db.insert(DBHelper.SUPPLY_TABLE, null, values);
        db.close();

        //product_supply
        SQLiteDatabase db2=dbHelper.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for (ProductSale ps: supply.getLines()){
            values2.put("id_product", ps.getProduct().getId());
            values2.put("id_supply", supply.getId());
            values2.put("amount", ps.getAmount());
            values2.put("ind_price",ps.getIndPrice());
            db2.insert(DBHelper.PRODUCT_SUPPLY_TABLE, null, values);
        }
        db2.close();
        readSupplies();
    }

    public void addProductToSupply(ProductSale productSale, int id_supply){
        if(!existsProductInSupply(productSale.getProduct().getId(), id_supply)){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", productSale.getProduct().getId());
            values.put("id_supply", id_supply);
            values.put("amount",productSale.getAmount());
            values.put("ind_price",productSale.getIndPrice()+"");

            db.insert(DBHelper.PRODUCT_SUPPLY_TABLE, null, values);
            db.close();
        }
    }
    /************************************************************************/
    //delete methods
    public void deleteSupply(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.PRODUCT_SUPPLY_TABLE,"id_supply="+id,null);
        db.delete(DBHelper.SUPPLY_TABLE, "id="+id, null);

        readSupplies();
    }

    public void deleteProductSupply(int id_product, int id_supply) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+DBHelper.PRODUCT_SUPPLY_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+DBHelper.PRODUCT_SUPPLY_TABLE+"WHERE id_product="+id_product+" AND id_supply="+id_supply+")");
        db.close();

        checkEmptySupplies();
    }

    private void checkEmptySupplies(){
        for(Supply s:supplies){
            if(s.getLines().size()==0){
                deleteSupply(s.getId());
            }
        }
        readSupplies();
    }
    /************************************************************************/
    //update methods
    public void updateSupply(Supply supply){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", supply.getId());
        values.put("id_supplier", supply.getSupplier().getId());
        values.put("date", supply.getDate());
        int state=0;
        if(supply.isState())state=1;
        values.put("state", state);
        values.put("shipping_costs", supply.getShipping_costs()+"");

        db.update(DBHelper.SUPPLY_TABLE, values, "id="+ supply.getId(), null);

        readSupplies();
    }

    public void updateSupplyProduct(ProductSale newLine, int id_supply){
        SQLiteDatabase db =dbHelper.getWritableDatabase();

        String sql = "UPDATE "+DBHelper.PRODUCT_SUPPLY_TABLE+" SET amount = ?, ind_price = ? WHERE id_product = ? AND id_supply = ?";
        Object[] params = {newLine.getAmount(), newLine.getIndPrice(), newLine.getProduct().getId(), id_supply};
        db.execSQL(sql, params);
        db.close();

        readSupplies();
    }
    /************************************************************************/
    //information and filters about supplies
    private boolean existsSupply(int id){
        for(Supply supply :supplies){
            if(supply.getId()==id)return true;
        }
        return false;
    }

    private boolean existsProductInSupply(int id_product, int id_supply){
        for(ProductSale productSale:getSupplyById(id_supply).getLines()){
            if(productSale.getProduct().getId()==id_product)return true;
        }
        return false;
    }

    private Supply getSupplyById(int id) {
        for(Supply supply :supplies){
            if(supply.getId()==id)return supply;
        }
        return null;
    }

    public ArrayList<Supply>getPendingSuppliesOfSupplier(int id_supplier){
        ArrayList<Supply>toret=new ArrayList<Supply>();

        for(Supply supply :supplies){
            if(supply.getSupplier().getId()==id_supplier && !supply.isState()){
                toret.add(supply);
            }
        }
        return toret;
    }

    public int getNewId(){
        int toret=1;
        while(existsSupply(toret)){
            toret++;
        }
        return toret;
    }


    /************************************************************************/
    //getters && setters

    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }


}
