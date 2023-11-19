package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Employee extends Department{
    protected String idNumber;
    protected String firstName;
    protected String lastName;
    private Scanner input;

    public Employee(){
        input = new Scanner(System.in);
    }

    public Employee(String idNo, String fName, String lName, String deptCode, String pos){
        idNumber = idNo;
        firstName = fName;
        lastName = lName;
        departmentCode = deptCode;
        position = pos;
    }

    public List<Employee> createEmployeeRecord(){
        List<Employee> newRecord = new ArrayList<>();

        newRecord.add(this);

        return newRecord;
    }
    public List<Employee> updateEmployeeRecord() {
        List<Employee> updatedRecords = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("employees.txt"));
            String line;
            boolean headerSkipped = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] employeeData = line.split("\t");

                if (employeeData.length == 5 && employeeData[0].equals(getIdNumber())) {

                    // Create an updated Employee object
                    Employee updatedEmployee = new Employee(getIdNumber(), getFirstName(), getLastName(), getDepartmentCode(), getPosition());
                    updatedRecords.add(updatedEmployee);
                    System.out.println("Employee record updated successfully!");
                } else {
                    // If not the record to be updated, add the unchanged record to the list
                    Employee unchangedEmployee = new Employee(employeeData[0], employeeData[1], employeeData[2], employeeData[3], employeeData[4]);
                    updatedRecords.add(unchangedEmployee);
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("An error has occurred: " + e);
        }

        return updatedRecords;
    }

    public void viewSingleEmployee(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Employee> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter Employee ID Number: ");
                setIdNumber(input.nextLine());
                String[] employeeData;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    employeeData = line.split("\t");

                    if (employeeData.length == 5 && employeeData[0].equals(getIdNumber())) {
                        String idNo = employeeData[0];
                        String fName = employeeData[1];
                        String lName = employeeData[2];
                        String dpName = employeeData[3];
                        String position = employeeData[4];

                        Employee employee = new Employee(idNo, fName, lName, dpName, position);
                        data.add(employee);
                    }else {
                        System.out.println("Employee record does not exist.");
                    }
                }
                for (Employee employee :
                        data) {
                    System.out.println(employee);
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    employeeFileProcessing(createEmployeeRecord(), path, validation(path));
                }else {
                    throw new FileNotFoundException("The employees file does not exist.");
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }

    public List<String> employeeIds(){
        List<String> employeeIds = new ArrayList<>();
        try {
            if (Files.exists(Path.of("employees.txt"))){
                BufferedReader reader = new BufferedReader(new FileReader(Path.of("employees.txt").toFile()));
                String line;
                boolean headerSkipped = false;

                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 5){
                        employeeIds.add(fileContent[0]);
                    }
                }
            }
        }catch (IOException e){}
        return employeeIds;
    }
    public void viewAllEmployees(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                List<Employee> data = new ArrayList<>();
                String line;
                boolean headerSkipped = false;
                System.out.print("Enter Department Code: ");
                String index = input.nextLine();

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] employeeData = line.split("\t");

                    if (employeeData.length == 6 && employeeData[3].equals(index)) {
                        String idNo = employeeData[0];
                        String fName = employeeData[1];
                        String lName = employeeData[2];
                        String dpCode = employeeData[3];
                        String position = employeeData[4];

                        Employee employee = new Employee(idNo, fName, lName, dpCode, position);
                        data.add(employee);
                    }
                }
                for (Employee employee :
                        data) {
                    System.out.println("\n" + employee.toString());
                }
            }else {
                System.out.print("\nThere are no available records. Would you like to create a new record? [y/n]: ");
                if (input.nextLine().equals("y")){
                    employeeFileProcessing(createEmployeeRecord(), path, validation(path));
                }else {
                    throw new FileNotFoundException("The employees file does not exist.");
                }
            }

        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public void employeeFileProcessing(List<Employee> record, Path path, boolean registered){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("ID No.\tF. Name\tL. Name\tDept. Name\tPosition");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            if (!registered){
                this.dataGather(Path.of("departments.txt"));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Employee employeeData : record){
                    bufferedWriter.write(employeeData.getIdNumber() + "\t" + employeeData.getFirstName() + "\t"
                            + employeeData.getLastName() + "\t" + getDepartmentName() + "\t" + getPosition());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                filesProcessing(Path.of("positions.txt"), validation(Path.of("positions.txt")));
            }

        }catch (IOException e){
            System.out.println("An error occurred " + e);
        }
    }

    public boolean validation(Path path){
        boolean employeeRegistered = false;

        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String index = getIdNumber();

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] employees = line.split("\t");

                    if (employees.length == 5  && employees[0].equals(index)) {
                        employeeRegistered = true;
                    }
                }
                bufferedReader.close();
            }else {throw new FileNotFoundException("Initial record created.");}
        }catch (IOException e){
        }

        return employeeRegistered;
    }

    public void dataGather(Path path){
        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String index = getDepartmentCode();

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] departments = line.split("\t");

                    if (departments.length == 2  && departments[0].equals(index)) {
                        setDepartmentName(departments[1]);
                    }
                }
                bufferedReader.close();
            }else {throw new FileNotFoundException("The employees file does not exist.");}
        }catch (IOException e){
            System.out.println("An error has occurred.\n" + e);
        }
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
