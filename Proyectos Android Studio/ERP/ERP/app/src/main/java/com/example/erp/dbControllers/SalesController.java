package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;

import java.util.ArrayList;

public class SalesController extends SQLiteOpenHelper {

    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;
    private static final String PRODUCT_TABLE="product";
    private static final String PRODUCT_SALE_TABLE="product_sale";
    private static final String SALE_TABLE="sale";
    private static final String EMPLOYEE_TABLE="employee";
    private static final String CUSTOMER_TABLE="customer";

    private ArrayList<Sale>sales;

    private ProductController productController;
    private EmployeeController employeeController;
    private CustomerController customerController;

    /************************************************************************/
    //Constructor
    public SalesController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readSales();
        productController=new ProductController(context);
        employeeController=new EmployeeController(context);
        customerController=new CustomerController(context);
    }

    /************************************************************************/
    //onCreate & onUpdate
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table sale
        String queryTable="CREATE TABLE "+SALE_TABLE+" ("
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SALE_TABLE);
        onCreate(db);
    }
    /************************************************************************/
    //reads all sales from database
    private void readSales(){
        this.sales=new ArrayList<Sale>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+SALE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                boolean state;
                if(cursor.getInt(3)==0)state=false;
                else state=true;

                Sale sale=new Sale(cursor.getInt(0),cursor.getString(1),Double.parseDouble(cursor.getString(2)),state, employeeController.getEmployeeById(cursor.getInt(4)), customerController.getCustomerById(cursor.getInt(5)));
                cursor2=db.rawQuery("SELECT * FROM "+PRODUCT_SALE_TABLE+" WHERE id_sale="+cursor.getInt(0), null);
                ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
                if(cursor2.moveToFirst()){
                    do{
                        lines.add(new ProductSale(productController.getProductById(cursor2.getInt(0)), cursor2.getInt(2), Double.parseDouble(cursor2.getString(3))));
                    }while (cursor2.moveToNext());
                }
                sale.setLines(lines);
                sales.add(sale);
            }while(cursor.moveToNext());
        }
    }
    /************************************************************************/
    //add methods
    public void addSale(Sale sale){
        int id=1;
        while(existsSale(sale.getId())){
            sale.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", sale.getId());
        values.put("date",sale.getDate());
        values.put("shipping_costs", sale.getShipping_costs()+"");
        int state=0;
        if(sale.isState())state=1;
        values.put("state",state);
        values.put("id_employee",sale.getSeller().getId());
        values.put("id_customer",sale.getBuyer().getId());


        db.insert(SALE_TABLE, null, values);
        db.close();

        //product_sale
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for (ProductSale ps:sale.getLines()){
            values2.put("id_product", ps.getProduct().getId());
            values2.put("id_sale",sale.getId());
            values2.put("amount", ps.getAmount());
            values2.put("ind_price",ps.getIndPrice());
            db2.insert(PRODUCT_SALE_TABLE, null, values);
        }
        db2.close();
        readSales();
    }

    public void addProductInSale(ProductSale productSale, int id_sale){
        if(!existsProductInSale(productSale.getProduct().getId(), id_sale)){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", productSale.getProduct().getId());
            values.put("id_sale", id_sale);
            values.put("amount",productSale.getAmount());
            values.put("ind_price",productSale.getIndPrice()+"");

            db.insert(PRODUCT_SALE_TABLE, null, values);
            db.close();
        }
    }
    /************************************************************************/
    //delete methods
    public void deleteSale(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_SALE_TABLE,"id_sale="+id,null);
        db.delete(SALE_TABLE, "id="+id, null);

        readSales();
    }

    public void deleteProductSale(int id_product, int id_sale){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+PRODUCT_SALE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+PRODUCT_SALE_TABLE+"WHERE id_product="+id_product+" AND id_sale="+id_sale+")");
        db.close();

        checkEmptySales();
    }

    private void checkEmptySales(){
        for(Sale s:sales){
            if(s.getLines().size()==0){
                deleteSale(s.getId());
            }
        }
        readSales();
    }
    /************************************************************************/
    //udpate methods
    public void updateSale(Sale sale){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",sale.getId());
        values.put("date",sale.getDate());
        values.put("shipping_costs",sale.getShipping_costs());
        int state=0;
        if(sale.isState())state=1;
        values.put("state",state);
        values.put("id_employee", sale.getSeller().getId());
        values.put("id_customer",sale.getBuyer().getId());

        db.update(SALE_TABLE, values, "id="+sale.getId(), null);
        db.close();

        readSales();
    }

    private void updateProductSale(ProductSale productSale, int id_sale){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_product", productSale.getProduct().getId());
        values.put("id_sale", id_sale);
        values.put("amount", productSale.getAmount());
        values.put("ind_price", productSale.getIndPrice()+"");

        db.update(PRODUCT_SALE_TABLE, values, "id_product="+productSale.getProduct().getId()+" AND id_sale="+id_sale, null);
        db.close();

        readSales();
    }
    /************************************************************************/
    //information and filters about sales
    public boolean existsSale(int id){
        for(Sale sale:sales){
            if(sale.getId()==id)return true;
        }
        return false;
    }

    public Sale getSaleById(int id){
        for(Sale sale:sales){
            if(sale.getId()==id)return sale;
        }
        return null;
    }

    public boolean existsProductInSale(int id_product, int id_sale){

        for(ProductSale productSale:getSaleById(id_sale).getLines()){
            if(productSale.getProduct().getId()==id_product)return true;
        }
        return false;
    }

}
