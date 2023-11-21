// File: PositionRates.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon

// Package declaration for the PositionRates class under the SSN package
package SSN;

// Import statements for necessary Java classes and libraries
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// PositionRates class extending Employee
public class PositionRates extends Employee {
    // Instance variables
    protected double positionRegRate;
    protected double positionOtRate;

    // Default constructor
    public PositionRates(){}

    // Method to view and potentially update rates for a single position
    public void viewSingleRates(Path path, boolean updateQuery){
        StringBuilder unedited = new StringBuilder();
        try{
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

                    // Remove the existing position and store unedited content
                    if (fileContent.length == 5 && !fileContent[1].equals(getPositionId())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    // Set attributes if the line contains information for the requested position
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

            // Update the file with unedited content if needed
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

    // Method to view all rates for positions within a department
    public List<String> viewAllDepartmentRates(Path path, boolean updating){
        List<String> positionIds = new ArrayList<>();
        List<String> departmentPositions = new ArrayList<>();
        try{
            // Check if the file exists
            if (Files.exists(path)){
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                // Read each line from the file
                while ((line = bufferedReader.readLine()) != null) {
                    // Skip the header
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    // Collect position IDs
                    if (fileContent.length == 5){
                        positionIds.add(fileContent[1]);
                    }

                    // Collect position information for the requested department
                    if (fileContent.length == 5 && fileContent[1].substring(0, 4).equals(getDepartmentCode())) {
                        departmentPositions.add(fileContent[1]);
                        setPosition(fileContent[2]);
                        setPositionRegRate(Double.parseDouble(fileContent[3]));
                        setPositionOtRate(Double.parseDouble(fileContent[4]));
                    }
                }

                // Return department positions if not updating
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

    // Method for processing position rate information and writing to a file
    public void fileProcessing(Path path, boolean registered) {
        try{
            // Check if the file exists, if not, create and write the header
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Dept. Code\tPosition ID\tPosition\tReg. Rate\tOT. Rate");
                writer.newLine();
                writer.close();
            }

            // If the position is not registered, append information to the file
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

    // Method to check if rates for a position are registered
    public boolean registeredRates(Path path){
        boolean registered = false;
        try {
            // Check if the file exists
            if(Files.exists(path)){
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                // Read each line from the file
                while ((line = reader.readLine()) != null) {
                    // Skip the header
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    // Check if the line contains information for the requested position
                    String[] rates = line.split("\t");
                    if (rates.length == 5 && rates[1].equals(getPositionId())){
                        registered = true;
                    }
                }
            }
        }catch (IOException ignored){}

        return registered;
    }

    // Getter and setter methods for position rate attributes
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
