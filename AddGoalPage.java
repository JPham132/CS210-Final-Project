import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddGoalPage
{
    private JFrame frame;
    private JTextField goalNameField, targetField, currentMoneyField;
    private JList<String> goalList;
    private DefaultListModel<String> goalListModel;
    private GoalManager goalManager;
    private HomeScreen homeScreen;    // reference for the home screen

    public AddGoalPage(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
        goalManager = new GoalManager();
        goalListModel = new DefaultListModel<>();

        frame = new JFrame("Add Goal");
        frame.setBounds(100, 100, 400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel for goal input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel goalNameLabel = new JLabel("Goal Name:");
        goalNameField = new JTextField(10);
        JLabel targetLabel = new JLabel("Target Amount:");
        targetField = new JTextField(10);
        JLabel currentMoneyLabel = new JLabel("Current Money:");
        currentMoneyField = new JTextField(10);


        JButton addGoalButton = new JButton("Add Goal");
        addGoalButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addGoalButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String name = goalNameField.getText();
                double target = Double.parseDouble(targetField.getText());
                double currentMoney = Double.parseDouble(currentMoneyField.getText());
                Goal newGoal = new Goal(name, target, currentMoney);
                goalManager.addGoal(newGoal);

                updateGoalList();
            }
        });

        // Add components to the input panel
        inputPanel.add(goalNameLabel);
        inputPanel.add(goalNameField);
        inputPanel.add(targetLabel);
        inputPanel.add(targetField);
        inputPanel.add(currentMoneyLabel);
        inputPanel.add(currentMoneyField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addGoalButton);

        // Back Button to return to the HomeScreen
        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                homeScreen.getFrame().setVisible(true);
            }
        });

        // Add the back button to the frame
        JPanel backPanel = new JPanel(new FlowLayout());
        backPanel.add(backButton);

        goalList = new JList<>(goalListModel);
        goalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        goalList.setFont(new Font("Arial", Font.PLAIN, 14));

        goalList.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int index = goalList.getSelectedIndex();
                if (index != -1)
                {
                    String goalName = goalList.getSelectedValue();
                    Goal selectedGoal = goalManager.getGoal(goalName);
                    if (selectedGoal != null)
                    {
                        new GoalProgressScreen(selectedGoal);
                    }
                }
            }
        });

        JScrollPane goalListScrollPane = new JScrollPane(goalList);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(goalListScrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(backPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void updateGoalList()
    {
        goalListModel.clear();
        for (Goal goal : goalManager.getGoals())
        {
            goalListModel.addElement(goal.getName());
        }
    }

    public JFrame getFrame()
    {
        return frame;
    }

    // Inner class to show the goal progress screen
    public class GoalProgressScreen
    {
        private JFrame progressFrame;
        private JProgressBar progressBar;
        private JLabel goalInfoLabel;
        private JTextField addMoneyField;

        public GoalProgressScreen(Goal goal) {
            progressFrame = new JFrame("Goal Progress");
            progressFrame.setBounds(100, 100, 450, 350);
            progressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            progressFrame.setLayout(new BorderLayout());

            // Progress Bar
            progressBar = new JProgressBar(0, 100);
            progressBar.setValue((int) goal.calculateProgress());
            progressBar.setStringPainted(true);
            goalInfoLabel = new JLabel(goal.getName() + " - Progress: " + (int) goal.calculateProgress() + "%");
            goalInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Panel for Adding Money
            JPanel addMoneyPanel = new JPanel(new FlowLayout());
            JLabel addMoneyLabel = new JLabel("Add Money: ");
            addMoneyField = new JTextField(10);
            JButton addMoneyButton = new JButton("Add Money");

            addMoneyButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String amountInput = addMoneyField.getText();
                    try
                    {
                        double amountToAdd = Double.parseDouble(amountInput);
                        goal.updateProgress(amountToAdd);
                        progressBar.setValue((int) goal.calculateProgress());
                        goalInfoLabel.setText(goal.getName() + " - Progress: " + (int) goal.calculateProgress() + "%");
                        addMoneyField.setText("");
                    } catch (NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(progressFrame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            addMoneyPanel.add(addMoneyLabel);
            addMoneyPanel.add(addMoneyField);
            addMoneyPanel.add(addMoneyButton);

            // Remove Goal Button
            JButton removeGoalButton = new JButton("Remove Goal");
            removeGoalButton.setFont(new Font("Arial", Font.PLAIN, 14));
            removeGoalButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    goalManager.removeGoal(goal.getName());
                    updateGoalList();
                    progressFrame.dispose();
                }
            });

            // Back Button
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    progressFrame.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(backButton);
            buttonPanel.add(removeGoalButton);

            progressFrame.getContentPane().add(goalInfoLabel, BorderLayout.NORTH);
            progressFrame.getContentPane().add(progressBar, BorderLayout.CENTER);
            progressFrame.getContentPane().add(addMoneyPanel, BorderLayout.EAST);
            progressFrame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
            progressFrame.setVisible(true);
        }
    }
}