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
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.dialogs.MessageDialog;

import java.util.List;

public class ListAdapterSales extends RecyclerView.Adapter<ListAdapterSales.ViewHolder4> {

    private Context context;
    private List<Sale> sales;
    private LayoutInflater inflater;
    private SalesController salesController;

    public ListAdapterSales(List<Sale>sales, Context context){
        this.inflater=LayoutInflater.from(context);
        this.sales=sales;
        this.context=context;
        this.salesController=new SalesController(context);
    }

    @NonNull
    @Override
    public ListAdapterSales.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_sale, null);
        return new ListAdapterSales.ViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterSales.ViewHolder4 holder, @SuppressLint("RecyclerView") int position) {
        holder.binData(sales.get(position));

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Borrar Compra")
                        .setMessage("Está a punto de eliminar esta compra.\n¿Está segur@?");

                builder.setPositiveButton("Sí,Bórrala", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        salesController.deleteSale(sales.get(position).getId());
                        sales.remove(position);
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

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return sales.size();
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

        void binData(final Sale sale){
            idSale.setText(sale.getId()+"");
            totalSale.setText(MyMultipurpose.format(MyMultipurpose.getTotalFromSale(sale))+" €");
            dateSale.setText(sale.getDate());

            if(!sale.isState())received.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
}
