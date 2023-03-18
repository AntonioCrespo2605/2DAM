package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;

import java.util.ArrayList;
import java.util.List;


public class ListAdapterCustomer extends RecyclerView.Adapter<ListAdapterCustomer.ViewHolder> {

    private List<Customer>customers;
    private LayoutInflater inflater;


    private char firstLetter=' ';

    public ListAdapterCustomer(List<Customer>customers, Context context){
        this.inflater=LayoutInflater.from(context);
        this.customers=orderCustomers(customers);
    }

    @NonNull
    @Override
    public ListAdapterCustomer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_customer, null);
        return new ListAdapterCustomer.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterCustomer.ViewHolder holder,@SuppressLint("RecyclerView") final int position) {
        holder.binData(customers.get(position));

        holder.ll_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }


    //order alphabetically
    private List<Customer> orderCustomers(List<Customer> customers) {
        Customer aux;
        for(int i=0;i<customers.size();i++){
            for(int j=i;j<customers.size();j++){
                if(customers.get(i).getName().compareTo(customers.get(j).getName())>0){
                    aux=new Customer(customers.get(i));
                    customers.set(i, customers.get(j));
                    customers.set(j,aux);
                }
            }
        }

        ArrayList<Customer>toret=new ArrayList<Customer>();
        toret.add(new Customer());
        toret.addAll(customers);
        return toret;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_letter, ll_customer;
        private TextView letter, nameCustomer, emailCustomer;
        private RelativeLayout rl_profile;
        private ImageView profile;


        public ViewHolder(View itemView) {
            super(itemView);

            ll_letter=itemView.findViewById(R.id.ll_letter2);
            ll_customer=itemView.findViewById(R.id.ll_supplier);
            letter=itemView.findViewById(R.id.letter2);
            nameCustomer=itemView.findViewById(R.id.nameSupplier);
            emailCustomer=itemView.findViewById(R.id.phoneSupplier);
            rl_profile=itemView.findViewById(R.id.rl_logo);
            profile=itemView.findViewById(R.id.logoSupplier);
        }

        void binData(final Customer item){
            if(item.getId()<0){
                ll_letter.setVisibility(View.GONE);
                rl_profile.setVisibility(View.GONE);
                emailCustomer.setVisibility(View.GONE);
                nameCustomer.setText("Añadir cliente");
            }else{
                if(item.getName().toUpperCase().charAt(0)!=firstLetter){
                    ll_letter.setVisibility(View.VISIBLE);
                    firstLetter=item.getName().toUpperCase().charAt(0);
                    letter.setText(firstLetter+"");
                }

                nameCustomer.setText(item.getName());
                emailCustomer.setText(item.getEmail());
                profile.setImageBitmap(item.getPhoto());
            }
        }
    }

}

