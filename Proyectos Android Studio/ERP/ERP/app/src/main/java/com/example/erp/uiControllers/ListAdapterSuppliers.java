package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.erp.dataBaseObjects.Supplier;
import com.example.erp.dbControllers.SupplierController;
import com.example.erp.dbControllers.SupplyController;
import com.example.erp.fragmentsNewAdminView.NewSupplier;
import com.example.erp.viewsEdit.EmployeesInformation;
import com.example.erp.viewsEdit.SuppliersInformation;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterSuppliers extends RecyclerView.Adapter<ListAdapterSuppliers.ViewHolder2> {

    private int newId;

    private List<Supplier>suppliers;
    private LayoutInflater inflater;
    private SupplyController supplyController;
    private SupplierController supplierController;
    private Context context;
    private Activity activity;

    //it is used as controller for showing the letter(when next supplier has a new first letter)
    private char firstLetter=' ';

    public ListAdapterSuppliers(List<Supplier> suppliers, int newId,Context context, Activity activity){
        this.inflater=LayoutInflater.from(context);
        this.suppliers=orderSuppliers(suppliers);
        this.newId=newId;
        this.supplyController =new SupplyController(context);
        this.supplierController=new SupplierController(context);
        this.context=context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ListAdapterSuppliers.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_supplier, null);
        return new ListAdapterSuppliers.ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterSuppliers.ViewHolder2 holder,@SuppressLint("RecyclerView") final int position) {
        holder.binData(suppliers.get(position));

        holder.ll_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){
                    Toast.makeText(context, "Cargando datos de "+suppliers.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SuppliersInformation.class);
                    intent.putExtra("id", suppliers.get(position).getId());
                    context.startActivity(intent);
                    activity.finish();
                }else{
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new NewSupplier(newId)).addToBackStack(null)
                            .commit();
                }
            }
        });

        holder.ll_supplier.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position!=0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Borrar proveedor")
                            .setMessage("Si borra un proveedor también borrará las compras asociadas.\n¿Está segur@?");

                    builder.setPositiveButton("Sí,Bórralo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            supplierController.deleteSupplier(suppliers.get(position).getId());
                            suppliers.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    builder.setNegativeButton("No, déjalo estar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog=builder.create();
                    dialog.show();

                }
                return true;
            }

        });


    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    private List<Supplier> orderSuppliers(List<Supplier> suppliers){
        Supplier aux;
        for(int i=0;i<suppliers.size();i++){
            for(int j=i;j<suppliers.size();j++){
                if(suppliers.get(i).getName().compareTo(suppliers.get(j).getName())>0){
                    aux=new Supplier(suppliers.get(i));
                    suppliers.set(i, suppliers.get(j));
                    suppliers.set(j,aux);
                }
            }
        }

        ArrayList<Supplier>toret=new ArrayList<Supplier>();
        toret.add(new Supplier());
        toret.addAll(suppliers);
        return toret;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        private LinearLayout ll_letter, ll_supplier;
        private TextView letter, nameSupplier, numberSupplier, stock;
        private RelativeLayout rl_logo;
        private ImageView logo, circle;


        public ViewHolder2(View itemView) {
            super(itemView);
            ll_letter=itemView.findViewById(R.id.ll_letter3);
            ll_supplier=itemView.findViewById(R.id.ll_employee);
            letter=itemView.findViewById(R.id.letter3);
            nameSupplier=itemView.findViewById(R.id.nameSupplier);
            numberSupplier=itemView.findViewById(R.id.phoneSupplier);
            rl_logo=itemView.findViewById(R.id.rl_profileEmployee);
            logo=itemView.findViewById(R.id.logoSupplier);
            stock=itemView.findViewById(R.id.supplies);
            circle=itemView.findViewById(R.id.circleSupplier);

        }

        void binData(final Supplier item){
            if(item.getId()<0){
                ll_letter.setVisibility(View.GONE);
                rl_logo.setVisibility(View.GONE);
                numberSupplier.setVisibility(View.GONE);
                circle.setVisibility(View.GONE);
                stock.setVisibility(View.GONE);
                nameSupplier.setText("Nuevo Proveedor");
            }else{
                if(item.getName().toUpperCase().charAt(0)!=firstLetter){
                    ll_letter.setVisibility(View.VISIBLE);
                    firstLetter=item.getName().toUpperCase().charAt(0);
                    letter.setText(firstLetter+"");
                }

                nameSupplier.setText(item.getName());
                numberSupplier.setText(item.getTel());
                logo.setImageBitmap(item.getLogo());
                int numPendingSupplies= supplyController.getPendingSuppliesOfSupplier(item.getId()).size();
                if(numPendingSupplies==0){
                    circle.setVisibility(View.GONE);
                    stock.setVisibility(View.GONE);
                }
                stock.setText(numPendingSupplies+"");
            }
        }
    }
}
