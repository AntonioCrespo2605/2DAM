package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dataBaseObjects.Transation;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class TransationController {

    private DBHelper dbHelper;
    private ArrayList<Transation>transations;

    /************************************************************************/
    public TransationController(Context context){
        this.dbHelper=new DBHelper(context);
        readTransations();
    }

    /************************************************************************/
    private void readTransations() {
        transations=new ArrayList<Transation>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.TRANSATION_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                Transation transation=new Transation(cursor.getInt(0),cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3)));
                transations.add(transation);
            }while(cursor.moveToNext());
        }
    }
    /************************************************************************/

    public void addTransation(Transation transation){
        int id=1;
        while(existsTransation(transation.getId())){
            transation.setId(id);
            id++;
        }

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", transation.getId());
        values.put("date", transation.getDate());
        values.put("reason",transation.getReason());
        values.put("amount",transation.getAmount());

        db.insert(DBHelper.TRANSATION_TABLE, null, values);
        db.close();
        readTransations();
    }

    /************************************************************************/

    public void deleteTransation(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TRANSATION_TABLE, "id="+id, null);

       readTransations();
    }
    /************************************************************************/
    private void updateTransation(Transation transation){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", transation.getId());
        values.put("date", transation.getDate());
        values.put("reason",transation.getReason());
        values.put("amount",transation.getAmount());

        db.update(DBHelper.TRANSATION_TABLE, values, "id="+transation.getId(), null);
        db.close();

        readTransations();
    }

    /************************************************************************/
    public boolean existsTransation(int id){
        for(Transation transation:transations){
            if(transation.getId()==id)return true;
        }
        return false;
    }



}
