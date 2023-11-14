package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class Payroll extends Employee{
    private double regularPay;
    private double overtimePay;
    private double grossPay;
    private LocalDate processedDate;
    private String chequeNumber;
    private Scanner input;

    public Payroll(){
        input = new Scanner(System.in);
    }

    public Payroll(LocalDate processedDate, String chqNo, String idNo, String fName, String lName, String deptCode, String position, double regPay, double otPay, double gross){
        regularPay = regPay;
        overtimePay = otPay;
        grossPay = gross;
    }

    public List<Payroll> calculatePay() {
        List<Payroll> newPayroll = new ArrayList<>();
        StringBuilder chequeNumber = new StringBuilder();
        Random seed = new Random();
        float overtime = 0;

        if (getHoursWorked() > 40.0f) {
            overtime = getHoursWorked() - 40.0f;
        }
        for (int ch = 0; ch < 4; ch++){
            int asciiAlpha = 65 + seed.nextInt(90 - 65 + 1);
            int asciiNumeric = 48 + seed.nextInt(57 - 48 + 1);

            chequeNumber.append((char) asciiAlpha);
            chequeNumber.append((char) asciiNumeric);
        }

        setRegularPay((getHoursWorked() - overtime) * getRegularRate());

        setOvertimePay(overtime * getOvertimeRate());

        setGrossPay(getRegularPay() + getOvertimePay());

        setProcessedDate(LocalDate.now());

        setChequeNumber(chequeNumber.toString());

        newPayroll.add(this);

        return newPayroll;
    }


    public void payrollData(Path payroll, Path dept, Path emp){
        try{
            if (Files.exists(dept) && Files.exists(emp)){
                BufferedReader departmentReader = new BufferedReader(new FileReader(dept.toFile()));
                BufferedReader employeeReader = new BufferedReader(new FileReader(emp.toFile()));
                List<Department> deptData = new ArrayList<>();
                List<Employee> empData = new ArrayList<>();
                String line;
                boolean deptHeaderSkipped = false;
                boolean empHeaderSkipped = false;

                while ((line = departmentReader.readLine()) != null) {
                    if (!deptHeaderSkipped) {
                        deptHeaderSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 4) {
                        String deptCode = fileContent[0];
                        String deptName = fileContent[1];
                        float regRate = Float.parseFloat(fileContent[2]);
                        float otRate = Float.parseFloat(fileContent[3]);

                        Department department = new Department(deptCode, deptName, regRate, otRate);
                        deptData.add(department);
                    }
                }

            for (Department department :
                    deptData) {
                String deptCode = department.getDepartmentCode();
                setRegularRate(department.getRegularRate());
                setOvertimeRate(department.getOvertimeRate());

                while ((line = employeeReader.readLine()) != null) {
                    if (!empHeaderSkipped){
                        empHeaderSkipped = true;
                        continue;
                    }
                    String[] employeeData = line.split("\t");

                    if (employeeData.length == 6 && employeeData[3].equals(deptCode)) {
                        String idNo = employeeData[0];
                        String fName = employeeData[1];
                        String lName = employeeData[2];
                        String dpCode = employeeData[3];
                        String position = employeeData[4];
                        float hrsWorked = Float.parseFloat(employeeData[5]);

                        Employee employee = new Employee(idNo, fName, lName, dpCode, position, hrsWorked);
                        empData.add(employee);
                    }
                }
                for (Employee employee :
                        empData) {
                    setIdNumber(employee.getIdNumber());
                    setFirstName(employee.getFirstName());
                    setLastName(employee.getLastName());
                    setDepartmentCode(employee.getDepartmentCode());
                    setPosition(employee.getPosition());
                    setHoursWorked(employee.getHoursWorked());
                    setProcessedDate(LocalDate.now());
                    payrollFileProcessing(calculatePay(), payroll);
                }
            }

            }else {
                throw new FileNotFoundException("Operations could not be completed.");
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public void viewEmployeePayroll(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Payroll> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter Employee ID Number: ");
                String index = input.nextLine();
                String[] payrollData;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    payrollData = line.split("\t");

                    if (payrollData.length == 11 && payrollData[2].equals(index)) {
                        LocalDate processedDate = LocalDate.parse(payrollData[0]);
                        String chequeNo = payrollData[1];
                        String idNo = payrollData[2];
                        String fName = payrollData[3];
                        String lName = payrollData[4];
                        String dpCode = payrollData[5];
                        String position = payrollData[6];
                        float hrsWorked = Float.parseFloat(payrollData[7]);
                        double regPay = Double.parseDouble(payrollData[8]);
                        double otPay = Double.parseDouble(payrollData[9]);
                        double grossPay = Double.parseDouble(payrollData[10]);

                        Payroll payroll = new Payroll(processedDate, chequeNo, idNo, fName, lName, dpCode, position, regPay, otPay, grossPay);
                        data.add(payroll);
                    }
                }
                for (Payroll payroll :
                        data) {
                    System.out.println(payroll);
                }
            }else {
                throw new FileNotFoundException("The payroll file does not exist.");
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public void viewDepartmentPayroll(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Payroll> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter Department Code: ");
                String index = input.nextLine();
                String[] payrollData;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    payrollData = line.split("\t");

                    if (payrollData.length == 11 && payrollData[5].equals(index)) {
                        LocalDate processedDate = LocalDate.parse(payrollData[0]);
                        String chequeNo = payrollData[1];
                        String idNo = payrollData[2];
                        String fName = payrollData[3];
                        String lName = payrollData[4];
                        String dpCode = payrollData[5];
                        String position = payrollData[6];
                        float hrsWorked = Float.parseFloat(payrollData[7]);
                        double regPay = Double.parseDouble(payrollData[8]);
                        double otPay = Double.parseDouble(payrollData[9]);
                        double grossPay = Double.parseDouble(payrollData[10]);

                        Payroll payroll = new Payroll(processedDate, chequeNo, idNo, fName, lName, dpCode, position, regPay, otPay, grossPay);
                        data.add(payroll);
                    }
                }
                for (Payroll payroll :
                        data) {
                    System.out.println("\n" + payroll);
                }
            }else {
                throw new FileNotFoundException("The payroll file does not exist.");
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void payrollFileProcessing(List<Payroll> payrolls, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Date\tCheque No.\tID No.\tF. Name\tL. Name\tDept. Code\tPosition\tHrs. Worked\tReg. Pay\tOT. Pay\tGross Pay");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

            for (Payroll ignored : payrolls){
                bufferedWriter.write(getProcessedDate() + "\t" + getChequeNumber() + "\t" +
                        getIdNumber() + "\t" + getFirstName() + "\t" + getLastName() + "\t" +
                        getDepartmentCode() + "\t" + getPosition() + "\t" + getHoursWorked() +
                        "\t" + getRegularPay() + "\t" + getOvertimePay() + "\t" + getGrossPay());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            System.out.println("Operations completed.");

        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    public double getRegularPay() {
        return regularPay;
    }

    public void setRegularPay(double regularPay) {
        this.regularPay = regularPay;
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public LocalDate getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDate processedDate) {
        this.processedDate = processedDate;
    }
    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

//    public String toString() {
//        return "Date" + '\t' + "Cheque Number" + '\t' + "ID Number" + '\t' + "First Name" + '\t' +
//                "Last Name" + '\t' + "Department Code" + '\t' + "Position" + '\t' +
//                "Hours Worked" + '\t' + "Regular Pay" + '\t' + "Overtime Pay" + '\t' + "Gross Pay\n" + getIdNumber() + '\t' + getFirstName() + '\t' + getLastName() + '\t' + getDepartmentCode() +
//                '\t' + getPosition() + '\t' + getHoursWorked() + '\t' + getRegularPay() + '\t' + getOvertimePay() + '\t'
//                + getGrossPay();
//    }
}
