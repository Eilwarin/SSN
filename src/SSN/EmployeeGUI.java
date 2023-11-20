package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;

public class EmployeeGUI extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> employeeDropdown;
    private final Employee employee = new Employee();
    private final Path path = Path.of("employees.txt");

    public EmployeeGUI() {
        setTitle("Employees");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createEmployeeButtons();

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

    private void registerEmployee(){
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

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

        employeeIdNumber.setText(null);
        employeeFirstName.setText(null);
        employeeLastName.setText(null);
        employeePosition.setText(null);

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
            employee.setIdNumber(employeeIdNumber.getText());
            employee.setFirstName(employeeFirstName.getText());
            employee.setLastName(employeeLastName.getText());
            employee.setPosition(employeePosition.getText());
            employee.setDepartmentCode(employee.getIdNumber().substring(0, 4));
            employee.setPositionId(employee.getIdNumber());

            JLabel removeMessage = new JLabel("Employee record successfully created.");
            GridBagConstraints messageConstraints = new GridBagConstraints();
            messageConstraints.gridx = 0;
            messageConstraints.gridy = 0;
            messageConstraints.insets = new Insets(10, 10, 10, 10);
            contentPanel.add(removeMessage, messageConstraints);

            employeeIdNumber.setText(null);
            employeeFirstName.setText(null);
            employeeLastName.setText(null);
            employeePosition.setText(null);

            employee.employeeFileProcessing(employee.createEmployeeRecord(), path, employee.validation(path));
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void updateEmployee() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();


        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID Number from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = employee.viewAllEmployees(path, true);
        employeeDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(employeeDropdown, dropdownConstraints);

        // Add action listener to employee dropdown
        employeeDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
            employee.viewSingleEmployee(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
            // Dynamically create and add text fields with labels based on selected option
            addTextFieldsForUpdate();
        });

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void removeEmployee(){
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID Number from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = employee.viewAllEmployees(path, true);
        employeeDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(employeeDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        employeeDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
            employee.viewSingleEmployee(path, true);

            JLabel removeMessage = new JLabel("Employee record successfully removed.");
            GridBagConstraints messageConstraints = new GridBagConstraints();
            messageConstraints.gridx = 0;
            messageConstraints.gridy = 0;
            messageConstraints.insets = new Insets(10, 10, 10, 10);
            contentPanel.add(removeMessage, messageConstraints);
        });

    }
    private void addTextFieldsForUpdate() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

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
            employee.setIdNumber(employeeIdNumber.getText());
            employee.setFirstName(employeeFirstName.getText());
            employee.setLastName(employeeLastName.getText());
            employee.setPosition(employeePosition.getText());
            employee.setDepartmentCode(employee.getIdNumber().substring(0, 4));
            employee.setPositionId(employee.getIdNumber().substring(4, 7));

            employeeIdNumber.setText(null);
            employeeFirstName.setText(null);
            employeeLastName.setText(null);
            employeePosition.setText(null);

            employee.employeeFileProcessing(employee.createEmployeeRecord(), path, employee.validation(path));
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showDepartmentEmployees() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        // Get a list of department codes
        java.util.List<String> departmentCodes = employee.viewAllDepartments(Path.of("departments.txt"));

        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select a Department Code from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);
        // Create and add department dropdown
        employeeDropdown = new JComboBox<>(departmentCodes.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(employeeDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        employeeDropdown.addActionListener(e -> {
            // Set the department code immediately
            employee.setDepartmentCode(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());

            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            addHeaderRowCentered();

            // Get a list of employees for the selected department
            java.util.List<String> employees = employee.viewAllEmployees(path, false);

            for (int grid = 0; grid < employees.size(); grid++) {
                employee.setIdNumber(employees.get(grid));
                employee.viewSingleEmployee(path, false);
                addLabelsBasedOnOptionCentered(grid);
            }

            // Refresh the UI
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showEmployee() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        // Create and add label for department selection
        JLabel selectLabel = new JLabel("Select an Employee ID from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = employee.viewAllEmployees(path, true);
        employeeDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(employeeDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        employeeDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            // Add header labels centered
            addHeaderRowCentered();
            employee.setIdNumber(Objects.requireNonNull(employeeDropdown.getSelectedItem()).toString());
            employee.viewSingleEmployee(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
        });

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void addHeaderRowCentered() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

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


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeGUI::new);
    }
}
