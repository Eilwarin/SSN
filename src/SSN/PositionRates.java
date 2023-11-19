package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PositionRates extends Department{
    protected double positionRegRate;
    protected double positionOtRate;
    private final Scanner input;


    public PositionRates(){
        input = new Scanner(System.in);
    }

    public List<PositionRates> rates(Path department){
        List<PositionRates> rateRecord = new ArrayList<>();
        System.out.print("Enter the Department code: ");
        setDepartmentCode(input.nextLine());
        System.out.println("Queried department: " + departmentData(department));

        return rateRecord;
    }
    public void fileProcessing(Path path) {
        try{
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Dept. Code\tPosition\tReg. Rate\tOT. Rate");
                writer.newLine();
                writer.close();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
            writer.write(getDepartmentCode() + "\t" + getPositionId() + "\t" + getPositionRegRate() + "\t" +
                    getPositionOtRate());
            writer.newLine();
            writer.close();
        }catch (IOException ex){
            System.out.println("An error has occurred. Operations could not be completed.\n" + ex);
        }
    }

    public String departmentData(Path department){
        try{
            if (Files.exists(department)){
                BufferedReader reader = new BufferedReader(new FileReader(department.toFile()));

                String lineReader;
                boolean headerSkipped = false;

                while((lineReader = reader.readLine()) != null){
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = lineReader.split("\t");

                    if (fileContent.length == 2 && fileContent[0].equals(getDepartmentCode())){
                        return fileContent[1];
                    }
                }
            }
        }catch (IOException e){
            System.out.println("An error occurred. Operations could not be completed.");
        }
        return null;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public double getPositionRegRate() {
        return positionRegRate;
    }

    public void setPositionRegRate(double positionRegRate) {
        this.positionRegRate = positionRegRate;
    }

    public double getPositionOtRate() {
        return positionOtRate;
    }

    public void setPositionOtRate(double positionOtRate) {
        this.positionOtRate = positionOtRate;
    }
}
