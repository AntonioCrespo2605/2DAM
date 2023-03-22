package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dbControllers.CustomerController;
import com.example.erp.dialogs.MessageDialog;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterMail extends RecyclerView.Adapter<ListAdapterMail.ViewHolder7> implements MessageDialog.OnDialogButtonClickListener{

    private List<Message> messages;
    private LayoutInflater inflater;
    private Context context;
    private CustomerController customerController;
    private Customer customer;
    private Fragment fragment;

    public ListAdapterMail(List<Message>messages, Context context, Customer customer, Fragment fragment){
        this.messages=addNewOption(messages);
        this.context=context;
        this.customerController=new CustomerController(context);
        this.customer=customer;
        this.fragment=fragment;
        this.inflater=LayoutInflater.from(context);
    }

    private List<Message> addNewOption(List<Message> messages) {
        ArrayList<Message>toret=new ArrayList<Message>();
        toret.add(new Message("","",true));
        toret.addAll(messages);
        return toret;
    }


    @NonNull
    @Override
    public ListAdapterMail.ViewHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_mail, null);
        return new ListAdapterMail.ViewHolder7(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterMail.ViewHolder7 holder, @SuppressLint("RecyclerView") int position) {
        holder.binData(messages.get(position));

        holder.ll_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog messageDialog;

                if(position!=0)messageDialog= new MessageDialog(messages.get(position),customer, context);
                else messageDialog=new MessageDialog(customer,context);

                messageDialog.show(fragment.getChildFragmentManager(), null);
            }
        });

        holder.ll_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position!=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Borrar mensaje")
                            .setMessage("Esta a punto de eliminar un mensaje.\n¿Está segur@?");

                    builder.setPositiveButton("Sí,Bórralo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            customerController.deleteMessage(customer.getId(), messages.get(position).getDate());
                            messages.remove(position);
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
        return messages.size();
    }

    @Override
    public void onButtonClick() {
        this.messages=customerController.getMessagesFromCustomer(customer.getId());
        notifyDataSetChanged();
    }


    public class ViewHolder7 extends RecyclerView.ViewHolder{
        private LinearLayout ll_message;
        private TextView date;
        private ImageView noRead;

        public ViewHolder7(View itemView) {
            super(itemView);

            itemView.findViewById(R.id.ll_mail);
            date=itemView.findViewById(R.id.mailDate);
            noRead=itemView.findViewById(R.id.mailRead);
            ll_message=itemView.findViewById(R.id.ll_mail);
        }

        void binData(final Message item){
            if(item.getDate().equals("")){
                noRead.setVisibility(View.GONE);
                date.setText("Crear nuevo Mail");
            }else{
                if(item.isReceived())noRead.setVisibility(View.INVISIBLE);
                date.setText(item.getDate());
            }
        }
    }




}
