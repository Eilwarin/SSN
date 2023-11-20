package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeTax extends Payroll{
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


    public EmployeeTax(){
    }

    public EmployeeTax(String trn, String nis, double eduTaxRate, double nisTaxRate, double incomeTaxRate, double incomeThreshold){
        this.trn = trn;
        this.nis = nis;
    }

    public List<EmployeeTax> createRecord(){
        List<EmployeeTax> newRecord = new ArrayList<>();

        setIncomeTaxable(!(getGrossPay() <= getIncomeThreshold()));

        if(isIncomeTaxable()){
            setTaxableIncome(getGrossPay() - getIncomeThreshold());
            setIncomeTax(getTaxableIncome() * getIncomeTaxRate());
            setNisTax(getTaxableIncome() * getNisTaxRate());
            setEduTax(getTaxableIncome() * getEduTaxRate());
            setNetPay(getGrossPay() - getIncomeTax() - getEduTax() - getNisTax());
            setPaidIncomeTax(getPaidIncomeTax() + getIncomeTax());
        }

        newRecord.add(this);

        return newRecord;
    }
    public void taxInformation(Path path, boolean registered, List<EmployeeTax> record){
        try{
            if(!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("ID No.\tTRN\tNIS\tTaxable?\tIncome Tax Paid\tNIS Tax Paid\tEduTax Paid");
                writer.newLine();
                writer.close();
            }
            if(!registered){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
                for(EmployeeTax tax : record){
                    writer.write(tax.getIdNumber() + "\t" + tax.getTrn() + "\t" + tax.getNis() + "\t" + tax.isIncomeTaxable()
                    + "\t" + tax.getPaidIncomeTax() + "\t" + getPaidNisTax() + "\t" + getPaidEduTax());
                    writer.newLine();
                }
                writer.close();
            }
        }catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

    public boolean registered(Path path){
        boolean registered = false;
        try {
            if (Files.exists(path)){
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while((line = reader.readLine()) != null){
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 7 && fileContent[0].equals(getIdNumber())){
                        registered = true;
                    }
                }
            }
        }catch (IOException ignored){}

        return registered;
    }

    public void taxesPaid(Path path, boolean registered){
        try {
            if (registered){
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while((line = reader.readLine()) != null){
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 7 && fileContent[0].equals(getIdNumber())){
                        setPaidIncomeTax(Double.parseDouble(fileContent[4]));
                        setPaidNisTax(Double.parseDouble(fileContent[5]));
                        setPaidEduTax(Double.parseDouble(fileContent[6]));
                    }
                }
            }
        }catch (IOException ignored){}
    }

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
