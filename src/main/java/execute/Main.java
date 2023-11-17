package execute;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        PositionRates rates = new PositionRates();
        Department department = new Department();
        Path ratesFile = Paths.get("rates.txt");
        Path departmentsFile = Paths.get("departments.txt");
//        department.departmentFileProcessing(department.createDepartmentRecord(), departmentsFile);
        department.viewSingleDepartment(departmentsFile);
    }
}
