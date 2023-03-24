package com.example.erp.uiControllersEmployees;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.ProductController;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterProductsInSale extends RecyclerView.Adapter<ListAdapterProductsInSale.ViewHolder>{

    private List<Product> products;
    private LayoutInflater inflater;
    private Context mContext;
    private ProductController productController;

    private boolean isDialog=false;
    private char firstLetter=' ';

    public ListAdapterProductsInSale(List<Product>products, Context context){
        this.inflater=LayoutInflater.from(context);
        this.products=orderProducts(products);
        this.mContext=context;
        this.productController=new ProductController(mContext);
    }


    public ListAdapterProductsInSale(List<Product>products, Context context, boolean isDialog){
        this.inflater=LayoutInflater.from(context);
        this.products=orderProducts(products);
        this.products=filterStock(this.products);
        this.mContext=context;
        this.productController=new ProductController(mContext);
        this.isDialog=isDialog;
    }

    @NonNull
    @Override
    public ListAdapterProductsInSale.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_product_sale, null);
        return new ListAdapterProductsInSale.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterProductsInSale.ViewHolder holder,@SuppressLint("RecyclerView") final int position) {
        holder.binData(products.get(position));

        if(!isDialog){
            holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemDelete(products.get(position).getId());
                    return true;
                }
            });
        }else{
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemSelected(products.get(position).getId());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    //order alphabetically
    private List<Product> orderProducts(List<Product> products) {
        Product aux;
        for(int i=0;i<products.size();i++){
            for(int j=i;j<products.size();j++){
                if(products.get(i).getName().compareTo(products.get(j).getName())>0){
                    aux=new Product(products.get(i));
                    products.set(i, products.get(j));
                    products.set(j,aux);
                }
            }
        }

        return products;
    }
    private List<Product> filterStock(List<Product> products) {
        ArrayList<Product>toret=new ArrayList<Product>();

        for(Product product:products){
            if(product.getStock()>0)toret.add(product);
        }

        return toret;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private TextView name, price;
        private ImageView photo;


        public ViewHolder(View itemView) {
            super(itemView);

            ll=itemView.findViewById(R.id.ll);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            photo=itemView.findViewById(R.id.productPhoto);
        }

        void binData(final Product item){
            name.setText(item.getName());
            price.setText(MyMultipurpose.format(item.getCurrent_price()));
            photo.setImageBitmap(item.getPhoto());
        }

    }

    public OnItemDeleteListener listener;
    public interface OnItemDeleteListener{
        void onItemDelete(int idProduct);
    }
    public void setOnItemDelete(OnItemDeleteListener item){
        this.listener=item;
    }
    //-----------------------------------
    public OnItemSelectedListener listener2;
    public interface OnItemSelectedListener{
        void onItemSelected(int idProduct);
    }

    public void setOnItemSelected(OnItemSelectedListener item){
        this.listener2=item;
    }



}
