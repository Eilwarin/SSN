package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Department department = new Department();
        Employee employee = new Employee();
        Payroll payroll = new Payroll();
        Path deptPath = Paths.get("departments.txt");
        Path empPath = Paths.get("employee.txt");
        Path payPath = Paths.get("payroll.txt");

//        department.viewSingleRecord(deptPath);
        employee.viewAllEmployees(empPath);
//        employee.viewSingleEmployee(empPath);
//        employee.employeeFileProcessing(employee.createEmployeeRecord(), empPath);
//        payroll.payrollData(payPath, deptPath, empPath);
//        payroll.viewEmployeePayroll(payPath);
//        payroll.viewDepartmentPayroll(payPath);
    }
}