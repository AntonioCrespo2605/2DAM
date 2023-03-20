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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.fragments.CustomersFragment;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCustomer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewCustomer() {
        // Required empty public constructor
    }

    private int id=1;
    private boolean createBy0;
    public NewCustomer(int id){
        this.id=id;
        createBy0=true;
    }

    private Customer customer=new Customer();
    public NewCustomer(Customer customer){
        this.id=customer.getId();
        this.customer=customer;
        createBy0=false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCustomer newInstance(String param1, String param2) {
        NewCustomer fragment = new NewCustomer();
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

    private CustomerController customerController;

    private EditText name, email, phone, pass;
    private ImageView photo, gallery;
    private TextView newId, title;
    private Button create;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new_customer, container, false);

        name=view.findViewById(R.id.inputNameNewCustomer);
        email=view.findViewById(R.id.inputEmailNewEmployee);
        phone=view.findViewById(R.id.inputPhoneNewCustomer);
        pass=view.findViewById(R.id.inputPassNewCustomer);
        create=view.findViewById(R.id.buttonCreateNewCustomer);
        photo=view.findViewById(R.id.photoNewCustomer);
        gallery=view.findViewById(R.id.galleryNewCustomer);
        newId=view.findViewById(R.id.idNewCustomer);
        title=view.findViewById(R.id.title);

        customerController=new CustomerController(getContext());

        if(!createBy0){
            create.setText("Modificar");
            name.setText(customer.getName());
            email.setText(customer.getEmail());
            phone.setText(customer.getTel());
            pass.setText(customer.getPassword());
            photo.setImageBitmap(customer.getPhoto());
            title.setText("Información de cliente");
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
                else if(!DataChecker.isEmail(email.getText().toString()))Toast.makeText(getContext(), "Formato de email incorrecto", Toast.LENGTH_SHORT).show();
                else if(customerController.existsEmailInCustomers(email.getText().toString())&&createBy0)Toast.makeText(getContext(), "Email repetido", Toast.LENGTH_SHORT).show();
                else if(customerController.existsEmailIgnoringUser(email.getText().toString(), id)&& !createBy0)Toast.makeText(getContext(), "Email repetido", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(phone)) Toast.makeText(getContext(), "Rellene el teléfono", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(pass)) Toast.makeText(getContext(), "Rellene la contraseña", Toast.LENGTH_SHORT).show();
                else{
                    if(createBy0){
                        customerController.addCustomer(
                                new Customer(id,
                                        MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                        phone.getText().toString(),
                                        email.getText().toString(),
                                        ImageCustomized.getBitmapFromImageView(photo),
                                        pass.getText().toString() ));
                        returnToLastFragment();
                    }else{
                        customerController.updateCustomer( new Customer(id,
                                MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                phone.getText().toString(),
                                email.getText().toString(),
                                ImageCustomized.getBitmapFromImageView(photo),
                                pass.getText().toString()));
                        Toast.makeText(getContext(), "Usuario modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

    private void returnToLastFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, CustomersFragment.class, null);
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