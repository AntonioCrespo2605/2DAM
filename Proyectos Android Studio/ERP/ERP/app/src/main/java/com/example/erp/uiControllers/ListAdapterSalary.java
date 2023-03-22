package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dialogs.SalaryDialog;
import com.example.erp.fragments.SalariesFragment;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterSalary extends RecyclerView.Adapter<ListAdapterSalary.ViewHolder6> {
    private List<Salary>salaries;
    private LayoutInflater layoutInflater;
    private Employee employee;
    private Fragment fragment;
    private Context context;
    private EmployeeController employeeController;

    public ListAdapterSalary(List<Salary>salaries, Employee employee, Context context, Fragment fragment){
        this.salaries=firstOption(salaries);
        this.employee=employee;
        this.layoutInflater=LayoutInflater.from(context);
        this.fragment=fragment;
        this.context=context;
        this.employeeController=new EmployeeController(context);
    }

    private List<Salary>firstOption(List<Salary>salaries){
        ArrayList<Salary> toret=new ArrayList<Salary>();
        toret.add(new Salary(-10,""));
        toret.addAll(salaries);
        return toret;
    }

    @NonNull
    @Override
    public ListAdapterSalary.ViewHolder6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.row_salary, null);
        return new ListAdapterSalary.ViewHolder6(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterSalary.ViewHolder6 holder, @SuppressLint("RecyclerView") int position) {
        holder.binData(salaries.get(position));

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalaryDialog salaryDialog;

                if(position!=0)salaryDialog = new SalaryDialog(salaries.get(position), employee);
                else salaryDialog = new SalaryDialog(employee);

                salaryDialog.show(fragment.getChildFragmentManager(), null);
            }
        });


        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position!=0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Borrar producto")
                            .setMessage("Si borra un producto también borrará las compras asociadas.\n¿Está segur@?");

                    builder.setPositiveButton("Sí,Bórralo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            employeeController.deleteSalary(employee.getId(), salaries.get(position).getDate());
                            salaries.remove(position);
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

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return salaries.size();
    }

    public class ViewHolder6 extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private TextView amount, date;
        public ViewHolder6(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.dateSalary);
            amount=itemView.findViewById(R.id.amountSalary);
            ll=itemView.findViewById(R.id.llSalary);
        }

        void binData(final Salary item){
            if(item.getSalary()<0){
                amount.setVisibility(View.GONE);
                date.setText("Nuevo salario");
                date.setTextSize(20f);
            }else{
                date.setText("Fecha: "+ MyMultipurpose.getDateWithDay(item.getDate()));
                amount.setText("Cantidad: "+item.getSalary()+"€");
            }
        }
    }




}
