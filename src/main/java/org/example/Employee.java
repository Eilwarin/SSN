package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee extends Department{
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

        System.out.print("\nEnter ID Number: ");
        setIdNumber(input.nextLine());
        System.out.print("\nEnter First Name: ");
        setFirstName(input.nextLine());
        System.out.print("\nEnter Last Name: ");
        setLastName(input.nextLine());
        System.out.print("\nEnter Department Code: ");
        setDepartmentCode(input.nextLine());
        System.out.print("\nEnter Position: ");
        setPosition(input.nextLine());
        System.out.print("\nEnter Total Hours Worked: ");
        setHoursWorked(input.nextFloat());

        Employee employeeData = new Employee(getIdNumber(), getFirstName(), getLastName(), getDepartmentCode(), getPosition(), getHoursWorked());
        newRecord.add(employeeData);

        return newRecord;
    }

    public void viewSingleEmployee(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Employee> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter Employee ID Number: ");
                String index = input.nextLine();
                String[] employeeData;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    employeeData = line.split("\t");

                    if (employeeData.length == 6 && employeeData[0].equals(index)) {
                        String idNo = employeeData[0];
                        String fName = employeeData[1];
                        String lName = employeeData[2];
                        String dpCode = employeeData[3];
                        String position = employeeData[4];
                        float hrsWorked = Float.parseFloat(employeeData[5]);

                        Employee employee = new Employee(idNo, fName, lName, dpCode, position, hrsWorked);
                        data.add(employee);
                    }else {
                        System.out.println("Employee record does not exist.");
                    }
                }
                for (Employee employee :
                        data) {
                    System.out.println(employee);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    employeeFileProcessing(createEmployeeRecord(), path);
                }else {
                    throw new FileNotFoundException("The employees file does not exist.");
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public void viewAllEmployees(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Employee> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] employeeData = line.split("\t");

                    if (employeeData.length == 6) {
                        String idNo = employeeData[0];
                        String fName = employeeData[1];
                        String lName = employeeData[2];
                        String dpCode = employeeData[3];
                        String position = employeeData[4];
                        float hrsWorked = Float.parseFloat(employeeData[5]);

                        Employee employee = new Employee(idNo, fName, lName, dpCode, position, hrsWorked);
                        data.add(employee);
                    }
                }
                for (Employee employee :
                        data) {
                    System.out.println("\n" + employee);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    employeeFileProcessing(createEmployeeRecord(), path);
                }else {
                    throw new FileNotFoundException("The employees file does not exist.");
                }
            }

        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void employeeFileProcessing(List<Employee> record, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("ID No.\tF. Name\tL. Name\tDept. Code\tPosition\tHrs. Worked");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

            for (Employee employeeData : record){
                bufferedWriter.write(employeeData.getIdNumber() + "\t" + employeeData.getFirstName() + "\t"
                        + employeeData.getLastName() + "\t" + getDepartmentCode() + "\t" +
                        employeeData.getPosition() + "\t" + employeeData.getHoursWorked());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();

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
    @Override
    public String toString() {
        return "ID Number" + '\t' + "First Name" + '\t' +
                "Last Name" + '\t' + "Department Code" + '\t' + "Position" + '\t' +
                "Hours Worked\n" + idNumber + '\t' + firstName + '\t' + lastName + '\t' + departmentCode +
                '\t' + position + '\t' + hoursWorked;
    }
}
