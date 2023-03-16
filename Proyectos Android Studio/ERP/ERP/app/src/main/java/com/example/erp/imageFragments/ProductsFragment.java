package com.example.erp.imageFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dbControllers.DBHandler;
import com.example.erp.R;
import com.example.erp.uiControllers.ProductRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rv;
    private DBHandler handler;
    private ProductRecyclerViewAdapter adapter;
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

        View view=inflater.inflate(R.layout.fragment_products, container, false);
        rv=view.findViewById(R.id.recyclerProducts);

        handler=new DBHandler(getContext());
        ArrayList<Product>products=new ArrayList<Product>();
        products.add(new Product());
        products.addAll(handler.getProducts());

        adapter = new ProductRecyclerViewAdapter(getContext(), products);
        Toast.makeText(getContext(), ""+products.size(), Toast.LENGTH_SHORT).show();
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv.setAdapter(adapter);

        return inflater.inflate(R.layout.fragment_products, container, false);
    }
}