package kuvankasittely.logic;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import kuvankasittely.domain.*;
import kuvankasittely.ui.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Logiikka {
    
    private HashMap<String,File> tiedostot;
    private HashMap<String,Kuva> kuvat;
    private Suodin suodin;
    
    public Logiikka() {
        this.tiedostot = new HashMap<String,File>();
        this.kuvat = new HashMap<String,Kuva>();
        this.suodin = new Suodin("Deltafunktio", 1);
    }

    /** Lataa kuvan tiedostosta.
     * 
     * @return True, jos tiedoston avaaminen ja kuvan lataaminen onnistuvat, muutoin False.
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
    
    /** Piirtää kuvan käyttäjän tarkasteltavaksi.
     * 
     * @param paneeli Paneeli, johon kuva piirretään.
     */
    
    public void piirraKuva(String tunnus, Paneeli paneeli) {
        if ( kuvat.get(tunnus) != null ) {
            paneeli.setKuva( kuvat.get(tunnus) );
            paneeli.repaint();
        }
    }

    /** Tummentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Tummennettava kuva.
     */
    
    public void tummennaKuvaa(Kuva kuva) {
        if (kuva != null) {
            saadaKuvanKirkkautta(kuva, 0.9);
        }
    }
    
    /** Vaalentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Vaalennettava kuva.
     */
    
    public void vaalennaKuvaa(Kuva kuva) {
        if (kuva != null) {
            saadaKuvanKirkkautta(kuva, 1.1);
        }
    }
    
    /** Suodattaa kuvan erikseen määriteltävällä suotimella.
     * <p>
     * Suodatus tapahtuu konvoluution avulla. 
     * 
     * @param kuva Suodatettava kuva.
     */
    
    public Suodin getSuodin() {
        return suodin;
    }
    
    public void setSuodin(String tyyppi, int sade) {
        suodin = new Suodin(tyyppi, sade);
    }
    
    public void suodataKuva(Kuva kuva) {
        if (kuva != null) {
            kuva.konvoloi( suodin.getMatriisi() );
        }
    }

    public void palautaAlkuperainenKuva(Paneeli paneeli) {
        kuvat.put("muokattu", new Kuva( kuvat.get("alkuperainen") ) );
        piirraKuva("alkuperainen", paneeli);
    }
    
    /** Tallentaa kuvan tiedostoon.
     * 
     * @return True, jos tiedoston avaaminen ja kuvan tallentaminen onnistuvat, muutoin False.
     */
    
    public boolean tallennaKuva() {
        if (kuvat.get("muokattu") != null) {
            tiedostot.put("kirjoitustiedosto", new File("../../muokattuKuva.PNG") );        
            try {
                tiedostot.get("kirjoitustiedosto").createNewFile();
                ImageIO.write(kuvat.get("muokattu").getPuskuroituKuva(), "PNG",
                              tiedostot.get("kirjoitustiedosto") );
                return true;
            } catch (IOException poikkeus) {
                return false;
            }
        }
        return false;
    }
    
    public HashMap<String,File> getTiedostot() {
        return tiedostot;
    }
    
    public HashMap<String,Kuva> getKuvat() {
        return kuvat;
    }
    
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
