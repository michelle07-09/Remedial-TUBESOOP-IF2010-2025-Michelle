package com.spakborhills.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private BufferedImage background;
    private JTextField playerNameField;
    private JTextField farmNameField;
    private JButton confirmButton;
    private JButton backButton;

    public LoginPanel(MainFrame mainFrame) {
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/assets/backgrounds/home bg new.png"));
        } catch (IOException e) {
            setBackground(Color.WHITE);
        }

        this.setPreferredSize(new Dimension(576, 576));
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        // Player Name Input
        JLabel playerNameLabel = new JLabel("Player Name");
        playerNameLabel.setBounds(270, 195, 100, 25);
        add(playerNameLabel);

        playerNameField = new JTextField(20);
        playerNameField.setBounds(210, 225, 200, 25);
        add(playerNameField);

        // Farm Name Input
        JLabel farmNameLabel = new JLabel("Farm Name");
        farmNameLabel.setBounds(270, 255, 100, 25);
        add(farmNameLabel);

        farmNameField = new JTextField(20);
        farmNameField.setBounds(210, 285, 200, 25);
        add(farmNameField);

        // Confirm Button
        confirmButton = new GameButton("Confirm");
        confirmButton.setBounds(260, 325, 100, 25);
        add(confirmButton);

        // Back to Home Screen Button
        backButton = new GameButton("Back to Home");
        backButton.setBounds(10, 10, 120, 25);
        add(backButton);

        // Action for Confirm Button
        confirmButton.addActionListener(e -> {
            if(getPlayerName().isEmpty() || getFarmName().isEmpty()){
                System.out.println("Player Name or Farm Name cannot be empty!");
                return;
            }
            System.out.println("Player Name: " + getPlayerName());
            System.out.println("Farm Name: " + getFarmName());

            // Switch to GamePanel
            mainFrame.switchPanel("game");
        });

        // Action for Back Button
        backButton.addActionListener(e -> {
            mainFrame.switchPanel("home");
        });
    }

    public String getPlayerName() {
        return playerNameField.getText();
    }

    public String getFarmName() {
        return farmNameField.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }
}
