package kuvankasittely.domain;

import static org.junit.Assert.*;
import org.junit.Test;

public class MatriisiTest {
    
    Matriisi matriisi;
    double tarkkuus;
       
    public MatriisiTest() {
        tarkkuus = Math.pow(10.0, -4.0);
    }
    
    @Test
    public void alustaminenEiOnnistuJosRivienTaiSarakkeidenMaaraOnPienempiKuin1() {
        String virheilmoitus = "Matriisin alustaminen ei saisi onnistua, jos rivien tai sarakkeiden määrä on < 1.";
        matriisi = new Matriisi(0, 0);
        assertNull( virheilmoitus, matriisi.getAlkiot() );
        matriisi = new Matriisi(-1, -1);
        assertNull( virheilmoitus, matriisi.getAlkiot() );                
    }
    
    @Test
    public void setAlkioAsettaaOikeanArvonAlkiolle() {
        String virheilmoitus = "Virhe asetettaessa arvoa yksittäiselle alkiolle.";
        matriisi = new Matriisi(1, 1);
        assertTrue( virheilmoitus, matriisi.setAlkio(0, 0, 7.0) );
        assertEquals( virheilmoitus, 7.0, matriisi.getAlkio(0, 0), tarkkuus );
    }
    
    @Test
    public void setAlkioEiAsetaArvoaAlkiolleJosIndeksiOnVirheellinen() {
        String virheilmoitus = "Alkion arvon asettaminen onnistui virheelliselle indeksille.";
        matriisi = new Matriisi(14, 23);
        assertFalse( virheilmoitus, matriisi.setAlkio(10, 23, 1.0) );
        assertFalse( virheilmoitus, matriisi.setAlkio(14, 10, 1.0) );
        assertFalse( virheilmoitus, matriisi.setAlkio(-1, 10, 1.0) );
        assertFalse( virheilmoitus, matriisi.setAlkio(10, -1, 1.0) );
    }
    
    @Test
    public void setAlkiotAsettaaOikeanArvonKaikilleAlkioille() {
        String virheilmoitus = "Virhe asetettaessa arvoa kaikille alkioille.";
        matriisi = new Matriisi(11, 11);
        assertTrue( virheilmoitus, matriisi.setAlkiot(4.0) );
        for (int rivi = 0; rivi < matriisi.getRivienMaara(); rivi++) {
            for (int sarake = 0; sarake < matriisi.getSarakkeidenMaara(); sarake++) {
                assertEquals( virheilmoitus, 4.0, matriisi.getAlkio(rivi, sarake), tarkkuus );
            }
        }
    }
    
    @Test
    public void getAlkioPalauttaaNollanJosMatriisiaEiOleAlustettu() {
        String virheilmoitus = "Alustamattoman matriisin alkioilla tulisi olla arvo 0.0.";
        matriisi = new Matriisi(8, 8);
        assertEquals( virheilmoitus, 0.0, matriisi.getAlkio(4, 6), tarkkuus );
    }
    
    @Test
    public void getAlkioPalauttaaOikeanArvon() {
        String virheilmoitus = "Alkion arvo palautui virheellisenä.";
        matriisi = new Matriisi(4, 9);
        matriisi.setAlkiot(15.0);
        assertEquals( virheilmoitus, 15.0, matriisi.getAlkio(0, 0), tarkkuus ); 
        matriisi.setAlkio(3, 8, 13);
        assertEquals( virheilmoitus, 13.0, matriisi.getAlkio(3, 8), tarkkuus );
    }
    
    @Test
    public void getAlkioPalauttaaPienemmanLuvunKuinMiinus255JosIndeksiOnVirheellinen() {
        String virheilmoitus = "Yritettiin palauttaa alkiota virheellisestä indeksistä.";
        matriisi = new Matriisi(13, 1);
        assertTrue( virheilmoitus, matriisi.getAlkio(4, -1) < -255.0 && matriisi.getAlkio(13, 0) < -255.0 );
    }
    
    @Test
    public void kaannaVasenOikeaJaKaannaYlosAlasSiirtavatAlkiotOikeillePaikoille() {
        matriisi = new Matriisi(3, 3);
        double alkio = 7.999;
        for (int rivi = 0; rivi < matriisi.getRivienMaara(); rivi++) {
            for (int sarake = 0; sarake < matriisi.getSarakkeidenMaara(); sarake++) {
                matriisi.setAlkio(rivi, sarake, alkio++ );
            }
        }
        assertTrue( "Matriisin kaantaminen vasen-oikea-suunnassa epäonnistui.", toimiikoVasenOikea(matriisi) );
        assertTrue( "Matriisin kaantaminen ylös-alas-suunnassa epäonnistui.", toimiikoYlosAlas(matriisi) );
    }
    
    @Test
    public void konvoloiEiMuutaVakiomatriisinKeskellaOleviaAlkioitaJosKaytossaOnAlipaastosuodin() {
        String virheilmoitus = "Vakiomatriisin konvolointi alipäästösuotimella antoi virheellisiä arvoja.";
        double testialkio = 83.999;
        matriisi = new Matriisi(100, 100);
        matriisi.setAlkiot(testialkio);
        Matriisi suodin = new Matriisi(5, 5);
        suodin.setAlkiot( 1.0 / ( suodin.getRivienMaara() * suodin.getSarakkeidenMaara() ) );
        matriisi = matriisi.konvoloi(suodin);
        for (int rivi = 2; rivi < 98; rivi++) {
            for (int sarake = 2; sarake < 98; sarake++) {
                assertEquals( virheilmoitus, testialkio, matriisi.getAlkio(rivi, sarake), tarkkuus );                
            }
        }
    }
    
    private boolean toimiikoVasenOikea(Matriisi alkuperainen) {
        Matriisi kopio = new Matriisi(alkuperainen);
        kopio.kaannaVasenOikea();
        return ( kopio.getAlkio(0,0) == alkuperainen.getAlkio(0, 2) &&                
                 kopio.getAlkio(1,2) == alkuperainen.getAlkio(1, 0) &&
                 kopio.getAlkio(2,0) == alkuperainen.getAlkio(2, 2) );
    }
    
    private boolean toimiikoYlosAlas(Matriisi alkuperainen) {
        Matriisi kopio = new Matriisi(alkuperainen);
        kopio.kaannaYlosAlas();
        return ( kopio.getAlkio(0,0) == alkuperainen.getAlkio(2, 0) &&                
                 kopio.getAlkio(2,1) == alkuperainen.getAlkio(0, 1) &&
                 kopio.getAlkio(0,2) == alkuperainen.getAlkio(2, 2) );
    }
    
    
}
