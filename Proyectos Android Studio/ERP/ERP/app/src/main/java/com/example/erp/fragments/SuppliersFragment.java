package com.example.erp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.R;
import com.example.erp.dbControllers.SupplierController;
import com.example.erp.uiControllers.ListAdapterSuppliers;

public class SuppliersFragment extends Fragment {
    private SupplierController supplierController;
    private RecyclerView rv;
    private ListAdapterSuppliers la;

    public SuppliersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_suppliers, container, false);

        supplierController=new SupplierController(getContext());

        rv=view.findViewById(R.id.recyclerSuppliers);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        la=new ListAdapterSuppliers(supplierController.getSuppliers(), supplierController.getNewId(), getContext());
        rv.setAdapter(la);

        return view;
    }
}