package com.example.erp.uiControllers;

import android.content.Context;
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

import java.util.List;

public class ListAdapterSales extends RecyclerView.Adapter<ListAdapterSales.ViewHolder4> {

    private Context context;
    private List<Sale> sales;
    private LayoutInflater inflater;

    public ListAdapterSales(List<Sale>sales, Context context){
        this.inflater=LayoutInflater.from(context);
        this.sales=sales;
        this.context=context;
    }

    @NonNull
    @Override
    public ListAdapterSales.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_sale, null);
        return new ListAdapterSales.ViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterSales.ViewHolder4 holder, int position) {
        holder.binData(sales.get(position));

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            received.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

        }
    }
}
