package com.example.erp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erp.dataBaseObjects.Product;
import com.example.erp.R;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.uiControllers.ProductRecyclerViewAdapter;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    private RecyclerView rv;
    private ProductController productController;
    private ProductRecyclerViewAdapter adapter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_products, container, false);
        rv=view.findViewById(R.id.recyclerProducts);

        productController=new ProductController(getContext());
        ArrayList<Product>products=new ArrayList<Product>();
        products.add(new Product());
        products.addAll(productController.getProducts());

        adapter = new ProductRecyclerViewAdapter(getContext(), productController.newId(), products);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv.setAdapter(adapter);

        return view;
    }
}