package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee{
    protected String idNumber;
    protected String firstName;
    protected String lastName;
    protected String position;
    protected float hoursWorked;
    private Scanner input;

    public Employee(){
        input = new Scanner(System.in);
    }

    public Employee(String idNo, String fName, String lName, String deptCode, String pos, float hrsWrk){
        idNumber = idNo;
        firstName = fName;
        lastName = lName;
        position = pos;
        hoursWorked = hrsWrk;
    }

    public List<Employee> createEmployeeRecord(){
        List<Employee> newRecord = new ArrayList<>();

        System.out.println("Enter the details of the Employee Payroll record in the order of {ID Number, First Name, " +
                "Last Name, Department Code, Position, Total Hours Worked}");

        System.out.print("\nEnter detail: ");
        setIdNumber(input.nextLine());
        System.out.print("\nEnter detail: ");
        setFirstName(input.nextLine());
        System.out.print("\nEnter detail: ");
        setLastName(input.nextLine());
        System.out.print("\nEnter detail: ");
        new Department().setDepartmentCode(input.nextLine());
        System.out.print("\nEnter detail: ");
        setPosition(input.nextLine());
        System.out.print("\nEnter detail: ");
        setHoursWorked(input.nextFloat());

        Employee employeeData = new Employee(getIdNumber(), getFirstName(), getLastName(), new Department().getDepartmentCode(), getPosition(), getHoursWorked());
        newRecord.add(employeeData);

        return newRecord;
    }

    public void employeeFileProcessing(List<Employee> record, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("ID No.\tF. Name\tL. Name\tDept. Code\tPosition\tHrs. Worked");
                bufferedWriter.newLine();

                for (Employee employeeData : record){
                    bufferedWriter.write(employeeData.getIdNumber() + "\t" + employeeData.getFirstName() + "\t"
                            + employeeData.getLastName() + "\t" + new Department().getDepartmentCode() + "\t" +
                            employeeData.getPosition() + "\t" + employeeData.getHoursWorked());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }else {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Employee employeeData : record){
                    bufferedWriter.write(employeeData.getIdNumber() + "\t" + employeeData.getFirstName() + "\t"
                            + employeeData.getLastName() + "\t" + new Department().getDepartmentCode() + "\t" +
                            employeeData.getPosition() + "\t" + employeeData.getHoursWorked());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(float hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
