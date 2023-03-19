package com.example.erp.fragmentsnew;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragments.EmployeesFragment;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewEmployee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewEmployee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewEmployee() {
        // Required empty public constructor
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewEmployee.
     */
    // TODO: Rename and change types and number of parameters
    public static NewEmployee newInstance(String param1, String param2) {
        NewEmployee fragment = new NewEmployee();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private EditText name, dni, phone, bank, password, salary;
    private Spinner spinner;
    private Button create;
    private TextView newId;
    private ImageView photo, gallery;
    private EmployeeController employeeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

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

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.workStations2, R.layout.spinner_item2);
        spinner.setAdapter(adapter);

        if(!createBy0){
            create.setText("Modificar");
        }

        employeeController=new EmployeeController(getContext());

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
                else{
                    employeeController.addEmployee(
                            new Employee(id,dni.getText().toString(),
                                    name.getText().toString(),
                                    phone.getText().toString(),
                                    spinner.getSelectedItem().toString(),
                                    bank.getText().toString(),
                                    Double.parseDouble(salary.getText().toString()),
                                    ImageCustomized.getBitmapFromImageView(photo),
                                    password.getText().toString() ));
                    returnToLastActivity();
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