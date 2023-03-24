package com.example.erp.fragmentsAdminView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.R;


public class ShoppingCartFragment extends Fragment {

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    private int idCustomer;
    public ShoppingCartFragment(int idCustomer){
        this.idCustomer=idCustomer;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }
}