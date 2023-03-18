package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ShoppingCart;
import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.DBHelper;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.dbControllers.SupplierController;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenSplash extends AppCompatActivity {
    private EmployeeController employeeController;
    private ProductController productController;
    private SupplierController supplierController;
    private CustomerController customerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        employeeController=new EmployeeController(this);
        productController=new ProductController(this);
        supplierController=new SupplierController(this);
        customerController=new CustomerController(this);

        defaultValues();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenSplash.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }, 3000);

        findViewById(R.id.splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.cancel();
                Intent intent = new Intent(ScreenSplash.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void defaultValues(){
        SharedPreferences sh = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        boolean started = sh.getBoolean("started", false);
        if (!started) {
            Toast.makeText(this, "Inicializando base de datos...", Toast.LENGTH_SHORT).show();

            //default employees
            employeeController.addEmployee(new Employee(0,"00000000A","BOT","000000000","bot","BARCGB22",0, ImageCustomized.fromIntToBitmap(R.drawable.trex, this),"0"));
            employeeController.addEmployee(new Employee(1,"111111111A","Administrador","000000000","Administrador","DEUTFRPP",0,ImageCustomized.fromIntToBitmap(R.drawable.admin, this),"admin"));
            employeeController.addEmployee(new Employee(2, "22222222A", "Morty","111222333","Vendedor", "ABCDUS33",600, ImageCustomized.fromIntToBitmap(R.drawable.morty, this),"morty"));

            //default products
            productController.addProduct(new Product(1, "TRex","Carnívoro",1, 1000000.99,ImageCustomized.fromIntToBitmap(R.drawable.dinosaur1,this)));
            productController.addProduct(new Product(2, "Espinosaurio","Carnívoro",2, 1200000.50,ImageCustomized.fromIntToBitmap(R.drawable.spinosaurus,this)));

            //default suppliers
            supplierController.addSupplier(new Supplier(1, "Jurassic Park", "123123123","Av. del dinosaurio", ImageCustomized.fromIntToBitmap(R.drawable.jurassicpark,this)));
            supplierController.addSupplier(new Supplier(2, "Tienda de dinosaurios", "321321321","Vigo", ImageCustomized.fromIntToBitmap(R.drawable.tiendadinosaurios,this)));

            //default customers
            customerController.addCustomer(new Customer(1, "Juan", "111111111", "juan@gmail.com", new ShoppingCart(), ImageCustomized.fromIntToBitmap(R.drawable.juan,this),new ArrayList<Message>(),"juan"));
            customerController.addCustomer(new Customer(2, "Bender","222222222","bender@hotmail.com",new ShoppingCart(), ImageCustomized.fromIntToBitmap(R.drawable.bender,this),new ArrayList<Message>(),"bender"));
            customerController.addCustomer(new Customer(3, "Bender2","222222222","bender2@hotmail.com",new ShoppingCart(), ImageCustomized.fromIntToBitmap(R.drawable.bender,this),new ArrayList<Message>(),"bender"));

            //started for first time
            editor.putBoolean("started", true);
            editor.commit();
        }
    }
}