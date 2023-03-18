package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class SupplierController extends SQLiteOpenHelper {

    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;

    private static final String SUPPLIER_TABLE="supplier";

    private ImageCustomized ic;

    private ArrayList<Supplier>suppliers;

    /************************************************************************/
    //Constructor
    public SupplierController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readSuppliers();
    }

    /************************************************************************/
    //OnCreate & onUpdate
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable="CREATE TABLE IF NOT EXISTS "+SUPPLIER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL, "
                +"address TEXT NOT NULL,"
                +"logo BLOB NOT NULL);";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIER_TABLE);
        onCreate(db);
    }

    /************************************************************************/
    //reads all the suppliers from database
    private void readSuppliers(){
        this.suppliers=new ArrayList<Supplier>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+SUPPLIER_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                suppliers.add(new Supplier(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),ic.fromBlobToBitmap(cursor.getBlob(4))));
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

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", supplier.getId());
        values.put("name", supplier.getName());
        values.put("tel",supplier.getTel());
        values.put("address",supplier.getAddress());
        values.put("logo", ic.fromBitmapToBlob(supplier.getLogo()));

        db.insert(SUPPLIER_TABLE, null, values);
        db.close();
        readSuppliers();
    }

    /************************************************************************/
    //delete method
    public void deleteSupplier(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SUPPLIER_TABLE, "id="+id, null);

        readSuppliers();
    }

    /************************************************************************/
    //update method
    public void updateSupplier(Supplier supplier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",supplier.getId());
        values.put("name", supplier.getName());
        values.put("tel", supplier.getTel());
        values.put("address", supplier.getAddress());
        values.put("logo", ic.fromBitmapToBlob(supplier.getLogo()));

        db.update(SUPPLIER_TABLE, values, "id="+supplier.getId(), null);
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





}
