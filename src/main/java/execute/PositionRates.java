package execute;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PositionRates extends Positions{
    protected String departmentCode;
    protected String departmentPosition;
    protected double positionRegRate;
    protected double positionOtRate;


    public PositionRates(){
    }

    public void fileProcessing(Path path) {
        try{
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Dept. Code\tPosition ID\tReg. Rate\tOT. Rate");
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

    public void dataGather(Path employees, Path positions){
        try{
            if (Files.exists(employees) && Files.exists(positions)){
                BufferedReader empReader = new BufferedReader(new FileReader(employees.toFile()));
                BufferedReader positionReader = new BufferedReader(new FileReader(positions.toFile()));

                String lineReader;
                boolean headerSkipped = false;

                while((lineReader = empReader.readLine()) != null){
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = lineReader.split("\t");

                    if (fileContent.length == 6){
//                        setDepartmentCode(fileContent[0].substring(0, 4));
                        setPositionId(fileContent[0].substring(4, 7));
                    }
                }

            }
        }catch (IOException e){
            System.out.println("An error occurred. Operations could not be completed.");
        }
    }
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentPosition() {
        return departmentPosition;
    }

    public void setDepartmentPosition(String departmentPosition) {
        this.departmentPosition = departmentPosition;
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
