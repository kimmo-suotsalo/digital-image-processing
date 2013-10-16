package kuvankasittely.ui;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * Kuvan suodatuksessa käytettävä ikkuna.
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class Suodatusikkuna implements Ikkuna {
    
    /**
     * Toinen ikkuna, josta käsin tämä ikkuna on avattu.
     */    
    
    private Ikkuna edeltaja;
    
    /**
     * Toinen ikkuna, joka avataan tästä ikkunasta käsin.
     */        
    
    private Ikkuna seuraaja;
    
    /**
     * Kehys, johon ikkunan komponentit sijoitetaan.
     */    
    
    private JFrame kehys;
    
    /**
     * Ikkunaan liittyvien painikkeiden muodostama kokonaisuus.
     */    
    
    private JPanel painikerivi;
    
    /**
     * Suodinmatriisia esittavä painiketaulukko, joka näytetään käyttäjälle.
     */        
    
    private JPanel taulukko;    
    
    /**
     * Paneeli, johon käsiteltävä kuva piirretään.
     */        
    
    private Paneeli paneeli;
    
    /**
     * Luo uuden suodatusikkunan.
     * 
     * @param otsikko Ikkunan yläreunassa näkyvä merkkijono.
     */
    
    public Suodatusikkuna(String otsikko) {
        kehys = new JFrame(otsikko);
        teeAlustukset();
        kehys.pack();
    }
       
    /** Sijoittaa ikkunan annettuun sijaintiin annetun kokoisena, asettaa
     * layoutin ja määrittää ikkunan sulkemiseen liittyvän toiminnon.
     * 
     * @param xSijainti Ikkunan vasemman yläkulman x-koordinaatti.
     * @param ySijainti Ikkunan vasemman yläkulman y-koordinaatti.
     * @param leveys Ikkunan leveys pikseleinä.
     * @param korkeus Ikkunan korkeus pikseleinä.
     * @param layout Ikkunan layout.
     */
    
    private void asettele(int xSijainti, int ySijainti, int leveys, int korkeus, LayoutManager layout) {
        kehys.setLocation(xSijainti, ySijainti);
        kehys.setPreferredSize( new Dimension(leveys, korkeus) );
        kehys.setLayout(layout);
        kehys.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    @Override
    public Ikkuna getEdeltaja() {
        return edeltaja;
    } 
    
    @Override
    public JFrame getKehys() {
        return kehys;
    }

    @Override    
    public Paneeli getPaneeli() {
        return paneeli;
    }

    @Override
    public Ikkuna getSeuraaja() {
        return seuraaja;
    }       
    
    /** Lisää painikkeille tapahtumankuuntelijat.
     * 
     * @param logiikka Ohjelman toimintalogiikka.
     */    
    
    @Override
    public void lisaaKuuntelijat(Logiikka logiikka) {
        int painikkeidenMaara = painikerivi.getComponentCount();
        for (int tunnus = 0; tunnus < painikkeidenMaara; tunnus++) {
            JButton painike = (JButton) painikerivi.getComponent(tunnus);
            painike.addActionListener( new Kuuntelija(logiikka, painike, paneeli, edeltaja, seuraaja) );    
        }
    }
    
    /**
     * Luo uuden painikerivin ja liittää sen ikkunaan.
     * 
     * @param sijainti Layoutin tunnistama ikkunan osa.
     * @param layout Ikkunassa käytettävä layout.
     */
    
    private void lisaaPainikerivi(String sijainti, LayoutManager layout) {
        painikerivi = new JPanel( layout );
        kehys.getContentPane().add(painikerivi, sijainti);
    }
    
    /**
     * Luo uuden paneelin ja liittää sen ikkunaan.
     * 
     * @param sijainti Layoutin tunnistama merkkijonovakio.
     */
    
    private void lisaaPaneeli(String sijainti) {        
        paneeli = new Paneeli();
        Container ikkunanSisalto = kehys.getContentPane();
        ikkunanSisalto.add(paneeli, sijainti);
    }
        
    /**
     * Luo suodinta esittävän painiketaulukon ja liittää sen ikkunaan.
     * 
     * @param sijainti Layoutin tunnistama ikkunan osa.
     * @param suodin Käytettävä suodin.
     */
    
    private void lisaaTaulukko(String sijainti, Suodin suodin) {
        taulukko = new JPanel( new GridLayout( suodin.getRivienMaara(), suodin.getSarakkeidenMaara() ) );
        for (int rivi = 0; rivi < suodin.getRivienMaara(); rivi++) {
            for (int sarake = 0; sarake < suodin.getSarakkeidenMaara(); sarake++) {
                double solunArvo = suodin.getMatriisi().getAlkio(rivi, sarake);                
                JButton solu = new JButton( new DecimalFormat("####.####").format(solunArvo) );
                solu.setEnabled(false);
                taulukko.add(solu);
            }
        }
        kehys.getContentPane().add(taulukko, sijainti);
    }
    
    @Override
    public void nayta() {
        kehys.setVisible(true);
    }
    
    @Override
    public void paivitaSuodin(Suodin suodin) {
        kehys.getContentPane().remove(kehys.getComponentCount());
        lisaaTaulukko(BorderLayout.CENTER, suodin);
        kehys.pack();
        kehys.repaint();
    }
    
    @Override
    public void piilota() {
        kehys.setVisible(false);
    }
    
    @Override
    public void setEdeltaja(Ikkuna edeltaja) {
        this.edeltaja = edeltaja;
    } 
    
    @Override
    public void setKehys(JFrame kehys) {
        this.kehys = kehys;
    }
        
    @Override
    public void setPaneeli(Paneeli paneeli) {
        this.paneeli = paneeli;
    }
    
    @Override
    public void setSeuraaja(Ikkuna seuraaja) {
        this.seuraaja = seuraaja;
    }    
    
    /** Alustaa ikkunan halutun kokoiseksi, määrittää sen sijainnin ja
     * lisää tarvittavat komponentit. 
     */
    
    @Override
    public void teeAlustukset() {
        asettele(100, 100, 400, 300, new BorderLayout() );
        lisaaPainikerivi( BorderLayout.WEST, new GridLayout(7, 1) );
        lisaaTaulukko( BorderLayout.CENTER, new Suodin("Deltafunktio", 1) );        
        String[] painikkeidenNimet = {"Nollaa", "Deltafunktio", "Alipäästö", "Ylipäästö", "Reunanetsintä", "Suodata", "Sulje"};
        for (String painikkeenNimi : painikkeidenNimet) {
            painikerivi.add( new JButton(painikkeenNimi) );
        }
    }
    
}
