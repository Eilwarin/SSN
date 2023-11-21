// File: Main.java
// Authors: Jamari Ferguson, Dontray Blackwood, Rajaire Thomas, Alexi Brooks, Rochelle Gordon
package SSN;

// Import statements for necessary Java Swing classes
import javax.swing.*;
import java.awt.*;

// Main class extending JFrame to create the main window
public class Main extends JFrame {

    // Constructor for the Main class
    public Main() {
        // Set the title of the window
        setTitle("SSN");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons for different functionalities
        JButton employeeButton = new JButton("Employee");
        JButton departmentButton = new JButton("Department");
        JButton payrollButton = new JButton("Payroll");
        JButton taxInfoButton = new JButton("Tax Info");
        JButton payRatesButton = new JButton("Pay Rates");

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(employeeButton);
        buttonPanel.add(departmentButton);
        buttonPanel.add(payrollButton);
        buttonPanel.add(taxInfoButton);
        buttonPanel.add(payRatesButton);

        // Add the button panel to the content pane
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        // Automatically adjust the size of the window
        pack();

        // Set the size of the window
        setSize(1280, 720);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the window visible
        setVisible(true);

        // Action listeners for button clicks, disposing of the current window and launching the corresponding GUI
        employeeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(EmployeeGui::new);
        });
        departmentButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(DepartmentGui::new);
        });
        payrollButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(PayrollGui::new);
        });
        taxInfoButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(TaxGui::new);
        });
        payRatesButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(RatesGui::new);
        });
    }

    // Main method to create and display the GUI
    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(Main::new));
    }
}
