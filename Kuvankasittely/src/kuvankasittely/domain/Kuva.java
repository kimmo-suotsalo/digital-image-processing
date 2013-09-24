package kuvankasittely.domain;

import java.awt.image.*;

public class Kuva {
    
    private BufferedImage puskuroituKuva;
    private WritableRaster rasteri;    
    
    public Kuva(BufferedImage puskuroituKuva){
        this.puskuroituKuva = puskuroituKuva;
        this.rasteri = puskuroituKuva.getRaster();
    }
    
    public int getKanavienMaara() {
        return rasteri.getNumBands();
    }
    
    public int getKuvanLeveys() {
        return rasteri.getWidth();
    }
    
    public int getKuvanKorkeus() {
        return rasteri.getHeight();
    }
    
    public BufferedImage getPuskuroituKuva() {
        return puskuroituKuva;
    }
    
    public WritableRaster getRasteri() {
        return rasteri;
    }    
    
    public Matriisi getKanava(int kanava) {
        if ( kanava >= getKanavienMaara() ) return null;
        Matriisi matriisi = new Matriisi( getKuvanKorkeus(), getKuvanLeveys() );
        for (int rivi = 0; rivi < getKuvanKorkeus(); rivi++) {
            for (int sarake = 0; sarake < getKuvanLeveys(); sarake++) {
                matriisi.setAlkio(rivi, sarake, rasteri.getPixel(sarake, rivi, new int[getKanavienMaara()])[kanava] );
            }
        }
        return matriisi;
    }
    
    public void setKanava(int kanava, Matriisi matriisi) {
        if ( kanava >= getKanavienMaara() ) return;
        int[] kerrokset = new int[getKanavienMaara()];
        for (int rivi = 0; rivi < getKuvanKorkeus(); rivi++) {
            for (int sarake = 0; sarake < getKuvanLeveys(); sarake++) {
                for (int indeksi = 0; indeksi < getKanavienMaara(); indeksi++) {
                    if ( indeksi == kanava) {
                        kerrokset[indeksi] = matriisi.getAlkio(rivi, sarake);
                    } else {
                        kerrokset[indeksi] = rasteri.getPixel(sarake, rivi, new int[3])[indeksi];
                    }
                }
                rasteri.setPixel(sarake, rivi, kerrokset);
            }
        }
    }
    
    public void konvoloi(Matriisi suodin) {
        for (int kanava = 0; kanava < this.getKanavienMaara(); kanava++) {
            Matriisi kohde = this.getKanava(kanava);
            suodin.kierra180Astetta();
            kohde.konvoloi(suodin);
            this.setKanava(kanava, kohde);
        }
    }
    
}
