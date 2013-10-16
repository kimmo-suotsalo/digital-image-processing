package kuvankasittely.ui;

import java.awt.event.*;
import javax.swing.*;
import kuvankasittely.logic.*;

/**
 * Tapahtumankuuntelija painikkeiden toiminnan toteuttamiseksi.
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class Kuuntelija implements ActionListener {

    /**
     * Ohjelman toimintalogiikka.
     */    
    
    private Logiikka logiikka;
    
    /**
     * Painike, jonka tapahtumia kuunnellaan.
     */    
    
    private JButton painike;
    
    /**
     * Paneeli, johon voi piirtää.
     */    
    
    private Paneeli paneeli;
    
    /**
     * Toinen ikkuna, josta käsin tähän kuuntelijaan liittyvä ikkuna on avattu.
     */

    private Ikkuna edeltaja;

    /**
     * Toinen ikkuna, joka on avattu tähän kuuntelijaan liittyvästä ikkunasta käsin.
     */
    
    private Ikkuna seuraaja;
    
    /**
     * Luo uuden tapahtumankuuntelijan.
     * 
     * @param logiikka Ohjelman toimintalogiikka.
     * @param painike Painike, jonka tapahtumia kuunnellaan.
     * @param paneeli Paneeli piirtämistä varten.
     * @param edeltaja Toinen ikkuna, josta käsin tähän kuuntelijaan liittyvä ikkuna on avattu.
     * @param seuraaja Toinen ikkuna, joka on avattu tähän kuuntelijaan liittyvästä ikkunasta käsin.
     */
    
    public Kuuntelija (Logiikka logiikka, JButton painike, Paneeli paneeli, Ikkuna edeltaja, Ikkuna seuraaja) {
        super();
        this.logiikka = logiikka;
        this.painike = painike;
        this.paneeli = paneeli;
        this.edeltaja = edeltaja;
        this.seuraaja = seuraaja;
    }
        
    @Override
    public void actionPerformed(ActionEvent tapahtuma) {
        if (edeltaja == null) {
            kasittelePaaikkunanPainike();
        } else {
            kasitteleSuodatusikkunanPainike();
        }                
    }

    /** 
     * Käsittee pääikkunan painikkeisiin liittyvät tapahtumat.
     */
    
    private void kasittelePaaikkunanPainike() {
        switch ( painike.getActionCommand() ) {
            case "Lataa kuva":
                this.lataaKuva();
                break;
            case "Tummenna":
                logiikka.tummennaKuvaa( logiikka.getKuvat().get("muokattu") );
                break;
            case "Vaalenna":
                logiikka.vaalennaKuvaa( logiikka.getKuvat().get("muokattu") );
                break;
            case "Harmaasävy":
                logiikka.muunnaHarmaasavyksi( logiikka.getKuvat().get("muokattu") );
                break;
            case "Suodatus":
                if (seuraaja != null) seuraaja.nayta();                
                break;
            case "Palauta":
                logiikka.palautaAlkuperainenKuva(paneeli);
                return;
            case "Tallenna kuva":
                this.tallennaKuva();
                break;
            case "Lopeta":
                System.exit(0);
        }
        if (paneeli != null) logiikka.piirraKuva("muokattu", paneeli);   
    }
    
    /** 
     * Käsittelee suodatusikkunan painikkeisiin liittyvät tapahtumat.
     */
    
    private void kasitteleSuodatusikkunanPainike() {
        switch ( painike.getActionCommand() ) {
            case "Nollaa":
                logiikka.setSuodin("Nolla", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Deltafunktio":
                logiikka.setSuodin("Deltafunktio", 1);                
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Alipäästö":
                logiikka.setSuodin("Alipäästö", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Ylipäästö":
                logiikka.setSuodin("Ylipäästö", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Reunanetsintä":
                logiikka.setSuodin("Reunanetsintä", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;                
            case "Suodata":
                logiikka.suodataKuva( logiikka.getKuvat().get("muokattu") );
                if (paneeli != null) logiikka.piirraKuva("muokattu", paneeli);                   
                break;
            case "Sulje":
                edeltaja.getSeuraaja().piilota();
        }
    }
    
    /** 
     * Avaa tiedostoikkunan ja lataa kuvan käyttäjän valitsemasta tiedostosta.
     */
    
    private void lataaKuva() {
        JFileChooser tiedostovalitsin = new JFileChooser();
        if ( tiedostovalitsin.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            logiikka.lataaKuva( tiedostovalitsin.getSelectedFile() );
        }        
    }
    
    /** 
     * Avaa tiedostoikkunan ja tallentaa kuvan käyttäjän nimeämään tiedostoon.
     */
    
    private void tallennaKuva() {
        JFileChooser tiedostovalitsin = new JFileChooser();
        if ( tiedostovalitsin.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            logiikka.tallennaKuva( tiedostovalitsin.getSelectedFile() );
        }        
    }
    
}
