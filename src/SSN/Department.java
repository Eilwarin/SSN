// File: Department.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon

// Package declaration for the Department class under the SSN package
package SSN;

// Import statements for necessary Java classes and libraries
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Department class extending Positions
public class Department extends Positions {
    // Instance variables specific to department information
    protected String departmentCode;
    protected String departmentName;

    // Default constructor
    public Department() {}

    // Parameterized constructor
    public Department(String dpCode, String dpName) {
        departmentCode = dpCode;
        departmentName = dpName;
    }

    // Method to create a record of department information
    public List<Department> createDepartmentRecord() {
        List<Department> newRecord = new ArrayList<>();

        newRecord.add(this);

        return newRecord;
    }

    // Method to view information for a single department, update file, and check registration
    public void viewSingleDepartment(Path path, boolean updateQuery) {
        StringBuilder unedited = new StringBuilder();
        try {
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

                    if (fileContent.length == 2 && !fileContent[0].equals(getDepartmentCode())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    if (fileContent.length == 2 && fileContent[0].equals(getDepartmentCode())) {
                        setDepartmentName(fileContent[1]);
                    }
                }
                bufferedReader.close();
            }
            if (updateQuery) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("departments.txt"));
                writer.write("Dept. Code\tDept. Name");
                writer.newLine();
                writer.write(unedited.toString());
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred. " + e);
        }
    }

    // Method to view information for all departments and return a list of department codes
    public List<String> viewAllDepartments(Path path) {
        List<String> data = new ArrayList<>();
        try {
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] fileContent = line.split("\t");

                    if (fileContent.length == 2) {
                        data.add(fileContent[0]);
                    }
                    if (fileContent.length == 2 && fileContent[0].equals(getDepartmentCode())) {
                        setDepartmentName(fileContent[1]);
                    }
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred. " + e);
        }
        return data;
    }

    // Method to process department information, write to a file, and check registration
    public void departmentFileProcessing(List<Department> record, Path path, boolean registered, boolean updating) {
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Dept. Code\tDept. Name");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            if (!registered && updating) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Department departmentData : record) {
                    bufferedWriter.write(departmentData.getDepartmentCode() + "\t" + departmentData.getDepartmentName());
                    bufferedWriter.newLine();
                    setDepartmentCode(departmentData.getDepartmentCode());
                }
                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred " + e);
        }
    }

    // Method to check if a department is registered in the file
    public boolean registeredDepartment(Path path) {
        boolean registered = false;
        try {
            if (Files.exists(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;

                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    String[] departments = line.split("\t");
                    if (departments.length == 2 && departments[0].equals(getDepartmentCode())) {
                        registered = true;
                    }
                }
            }
        } catch (IOException ignored) {}

        return registered;
    }

    // Getter and setter methods for department attributes
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
}
