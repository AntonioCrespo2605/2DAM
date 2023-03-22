package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;

import java.util.ArrayList;

public class SalesController{
    private DBHelper dbHelper;

    private ArrayList<Sale>sales;

    private ProductController productController;
    private EmployeeController employeeController;
    private CustomerController customerController;

    /************************************************************************/
    //Constructor
    public SalesController(Context context){
        dbHelper=new DBHelper(context);
        productController=new ProductController(context);
        employeeController=new EmployeeController(context);
        customerController=new CustomerController(context);
        readSales();
    }


    /************************************************************************/
    //reads all sales from database
    private void readSales(){
        this.sales=new ArrayList<Sale>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.SALE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                boolean state;
                if(cursor.getInt(3)==0)state=false;
                else state=true;

                Sale sale=new Sale(cursor.getInt(0),cursor.getString(1),Double.parseDouble(cursor.getString(2)),state, employeeController.getEmployeeById(cursor.getInt(4)), customerController.getCustomerById(cursor.getInt(5)));
                cursor2=db.rawQuery("SELECT * FROM "+DBHelper.PRODUCT_SALE_TABLE+" WHERE id_sale="+cursor.getInt(0), null);
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

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", sale.getId());
        values.put("date",sale.getDate());
        values.put("shipping_costs", sale.getShipping_costs()+"");
        int state=0;
        if(sale.isState())state=1;
        values.put("state",state);
        values.put("id_employee",sale.getSeller().getId());
        values.put("id_customer",sale.getBuyer().getId());


        db.insert(DBHelper.SALE_TABLE, null, values);
        db.close();

        //product_sale
        SQLiteDatabase db2=dbHelper.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for (ProductSale ps:sale.getLines()){
            values2.put("id_product", ps.getProduct().getId());
            values2.put("id_sale",sale.getId());
            values2.put("amount", ps.getAmount());
            values2.put("ind_price",ps.getIndPrice());
            db2.insert(DBHelper.PRODUCT_SALE_TABLE, null, values2);
        }
        db2.close();
        readSales();
    }

    public void addProductInSale(ProductSale productSale, int id_sale){
        if(!existsProductInSale(productSale.getProduct().getId(), id_sale)){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", productSale.getProduct().getId());
            values.put("id_sale", id_sale);
            values.put("amount",productSale.getAmount());
            values.put("ind_price",productSale.getIndPrice()+"");

            db.insert(DBHelper.PRODUCT_SALE_TABLE, null, values);
            db.close();
        }
    }
    /************************************************************************/
    //delete methods
    public void deleteSale(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.PRODUCT_SALE_TABLE,"id_sale="+id,null);
        db.delete(DBHelper.SALE_TABLE, "id="+id, null);

        readSales();
    }

    public void deleteProductSale(int id_product, int id_sale){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.PRODUCT_SALE_TABLE,"id_sale="+id_sale+" AND id_product="+id_product,null);

        db.close();

        readSales();
        //checkEmptySales();
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
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",sale.getId());
        values.put("date",sale.getDate());
        values.put("shipping_costs",sale.getShipping_costs());
        int state=0;
        if(sale.isState())state=1;
        values.put("state",state);
        values.put("id_employee", sale.getSeller().getId());
        values.put("id_customer",sale.getBuyer().getId());

        db.update(DBHelper.SALE_TABLE, values, "id="+sale.getId(), null);
        db.close();

        readSales();
    }

    public void updateProductSale(ProductSale productSale, int id_sale){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_product", productSale.getProduct().getId());
        values.put("id_sale", id_sale);
        values.put("amount", productSale.getAmount());
        values.put("ind_price", productSale.getIndPrice()+"");

        db.update(DBHelper.PRODUCT_SALE_TABLE, values, "id_product="+productSale.getProduct().getId()+" AND id_sale="+id_sale, null);
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

    public ArrayList<Sale> getSalesFromCustomer(int id_customer){
        ArrayList<Sale>toret=new ArrayList<Sale>();
        for(Sale sale:sales){
            if(sale.getBuyer().getId()==id_customer)toret.add(new Sale(sale));
        }
        return toret;
    }

    /************************************************************************/
    //getters && setters

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }
}
