package com.example.erp.uiControllersEmployees;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.fragmentsNewAdminView.NewCustomer;
import com.example.erp.uiControllers.ListAdapterCustomer;
import com.example.erp.viewsEdit.CustomersInformation;

import java.util.ArrayList;
import java.util.List;

public class CustomersAdapterEmployee extends RecyclerView.Adapter<CustomersAdapterEmployee.ViewHolder>{

    private List<Customer> customers;
    private LayoutInflater inflater;
    private Context mContext;
    private CustomerController customerController;


    private char firstLetter=' ';

    public CustomersAdapterEmployee(List<Customer>customers, Context context){
        this.inflater=LayoutInflater.from(context);
        this.customers=orderCustomers(customers);
        this.mContext=context;
        this.customerController=new CustomerController(mContext);
    }

    @NonNull
    @Override
    public CustomersAdapterEmployee.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_customer, null);
        return new CustomersAdapterEmployee.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomersAdapterEmployee.ViewHolder holder,@SuppressLint("RecyclerView") final int position) {
        holder.binData(customers.get(position));

        holder.ll_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
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
        return customers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_letter, ll_customer;
        private TextView letter, nameCustomer, emailCustomer;
        private ImageView profile;


        public ViewHolder(View itemView) {
            super(itemView);

            ll_letter=itemView.findViewById(R.id.ll_letter3);
            ll_customer=itemView.findViewById(R.id.ll_employee);
            letter=itemView.findViewById(R.id.letter3);
            nameCustomer=itemView.findViewById(R.id.nameSupplier);
            emailCustomer=itemView.findViewById(R.id.phoneSupplier);
            profile=itemView.findViewById(R.id.logoSupplier);
        }

        void binData(final Customer item){

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
