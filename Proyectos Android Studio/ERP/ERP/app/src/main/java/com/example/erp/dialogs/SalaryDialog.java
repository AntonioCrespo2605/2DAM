package com.example.erp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dataTransformers.DataChecker;
import com.example.erp.dataTransformers.MyMultipurpose;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.fragments.SalariesFragment;

public class SalaryDialog extends DialogFragment {

    private Salary salary;
    private Employee employee;
    private boolean isNew;
    private EmployeeController employeeController;
    private OnRefreshAdapterListener mListener;

    public SalaryDialog(Salary salary, Employee employee, Context context){
        isNew=false;
        this.employee=employee;
        this.salary=salary;
        this.employeeController=new EmployeeController(context);
    }

    public SalaryDialog(Employee empoyee, Context context){
        isNew=true;
        this.employee=empoyee;
        this.salary=new Salary(12.0,"21/03/2023 22:59:00");
        this.employeeController=new EmployeeController(context);
    }


    private TextView title, date, hour;
    private ImageView reload;
    private Button create;
    private EditText amount;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());

        View view= LayoutInflater.from(requireActivity()).inflate(R.layout.salary_dialog, null);

        title=view.findViewById(R.id.titleSalary);
        date=view.findViewById(R.id.dateSalary);
        hour=view.findViewById(R.id.hourNewSalary);
        create=view.findViewById(R.id.saveSalary);
        reload=view.findViewById(R.id.reload);
        amount=view.findViewById(R.id.inputAmountNewSalary);

        if(isNew){
            title.setText("Nuevo salario");
            date.setText(MyMultipurpose.separateFromDate(MyMultipurpose.getSystemDate(),true));
            hour.setText(MyMultipurpose.separateFromDate(MyMultipurpose.getSystemDate(),false));
            amount.setText(employee.getCurrent_salary()+"");
        }else{
            title.setText("Editar salario");
            date.setText(MyMultipurpose.separateFromDate(salary.getDate(),true));
            hour.setText(MyMultipurpose.separateFromDate(salary.getDate(),false));
            amount.setText(salary.getSalary()+"");
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText(MyMultipurpose.separateFromDate(MyMultipurpose.getSystemDate(),true));
                hour.setText(MyMultipurpose.separateFromDate(MyMultipurpose.getSystemDate(),false));
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DataChecker.correctDouble(amount.getText().toString())) Toast.makeText(view.getContext(), "Formato de cantidad incorrecto", Toast.LENGTH_SHORT).show();
                else {
                    if(isNew){
                        employeeController.addSalary(new Salary(Double.parseDouble(amount.getText().toString()), date.getText().toString()+" "+hour.getText().toString() ), employee.getId());
                    }else{
                        employeeController.updateSalary(employee.getId(),
                                salary.getDate(),
                                new Salary(Double.parseDouble(amount.getText().toString()), date.getText().toString()+" "+date.getText().toString() ));
                    }

                    mListener.onRefreshAdapter();
                    dismiss();

                }
            }
        });

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (SalaryDialog.OnRefreshAdapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDialogButtonClickListener");
        }
    }

    public interface OnRefreshAdapterListener {
        void onRefreshAdapter();
    }

}
