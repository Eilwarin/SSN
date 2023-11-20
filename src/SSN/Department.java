package SSN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Department extends Positions{
    protected String departmentCode;
    protected String departmentName;


    public Department() {}

    public Department(String dpCode, String dpName) {
        departmentCode = dpCode;
        departmentName = dpName;
    }

    public List<Department> createDepartmentRecord() {
        List<Department> newRecord = new ArrayList<>();

        newRecord.add(this);

        return newRecord;
    }
    public void viewSingleDepartment(Path path, boolean updateQuery){
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

                    if (fileContent.length == 2 && !fileContent[0].equals(getDepartmentCode())) {
                        unedited.append(line).append(System.lineSeparator());
                    }

                    if (fileContent.length == 2 && fileContent[0].equals(getDepartmentCode())) {
                        setDepartmentName(fileContent[1]);
                    }
                }
                bufferedReader.close();
            }
            if (updateQuery){
                BufferedWriter writer = new BufferedWriter(new FileWriter("departments.txt"));
                writer.write("Dept. Code\tDept. Name");
                writer.newLine();
                writer.write(unedited.toString());
                writer.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
    }
    public List<String> viewAllDepartments(Path path){
        List<String> data = new ArrayList<>();
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

                    if (fileContent.length == 2) {
                        data.add(fileContent[0]);
                    }
                    if (fileContent.length == 2 && fileContent[0].equals(getDepartmentCode())) {
                        setDepartmentName(fileContent[1]);
                    }
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            System.out.println("An error has occurred. " + e);
        }
        return data;
    }
    public void departmentFileProcessing(List<Department> record, Path path, boolean registered){
        try {
            if (!Files.exists(path)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()));

                bufferedWriter.write("Dept. Code\tDept. Name");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            if (!registered){
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), true));

                for (Department departmentData : record){

                    bufferedWriter.write(departmentData.getDepartmentCode() + "\t" + departmentData.getDepartmentName());
                    bufferedWriter.newLine();
                    setDepartmentCode(departmentData.getDepartmentCode());
                }
                bufferedWriter.close();
            }

        }catch (IOException e){
            System.out.println("An error occurred " + e);
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

                    String[] departments = line.split("\t");
                    if (departments.length == 2 && departments[0].equals(getDepartmentCode())){
                        registered = true;
                    }
                }
            }
        }catch (IOException ignored){}

        return registered;
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
}
