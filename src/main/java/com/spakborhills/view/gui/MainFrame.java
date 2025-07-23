package com.spakborhills.view.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    JPanel mainPanel;

    public MainFrame() {
        setTitle("Spakbor Hills");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        HomePanel homePanel = new HomePanel(this);
        LoginPanel loginPanel = new LoginPanel(this);
        GamePanel gamePanel = new GamePanel(this, loginPanel);
        CreditPanel creditPanel = new CreditPanel(this);
        HelpPanel helpPanel = new HelpPanel(this);

        mainPanel.add(homePanel, "home");
        mainPanel.add(loginPanel, "login");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(creditPanel, "credit");
        mainPanel.add(helpPanel, "help");


        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
        System.out.println("Switching to " + panelName);

        if(panelName.equals("game")) {
            GamePanel gamePanel = (GamePanel) mainPanel.getComponent(2);
            gamePanel.startGame();
            gamePanel.requestFocusInWindow();
        }
    }
}
