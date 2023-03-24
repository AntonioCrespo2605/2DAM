package com.example.erp.viewsEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.R;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dbControllers.SupplierController;
import com.example.erp.fragmentsAdminView.SuppliesFragment;
import com.example.erp.fragmentsNewAdminView.NewSupplier;

public class SuppliersInformation extends AppCompatActivity {

    private ImageView supplierOption, supplyOption;
    private TextView supplierText, supplyText;
    private int fragmentSelected=0;
    private SupplierController supplierController;
    private EmployeeController employeeController;
    private int idSupplier, idEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_information);

        supplyOption=findViewById(R.id.suppliesOption);
        supplierOption=findViewById(R.id.suppliersOption);
        supplierText=findViewById(R.id.suppliersText);
        supplyText=findViewById(R.id.suppliesText);

        supplierController=new SupplierController(this);
        employeeController=new EmployeeController(this);

        Bundle extras=getIntent().getExtras();
        idSupplier=extras.getInt("id",1);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        idEmployee=sharedPreferences.getInt("adminId", 1);

        supplierText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierSelected();
            }
        });
        supplierOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierSelected();
            }
        });

        supplyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplySelected();
            }
        });

        supplyOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplySelected();
            }
        });

        supplierSelected();

    }

    private void supplierSelected(){
        if(fragmentSelected!=1){
            fragmentSelected=1;
            normalTints();
            supplierOption.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
            supplierText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

            NewSupplier newSupplier=new NewSupplier(supplierController.getSupplierById(idSupplier), true);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.fragmentContainerView, newSupplier);
            transaction.commit();
        }
    }

    private void supplySelected(){
        if(fragmentSelected!=2){
            fragmentSelected=2;
            normalTints();
            supplyOption.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
            supplyText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

            SuppliesFragment suppliesFragment=new SuppliesFragment(idSupplier);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.fragmentContainerView, suppliesFragment);
            transaction.commit();
        }
    }

    private void normalTints(){
        supplierOption.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        supplierText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
        supplyOption.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        supplyText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
    }
}