package com.example.erp.uiControllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragmentsNewAdminView.NewEmployee;
import com.example.erp.viewsEdit.EmployeesInformation;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterEmployees extends RecyclerView.Adapter<ListAdapterEmployees.ViewHolder3> {

    private List<Employee> employees;
    private LayoutInflater inflater;

    private int newId;

    private char firstLetter=' ';
    private Context mContext;

    private Activity activity;
    private EmployeeController employeeController;
    private int idAdmin;


    public ListAdapterEmployees(List<Employee>employees,int newId, Context context, Activity activity, int idAdmin){
        this.inflater=LayoutInflater.from(context);
        this.employees=orderEmployees(employees);
        this.newId=newId;
        this.mContext=context;
        this.activity=activity;
        this.employeeController=new EmployeeController(context);
        this.idAdmin=idAdmin;
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
                    Toast.makeText(mContext, "Cargando datos de "+employees.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, EmployeesInformation.class);
                    intent.putExtra("id", employees.get(position).getId());
                    mContext.startActivity(intent);
                    activity.finish();
                }else{
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new NewEmployee(newId)).addToBackStack(null)
                            .commit();

                }
            }
        });

        holder.ll_employee.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(employees.get(position).getId()!=1 && employees.get(position).getId()!=idAdmin){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Borrar empleado")
                            .setMessage("Si borra un empleado también borrará las compras y sus sueldos asociados.\n¿Está segur@?");

                    builder.setPositiveButton("Sí,Bórralo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            employeeController.deleteEmployee(employees.get(position).getId());
                            employees.remove(position);
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

                return true;
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
