package com.spakborhills.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HomePanel extends JPanel{

    private BufferedImage background;

    public HomePanel(MainFrame mainFrame) {
        try{
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/home bg new.png"));
        } catch (IOException e) {
            setBackground(Color.black);
        }

        this.setPreferredSize(new Dimension(576, 576));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        // bikin 4 button home screen

        JButton playButton = new GameButton("Play");
        playButton.setBounds(160, 240, 250, 50);
        this.add(playButton);

        JButton helpButton = new GameButton("Help");
        helpButton.setBounds(160, 295, 250, 50);
        this.add(helpButton);

        JButton creditButton = new GameButton("Credit");
        creditButton.setBounds(160, 350, 250, 50);
        this.add(creditButton);

        JButton exitButton = new GameButton("Exit");
        exitButton.setBounds(160, 405, 250, 50);
        this.add(exitButton);

        //action 4 button home screen
        playButton.addActionListener(e -> mainFrame.switchPanel("login"));
        helpButton.addActionListener(e -> mainFrame.switchPanel("help"));
        creditButton.addActionListener(e -> mainFrame.switchPanel("credit"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }
}