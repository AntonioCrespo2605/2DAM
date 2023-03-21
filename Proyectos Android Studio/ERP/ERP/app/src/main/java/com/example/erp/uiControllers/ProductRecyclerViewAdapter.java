package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.fragmentsnew.NewCustomer;
import com.example.erp.fragmentsnew.NewProduct;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Product> mData;
    private int newId;

    //Constructor
    public ProductRecyclerViewAdapter(Context context, int newId,List<Product>mData){
        this.context=context;
        this.mData=mData;
        this.newId=newId;
    }

    //new View for each product
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.card_view_item_product, parent, false);
        return new MyViewHolder(view);
    }

    //values to each element of the view with the products data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(position==0){
            holder.imgProduct.setVisibility(View.GONE);
            holder.productStock.setVisibility(View.GONE);
            holder.productName.setText("Nuevo producto");
            holder.productName.setTextSize(18f);
            holder.circle.setVisibility(View.GONE);
        }else{
            holder.imgProduct.setImageBitmap(mData.get(position).getPhoto());
            holder.productName.setText(mData.get(position).getName());
            holder.productStock.setText(""+mData.get(position).getStock());
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView,new NewProduct(mData.get(position))).addToBackStack(null)
                            .commit();
                }else{
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView,new NewProduct(newId)).addToBackStack(null)
                            .commit();
                }

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productStock;
        ImageView imgProduct, circle;
        CardView cardView;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);

            productStock = itemView.findViewById(R.id.productStock);
            productName = itemView.findViewById(R.id.productName);
            imgProduct = itemView.findViewById(R.id.productImage);
            cardView = itemView.findViewById(R.id.cardview_id);
            ll = itemView.findViewById(R.id.llP);
            circle = itemView.findViewById(R.id.circle);
        }
    }
}
