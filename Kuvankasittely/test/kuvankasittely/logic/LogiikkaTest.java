package kuvankasittely.logic;

import java.io.File;
import kuvankasittely.domain.*;
import kuvankasittely.ui.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class LogiikkaTest {
    
    private Logiikka logiikka;
    File tiedosto;
    double tarkkuus;
    
    public LogiikkaTest() {
        logiikka = new Logiikka();
        tiedosto = new File("../dokumentointi/luokkakaavio.PNG");
        tarkkuus = Math.pow(10.0, -4.0);
    }
    
    @Test
    public void kuvanLataaminen() {
        String virheilmoitus = "Kuvan lataaminen epäonnistui.";
        assertTrue( virheilmoitus, logiikka.lataaKuva(tiedosto) );
    }
    
    @Test
    public void kuvanPiirtaminen() {
        String virheilmoitus = "Kuvan piirtäminen epäonnistui.";
        Paneeli paneeli = new Paneeli();
        logiikka.lataaKuva(tiedosto);
        logiikka.piirraKuva("alkuperainen", paneeli);
        assertNotNull( virheilmoitus, paneeli.getKuva() );
    }
    
    @Test
    public void kuvanTallentaminen() {
        String virheilmoitus = "Kuvan tallentaminen epäonnistui.";
        logiikka.lataaKuva(tiedosto);
        assertTrue( virheilmoitus, logiikka.tallennaKuva() );
    }

    @Test
    public void tummennaKuvaaVahentaaKuvanKirkkautta10prosenttia() {       
        assertTrue( "Kuvan tummennus epäonnistui.", tarkistaKirkkaudenSaato("Tummennus") );        
    }
    
    @Test
    public void vaalennaKuvaaLisaaKuvanKirkkautta10prosenttia() {
        assertTrue( "Kuvan vaalennus epäonnistui.", tarkistaKirkkaudenSaato("Vaalennus") );        
    }
    
    private boolean tarkistaKirkkaudenSaato(String toiminto) {
        logiikka.lataaKuva(tiedosto);
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
    
}
