package com.example.erp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.R;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.uiControllers.ListAdapterCustomer;

public class CustomersFragment extends Fragment {
    private ListAdapterCustomer la;
    private CustomerController customerController;
    private RecyclerView rv;


    public CustomersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customers, container, false);

        customerController=new CustomerController(getContext());

        rv=view.findViewById(R.id.recyclerCustomers);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        la=new ListAdapterCustomer(customerController.getCustomers(), customerController.newId(), getContext());
        rv.setAdapter(la);


        return view;
    }
}