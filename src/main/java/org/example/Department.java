package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Department {
    protected String departmentCode;
    protected String departmentName;
    protected float regularRate;
    protected float overtimeRate;

    private Scanner input;

    public Department() {
        input = new Scanner(System.in);
    }

    public Department(String dpCode, String dpName, float regRate, float otRate) {
        departmentCode = dpCode;
        departmentName = dpName;
        regularRate = regRate;
        overtimeRate = otRate;
    }

    public List<Department> createRecord() {
        List<Department> newRecord = new ArrayList<>();

        System.out.println("Enter the details of the Department's record in the order of {Department Code, Department Name, Regular Pay Rate, Overtime Pay Rate}");

        System.out.print("\nEnter department code: ");
        setDepartmentCode(input.nextLine());
        System.out.print("\nEnter department name: ");
        setDepartmentName(input.nextLine());
        System.out.print("\nEnter the department's regular pay rate: ");
        setRegularRate(input.nextFloat());
        System.out.print("\nEnter the department's overtime pay rate: ");
        setOvertimeRate(input.nextFloat());

        Department departmentData = new Department(getDepartmentCode(), getDepartmentName(), getRegularRate(), getOvertimeRate());
        newRecord.add(departmentData);

        return newRecord;
    }

    public void updateRecord(Path path, Path outFile) throws IOException {
        viewSingleRecord(path);

        List<Department> updatedRecord = new ArrayList<>();

        System.out.println("Enter the new details of the Department's record in the order of {Department Code, Department Name, Regular Pay Rate, Overtime Pay Rate}");

        System.out.print("\nEnter department code: ");
        setDepartmentCode(input.nextLine());
        System.out.print("\nEnter department name: ");
        setDepartmentName(input.nextLine());
        System.out.print("\nEnter the department's regular pay rate: ");
        setRegularRate(input.nextFloat());
        System.out.print("\nEnter the department's overtime pay rate: ");
        setOvertimeRate(input.nextFloat());

        Department departmentData = new Department(getDepartmentCode(), getDepartmentName(), getRegularRate(), getOvertimeRate());
        updatedRecord.add(departmentData);

        File copyFile = new File(outFile.toString());
        BufferedWriter copyWriter = new BufferedWriter(new FileWriter(outFile.toFile()));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile.toFile(), true))) {

            String line;
            boolean headerSkipped = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (!headerSkipped) {
                    bufferedWriter.write(line + System.lineSeparator());
                    headerSkipped = true;
                    continue;
                }

                String[] fileContent = line.split("\t");

                if (fileContent.length >= 1 && fileContent[0].equals(getDepartmentCode())) {
                    continue;
                }
                bufferedWriter.write(line + System.lineSeparator());
            }

            for (Department department : updatedRecord){

                bufferedWriter.write(department.getDepartmentCode() + "\t" + department.getDepartmentName() + "\t"
                        + department.getRegularRate() + "\t" + department.getOvertimeRate());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.\n" + e);
        }
    }


    public void viewSingleRecord(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Department> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter department code: ");
                String index = input.nextLine();
                String[] fileContent;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    fileContent = line.split("\t");

                    if (fileContent.length == 4 && fileContent[0].equals(index)) {
                        String deptCode = fileContent[0];
                        String deptName = fileContent[1];
                        float regRate = Float.parseFloat(fileContent[2]);
                        float otRate = Float.parseFloat(fileContent[3]);

                        Department department = new Department(deptCode, deptName, regRate, otRate);
                        data.add(department);
                    }
                }
                for (Department department :
                        data) {
                    System.out.println(department);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    fileProcessing(createRecord(), path);
                }else {
                    throw new FileNotFoundException("The departments file does not exist.");
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void viewAllRecords(Path path){
        try{
            if (Files.exists(path)){
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Department> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 4) {
                        String deptCode = fileContent[0];
                        String deptName = fileContent[1];
                        float regRate = Float.parseFloat(fileContent[2]);
                        float otRate = Float.parseFloat(fileContent[3]);

                        Department department = new Department(deptCode, deptName, regRate, otRate);
                        data.add(department);
                    }
                }
                for (Department department :
                        data) {
                    System.out.println("\n" + department);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    fileProcessing(createRecord(), path);
                }else {
                    throw new FileNotFoundException("The departments file does not exist.");
                }
            }

        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void fileProcessing(List<Department> record, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Dept. Code\tDept. Name\tDept. Regular Rate\tDept. Overtime Rate");
                bufferedWriter.newLine();

                for (Department departmentData : record){
                    bufferedWriter.write(departmentData.getDepartmentCode() + "\t" + departmentData.getDepartmentName() + "\t"
                            + departmentData.getRegularRate() + "\t" + departmentData.getOvertimeRate());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }else {

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Department departmentData : record){

                    bufferedWriter.write(departmentData.getDepartmentCode() + "\t" + departmentData.getDepartmentName() + "\t"
                            + departmentData.getRegularRate() + "\t" + departmentData.getOvertimeRate());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public float getRegularRate() {
        return regularRate;
    }

    public void setRegularRate(float regularRate) {
        this.regularRate = regularRate;
    }

    public float getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(float overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    @Override
    public String toString() {
        return "Department Code" + '\t' + "Department Name" + '\t' +
                "Regular Rate" + '\t' +
                "Overtime Rate\n" + departmentCode + '\t' + departmentName + '\t' + regularRate + '\t' + overtimeRate;
    }
}
