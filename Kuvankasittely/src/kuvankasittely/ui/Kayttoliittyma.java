package kuvankasittely.ui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import kuvankasittely.logic.*;

public class Kayttoliittyma implements Runnable {

    private JFrame paaikkuna;
    private Logiikka logiikka;
    private Paneeli paneeli;
    
    public Kayttoliittyma(Logiikka logiikka) {
        this.paaikkuna = new JFrame("Kuvankäsittelyn harjoitusohjelma");
        this.logiikka = logiikka;
        this.paneeli = new Paneeli();
    }
    
    @Override
    public void run() {
        teeAlustukset(paaikkuna);
        asettele(paaikkuna);
        lisaaPainikkeet(paaikkuna);
        nayta(paaikkuna);
    }
    
    public JFrame getPaaikkuna() {
        return paaikkuna;
    }
    
    public Logiikka getLogiikka() {
        return logiikka;
    }
    
    public Paneeli getPaneeli() {
        return paneeli;
    }
    
    private void teeAlustukset(JFrame ikkuna) {
        ikkuna.setLocation(400, 300);
        ikkuna.setPreferredSize( new Dimension(1300, 620) );
        ikkuna.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void asettele(JFrame ikkuna) {
        Container ikkunanSisalto = ikkuna.getContentPane();
        ikkunanSisalto.setLayout( new BoxLayout(ikkunanSisalto, BoxLayout.X_AXIS) );
        ikkunanSisalto.add(paneeli);
    }
    
    private void lisaaPainikkeet(JFrame ikkuna) {
        ArrayList<JButton> painikkeet = new ArrayList<>();
        painikkeet.add( new JButton("Lataa kuva") );
        painikkeet.add( new JButton("Tummenna") );
        painikkeet.add( new JButton("Vaalenna") );
        painikkeet.add( new JButton("Tallenna kuva") );
        painikkeet.add( new JButton("Lopeta") );
        for (JButton painike : painikkeet) {
            painike.addActionListener( new Kuuntelija(painike, logiikka, paneeli) );
            ikkuna.getContentPane().add(painike);
        }
    }
    
    private void nayta(JFrame ikkuna) {
        ikkuna.pack();
        ikkuna.setVisible(true);
    }
    
}
