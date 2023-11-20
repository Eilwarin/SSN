package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PositionRates extends Employee{
    protected double positionRegRate;
    protected double positionOtRate;


    public PositionRates(){
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

    public boolean registered(Path path){
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
                    if (rates.length == 5 && rates[0].equals(getDepartmentCode())){
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
