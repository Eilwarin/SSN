package execute;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        PositionRates rates = new PositionRates();
        Department department = new Department();
        Employee employee = new Employee();
        Positions positions = new Positions();
        Path ratesFile = Paths.get("rates.txt");
        Path departmentsFile = Paths.get("departments.txt");
        Path employeesFile = Paths.get("employees.txt");
        Path positionsFile = Paths.get("positions.txt");
        Scanner choice = new Scanner(System.in);

//        department.departmentFileProcessing(department.createDepartmentRecord(), departmentsFile);


//        department.viewSingleDepartment(departmentsFile);
        employee.employeeFileProcessing(employee.createEmployeeRecord(), employeesFile, employee.validation(employeesFile));
    }
}
