package com.example.erp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.erp.R;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.uiControllers.ListAdapterEmployees;

public class EmployeesFragment extends Fragment {

    private EmployeeController employeeController;

    private RecyclerView rv;

    private Spinner spinner;
    private ListAdapterEmployees la;


    public EmployeesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_employees, container, false);

        spinner=view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.workStations, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        employeeController=new EmployeeController(getContext());

        rv=view.findViewById(R.id.recyclerEmployees);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        la=new ListAdapterEmployees(employeeController.getEmployees(), employeeController.newId(), getContext(), getActivity());
        rv.setAdapter(la);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workStation=parent.getItemAtPosition(position).toString();
                la=new ListAdapterEmployees(employeeController.getEmployeesInWorkStation(workStation), employeeController.newId(), getContext(), getActivity());
                rv.setAdapter(la);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


}