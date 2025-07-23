package com.spakborhills.view.gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameLabel extends JLabel {

    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    private static final Color SHADOW_COLOR = new Color(48, 80, 80);

    public GameLabel(String text) {
        super(text);
        setupLabel();
    }

    private void setupLabel() {
        // Basic label setup
        setForeground(TEXT_COLOR);

        // Create pixel-style font
        setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        // Create 8-bit style border (optional, can be removed if not needed)
        setBorder(createPixelBorder());
    }

    private CompoundBorder createPixelBorder() {
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(2, 5, 2, 5), // Padding
                BorderFactory.createLineBorder(SHADOW_COLOR, 2)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Draw text with shadow effect
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int x = getInsets().left; // Start at the left inset/padding
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw text shadow
        g2d.setColor(SHADOW_COLOR);
        g2d.drawString(getText(), x + 1, y + 1);

        // Draw main text
        g2d.setColor(getForeground());
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }
}