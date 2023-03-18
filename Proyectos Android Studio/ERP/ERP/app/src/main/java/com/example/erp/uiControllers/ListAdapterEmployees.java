package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Supplier;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterEmployees extends RecyclerView.Adapter<ListAdapterEmployees.ViewHolder3> {

    private List<Employee> employees;
    private LayoutInflater inflater;

    private char firstLetter=' ';

    public ListAdapterEmployees(List<Employee>employees, Context context){
        this.inflater=LayoutInflater.from(context);
        this.employees=orderEmployees(employees);
    }


    @NonNull
    @Override
    public ListAdapterEmployees.ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_employee, null);
        return new ListAdapterEmployees.ViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterEmployees.ViewHolder3 holder,@SuppressLint("RecyclerView") final int position) {
        holder.binData(employees.get(position));

        holder.ll_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    private List<Employee> orderEmployees(List<Employee>employees){
        Employee aux;
        for(int i=0;i<employees.size();i++){
            for(int j=i;j<employees.size();j++){
                if(employees.get(i).getName().compareTo(employees.get(j).getName())>0){
                    aux=new Employee(employees.get(i));
                    employees.set(i, employees.get(j));
                    employees.set(j,aux);
                }
            }
        }
        ArrayList<Employee>toret=new ArrayList<Employee>();
        toret.add(new Employee());
        toret.addAll(employees);

        for(int i=0;i<toret.size();i++){
            if(toret.get(i).getId()==0){
                toret.remove(i);
                break;
            }
        }

        return toret;
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder{
        private LinearLayout ll_letter, ll_employee;
        private TextView nameEmployee, workStation, letter;
        private RelativeLayout rlEmployee;
        private ImageView profile;

        public ViewHolder3(View itemView) {
            super(itemView);

            ll_letter=itemView.findViewById(R.id.ll_letter3);
            ll_employee=itemView.findViewById(R.id.ll_employee);
            nameEmployee=itemView.findViewById(R.id.employeeName);
            workStation=itemView.findViewById(R.id.employeeWorkstation);
            rlEmployee=itemView.findViewById(R.id.rl_profileEmployee);
            profile=itemView.findViewById(R.id.employeeProfile);
            letter=itemView.findViewById(R.id.letter3);
        }

        void binData(final Employee item){
            if(item.getId()<0){
                ll_letter.setVisibility(View.GONE);
                rlEmployee.setVisibility(View.GONE);
                workStation.setVisibility(View.GONE);
                nameEmployee.setText("Nuevo Trabajador");
            }else{
                if(item.getName().toUpperCase().charAt(0)!=firstLetter){
                    ll_letter.setVisibility(View.VISIBLE);
                    firstLetter=item.getName().toUpperCase().charAt(0);
                    letter.setText(firstLetter+"");
                }

                nameEmployee.setText(item.getName());
                workStation.setText(item.getWorkstation());
                profile.setImageBitmap(item.getPhoto());
            }
        }
    }
}
