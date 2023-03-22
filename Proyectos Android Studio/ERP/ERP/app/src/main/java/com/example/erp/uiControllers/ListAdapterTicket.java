package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SalesController;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterTicket extends RecyclerView.Adapter<ListAdapterTicket.ViewHolder9> {

    private List<ProductSale>lines;
    private SalesController salesController;
    private LayoutInflater inflater;
    private Context context;
    private Sale sale;

    public ListAdapterTicket(Sale sale, Context context){
        this.lines=sale.getLines();
        this.salesController=new SalesController(context);
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.sale=sale;
    }


    @NonNull
    @Override
    public ListAdapterTicket.ViewHolder9 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_lines_sale, null);
        return new ListAdapterTicket.ViewHolder9(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterTicket.ViewHolder9 holder, @SuppressLint("RecyclerView") int position) {
        holder.binData(lines.get(position));

        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(lines.get(position).getAmount() == 1 ){
                    salesController.deleteProductSale(lines.get(position).getProduct().getId(), sale.getId());
                }else{
                    lines.get(position).setAmount(lines.get(position).getAmount()-1);

                    salesController.updateProductSale(lines.get(position), sale.getId());
                }
                listener.onItemClick();
                notifyDataSetChanged();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    public class ViewHolder9 extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView name, amount, price;
        private LinearLayout ll;

        public ViewHolder9(@NonNull View itemView) {
            super(itemView);
            this.photo=itemView.findViewById(R.id.product_photo);
            this.name=itemView.findViewById(R.id.product_name);
            this.price=itemView.findViewById(R.id.product_price);
            this.amount=itemView.findViewById(R.id.product_amount);
            this.ll=itemView.findViewById(R.id.ll_itemTicket);
        }

        void binData(final ProductSale item){
            photo.setImageBitmap(item.getProduct().getPhoto());
            name.setText(item.getProduct().getName());
            price.setText("P.ud: "+ MyMultipurpose.format(item.getIndPrice()));
            amount.setText("X"+item.getAmount());
        }

    }


    public interface OnItemClickListener{
        void onItemClick();
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
