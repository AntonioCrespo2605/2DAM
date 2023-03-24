package com.example.erp.fragmentsAdminView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataBaseObjects.Supply;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SupplyController;
import com.example.erp.uiControllers.ListAdapterSupplies;


public class SuppliesFragment extends Fragment {

    private SupplyController supplyController;
    private Context context;
    private Supplier supplier;


    public SuppliesFragment() {
        // Required empty public constructor
    }

    public SuppliesFragment(Supplier supplier, Context context){
        this.supplyController=new SupplyController(context);
        this.context=context;
        this.supplier=supplier;
    }
    private Button newSupplie;
    private TextView supplierName;
    private ImageView supplierLogo;
    private RecyclerView rc;
    private ListAdapterSupplies la;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_supplies, container, false);

        newSupplie=view.findViewById(R.id.newsupplie);
        supplierName=view.findViewById(R.id.suppliername);
        supplierLogo=view.findViewById(R.id.logosupplier);
        rc=view.findViewById(R.id.rcSupplies);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh();

        newSupplie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Supply supply=new Supply(1, supplier, MyMultipurpose.getSystemDate(), false, 0);
                supplyController.addSupply(supply);
                refresh();
                Toast.makeText(context, ""+supplyController.getSuppliesOfSupplier(supplier.getId()).size(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private void refresh(){
        supplyController=new SupplyController(context);
        la=new ListAdapterSupplies(supplyController.getSuppliesOfSupplier(supplier.getId()),context);

        la.setOnItemDeleteListener(new ListAdapterSupplies.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int idSupplie) {
                supplyController.deleteSupply(idSupplie);
                refresh();
            }
        });

        la.setOnItemSelectedListener(new ListAdapterSupplies.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int idSupply) {
                FragmentProductSupply fps=new FragmentProductSupply(idSupply, context, supplier);

                FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fps);
                transaction.commit();
            }
        });

        rc.setAdapter(la);
    }
}