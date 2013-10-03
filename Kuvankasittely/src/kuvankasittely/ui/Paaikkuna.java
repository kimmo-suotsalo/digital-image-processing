package kuvankasittely.ui;

import java.awt.*;
import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Paaikkuna implements Ikkuna {
    
    Ikkuna edeltaja, seuraaja;
    JFrame kehys;
    JPanel painikerivi;
    Paneeli paneeli;
    
    public Paaikkuna(String otsikko) {
        kehys = new JFrame(otsikko);
        teeAlustukset();
        kehys.pack();
    }

    @Override
    public void setKehys(JFrame kehys) {
        this.kehys = kehys;
    }

    @Override
    public void setEdeltaja(Ikkuna edeltaja) {
        this.edeltaja = edeltaja;
    }    
    
    @Override
    public void setSeuraaja(Ikkuna seuraaja) {
        this.seuraaja = seuraaja;
    }    

    @Override
    public void setPaneeli(Paneeli paneeli) {
        this.paneeli = paneeli;
    }        
    
    @Override
    public JFrame getKehys() {
        return kehys;
    }

    @Override
    public Ikkuna getEdeltaja() {
        return edeltaja;
    }

    @Override
    public Ikkuna getSeuraaja() {
        return seuraaja;
    }       

    @Override
    public Paneeli getPaneeli() {
        return this.paneeli;
    }

    @Override
    public void paivitaSuodin(Suodin suodin) {
        return;
    }
    
    @Override
    public void nayta() {
        kehys.setVisible(true);
    }

    @Override
    public void piilota() {
        kehys.setVisible(false);
    }

    @Override
    public void lisaaKuuntelijat(Logiikka logiikka) {
        int painikkeidenMaara = painikerivi.getComponentCount();
        for (int tunnus = 0; tunnus < painikkeidenMaara; tunnus++) {
            JButton painike = (JButton) painikerivi.getComponent(tunnus);
            painike.addActionListener( new Kuuntelija(logiikka, painike, paneeli, edeltaja, seuraaja) );    
        }
    }
    
    @Override
    public void teeAlustukset() {
        asettele(200, 100, 800, 657, new BorderLayout() );
        lisaaPaneeli(BorderLayout.CENTER);
        lisaaPainikerivi( BorderLayout.SOUTH, new FlowLayout() );
        String[] painikkeidenNimet = {"Lataa kuva", "Tummenna", "Vaalenna", "Suodata", "Palauta", "Tallenna kuva", "Lopeta"};
        for (String painikkeenNimi : painikkeidenNimet) {
            painikerivi.add( new JButton(painikkeenNimi) );
        }
    }
    
    private void asettele(int xSijainti, int ySijainti, int leveys, int korkeus, LayoutManager layout) {
        kehys.setLocation(xSijainti, ySijainti);
        kehys.setPreferredSize( new Dimension(leveys, korkeus) );
        kehys.setLayout(layout);
        kehys.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    private void lisaaPaneeli(String sijainti) {        
        paneeli = new Paneeli();
        Container ikkunanSisalto = kehys.getContentPane();
        ikkunanSisalto.add(paneeli, sijainti);
    }
    
    private void lisaaPainikerivi(String sijainti, LayoutManager layout) {
        painikerivi = new JPanel( layout );
        kehys.getContentPane().add(painikerivi, sijainti);
    }
    
}
