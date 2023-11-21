// File: EmployeeGui.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon
package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class EmployeeGui extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> employeeDropdown;
    private final Employee employee = new Employee();
    private final Path path = Path.of("employees.txt");

    public EmployeeGui() {
        setTitle("Employees");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createEmployeeButtons();
        addBackButton();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createEmployeeButtons() {
        // Create department buttons
        JButton addRecordButton = new JButton("Add new record");
        JButton updateRecordButton = new JButton("Update existing record");
        JButton viewSingleRecordButton = new JButton("View a single record");
        JButton viewAllRecordsButton = new JButton("View all records");
        JButton removeRecordButton = new JButton("Remove a record");

        // Set layout manager for buttonPanel
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Center buttons at the top
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        buttonPanel.add(addRecordButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(updateRecordButton, gbc);

        gbc.gridx = 3;
        buttonPanel.add(viewSingleRecordButton, gbc);

        gbc.gridx = 4;
        buttonPanel.add(viewAllRecordsButton, gbc);

        gbc.gridx = 5;
        buttonPanel.add(removeRecordButton, gbc);

        // Add action listeners to department buttons
        addRecordButton.addActionListener(e -> registerEmployee());
        updateRecordButton.addActionListener(e -> updateEmployee());
        viewSingleRecordButton.addActionListener(e -> showEmployee());
        viewAllRecordsButton.addActionListener(e -> showDepartmentEmployees());
        removeRecordButton.addActionListener(e -> removeEmployee());
    }
    private void addBackButton() {
        JButton backButton = new JButton("Back to Menu");
        GridBagConstraints backButtonConstraints = new GridBagConstraints();
        backButtonConstraints.gridx = 3;
        backButtonConstraints.gridy = 1; // Adjust the y-coordinate based on your layout
        backButtonConstraints.insets = new Insets(10, 10, 10, 10);
        backButtonConstraints.anchor = GridBagConstraints.CENTER;

        backButton.addActionListener(e -> {
            // Close the current window
            dispose();

            // Invoke the main GUI
            SwingUtilities.invokeLater(Main::new);
        });

        buttonPanel.add(backButton, backButtonConstraints);
    }
    private void clearContent(){
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void refreshUi(){
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void employeeDropdown(){
        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID Number from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = employee.viewAllEmployees(path, true);
        employeeDropdown(employeeIdNumbers);

    }
    private boolean validId(String id){
        try {
            Double.parseDouble(id);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    private boolean validName(String name){
        int hyphenCount = 0;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            // Check if the character is not a letter or a hyphen
            if (!(Character.isLetter(ch) ||
                    (ch == '-' && hyphenCount == 0 && i > 0 && i < name.length() - 1)
                    || Character.isWhitespace(ch))) {
                return false; // Found a non-letter character (excluding one hyphen)
            }
            if (ch == '-') {
                hyphenCount++;
            }
        }
        return true; // No non-letter characters found (excluding one hyphen)
    }
    private boolean validPosition(String position){
        for (int i = 0; i < position.length(); i++) {
            char ch = position.charAt(i);
            // Check if the character is not a letter
            if (!(Character.isLetter(ch) || Character.isWhitespace(ch))) {
                return false; // Found a non-letter character
            }
        }
        return true; // No non-letter characters found
    }
    private void departmentDropdown(){
        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select a Department Code from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        // Get a list of department codes
        java.util.List<String> departmentCodes = employee.viewAllDepartments(Path.of("departments.txt"));
        employeeDropdown(departmentCodes);
    }
    private void registerEmployee(){
        // Clear existing components from contentPanel
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField employeeIdNumber = new JTextField(15);
        JTextField employeeFirstName = new JTextField(15);
        JTextField employeeLastName = new JTextField(15);
        JTextField employeePosition = new JTextField(15);

        // Create and add labels for text fields
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel positionLabel = new JLabel("Job Title/Position:");

        // Add labels and text fields to contentPanel
        contentPanel.add(idNumberLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeIdNumber, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(firstNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeFirstName, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 3;
        contentPanel.add(lastNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeLastName, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 4;
        contentPanel.add(positionLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeePosition, textFieldConstraints);

        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 5; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            // Retrieve values from text fields
            submitAction(employeeIdNumber, employeeFirstName, employeeLastName, employeePosition);
            Path departmentFile = Path.of("departments.txt");

            //Writing to the employee file with the new employee's details.
            employee.employeeFileProcessing(employee.createEmployeeRecord(), path, employee.validation(path), employee.dataGather(departmentFile));
            JOptionPane.showMessageDialog(this, "Operations completed.", "Alert", JOptionPane.INFORMATION_MESSAGE);
            clearContent();
            registerEmployee();
        });
        contentPanel.add(submitButton, submitButtonConstraints);

        // Refresh the UI
        refreshUi();
    }
    private void submitAction(JTextField employeeIdNumber, JTextField employeeFirstName, JTextField employeeLastName, JTextField employeePosition) {
        employee.setDepartmentCode(employeeIdNumber.getText().substring(0, 4));
        Path departmentFile = Path.of("departments.txt");
        if (!(employee.dataGather(departmentFile))){
            JOptionPane.showMessageDialog(this, "Department Code: " + employee.getDepartmentCode() + " does not exist.\n" +
                    "(You must add the Department before adding an Employee)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerEmployee();
        }
        if (validId(employeeIdNumber.getText())){
            employee.setIdNumber(employeeIdNumber.getText());
            employee.setPositionId(employee.getIdNumber());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid ID Number\n(Must be numeric and 7 characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerEmployee();
        }

        if (validName(employeeFirstName.getText())){
            employee.setFirstName(employeeFirstName.getText());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid Name Format\n(Must be alphabetic. Hyphens and spaces allowed.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerEmployee();
        }

        if (validName(employeeLastName.getText())){
            employee.setLastName(employeeLastName.getText());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid Name Format\n(Must be alphabetic. Hyphens and spaces allowed.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerEmployee();
        }

        if (validPosition(employeePosition.getText())){
            employee.setPosition(employeePosition.getText());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid Name Format\n(Must be alphabetic. Hyphens and spaces allowed.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerEmployee();
        }

        employeeIdNumber.setText(null);
        employeeFirstName.setText(null);
        employeeLastName.setText(null);
        employeePosition.setText(null);
    }
    private void updateEmployee() {
        // Clear existing components from contentPanel
        clearContent();

        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID Number from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = employee.viewAllEmployees(path, true);
        employeeDropdown(employeeIdNumbers);

        try{
            // Add action listener to employee dropdown
            employeeDropdown.addActionListener(e -> {
                // Clear existing components from contentPanel
                clearContent();

                employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
                employee.viewSingleEmployee(path, false);

                // Dynamically create and add centered labels based on selected option
                addLabelsBasedOnOptionCentered(0);
                // Dynamically create and add text fields with labels based on selected option
                addTextFieldsForUpdate();
            });
        }catch (NullPointerException ignored){}

        // Refresh the UI
        refreshUi();
    }

    private void employeeDropdown(List<String> employeeIdNumbers){
        if (employeeIdNumbers.isEmpty()){
            JOptionPane.showMessageDialog(this, "There are no records available.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            clearContent();
        }else {
            employeeDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
            GridBagConstraints dropdownConstraints = new GridBagConstraints();
            dropdownConstraints.gridx = 0;
            dropdownConstraints.gridy = 1;
            dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
            contentPanel.add(employeeDropdown, dropdownConstraints);
        }
    }

    private void removeEmployee(){
        // Clear existing components from contentPanel
        clearContent();

        employeeDropdown();

        // Add action listener to department dropdown
        employeeDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
            employee.viewSingleEmployee(path, true);

            JOptionPane.showMessageDialog(this, "Operations completed.", "Alert", JOptionPane.INFORMATION_MESSAGE);
            removeEmployee();
        });

    }
    private void addTextFieldsForUpdate() {
        // Clear existing components from contentPanel
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField employeeIdNumber = new JTextField(employee.getIdNumber(), 15);
        JTextField employeeFirstName = new JTextField(employee.getFirstName(), 15);
        JTextField employeeLastName = new JTextField(employee.getLastName(), 15);
        JTextField employeePosition = new JTextField(employee.getPosition(), 15);

        // Create and add labels for text fields
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel positionLabel = new JLabel("Job Title/Position:");

        // Add labels and text fields to contentPanel
        contentPanel.add(idNumberLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeIdNumber, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(firstNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeFirstName, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 3;
        contentPanel.add(lastNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeLastName, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 4;
        contentPanel.add(positionLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeePosition, textFieldConstraints);

        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 5; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            employee.viewSingleEmployee(path, true);
            // Retrieve values from text fields
            submitAction(employeeIdNumber, employeeFirstName, employeeLastName, employeePosition);

            employeeIdNumber.setText(null);
            employeeFirstName.setText(null);
            employeeLastName.setText(null);
            employeePosition.setText(null);

            employee.employeeFileProcessing(employee.createEmployeeRecord(), path, employee.validation(path), employee.dataGather(Path.of("departments.txt")));
            JOptionPane.showMessageDialog(this, "Operations completed.", "Alert", JOptionPane.INFORMATION_MESSAGE);
            updateEmployee();
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private void showDepartmentEmployees() {
        clearContent();

        departmentDropdown();

        // Add action listener to department dropdown
        employeeDropdown.addActionListener(e -> {
            // Set the department code immediately
            employee.setDepartmentCode(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());

            // Clear existing components from contentPanel
            clearContent();

            // Get a list of employees for the selected department
            java.util.List<String> employees = employee.viewAllEmployees(path, false);

            if(employees.isEmpty()){
                JOptionPane.showMessageDialog(this, "There are no records for this Department.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                showDepartmentEmployees();
            }else {
                addHeaderRowCentered();
                for (int grid = 0; grid < employees.size(); grid++) {
                    employee.setIdNumber(employees.get(grid));
                    employee.viewSingleEmployee(path, false);
                    addLabelsBasedOnOptionCentered(grid);
                }
            }

            // Refresh the UI
            refreshUi();
        });

        // Refresh the UI
        refreshUi();
    }
    private void showEmployee() {
        // Clear existing components from contentPanel
        clearContent();

        employeeDropdown();

        try{
            // Add action listener to department dropdown
            employeeDropdown.addActionListener(e -> {
                // Clear existing components from contentPanel
                clearContent();

                // Add header labels centered
                addHeaderRowCentered();
                employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
                employee.viewSingleEmployee(path, false);

                // Dynamically create and add centered labels based on selected option
                addLabelsBasedOnOptionCentered(0);
            });
        }catch (NullPointerException ignored){}

        // Refresh the UI
        refreshUi();
    }
    private void addHeaderRowCentered() {
        clearContent();

        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.insets = new Insets(10, 10, 10, 10);
        headerConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add labels for header row.
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel departmentNameLabel = new JLabel("Department Name:");
        JLabel positionLabel = new JLabel("Job Title/Position:");

        // Adjust gridx values for each label
        contentPanel.add(idNumberLabel, headerConstraints);
        headerConstraints.gridx = 1;
        contentPanel.add(firstNameLabel, headerConstraints);
        headerConstraints.gridx = 2;
        contentPanel.add(lastNameLabel, headerConstraints);
        headerConstraints.gridx = 3;
        contentPanel.add(departmentNameLabel, headerConstraints);
        headerConstraints.gridx = 4;
        contentPanel.add(positionLabel, headerConstraints);

        refreshUi();
    }
    private void addLabelsBasedOnOptionCentered(int grid) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        labelConstraints.insets = new Insets(10, 10, 10, 10);
        labelConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add centered labels dynamically
        JLabel idNumber = new JLabel(employee.getIdNumber());
        JLabel firstName = new JLabel(employee.getFirstName());
        JLabel lastName = new JLabel(employee.getLastName());
        JLabel departmentName = new JLabel(employee.getDepartmentName());
        JLabel position = new JLabel(employee.getPosition());

        // Add labels to contentPanel
        contentPanel.add(idNumber, labelConstraints);
        labelConstraints.gridx = 1;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(firstName, labelConstraints);

        labelConstraints.gridx = 2;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(lastName, labelConstraints);

        labelConstraints.gridx = 3;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(departmentName, labelConstraints);

        labelConstraints.gridx = 4;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(position, labelConstraints);

        refreshUi();
    }
}
