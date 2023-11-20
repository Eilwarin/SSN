package SSN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Objects;

public class GUI extends JFrame {

    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JComboBox<String> departmentDropdown;
    private Department department = new Department();
    private final Path path = Path.of("departments.txt");

    public GUI() {
        setTitle("Departments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createDepartmentButtons();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
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

    private void registerDepartment(){
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
            // Retrieve values from text fields
            department.setDepartmentCode(departmentCodeTextField.getText());
            department.setDepartmentName(departmentNameTextField.getText());
            department.departmentFileProcessing(department.createDepartmentRecord(), path, department.registered(path));
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void updateDepartment() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        // Create and add label for department selection
        JLabel selectLabel = new JLabel("Select a department from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        // Create and add department dropdown
        departmentDropdown = new JComboBox<>(department.viewAllDepartments(path).toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(departmentDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        departmentDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            department.setDepartmentCode(Objects.requireNonNull(departmentDropdown.getSelectedItem()).toString());
            department.viewSingleDepartment(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
            // Dynamically create and add text fields with labels based on selected option
            addTextFieldsForUpdate();
        });

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
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
            department.setDepartmentCode(departmentCodeTextField.getText());
            department.setDepartmentName(departmentNameTextField.getText());
            department.departmentFileProcessing(department.createDepartmentRecord(), path, department.registered(path));
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showAllDepartments() {
        java.util.List<String> departments = department.viewAllDepartments(path);

        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        addHeaderRowCentered();

        for (int grid = 0; grid < departments.size(); grid++) {
            department.setDepartmentCode(departments.get(grid));
            department.viewSingleDepartment(path, false);
            addLabelsBasedOnOptionCentered(grid);
        }

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showDepartment() {
        // Clear existing components from contentPanel
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        // Create and add label for department selection
        JLabel selectLabel = new JLabel("Select a department from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        // Create and add department dropdown
        departmentDropdown = new JComboBox<>(department.viewAllDepartments(path).toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(departmentDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        departmentDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            // Add header labels centered
            addHeaderRowCentered();
            department.setDepartmentCode(Objects.requireNonNull(departmentDropdown.getSelectedItem()).toString());
            department.viewSingleDepartment(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
        });

        // Refresh the UI
        contentPanel.revalidate();
        contentPanel.repaint();
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
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
