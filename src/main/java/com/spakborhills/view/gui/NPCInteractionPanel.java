package com.spakborhills.view.gui;

import com.spakborhills.model.game.Store;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class NPCInteractionPanel extends JPanel {

    private BufferedImage background;
    private Store store;
    private GamePanel gp;

    public NPCInteractionPanel(MainFrame mainFrame, GamePanel gp, String npcName) {
        this.gp = gp;
        this.store = new Store();

        // Format nama NPC menjadi nama file
        String npcFilename = getNpcFilename(npcName);

        try {
            background = ImageIO.read(Objects.requireNonNull(
                    getClass().getResource("/assets/backgrounds/NPCInteractBG/" + npcFilename + "_BG.png")
            ));
        } catch (IOException | NullPointerException e) {
            System.err.println("Gagal load background NPC: " + npcName + ". Detail: " + e.getMessage());
            setBackground(Color.BLACK); // fallback
        }

        this.setPreferredSize(new Dimension(576, 576));
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setOpaque(false);
    }

    private String getNpcFilename(String rawNpcName) {
        switch (rawNpcName) {
            case "Mayor Tadi":
            case "MayorTadi":
                return "MayorTadi";
            default:
                return rawNpcName.replace(" ", "");
        }
    }

    public void showImagePopup(String imagePath, int durationMillis, int offset) {
        try {
            JDialog popup = new JDialog();
            popup.setUndecorated(true);
            popup.setBackground(new Color(0, 0, 0, 0));

            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.err.println("Gambar tidak ditemukan: " + imagePath);
                return;
            }

            BufferedImage image = ImageIO.read(imageUrl);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBorder(new EmptyBorder(5, 5, 5, 5));

            popup.add(label, BorderLayout.CENTER);
            popup.pack();

            int x = this.getLocationOnScreen().x + (this.getWidth() - popup.getWidth()) / 2;
            int y = this.getLocationOnScreen().y + (this.getHeight() - popup.getHeight()) / 2 - offset;
            popup.setLocation(x, y);

            popup.setVisible(true);

            Timer timer = new Timer(durationMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                    ((Timer) e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            System.err.println("Gagal menampilkan gambar: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
    }
    private void drawTextButton(Graphics2D g2, String text, int x, int y) {
        int width = 100;
        int height = 50;
        g2.setColor(new Color(255, 255, 255, 200)); // semi-transparent white
        g2.fillRoundRect(x, y, width, height, 15, 15);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, width, height, 15, 15);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2.drawString(text, x + (width - textWidth) / 2, y + (height + textHeight) / 2 - 5);
    }

}
