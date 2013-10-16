package kuvankasittely.ui;

import kuvankasittely.logic.*;

/**
 * Ohjelman graafinen käyttöliittymä.
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class Kayttoliittyma implements Runnable {

    /**
     * Ohjelman pääikkuna.
     */    
    
    private Ikkuna paaikkuna;
    
    /**
     * Kuvan suodatuksessa käytettävä ikkuna.
     */    
    
    private Ikkuna suodatusikkuna;

    /**
     * Ohjelman toimintalogiikka.
     */    
    
    private Logiikka logiikka;
    
    /**
     * Luo uuden käyttöliittymän, johon kuuluu pääikkuna ja suodatusikkuna.
     * 
     * @param logiikka Ohjelman toimintalogiikka.
     */    
    
    public Kayttoliittyma(Logiikka logiikka) {        
        this.paaikkuna = new Paaikkuna("Kuvankäsittelyn harjoitusohjelma");
        this.suodatusikkuna = new Suodatusikkuna("Kuvan suodatus");
        this.logiikka = logiikka;
    }

    /**
     * Käynnistää käyttöliittymän.
     * <p>
     * Asettaa suodatusikkunan pääikkunan seuraajaksi ja pääikkunan suodatusikkunan
     * edeltäjäksi. Lisää molempiin ikkunoihin tapahtumankuuntelijat. Asettaa
     * suodatusikkunan paneeliksi pääikkunan paneelin piirtämistä varten. Asettaa
     * pääikkunan näkyville.
     */    
    
    @Override
    public void run() { 
        paaikkuna.setSeuraaja(suodatusikkuna);
        paaikkuna.lisaaKuuntelijat(logiikka);
        paaikkuna.nayta();
        
        suodatusikkuna.setEdeltaja(paaikkuna);
        suodatusikkuna.setPaneeli( paaikkuna.getPaneeli() );                
        suodatusikkuna.lisaaKuuntelijat(logiikka);
    }
        
}