// File: EmployeeTax.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon

// Package declaration for the EmployeeTax class under the SSN package
package SSN;

// Import statements for necessary Java classes and libraries
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// EmployeeTax class extending PositionRates
public class EmployeeTax extends PositionRates {
    // Instance variables specific to tax information
    protected String trn;
    protected String nis;
    protected final double eduTaxRate = 0.0225;
    protected final double nisTaxRate = 0.025;
    protected final double incomeTaxRate = 0.25;
    protected final double incomeThreshold = 31250;
    protected double taxableIncome;
    protected boolean incomeTaxable;
    protected double paidIncomeTax;
    protected double paidNisTax;
    protected double paidEduTax;

    // Default constructor
    public EmployeeTax(){}

    // Method to create a record of tax information
    public List<EmployeeTax> createRecord(){
        List<EmployeeTax> newRecord = new ArrayList<>();

        newRecord.add(this);

        return newRecord;
    }

    // Method to view information for a single department, update file, and check registration
    public void viewSingleTax(Path path, boolean updateQuery) {
        StringBuilder unedited = new StringBuilder();
        try {
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String[] fileContent;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    fileContent = line.split("\t");

                    if (fileContent.length == 3 && !fileContent[0].equals(getIdNumber())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    if (fileContent.length == 3 && fileContent[0].substring(0, 4).equals(getDepartmentCode())) {
                        setTrn(fileContent[1]);
                        setNis(fileContent[2]);
                    }
                }
                bufferedReader.close();
            }
            if (updateQuery) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("tax_info.txt"));
                writer.write("ID No.\tTRN\tNIS");
                writer.newLine();
                writer.write(unedited.toString());
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred. " + e);
        }
    }

    public List<String> viewAllTax(Path path, boolean updating){
        List<String> employeeIds = new ArrayList<>();
        List<String> departmentEmployees = new ArrayList<>();
        try{
            if (Files.exists(path)){
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 3){
                        employeeIds.add(fileContent[0]);
                    }

                    if (fileContent.length == 3 && fileContent[0].equals(getIdNumber())) {
                        departmentEmployees.add(fileContent[0]);
                        setTrn(fileContent[1]);
                        setNis(fileContent[2]);
                    }
                }
                if (!updating){
                    return departmentEmployees;
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }

        return employeeIds;
    }

    // Method to manage tax information, write to a file, and check registration
    public void taxInformation(Path path, boolean registered, List<EmployeeTax> record){
        try{
            // Check if the file exists, if not, create and write the header
            if(!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("ID No.\tTRN\tNIS");
                writer.newLine();
                writer.close();
            }

            // If tax information is not registered, append information to the file
            if(!registered){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
                for(EmployeeTax tax : record){
                    writer.write(tax.getIdNumber() + "\t" + tax.getTrn() + "\t" + tax.getNis());
                    writer.newLine();
                }
                writer.close();
            }
        }catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

    // Method to check if tax information is registered for an employee
    public boolean registeredTax(Path path){
        boolean registered = false;
        try {
            // Check if the file exists
            if (Files.exists(path)){
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                // Read each line from the file
                while((line = reader.readLine()) != null){
                    // Skip the header
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    // Check if the line contains information for the requested employee
                    if (fileContent.length == 3 && fileContent[0].equals(getIdNumber())){
                        registered = true;
                    }
                }
            }
        }catch (IOException ignored){}

        return registered;
    }

    // Getter and setter methods for tax information attributes

    public String getTrn() {
        return trn;
    }

    public void setTrn(String trn) {
        this.trn = trn;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getEduTaxRate() {
        return eduTaxRate;
    }

    public double getNisTaxRate() {
        return nisTaxRate;
    }

    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }

    public double getIncomeThreshold() {
        return incomeThreshold;
    }

    public boolean isIncomeTaxable() {
        return incomeTaxable;
    }

    public void setIncomeTaxable(boolean incomeTaxable) {
        this.incomeTaxable = incomeTaxable;
    }

    public double getTaxableIncome() {
        return taxableIncome;
    }

    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double getPaidIncomeTax() {
        return paidIncomeTax;
    }

    public void setPaidIncomeTax(double paidIncomeTax) {
        this.paidIncomeTax = paidIncomeTax;
    }

    public double getPaidNisTax() {
        return paidNisTax;
    }

    public void setPaidNisTax(double paidNisTax) {
        this.paidNisTax = paidNisTax;
    }

    public double getPaidEduTax() {
        return paidEduTax;
    }

    public void setPaidEduTax(double paidEduTax) {
        this.paidEduTax = paidEduTax;
    }
}
