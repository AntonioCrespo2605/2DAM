package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.erp.DBControllers.DBHandler;

public class AdminView extends AppCompatActivity {
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        handler=new DBHandler(this);

        Bundle b = getIntent().getExtras();


    }
}