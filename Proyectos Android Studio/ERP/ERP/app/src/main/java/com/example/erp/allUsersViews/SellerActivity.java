package com.example.erp.allUsersViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragmentsEmployeeView.FragmentCustomersAdminView;
import com.google.android.material.navigation.NavigationView;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ImageView profile;
    private TextView name;
    private EmployeeController employeeController;
    private Employee seller;

    private ImageView profileHeaderMenu;
    private TextView nameHeaderMenu, idHeaderMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        employeeController=new EmployeeController(this);
        Bundle extras=getIntent().getExtras();
        seller=employeeController.getEmployeeById(extras.getInt("employeeID", 0));

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        profileHeaderMenu = headerView.findViewById(R.id.profileHeader);
        nameHeaderMenu=headerView.findViewById(R.id.nameHeader);
        idHeaderMenu=headerView.findViewById(R.id.idHeader);

        profileHeaderMenu.setImageBitmap(seller.getPhoto());
        nameHeaderMenu.setText(seller.getName());
        idHeaderMenu.setText("ID: "+seller.getId());

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        profile=findViewById(R.id.profile);
        name=findViewById(R.id.employeeName);

        profile.setImageBitmap(seller.getPhoto());
        name.setText(seller.getName());


        changeFragment();
    }
    private void changeFragment() {
        FragmentCustomersAdminView fcav=new FragmentCustomersAdminView(seller);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, fcav, null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newPass:
                break;

            case R.id.exit2:exitMenu();
                break;

            case R.id.logOut2:logOutMenu();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exitMenu(){
        finishAffinity();
    }

    private void logOutMenu(){
        finish();
    }
}