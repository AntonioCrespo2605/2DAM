package com.example.erp.viewsEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.R;
import com.example.erp.allUsersViews.AdminView;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragmentsnew.NewCustomer;

public class CustomersInformation extends AppCompatActivity {
    private Employee admin;
    private EmployeeController employeeController;
    private CustomerController customerController;

    //top menu
    private TextView name;
    private ImageView profile;

    //down menu
    private ImageView data, bag, sc, mailbox;
    private TextView dataText, bagText, scText, mailText;

    private int fragmentSelected=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_information);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        employeeController=new EmployeeController(this);
        customerController=new CustomerController(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin=employeeController.getEmployeeById(sharedPreferences.getInt("employeeID", 1));

        Bundle extras=getIntent().getExtras();

        profile=findViewById(R.id.profile);
        name=findViewById(R.id.employeeName);

        data=findViewById(R.id.dataImg);
        bag=findViewById(R.id.saleImg);
        sc=findViewById(R.id.scImg);
        mailbox=findViewById(R.id.mbImg);
        dataText=findViewById(R.id.data);
        bagText=findViewById(R.id.sales);
        scText=findViewById(R.id.cart);
        mailText=findViewById(R.id.mails);


        profile.setImageBitmap(admin.getPhoto());
        name.setText(admin.getName());

        NewCustomer newCustomer=new NewCustomer(customerController.getCustomerById(extras.getInt("id")));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, newCustomer);
        transaction.commit();


        //-----------------------------------------
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=1){
                    fragmentSelected=1;

                }
            }
        });

        dataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=1){
                    fragmentSelected=1;
                }
            }
        });
        //-----------------------------------------
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=2){
                    fragmentSelected=2;

                }
            }
        });

        bagText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=2){
                    fragmentSelected=2;

                }
            }
        });

        //-----------------------------------------
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=3){
                    fragmentSelected=3;

                }
            }
        });

        scText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=3){
                    fragmentSelected=3;

                }
            }
        });
        //-----------------------------------------
        mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=4){
                    fragmentSelected=4;

                }
            }
        });

        mailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=4){
                    fragmentSelected=4;

                }
            }
        });
        //-----------------------------------------

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminView.class);
        intent.putExtra("employeeID", admin.getId());
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}