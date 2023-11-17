package execute;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PositionRates {
    protected String departmentCode;
    protected String departmentPosition;
    protected double positionRegRate;
    protected double positionOtRate;
    protected String positionID;


    public PositionRates(){
    }

    public void fileProcessing(Path path) {
        try{
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Dept. Code\tDept. Position\tReg. Rate\tOT. Rate");
                writer.newLine();
                writer.close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
            writer.write(getDepartmentCode() + "\t" + getDepartmentPosition() + "\t" + getPositionRegRate() + "\t" +
                    getPositionOtRate());
            writer.newLine();
            writer.close();
        }catch (IOException ex){
            System.out.println("An error has occurred. Operations could not be completed.\n" + ex);
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
