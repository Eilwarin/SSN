package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PositionRates extends Employee{
    protected double positionRegRate;
    protected double positionOtRate;


    public PositionRates(){
    }

    public void viewSingleRates(Path path, boolean updateQuery){
        StringBuilder unedited = new StringBuilder();
        try{
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

                    if (fileContent.length == 5 && !fileContent[1].equals(getPositionId())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    if (fileContent.length == 5 && fileContent[1].equals(getPositionId())) {
                        setDepartmentCode(fileContent[0]);
                        setPositionId(fileContent[1]);
                        setPosition(fileContent[2]);
                        setPositionRegRate(Double.parseDouble(fileContent[3]));
                        setPositionOtRate(Double.parseDouble(fileContent[4]));
                    }
                }
                bufferedReader.close();
            }
            if (updateQuery){
                BufferedWriter writer = new BufferedWriter(new FileWriter("rates.txt"));
                writer.write("Dept. Code\tPosition ID\tPosition\tReg. Rate\tOT. Rate");
                writer.newLine();
                writer.write(unedited.toString());
                writer.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public List<String> viewAllDepartmentRates(Path path, boolean updating){
        List<String> positionIds = new ArrayList<>();
        List<String> departmentPositions = new ArrayList<>();
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

                    if (fileContent.length == 5){
                        positionIds.add(fileContent[1]);
                    }

                    if (fileContent.length == 5 && fileContent[1].substring(0, 4).equals(getDepartmentCode())) {
                        departmentPositions.add(fileContent[1]);
                        setPosition(fileContent[2]);
                        setPositionRegRate(Double.parseDouble(fileContent[3]));
                        setPositionOtRate(Double.parseDouble(fileContent[4]));
                    }
                }
                if (!updating){
                    return departmentPositions;
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }

        return positionIds;
    }
    public void fileProcessing(Path path, boolean registered) {
        try{
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Dept. Code\tPosition ID\tPosition\tReg. Rate\tOT. Rate");
                writer.newLine();
                writer.close();
            }

            if (!registered){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
                writer.write(getDepartmentCode() + "\t" + getPositionId() + "\t" + getPosition() + "\t" + getPositionRegRate() + "\t" +
                        getPositionOtRate());
                writer.newLine();
                writer.close();
            }

        }catch (IOException ex){
            System.out.println("An error has occurred. Operations could not be completed.\n" + ex);
        }
    }

    public boolean registeredRates(Path path){
        boolean registered = false;
        try {
            if(Files.exists(path)){
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    String[] rates = line.split("\t");
                    if (rates.length == 5 && rates[1].equals(getPositionId())){
                        registered = true;
                    }
                }
            }
        }catch (IOException ignored){}

        return registered;
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
