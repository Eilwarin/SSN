package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;
public class PayrollGUI extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> payrollDropdown;
    private final Payroll payroll = new Payroll();
    private final Path path = Path.of("payroll.txt");

    public PayrollGUI() {
        setTitle("Payroll");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createPayrollButtons();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createPayrollButtons() {
        // Create department buttons
        JButton processPayrollButton = new JButton("Process Payroll");
        JButton viewEmployeePayrollButton = new JButton("View Employee Payroll");
        JButton viewDepartmentPayrollButton = new JButton("View Department Payroll");

        // Set layout manager for buttonPanel
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Center buttons at the top
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        buttonPanel.add(processPayrollButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(viewEmployeePayrollButton, gbc);

        gbc.gridx = 3;
        buttonPanel.add(viewDepartmentPayrollButton, gbc);
        // Add action listeners to department buttons
        processPayrollButton.addActionListener(e -> processPayroll());
        viewEmployeePayrollButton.addActionListener(e -> viewEmployeePayroll());
        viewDepartmentPayrollButton.addActionListener(e -> viewDepartmentPayroll());
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
    private void departmentDropdown(){
        // Create and add label for Department selection
        JLabel selectLabel = new JLabel("Select a Department Code from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> rateIdNumbers = payroll.viewAllDepartments(Path.of("departments.txt"));
        payrollDropdown = new JComboBox<>(rateIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(payrollDropdown, dropdownConstraints);
    }
    private void employeeDropdown(boolean gather){
        // Create and add label for Employee selection
        JLabel selectLabel = new JLabel("Select an Employee ID from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> rateIdNumbers = payroll.viewAllEmployees(Path.of("employees.txt"), gather);
        payrollDropdown = new JComboBox<>(rateIdNumbers.toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(payrollDropdown, dropdownConstraints);

        refreshUi();
    }

    private void successMessage(){
        JLabel removeMessage = new JLabel("Operation successfully completed.");
        GridBagConstraints messageConstraints = new GridBagConstraints();
        messageConstraints.gridx = 0;
        messageConstraints.gridy = 0;
        messageConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(removeMessage, messageConstraints);

        refreshUi();
    }
    private void failureMessage(String reason){
        clearContent();
        JLabel removeMessage = new JLabel("Operation could not be completed. " + "(" + reason + ")");
        GridBagConstraints messageConstraints = new GridBagConstraints();
        messageConstraints.gridx = 0;
        messageConstraints.gridy = 0;
        messageConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(removeMessage, messageConstraints);

        refreshUi();
    }
    private void processPayroll(){
        clearContent();

        departmentDropdown();

        payrollDropdown.addActionListener(e -> {
            payroll.setDepartmentCode(Objects.requireNonNull(payrollDropdown.getSelectedItem()).toString());
            clearContent();
            employeeDropdown(false);
            payrollDropdown.addActionListener(e1 -> {
                payroll.setIdNumber(Objects.requireNonNull(payrollDropdown.getSelectedItem()).toString());
                calculatePay();
            });
        });
    }
    private void calculatePay(){
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField hoursWorked = new JTextField(15);

        // Create and add labels for text fields
        JLabel hoursWorkedLabel = new JLabel("Hours worked:");

        // Add labels and text fields to contentPanel
        contentPanel.add(hoursWorkedLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(hoursWorked, textFieldConstraints);

        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 2; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            // Retrieve values from text fields
            payroll.setHoursWorked(Float.parseFloat(hoursWorked.getText()));
            clearContent();
            if (payroll.getPositionRates() && payroll.getTaxInfo()){
                successMessage();
                payroll.payrollFileProcessing(payroll.calculatePay(), path, payroll.getPositionRates());
            }else {
                failureMessage("No pay rate/tax information available for ID Number# " + payroll.getIdNumber());
            }
        });
        contentPanel.add(submitButton, submitButtonConstraints);
        // Refresh the UI
        refreshUi();
    }
    private void viewEmployeePayroll(){
        clearContent();

        employeeDropdown(true);

        payrollDropdown.addActionListener(e -> {
            payroll.setIdNumber(Objects.requireNonNull(payrollDropdown.getSelectedItem()).toString());
            if (payroll.viewEmployeePayroll(path)){
                addHeaderRowCentered();
                addLabelsBasedOnOptionCentered(0);
            }else {
                clearContent();
                failureMessage("No record found for ID Number# " + payroll.getIdNumber());
            }

        });
    }
    private void viewDepartmentPayroll(){
        clearContent();
        departmentDropdown();

        payrollDropdown.addActionListener(e -> {
            payroll.setDepartmentCode(Objects.requireNonNull(payrollDropdown.getSelectedItem()).toString());
            clearContent();
            addHeaderRowCentered();

            java.util.List<String> employees = payroll.viewAllEmployees(Path.of("employees.txt"), false);

            if (payroll.viewEmployeePayroll(path)){
                for (int grid = 0; grid < employees.size(); grid++){
                    payroll.setIdNumber(employees.get(grid));
                    payroll.viewEmployeePayroll(path);
                    addLabelsBasedOnOptionCentered(grid);
                }
            }else {
                clearContent();
                failureMessage("No records found for Department Code# " + payroll.getDepartmentCode());
            }


            refreshUi();
        });
    }

    private void addHeaderRowCentered() {
        clearContent();

        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.insets = new Insets(10, 10, 10, 10);
        headerConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add labels for header row.
        JLabel dateLabel = new JLabel("Date of Transaction:");
        JLabel chequeLabel = new JLabel("Cheque No.:");
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel departmentCodeLabel = new JLabel("Department Code:");
        JLabel positionLabel = new JLabel("Position/Job Title:");
        JLabel hoursWorkedLabel = new JLabel("Hours Worked:");
        JLabel regularPayLabel = new JLabel("Regular Pay:");
        JLabel overtimePayLabel = new JLabel("Overtime Pay:");
        JLabel grossPayLabel = new JLabel("Gross Pay:");
        JLabel netPayLabel = new JLabel("Net Pay:");
        JLabel incomeTaxLabel = new JLabel("Income Tax:");
        JLabel nisTaxLabel = new JLabel("NIS Tax:");
        JLabel eduTaxLabel = new JLabel("EduTax:");


        // Adjust gridx values for each label
        contentPanel.add(dateLabel, headerConstraints);
        headerConstraints.gridx = 1;
        contentPanel.add(chequeLabel, headerConstraints);
        headerConstraints.gridx = 2;
        contentPanel.add(idNumberLabel, headerConstraints);
        headerConstraints.gridx = 3;
        contentPanel.add(firstNameLabel, headerConstraints);
        headerConstraints.gridx = 4;
        contentPanel.add(lastNameLabel, headerConstraints);
        headerConstraints.gridx = 5;
        contentPanel.add(departmentCodeLabel, headerConstraints);
        headerConstraints.gridx = 6;
        contentPanel.add(positionLabel, headerConstraints);
        headerConstraints.gridx = 7;
        contentPanel.add(hoursWorkedLabel, headerConstraints);
        headerConstraints.gridx = 8;
        contentPanel.add(regularPayLabel, headerConstraints);
        headerConstraints.gridx = 9;
        contentPanel.add(overtimePayLabel, headerConstraints);
        headerConstraints.gridx = 10;
        contentPanel.add(grossPayLabel, headerConstraints);
        headerConstraints.gridx = 11;
        contentPanel.add(netPayLabel, headerConstraints);
        headerConstraints.gridx = 12;
        contentPanel.add(incomeTaxLabel, headerConstraints);
        headerConstraints.gridx = 13;
        contentPanel.add(nisTaxLabel, headerConstraints);
        headerConstraints.gridx = 14;
        contentPanel.add(eduTaxLabel, headerConstraints);
    }
    private void addLabelsBasedOnOptionCentered(int grid) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        labelConstraints.insets = new Insets(10, 10, 10, 10);
        labelConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add centered labels dynamically
        JLabel dateLabel = new JLabel(String.valueOf(payroll.getProcessedDate()));
        JLabel chequeLabel = new JLabel(payroll.getChequeNumber());
        JLabel idNumberLabel = new JLabel(payroll.getIdNumber());
        JLabel firstNameLabel = new JLabel(payroll.getFirstName());
        JLabel lastNameLabel = new JLabel(payroll.getLastName());
        JLabel departmentCodeLabel = new JLabel(payroll.getDepartmentCode());
        JLabel positionLabel = new JLabel(payroll.getPosition());
        JLabel hoursWorkedLabel = new JLabel(String.valueOf(payroll.getHoursWorked()));
        JLabel regularPayLabel = new JLabel(String.valueOf(payroll.getRegularPay()));
        JLabel overtimePayLabel = new JLabel(String.valueOf(payroll.getOvertimePay()));
        JLabel grossPayLabel = new JLabel(String.valueOf(payroll.getGrossPay()));
        JLabel netPayLabel = new JLabel(String.valueOf(payroll.getNetPay()));
        JLabel incomeTaxLabel = new JLabel(String.valueOf(payroll.getIncomeTax()));
        JLabel nisTaxLabel = new JLabel(String.valueOf(payroll.getNisTax()));
        JLabel eduTaxLabel = new JLabel(String.valueOf(payroll.getEduTax()));

        // Add labels to contentPanel
        contentPanel.add(dateLabel, labelConstraints);
        labelConstraints.gridx = 1;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(chequeLabel, labelConstraints);

        labelConstraints.gridx = 2;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(idNumberLabel, labelConstraints);

        labelConstraints.gridx = 3;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(firstNameLabel, labelConstraints);

        labelConstraints.gridx = 4;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(lastNameLabel, labelConstraints);

        labelConstraints.gridx = 5;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(departmentCodeLabel, labelConstraints);

        labelConstraints.gridx = 6;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(positionLabel, labelConstraints);

        labelConstraints.gridx = 7;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(hoursWorkedLabel, labelConstraints);

        labelConstraints.gridx = 8;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(regularPayLabel, labelConstraints);

        labelConstraints.gridx = 9;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(overtimePayLabel, labelConstraints);

        labelConstraints.gridx = 10;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(grossPayLabel, labelConstraints);

        labelConstraints.gridx = 11;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(netPayLabel, labelConstraints);

        labelConstraints.gridx = 12;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(incomeTaxLabel, labelConstraints);

        labelConstraints.gridx = 13;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(nisTaxLabel, labelConstraints);

        labelConstraints.gridx = 14;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(eduTaxLabel, labelConstraints);

        refreshUi();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayrollGUI::new);
    }
}
