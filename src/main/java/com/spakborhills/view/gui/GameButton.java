package com.spakborhills.view.gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameButton extends JButton {
    private static final Color BUTTON_COLOR = new Color(112, 168, 168);
    private static final Color HIGHLIGHT_COLOR = new Color(192, 224, 224);
    private static final Color SHADOW_COLOR = new Color(48, 80, 80);
    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    
    public GameButton(String text) {
        super(text);
        setupButton();
    }
    
    private void setupButton() {
        // Basic button setup
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(BUTTON_COLOR);
        setForeground(TEXT_COLOR);
        
        // Create pixel-style font
        setFont(new Font("Courier New", Font.BOLD, 13));


        // Create 8-bit style border
        setBorder(createPixelBorder());
        
        // Add hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HIGHLIGHT_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(BUTTON_COLOR);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(createPressedPixelBorder());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(createPixelBorder());
            }
        });
    }
    
    private CompoundBorder createPixelBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SHADOW_COLOR, 2),
            BorderFactory.createLineBorder(HIGHLIGHT_COLOR, 2)
        );
    }
    
    private CompoundBorder createPressedPixelBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HIGHLIGHT_COLOR, 2),
            BorderFactory.createLineBorder(SHADOW_COLOR, 2)
        );
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // Draw button background
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw text with shadow effect
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
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