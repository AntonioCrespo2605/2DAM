package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Customer;
import com.example.erp.dataBaseObjects.Message;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.CustomerController;

public class MessageDialog extends DialogFragment {

    private Message message;
    private Customer customer;
    private boolean isNew;
    private CustomerController customerController;

    public MessageDialog(Message message, Customer customer, Context context){
        isNew=false;
        this.customer=customer;
        this.message=message;
        this.customerController=new CustomerController(context);
    }

    public MessageDialog(Customer customer, Context context){
        isNew=true;
        this.customer=customer;
        this.message=null;
        this.customerController=new CustomerController(context);
    }


    private TextView date;
    private Button create;
    private EditText content;
    private CheckBox checkBox;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());

        View view= LayoutInflater.from(requireActivity()).inflate(R.layout.message_dialog, null);

        date=view.findViewById(R.id.dateNewMail);
        create=view.findViewById(R.id.createMail);
        content=view.findViewById(R.id.etContent);
        checkBox=view.findViewById(R.id.checkBox);

        if(isNew){
            date.setText(MyMultipurpose.getSystemDate());
        }else{
            date.setText(message.getDate());
            content.setText(message.getContent());
            if(message.isReceived())checkBox.setChecked(true);
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataChecker.isEmpty(content)) Toast.makeText(view.getContext(), "Rellene el contenido", Toast.LENGTH_SHORT).show();
                else {
                    boolean received=checkBox.isChecked();

                    if(isNew){
                        customerController.addMessage(new Message(date.getText().toString(), content.getText().toString(), received), customer.getId());
                    }else{
                        customerController.updateMessage(customer.getId(), new Message(date.getText().toString(), content.getText().toString(), received));
                    }
                    mListener.onButtonClick();
                    dismiss();
                }
            }
        });

        builder.setView(view);

        return builder.create();
    }

    private OnDialogButtonClickListener mListener;

    public interface OnDialogButtonClickListener {
        void onButtonClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (OnDialogButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDialogButtonClickListener");
        }
    }
}
