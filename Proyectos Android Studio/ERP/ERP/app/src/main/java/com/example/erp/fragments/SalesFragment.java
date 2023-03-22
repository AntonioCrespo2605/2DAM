package com.example.erp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.R;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.uiControllers.ListAdapterSales;


public class SalesFragment extends Fragment {

    public SalesFragment() {
        // Required empty public constructor
    }

    public SalesFragment(int id_customer){
        this.id_customer=id_customer;
    }

    private int id_customer=1;
    private RecyclerView rv;
    private SalesController salesController;
    private ListAdapterSales la;

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


        return view;
    }


}