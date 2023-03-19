package com.example.erp.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Transation;
import com.example.erp.dataTransformers.MyMultipurpose;

public class TransactionDialog extends DialogFragment {

    private Button create;
    private EditText amount, reason;
    private boolean positive;
    private TextView tv;



    public TransactionDialog(boolean positive){
        this.positive=positive;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());


        View view=LayoutInflater.from(requireActivity()).inflate(R.layout.transaction_dialog,null);

        create=view.findViewById(R.id.createTransaction);
        amount=view.findViewById(R.id.inputAmount);
        reason=view.findViewById(R.id.inputReason);
        tv=view.findViewById(R.id.typeTransaction);

        if(!positive)tv.setText("Transaccion: Pérdida");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().equals(""))Toast.makeText(getContext(), "Cantidad incorrecta", Toast.LENGTH_SHORT).show();
                else if(reason.getText().toString().trim().equals(""))Toast.makeText(getContext(), "adjunte un motivo", Toast.LENGTH_SHORT).show();
                else{
                    Transation t;
                    if(positive)t=new Transation(1, MyMultipurpose.getSystemDate(),reason.getText().toString(),Double.parseDouble(amount.getText().toString()));
                    else t=new Transation(1, MyMultipurpose.getSystemDate(),reason.getText().toString(),(-1)*Double.parseDouble(amount.getText().toString()));

                    TransactionDialogListener listener = (TransactionDialogListener)getTargetFragment();
                    listener.passTransaction(t);

                    dismiss();
                }
            }
        });

        builder.setView(view);

        return builder.create();
    }

    public interface TransactionDialogListener{
        void passTransaction(Transation transation);
    }
}
