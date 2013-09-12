package kuvankasittely.domain;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatriisiTest {
    
    Matriisi matriisi;
       
    @Test
    public void alustaminen() {
        String virheilmoitus = "Matriisin alustaminen ei saisi onnistua, jos rivien tai sarakkeiden määrä on < 1.";
        matriisi = new Matriisi(0, 0);
        assertNull( virheilmoitus, matriisi.getAlkiot() );
        matriisi = new Matriisi(-1, -1);
        assertNull( virheilmoitus, matriisi.getAlkiot() );                
    }
    
    @Test
    public void alkionAsettaminen() {
        String virheilmoitus = "Virhe asetettaessa arvoa yksittäiselle alkiolle.";
        matriisi = new Matriisi(1, 1);
        assertTrue( virheilmoitus, matriisi.setAlkio(0, 0, 7) );
        assertFalse( virheilmoitus, matriisi.setAlkio(1, 1, 7) );
    }
    
    @Test
    public void kaikkienAlkioidenAsettaminen() {
        String virheilmoitus = "Virhe asetettaessa arvoa kaikille alkioille.";
        matriisi = new Matriisi(11, 11);
        assertTrue( virheilmoitus, matriisi.setAlkiot(4) );
    }
    
    @Test
    public void alkionPalauttaminenJosEiAlustettu() {
        String virheilmoitus = "Alustamattoman matriisin alkioilla tulisi olla arvo 0.";
        matriisi = new Matriisi(8, 8);
        assertEquals( virheilmoitus, 0, matriisi.getAlkio(4, 6) );
    }
    
    @Test
    public void alkionPalauttaminen() {
        String virheilmoitus = "Alkion arvo palautui virheellisenä.";
        matriisi = new Matriisi(4, 9);
        matriisi.setAlkiot(15);
        assertEquals( virheilmoitus, 15, matriisi.getAlkio(0, 0) ); 
        matriisi.setAlkio(3,8,13);
        assertEquals( virheilmoitus, 13, matriisi.getAlkio(3, 8) );
    }
    
    @Test
    public void alkionPalauttaminenVirheellisestaIndeksista() {
        String virheilmoitus = "Yritettiin palauttaa alkiota virheellisestä indeksistä.";
        matriisi = new Matriisi(13, 1);
        assertTrue( virheilmoitus, matriisi.getAlkio(4, -1) == -1 && matriisi.getAlkio(13, 0) == -1 );
    }
    
}
