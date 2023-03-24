package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DialogMasiveEmail extends DialogFragment {
    private Button button;
    private EditText et;
    private CustomerController customerController;
    
    public DialogMasiveEmail(Context context){
        this.customerController=new CustomerController(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view= LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_global_message,null);
        button=view.findViewById(R.id.send);
        et=view.findViewById(R.id.input);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataChecker.isEmpty(et))Toast.makeText(getContext(), "Rellena el mensaje", Toast.LENGTH_SHORT).show();
                else{
                    for(Customer c:customerController.getCustomers()){
                        Message message=new Message(MyMultipurpose.getSystemDate(), et.getText().toString(), false);
                        customerController.addMessage(message, c.getId());
                    }
                }
                Toast.makeText(getContext(), "Enviado", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
