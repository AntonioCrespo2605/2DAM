package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dbControllers.CustomerController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class CreateUser extends AppCompatActivity {

    private FloatingActionButton gallery;
    private ImageView profile;
    private TextView email;
    private EditText inputName, inputTel, inputPass, inputRepeatPass;
    private Button create;
    private CustomerController customerController;

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
        create=findViewById(R.id.createAcount);

        customerController=new CustomerController(this);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataChecker.isEmpty(inputName)) Toast.makeText(CreateUser.this, "Rellene el nombre", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(inputTel)) Toast.makeText(CreateUser.this, "Rellene el telefono", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(inputPass)) Toast.makeText(CreateUser.this, "Rellene la contraseña", Toast.LENGTH_SHORT).show();
                else if(!inputPass.getText().toString().equals(inputRepeatPass.getText().toString()))Toast.makeText(CreateUser.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                else {
                    customerController.addCustomer(
                            new Customer(1,
                                    inputName.getText().toString(),
                                    inputTel.getText().toString(),
                                    email.getText().toString(),
                                    ImageCustomized.getBitmapFromImageView(profile),
                                    inputPass.getText().toString()));

                    Toast.makeText(CreateUser.this, "Usuaro creado con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateUser.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private static final int REQUEST_PICK_IMAGE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Bitmap croppedBitmap = ImageCustomized.cropBitmapToSquare(bitmap);
                Bitmap scaledBitmap = ImageCustomized.scaleBitmap(croppedBitmap, profile.getWidth(),profile.getHeight());
                profile.setImageBitmap(scaledBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}