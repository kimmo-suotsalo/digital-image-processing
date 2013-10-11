package kuvankasittely.ui;

import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * Ikkunoiden yleiset palvelut määrittävä rajapinta.
 * 
 * @author      kimpe
 * @version     6.0
 * @since       2013-10-11
 */

public interface Ikkuna {
    
    /**
     * Alustaa ikkunan käyttöä varten.
     */
    
    void teeAlustukset();

    /**
     * Lisää tarvittavat tapahtumankuuntelijat ikkunan komponenteille.
     * 
     * @param logiikka Ohjelman toimintalogiikka.
     */
    
    void lisaaKuuntelijat(Logiikka logiikka);
    
    /**
     * Asettaa ikkunaan liittyvän kehyksen.
     * 
     * @param kehys Käytettävä kehys.
     */    
    
    void setKehys(JFrame kehys);
    
    /**
     * Asettaa ikkunan edeltäjän.
     * 
     * @param edeltaja Toinen ikkuna, josta käsin tämä ikkuna on avattu.
     */
    
    void setEdeltaja(Ikkuna edeltaja);        
    
    /**
     * Asettaa ikkunan seuraajan.
     * 
     * @param seuraaja Toinen ikkuna, joka on avattu tästä ikkunasta käsin.
     */
    
    void setSeuraaja(Ikkuna seuraaja);        
    
    /**
     * Asettaa ikkunaan liittyvän paneelin.
     * 
     * @param paneeli Paneeli, johon voi piirtää.
     */
    
    void setPaneeli(Paneeli paneeli);

    /**
     * Palauttaa kehyksen.
     * 
     * @return Ikkunaan liittyvä kehys.
     */
    
    JFrame getKehys();
    
    /**
     * Palauttaa ikkunan edeltäjän.
     * 
     * @return Ikkuna, josta käsin tämä ikkuna on avattu.
     */
    
    Ikkuna getEdeltaja();
    
    /**
     * Palauttaa ikkunan seuraajan.
     * 
     * @return Ikkuna, joka on avattu tästä ikkunasta käsin.
     */
    
    Ikkuna getSeuraaja();
    
    /**
     * Palauttaa paneelin.
     * 
     * @return Ikkunaan liittyvä paneeli.
     */
    
    Paneeli getPaneeli();
    
    /**
     * Päivittää ikkunaan liittyvän suotimen.
     * 
     * @param suodin Uusi suodin. Korvaa aiemman suotimen, jos sellainen on olemassa.
     */
    
    void paivitaSuodin(Suodin suodin);
    
    /**
     * Asettaa ikkunan näkyville.
     */
    
    void nayta();    
    
    /**
     * Poistaa ikkunan näkyvistä.
     */
    
    void piilota();
    
}