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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.ImageCustomized;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.fragments.CustomersFragment;
import com.example.erp.fragments.ProductsFragment;

import java.io.IOException;

public class NewProduct extends Fragment {

    public NewProduct() {
        // Required empty public constructor
    }

    private int newId;
    private boolean createBy0;
    public NewProduct(int newId){
        this.newId=newId;
        createBy0=true;
    }

    private Product product;
    public NewProduct(Product product){
        this.product=product;
        this.newId= product.getId();
        createBy0=false;
    }

    private ProductController productController;
    private EditText name, stock, price,description;
    private ImageView photo;
    private RelativeLayout gallery;
    private Button create;
    private TextView id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_new_product, null);

        name=view.findViewById(R.id.inputNameProduct);
        stock=view.findViewById(R.id.inputStockProduct);
        price=view.findViewById(R.id.inputPriceProduct);
        description=view.findViewById(R.id.inputDescriptionProduct);
        create=view.findViewById(R.id.createProduct);
        id=view.findViewById(R.id.idNewProduct);
        gallery=view.findViewById(R.id.galleryNewProduct);
        photo=view.findViewById(R.id.photoNewProduct);

        productController=new ProductController(getContext());

        id.setText(newId+"");

        if(!createBy0){
            name.setText(product.getName());
            stock.setText(product.getStock()+"");
            price.setText(product.getCurrent_price()+"");
            description.setText(product.getDescription());
            create.setText("modificar");
            photo.setImageBitmap(product.getPhoto());
        }

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
                if(DataChecker.isEmpty(stock)) Toast.makeText(getContext(), "Rellene el stock", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(price))Toast.makeText(getContext(), "Rellene el precio", Toast.LENGTH_SHORT).show();
                else if(!DataChecker.correctDouble(price.getText().toString()))Toast.makeText(getContext(), "Frormato de precio Incorrecto", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(name))Toast.makeText(getContext(), "Rellene el nombre", Toast.LENGTH_SHORT).show();
                else if(DataChecker.isEmpty(description))Toast.makeText(getContext(), "Rellene la descripción", Toast.LENGTH_SHORT).show();
                else{
                    if(createBy0){
                        productController.addProduct(
                                new Product(newId,
                                        name.getText().toString(),
                                        description.getText().toString(),
                                        Integer.parseInt(stock.getText().toString()),
                                        Double.parseDouble(price.getText().toString()),
                                        ImageCustomized.getBitmapFromImageView(photo)));

                    }else{
                        productController.updateProduct(
                                new Product(newId,
                                        name.getText().toString(),
                                        description.getText().toString(),
                                        Integer.parseInt(stock.getText().toString()),
                                        Double.parseDouble(price.getText().toString()),
                                        ImageCustomized.getBitmapFromImageView(photo)));
                    }
                    returnToLastFragment();

                }
            }
        });


        return view;
    }

    private void returnToLastFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragmentContainerView, ProductsFragment.class, null);
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