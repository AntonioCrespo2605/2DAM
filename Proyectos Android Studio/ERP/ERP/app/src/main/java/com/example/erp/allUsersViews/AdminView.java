package com.example.erp.allUsersViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.Login;
import com.example.erp.R;
import com.example.erp.ScreenSplash;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragmentsAdminView.AccountingFragment;
import com.example.erp.fragmentsAdminView.CustomersFragment;
import com.example.erp.fragmentsAdminView.EmployeesFragment;
import com.example.erp.fragmentsAdminView.ProductsFragment;
import com.example.erp.fragmentsAdminView.SuppliersFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class AdminView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private EmployeeController employeeController;
    private ImageView employeeOp, customerOp, productOp, supplierOp, moneyOp, profile, profileHeaderMenu;
    private TextView name, empleadosText, clientesText, productosText, proveedoresText, contabilidadText, nameHeaderMenu, idHeaderMenu;


    //variable for controlling the current fragment. It is needed to swipe
    private int fragmentSelected=3;
    private int idAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        employeeController=new EmployeeController(this);

        Bundle b = getIntent().getExtras();
        Employee employee=employeeController.getEmployeeById(b.getInt("employeeID"));
        idAdmin=employee.getId();

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        profileHeaderMenu = headerView.findViewById(R.id.profileHeader);
        nameHeaderMenu=headerView.findViewById(R.id.nameHeader);
        idHeaderMenu=headerView.findViewById(R.id.idHeader);

        profileHeaderMenu.setImageBitmap(employee.getPhoto());
        nameHeaderMenu.setText(employee.getName());
        idHeaderMenu.setText("ID: "+employee.getId());

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        employeeOp=findViewById(R.id.employeesOption);
        customerOp=findViewById(R.id.customersOption);
        productOp=findViewById(R.id.productsOption);
        supplierOp=findViewById(R.id.suppliersOption);
        moneyOp=findViewById(R.id.moneyOption);
        profile=findViewById(R.id.profile);
        name=findViewById(R.id.employeeName);
        empleadosText=findViewById(R.id.empleadosText);
        clientesText=findViewById(R.id.clientesText);
        productosText=findViewById(R.id.productosText);
        proveedoresText=findViewById(R.id.proveedoresText);
        contabilidadText=findViewById(R.id.contabilidadText);



        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("adminId", employee.getId());
        editor.commit();

        profile.setImageBitmap(employee.getPhoto());
        name.setText(employee.getName());

        employeeOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=1){
                    fragmentSelected=1;
                    employeeSelected(idAdmin);
                }
            }
        });

        customerOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=2){
                    fragmentSelected=2;
                    customerSelected();
                }
            }
        });

        productOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=3){
                    fragmentSelected=3;
                    productSelected();
                }
            }
        });

        supplierOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=4){
                    fragmentSelected=4;
                    supplierSelected();
                }
            }
        });

        moneyOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentSelected!=5){
                    fragmentSelected=5;
                    moneySelected();
                }
            }
        });
    }

    //down menu options
    private void employeeSelected(int idAdmin){
        normalTints();
        employeeOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        empleadosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        EmployeesFragment employeesFragment=new EmployeesFragment(idAdmin);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, employeesFragment, null);
        transaction.commit();

    }

    private void customerSelected(){
        normalTints();
        customerOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        clientesText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, CustomersFragment.class, null);
        transaction.commit();
    }

    private void productSelected(){
        normalTints();
        productOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        productosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, ProductsFragment.class, null);
        transaction.commit();
    }

    private void supplierSelected(){
        normalTints();
        supplierOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        proveedoresText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, SuppliersFragment.class, null);
        transaction.commit();
    }

    private void moneySelected(){
        normalTints();
        moneyOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        contabilidadText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, AccountingFragment.class, null);
        transaction.commit();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seller:sellerMenu();
                break;

            case R.id.log:
                break;

            case R.id.email:
                break;

            case R.id.github_menu:githubMenu();
                break;

            case R.id.deleteAll:deleteAll();
                break;

            case R.id.exit:exitMenu();
                break;

            case R.id.logOut:logOutMenu();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void githubMenu(){
        String url = "https://github.com/AntonioCrespo2605/2DAM/tree/main/Proyectos%20Android%20Studio/ERP";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void exitMenu(){
        finishAffinity();
    }

    private void logOutMenu(){
        finish();
    }

    private void deleteAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Borrar datos")
                .setMessage("¿Está seguro que desea borrar la base de datos?\nLa aplicación se reiniciará si acepta")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File[] files = getApplicationContext().getDatabasePath("dinosDB").getParentFile().listFiles();
                        for (File file : files) {
                            if (file.getName().endsWith(".sqlite")||file.getName().endsWith(".sqlite-journal")) {
                                file.delete();
                            }
                        }

                        SharedPreferences preferences = getSharedPreferences("myPrefs", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), ScreenSplash.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();


    }
    private void sellerMenu(){
        Intent intent2 = new Intent(this, SellerActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.putExtra("employeeID",idAdmin);
        startActivity(intent2);
        finish();
    }




}