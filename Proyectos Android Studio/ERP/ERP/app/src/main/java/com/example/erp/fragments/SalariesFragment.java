package com.example.erp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.dialogs.SalaryDialog;
import com.example.erp.uiControllers.ListAdapterSalary;

import java.util.ArrayList;
import java.util.List;

public class SalariesFragment extends Fragment implements SalaryDialog.OnRefreshAdapterListener {

    private ListAdapterSalary la;
    private RecyclerView rv;
    private int id_employee;
    private EmployeeController employeeController;

    public SalariesFragment() {
        // Required empty public constructor
    }

    public SalariesFragment(int id_employee, Context context){
        this.id_employee=id_employee;
        this.employeeController=new EmployeeController(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salaries, container, false);

        rv=view.findViewById(R.id.recyclerSalary);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        la=new ListAdapterSalary(employeeController.getSalariesFromEmployee(id_employee), employeeController.getEmployeeById(id_employee), getActivity(), this);
        rv.setAdapter(la);

        return view;
    }

    @Override
    public void onRefreshAdapter() {
        la=new ListAdapterSalary(employeeController.getSalariesFromEmployee(id_employee), employeeController.getEmployeeById(id_employee), getActivity(), this);
        rv.setAdapter(la);
    }
}