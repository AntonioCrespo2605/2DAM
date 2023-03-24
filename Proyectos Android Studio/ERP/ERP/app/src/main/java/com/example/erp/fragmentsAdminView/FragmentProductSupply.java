package com.example.erp.fragmentsAdminView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dataBaseObjects.Supply;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.dbControllers.SupplyController;
import com.example.erp.dialogs.DialogProducts;
import com.example.erp.uiControllers.ListAdapterProductSupply;
import com.example.erp.uiControllersEmployees.ListAdapterProductsInSale;

import java.util.ArrayList;

public class FragmentProductSupply extends Fragment {

    public FragmentProductSupply() {
        // Required empty public constructor
    }

    public FragmentProductSupply(int idSupplie, Context context, Supplier supplier){
        this.idSupplie=idSupplie;
        this.supplyController=new SupplyController(context);
        this.productController=new ProductController(context);
        this.supplier=supplier;
        this.context=context;
        this.fragment=this;
    }

    private Supplier supplier;
    private Context context;
    private int idSupplie;
    private ListAdapterProductSupply la;
    private SupplyController supplyController;
    private ProductController productController;
    private ArrayList<Product>products;
    private Fragment fragment;

    private TextView name, number, total;
    private RecyclerView rv;
    private EditText sc;
    private CheckBox checkBox;
    private Button button;
    private LinearLayout insert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_supply, container, false);

        name=view.findViewById(R.id.name);
        number=view.findViewById(R.id.phone);
        total=view.findViewById(R.id.total);
        rv=view.findViewById(R.id.rv);
        sc=view.findViewById(R.id.sc);
        checkBox=view.findViewById(R.id.checkbox);
        button=view.findViewById(R.id.pay);
        insert=view.findViewById(R.id.ll);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        Supply supply=supplyController.getSupplyById(idSupplie);

        products=new ArrayList<Product>();
        for(ProductSale ps:supply.getLines()){
            for(int i=0;i<ps.getAmount();i++){
                products.add(ps.getProduct());
            }
        }

        refresh();

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogProducts dp =new DialogProducts(productController.getProducts(), true);
                dp.setOnItemReceivedListener(new DialogProducts.OnItemReceivedListener() {
                    @Override
                    public void onItemReceived(int idProduct) {
                        products.add(productController.getProductById(idProduct));
                        refresh();
                    }
                });
                dp.show(fragment.getChildFragmentManager(), null);
            }
        });

        return view;
    }

    private void refresh() {
        productController=new ProductController(context);

        la=new ListAdapterProductSupply(products, context);
        la.setOnItemDelete(new ListAdapterProductsInSale.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int idProduct) {
                for(int i=0;i<products.size();i++){
                    if(products.get(i).getId()==idProduct){
                        products.remove(i);
                        break;
                    }
                }
                refresh();
            }
        });

        rv.setAdapter(la);
    }


}