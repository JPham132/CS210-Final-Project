import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class TrackTransactionsPage {
    private JFrame frame;
    private JTextField descriptionField, costField, dateField;
    private JComboBox<String> categoryComboBox;
    private JTextArea categoryTextArea;
    private TransactionManager transactionManager;
    private CategoryManager categoryManager;

    public TrackTransactionsPage()
    {
        transactionManager = new TransactionManager();
        categoryManager = new CategoryManager();

        // Create frame
        frame = new JFrame("Track Transactions");
        frame.setBounds(100, 100, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(10);
        JLabel costLabel = new JLabel("Cost:");
        costField = new JTextField(10);
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        dateField = new JTextField(10);
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Entertainment", "Bills", "Food", "Travel"};
        categoryComboBox = new JComboBox<>(categories);

        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(costLabel);
        inputPanel.add(costField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryComboBox);

        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addTransaction();
            }
        });

        JPanel displayPanel = new JPanel(new BorderLayout());
        JLabel categoryDisplayLabel = new JLabel("Transactions for Selected Category:");
        categoryTextArea = new JTextArea(10, 40);
        categoryTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(categoryTextArea);
        displayPanel.add(categoryDisplayLabel, BorderLayout.NORTH);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(addButton, BorderLayout.CENTER);
        frame.add(displayPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addTransaction()
    {
        String description = descriptionField.getText();
        String costStr = costField.getText();
        String date = dateField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        double cost;

        try
        {
            cost = Double.parseDouble(costStr);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for cost.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Transaction transaction = new Transaction(date, cost, category, description);

        transactionManager.addTransactionAtEnd(transaction);
        categoryManager.addItem(category, transaction);

        updateCategoryDisplay(category);

        descriptionField.setText("");
        costField.setText("");
        dateField.setText("");
    }

    private void updateCategoryDisplay(String category)
    {
        Set<Transaction> transactions = categoryManager.getTransByCategory(category);

        StringBuilder sb = new StringBuilder();
        sb.append("Date            Description               Cost\n");
        sb.append("--------------------------------------------\n");

        for (Transaction transaction : transactions)
        {
            sb.append(transaction.getDate()).append("    ")
                    .append(transaction.getDescription()).append("    $")
                    .append(transaction.getAmount()).append("\n");
        }

        categoryTextArea.setText(sb.toString());
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public static void main(String[] args)
    {
        new TrackTransactionsPage();
    }
}
