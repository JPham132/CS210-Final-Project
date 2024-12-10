import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen
{
    private JFrame frame;

    public HomeScreen()
    {
        frame = new JFrame("Home Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton addGoalButton = new JButton("Add Goal");
        addGoalButton.setFont(new Font("Arial", Font.PLAIN, 18));
        addGoalButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddGoalPage addGoalPage = new AddGoalPage(HomeScreen.this);
                frame.setVisible(false);
                addGoalPage.getFrame().setVisible(true);
            }
        });

        JButton trackTransactionButton = new JButton("Track Transactions");
        trackTransactionButton.setFont(new Font("Arial", Font.PLAIN, 18));
        trackTransactionButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                TrackTransactionsPage trackTransactionPage = new TrackTransactionsPage();
                frame.setVisible(false);
                trackTransactionPage.getFrame().setVisible(true);
            }
        });

        // Add buttons to the panel
        buttonPanel.add(addGoalButton);
        buttonPanel.add(trackTransactionButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Welcome to Goal and Transaction Tracker!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public static void main(String[] args)
    {
        new HomeScreen();
    }
}