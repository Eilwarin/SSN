// File: Positions.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon

// Package declaration for the Positions class under the SSN package
package SSN;

// Import statements for necessary Java classes and libraries
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

// Positions class to manage employee positions
public class Positions {
    // Instance variables
    protected String position;
    protected String positionId;

    // Default constructor
    public Positions(){}

    // Method for processing position information and writing to a file
    public void filesProcessing(Path path, boolean registered){
        try{
            // Check if the file exists, if not, create and write the header
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Position ID\tPosition Title");
                writer.newLine();
                writer.close();
            }

            // If the position is not registered, append information to the file
            if (!registered){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
                writer.write(getPositionId() + "\t" + getPosition());
                writer.newLine();
                writer.close();
            }

        }catch (IOException ex){
            System.out.println("An error has occurred. Operations could not be completed.\n" + ex);
        }
    }

    // Method for validating if a position is registered based on its ID
    public boolean validation(Path path){
        boolean positionRegistered = false;

        try{
            // Check if the file exists
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String index = getPositionId();

                // Read each line from the file
                while ((line = bufferedReader.readLine()) != null) {
                    // Skip the header
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] positions = line.split("\t");

                    // Check if the line contains information for the requested position
                    if (positions.length == 2 && positions[0].equals(index)) {
                        positionRegistered = true;
                    }
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred.\n" + e);
        }

        return positionRegistered;
    }

    // Getter and setter methods for position attributes
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
