package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class SupplierController{

    private DBHelper dbHelper;

    private ArrayList<Supplier>suppliers;

    /************************************************************************/
    //Constructor
    public SupplierController(Context context){
        dbHelper=new DBHelper(context);
        readSuppliers();
    }

    /************************************************************************/
    //reads all the suppliers from database
    private void readSuppliers(){
        this.suppliers=new ArrayList<Supplier>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.SUPPLIER_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                suppliers.add(new Supplier(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),ImageCustomized.fromBlobToBitmap(cursor.getBlob(4))));
            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //add method
    public void addSupplier(Supplier supplier){
        int id=1;
        while(existsSupplier(supplier.getId())){
            supplier.setId(id);
            id++;
        }

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", supplier.getId());
        values.put("name", supplier.getName());
        values.put("tel",supplier.getTel());
        values.put("address",supplier.getAddress());
        values.put("logo",ImageCustomized.fromBitmapToBlob(supplier.getLogo()));

        db.insert(DBHelper.SUPPLIER_TABLE, null, values);
        db.close();
        readSuppliers();
    }

    /************************************************************************/
    //delete method
    public void deleteSupplier(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.SUPPLIER_TABLE, "id="+id, null);

        readSuppliers();
    }

    /************************************************************************/
    //update method
    public void updateSupplier(Supplier supplier){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",supplier.getId());
        values.put("name", supplier.getName());
        values.put("tel", supplier.getTel());
        values.put("address", supplier.getAddress());
        values.put("logo", ImageCustomized.fromBitmapToBlob(supplier.getLogo()));

        db.update(DBHelper.SUPPLIER_TABLE, values, "id="+supplier.getId(), null);
        db.close();

        readSuppliers();
    }

    /************************************************************************/
    //information and filters about supplier
    public Supplier getSupplierById(int id){
        for(Supplier supplier:suppliers){
            if(supplier.getId()==id)return supplier;
        }
        return null;
    }

    public boolean existsSupplier(int id){
        for(Supplier supplier:suppliers){
            if(supplier.getId()==id)return true;
        }
        return false;
    }

    /************************************************************************/
    //getters && setters

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
