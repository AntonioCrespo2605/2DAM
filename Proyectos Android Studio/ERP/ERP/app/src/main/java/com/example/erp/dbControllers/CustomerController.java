package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ShoppingCart;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class CustomerController extends SQLiteOpenHelper {

    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;
    private static final String CUSTOMER_TABLE="customer";
    private static final String SHOPPING_CART="shopping_cart";
    private static final String MESSAGE_TABLE="message";
    private static final String PRODUCT_TABLE="product";

    private ArrayList<Customer>customers;


    private ImageCustomized ic;

    private ProductController productController;

    /************************************************************************/
    //Constructor
    public CustomerController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readCustomers();

        productController=new ProductController(context);
    }

    /************************************************************************/
    //onCreate and onUpgrade
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table customer
        String queryTable="CREATE TABLE IF NOT EXISTS "+CUSTOMER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"email TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);
        //Create table shopping cart
        queryTable="CREATE TABLE IF NOT EXISTS "+SHOPPING_CART+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_customer INTEGER NOT NULL REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"amount INTEGER NOT NULL, "
                +"PRIMARY KEY(id_product, id_customer));";

        db.execSQL(queryTable);

        //Create table message
        queryTable="CREATE TABLE IF NOT EXISTS "+MESSAGE_TABLE+"("
                +"id_customer INTEGER REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL,"
                +"content TEXT NOT NULL,"
                +"received INTEGER NOT NULL);";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SHOPPING_CART);
        db.execSQL("DROP TABLE IF EXISTS "+MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE);
    }
    /************************************************************************/
    //reads all customers from database
    private void readCustomers(){
        this.customers=new ArrayList<Customer>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+CUSTOMER_TABLE, null);
        Cursor cursor2, cursor3;

        if(cursor.moveToFirst()){
            do{
                Customer customer=(new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), ic.fromBlobToBitmap(cursor.getBlob(4)),cursor.getString(5)));
                cursor2=db.rawQuery("SELECT * FROM "+SHOPPING_CART+" WHERE id_customer="+cursor.getInt(0), null);
                ShoppingCart shoppingCart=new ShoppingCart();
                if(cursor2.moveToFirst()){
                    do{
                        shoppingCart.addProduct(productController.getProductById(cursor2.getInt(0)), cursor2.getInt(2));
                    }while(cursor2.moveToNext());
                }
                customer.setShoppingCart(shoppingCart);

                cursor3=db.rawQuery("SELECT * FROM "+MESSAGE_TABLE+" WHERE id_customer="+cursor.getInt(0),null);
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

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", customer.getId());
        values.put("name",customer.getName());
        values.put("tel",customer.getTel());
        values.put("email",customer.getEmail());
        values.put("photo",ic.fromBitmapToBlob(customer.getPhoto()));
        values.put("password",customer.getPassword());

        db.insert(CUSTOMER_TABLE, null, values);
        db.close();

        //sopping_cart
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<customer.getShoppingCart().getProducts().size();i++){
            values2.put("id_product",customer.getShoppingCart().getProducts().get(i).getId());
            values2.put("id_custumer", customer.getId());
            values2.put("amount",customer.getShoppingCart().getAmounts().get(i));
            db2.insert(SHOPPING_CART, null, values2);
        }
        db2.close();

        //messages
        SQLiteDatabase db3=this.getWritableDatabase();
        ContentValues values3 =new ContentValues();

        for(Message m:customer.getMailbox()){
            values3.put("id_customer", customer.getId());
            values3.put("date",m.getDate());
            values3.put("content",m.getContent());
            int received=0;
            if(m.isReceived())received=1;
            values3.put("received",received);
            db3.insert(MESSAGE_TABLE, null, values3);
        }
        db3.close();
        readCustomers();
    }

    public void addMessage(Message message, int idCustomer){
        if(!existsMessage(message.getDate(), idCustomer)){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_customer", idCustomer);
            values.put("date", message.getDate());
            values.put("content", message.getContent());
            int received=0;
            if(message.isReceived())received=1;
            values.put("received",received);

            db.insert(MESSAGE_TABLE, null, values);
            db.close();
            readCustomers();
        }
    }

    public void addProductToShoppingCart(int id_product, int id_customer, int amount){
        if(!existsProductInShoppingCart(id_product, id_customer)){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("id_product", id_product);
            values.put("id_customer", id_customer);
            values.put("amount", amount);

            db.insert(SHOPPING_CART, null, values);
            db.close();
            readCustomers();
        }
    }

    /************************************************************************/
    //delete methods

    public void deleteCustomer(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CUSTOMER_TABLE, "id="+id, null);

        readCustomers();
    }

    public void deleteProductFromShoppingCart(int id_product, int id_customer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SHOPPING_CART+" WHERE EXISTS" +
                "(SELECT * FROM "+SHOPPING_CART+" WHERE id_product="+id_product+" AND id_customer="+id_customer+")");
        db.close();

        readCustomers();
    }

    public void deleteMessage(int id_customer, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+MESSAGE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+MESSAGE_TABLE+" WHERE id_customer="+id_customer+" AND date="+"\'"+date+"\'"+")");
        db.close();

        readCustomers();
    }

    /************************************************************************/
    //updateMethods

    public void updateCustomer(Customer customer){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", customer.getId());
        values.put("name",customer.getName());
        values.put("tel", customer.getTel());
        values.put("email", customer.getEmail());
        values.put("photo",ic.fromBitmapToBlob(customer.getPhoto()));
        values.put("password", customer.getPassword());

        db.update(CUSTOMER_TABLE, values, "id="+customer.getId(), null);
        db.close();
        readCustomers();
    }

    private void updateMessage(int id_customer, Message message){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_customer", id_customer);
        values.put("date", message.getDate());
        values.put("content", message.getContent());
        int received=0;
        if(message.isReceived())received=1;
        values.put("received",received);

        db.update(MESSAGE_TABLE, values, "id_customer="+id_customer+" AND date="+message.getDate(),null);
        db.close();

        readCustomers();
    }

    private void updateProductInShoppingCart(int newAmount, int id_customer, int id_product){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("amount", newAmount);
        db.update(SHOPPING_CART, values, "id_product="+id_product+" AND id_customer="+id_customer, null);
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

}
