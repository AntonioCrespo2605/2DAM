package com.example.erp.imageFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.erp.R;
import com.example.erp.dialogs.TransactionDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountingFragment extends Fragment {

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_accounting, container, false);

        p1=view.findViewById(R.id.p1);
        p2=view.findViewById(R.id.p2);
        money=view.findViewById(R.id.allMoney);
        stock=view.findViewById(R.id.allStock);
        values=view.findViewById(R.id.allValues);

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

    private void openDialogTransaction(boolean positive){
        TransactionDialog transactionDialog=new TransactionDialog(positive);
        transactionDialog.show(getActivity().getSupportFragmentManager(), "");
    }
}