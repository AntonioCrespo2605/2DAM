package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.DBHelper;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.EmployeeController;

public class Login extends AppCompatActivity {

    private EditText input1, input2;
    private ImageView image1, github;
    private TextView link1, link2;
    private Button button;

    private EmployeeController employeeController;
    private CustomerController customerController;

    private boolean interfaceEmployee=true, login=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        input1=findViewById(R.id.inputDni);
        input2=findViewById(R.id.inputPass);
        image1=findViewById(R.id.image1);
        github=findViewById(R.id.github);
        link1=findViewById(R.id.tv1);
        link2=findViewById(R.id.tv2);
        button=findViewById(R.id.button);

        employeeController=new EmployeeController(this);
        customerController=new CustomerController(this);

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode();
            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserMode();
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/AntonioCrespo2605"));
                startActivity(browserIntent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClicked();
            }
        });
    }


    private void changeMode(){
        login=true;
        input1.setText("");
        input2.setText("");
        input2.setHint("Contraseña");
        if(interfaceEmployee){
            interfaceEmployee=false;
            link1.setVisibility(View.VISIBLE);
            link1.setText("Crear un usuario nuevo");
            link2.setText("Acceder cómo empleado");
            image1.setImageResource(R.drawable.usericon);
            input1.setHint("Correo electrónico");
            button.setText("Iniciar\nSesión");
        }else{
            interfaceEmployee=true;
            link1.setVisibility(View.INVISIBLE);
            link1.setText("Crear un usuario nuevo");
            link2.setText("Acceder cómo cliente");
            image1.setImageResource(R.drawable.trex);
            input1.setHint("Identificador");
            button.setText("Gestionar");
        }
    }

    private void changeUserMode(){
        input2.setVisibility(View.VISIBLE);
        input1.setText("");
        input2.setText("");
        if(login){
            input2.setVisibility(View.INVISIBLE);
            login=false;
            link1.setText("Acceder con usuario existente");
            input1.setHint("Nuevo correo electrónico");
            button.setText("Crear\nCuenta");
        }else{
            input2.setVisibility(View.VISIBLE);
            login=true;
            link1.setText("Crear un nuevo usuario");
            input1.setHint("Correo electrónico");
            button.setText("Iniciar\nSesión");
        }
    }

    private void eventClicked(){
        if(interfaceEmployee){
            boolean finded=false;
            for(Employee e:employeeController.getEmployees()){
                if((e.getId()+"").equals(input1.getText().toString())&&input2.getText().toString().equals(e.getPassword())){
                    finded=true;
                    checkPosition(e);
                }
            }
            if(!finded){Toast.makeText(this, "Identificador o contraseña incorrecta", Toast.LENGTH_SHORT).show();}

        }else if(login){
            boolean finded=false;
            for(Customer c:customerController.getCustomers()){
                if(c.getEmail().equals(input1.getText().toString())&&input2.getText().toString().equals(c.getPassword())){
                    finded=true;
                    accessAsUser(c);
                }
            }
            if(!finded) Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();

        }else{
            boolean finded=false;
            for(Customer c: customerController.getCustomers()){
                if(c.getEmail().equals(input1.getText().toString())){
                    finded=true;
                    break;
                }
            }
            if(finded) Toast.makeText(this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
            else if(correctEmailFormat(input1.getText().toString()))createUser(input1.getText().toString());
            else Toast.makeText(this, "Formato de correo erróneo", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPosition(Employee employee){
        switch (employee.getWorkstation().toLowerCase()){
            case "administrador":
            case "administrador jefe":
                Intent intent = new Intent(Login.this, AdminView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("employeeID",employee.getId());
                startActivity(intent);
                break;
            case "bot":
                Toast.makeText(this, "Accediendo como bot", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void accessAsUser(Customer customer){
        Toast.makeText(this, "Usuario:"+customer.getName(), Toast.LENGTH_SHORT).show();
    }

    private void createUser(String email){
        Intent intent = new Intent(Login.this, CreateUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("email",input1.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    private static boolean correctEmailFormat(String email){
        email=email.trim();
        String pattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        return email.matches(pattern);
    }

}