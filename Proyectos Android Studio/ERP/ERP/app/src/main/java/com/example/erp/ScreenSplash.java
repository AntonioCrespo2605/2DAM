package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erp.DBControllers.DBHandler;
import com.example.erp.dataBaseObjects.Employee;

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

            Bitmap default_icon= BitmapFactory.decodeResource(this.getResources(), R.drawable.trex);
            handler.addEmployee(new Employee(0,"00000000A","BOT","000000000","bot","0",0,default_icon,"0"));
            handler.addEmployee(new Employee(1,"111111111A","ADMIN","000000000","administrador jefe","0",0,default_icon,"admin"));

            editor.putBoolean("started", true);
            editor.commit();
        }
    }
}