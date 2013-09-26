package kuvankasittely.domain;

import kuvankasittely.logic.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class KuvaTest {
    
    Logiikka logiikka;
    Kuva kuva;
    Matriisi matriisi;
    double tarkkuus; 
    
    public KuvaTest() {
        logiikka = new Logiikka();
        logiikka.lataaKuva();
        kuva = new Kuva( logiikka.getKuvat().get("alkuperainen").getPuskuroituKuva() );
        matriisi = new Matriisi( kuva.getKuvanKorkeus(), kuva.getKuvanLeveys() );
        tarkkuus = Math.pow(10.0, -4.0);
    }
    
    @Test
    public void getKanavienMaaraPalauttaaLuvunYhdestaKolmeen() {
        assertFalse( "Kuvalla on vähemmän kuin 1 kanavaa.", kuva.getKanavienMaara() < 1);
        assertFalse( "Kuvalla on enemmän kuin 3 kanavaa.", kuva.getKanavienMaara() > 3);
    }
    
    @Test
    public void getKuvanLeveysPalauttaaNollaaSuuremmanLuvun() {
        assertFalse( "Kuvan leveys on < 1.", kuva.getKuvanLeveys() < 1 );
    }
    
    @Test
    public void getKuvanKorkeusPalauttaaNollaaSuuremmanLuvun() {
        assertFalse( "Kuvan korkeus on < 1.", kuva.getKuvanKorkeus() < 1 );
    }
    
    @Test
    public void getKanavaPalauttaaMatriisinJonkaKokoOnVahintaan1x1() {
        for (int kanava = 0; kanava < kuva.getKanavienMaara(); kanava++) {
            assertTrue( "Matriisin rivien tai sarakkeiden määrä on < 1.",
                        kuva.getKanava(kanava).getRivienMaara() > 0 &&
                        kuva.getKanava(kanava).getSarakkeidenMaara() > 0);
        }
    }
    
    @Test
    public void getKanavaPalauttaaMatriisinJonkaAlkiotOvatValilta0ja255() {
        for (int kanava = 0; kanava < kuva.getKanavienMaara(); kanava++) {
            matriisi = kuva.getKanava(kanava);
            for (int rivi = 0; rivi < matriisi.getRivienMaara(); rivi++) {
                for (int sarake = 0; sarake < matriisi.getSarakkeidenMaara(); sarake++) {
                    double alkio = (double) matriisi.getAlkio(rivi, sarake);
                    assertTrue( "Pikselin (" + rivi + ", " + sarake + ") arvo on virheellinen.", 
                                0 <= alkio && alkio <= 255);
                }
                
            }
        }
    }
    
    @Test
    public void setKanavaAsettaaMatriisinOikein() {
        String virheilmoitus = "Matriisin R viimeisen alkion arvo on virheellinen.";
        for (int kanava = 0; kanava < kuva.getKanavienMaara(); kanava++) {
            matriisi = kuva.getKanava(kanava);
            int rivi = matriisi.getRivienMaara() - 1;
            int sarake = matriisi.getSarakkeidenMaara() - 1;
            double testialkio = matriisi.getAlkio(rivi, sarake);
            Matriisi uusi = new Matriisi(matriisi);
            uusi.setAlkiot(testialkio / 2.0);
            kuva.setKanava(kanava, uusi);
            assertEquals( virheilmoitus, Math.round( (float) testialkio / 2.0),
                          kuva.getKanava(kanava).getAlkio(rivi, sarake), tarkkuus );
        }
    }
    
    @Test
    public void konvoloiEiMuutaKuvaaJosKaytossaOnDeltafunktio() {
        Matriisi suodin = new Matriisi(5, 5);
        suodin.setAlkiot(0.0);
        suodin.setAlkio(2, 2, 1.0);
        
        Matriisi R = kuva.getKanava(0);        
        Matriisi uusiR = kuva.getKanava(0).konvoloi(suodin);
        
        for (int rivi = 0; rivi < kuva.getKuvanLeveys(); rivi++) {
            for (int sarake = 0; sarake < kuva.getKuvanKorkeus(); sarake++) {
                assertEquals( "Kuva muuttui konvoloitaessa sitä deltafunktiolla.",
                              R.getAlkio(rivi, sarake), uusiR.getAlkio(rivi, sarake), tarkkuus );
            }
        }
    }
    
}
