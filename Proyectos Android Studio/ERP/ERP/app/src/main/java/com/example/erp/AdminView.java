package com.example.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erp.dbControllers.DBHandler;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.imageFragments.AccountingFragment;
import com.example.erp.imageFragments.CustomersFragment;
import com.example.erp.imageFragments.EmployeesFragment;
import com.example.erp.imageFragments.ProductsFragment;
import com.example.erp.imageFragments.SuppliersFragment;

public class AdminView extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private DBHandler handler;
    private ImageView employeeOp, customerOp, productOp, supplierOp, moneyOp, menu, profile;
    private TextView name, empleadosText, clientesText, productosText, proveedoresText, contabilidadText;

    //variables for swipping
    private static final String TAG="Swipe Position";
    private static final int MIN_DISTANCE=150;
    private float x1, x2, y1, y2;
    private GestureDetector gestureDetector;

    //variable for controlling the current fragment. It is needed to swipe
    private int fragmentSelected=3;

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
                if(fragmentSelected!=1){
                    fragmentSelected=1;
                    employeeSelected();
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

        //initialize of gesture detector
        gestureDetector=new GestureDetector(AdminView.this, this);
    }

    //down menu options
    private void employeeSelected(){
        normalTints();
        employeeOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        empleadosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, EmployeesFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

    }

    private void customerSelected(){
        normalTints();
        customerOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        clientesText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CustomersFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    private void productSelected(){
        normalTints();
        productOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        productosText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, ProductsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    private void supplierSelected(){
        normalTints();
        supplierOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        proveedoresText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SuppliersFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    private void moneySelected(){
        normalTints();
        moneyOp.setColorFilter(ContextCompat.getColor(this, R.color.dark_brown));
        contabilidadText.setTextColor(ContextCompat.getColor(this, R.color.dark_brown));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AccountingFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
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

    //override on touch event for scrolling
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueX=x2-x1;
                float valueY=y2-y1;


                if(Math.abs(valueX)>MIN_DISTANCE){
                    if(x2>x1){
                        swipeLeft();
                    }else{
                        swipeRight();
                    }
                }
                break;

        }

        return super.onTouchEvent(event);
    }

    private void swipeLeft() {
        fragmentSelected--;
        if(fragmentSelected<1)fragmentSelected=5;
        changeFragment();
    }

    private void swipeRight() {
        fragmentSelected++;
        if(fragmentSelected>5)fragmentSelected=1;
        changeFragment();
    }

    private void changeFragment() {
        switch (fragmentSelected){
            case 1:
                employeeSelected();
                break;
            case 2:
                customerSelected();
                break;
            case 3:
                productSelected();
                break;
            case 4:
                supplierSelected();
                break;
            case 5:
                moneySelected();
                break;
        }
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}