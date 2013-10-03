package kuvankasittely.ui;

import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Kayttoliittyma implements Runnable {

    private Ikkuna paaikkuna;
    private Ikkuna suodatusikkuna;
    private Logiikka logiikka;
    
    public Kayttoliittyma(Logiikka logiikka) {        
        this.paaikkuna = new Paaikkuna("Kuvankäsittelyn harjoitusohjelma");
        this.suodatusikkuna = new Suodatusikkuna("Kuvan suodatus");
        this.logiikka = logiikka;
    }

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