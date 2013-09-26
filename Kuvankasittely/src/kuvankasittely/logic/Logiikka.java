package kuvankasittely.logic;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import kuvankasittely.domain.*;
import kuvankasittely.ui.*;

/**
 * @author      kimpe
 * @version     4.1
 * @since       2013-09-26
 */

public class Logiikka {
    
    private HashMap<String,File> tiedostot;
    private HashMap<String,Kuva> kuvat;
    
    public Logiikka() {
        this.tiedostot = new HashMap<String,File>();
        this.kuvat = new HashMap<String,Kuva>();
    }

    /** Lataa kuvan tiedostosta.
     * 
     * @return True, jos tiedoston avaaminen ja kuvan lataaminen onnistuvat, muutoin False.
     */
    
    public boolean lataaKuva() {
        tiedostot.put("lukutiedosto", new File("./dokumentointi/luokkakaavio.PNG") );
        try {
            kuvat.put("alkuperainen", new Kuva( ImageIO.read( tiedostot.get("lukutiedosto") ) ) );
            return true;
        } catch (IOException poikkeus) {
            return false;
        }
    }
    
    /** Piirtää kuvan käyttäjän tarkasteltavaksi.
     * 
     * @param paneeli Paneeli, johon kuva piirretään.
     */
    
    public void piirraKuva(Paneeli paneeli) {
        if ( kuvat.get("alkuperainen") == null ) return;
        paneeli.setKuva( kuvat.get("alkuperainen") );
        paneeli.repaint();
    }

    /** Tummentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Tummennettava kuva.
     */
    
    public void tummennaKuvaa(Kuva kuva) {
        if (kuva == null) return;
        saadaKuvanKirkkautta(kuva, 0.9);
    }
    
    /** Vaalentaa kuvaa 10 prosenttia.
     * 
     * @param kuva Vaalennettava kuva.
     */
    
    public void vaalennaKuvaa(Kuva kuva) {
        if (kuva == null) return;
        saadaKuvanKirkkautta(kuva, 1.1);
    }
    
    /** Suodattaa kuvan erikseen määriteltävällä suotimella.
     * <p>
     * Suodatus tapahtuu konvoluution avulla. 
     * 
     * @param kuva Suodatettava kuva.
     */
    
    public void suodataKuva(Kuva kuva) {
        if (kuva == null) return;
        int suotimenRivienMaara = 3;
        int suotimenSarakkeidenMaara = 3;
        Matriisi suodin = new Matriisi(suotimenRivienMaara, suotimenSarakkeidenMaara);        
        suodin.setAlkiot( 1.0 / (suotimenRivienMaara * suotimenSarakkeidenMaara) );
        kuva.konvoloi(suodin);
    }
    
    /** Tallentaa kuvan tiedostoon.
     * 
     * @return True, jos tiedoston avaaminen ja kuvan tallentaminen onnistuvat, muutoin False.
     */
    
    public boolean tallennaKuva() {
        if (kuvat.get("alkuperainen") == null) return false;
        tiedostot.put("kirjoitustiedosto", new File("../muokattuKuva.PNG") );        
        try {
            tiedostot.get("kirjoitustiedosto").createNewFile();
            ImageIO.write(kuvat.get("alkuperainen").getPuskuroituKuva(), "PNG",
                          tiedostot.get("kirjoitustiedosto") );
            return true;
        } catch (IOException poikkeus) {
            return false;
        }
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
