package kuvankasittely.logic;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import kuvankasittely.domain.*;
import kuvankasittely.ui.*;

/**
 * Ohjelman toimintalogiikasta vastaava luokka.
 * <p>
 * Huolehtii tiedostoista, piirtämisestä ja kuvan muokkaamisesta.
 * 
 * @author      kimpe
 * @version     6.0
 * @since       2013-10-11
 */

public class Logiikka {

    /**
     * Ohjelman käyttämät luku- ja kirjoitustiedostot.
     */        
    
    private HashMap<String,File> tiedostot;
    
    /**
     * Alkuperäiset ja muokatut kuvat.
     */     
    
    private HashMap<String,Kuva> kuvat;
    
    /**
     * Kuvan muokkaamiseen käytettävä suodin.
     */     
    
    private Suodin suodin;
    
    /**
     * Luo ohjelman toimintalogiikan.
     * <p>
     * Suotimeksi asetetaan oletusarvoisesti deltafunktio.
     */
    
    public Logiikka() {
        this.tiedostot = new HashMap<String,File>();
        this.kuvat = new HashMap<String,Kuva>();
        this.suodin = new Suodin("Deltafunktio", 1);
    }

    /**
     * Lataa kuvan tiedostosta.
     * 
     * @param lukutiedosto Tiedosto, josta kuva ladataan.
     * @return True, jos kuvan lataaminen onnistui, muutoin False.
     */
    
    public boolean lataaKuva(File lukutiedosto) {
        tiedostot.put("lukutiedosto", lukutiedosto);
        try {
            kuvat.put("alkuperainen", new Kuva( ImageIO.read( tiedostot.get("lukutiedosto") ) ) );
            kuvat.put("muokattu", new Kuva( kuvat.get("alkuperainen") ) );
            return true;
        } catch (Exception poikkeus) {
            return false;
        }
    }
    
    /**
     * Piirtää kuvan käyttäjän tarkasteltavaksi.
     * 
     * @param tunnus Avain, jonka perusteella kuva haetaan hajautustaulusta.
     * @param paneeli Paneeli, johon kuva piirretään.
     */
    
    public void piirraKuva(String tunnus, Paneeli paneeli) {
        if ( kuvat.get(tunnus) != null ) {
            paneeli.setKuva( kuvat.get(tunnus) );
            paneeli.repaint();
        }
    }

    /**
     * Tummentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Käsiteltävä kuva.
     */
    
    public void tummennaKuvaa(Kuva kuva) {
        if (kuva != null) {
            saadaKuvanKirkkautta(kuva, 0.9);
        }
    }
    
    /**
     * Vaalentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Käsiteltävä kuva.
     */
    
    public void vaalennaKuvaa(Kuva kuva) {
        if (kuva != null) {
            saadaKuvanKirkkautta(kuva, 1.1);
        }
    }
    
    /**
     * Palauttaa logiikkaan liittyvän suotimen.
     * 
     * @return Suodin.
     */
    
    public Suodin getSuodin() {
        return suodin;
    }

    /**
     * Asettaa logiikkaan liittyvän suotimen.
     * 
     * @param tyyppi Tyyppiä kuvaava merkkijono.
     * @param sade Ei-negatiivinen kokonaisluku.
     * @see Suodin
     */
    
    public void setSuodin(String tyyppi, int sade) {
        suodin = new Suodin(tyyppi, sade);
    }
    
    /**
     * Suodattaa kuvan logiikkaan liittyvällä suotimella.
     * <p>
     * Suodatus tapahtuu konvoloimalla kuva suotimen matriisilla.
     * 
     * @param kuva Suodatettava kuva.
     */
    
    public void suodataKuva(Kuva kuva) {
        if (kuva != null) {
            kuva.konvoloi( suodin.getMatriisi() );
        }
    }

    /**
     * Palauttaa alkuperäisen kuvan käyttäjän tarkasteltavaksi.
     * 
     * @param paneeli Paneeli, johon kuva piirretään.
     */
    
    public void palautaAlkuperainenKuva(Paneeli paneeli) {
        kuvat.put("muokattu", new Kuva( kuvat.get("alkuperainen") ) );
        piirraKuva("alkuperainen", paneeli);
    }
    
    /** Tallentaa kuvan tiedostoon.
     * 
     * @return True, jos kuvan tallentaminen onnistui, muutoin False.
     */
    
    public boolean tallennaKuva(File kirjoitustiedosto) {
        tiedostot.put("kirjoitustiedosto", kirjoitustiedosto);        
        try {
            tiedostot.get("kirjoitustiedosto").createNewFile();
            ImageIO.write(kuvat.get("muokattu").getPuskuroituKuva(), "PNG",
                          tiedostot.get("kirjoitustiedosto") );
            return true;
        } catch (IOException poikkeus) {
            return false;
        }
    }
    
    /**
     * Palauttaa ohjelman käyttämät tiedostot.
     * 
     * @return Hajautustaulu, johon tiedostot on talletettu.
     */
    
    public HashMap<String,File> getTiedostot() {
        return tiedostot;
    }
    
    /**
     * Palauttaa alkuperäiset ja muokatut kuvat.
     * 
     * @return Hajautustaulu, johon kuvat on talletettu.
     */
    
    public HashMap<String,Kuva> getKuvat() {
        return kuvat;
    }
    
    /**
     * Säätää kuvan kirkkautta vakiokertoimen verran.
     * 
     * @param kuva Käsiteltävä kuva.
     * @param kerroin Kaksoistarkkuuden liukuluku. Jos kerroin on pienempi kuin
     * 1.0, kuva tummenee. Jos kerroin on suurempi kuin 1.0, kuva vaalenee.
     */
    
    private void saadaKuvanKirkkautta(Kuva kuva, double kerroin) {
        for (int kanava = 0; kanava < kuva.getKanavienMaara(); kanava++) {
            Matriisi matriisi = kuva.getKanava(kanava);
            for (int rivi = 0; rivi < matriisi.getRivienMaara(); rivi++) {
                for (int sarake = 0; sarake < matriisi.getSarakkeidenMaara(); sarake++) {
                    matriisi.setAlkio(rivi, sarake, (int) Math.round( kerroin * matriisi.getAlkio(rivi, sarake) ) );
                }
            }
            kuva.setKanava(kanava, matriisi);
        }
    }
    
}
