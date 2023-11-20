package SSN;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        // Set the title of the window
        setTitle("SSN");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
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

        pack();
        // Set the size of the window
        setSize(1280, 720);
        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);

        employeeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(EmployeeGUI::new);
        });
        departmentButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(DepartmentGUI::new);
        });
        payrollButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(PayrollGUI::new);
        });
//        taxInfoButton.addActionListener();
        payRatesButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(RatesGUI::new);
        });
    }



    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(Main::new));
    }
}
