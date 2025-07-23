package com.spakborhills.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CreditPanel extends JPanel {

    private BufferedImage background;
    private MainFrame mainFrame;

    public CreditPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        try {
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/credit_bg.png"));
        } catch (IOException e) {
            System.out.println("Gagal load background credit.");
            setBackground(Color.DARK_GRAY);
        }

        setPreferredSize(new Dimension(576, 576));
        setLayout(null);

        JButton backButton = new GameButton("Back");
        backButton.setBounds(20, 500, 100, 40);
        backButton.addActionListener(e -> mainFrame.switchPanel("home"));
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
