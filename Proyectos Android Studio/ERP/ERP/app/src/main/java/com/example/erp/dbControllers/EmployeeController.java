package com.example.erp.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erp.dataBaseObjects.Employee;
import com.example.erp.dataBaseObjects.Salary;
import com.example.erp.dataTransformers.ImageCustomized;

import java.util.ArrayList;

public class EmployeeController extends SQLiteOpenHelper {
    private static final String DB_NAME="dinosDB.sqlite";
    private static final int DB_VERSION=1;
    private static final String EMPLOYEE_TABLE="employee";
    private static final String SALARY_TABLE="salary";
    private static final String SALE_TABLE="sale";

    private ArrayList<Employee>employees;

    private static ImageCustomized ic;


    /************************************************************************/
    //Constructor
    public EmployeeController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readEmployees();
    }

    /************************************************************************/
    //onCreate and onUpgrade
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table employee
        String queryTable="CREATE TABLE IF NOT EXISTS "+EMPLOYEE_TABLE+" ("
                +"id INTEGER PRIMARY KEY, "
                +"dni TEXT NOT NULL,"
                +"name TEXT NOT NULL, "
                +"tel TEXT NOT NULL,"
                +"workstation TEXT NOT NULL,"
                +"bank_number TEXT NOT NULL, "
                +"current_salary TEXT NOT NULL,"
                +"photo BLOB,"
                +"password TEXT NOT NULL);";

        db.execSQL(queryTable);

        //Create table salary
        queryTable="CREATE TABLE IF NOT EXISTS "+SALARY_TABLE+" ("
                +"date TEXT NOT NULL, "
                +"id_employee INTEGER NOT NULL REFERENCES "+EMPLOYEE_TABLE+"(id) ON DELETE CASCADE ON UPDATE CASCADE, "//
                +"salary TEXT NOT NULL, "
                +"PRIMARY KEY(date, id_employee));";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SALARY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);

        onCreate(db);
    }

    /************************************************************************/
    //reads all the employees from database
    private void readEmployees(){
        this.employees=new ArrayList<Employee>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+EMPLOYEE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                Employee employee=new Employee(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), Double.parseDouble(cursor.getString(6)),ic.fromBlobToBitmap(cursor.getBlob(7)),cursor.getString(8));

                cursor2=db.rawQuery("SELECT * FROM "+SALARY_TABLE+" WHERE id_employee="+cursor.getInt(0), null);
                ArrayList<Salary>salaries=new ArrayList<Salary>();
                if(cursor2.moveToFirst()){
                    do{
                        salaries.add(new Salary(Double.parseDouble(cursor2.getString(2)),cursor2.getString(0)));
                    }while (cursor2.moveToNext());
                }
                employee.setSalaries(salaries);
                employees.add(employee);

            }while(cursor.moveToNext());
        }
    }

    /************************************************************************/
    //adding methods
    public void addEmployee(Employee employee){
        int id=1;
        while(existsEmployee(employee.getId())){
            employee.setId(id);
            id++;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel",employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number", employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary()+"");
        values.put("photo", ic.fromBitmapToBlob(employee.getPhoto()));
        values.put("password",employee.getPassword());

        db.insert(EMPLOYEE_TABLE, null, values);
        db.close();

        //salaries
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<employee.getSalaries().size();i++){
            values2.put("date",employee.getSalaries().get(i).getDate());
            values2.put("id_employee", employee.getId());
            values2.put("salary",employee.getSalaries().get(i).getSalary()+"");
            db2.insert(SALARY_TABLE, null, values2);
        }
        db2.close();
        readEmployees();
    }

    public void addSalary(Salary salary, int idEmployee){
        if(!existsSalary(salary.getDate(), idEmployee)) {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("date", salary.getDate());
            values.put("id_employee", idEmployee);
            values.put("salary", salary.getSalary());

            db.insert(SALARY_TABLE, null, values);
            db.close();
            readEmployees();
        }
    }

    /************************************************************************/
    //delete methods
    public void deleteEmployee(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EMPLOYEE_TABLE, "id="+id, null);

        readEmployees();

    }

    public void deleteSalary(int id_employee, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SALARY_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+SALARY_TABLE+" WHERE id_employee="+id_employee+" AND date="+date+")");
        db.close();

        readEmployees();
    }

    /************************************************************************/
    //update methods
    public void updateEmployee(Employee employee){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel", employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number",employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary());
        values.put("photo",ic.fromBitmapToBlob(employee.getPhoto()));
        values.put("password", employee.getPassword());

        db.update(EMPLOYEE_TABLE, values, "id="+employee.getId(), null);
        db.close();

        readEmployees();
    }

    private void updateSalary(int id_employee, Salary salary){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_employee", id_employee);
        values.put("date", salary.getDate());
        values.put("salary", salary.getSalary());

        db.update(SALARY_TABLE, values, "id_employee="+id_employee+" AND date="+salary.getDate(),null);
        db.close();

        readEmployees();
    }

    /************************************************************************/
    //information and filters about employees

    public boolean existsEmployee(int id){
        for(Employee employee:employees){
            if(employee.getId()==id)return true;
        }
        return false;
    }

    public Employee getEmployeeById(int id){
        for(Employee employee:employees){
            if(employee.getId()==id)return employee;
        }
        return null;
    }

    public boolean existsSalary(String date, int id_employee){
        for(Salary salary:getEmployeeById(id_employee).getSalaries()){
            if(salary.getDate()==date)return true;
        }
        return false;
    }
}
