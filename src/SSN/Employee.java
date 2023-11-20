package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Department{
    protected String idNumber;
    protected String firstName;
    protected String lastName;

    public Employee(){}

    public List<Employee> createEmployeeRecord(){
        List<Employee> newRecord = new ArrayList<>();

        newRecord.add(this);

        return newRecord;
    }
    public void viewSingleEmployee(Path path, boolean updateQuery){
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

                    if (fileContent.length == 5 && !fileContent[0].equals(getIdNumber())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    if (fileContent.length == 5 && fileContent[0].equals(getIdNumber())) {
                        setIdNumber(fileContent[0]);
                        setFirstName(fileContent[1]);
                        setLastName(fileContent[2]);
                        setDepartmentName(fileContent[3]);
                        setPosition(fileContent[4]);
                    }
                }
                bufferedReader.close();
            }
            if (updateQuery){
                BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt"));
                writer.write("ID No.\tF. Name\tL. Name\tDept. Name\tPosition");
                writer.newLine();
                writer.write(unedited.toString());
                writer.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }


    public List<String> viewAllEmployees(Path path, boolean updating){
        List<String> employeeIds = new ArrayList<>();
        List<String> departmentEmployees = new ArrayList<>();
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
                        employeeIds.add(fileContent[0]);
                    }

                    if (fileContent.length == 5 && fileContent[0].substring(0, 4).equals(getDepartmentCode())) {
                        departmentEmployees.add(fileContent[0]);
                        setFirstName(fileContent[1]);
                        setLastName(fileContent[2]);
                        setDepartmentName(fileContent[3]);
                        setPosition(fileContent[4]);
                    }
                }
                if (!updating){
                    return departmentEmployees;
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }

        return employeeIds;
    }
    public void employeeFileProcessing(List<Employee> record, Path path, boolean registered, boolean departmentExists){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("ID No.\tF. Name\tL. Name\tDept. Name\tPosition");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            if (!registered && departmentExists){
                this.dataGather(Path.of("departments.txt"));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Employee employeeData : record){
                    bufferedWriter.write(employeeData.getIdNumber() + "\t" + employeeData.getFirstName() + "\t"
                            + employeeData.getLastName() + "\t" + getDepartmentName() + "\t" + getPosition());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                Path positionsFile = Path.of("positions.txt");
                filesProcessing(positionsFile, validation(positionsFile));
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

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] employees = line.split("\t");

                    if (employees.length == 5  && employees[0].equals(getIdNumber())) {
                        employeeRegistered = true;
                    }
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred.");
        }

        return employeeRegistered;
    }

    public boolean dataGather(Path path){
        boolean departmentExists = false;
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
                        departmentExists = true;
                        setDepartmentName(departments[1]);
                    }
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred.\n" + e);
        }
        return departmentExists;
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
