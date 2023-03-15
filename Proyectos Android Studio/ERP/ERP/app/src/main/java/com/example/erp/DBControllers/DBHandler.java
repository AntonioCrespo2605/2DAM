package com.example.erp.DBControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataBaseObjects.ShoppingCart;
import com.example.erp.dataBaseObjects.Supplie;
import com.example.erp.dataBaseObjects.Supplier;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    //db info
    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;

    //table names
    private static final String SUPPLIER_TABLE="supplier";
    private static final String SUPPLIE_TABLE="supplie";
    private static final String PRODUCT_TABLE="product";
    private static final String PRODUCT_SUPPLIE_TABLE="product_supplie";
    private static final String SALE_TABLE="sale";
    private static final String PRODUCT_SALE_TABLE="product_sale";
    private static final String EMPLOYEE_TABLE="employee";
    private static final String SALARY_TABLE="salary";
    private static final String CUSTOMER_TABLE="customer";
    private static final String SHOPPING_CART="shopping_cart";
    private static final String MESSAGE_TABLE="message";

    //controllers
    private ArrayList<Customer>customers;
    private ArrayList<Employee>employees;
    private ArrayList<Product>products;
    private ArrayList<Supplie>supplies;
    private ArrayList<Supplier>suppliers;
    private ArrayList<Sale>sales;

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table supplier
        String queryTable="CREATE TABLE "+SUPPLIER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL, "
                +"address TEXT NOT NULL,"
                +"logo BLOB NOT NULL);";

        db.execSQL(queryTable);

        //Create table supplie
        queryTable="CREATE TABLE "+SUPPLIE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"id_supplier INTEGER NOT NULL REFERENCES "+SUPPLIER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL, "
                +"state INTEGER NOT NULL,"
                +"shipping_costs TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table product
        queryTable="CREATE TABLE "+PRODUCT_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"description TEXT NOT NULL,"
                +"stock INTEGER NOT NULL,"
                +"current_price TEXT NOT NULL,"
                +"photo BLOB);";

        db.execSQL(queryTable);

        //Create table product_supplie
        queryTable="CREATE TABLE "+PRODUCT_SUPPLIE_TABLE+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_supplie INTEGER NOT NULL REFERENCES "+SUPPLIE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"amount INTEGER NOT NULL,"
                +"ind_price TEXT NOT NULL,"
                +"PRIMARY KEY(id_product, id_supplie));";

        db.execSQL(queryTable);

        //Create table employee
        queryTable="CREATE TABLE "+EMPLOYEE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"dni TEXT NOT NULL,"
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"workstation TEXT NOT NULL,"
                +"bank_number TEXT NOT NULL, "
                +"current_salary TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table salary
        queryTable="CREATE TABLE "+SALARY_TABLE+" ("
                +"date TEXT NOT NULL, "
                +"id_employee INTEGER NOT NULL REFERENCES "+EMPLOYEE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "//
                +"salary TEXT NOT NULL, "
                +"PRIMARY KEY(date, id_employee));";

        db.execSQL(queryTable);

        //Create table customer
        queryTable="CREATE TABLE "+CUSTOMER_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"email TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table sale
        queryTable="CREATE TABLE "+SALE_TABLE+" ("
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

        //Create table shopping cart
        queryTable="CREATE TABLE "+SHOPPING_CART+" ("
                +"id_product INTEGER NOT NULL REFERENCES "+PRODUCT_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"id_customer INTEGER NOT NULL REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                +"amount INTEGER NOT NULL, "
                +"PRIMARY KEY(id_product, id_customer));";

        db.execSQL(queryTable);

        //Create table message
        queryTable="CREATE TABLE "+MESSAGE_TABLE+"("
                +"id_customer INTEGER REFERENCES "+CUSTOMER_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                +"date TEXT NOT NULL,"
                +"content TEXT NOT NULL,"
                +"received INTEGER NOT NULL);";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_SUPPLIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SUPPLIER_TABLE);

        onCreate(db);
    }

    /************************************************************************/
    //Constructor
    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readDB();
    }

    /************************************************************************/
    //read from database methods

    //read all database
    private void readDB(){
        readCustomers();
        readSuppliers();
        readProducts();
        readEmployees();
        readSupplies();
        readSales();
    }

    //read Customers information
    private void readCustomers(){
        this.customers=new ArrayList<Customer>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+CUSTOMER_TABLE, null);
        Cursor cursor2, cursor3;

        if(cursor.moveToFirst()){
            do{
                Customer customer=(new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), fromBlobToBitmap(cursor.getBlob(4)),cursor.getString(5)));
                cursor2=db.rawQuery("SELECT * FROM "+SHOPPING_CART+" WHERE id_costumer="+cursor.getInt(0), null);
                ShoppingCart shoppingCart=new ShoppingCart();
                if(cursor2.moveToFirst()){
                    do{
                        shoppingCart.addProduct(getProductById(cursor2.getInt(0)), cursor2.getInt(2));
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

    //read suppliers information
    private void readSuppliers(){
        this.suppliers=new ArrayList<Supplier>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+SUPPLIER_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                suppliers.add(new Supplier(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),fromBlobToBitmap(cursor.getBlob(4))));
            }while(cursor.moveToNext());
        }
    }

    //read products information
    private void readProducts(){
        this.products=new ArrayList<Product>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+PRODUCT_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                double current_price=Double.parseDouble(cursor.getString(4));
                products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),current_price,fromBlobToBitmap(cursor.getBlob(5))));
            }while(cursor.moveToNext());
        }
    }

    //read employees information
    private void readEmployees(){
        this.employees=new ArrayList<Employee>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+EMPLOYEE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                Employee employee=new Employee(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), Double.parseDouble(cursor.getString(6)),fromBlobToBitmap(cursor.getBlob(7)),cursor.getString(8));

                cursor2=db.rawQuery("SELECT * FROM "+SALARY_TABLE+" WHERE id_employee="+cursor.getInt(0), null);
                ArrayList<Salary>salaries=new ArrayList<Salary>();
                if(cursor2.moveToFirst()){
                    do{
                        salaries.add(new Salary(Double.parseDouble(cursor2.getString(2)),cursor2.getString(0)));
                    }while (cursor2.moveToNext());
                }
                employee.setSalaries(salaries);
                employees.add(employee);

            }while(cursor.moveToNext());
        }
    }

    //read supplies information
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
                Supplie supplie=new Supplie(cursor.getInt(0), getSupplierById(cursor.getInt(1)), cursor.getString(2), state, Double.parseDouble(cursor.getString(4)));

                cursor2=db.rawQuery("SELECT * FROM "+PRODUCT_SUPPLIE_TABLE+" WHERE id_supplie="+cursor.getInt(0), null);
                ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
                if(cursor2.moveToFirst()){
                    do{
                        lines.add(new ProductSale(getProductById(cursor2.getInt(0)), cursor2.getInt(2), Double.parseDouble(cursor2.getString(3))));
                    }while (cursor2.moveToNext());
                }
                supplie.setLines(lines);
                supplies.add(supplie);

            }while(cursor.moveToNext());
        }
    }

    //read sales information
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

                Sale sale=new Sale(cursor.getInt(0),cursor.getString(1),Double.parseDouble(cursor.getString(2)),state, getEmployeeById(cursor.getInt(4)), getCustomerById(cursor.getInt(5)));
                cursor2=db.rawQuery("SELECT * FROM "+PRODUCT_SALE_TABLE+" WHERE id_sale="+cursor.getInt(0), null);
                ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
                if(cursor2.moveToFirst()){
                    do{
                        lines.add(new ProductSale(getProductById(cursor2.getInt(0)), cursor2.getInt(2), Double.parseDouble(cursor2.getString(3))));
                    }while (cursor2.moveToNext());
                }
                sale.setLines(lines);
                sales.add(sale);
            }while(cursor.moveToNext());
        }
    }


    /************************************************************************/
    //information filters

    private Supplier getSupplierById(int id){
        for(Supplier supplier:suppliers){
            if(supplier.getId()==id)return supplier;
        }
        return null;
    }

    private Product getProductById(int id){
        for(Product product:products){
            if(product.getId()==id)return product;
        }
        return null;
    }

    private Employee getEmployeeById(int id){
        for(Employee employee:employees){
            if(employee.getId()==id)return employee;
        }
        return null;
    }

    private Customer getCustomerById(int id){
        for(Customer customer:customers){
            if(customer.getId()==id)return customer;
        }
        return null;
    }

    /************************************************************************/
    //adding Methods

    //add a new Customer to the database
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
        values.put("photo",fromBitmapToBlob(customer.getPhoto()));
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

    //add a new Employee to the database
    public void addEmployee(Employee employee){
        int id=1;
        while(existsEmployee(employee.getId())){
            employee.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel",employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number", employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary()+"");
        values.put("photo", fromBitmapToBlob(employee.getPhoto()));
        values.put("password",employee.getPassword());

        db.insert(EMPLOYEE_TABLE, null, values);
        db.close();

        //salaries
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<employee.getSalaries().size();i++){
            values2.put("date",employee.getSalaries().get(i).getDate());
            values2.put("id_employee", employee.getId());
            values2.put("salary",employee.getSalaries().get(i).getSalary()+"");
            db2.insert(SALARY_TABLE, null, values2);
        }
        db2.close();
        readEmployees();
    }

    //add a new Product to the database
    public void addProduct(Product product){
        int id=1;
        while(existsProduct(product.getId())){
            product.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description",product.getDescription());
        values.put("stock",product.getStock());
        values.put("current_price", product.getCurrent_price()+"");
        values.put("photo", fromBitmapToBlob(product.getPhoto()));

        db.insert(PRODUCT_TABLE, null, values);
        db.close();
        readProducts();
    }

    //add a new Supplier to the database
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
        values.put("logo", fromBitmapToBlob(supplier.getLogo()));

        db.insert(SUPPLIER_TABLE, null, values);
        db.close();
        readSuppliers();
    }

    //add a new Supplie to the database
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

    //add a new Sale to the database
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

    /************************************************************************/
    //delete methods

    //delete Supplier(it also deletes from supplie table)
    public void deleteSupplier(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Supplie s:supplies){
            if(s.getSupplier().getId()==id){
                db.delete(PRODUCT_SUPPLIE_TABLE,"id_supplie="+s.getId(),null);
            }
        }

        db.delete(SUPPLIE_TABLE, "id_supplier="+id, null);
        db.delete(SUPPLIER_TABLE, "id="+id, null);

        readSuppliers();
        readSupplies();
    }

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

        readSupplies();

        checkEmptySupplies();
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(PRODUCT_SUPPLIE_TABLE,"id_product="+id,null);
        db.delete(PRODUCT_SALE_TABLE,"id_product="+id, null);
        db.delete(SHOPPING_CART,"id_product="+id,null);

        db.delete(PRODUCT_TABLE, "id="+id, null);

        readProducts();
        readSupplies();
        readCustomers();
        readSales();

        checkEmptySupplies();
        checkEmptySales();
    }

    public void deleteProductSale(int id_product, int id_sale){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+PRODUCT_SALE_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+PRODUCT_SALE_TABLE+"WHERE id_product="+id_product+" AND id_sale="+id_sale+")");
        db.close();

        readSales();
        checkEmptySales();
    }

    public void deleteShoppingCart(int id_product, int id_customer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SHOPPING_CART+" WHERE EXISTS" +
                "(SELECT * FROM "+SHOPPING_CART+" WHERE id_product="+id_product+" AND id_customer="+id_customer+")");
        db.close();

        readCustomers();
    }

    public void deleteSale(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_SALE_TABLE,"id_sale="+id,null);
        db.delete(SALE_TABLE, "id="+id, null);

        readSales();
    }

    public void deleteEmployee(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SALARY_TABLE, "id_employee="+id, null);
        db.delete(SALE_TABLE, "id_employee="+id, null);
        db.delete(EMPLOYEE_TABLE, "id="+id, null);

        readEmployees();
        readSales();
    }

    public void deleteSalary(int id_employee, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SALARY_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+SALARY_TABLE+" WHERE id_employee="+id_employee+" AND date="+date+")");
        db.close();

        readEmployees();
    }

    public void deleteCustomer(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SHOPPING_CART, "id_customer="+id, null);

        for(Sale s:sales){
            if(s.getBuyer().getId()==id){
                db.delete(PRODUCT_SALE_TABLE, "id_sale="+s.getId(), null);
            }
        }

        db.delete(SALE_TABLE, "id_customer="+id, null);
        db.delete(CUSTOMER_TABLE, "id="+id, null);

        readSales();
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
    //automatic delete methods
    private void checkEmptySupplies(){
        for(Supplie s:supplies){
            if(s.getLines().size()==0){
                deleteSupplie(s.getId());
            }
        }
        readSupplies();
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
    //update methods

    public void updateSupplier(Supplier supplier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",supplier.getId());
        values.put("name", supplier.getName());
        values.put("tel", supplier.getTel());
        values.put("address", supplier.getAddress());
        values.put("logo", fromBitmapToBlob(supplier.getLogo()));

        db.update(SUPPLIER_TABLE, values, "id="+supplier.getId(), null);
        db.close();

        readSuppliers();
        readSupplies();
    }

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

    public void updateProduct(Product product){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("stock", product.getStock());
        values.put("current_price",product.getCurrent_price()+"");
        values.put("photo", fromBitmapToBlob(product.getPhoto()));

        db.update(PRODUCT_TABLE, values, "id="+product.getId(), null);
        db.close();

        readProducts();
    }

    public void updateCustomer(Customer customer){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", customer.getId());
        values.put("name",customer.getName());
        values.put("tel", customer.getTel());
        values.put("email", customer.getEmail());
        values.put("photo",fromBitmapToBlob(customer.getPhoto()));
        values.put("password", customer.getPassword());

        db.update(CUSTOMER_TABLE, values, "id="+customer.getId(), null);
        db.close();
        readCustomers();
        readSales();
    }

    public void updateEmployee(Employee employee){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel", employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number",employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary());
        values.put("photo",fromBitmapToBlob(employee.getPhoto()));
        values.put("password", employee.getPassword());

        db.update(EMPLOYEE_TABLE, values, "id="+employee.getId(), null);
        db.close();

        readEmployees();
    }

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

    private void updateSalary(int id_employee, Salary salary){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_employee", id_employee);
        values.put("date", salary.getDate());
        values.put("salary", salary.getSalary());

        db.update(SALARY_TABLE, values, "id_employee="+id_employee+" AND date="+salary.getDate(),null);
        db.close();

        readEmployees();
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

    private void updateShoppingCart(int amount, int id_customer, int id_product){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("amount", amount);
        db.update(SHOPPING_CART, values, "id_product="+id_product+" AND id_customer="+id_customer, null);
        db.close();
    }

    /************************************************************************/
    //check if exists

    private boolean existsCustomer(int id){
        for(Customer customer:customers){
            if(customer.getId()==id)return true;
        }
        return false;
    }

    private boolean existsEmployee(int id){
        for(Employee employee:employees){
            if(employee.getId()==id)return true;
        }
        return false;
    }

    private boolean existsProduct(int id){
        for(Product product:products){
            if(product.getId()==id)return true;
        }
        return false;
    }

    private boolean existsSupplie(int id){
        for(Supplie supplie:supplies){
            if(supplie.getId()==id)return true;
        }
        return false;
    }

    private boolean existsSupplier(int id){
        for(Supplier supplier:suppliers){
            if(supplier.getId()==id)return true;
        }
        return false;
    }

    private boolean existsSale(int id){
        for(Sale sale:sales){
            if(sale.getId()==id)return true;
        }
        return false;
    }

    /************************************************************************/
    //Other auxiliar methods

    //it transforms a blob(byteArray)to Bitmap
    private Bitmap fromBlobToBitmap(byte[]imgByte){
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
    }

    //it transforms a Bitmap to blob(byteArray)
    private byte[] fromBitmapToBlob(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /************************************************************************/
    //getters and setters

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Supplie> getSupplies() {
        return supplies;
    }

    public void setSupplies(ArrayList<Supplie> supplies) {
        this.supplies = supplies;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }
}