package com.example.erp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.uiControllers.ListAdapterTicket;

public class LinesInSaleFragment extends Fragment{

    private SalesController salesController;
    private Sale sale;
    private RecyclerView rv;
    private ListAdapterTicket la;

    public LinesInSaleFragment() {
        // Required empty public constructor
    }
    public LinesInSaleFragment(int idSale, Context context){
        this.salesController=new SalesController(context);
        this.sale=salesController.getSaleById(idSale);
    }

    private TextView numberTicket, dateTicket, hourTicket, base, shipping_costs, total;
    private Button addLine, ok;
    private CheckBox checkBox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_lines_in_sale, container, false);

        rv=view.findViewById(R.id.recyclerProductList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        la=new ListAdapterTicket(sale, getContext());
        rv.setAdapter(la);

        la.setOnItemClickListener(new ListAdapterTicket.OnItemClickListener() {
            @Override
            public void onItemClick() {
                setVariables();
            }
        });

        numberTicket=view.findViewById(R.id.ticketName);
        dateTicket=view.findViewById(R.id.dateTicket);
        base=view.findViewById(R.id.baseMoneyTicket);
        shipping_costs=view.findViewById(R.id.shippingCostsTicket);
        total=view.findViewById(R.id.totalTicket);
        addLine=view.findViewById(R.id.addToTicket);
        checkBox=view.findViewById(R.id.checkRecived);
        hourTicket=view.findViewById(R.id.hourTicket);
        ok=view.findViewById(R.id.buttonOk);

        numberTicket.setText("Ticket "+sale.getId());
        dateTicket.setText(MyMultipurpose.separateFromDate(sale.getDate(), true));
        hourTicket.setText(MyMultipurpose.separateFromDate(sale.getDate(), true));
        shipping_costs.setText(MyMultipurpose.format(sale.getShipping_costs())+"");

        setVariables();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salesController.updateSale(new Sale(sale.getId(), ));
            }
        });

        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void setVariables(){
        double t=0;
        for(ProductSale ps: sale.getLines()){
            t+=(ps.getAmount()*ps.getIndPrice());
        }

        base.setText("Base: "+MyMultipurpose.format(t)+"€");
        total.setText("");

        if(sale.isState())checkBox.setChecked(true);
    }
}