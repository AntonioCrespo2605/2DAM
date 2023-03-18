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

public class EmployeeController{

    private DBHelper dbHelper;

    private ArrayList<Employee>employees;

    /************************************************************************/
    //Constructor
    public EmployeeController(Context context){
        dbHelper=new DBHelper(context);
        readEmployees();
    }


    /************************************************************************/
    //reads all the employees from database
    private void readEmployees(){
        this.employees=new ArrayList<Employee>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DBHelper.EMPLOYEE_TABLE, null);
        Cursor cursor2;

        if(cursor.moveToFirst()){
            do{
                Employee employee=new Employee(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), Double.parseDouble(cursor.getString(6)),ImageCustomized.fromBlobToBitmap(cursor.getBlob(7)),cursor.getString(8));

                cursor2=db.rawQuery("SELECT * FROM "+DBHelper.SALARY_TABLE+" WHERE id_employee="+cursor.getInt(0), null);
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

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel",employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number", employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary()+"");
        values.put("photo", ImageCustomized.fromBitmapToBlob(employee.getPhoto()));
        values.put("password",employee.getPassword());

        db.insert(DBHelper.EMPLOYEE_TABLE, null, values);
        db.close();

        //salaries
        SQLiteDatabase db2=dbHelper.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<employee.getSalaries().size();i++){
            values2.put("date",employee.getSalaries().get(i).getDate());
            values2.put("id_employee", employee.getId());
            values2.put("salary",employee.getSalaries().get(i).getSalary()+"");
            db2.insert(DBHelper.SALARY_TABLE, null, values2);
        }
        db2.close();
        readEmployees();
    }

    public void addSalary(Salary salary, int idEmployee){
        if(!existsSalary(salary.getDate(), idEmployee)) {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values =new ContentValues();

            values.put("date", salary.getDate());
            values.put("id_employee", idEmployee);
            values.put("salary", salary.getSalary());

            db.insert(DBHelper.SALARY_TABLE, null, values);
            db.close();
            readEmployees();
        }
    }

    /************************************************************************/
    //delete methods
    public void deleteEmployee(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.EMPLOYEE_TABLE, "id="+id, null);

        readEmployees();

    }

    public void deleteSalary(int id_employee, String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+DBHelper.SALARY_TABLE+" WHERE EXISTS" +
                "(SELECT * FROM "+DBHelper.SALARY_TABLE+" WHERE id_employee="+id_employee+" AND date="+date+")");
        db.close();

        readEmployees();
    }

    /************************************************************************/
    //update methods
    public void updateEmployee(Employee employee){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", employee.getId());
        values.put("dni", employee.getDni());
        values.put("name",employee.getName());
        values.put("tel", employee.getTel());
        values.put("workstation", employee.getWorkstation());
        values.put("bank_number",employee.getBank_number());
        values.put("current_salary", employee.getCurrent_salary());
        values.put("photo",ImageCustomized.fromBitmapToBlob(employee.getPhoto()));
        values.put("password", employee.getPassword());

        db.update(DBHelper.EMPLOYEE_TABLE, values, "id="+employee.getId(), null);
        db.close();

        readEmployees();
    }

    private void updateSalary(int id_employee, Salary salary){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_employee", id_employee);
        values.put("date", salary.getDate());
        values.put("salary", salary.getSalary());

        db.update(DBHelper.SALARY_TABLE, values, "id_employee="+id_employee+" AND date="+salary.getDate(),null);
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

    /************************************************************************/
    //getters && setters

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
}
