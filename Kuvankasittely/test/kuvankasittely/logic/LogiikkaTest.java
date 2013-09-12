package kuvankasittely.logic;

import kuvankasittely.ui.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class LogiikkaTest {
    
    private Logiikka logiikka;
    
    public LogiikkaTest() {
        this.logiikka = new Logiikka();
    }
    
    @Test
    public void kuvanLataaminen() {
        String virheilmoitus = "Kuvan lataaminen epäonnistui.";
        assertTrue( virheilmoitus, logiikka.lataaKuva() );
    }
    
    @Test
    public void kuvanPiirtaminen() {
        String virheilmoitus = "Kuvan piirtäminen epäonnistui.";
        Paneeli paneeli = new Paneeli();
        logiikka.lataaKuva();
        logiikka.piirraKuva(paneeli);
        assertNotNull( virheilmoitus, paneeli.getKuva() );
    }
    
    @Test
    public void kuvanTallentaminen() {
        String virheilmoitus = "Kuvan tallentaminen epäonnistui.";
        logiikka.lataaKuva();
        assertTrue( virheilmoitus, logiikka.tallennaKuva() );
    }
    
}
