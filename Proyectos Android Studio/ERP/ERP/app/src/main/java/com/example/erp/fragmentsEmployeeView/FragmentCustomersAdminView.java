package com.example.erp.fragmentsEmployeeView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.erp.R;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.fragmentsNewAdminView.NewCustomer;
import com.example.erp.uiControllersEmployees.CustomersAdapterEmployee;


public class FragmentCustomersAdminView extends Fragment {

    public FragmentCustomersAdminView() {
        // Required empty public constructor
    }

    private CustomersAdapterEmployee la;
    private CustomerController customerController;
    private RecyclerView rv;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_customers_admin_view, container, false);

        customerController=new CustomerController(getContext());

        rv=view.findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        la=new CustomersAdapterEmployee(customerController.getCustomers(), getContext());
        rv.setAdapter(la);

        button=view.findViewById(R.id.bnc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new NewCustomer(customerController.newId(), true)).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}