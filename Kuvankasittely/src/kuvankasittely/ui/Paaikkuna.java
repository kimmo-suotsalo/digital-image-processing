package kuvankasittely.ui;

import java.awt.*;
import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * Ohjelman pääikkuna.
 * 
 * @author      kimpe
 * @version     6.0
 * @since       2013-10-11
 */

public class Paaikkuna implements Ikkuna {
    
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
     * Paneeli, johon käsiteltävä kuva piirretään.
     */        
    
    private Paneeli paneeli;
    
    /**
     * Luo uuden pääikkunan.
     * 
     * @param otsikko Ikkunan yläreunassa näkyvä merkkijono.
     */
    
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

    /**
     * Lisää painikkeille tapahtumankuuntelijat.
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
     * Alustaa ikkunan halutun kokoiseksi, määrittää sen sijainnin ja
     * lisää tarvittavat komponentit. 
     */
    
    @Override
    public void teeAlustukset() {
        asettele(200, 100, 1000, 750, new BorderLayout() );
        lisaaPaneeli(BorderLayout.CENTER);
        lisaaPainikerivi( BorderLayout.SOUTH, new FlowLayout() );
        String[] painikkeidenNimet = {"Lataa kuva", "Tummenna", "Vaalenna", "Suodata", "Palauta", "Tallenna kuva", "Lopeta"};
        for (String painikkeenNimi : painikkeidenNimet) {
            painikerivi.add( new JButton(painikkeenNimi) );
        }
    }
    
    /**
     * Sijoittaa ikkunan annettuun sijaintiin annetun kokoisena, asettaa
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
        kehys.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
     * Luo uuden painikerivin ja liittää sen ikkunaan.
     * 
     * @param sijainti Layoutin tunnistama ikkunan osa.
     * @param layout Ikkunassa käytettävä layout.
     */
    
    private void lisaaPainikerivi(String sijainti, LayoutManager layout) {
        painikerivi = new JPanel( layout );
        Container ikkunanSisalto = kehys.getContentPane();
        ikkunanSisalto.add(painikerivi, sijainti);
    }
    
}
