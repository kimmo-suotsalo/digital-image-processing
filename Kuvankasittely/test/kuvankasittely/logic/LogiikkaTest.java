package kuvankasittely.logic;

import java.io.File;
import java.nio.file.*;
import kuvankasittely.domain.*;
import kuvankasittely.ui.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class LogiikkaTest {
    
    private Logiikka logiikka;
    File lukutiedosto;
    double tarkkuus;
    
    public LogiikkaTest() {
        logiikka = new Logiikka();
        lukutiedosto = new File("../dokumentointi/luokkakaavio.PNG");
        tarkkuus = Math.pow(10.0, -4.0);
    }
    
    @Test
    public void kuvanLataaminen() {
        String virheilmoitus = "Kuvan lataaminen epäonnistui.";
        assertTrue( virheilmoitus, logiikka.lataaKuva(lukutiedosto) );
    }
    
    @Test
    public void kuvanPiirtaminen() {
        String virheilmoitus = "Kuvan piirtäminen epäonnistui.";
        Paneeli paneeli = new Paneeli();
        logiikka.lataaKuva(lukutiedosto);
        logiikka.piirraKuva("alkuperainen", paneeli);
        assertNotNull( virheilmoitus, paneeli.getKuva() );
    }
        
    @Test
    public void kuvanTallentaminen() {
        String virheilmoitus = "Kuvan tallentaminen epäonnistui.";
        logiikka.lataaKuva(lukutiedosto);
        String tiedostonNimi = "../../luokkakaavionKopio.jpg";
        Path hakemistopolku = FileSystems.getDefault().getPath(tiedostonNimi);          
        if ( Files.notExists(hakemistopolku, LinkOption.NOFOLLOW_LINKS) ) {
            File kirjoitustiedosto = new File(tiedostonNimi);
            assertTrue( virheilmoitus, logiikka.tallennaKuva(kirjoitustiedosto) );
        }
    }
        
    @Test
    public void muunnaHarmaasavyksiRajaaKuvanKanavienMaaranYhteen() {
        String virheilmoitus = "Harmaasävykuvassa oli enemmän kuin yksi RGB-kanava.";
        logiikka.lataaKuva(lukutiedosto);
        Kuva alkuperainen = logiikka.getKuvat().get("alkuperainen");
        logiikka.muunnaHarmaasavyksi(alkuperainen);
        Kuva harmaasavy = logiikka.getKuvat().get("muokattu");
        assertEquals(virheilmoitus, 1, harmaasavy.getKanavienMaara() );
    }
    
    @Test
    public void palautaAlkuperainenKuvaAsettaaMuokattavaksiKuvaksiAlkuperaisenKopion() {
        String virheilmoitus = "Alkuperäisen kuvan palauttaminen epäonnistui.";
        logiikka.lataaKuva(lukutiedosto);                
        logiikka.tummennaKuvaa( logiikka.getKuvat().get("muokattu") );
        logiikka.palautaAlkuperainenKuva( new Paneeli() );
        for (int kanava = 0; kanava < logiikka.getKuvat().get("alkuperainen").getKanavienMaara(); kanava++) {
            Matriisi alkuperainen = logiikka.getKuvat().get("alkuperainen").getKanava(kanava);
            Matriisi palautettu = logiikka.getKuvat().get("muokattu").getKanava(kanava);
            for (int rivi = 0; rivi < alkuperainen.getRivienMaara(); rivi++) {
                 for (int sarake = 0; sarake < alkuperainen.getSarakkeidenMaara(); sarake++) {                
                    assertEquals(virheilmoitus, (int) alkuperainen.getAlkio(rivi, sarake),
                                 (int) palautettu.getAlkio(rivi, sarake) );
                 }
            }
        }
    }

    private boolean tarkistaKirkkaudenSaato(String toiminto) {
        logiikka.lataaKuva(lukutiedosto);
        Kuva kuva = logiikka.getKuvat().get("alkuperainen");
        double testialkio = kuva.getKanava(0).getAlkio(0, 0);        
        if ( toiminto.equals("Tummennus") ) {            
            logiikka.tummennaKuvaa(kuva);
            if ( 0 <= (int) Math.round( 0.9 * testialkio ) ) {            
                return Math.abs( (int) Math.round(0.9 * testialkio) -
                       kuva.getKanava(0).getAlkio(0, 0) ) <= tarkkuus;
            }
        } else if (toiminto.equals("Vaalennus") ) {
            logiikka.vaalennaKuvaa(kuva);
            if ( (int) Math.round( 1.1 * testialkio ) <= 255 ) {
                return Math.abs( (int) Math.round( 1.1 * testialkio ) -
                       kuva.getKanava(0).getAlkio(0, 0) ) <= tarkkuus;
            }
        }
        return true;
    }
    
    @Test
    public void tummennaKuvaaVahentaaKuvanKirkkautta10prosenttia() {       
        assertTrue( "Kuvan tummennus epäonnistui.", tarkistaKirkkaudenSaato("Tummennus") );        
    }
        
    @Test
    public void vaalennaKuvaaLisaaKuvanKirkkautta10prosenttia() {
        assertTrue( "Kuvan vaalennus epäonnistui.", tarkistaKirkkaudenSaato("Vaalennus") );        
    }
    
}
