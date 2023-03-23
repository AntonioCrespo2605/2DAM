package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dbControllers.ProductController;

import java.util.ArrayList;
import java.util.List;

public class AddProductToSaleDialog extends DialogFragment {

    private ProductController productController;

    public AddProductToSaleDialog(Context context){
        this.productController=new ProductController(context);
    }

    private Spinner spinner;
    private EditText price, amount;
    private Button add;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
        View view= LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_new_line_sale, null);

        spinner=view.findViewById(R.id.spinnerAddProduct);
        price=view.findViewById(R.id.inputPriceAddProduct);
        amount=view.findViewById(R.id.inputAmounAddProduct);
        add=view.findViewById(R.id.addProduct);

        List<String> options = new ArrayList<>();

        for(Product product : productController.getProducts()){
            options.add(product.getId()+". "+product.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item2, options);
        adapter.setDropDownViewResource(R.layout.spinner_item2);

        spinner.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataChecker.isEmpty(amount)) Toast.makeText(getContext(), "Rellena la cantidad", Toast.LENGTH_SHORT).show();
                else if(!DataChecker.correctDouble(price.getText().toString())) Toast.makeText(getContext(), "Formato de precio incorrecto", Toast.LENGTH_SHORT).show();
                else{
                    mListener.onProductCreated(productController.getProducts().get(spinner.getSelectedItemPosition()), Integer.parseInt(amount.getText().toString()), Double.parseDouble(price.getText().toString()));
                    dismiss();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                price.setText(productController.getProducts().get(position).getCurrent_price()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amount.setText("1");

        builder.setView(view);
        return builder.create();
    }

    public OnProductCreatedListener mListener;

    public interface OnProductCreatedListener{
        void onProductCreated(Product product, int amount, double price);
    }

    public void setOnProductCreatedListener(AddProductToSaleDialog.OnProductCreatedListener listener) {
        mListener = listener;
    }
}
