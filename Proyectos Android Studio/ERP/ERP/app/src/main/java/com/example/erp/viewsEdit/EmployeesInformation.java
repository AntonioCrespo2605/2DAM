package com.example.erp.viewsEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.allUsersViews.AdminView;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dialogs.MessageDialog;
import com.example.erp.dialogs.SalaryDialog;
import com.example.erp.fragments.SalariesFragment;
import com.example.erp.fragmentsnew.NewEmployee;

import java.util.List;

public class EmployeesInformation extends AppCompatActivity implements SalaryDialog.OnRefreshAdapterListener {
    private EmployeeController employeeController;
    private Employee admin;

    private ImageView profile, money, data;
    private TextView name, moneyText, dataText;
    int idEmployee;

    private int fragmentSelected=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_information);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        employeeController=new EmployeeController(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin=employeeController.getEmployeeById(sharedPreferences.getInt("adminId", 1));

        Bundle extras=getIntent().getExtras();

        profile=findViewById(R.id.profile);
        name=findViewById(R.id.employeeName);
        money=findViewById(R.id.moneyOption);
        moneyText=findViewById(R.id.moneyText);
        data=findViewById(R.id.employeesOption);
        dataText=findViewById(R.id.employeesText);

        profile.setImageBitmap(admin.getPhoto());
        name.setText(admin.getName());


        idEmployee=extras.getInt("id");

        NewEmployee newEmployee=new NewEmployee(employeeController.getEmployeeById(idEmployee));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, newEmployee);
        transaction.commit();

        //------------------------------------------------------------------------------------------------------------------
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=2){
                    fragmentSelected=2;
                    moneySelected();
                }
            }
        });

        moneyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=2){
                    fragmentSelected=2;
                    moneySelected();
                }
            }
        });

        //------------------------------------------------------------------------------------------------------------------
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=1){
                    fragmentSelected=1;
                    dataSelected(newEmployee);
                }
            }
        });

        dataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=1){
                    fragmentSelected=1;
                    dataSelected(newEmployee);
                }
            }
        });
        //------------------------------------------------------------------------------------------------------------------

    }

    private void moneySelected(){
        normalTints();
        money.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        moneyText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        SalariesFragment salariesFragment=new SalariesFragment(idEmployee, this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, salariesFragment);
        transaction.commit();
    }

    private void dataSelected(NewEmployee newEmployee){
        normalTints();
        data.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        dataText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, newEmployee);
        transaction.commit();
    }

    private void normalTints(){
        data.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        dataText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));

        money.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        moneyText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminView.class);
        intent.putExtra("employeeID", admin.getId());
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRefreshAdapter() {
        moneySelected();
    }
}