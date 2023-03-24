package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.uiControllersEmployees.ListAdapterProductsInSale;

import java.util.ArrayList;

public class DialogProducts extends DialogFragment {

    private ArrayList<Product>products;
    public DialogProducts(ArrayList<Product>products){
        this.products=products;
        this.showall=false;
    }

    public DialogProducts(ArrayList<Product>products, boolean showall){
        this.products=products;
        this.showall=showall;
    }

    private boolean showall;
    private ListAdapterProductsInSale la;
    private RecyclerView rv;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());

        View view= LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_products, null);

        rv=view.findViewById(R.id.rcDialog);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        la=new ListAdapterProductsInSale(products,getContext(),true,showall);

        la.setOnItemSelected(new ListAdapterProductsInSale.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int idProduct) {
                listener.onItemReceived(idProduct);
                dismiss();
            }
        });

        rv.setAdapter(la);

        builder.setView(view);

        return builder.create();
    }


    public interface OnItemReceivedListener{
        void onItemReceived(int idPorduct);
    }

    public OnItemReceivedListener listener;

    public void setOnItemReceivedListener(OnItemReceivedListener listener){
        this.listener=listener;
    }
}
