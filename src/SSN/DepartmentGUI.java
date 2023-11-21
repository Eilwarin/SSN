//Created by Dontray Blackwood, Rajaire Thomas, Rochelle Gordon, Alexi Brooks, Jamari Ferguson
package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;

public class DepartmentGUI extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> departmentDropdown;
    private final Department department = new Department();
    private final Path path = Path.of("departments.txt");

    public DepartmentGUI() {
        setTitle("Departments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createDepartmentButtons();
        addBackButton();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createDepartmentButtons() {
        // Create department buttons
        JButton addRecordButton = new JButton("Add new record");
        JButton updateRecordButton = new JButton("Update existing record");
        JButton viewSingleRecordButton = new JButton("View a single record");
        JButton viewAllRecordsButton = new JButton("View all records");

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

        // Add action listeners to department buttons
        addRecordButton.addActionListener(e -> registerDepartment());
        updateRecordButton.addActionListener(e -> updateDepartment());
        viewSingleRecordButton.addActionListener(e -> showDepartment());
        viewAllRecordsButton.addActionListener(e -> showAllDepartments());
    }
    private void addBackButton() {
        JButton backButton = new JButton("Back to Menu");
        GridBagConstraints backButtonConstraints = new GridBagConstraints();
        backButtonConstraints.gridx = 2;
        backButtonConstraints.gridy = 1; // Adjust the y-coordinate based on your layout
        backButtonConstraints.gridwidth = 2; // Span two columns
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
    private void clearContent() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void refreshUi() {
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private boolean validCode(String dpCode){
        try{
            Double.parseDouble(dpCode);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    private boolean validName(String dpName){
        for (int i = 0; i < dpName.length(); i++) {
            char ch = dpName.charAt(i);
            // Check if the character is not an alphanumeric character
            if (!(Character.isLetterOrDigit(ch) || Character.isWhitespace(ch))) {
                return false; // Found a non-alphanumeric character
            }
        }
        return true; // No non-alphanumeric characters found
    }
    private void departmentDropdown(){
        // Create and add label for department selection
        JLabel selectLabel = new JLabel("Select a Department from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        // Create and add department dropdown
        java.util.List<String> departments = department.viewAllDepartments(path);
        if (departments.isEmpty()){
            JOptionPane.showMessageDialog(this, "There are no records available.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            clearContent();
        }else {
            departmentDropdown = new JComboBox<>(departments.toArray(new String[0]));
            GridBagConstraints dropdownConstraints = new GridBagConstraints();
            dropdownConstraints.gridx = 0;
            dropdownConstraints.gridy = 1;
            dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
            contentPanel.add(departmentDropdown, dropdownConstraints);
        }

        refreshUi();
    }
    private void submitAction(JTextField departmentCodeTextField, JTextField departmentNameTextField) {
        if (validCode(departmentCodeTextField.getText()) && departmentCodeTextField.getText().length() == 4){
            department.setDepartmentCode(departmentCodeTextField.getText());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid Department Code\n(Must be numeric and 4 characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerDepartment();
        }

        if (validName(departmentNameTextField.getText())){
            department.setDepartmentName(departmentNameTextField.getText());
        }else {
            JOptionPane.showMessageDialog(this, "Invalid Department Name\n(Must be alphanumeric. Cannot contain special characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            registerDepartment();
        }
    }
    private void registerDepartment() {
        // Clear existing components from contentPanel
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField departmentCodeTextField = new JTextField(department.getDepartmentCode(), 15);
        JTextField departmentNameTextField = new JTextField(department.getDepartmentName(), 15);

        departmentCodeTextField.setText(null);
        departmentNameTextField.setText(null);

        // Create and add labels for text fields
        JLabel departmentCodeLabel = new JLabel("Department Code:");
        JLabel departmentNameLabel = new JLabel("Department Name:");

        // Add labels and text fields to contentPanel
        contentPanel.add(departmentCodeLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(departmentCodeTextField, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(departmentNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(departmentNameTextField, textFieldConstraints);

        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 3; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            // Retrieve values from text fields
            submitAction(departmentCodeTextField, departmentNameTextField);

            departmentCodeTextField.setText(null);
            departmentNameTextField.setText(null);

            department.departmentFileProcessing(department.createDepartmentRecord(), path, department.registeredDepartment(path), true);
            JOptionPane.showMessageDialog(this, "Operations completed.", "Alert", JOptionPane.INFORMATION_MESSAGE);
            clearContent();
            registerDepartment();
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private void updateDepartment() {
        // Clear existing components from contentPanel
        clearContent();

        departmentDropdown();

        // Add action listener to department dropdown
        departmentDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            department.setDepartmentCode(Objects.requireNonNull(departmentDropdown.getSelectedItem()).toString());
            department.viewSingleDepartment(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
            // Dynamically create and add text fields with labels based on selected option
            addTextFieldsForUpdate();
        });

        // Refresh the UI
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
        JTextField departmentCodeTextField = new JTextField(department.getDepartmentCode(), 15);
        JTextField departmentNameTextField = new JTextField(department.getDepartmentName(), 15);

        // Create and add labels for text fields
        JLabel departmentCodeLabel = new JLabel("Department Code:");
        JLabel departmentNameLabel = new JLabel("Department Name:");

        // Add labels and text fields to contentPanel
        contentPanel.add(departmentCodeLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(departmentCodeTextField, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(departmentNameLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(departmentNameTextField, textFieldConstraints);

        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 3; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            department.viewSingleDepartment(path, true);
            // Retrieve values from text fields
            submitAction(departmentCodeTextField, departmentNameTextField);

            departmentCodeTextField.setText(null);
            departmentNameTextField.setText(null);

            department.departmentFileProcessing(department.createDepartmentRecord(), path, department.registeredDepartment(path), true);
            JOptionPane.showMessageDialog(this, "Operations completed.", "Alert", JOptionPane.INFORMATION_MESSAGE);
            updateDepartment();
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private void showAllDepartments() {
        clearContent();

        java.util.List<String> departments = department.viewAllDepartments(path);
        if (departments.isEmpty()){
            JOptionPane.showMessageDialog(this, "There are no records available.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
        }else {
            addHeaderRowCentered();

            for (int grid = 0; grid < departments.size(); grid++) {
                department.setDepartmentCode(departments.get(grid));
                department.viewSingleDepartment(path, false);
                addLabelsBasedOnOptionCentered(grid);
            }
        }
        // Refresh the UI
        refreshUi();
    }
    private void showDepartment() {
        // Clear existing components from contentPanel
        clearContent();

        departmentDropdown();

        // Add action listener to department dropdown
        departmentDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            // Add header labels centered
            addHeaderRowCentered();
            department.setDepartmentCode(Objects.requireNonNull(departmentDropdown.getSelectedItem()).toString());
            department.viewSingleDepartment(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
        });

        // Refresh the UI
        refreshUi();
    }
    private void addHeaderRowCentered() {
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.insets = new Insets(10, 10, 10, 10);
        headerConstraints.anchor = GridBagConstraints.CENTER;

        // Add header labels centered
        JLabel departmentCodeLabel = new JLabel("Department Code");
        JLabel departmentNameLabel = new JLabel("Department Name");

        // Add header labels to contentPanel
        contentPanel.add(departmentCodeLabel, headerConstraints);
        headerConstraints.gridx = 1;
        contentPanel.add(departmentNameLabel, headerConstraints);

        refreshUi();
    }
    private void addLabelsBasedOnOptionCentered(int grid) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        labelConstraints.insets = new Insets(10, 10, 10, 10);
        labelConstraints.anchor = GridBagConstraints.CENTER;

        // Determine label values based on set index (replace with your logic)
        String departmentCodeValue = department.getDepartmentCode();
        String departmentNameValue = department.getDepartmentName();

        // Create and add centered labels dynamically
        JLabel departmentCodeLabel = new JLabel(departmentCodeValue);
        JLabel departmentNameLabel = new JLabel(departmentNameValue);

        // Add labels to contentPanel
        contentPanel.add(departmentCodeLabel, labelConstraints);
        labelConstraints.gridx = 1;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(departmentNameLabel, labelConstraints);

        refreshUi();
    }
}
