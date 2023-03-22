package com.example.erp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.erp.R;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.uiControllers.ListAdapterEmployees;
import com.example.erp.uiControllers.ListAdapterMail;

public class MailFragment extends Fragment {


    public MailFragment() {
        // Required empty public constructor
    }

    private int idCustomer;
    private CustomerController customerController;
    private ListAdapterMail la;
    RecyclerView rv;
    public MailFragment(int idCustomer){
        this.idCustomer=idCustomer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mail, container, false);

        customerController=new CustomerController(getContext());

        rv=view.findViewById(R.id.recyclerMail);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        la=new ListAdapterMail(customerController.getMessagesFromCustomer(idCustomer), getContext(), customerController.getCustomerById(idCustomer), this);
        rv.setAdapter(la);

        return view;
    }
}