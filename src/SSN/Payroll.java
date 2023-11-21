// File: Payroll.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon

// Package declaration for the Payroll class under the SSN package
package SSN;

// Import statements for necessary Java classes and libraries
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Random;

// Payroll class extending EmployeeTax
public class Payroll extends EmployeeTax {
    // Instance variables
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

    // Default constructor
    public Payroll() {}

    // Method to calculate payroll for employees
    public List<Payroll> calculatePay() {
        List<Payroll> newPayroll = new ArrayList<>();
        StringBuilder chequeNumber = new StringBuilder();
        Random seed = new Random();
        double overtime = 0;

        // Check for overtime
        if (getHoursWorked() > 40.0f) {
            overtime = getHoursWorked() - 40.0f;
        }

        // Generate a random cheque number
        for (int ch = 0; ch < 4; ch++) {
            int asciiAlpha = 65 + seed.nextInt(90 - 65 + 1);
            int asciiNumeric = 48 + seed.nextInt(57 - 48 + 1);
            chequeNumber.append((char) asciiAlpha);
            chequeNumber.append((char) asciiNumeric);
        }

        // Calculate various pay components
        setRegularPay((getHoursWorked() - overtime) * getPositionRegRate());
        setOvertimePay(overtime * getPositionOtRate());
        setGrossPay(getRegularPay() + getOvertimePay());
        setNetPay(getGrossPay() - getIncomeTax() - getEduTax() - getNisTax());
        setIncomeTaxable(getGrossPay() > getIncomeThreshold());

        // Calculate taxes and net pay if income is taxable
        if (isIncomeTaxable()) {
            setTaxableIncome(getGrossPay() - getIncomeThreshold());
            setIncomeTax(getTaxableIncome() * getIncomeTaxRate());
            setNisTax(getTaxableIncome() * getNisTaxRate());
            setEduTax(getTaxableIncome() * getEduTaxRate());
            setNetPay(getGrossPay() - getIncomeTax() - getEduTax() - getNisTax());
        }

        // Set processed date, cheque number, and add to the payroll list
        setProcessedDate(LocalDate.now());
        setChequeNumber(chequeNumber.toString());
        newPayroll.add(this);

        return newPayroll;
    }

    // Method for processing payroll and writing to a file
    public void payrollFileProcessing(List<Payroll> payrolls, Path path, boolean ratesRegistered) {
        try {
            // Check if the file exists, if not, create and write the header
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));
                bufferedWriter.write("Date\tCheque No.\tID No.\tF. Name\tL. Name\tDept. Code\tPosition\tHrs. Worked\t" +
                        "Reg. Pay\tOT. Pay\tGross Pay\tNet Pay\tIncome Tax\tNIS Tax\tEducation Tax");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            // If rates are registered, append payroll information to the file
            if (ratesRegistered) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Payroll ignored : payrolls) {
                    bufferedWriter.write(getProcessedDate() + "\t" + getChequeNumber() + "\t" +
                            getIdNumber() + "\t" + getFirstName() + "\t" + getLastName() + "\t" +
                            getDepartmentCode() + "\t" + getPosition() + "\t" + getHoursWorked() +
                            "\t" + getRegularPay() + "\t" + getOvertimePay() + "\t" + getGrossPay() + "\t" + getNetPay()
                            + "\t" + getIncomeTax() + "\t" + getNisTax() + "\t" + getEduTax());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred " + e);
        }
    }

    // Method to view an employee's payroll information from a file
    public boolean viewEmployeePayroll(Path path) {
        boolean isPaid = false;
        try {
            // Check if the file exists
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String[] fileContent;

                // Read each line from the file
                while ((line = bufferedReader.readLine()) != null) {
                    // Skip the header
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    fileContent = line.split("\t");

                    // Check if the line contains information for the requested employee
                    if (fileContent.length == 15 && fileContent[2].equals(getIdNumber())) {
                        isPaid = true;
                        // Set payroll information
                        setProcessedDate(LocalDate.parse(fileContent[0]));
                        setChequeNumber(fileContent[1]);
                        setFirstName(fileContent[3]);
                        setLastName(fileContent[4]);
                        setDepartmentCode(fileContent[5]);
                        setPosition(fileContent[6]);
                        setHoursWorked(Float.parseFloat(fileContent[7]));
                        setRegularPay(Double.parseDouble(fileContent[8]));
                        setOvertimePay(Double.parseDouble(fileContent[9]));
                        setGrossPay(Double.parseDouble(fileContent[10]));
                        setNetPay(Double.parseDouble(fileContent[11]));
                        setIncomeTax(Double.parseDouble(fileContent[12]));
                        setNisTax(Double.parseDouble(fileContent[13]));
                        setEduTax(Double.parseDouble(fileContent[14]));
                    }
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred. " + e);
        }

        return isPaid;
    }

    // Method to retrieve position rates from a file
    public boolean getPositionRates() {
        boolean ratesRegistered = false;
        try {
            // Check if the rates file exists
            if (Files.exists(Path.of("rates.txt"))) {
                BufferedReader reader = new BufferedReader(new FileReader("rates.txt"));
                String line;
                boolean headerSkipped = false;

                // Read each line from the rates file
                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    // Check if the line contains information for the requested employee
                    if (fileContent.length == 5 && fileContent[1].equals(getIdNumber())) {
                        ratesRegistered = true;
                        setPositionRegRate(Double.parseDouble(fileContent[3]));
                        setPositionOtRate(Double.parseDouble(fileContent[4]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
        return ratesRegistered;
    }

    // Method to retrieve tax information from a file
    public boolean getTaxInfo() {
        boolean taxRegistered = false;
        try {
            // Check if the tax info file exists
            if (Files.exists(Path.of("tax_info.txt"))) {
                BufferedReader reader = new BufferedReader(new FileReader("tax_info.txt"));
                String line;
                boolean headerSkipped = false;

                // Read each line from the tax info file
                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    // Check if the line contains information for the requested employee
                    if (fileContent.length == 6 && fileContent[0].equals(getIdNumber())) {
                        taxRegistered = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
        return taxRegistered;
    }

    // Getter and setter methods for utilized attributes

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
