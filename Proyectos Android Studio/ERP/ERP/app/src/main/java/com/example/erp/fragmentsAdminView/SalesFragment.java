package com.example.erp.fragmentsAdminView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.uiControllers.ListAdapterSales;


public class SalesFragment extends Fragment {

    public SalesFragment() {
        // Required empty public constructor
    }

    private Employee employee;
    private Customer customer;
    public SalesFragment(Customer customer, Employee employee){
        this.customer=customer;
        this.id_customer=customer.getId();
        this.employee=employee;
    }

    private int id_customer=1;
    private RecyclerView rv;
    private SalesController salesController;
    private ListAdapterSales la;
    private LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sales, container, false);
        rv=view.findViewById(R.id.recyclerSales);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        salesController=new SalesController(getContext());
        la=new ListAdapterSales(salesController.getSalesFromCustomer(id_customer),getContext());
        rv.setAdapter(la);

        la.setOnItemClickListener(new ListAdapterSales.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                salesController=new SalesController(getContext());
                LinesInSaleFragment newFragment = new LinesInSaleFragment(salesController.getSalesFromCustomer(id_customer).get(position).getId(), getContext());

                fragmentTransaction.replace(R.id.fragmentContainerView, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        ll=view.findViewById(R.id.createTicketLL);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sale sale=new Sale(1, MyMultipurpose.getSystemDate(), 0, false, employee, customer);
                salesController.addSale(sale);

                SalesFragment salesFragment=new SalesFragment(customer, employee);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.fragmentContainerView, salesFragment);
                transaction.commit();
            }
        });

        return view;
    }


}