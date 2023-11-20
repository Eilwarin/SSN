//Created by Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon
package SSN;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;

public class RatesGUI extends JFrame {

    private final JPanel buttonPanel;
    private final JPanel contentPanel;
    private JComboBox<String> positionsDropdown;
    private final PositionRates positionRates = new PositionRates();
    private final Path path = Path.of("rates.txt");

    public RatesGUI() {
        setTitle("Position Pay Rates");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createRateButtons();
        addBackButton();

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Set frame properties
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createRateButtons() {
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
        addRecordButton.addActionListener(e -> registerRate(true));
        updateRecordButton.addActionListener(e -> updateRate());
        viewSingleRecordButton.addActionListener(e -> showRate());
        viewAllRecordsButton.addActionListener(e -> showDepartmentRates());
        removeRecordButton.addActionListener(e -> removeRate());
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
    private void ratesDropdown(){
        // Create and add label for position selection
        JLabel selectLabel = new JLabel("Select a Rate ID from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> rateIdNumbers = positionRates.viewAllDepartmentRates(path, true);
        if (rateIdNumbers.isEmpty()){
            JOptionPane.showMessageDialog(this, "There are no records available.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
        }else {
            positionsDropdown = new JComboBox<>(rateIdNumbers.toArray(new String[0]));
            GridBagConstraints dropdownConstraints = new GridBagConstraints();
            dropdownConstraints.gridx = 0;
            dropdownConstraints.gridy = 1;
            dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
            contentPanel.add(positionsDropdown, dropdownConstraints);
        }
    }

    private boolean validId(String id){
        try {
            Double.parseDouble(id);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    private boolean validRate(String rate){
        try {
            Double.parseDouble(rate);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }

    private void departmentDropdown(){
        // Create and add label for Department selection
        JLabel selectLabel = new JLabel("Select a Department Code from the drop-down list.");
        GridBagConstraints selectLabelConstraints = new GridBagConstraints();
        selectLabelConstraints.gridx = 0;
        selectLabelConstraints.gridy = 0;
        selectLabelConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectLabel, selectLabelConstraints);

        java.util.List<String> departments = positionRates.viewAllDepartments(Path.of("departments.txt"));
        if(departments.isEmpty()){
            JOptionPane.showMessageDialog(this, "There are no records available.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
        }else {
            positionsDropdown = new JComboBox<>(departments.toArray(new String[0]));
            GridBagConstraints dropdownConstraints = new GridBagConstraints();
            dropdownConstraints.gridx = 0;
            dropdownConstraints.gridy = 1;
            dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
            contentPanel.add(positionsDropdown, dropdownConstraints);
        }

    }
    private void registerRate(boolean refresh){
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField positionId;
        JTextField positionTitle;
        JTextField positionPayRate;

        if (refresh){
            positionId = new JTextField(15);
            positionTitle = new JTextField(15);
            positionPayRate = new JTextField(15);
        }else {
            positionId = new JTextField(positionRates.getPositionId(), 15);
            positionTitle = new JTextField(positionRates.getPosition(), 15);
            positionPayRate = new JTextField(positionRates.getPosition(), 15);
        }

        // Create and add labels for text fields
        JLabel positionIdLabel = new JLabel("Position ID");
        JLabel positionTitleLabel = new JLabel("Position/Job Title:");
        JLabel positionPayRateLabel = new JLabel("Hourly Pay Rate:");

        contentPanel.add(positionIdLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionId, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(positionTitleLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionTitle, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 3;
        contentPanel.add(positionPayRateLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionPayRate, textFieldConstraints);


        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 4; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            // Retrieve values from text fields
            if (validId(positionId.getText()) && positionId.getText().length() == 7){
                positionRates.setDepartmentCode(positionId.getText().substring(0, 4));
                positionRates.setPositionId(positionId.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Invalid ID Number\n(Must be numeric and seven characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                registerRate(true);
            }

            positionRates.setPosition(positionTitle.getText());
            if (validRate(positionPayRate.getText())){
                positionRates.setPositionRegRate(Double.parseDouble(positionPayRate.getText()));
                positionRates.setPositionOtRate(positionRates.getPositionRegRate() * 1.5);
            }else {
                JOptionPane.showMessageDialog(this, "Invalid Pay Rate\n(Must be numeric.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                registerRate(true);
            }

            positionId.setText(null);
            positionTitle.setText(null);
            positionPayRate.setText(null);

            positionRates.fileProcessing(path, positionRates.registeredRates(path));
            JOptionPane.showMessageDialog(this, "Record successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearContent();
            registerRate(true);
        });
        contentPanel.add(submitButton, submitButtonConstraints);

        refreshUi();
    }
    private void updateRate() {
        // Clear existing components from contentPanel
        clearContent();

        // Create and add label for employee selection
        ratesDropdown();

        // Add action listener to employee dropdown
        positionsDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();

            positionRates.setPositionId(Objects.requireNonNull(positionsDropdown.getSelectedItem()).toString());
            positionRates.viewSingleRates(path, false);

            // Dynamically create and add centered labels based on selected option
            addLabelsBasedOnOptionCentered(0);
            // Dynamically create and add text fields with labels based on selected option
            addTextFieldsForUpdate();
        });

        // Refresh the UI
        refreshUi();
    }

    private void addTextFieldsForUpdate(){
        clearContent();

        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 1;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);
        textFieldConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add text fields dynamically
        JTextField positionId = new JTextField(positionRates.getPositionId(), 15);
        JTextField positionTitle = new JTextField(positionRates.getPosition(), 15);
        JTextField positionPayRate = new JTextField(String.valueOf(positionRates.getPositionRegRate()), 15);

        // Create and add labels for text fields
        JLabel positionIdLabel = new JLabel("Position ID");
        JLabel positionTitleLabel = new JLabel("Position/Job Title:");
        JLabel positionPayRateLabel = new JLabel("Hourly Pay Rate:");

        contentPanel.add(positionIdLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionId, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 2;
        contentPanel.add(positionTitleLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionTitle, textFieldConstraints);

        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 3;
        contentPanel.add(positionPayRateLabel, textFieldConstraints);
        textFieldConstraints.gridx = 1;
        contentPanel.add(positionPayRate, textFieldConstraints);


        JButton submitButton = new JButton("Submit");
        GridBagConstraints submitButtonConstraints = new GridBagConstraints();
        submitButtonConstraints.gridx = 0;
        submitButtonConstraints.gridy = 4; // Adjust the y-coordinate based on your layout
        submitButtonConstraints.gridwidth = 2; // Span two columns
        submitButtonConstraints.insets = new Insets(10, 10, 10, 10);
        submitButtonConstraints.anchor = GridBagConstraints.CENTER;

        submitButton.addActionListener(e -> {
            positionRates.viewSingleRates(path, true);
            // Retrieve values from text fields
            if (validId(positionId.getText()) && positionId.getText().length() == 7){
                positionRates.setDepartmentCode(positionId.getText().substring(0, 4));
                positionRates.setPositionId(positionId.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Invalid ID Number\n(Must be numeric and seven characters.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            }

            positionRates.setPosition(positionTitle.getText());
            if (validRate(positionPayRate.getText())){
                positionRates.setPositionRegRate(Double.parseDouble(positionPayRate.getText()));
                positionRates.setPositionOtRate(positionRates.getPositionRegRate() * 1.5);
            }else {
                JOptionPane.showMessageDialog(this, "Invalid Pay Rate\n(Must be numeric.)", "Attention!", JOptionPane.INFORMATION_MESSAGE);
            }

            positionId.setText(null);
            positionTitle.setText(null);
            positionPayRate.setText(null);

            positionRates.fileProcessing(path, positionRates.registeredRates(path));
            JOptionPane.showMessageDialog(this, "Record successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateRate();
        });
        contentPanel.add(submitButton, submitButtonConstraints);

        refreshUi();
    }
    private void removeRate(){
        // Clear existing components from contentPanel
        clearContent();

        // Create and add label for employee selection
        ratesDropdown();

        // Add action listener to department dropdown
        positionsDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            positionRates.setPositionId(Objects.requireNonNull(positionsDropdown.getSelectedItem()).toString());
            positionRates.viewSingleRates(path, true);

            JOptionPane.showMessageDialog(this, "Record successfully removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            removeRate();
        });
    }
    private void showDepartmentRates() {
        clearContent();

        departmentDropdown();

        // Add action listener to department dropdown
        positionsDropdown.addActionListener(e -> {
            // Set the department code immediately
            positionRates.setDepartmentCode(Objects.requireNonNull(positionsDropdown.getSelectedItem()).toString());

            // Clear existing components from contentPanel
            clearContent();

            // Get a list of rates for the selected department
            java.util.List<String> rates = positionRates.viewAllDepartmentRates(path, false);

            if (rates.isEmpty()){
                JOptionPane.showMessageDialog(this, "There are no records for this Department.", "Attention!", JOptionPane.INFORMATION_MESSAGE);
                showDepartmentRates();
            }else {
                addHeaderRowCentered();
                for (int grid = 0; grid < rates.size(); grid++) {
                    positionRates.setPositionId(rates.get(grid));
                    positionRates.viewSingleRates(path, false);
                    addLabelsBasedOnOptionCentered(grid);
                }
            }
            // Refresh the UI
            refreshUi();
        });

        // Refresh the UI
        refreshUi();
    }
    private void showRate() {
        // Clear existing components from contentPanel
        clearContent();

        ratesDropdown();

        // Add action listener to department dropdown
        positionsDropdown.addActionListener(e -> {
            // Clear existing components from contentPanel
            clearContent();

            // Add header labels centered
            addHeaderRowCentered();
            positionRates.setPositionId(Objects.requireNonNull(positionsDropdown.getSelectedItem()).toString());
            positionRates.viewSingleRates(path, false);

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
        JLabel departmentCodeLabel = new JLabel("Department Code");
        JLabel positionIdLabel = new JLabel("Position ID");
        JLabel positionTitleLabel = new JLabel("Position/Job Title");
        JLabel hourlyRateLabel = new JLabel("Hourly Rate");
        JLabel overtimeRateLabel = new JLabel("Overtime Rate");

        // Adjust gridx values for each label
        contentPanel.add(departmentCodeLabel, headerConstraints);
        headerConstraints.gridx = 1;
        contentPanel.add(positionIdLabel, headerConstraints);
        headerConstraints.gridx = 2;
        contentPanel.add(positionTitleLabel, headerConstraints);
        headerConstraints.gridx = 3;
        contentPanel.add(hourlyRateLabel, headerConstraints);
        headerConstraints.gridx = 4;
        contentPanel.add(overtimeRateLabel, headerConstraints);
    }
    private void addLabelsBasedOnOptionCentered(int grid) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        labelConstraints.insets = new Insets(10, 10, 10, 10);
        labelConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add centered labels dynamically
        JLabel departmentCode = new JLabel(positionRates.getDepartmentCode());
        JLabel positionId = new JLabel(positionRates.getPositionId());
        JLabel positionTitle = new JLabel(positionRates.getPosition());
        JLabel hourlyRate = new JLabel(String.valueOf(positionRates.getPositionRegRate()));
        JLabel overtimeRate = new JLabel(String.valueOf(positionRates.getPositionOtRate()));

        // Add labels to contentPanel
        contentPanel.add(departmentCode, labelConstraints);
        labelConstraints.gridx = 1;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(positionId, labelConstraints);

        labelConstraints.gridx = 2;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(positionTitle, labelConstraints);

        labelConstraints.gridx = 3;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(hourlyRate, labelConstraints);

        labelConstraints.gridx = 4;
        labelConstraints.gridy = grid * 2 + 1; // Increase y-coordinate for each set
        contentPanel.add(overtimeRate, labelConstraints);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RatesGUI::new);
    }
}
