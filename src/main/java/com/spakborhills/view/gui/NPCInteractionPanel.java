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

    GamePanel gp;

    public NPCInteractionPanel(MainFrame mainFrame, GamePanel gp, String npcName) {
        this.gp = gp;
        this.store = new Store();

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/backgrounds/NPCInteractBG/" + npcName + "_BG.png")));
        } catch (IOException e) {
            System.err.println("Failed to load NPC background: " + e.getMessage());
            // Jika gambar tidak bisa dimuat, set background JPanel menjadi warna solid
            setBackground(Color.black);
        }

        this.setPreferredSize(new Dimension(576, 576));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);
        this.setOpaque(false);

        // bikin 4 button home screen

        JButton proposeButton = new GameButton("Proposing");
        proposeButton.setBounds(160, 240, 250, 50);
        this.add(proposeButton);

        JButton marryButton = new GameButton("Marrying");
        marryButton.setBounds(160, 295, 250, 50);
        this.add(marryButton);

        JButton chatButton = new GameButton("Chatting");
        chatButton.setBounds(160, 350, 250, 50);
        this.add(chatButton);

        JButton giftButton = new GameButton("Gifting");
        giftButton.setBounds(160, 405, 250, 50);
        this.add(giftButton);

        if (gp.getCurrentNPC().getName().equals("Emily")) {
            JButton storeButton = new GameButton("Store");
            storeButton.setBounds(160, 460, 250, 50);
            this.add(storeButton);

            storeButton.addActionListener(e -> {store.buyItem();});
        }

        JButton backGameButton = new GameButton("Back to game");
        backGameButton.setBounds(15, 10, 157, 25);
        this.add(backGameButton);

        proposeButton.addActionListener(e -> {gp.getPlayerController().proposing(gp.getCurrentNPC());});
        marryButton.addActionListener(e -> {gp.getPlayerController().marrying(gp.getCurrentNPC());}); //belum implement
        chatButton.addActionListener(e -> System.out.println("Chatting with NPC " + gp.getCurrentNPC().getName())); //belum implement
        giftButton.addActionListener(e -> {gp.getPlayerController().gifting(gp.getCurrentNPC(), gp.getPlayer().getItemHeld());});
        backGameButton.addActionListener(e -> {mainFrame.switchPanel("game");});
    }

    public void showTemporaryPopUpNPC(String imagePath, int durationMillis, int offset) {
        JWindow popup = new JWindow(SwingUtilities.getWindowAncestor(this));

        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.err.println("Error: Image not found at " + imagePath);
                return;
            }
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding untuk gambar

            popup.add(label, BorderLayout.CENTER);
            popup.pack();

            int x = this.getLocationOnScreen().x + (this.getWidth() - popup.getWidth()) / 2;
            int y = (this.getLocationOnScreen().y + (this.getHeight() - popup.getHeight()) / 2) - offset;
            popup.setLocation(x, y);

            popup.setVisible(true);

            Timer timer = new Timer(durationMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                    ((Timer)e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            System.err.println("Failed to load or display image: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }
}
