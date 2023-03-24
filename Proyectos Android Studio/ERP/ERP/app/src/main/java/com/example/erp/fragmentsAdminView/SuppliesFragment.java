package com.example.erp.fragmentsAdminView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.SupplyController;


public class SuppliesFragment extends Fragment {

    private SupplyController supplyController;

    public SuppliesFragment() {
        // Required empty public constructor
    }

    public SuppliesFragment(int idSupply){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplies, container, false);
    }
}