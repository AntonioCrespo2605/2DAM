package com.example.erp.fragmentsAdminView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Product;
import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dbControllers.SalesController;
import com.example.erp.dialogs.AddProductToSaleDialog;
import com.example.erp.uiControllers.ListAdapterTicket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LinesInSaleFragment extends Fragment{

    private SalesController salesController;
    private EmployeeController employeeController;
    private Sale sale;
    private RecyclerView rv;
    private ListAdapterTicket la;
    private Context context;

    public LinesInSaleFragment() {
        // Required empty public constructor
    }
    public LinesInSaleFragment(int idSale, Context context){
        this.salesController=new SalesController(context);
        this.sale=salesController.getSaleById(idSale);
        this.employeeController=new EmployeeController(context);
        this.context=context;
    }

    private TextView numberTicket, dateTicket, hourTicket, base, shipping_costs, total;
    private Button addLine, ok;
    private CheckBox checkBox;
    private ImageView seller;
    private Spinner spinner;
    private Fragment fragment;


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


        fragment=this;

        numberTicket=view.findViewById(R.id.ticketName);
        dateTicket=view.findViewById(R.id.dateTicket);
        base=view.findViewById(R.id.baseMoneyTicket);
        shipping_costs=view.findViewById(R.id.shippingCostsTicket);
        total=view.findViewById(R.id.totalTicket);
        addLine=view.findViewById(R.id.addToTicket);
        checkBox=view.findViewById(R.id.checkRecived);
        hourTicket=view.findViewById(R.id.hourTicket);
        ok=view.findViewById(R.id.buttonOk);
        seller=view.findViewById(R.id.sellerImageTikcket);
        spinner=view.findViewById(R.id.spinnerSeller);

        numberTicket.setText("Ticket "+sale.getId());
        dateTicket.setText(MyMultipurpose.separateFromDate(sale.getDate(), true));
        hourTicket.setText(MyMultipurpose.separateFromDate(sale.getDate(), false));
        shipping_costs.setText(MyMultipurpose.format(sale.getShipping_costs())+"");

        setVariables();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DataChecker.correctDouble(MyMultipurpose.deformat(shipping_costs.getText().toString()))) Toast.makeText(context, "El formato de los gastos de envío es incorrecto", Toast.LENGTH_SHORT).show();
                else if(DataChecker.correctDouble(shipping_costs.getText().toString())){
                    boolean received=false;
                    if(checkBox.isChecked())received=true;
                    salesController.updateSale(new Sale(sale.getId(),
                            dateTicket.getText().toString()+" "+hourTicket.getText().toString(),
                            Double.parseDouble(shipping_costs.getText().toString()),
                            received,
                            employeeController.getEmployeeById(Integer.parseInt(spinner.getSelectedItem().toString())),
                            sale.getBuyer()));

                    returnToLastFragment();

                }else Toast.makeText(getContext(), "Rellena los gastos de envío", Toast.LENGTH_SHORT).show();
            }
        });

        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductToSaleDialog aptsd=new AddProductToSaleDialog(getContext());
                aptsd.setTargetFragment(fragment, 0);
                aptsd.setOnProductCreatedListener(new AddProductToSaleDialog.OnProductCreatedListener() {
                    @Override
                    public void onProductCreated(Product product, int amount, double price) {
                        updateProductsBuyed(product, amount, price);
                        setVariables();
                    }
                });
                aptsd.show(requireActivity().getSupportFragmentManager(), null);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee employee=employeeController.getEmployeeById(Integer.parseInt(spinner.getSelectedItem().toString()));
                Toast.makeText(getContext(), employee.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        hourTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker();
            }
        });

        dateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                int style= DatePickerDialog.THEME_HOLO_DARK;

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),style ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dAux=dayOfMonth+"";
                        String mAux=(month+1)+"";
                        if(dayOfMonth<10)dAux="0"+dayOfMonth;
                        if(month<9)mAux="0"+(month+1);
                        dateTicket.setText(dAux+"/"+mAux+"/"+year+"");
                    }
                }, day, month, year);

                datePickerDialog.show();
            }
        });

        return view;
    }

    private void returnToLastFragment() {

    }

    private void updateProductsBuyed(Product product, int amount, double price) {

        if(salesController.existsProductInSale(product.getId(), sale.getId())){
            ArrayList<ProductSale>productSales=salesController.getSaleById(sale.getId()).getLines();
            ProductSale ps2=productSales.get(0);

            for(ProductSale productSale:productSales){
                if(productSale.getProduct().getId()==product.getId()){
                    ps2=productSale;
                    break;
                }
            }

            ps2.setAmount(ps2.getAmount()+amount);
            ps2.setIndPrice(price);
            salesController.updateProductSale(ps2, sale.getId());
        }else salesController.addProductInSale(new ProductSale(product, amount, price), sale.getId());

        salesController=new SalesController(context);
        sale=salesController.getSaleById(sale.getId());

        refreshAllAdapter();
    }

    private void refreshAllAdapter() {

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
    }

    private void setVariables(){
        double t=0;
        for(ProductSale ps: sale.getLines()){
            t+=(ps.getAmount()*ps.getIndPrice());
        }

        base.setText("Base: "+MyMultipurpose.format(t)+"€");

        if(DataChecker.correctDouble(MyMultipurpose.deformat(shipping_costs.getText().toString()))){
            total.setText("Total: "+MyMultipurpose.format(t+Double.parseDouble(MyMultipurpose.deformat(shipping_costs.getText().toString())))+"€");
        }else{
            total.setText("Total: "+MyMultipurpose.format(t)+"€");
        }

        if(sale.isState())checkBox.setChecked(true);

        List<String> options = new ArrayList<>();

        for(Employee employee:employeeController.getEmployees()){
            options.add(employee.getId()+"");
        }

        int pos=0;
        for(int i=0;i<employeeController.getEmployees().size();i++){
            if(employeeController.getEmployees().get(i).getId()==sale.getSeller().getId()){
                pos=1;
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item3, options);
        adapter.setDropDownViewResource(R.layout.spinner_item3);
        spinner.setAdapter(adapter);
        spinner.setSelection(pos);
    }

    private int h, m;
    private void popTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h=hourOfDay;
                m=minute;

                hourTicket.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m)+":00");

            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), style, onTimeSetListener, h, m, true);
        timePickerDialog.setTitle("Hora");
        timePickerDialog.show();
    }
}