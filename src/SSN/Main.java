package SSN;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    DepartmentGUI department = new DepartmentGUI();
    EmployeeGUI employee = new EmployeeGUI();
    RatesGUI rates = new RatesGUI();
    PayrollGUI payroll = new PayrollGUI();
    public Main() {
        // Set the title of the window
        setTitle("SSN");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton employeeButton = new JButton("Employee");
        JButton departmentButton = new JButton("Department");
        JButton payrollButton = new JButton("Payroll");

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(employeeButton);
        buttonPanel.add(departmentButton);
        buttonPanel.add(payrollButton);

        // Add the button panel to the content pane
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        // Set the size of the window
        setSize(400, 200);

        pack();
        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(EmployeeGUI::new));
    }
}
