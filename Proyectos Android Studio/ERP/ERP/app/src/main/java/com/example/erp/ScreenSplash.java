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
import com.example.erp.dbControllers.DBHandler;
import com.example.erp.dataBaseObjects.Employee;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenSplash extends AppCompatActivity {

    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        handler=new DBHandler(this);

        initAdmin();

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

    private void initAdmin(){
        SharedPreferences sh = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        boolean started = sh.getBoolean("started", false);
        if (!started) {
            Toast.makeText(this, "Inicializando base de datos...", Toast.LENGTH_SHORT).show();

            //default employees
            handler.addEmployee(new Employee(0,"00000000A","BOT","000000000","bot","0",0,fromIntToBitmap(R.drawable.trex),"0"));
            handler.addEmployee(new Employee(1,"111111111A","ADMIN","000000000","administrador jefe","0",0,fromIntToBitmap(R.drawable.admin),"admin"));

            //default products
            handler.addProduct(new Product(1, "TRex","Carnívoro",1, 1000000.99,fromIntToBitmap(R.drawable.dinosaur1)));
            handler.addProduct(new Product(2, "Espinosaurio","Carnívoro",2, 1200000.50,fromIntToBitmap(R.drawable.dinosaur1)));

            //default customers
            handler.addCustomer(new Customer(1, "Juan", "111111111", "juan@gmail.com", new ShoppingCart(), fromIntToBitmap(R.drawable.juan),new ArrayList<Message>(),"juan"));
            handler.addCustomer(new Customer(2, "Bender","222222222","bender@hotmail.com",new ShoppingCart(), fromIntToBitmap(R.drawable.bender),new ArrayList<Message>(),"bender"));
            handler.addCustomer(new Customer(3, "Bender2","222222222","bender2@hotmail.com",new ShoppingCart(), fromIntToBitmap(R.drawable.bender),new ArrayList<Message>(),"bender"));


            //started for first time
            editor.putBoolean("started", true);
            editor.commit();
        }
    }

    private Bitmap fromIntToBitmap(int img){
        return BitmapFactory.decodeResource(this.getResources(), img);
    }
}