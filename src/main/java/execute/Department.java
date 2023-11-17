package execute;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Department extends PositionRates{
    protected String departmentName;

    private Scanner input;

    public Department() {
        input = new Scanner(System.in);
    }

    public Department(String dpCode, String dpName, String dpPos, double regRate, double otRate) {
        departmentCode = dpCode;
        departmentName = dpName;
        departmentPosition = dpPos;
        positionRegRate = regRate;
        positionOtRate = otRate;
    }

    public List<Department> createDepartmentRecord() {
        List<Department> newRecord = new ArrayList<>();

        System.out.println("Enter the details of the Department's record in the order of {Department Code, Department Name, Department Position, Position's Hourly Rate}");

        System.out.print("\nEnter Department code: ");
        setDepartmentCode(input.nextLine());
        System.out.print("\nEnter Department name: ");
        setDepartmentName(input.nextLine());
        System.out.print("\nEnter Department position: ");
        setDepartmentPosition(input.nextLine());
        System.out.print("\nEnter the Position's hourly pay rate: ");
        setPositionRegRate(input.nextDouble());
        setPositionOtRate(getPositionRegRate() * .5);

        newRecord.add(this);

        return newRecord;
    }

//    public void updateRecord(Path path, Path outFile) throws IOException {
//        viewSingleDepartment(path);
//
//        List<Department> updatedRecord = new ArrayList<>();
//
//        System.out.println("Enter the new details of the Department's record in the order of {Department Code, Department Name, Regular Pay Rate, Overtime Pay Rate}");
//
//        System.out.print("\nEnter department code: ");
//        setDepartmentCode(input.nextLine());
//        System.out.print("\nEnter department name: ");
//        setDepartmentName(input.nextLine());
//        System.out.print("\nEnter the department's regular pay rate: ");
//        setRegularRate(input.nextFloat());
//        System.out.print("\nEnter the department's overtime pay rate: ");
//        setOvertimeRate(input.nextFloat());
//
//        Department departmentData = new Department(getDepartmentCode(), getDepartmentName(), getRegularRate(), getOvertimeRate());
//        updatedRecord.add(departmentData);
//
//        File copyFile = new File(outFile.toString());
//        BufferedWriter copyWriter = new BufferedWriter(new FileWriter(outFile.toFile()));
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
//             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile.toFile(), true))) {
//
//            String line;
//            boolean headerSkipped = false;
//
//            while ((line = bufferedReader.readLine()) != null) {
//                if (!headerSkipped) {
//                    bufferedWriter.write(line + System.lineSeparator());
//                    headerSkipped = true;
//                    continue;
//                }
//
//                String[] fileContent = line.split("\t");
//
//                if (fileContent.length >= 1 && fileContent[0].equals(getDepartmentCode())) {
//                    continue;
//                }
//                bufferedWriter.write(line + System.lineSeparator());
//            }
//
//            for (Department department : updatedRecord){
//
//                bufferedWriter.write(department.getDepartmentCode() + "\t" + department.getDepartmentName() + "\t"
//                        + department.getRegularRate() + "\t" + department.getOvertimeRate());
//                bufferedWriter.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.\n" + e);
//        }
//    }
//
//
    public void viewSingleDepartment(Path path){
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

                    if (fileContent.length == 5 && fileContent[0].equals(index)) {
                        String deptCode = fileContent[0];
                        String deptName = fileContent[1];
                        String deptPosition = fileContent[2];
                        double regRate = Double.parseDouble(fileContent[3]);
                        double otRate = Double.parseDouble(fileContent[4]);

                        Department department = new Department(deptCode, deptName, deptPosition, regRate, otRate);
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
                    departmentFileProcessing(createDepartmentRecord(), path);
                }else {
                    throw new FileNotFoundException("The departments file does not exist.");
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public void viewAllDepartments(Path path){
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

                    if (fileContent.length == 5) {
                        String deptCode = fileContent[0];
                        String deptName = fileContent[1];
                        String deptPosition = fileContent[2];
                        double regRate = Double.parseDouble(fileContent[3]);
                        double otRate = Double.parseDouble(fileContent[4]);

                        Department department = new Department(deptCode, deptName, deptPosition, regRate, otRate);
                        data.add(department);
                    }
                }
                bufferedReader.close();
                for (Department department :
                        data) {
                    System.out.println("\n" + department);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    departmentFileProcessing(createDepartmentRecord(), path);
                }else {
                    throw new FileNotFoundException("The departments file does not exist.");
                }
            }

        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void departmentFileProcessing(List<Department> record, Path path){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Dept. Code\tDept. Name\tDept. Position\tHourly Rate\tOvertime Rate");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

            for (Department departmentData : record){

                bufferedWriter.write(departmentData.getDepartmentCode() + "\t" + departmentData.getDepartmentName() + "\t"
                        + departmentData.getDepartmentPosition() + "\t" + departmentData.getPositionRegRate() + "\t" + departmentData.getPositionOtRate());
                bufferedWriter.newLine();
                setDepartmentCode(departmentData.getDepartmentCode());
            }
            bufferedWriter.close();
            fileProcessing(Path.of("rates.txt"));
        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "\nDepartment Code - " + getDepartmentCode() + "\nDepartment Name - " + getDepartmentName() + "\nDepartment Position - " +
                getDepartmentPosition() + "\nRegular Rate - " + getPositionRegRate() + "\nOvertime Rate - " + getPositionOtRate();
    }
}
