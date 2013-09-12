package kuvankasittely.ui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Paneeli extends JPanel {
    
    BufferedImage kuva;
    
    public Paneeli() {
        super.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics graphics) { 
         super.paintComponent(graphics); // ilman tätä super.setBackground(Color.WHITE) ei toimi ?
         graphics.drawImage(kuva, 0, 0, null);
    }
 
    public void asetaKuva(BufferedImage kuva) {
        this.kuva = kuva;
    }
    
    public BufferedImage getKuva() {
        return kuva;
    }
    
}
