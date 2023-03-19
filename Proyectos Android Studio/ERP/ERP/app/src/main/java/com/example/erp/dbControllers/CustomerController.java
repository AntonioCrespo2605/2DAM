package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ShoppingCart;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class CustomerController {

    private DBHelper dbHelper;

    private ArrayList<Customer>customers;

    private ProductController productController;


    /************************************************************************/
    //Constructor
    public CustomerController(Context context){
        dbHelper=new DBHelper(context);
        productController=new ProductController(context);

        readCustomers();
    }
    /************************************************************************/
    //reads all customers from database
    private void readCustomers(){
        this.customers=new ArrayList<Customer>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.CUSTOMER_TABLE, null);
        Cursor cursor2, cursor3;

        if(cursor.moveToFirst()){
            do{
                Customer customer=(new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), ImageCustomized.fromBlobToBitmap(cursor.getBlob(4)),cursor.getString(5)));
                cursor2=db.rawQuery("SELECT * FROM "+DBHelper.SHOPPING_CART+" WHERE id_customer="+cursor.getInt(0), null);
                ShoppingCart shoppingCart=new ShoppingCart();
                if(cursor2.moveToFirst()){
                    do{
                        shoppingCart.addProduct(productController.getProductById(cursor2.getInt(0)), cursor2.getInt(2));
                    }while(cursor2.moveToNext());
                }
                customer.setShoppingCart(shoppingCart);

                cursor3=db.rawQuery("SELECT * FROM "+DBHelper.MESSAGE_TABLE+" WHERE id_customer="+cursor.getInt(0),null);
                if(cursor3.moveToFirst()){
                    do{
                        boolean received=false;
                        if(cursor3.getInt(3)==1)received=true;
                        Message message=new Message(cursor3.getString(1), cursor3.getString(2), received);
                        customer.addMessage(message);
                    }while(cursor3.moveToNext());
                }

                customers.add(customer);
            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //add methods
    public void addCustomer(Customer customer){

        int id=1;
        while(existsCustomer(customer.getId())){
            customer.setId(id);
            id++;
        }

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", customer.getId());
        values.put("name",customer.getName());
        values.put("tel",customer.getTel());
        values.put("email",customer.getEmail());
        values.put("photo",ImageCustomized.fromBitmapToBlob(customer.getPhoto()));
        values.put("password",customer.getPassword());

        db.insert(DBHelper.CUSTOMER_TABLE, null, values);
        db.close();

        //sopping_cart
        SQLiteDatabase db2=dbHelper.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<customer.getShoppingCart().getProducts().size();i++){
            values2.put("id_product",customer.getShoppingCart().getProducts().get(i).getId());
            values2.put("id_custumer", customer.getId());
            values2.put("amount",customer.getShoppingCart().getAmounts().get(i));
            db2.insert(DBHelper.SHOPPING_CART, null, values2);
        }
        db2.close();

        //messages
        SQLiteDatabase db3=dbHelper.getWritableDatabase();
        ContentValues values3 =new ContentValues();

        for(Message m:customer.getMailbox()){
            values3.put("id_customer", customer.getId());
            values3.put("date",m.getDate());
            values3.put("content",m.getContent());
            int received=0;
            if(m.isReceived())received=1;
            values3.put("received",received);
            db3.insert(DBHelper.MESSAGE_TABLE, null, values3);
        }
        db3.close();
        readCustomers();
    }

    public void addMessage(Message message, int idCustomer){
        if(!existsMessage(message.getDate(), idCustomer)){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_customer", idCustomer);
            values.put("date", message.getDate());
            values.put("content", message.getContent());
            int received=0;
            if(message.isReceived())received=1;
            values.put("received",received);

            db.insert(DBHelper.MESSAGE_TABLE, null, values);
            db.close();
            readCustomers();
        }
    }

    public void addProductToShoppingCart(int id_product, int id_customer, int amount){
        if(!existsProductInShoppingCart(id_product, id_customer)){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", id_product);
            values.put("id_customer", id_customer);
            values.put("amount", amount);

            db.insert(DBHelper.SHOPPING_CART, null, values);
            db.close();
            readCustomers();
        }
    }

    /************************************************************************/
    //delete methods

    public void deleteCustomer(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.CUSTOMER_TABLE, "id="+id, null);

        readCustomers();
    }

    public void deleteProductFromShoppingCart(int id_product, int id_customer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+DBHelper.SHOPPING_CART+" WHERE EXISTS" +
                "(SELECT * FROM "+DBHelper.SHOPPING_CART+" WHERE id_product="+id_product+" AND id_customer="+id_customer+")");
        db.close();

        readCustomers();
    }

    public void deleteMessage(int id_customer, String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+DBHelper.MESSAGE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+DBHelper.MESSAGE_TABLE+" WHERE id_customer="+id_customer+" AND date="+"\'"+date+"\'"+")");
        db.close();

        readCustomers();
    }

    /************************************************************************/
    //updateMethods

    public void updateCustomer(Customer customer){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", customer.getId());
        values.put("name",customer.getName());
        values.put("tel", customer.getTel());
        values.put("email", customer.getEmail());
        values.put("photo",ImageCustomized.fromBitmapToBlob(customer.getPhoto()));
        values.put("password", customer.getPassword());

        db.update(DBHelper.CUSTOMER_TABLE, values, "id="+customer.getId(), null);
        db.close();
        readCustomers();
    }

    private void updateMessage(int id_customer, Message message){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_customer", id_customer);
        values.put("date", message.getDate());
        values.put("content", message.getContent());
        int received=0;
        if(message.isReceived())received=1;
        values.put("received",received);

        db.update(DBHelper.MESSAGE_TABLE, values, "id_customer="+id_customer+" AND date="+message.getDate(),null);
        db.close();

        readCustomers();
    }

    private void updateProductInShoppingCart(int newAmount, int id_customer, int id_product){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("amount", newAmount);
        db.update(DBHelper.SHOPPING_CART, values, "id_product="+id_product+" AND id_customer="+id_customer, null);
        db.close();
    }

    /************************************************************************/
    //information and filters about customers
    public boolean existsCustomer(int id){
        for(Customer customer:customers){
            if(customer.getId()==id)return true;
        }
        return false;
    }

    public boolean existsMessage(String date, int id_customer){
        for(Message m : getCustomerById(id_customer).getMailbox()){
            if(m.getDate()==date)return true;
        }
        return false;
    }

    public boolean existsProductInShoppingCart(int id_product, int id_customer){
        for(Product product : getCustomerById (id_customer).getShoppingCart().getProducts()){
            if(product.getId()==id_product)return true;
        }
        return false;
    }

    public Customer getCustomerById(int id){
        for(Customer customer:customers){
            if(customer.getId()==id)return customer;
        }
        return null;
    }

    public boolean existsEmailInCustomers(String email) {
        for(Customer customer:customers){
            if(customer.getEmail().equals(email))return true;
        }
        return false;
    }

    /************************************************************************/
    //getters && setters

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }


    public int newId() {
        int toret=1;
        while(existsCustomer(toret)){
            toret++;
        }
        return toret;
    }
}
