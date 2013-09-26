package kuvankasittely.ui;

import java.awt.*;
import javax.swing.*;
import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     4.1
 * @since       2013-09-26
 */

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
        asettele( paaikkuna, 700, 200, 760, 657, new BorderLayout() );
        lisaaPaneeli(paaikkuna);
        lisaaPainikerivi(paaikkuna);        
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
    
    private void asettele(JFrame ikkuna, int xSijainti, int ySijainti, int leveys, int korkeus, LayoutManager layout) {
        ikkuna.setLocation(xSijainti, ySijainti);
        ikkuna.setPreferredSize( new Dimension(leveys, korkeus) );
        ikkuna.setLayout(layout);
        ikkuna.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        
    }
    
    private void lisaaPaneeli(JFrame ikkuna) {
        Container ikkunanSisalto = ikkuna.getContentPane();
        ikkunanSisalto.add(paneeli, BorderLayout.CENTER);
    }
    
    private void lisaaPainikerivi(JFrame ikkuna) {
        JPanel painikerivi = new JPanel( new FlowLayout() );
        lisaaPainikkeet(painikerivi);
        lisaaKuuntelijat(painikerivi);
        ikkuna.getContentPane().add(painikerivi, BorderLayout.SOUTH);
    }
    
    private void lisaaPainikkeet(JPanel painikerivi) {
        painikerivi.add( new JButton("Lataa kuva") );
        painikerivi.add( new JButton("Tummenna") );
        painikerivi.add( new JButton("Vaalenna") );
        painikerivi.add( new JButton("Suodata") );
        painikerivi.add( new JButton("Tallenna kuva") );
        painikerivi.add( new JButton("Lopeta") );
    }
    
    private void lisaaKuuntelijat(JPanel painikerivi) {
        for ( Component komponentti : painikerivi.getComponents() ) {
            JButton painike = (JButton) komponentti;
            painike.addActionListener( new Kuuntelija(painike, logiikka, paneeli) );
        }
    }
    
    private void nayta(JFrame ikkuna) {
        ikkuna.pack();
        ikkuna.setVisible(true);
    }
    
}
