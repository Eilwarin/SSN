package SSN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {
    private JComboBox<String> dropdown;
    private JPanel labelsPanel;

    public Main(List<String> items) {
        super("Cascading Dropdown Example");

        // Set up the content pane with GridBagLayout
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create dropdown and set list of strings
        dropdown = new JComboBox<>(items.toArray(new String[0]));

        // Add an ActionListener to update the cascading labels
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCascadingLabels();
            }
        });

        // Create panel to hold the dynamically created labels
        labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(0, 1)); // One column for headers, multiple columns for labels

        // Add a header row
        labelsPanel.add(new JLabel("Header"));

        // Add components to the content pane using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(dropdown, gbc);

        gbc.gridx = 1;
        container.add(labelsPanel, gbc);

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateCascadingLabels() {
        // Remove existing labels
        labelsPanel.removeAll();

        // Get the selected item from the dropdown
        String selectedItem = (String) dropdown.getSelectedItem();

        // Add a header row
        labelsPanel.add(new JLabel("Header"));

// Create a label for each item (excluding the selected item)
        for (int i = 0; i < dropdown.getItemCount(); i++) {
            String item = dropdown.getItemAt(i);
            if (!item.equals(selectedItem)) {
                JLabel label = new JLabel(item);
                labelsPanel.add(label);
            }
        }


        // Refresh the panel
        labelsPanel.revalidate();
        labelsPanel.repaint();
    }

    public static void main(String[] args) {
        // Sample list of strings
        List<String> items = List.of("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main(items);
            }
        });
    }
}
