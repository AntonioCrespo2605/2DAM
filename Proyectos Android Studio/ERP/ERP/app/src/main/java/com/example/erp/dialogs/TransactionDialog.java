package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Transation;
import com.example.erp.dataTransformers.DateCustomized;
import com.example.erp.dbControllers.TransationController;

public class TransactionDialog extends AppCompatDialogFragment {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.transaction_dialog, null);

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
                    TransationController transationController=new TransationController(getContext());
                    transationController.addTransation(new Transation(1, DateCustomized.getSystemDate(),reason.getText().toString(),Double.parseDouble(amount.getText().toString())));
                    Toast.makeText(getContext(), "Transacción realizada con éxito", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        builder.setView(view);

        return builder.create();
    }
}
