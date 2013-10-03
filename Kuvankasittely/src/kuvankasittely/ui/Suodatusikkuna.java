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

public class Suodatusikkuna implements Ikkuna {
    
    Ikkuna edeltaja, seuraaja;
    JFrame kehys;
    JPanel painikerivi, taulukko;    
    Paneeli paneeli;
    
    public Suodatusikkuna(String otsikko) {
        kehys = new JFrame(otsikko);
        teeAlustukset();
        kehys.pack();
    }
       
    @Override
    public void lisaaKuuntelijat(Logiikka logiikka) {
        int painikkeidenMaara = painikerivi.getComponentCount();
        for (int tunnus = 1; tunnus < painikkeidenMaara; tunnus++) {
            JButton painike = (JButton) painikerivi.getComponent(tunnus);
            painike.addActionListener( new Kuuntelija(logiikka, painike, paneeli, edeltaja, seuraaja) );    
        }
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
        return paneeli;
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
    public void teeAlustukset() {
        asettele(1000, 100, 400, 300, new BorderLayout() );        
        lisaaPainikerivi( BorderLayout.WEST, new GridLayout(8,1) );
        lisaaTaulukko( BorderLayout.CENTER, new Suodin("Deltafunktio", 1) );        
        painikerivi.add( new JLabel("  Suotimen valinta") );
        String[] painikkeidenNimet = {"Nollaa", "Deltafunktio", "Alipäästö", "Ylipäästö", "Reunanetsintä", "Suodata", "Sulje"};
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

    @Override
    public void paivitaSuodin(Suodin suodin) {
        kehys.getContentPane().remove(kehys.getComponentCount());
        lisaaTaulukko(BorderLayout.CENTER, suodin);
        kehys.pack();
        kehys.repaint();
    }
    
    private void lisaaTaulukko(String sijainti, Suodin suodin) {
        taulukko = new JPanel( new GridLayout( suodin.getRivienMaara(), suodin.getSarakkeidenMaara() ) );
        for (int rivi = 0; rivi < suodin.getRivienMaara(); rivi++) {
            for (int sarake = 0; sarake < suodin.getSarakkeidenMaara(); sarake++) {
                double arvo = suodin.getMatriisi().getAlkio(rivi, sarake);                
                JButton solu = new JButton("" + arvo);
                solu.setEnabled(false);
                taulukko.add(solu);
            }
        }
        kehys.getContentPane().add(taulukko, sijainti);
    }
    
}
