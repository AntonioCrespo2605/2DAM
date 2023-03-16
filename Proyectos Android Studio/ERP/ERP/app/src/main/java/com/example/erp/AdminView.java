package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.dbControllers.DBHandler;
import com.example.erp.dataBaseObjects.Employee;

public class AdminView extends AppCompatActivity {
    private DBHandler handler;
    private ImageView employeeOp, customerOp, productOp, supplierOp, moneyOp, menu, profile;
    private TextView name, empleadosText, clientesText, productosText, proveedoresText, contabilidadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        employeeOp=findViewById(R.id.employeesOption);
        customerOp=findViewById(R.id.customersOption);
        productOp=findViewById(R.id.productsOption);
        supplierOp=findViewById(R.id.suppliersOption);
        moneyOp=findViewById(R.id.moneyOption);
        menu=findViewById(R.id.menuImg);
        profile=findViewById(R.id.profile);
        name=findViewById(R.id.employeeName);
        empleadosText=findViewById(R.id.empleadosText);
        clientesText=findViewById(R.id.clientesText);
        productosText=findViewById(R.id.productosText);
        proveedoresText=findViewById(R.id.proveedoresText);
        contabilidadText=findViewById(R.id.contabilidadText);

        handler=new DBHandler(this);

        Bundle b = getIntent().getExtras();
        Employee employee=handler.getEmployeeById(b.getInt("employeeID"));

        profile.setImageBitmap(employee.getPhoto());
        name.setText(employee.getName());

        employeeOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeSelected();
            }
        });

        customerOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerSelected();
            }
        });

        productOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSelected();
            }
        });

        supplierOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierSelected();
            }
        });

        moneyOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneySelected();
            }
        });
    }

    //down menu options
    private void employeeSelected(){
        normalTints();
        employeeOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        empleadosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    private void customerSelected(){
        normalTints();
        customerOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        clientesText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    private void productSelected(){
        normalTints();
        productOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        productosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    private void supplierSelected(){
        normalTints();
        supplierOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        proveedoresText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    private void moneySelected(){
        normalTints();
        moneyOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        contabilidadText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    //auxiliar methods
    private void normalTints(){
        employeeOp.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        customerOp.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        productOp.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        supplierOp.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));
        moneyOp.setColorFilter(ContextCompat.getColor(this, R.color.light_brown));

        empleadosText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
        clientesText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
        productosText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
        proveedoresText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
        contabilidadText.setTextColor(ContextCompat.getColor(this, R.color.light_brown));
    }
}