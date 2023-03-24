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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SupplierController;
import com.example.erp.fragmentsAdminView.SuppliersFragment;

import java.io.IOException;

public class NewSupplier extends Fragment {

    public NewSupplier() {
        // Required empty public constructor
    }

    private int id;
    private boolean createBy0;
    public NewSupplier(int id){
        this.id=id;
        createBy0=true;
    }

    private Supplier supplier;
    public NewSupplier(Supplier supplier){
        this.id=supplier.getId();
        this.supplier=supplier;
        createBy0=false;
    }

    private SupplierController supplierController;
    private EditText name, location, phone;
    private ImageView gallery, logo;
    private TextView newId;
    private Button create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_supplier, container, false);

        name=view.findViewById(R.id.inputNameNewSupplier);
        phone=view.findViewById(R.id.inputPhoneNewSupplier);
        location=view.findViewById(R.id.inputLocationNewSupplier);
        gallery=view.findViewById(R.id.galleryNewSupplier);
        logo=view.findViewById(R.id.logoNewSupplier);
        newId=view.findViewById(R.id.idNewSupplier);
        create=view.findViewById(R.id.buttonCreateNewSupplier);

        supplierController=new SupplierController(getContext());

        if(!createBy0){
            create.setText("Modificar");
            logo.setImageBitmap(supplier.getLogo());
            name.setText(supplier.getName());
            newId.setText(supplier.getId()+"");
            phone.setText(supplier.getTel());
            location.setText(supplier.getAddress());
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
                else if(DataChecker.isEmpty(phone)) Toast.makeText(getContext(), "Rellene el teléfono", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(location)) Toast.makeText(getContext(), "Rellene la dirección", Toast.LENGTH_SHORT).show();
                else{
                    if(createBy0) {
                        supplierController.addSupplier(
                                new Supplier(id,
                                        MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                        phone.getText().toString(),
                                        location.getText().toString(),
                                        ImageCustomized.getBitmapFromImageView(logo)));
                    }else{
                        supplierController.updateSupplier(
                                new Supplier(id,
                                        MyMultipurpose.capitalizeFirst(name.getText().toString()),
                                        phone.getText().toString(),
                                        location.getText().toString(),
                                        ImageCustomized.getBitmapFromImageView(logo)));
                    }
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
        transaction.replace(R.id.fragmentContainerView, SuppliersFragment.class, null);
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
                Bitmap scaledBitmap = ImageCustomized.scaleBitmap(croppedBitmap, logo.getWidth(),logo.getHeight());
                logo.setImageBitmap(scaledBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}