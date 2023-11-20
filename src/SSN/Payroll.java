package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class Payroll extends PositionRates{
    protected float hoursWorked;
    protected double regularPay;
    protected double overtimePay;
    protected double grossPay;
    protected double netPay;
    protected double incomeTax;
    protected double nisTax;
    protected double eduTax;
    protected LocalDate processedDate;
    protected String chequeNumber;

    public Payroll(){
    }

    public Payroll(LocalDate processedDate, String chqNo, String idNo, String fName, String lName, String deptCode,
                   String deptName, String position, String trn, String nis, double regPay, double otPay, double gross,
                   double incomeTax, double nisTax, double eduTax){
        regularPay = regPay;
        overtimePay = otPay;
        grossPay = gross;
    }

    public List<Payroll> calculatePay() {
        List<Payroll> newPayroll = new ArrayList<>();
        StringBuilder chequeNumber = new StringBuilder();
        Random seed = new Random();
        double overtime = 0;

        if (getHoursWorked() > 40.0f) {
            overtime = getHoursWorked() - 40.0f;
        }
        for (int ch = 0; ch < 4; ch++){
            int asciiAlpha = 65 + seed.nextInt(90 - 65 + 1);
            int asciiNumeric = 48 + seed.nextInt(57 - 48 + 1);

            chequeNumber.append((char) asciiAlpha);
            chequeNumber.append((char) asciiNumeric);
        }

        setRegularPay((getHoursWorked() - overtime));

        setOvertimePay(overtime);

        setGrossPay(getRegularPay() + getOvertimePay());

        setProcessedDate(LocalDate.now());

        setChequeNumber(chequeNumber.toString());

        newPayroll.add(this);

        return newPayroll;
    }


    public void payrollData(Path payroll, Path dept, Path emp){
//        try {
//            System.out.print("Enter the employee's ID Number: ");
//            setIdNumber(input.nextLine());
//
//
//        }catch (IOException e){
//        }
    }

    public void payrollFileProcessing(List<Payroll> payrolls, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Date\tCheque No.\tID No.\tF. Name\tL. Name\tDept. Code\tPosition\tHrs. Worked\t" +
                        "Reg. Pay\tOT. Pay\tGross Pay\tIncome Tax\tNIS\tEducation Tax\tNET PAY");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

            for (Payroll ignored : payrolls){
                bufferedWriter.write(getProcessedDate() + "\t" + getChequeNumber() + "\t" +
                        getIdNumber() + "\t" + getFirstName() + "\t" + getLastName() + "\t" +
                        getDepartmentCode() + "\t" + getPosition() + "\t" + getHoursWorked() +
                        "\t" + getRegularPay() + "\t" + getOvertimePay() + "\t" + getGrossPay() + "\t" + getIncomeTax()
                        + "\t" + getNisTax() + "\t" + getEduTax() + "\t" + getNetPay());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            System.out.println("Operations completed.");

        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    //    public void getPositionRates(){
//        try {
//            if (Files.exists(Path.of("rates.txt"))){
//                BufferedReader reader = new BufferedReader(new FileReader("rates.txt"));
//                String line;
//                boolean headerSkipped = false;
//
//                while((line = reader.readLine()) != null){
//                    if (!headerSkipped){
//                        headerSkipped = true;
//                        continue;
//                    }
//                    String[] fileContent = line.split("\t");
//
//                    if (fileContent == 4 && fileContent[1].equals(getIdNumber().substring(4, 7))){
//
//                    }
//                }
//
//            }
//        }catch (IOException e){
//
//        }
//    }
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

    public float getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(float hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public double getNisTax() {
        return nisTax;
    }

    public void setNisTax(double nisTax) {
        this.nisTax = nisTax;
    }

    public double getEduTax() {
        return eduTax;
    }

    public void setEduTax(double eduTax) {
        this.eduTax = eduTax;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

}
