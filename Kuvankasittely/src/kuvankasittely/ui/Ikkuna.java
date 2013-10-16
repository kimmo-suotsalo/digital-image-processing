package kuvankasittely.ui;

import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * Ikkunoiden yleiset palvelut määrittävä rajapinta.
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public interface Ikkuna {
    
    /**
     * Palauttaa ikkunan edeltäjän.
     * 
     * @return Ikkuna, josta käsin tämä ikkuna on avattu.
     */
    
    Ikkuna getEdeltaja();
    
    /**
     * Palauttaa kehyksen.
     * 
     * @return Ikkunaan liittyvä kehys.
     */
    
    JFrame getKehys();
 
    /**
     * Palauttaa paneelin.
     * 
     * @return Ikkunaan liittyvä paneeli.
     */
    
    Paneeli getPaneeli();
    
    /**
     * Palauttaa ikkunan seuraajan.
     * 
     * @return Ikkuna, joka on avattu tästä ikkunasta käsin.
     */
    
    Ikkuna getSeuraaja();
   
    /**
     * Lisää tarvittavat tapahtumankuuntelijat ikkunan komponenteille.
     * 
     * @param logiikka Ohjelman toimintalogiikka.
     */
    
    void lisaaKuuntelijat(Logiikka logiikka);
    
    /**
     * Asettaa ikkunan näkyville.
     */
    
    void nayta();    
        
    /**
     * Päivittää ikkunaan liittyvän suotimen.
     * 
     * @param suodin Uusi suodin. Korvaa aiemman suotimen, jos sellainen on olemassa.
     */
    
    void paivitaSuodin(Suodin suodin);
    
    /**
     * Poistaa ikkunan näkyvistä.
     */
    
    void piilota();
    
    /**
     * Asettaa ikkunan edeltäjän.
     * 
     * @param edeltaja Toinen ikkuna, josta käsin tämä ikkuna on avattu.
     */
    
    void setEdeltaja(Ikkuna edeltaja);  
    
    /**
     * Asettaa ikkunaan liittyvän kehyksen.
     * 
     * @param kehys Käytettävä kehys.
     */    
    
    void setKehys(JFrame kehys);
    
    /**
     * Asettaa ikkunaan liittyvän paneelin.
     * 
     * @param paneeli Paneeli, johon voi piirtää.
     */
    
    void setPaneeli(Paneeli paneeli);
    
    /**
     * Asettaa ikkunan seuraajan.
     * 
     * @param seuraaja Toinen ikkuna, joka on avattu tästä ikkunasta käsin.
     */
    
    void setSeuraaja(Ikkuna seuraaja);        
       
    /**
     * Alustaa ikkunan käyttöä varten.
     */
    
    void teeAlustukset();
    
}