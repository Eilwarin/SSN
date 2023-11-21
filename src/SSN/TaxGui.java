// File: TaxGui.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon
package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;

public class TaxGui extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> taxDropdown;
    private final EmployeeTax tax = new EmployeeTax();
    private final Path path = Path.of("tax_info.txt");

    public TaxGui() {
        setTitle("Employee's Tax Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createTaxButtons();
        addBackButton();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createTaxButtons() {
        // Create department buttons
        JButton addRecordButton = new JButton("Add new record");
        JButton updateRecordButton = new JButton("Update existing record");
        JButton viewSingleRecordButton = new JButton("View a single record");
        JButton viewAllRecordsButton = new JButton("View all records (by Department)");
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
        JTextField employeeTrn = new JTextField(15);
        JTextField employeeNis = new JTextField(15);

        // Create and add labels for text fields
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel firstNameLabel = new JLabel("TRN:");
        JLabel lastNameLabel = new JLabel("NIS:");

        // Add labels and text fields to contentPanel
        contentPanel.add(idNumberLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeIdNumber, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(firstNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeTrn, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 3;
        contentPanel.add(lastNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(employeeNis, textFieldConstraints);


        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 4; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            // Retrieve values from text fields
            if (validId(employeeIdNumber.getText()) && employeeIdNumber.getText().length() == 7){
                tax.setIdNumber(employeeIdNumber.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Invalid ID Number\n(Must be numeric and seven characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                registerEmployee();
            }
            if (validId(employeeTrn.getText()) && employeeTrn.getText().length() == 9){
                tax.setTrn(employeeTrn.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Invalid TRN\n(Must be numeric and nine characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                registerEmployee();
            }
            if (validName(employeeNis.getText()) && employeeNis.getText().length() == 7){
                tax.setNis(employeeNis.getText());
                tax.taxInformation(path, tax.registeredTax(path), tax.createRecord());
            }else {
                JOptionPane.showMessageDialog(this, "Invalid NIS\n(Must be alphanumeric and seven characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                registerEmployee();
            }

            employeeIdNumber.setText(null);
            employeeTrn.setText(null);
            employeeNis.setText(null);

            refreshUi();
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private boolean validId(String id){
        try {
            Double.parseDouble(id);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    private boolean validName(String trn){
        for (int i = 0; i < trn.length(); i++) {
            char ch = trn.charAt(i);
            // Check if the character is not an alphanumeric character
            if (!(Character.isLetterOrDigit(ch))) {
                return false; // Found a non-alphanumeric character
            }
        }
        return true; // No non-alphanumeric characters found
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

        java.util.List<String> employeeIdNumbers = tax.viewAllEmployees(path, true);
        taxDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(taxDropdown, dropdownConstraints);

        try{
            // Add action listener to employee dropdown
            taxDropdown.addActionListener(e -> {
                // Clear existing components from contentPanel
                clearContent();

                tax.setIdNumber(Objects.requireNonNull(taxDropdown.getSelectedItem()).toString());
                tax.viewSingleEmployee(path, false);

                // Dynamically create and add centered labels based on selected option
                addLabelsBasedOnOptionCentered(0);
                // Dynamically create and add text fields with labels based on selected option
                addTextFieldsForUpdate();
            });
        }catch (NullPointerException ignored){}

        // Refresh the UI
        refreshUi();
    }
    private void removeEmployee(){
        // Clear existing components from contentPanel
        clearContent();

        // Create and add label for employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID Number from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = tax.viewAllEmployees(path, true);
        taxDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(taxDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        taxDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            tax.setIdNumber(Objects.requireNonNull(taxDropdown.getSelectedItem()).toString());
            tax.viewSingleEmployee(path, true);

            JLabel removeMessage = new JLabel("Employee record successfully removed.");
            GridBagConstraints messageConstraints = new GridBagConstraints();
            messageConstraints.gridx = 0;
            messageConstraints.gridy = 0;
            messageConstraints.insets = new Insets(10, 10, 10, 10);
            contentPanel.add(removeMessage, messageConstraints);
        });

        refreshUi();
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
        JTextField employeeIdNumber = new JTextField(tax.getIdNumber(), 15);
        JTextField employeeFirstName = new JTextField(tax.getFirstName(), 15);
        JTextField employeeLastName = new JTextField(tax.getLastName(), 15);
        JTextField employeePosition = new JTextField(tax.getPosition(), 15);

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
            tax.viewSingleEmployee(path, true);
            // Retrieve values from text fields
            tax.setIdNumber(employeeIdNumber.getText());
            tax.setFirstName(employeeFirstName.getText());
            tax.setLastName(employeeLastName.getText());
            tax.setPosition(employeePosition.getText());
            tax.setDepartmentCode(tax.getIdNumber().substring(0, 4));
            tax.setPositionId(tax.getIdNumber().substring(4, 7));

            JLabel removeMessage = new JLabel("Employee record successfully updated.");
            GridBagConstraints messageConstraints = new GridBagConstraints();
            messageConstraints.gridx = 0;
            messageConstraints.gridy = 0;
            messageConstraints.insets = new Insets(10, 10, 10, 10);
            contentPanel.add(removeMessage, messageConstraints);

            employeeIdNumber.setText(null);
            employeeFirstName.setText(null);
            employeeLastName.setText(null);
            employeePosition.setText(null);

            tax.taxInformation(path, tax.registeredTax(path), tax.createRecord());
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private void showDepartmentEmployees() {
        clearContent();

        // Get a list of department codes
        java.util.List<String> departmentCodes = tax.viewAllDepartments(Path.of("departments.txt"));

        // Create and add label for tax selection
        JLabel selectLabel = new JLabel("Select a Department Code from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);
        // Create and add department dropdown
        taxDropdown = new JComboBox<>(departmentCodes.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(taxDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        taxDropdown.addActionListener(e -> {
            // Set the department code immediately
            tax.setDepartmentCode(Objects.requireNonNull(taxDropdown.getSelectedItem()).toString());

            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            addHeaderRowCentered();

            // Get a list of employees for the selected department
            java.util.List<String> employees = tax.viewAllEmployees(path, false);

            for (int grid = 0; grid < employees.size(); grid++) {
                tax.setIdNumber(employees.get(grid));
                tax.viewSingleEmployee(path, false);
                addLabelsBasedOnOptionCentered(grid);
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

        // Create and add label for department selection
        JLabel selectLabel = new JLabel("Select an Employee ID from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> employeeIdNumbers = tax.viewAllEmployees(path, true);
        taxDropdown = new JComboBox<>(employeeIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(taxDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        taxDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            // Add header labels centered
            addHeaderRowCentered();
            tax.setIdNumber(Objects.requireNonNull(taxDropdown.getSelectedItem()).toString());
            tax.viewSingleEmployee(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
        });

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
    }
    private void addLabelsBasedOnOptionCentered(int grid) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        labelConstraints.insets = new Insets(10, 10, 10, 10);
        labelConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add centered labels dynamically
        JLabel idNumber = new JLabel(tax.getIdNumber());
        JLabel firstName = new JLabel(tax.getFirstName());
        JLabel lastName = new JLabel(tax.getLastName());
        JLabel departmentName = new JLabel(tax.getDepartmentName());
        JLabel position = new JLabel(tax.getPosition());

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
