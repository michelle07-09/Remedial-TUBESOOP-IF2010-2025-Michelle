package com.spakborhills.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelpPanel extends JPanel {

    private BufferedImage background;
    private MainFrame mainFrame;

    public HelpPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        try {
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/helppanel.png"));
        } catch (IOException e) {
            System.out.println("Gagal load background help.");
            setBackground(Color.DARK_GRAY);
        }

        setPreferredSize(new Dimension(576, 576));
        setLayout(null);

        bindEscapeKey();
    }

    private void bindEscapeKey() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "goHome");
        getActionMap().put("goHome", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mainFrame.switchPanel("home");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
