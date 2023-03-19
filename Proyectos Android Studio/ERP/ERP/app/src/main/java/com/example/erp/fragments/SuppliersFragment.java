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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuppliersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuppliersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SupplierController supplierController;
    private RecyclerView rv;
    private ListAdapterSuppliers la;

    public SuppliersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuppliersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuppliersFragment newInstance(String param1, String param2) {
        SuppliersFragment fragment = new SuppliersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        la=new ListAdapterSuppliers(supplierController.getSuppliers(), getContext());
        rv.setAdapter(la);

        return view;
    }
}