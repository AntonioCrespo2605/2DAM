package com.example.erp.fragmentsNewAdminView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragmentsAdminView.EmployeesFragment;

import java.io.IOException;


public class NewEmployee extends Fragment {

    public NewEmployee() {
        createBy0=true;
    }

    private int id=1;
    private boolean createBy0;
    public NewEmployee(int id){
        this.id=id;
        createBy0=true;
    }

    private Employee employee;
    public NewEmployee(Employee employee){
        this.id=employee.getId();
        this.employee=employee;
        createBy0=false;
    }

    private EditText name, dni, phone, bank, password, salary;
    private Spinner spinner;
    private Button create;
    private TextView newId, title;
    private ImageView photo, gallery;
    private EmployeeController employeeController;
    private LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_new_employee, container, false);

        spinner=view.findViewById(R.id.spinnerWorkStationNewEmployee);
        name=view.findViewById(R.id.inputNameNewEmployee);
        dni=view.findViewById(R.id.inputDniNewEmployee);
        phone=view.findViewById(R.id.inputPhoneNewEmployee);
        bank=view.findViewById(R.id.inputBankNewEmployee);
        password=view.findViewById(R.id.inputPassNewEmployee);
        salary=view.findViewById(R.id.inputSalaryNewEmployee);
        create=view.findViewById(R.id.button3);
        newId=view.findViewById(R.id.idNewEmployee);
        photo=view.findViewById(R.id.photoNewEmployee);
        gallery=view.findViewById(R.id.galleryNewEmployee);
        title=view.findViewById(R.id.titleEmployee);
        ll=view.findViewById(R.id.ll_job);

        employeeController=new EmployeeController(getContext());

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.workStations2, R.layout.spinner_item2);
        spinner.setAdapter(adapter);

        if(!createBy0){
            create.setText("Modificar");
            name.setText(employee.getName());
            dni.setText(employee.getDni());
            phone.setText(employee.getTel());
            bank.setText(employee.getBank_number());
            password.setText(employee.getPassword());
            salary.setText(employee.getCurrent_salary()+"");
            create.setText("Modificar");
            photo.setImageBitmap(employee.getPhoto());
            title.setText("Información de empleado");
            if(employee.getWorkstation().equalsIgnoreCase("Administrador jefe")||employee.getId()==getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE).getInt("adminId", 1)){
                spinner.setVisibility(View.INVISIBLE);
                ll.setVisibility(View.INVISIBLE);
            }else{
                for(int i=0; i<20; i++){
                    if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(employee.getWorkstation())){
                        spinner.setSelection(i, true);
                        break;
                    }
                }

            }
        }

        newId.setText(""+id);

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
                if(DataChecker.isEmpty(name)) Toast.makeText(getContext(), "Rellene el nombre", Toast.LENGTH_SHORT).show();
                else if(!DataChecker.isDniValid(dni.getText().toString()))Toast.makeText(getContext(), "Formato de DNI incorrecto", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(phone)) Toast.makeText(getContext(), "Rellene el teléfono", Toast.LENGTH_SHORT).show();
                else if(!DataChecker.isBankNumber(bank.getText().toString())) Toast.makeText(getContext(), "Formato de cuenta bancaria incorrecto", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(password)) Toast.makeText(getContext(), "Rellene la contraseña", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(salary)) Toast.makeText(getContext(), "Rellene el salario", Toast.LENGTH_SHORT).show();
                else if(!DataChecker.correctDouble(salary.getText().toString())) Toast.makeText(getContext(), "Formato de salario incorrecto", Toast.LENGTH_SHORT).show();
                else{
                    if(createBy0){
                        employeeController.addEmployee(
                                new Employee(id,
                                        dni.getText().toString(),
                                        MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                        phone.getText().toString(),
                                        spinner.getSelectedItem().toString(),
                                        bank.getText().toString(),
                                        Double.parseDouble(salary.getText().toString()),
                                        ImageCustomized.getBitmapFromImageView(photo),
                                        password.getText().toString()));
                        returnToLastActivity();
                    }else {
                        String job=spinner.getSelectedItem().toString();
                        String n="Empleado";
                        if(id==1){
                            job="Administrador Jefe";
                            n="Administrador Jefe";
                        }

                        employeeController.updateEmployee(
                                new Employee(id,
                                        dni.getText().toString(),
                                        MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                        phone.getText().toString(),
                                        job,
                                        bank.getText().toString(),
                                        Double.parseDouble(salary.getText().toString()),
                                        ImageCustomized.getBitmapFromImageView(photo),
                                        password.getText().toString()));
                        Toast.makeText(getContext(), n+" modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void returnToLastActivity() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, EmployeesFragment.class, null);
        transaction.commit();
    }

    private static final int REQUEST_PICK_IMAGE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                Bitmap croppedBitmap = ImageCustomized.cropBitmapToSquare(bitmap);
                Bitmap scaledBitmap = ImageCustomized.scaleBitmap(croppedBitmap, photo.getWidth(),photo.getHeight());
                photo.setImageBitmap(scaledBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}