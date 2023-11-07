package org.example;

import java.nio.file.Path;
import java.util.List;

public interface DataProcessing {
    List createRecord();
    void fileProcessing(List<Department> record, Path path);
}
