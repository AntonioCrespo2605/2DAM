package com.example.erp.fragmentsEmployeeView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.dialogs.DialogProducts;
import com.example.erp.dialogs.MessageDialog;
import com.example.erp.uiControllers.ListAdapterCustomer;
import com.example.erp.uiControllersEmployees.ListAdapterProductsInSale;

import java.util.ArrayList;

public class FragmentSaleEmployee extends Fragment implements TextWatcher {


    public FragmentSaleEmployee() {
        // Required empty public constructor
    }

    private CustomerController customerController;
    private SalesController salesController;
    private ProductController productController;
    private ListAdapterProductsInSale la;
    private Customer customer;
    private Employee employee;
    private ArrayList<Product>products;

    private EditText shippings;


    private ImageView profile;
    private TextView name, mail, total;
    private RecyclerView rv;
    private Button pay;
    private LinearLayout ll;
    private Fragment fragment=this;
    private ArrayList<Product>allProducts;
    private double topay=0;

    public FragmentSaleEmployee(Customer customer, Employee employee, Context context){
        this.salesController=new SalesController(context);
        this.customerController=new CustomerController(context);
        this.productController=new ProductController(context);
        this.customer=customer;
        this.employee=employee;
        this.products=new ArrayList<Product>();
        this.allProducts=productController.getProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_sale_employee, container, false);

        profile=view.findViewById(R.id.profile);
        name=view.findViewById(R.id.name);
        mail=view.findViewById(R.id.mail);
        rv=view.findViewById(R.id.rv);
        total=view.findViewById(R.id.total);
        pay=view.findViewById(R.id.pay);
        shippings=view.findViewById(R.id.sc);
        shippings.addTextChangedListener(this);
        ll=view.findViewById(R.id.ll);

        profile.setImageBitmap(customer.getPhoto());
        name.setText(customer.getName());
        mail.setText(customer.getEmail());

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshAdapter();

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogProducts dp =new DialogProducts(allProducts);
                dp.setOnItemReceivedListener(new DialogProducts.OnItemReceivedListener() {
                    @Override
                    public void onItemReceived(int idProduct) {
                        products.add(productController.getProductById(idProduct));
                        for(int i=0;i<allProducts.size();i++){
                            if(allProducts.get(i).getId()==idProduct)allProducts.get(i).setStock(allProducts.get(i).getStock()-1);
                        }
                        refreshAdapter();
                        updateTotal();
                    }
                });
                dp.show(fragment.getChildFragmentManager(), null);
            }
        });

        refreshAdapter();


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(products.size()==0) Toast.makeText(getContext(), "No se puede realizar un pago en un ticket sin productos", Toast.LENGTH_SHORT).show();
                else{
                    newSale();

                    ((FragmentActivity)getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new FragmentCustomersAdminView(employee)).addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }

    private void newSale() {
        ArrayList<Product>buyedProducts=new ArrayList<Product>();

        for(Product p:products){
            if(existsProductInArray(p, buyedProducts)){
                for(int i=0;i<buyedProducts.size();i++){
                    if(buyedProducts.get(i).getId()==p.getId()){
                        buyedProducts.get(i).setStock(buyedProducts.get(i).getStock()+1);
                    }
                }
            }else buyedProducts.add(new Product(p.getId(),p.getName(), p.getDescription(),1, p.getCurrent_price(),p.getPhoto()));
        }

        ArrayList<ProductSale>lines=new ArrayList<ProductSale>();
        for(Product p:buyedProducts){
            lines.add(new ProductSale(productController.getProductById(p.getId()),p.getStock(),productController.getProductById(p.getId()).getCurrent_price()));
        }

        double sc=0;
        if(DataChecker.correctDouble(shippings.getText().toString()));

        Sale s=new Sale(1,MyMultipurpose.getSystemDate(),sc,false, employee, customer, lines);
        salesController.addSale(s);

        for(Product product:buyedProducts){
            Product aux=productController.getProductById(product.getId());
            aux.setStock(aux.getStock()-product.getStock());
            productController.updateProduct(aux);
        }

        SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        float money=sh.getFloat("money", 10000000f);
        money+=topay;
        editor.putFloat("money",money);
        editor.commit();

        Toast.makeText(getContext(), "Venta realizada con éxito", Toast.LENGTH_SHORT).show();
    }

    private boolean existsProductInArray(Product product, ArrayList<Product>products){
        for(Product p2:products){
            if(p2.getId()==product.getId())return true;
        }
        return false;
    }


    private void refreshAdapter(){
        la=new ListAdapterProductsInSale(products, this.getContext());

        la.setOnItemDelete(new ListAdapterProductsInSale.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int idProduct) {

                for(int i=0;i<products.size();i++){
                    if(products.get(i).getId()==idProduct){
                        products.remove(i);
                        break;
                    }
                }

                for(int i=0;i<allProducts.size();i++){
                    if(allProducts.get(i).getId()==idProduct)allProducts.get(i).setStock(allProducts.get(i).getStock()+1);
                }
                refreshAdapter();
                updateTotal();
            }
        });

        rv.setAdapter(la);
    }

    private void updateTotal(){
        double t=0;
        for(int i=0;i<products.size();i++){
            t+=products.get(i).getCurrent_price();
        }

        if(DataChecker.correctDouble(shippings.getText().toString()))topay=Double.parseDouble(shippings.getText().toString());
        else topay=t;

        if(DataChecker.correctDouble(shippings.getText().toString()))total.setText("Total: "+ MyMultipurpose.format(topay)+"€");
        else total.setText("Total: "+ MyMultipurpose.format(topay)+"€");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateTotal();
    }
}