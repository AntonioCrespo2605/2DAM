package com.example.erp.fragments;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountingFragment extends Fragment implements TransactionDialog.TransactionDialogListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountingFragment newInstance(String param1, String param2) {
        AccountingFragment fragment = new AccountingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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