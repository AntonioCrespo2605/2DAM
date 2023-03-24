package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataBaseObjects.Supply;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.dbControllers.SupplyController;

import java.util.List;

public class ListAdapterSupplies extends RecyclerView.Adapter<ListAdapterSupplies.ViewHolder4>{
    private Context context;
    private List<Supply> supplies;
    private LayoutInflater inflater;
    private SupplyController supplyController;

    public ListAdapterSupplies(List<Supply>supplies, Context context){
        this.inflater=LayoutInflater.from(context);
        this.supplies=supplies;
        this.context=context;
        this.supplyController=new SupplyController(context);
    }

    @NonNull
    @Override
    public ListAdapterSupplies.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_sale, null);
        return new ListAdapterSupplies.ViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterSupplies.ViewHolder4 holder, @SuppressLint("RecyclerView") int position) {
        holder.binData(supplies.get(position));

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onItemSelected(supplies.get(position).getId());
            }
        });

        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Borrar Suministro")
                        .setMessage("Está a punto de eliminar el suministro.\n¿Está segur@?");

                builder.setPositiveButton("Sí,Bórralo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onItemDelete(supplies.get(position).getId());
                    }
                });

                builder.setNegativeButton("No, déjalo estar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return supplies.size();
    }

    public class ViewHolder4 extends RecyclerView.ViewHolder{
        private ImageView received;
        private TextView idSale, dateSale, totalSale;
        private LinearLayout ll;

        public ViewHolder4(View itemView) {
            super(itemView);

            received=itemView.findViewById(R.id.received);
            dateSale=itemView.findViewById(R.id.dateSale);
            idSale=itemView.findViewById(R.id.idSale);
            totalSale=itemView.findViewById(R.id.totalSale);
            ll=itemView.findViewById(R.id.ll_sale);
        }

        void binData(final Supply supply){
            idSale.setText(supply.getId()+"");
            totalSale.setText("Num Productos:"+MyMultipurpose.getTotalProductsFromSupply(supply));
            dateSale.setText(supply.getDate());

            if(!supply.isState())received.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);
        }
    }

    public interface OnItemDeleteListener{
        void onItemDelete(int idSupply);
    }

    public OnItemDeleteListener listener;

    public void setOnItemDeleteListener(OnItemDeleteListener listener){
        this.listener=listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int idSupply);
    }

    public OnItemSelectedListener listener2;
    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.listener2=listener;
    }

}
