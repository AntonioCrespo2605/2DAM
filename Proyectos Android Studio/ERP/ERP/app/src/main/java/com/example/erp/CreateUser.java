package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateUser extends AppCompatActivity {

    private FloatingActionButton gallery;
    private ImageView profile;
    private TextView email;
    private EditText inputName, inputTel, inputPass, inputRepeatPass;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        gallery=findViewById(R.id.galley);
        profile=findViewById(R.id.profileicon);
        email=findViewById(R.id.email);
        inputName=findViewById(R.id.inputName);
        inputTel=findViewById(R.id.inputTel);
        inputPass=findViewById(R.id.inputPassCreate);
        inputRepeatPass=findViewById(R.id.inputPassRepeat);


    }
}