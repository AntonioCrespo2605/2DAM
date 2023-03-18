package com.example.erp.imageFragments;

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
import android.widget.Toast;

import com.example.erp.R;
import com.example.erp.dbControllers.EmployeeController;
import com.example.erp.uiControllers.ListAdapterEmployees;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EmployeeController employeeController;

    private RecyclerView rv;

    private Spinner spinner;
    private ListAdapterEmployees la;


    public EmployeesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeesFragment newInstance(String param1, String param2) {
        EmployeesFragment fragment = new EmployeesFragment();
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

        la=new ListAdapterEmployees(employeeController.getEmployees(), getContext());
        rv.setAdapter(la);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workStation=parent.getItemAtPosition(position).toString();
                la=new ListAdapterEmployees(employeeController.getEmployeesInWorkStation(workStation), getContext());
                rv.setAdapter(la);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


}