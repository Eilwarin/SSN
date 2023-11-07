package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Department department = new Department();
        Path path = Paths.get("departments.txt");
//        department.fileProcessing(department.createRecord(), path);
//        department.viewAllRecords(path);
//        department.viewSingleRecord(path);
        department.updateRecord(path, path);
    }
}