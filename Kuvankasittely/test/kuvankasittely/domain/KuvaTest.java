package kuvankasittely.domain;

import kuvankasittely.logic.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class KuvaTest {
    
    Logiikka logiikka;
    Kuva kuva;
    Matriisi matriisi;
    
    public KuvaTest() {
        logiikka = new Logiikka();
        logiikka.lataaKuva();
        kuva = new Kuva( logiikka.getKuvat().get("alkuperainen").getPuskuroituKuva() );
        matriisi = new Matriisi( kuva.getKuvanKorkeus(), kuva.getKuvanLeveys() );
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
    public void setKanavaAsettaaMatriisinOikein() {
        for (int kanava = 0; kanava < kuva.getKanavienMaara(); kanava++) {
            matriisi = kuva.getKanava(kanava);
            int rivi = matriisi.getRivienMaara() - 1;
            int sarake = matriisi.getSarakkeidenMaara() - 1;
            int testialkio = matriisi.getAlkio(rivi, sarake);
            Matriisi uusi = new Matriisi(matriisi);
            uusi.setAlkiot(testialkio / 2);
            kuva.setKanava(kanava, uusi);
            assertEquals( "Matriisin R viimeisen alkion arvo on virheellinen.",
                          kuva.getKanava(kanava).getAlkio(rivi, sarake), testialkio / 2 );
        }
    }
    
    
}
