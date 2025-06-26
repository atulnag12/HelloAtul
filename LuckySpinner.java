package org.akn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LuckySpinner extends JFrame {
    private List<String> defaultParticipants = new ArrayList<>(Arrays.asList(
            "Anjana", "Janak", "Rachana", "Sheetal", "Shubham", "Tushar", "Ankita Sharma",
            "Ashok", "Mandar", "Nikhil", "Rinki", "Shahista", "Ankita Govekar", "Swathi",
            "Kaustubh", "Aaron", "Abhijit", "Subodh", "Monal", "Komal", "Sarah",
            "Priyamvada", "Radhika", "Tapas", "Priyanka"
    ));

    private List<String> participants = new ArrayList<>(defaultParticipants);
    private JLabel nameLabel = new JLabel("Press Roll to Start", SwingConstants.CENTER);
    private JLabel countLabel = new JLabel("Remaining: " + defaultParticipants.size(), SwingConstants.CENTER);
    private JButton rollButton = new JButton("🎲 Roll");
    private JButton resetButton = new JButton("🔄 Reset");

    private Timer spinTimer;
    private Random rand = new Random();

    public LuckySpinner() {
        setTitle("🎡 Lucky Spinner Game");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 183, 197);
                Color color2 = new Color(135, 206, 250);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        nameLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(255, 255, 255));
        nameLabel.setForeground(new Color(25, 25, 112));
        nameLabel.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 3));
        nameLabel.setPreferredSize(new Dimension(450, 100));

        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        countLabel.setForeground(new Color(47, 79, 79));
        countLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        rollButton.setFont(new Font("Arial", Font.BOLD, 20));
        rollButton.setBackground(new Color(60, 179, 113));
        rollButton.setForeground(Color.WHITE);
        rollButton.setFocusPainted(false);
        rollButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setBackground(new Color(220, 20, 60));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(rollButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(resetButton);

        backgroundPanel.add(countLabel, BorderLayout.NORTH);
        backgroundPanel.add(nameLabel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        rollButton.addActionListener(e -> startSpin());
        resetButton.addActionListener(e -> resetGame());
    }

    private void startSpin() {
        if (participants.isEmpty()) {
            showStyledDialog("All participants have been chosen.", "Game Over");
            return;
        }

        rollButton.setEnabled(false);
        long startTime = System.currentTimeMillis();
        int spinDuration = 6000;

        spinTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= spinDuration) {
                    spinTimer.stop();
                    String selected = nameLabel.getText();
                    showStyledDialog(selected + " will speak!", "Selected");
                    participants.remove(selected);
                    updateCount();
                    rollButton.setEnabled(true);
                } else {
                    String randomName = participants.get(rand.nextInt(participants.size()));
                    nameLabel.setText(randomName);
                }
            }
        });
        spinTimer.start();
    }

    private void showStyledDialog(String message, String title) {
        JLabel msgLabel = new JLabel(message, SwingConstants.CENTER);
        msgLabel.setFont(new Font("Arial", Font.BOLD, 22));
        msgLabel.setForeground(new Color(0, 102, 204));
        msgLabel.setBackground(Color.WHITE);
        msgLabel.setOpaque(true);
        msgLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2));
        JOptionPane.showMessageDialog(this, msgLabel, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCount() {
        countLabel.setText("Remaining: " + participants.size());
    }

    private void resetGame() {
        participants = new ArrayList<>(defaultParticipants);
        nameLabel.setText("Press Roll to Start");
        updateCount();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LuckySpinner().setVisible(true));
    }
}
