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

    public GUI() {
        setTitle("Departments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());

        createDepartmentButtons();

        add(buttonPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

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
//        addRecordButton.addActionListener(e -> showDepartmentView("Add new record"));
        updateRecordButton.addActionListener(e -> showDepartmentView(false));
        viewSingleRecordButton.addActionListener(e -> showDepartmentView(false));
        viewAllRecordsButton.addActionListener(e -> showDepartmentView(true));
    }

    private void showDepartmentView(boolean isIncrement) {
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
        departmentDropdown = new JComboBox<>(department.viewAllDepartments(Path.of("departments.txt")).toArray(new String[0]));
        GridBagConstraints dropdownConstraints = new GridBagConstraints();
        dropdownConstraints.gridx = 0;
        dropdownConstraints.gridy = 1;
        dropdownConstraints.anchor = GridBagConstraints.CENTER; // Center the dropdown
        contentPanel.add(departmentDropdown, dropdownConstraints);

        // Add action listener to department dropdown
        departmentDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear existing components from contentPanel
                contentPanel.removeAll();
                contentPanel.revalidate();
                contentPanel.repaint();

                // Add header labels centered
                addHeaderRowCentered();
                department.setDepartmentCode(Objects.requireNonNull(departmentDropdown.getSelectedItem()).toString());
                department.viewSingleDepartment(Path.of("departments.txt"));

                // Dynamically create and add centered labels based on selected option
                addLabelsBasedOnOptionCentered(isIncrement);
            }
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

    private void addLabelsBasedOnOptionCentered(boolean isIncrement) {
        int gridx = 0;
        GridBagConstraints labelConstraints = new GridBagConstraints();

        if (isIncrement){
            for (String ignored :
                    department.viewAllDepartments(Path.of("departments.txt")).toArray(new String[0])) {

                labelConstraints.gridx = gridx;
                labelConstraints.gridy = 1;
                labelConstraints.insets = new Insets(10, 10, 10, 10);
                labelConstraints.anchor = GridBagConstraints.CENTER;

                // Create and add centered labels dynamically
                JLabel departmentCodeLabel = new JLabel(department.getDepartmentCode());
                JLabel departmentNameLabel = new JLabel(department.getDepartmentName());

                // Add labels to contentPanel
                contentPanel.add(departmentCodeLabel, labelConstraints);
                labelConstraints.gridx = gridx+1;
                contentPanel.add(departmentNameLabel, labelConstraints);

                // Refresh the UI
                contentPanel.revalidate();
                contentPanel.repaint();

                gridx++;
            }
        }else {
            labelConstraints.gridx = gridx;
            labelConstraints.gridy = 1;
            labelConstraints.insets = new Insets(10, 10, 10, 10);
            labelConstraints.anchor = GridBagConstraints.CENTER;

            // Create and add centered labels dynamically
            JLabel departmentCodeLabel = new JLabel(department.getDepartmentCode());
            JLabel departmentNameLabel = new JLabel(department.getDepartmentName());

            // Add labels to contentPanel
            contentPanel.add(departmentCodeLabel, labelConstraints);
            labelConstraints.gridx = gridx+1;
            contentPanel.add(departmentNameLabel, labelConstraints);

            // Refresh the UI
            contentPanel.revalidate();
            contentPanel.repaint();
        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
