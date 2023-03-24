package com.example.erp.fragmentsAdminView;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Transation;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.ProductController;
import com.example.erp.dbControllers.TransationController;
import com.example.erp.dialogs.TransactionDialog;

public class AccountingFragment extends Fragment implements TransactionDialog.TransactionDialogListener {

    public AccountingFragment() {
        // Required empty public constructor
    }

    private LinearLayout p1, p2;
    private TextView money, stock, values;

    private TransationController transationController;
    private ProductController productController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_accounting, container, false);

        transationController=new TransationController(getContext());
        productController=new ProductController(getContext());

        p1=view.findViewById(R.id.p1);
        p2=view.findViewById(R.id.p2);
        money=view.findViewById(R.id.allMoney);
        stock=view.findViewById(R.id.allStock);
        values=view.findViewById(R.id.allValues);

        calculateNumbers();

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogTransaction(true);
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogTransaction(false);
            }
        });

        return view;
    }

    private void calculateNumbers(){
        SharedPreferences sh = getContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        money.setText("Capital actual: " + MyMultipurpose.format(sh.getFloat("money", 10000000))+" €");

        stock.setText("Cantidad de productos: "+productController.getAllAmountOfProducts());
        values.setText("Valor en productos: "+productController.getValueOfAllProducts()+" €");
    }

    private void openDialogTransaction(boolean positive){
        TransactionDialog transactionDialog=new TransactionDialog(positive);
        transactionDialog.setTargetFragment(this, 0);
        transactionDialog.show(requireActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void passTransaction(Transation transation) {
        SharedPreferences sh = getContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        double m=sh.getFloat("money",10000000);

        if(m + transation.getAmount()<0) Toast.makeText(getContext(), "Transacción imposible(cantidad final negativa)", Toast.LENGTH_SHORT).show();
        else{
            transationController.addTransation(transation);
            editor.putFloat("money", (float) (m+ transation.getAmount()));
            editor.commit();
            money.setText("Capital actual: " + MyMultipurpose.format(sh.getFloat("money", 10000000))+" €");
            Toast.makeText(getContext(), "Transacción realizada con éxito", Toast.LENGTH_SHORT).show();
        }

    }
}