package kuvankasittely.domain;

import java.awt.image.*;

/**
 * @author      kimpe
 * @version     4.0
 * @since       2013-09-24
 */

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
    
    /** Palauttaa RGB-kanavan.
     * <p>
     * Metodi määrittelee matriisin, jonka rivien määrä vastaa kuvan korkeutta ja sarakkeiden
     * määrä kuvan leveyttä. Matriisin alkiot ovat kokonaislukuja väliltä [0, 255], ja ne
     * saadaan kuvarasterin pikselin arvoista valitulla kanavalla. 
     * 
     * @param kanava RGB-kanavan tunnus: 0-punainen, 1-vihreä, 2-sininen.
     * @return Parametrin yksilöimä kanava matriisimuodossa.
     */
    
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
    
    /** Asettaa RGB-kanavan.
     * <p>
     * Metodi asettaa matriisin alkiot kuvarasterin pikselin arvoiksi valitulla RGB-kanavalla.
     * 
     * @param kanava RGB-kanavan tunnus: 0-punainen, 1-vihreä, 2-sininen.
     * @param matriisi Matriisi, jonka alkiot ovat kokonaislukuja väliltä [0, 255].
     */
    
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
    
    /** Konvoloi kuvan valitulla suotimella.
     * <p>
     * Metodi määrittelee kohdematriisin, jonka alkiot saadaan kuvarasterin pikselin arvoista
     * valitulla kanavalla. Kohdematriisin ja suotimen välinen konvoluutio lasketaan Matriisi-luokan
     * metodilla {@see konvoloi}, mutta suodinmatriisin kierto tehdään jo tässä metodissa.
     * 
     * @param suodin Suodinmatriisi.
     * @see Matriisi
     */
    
    public void konvoloi(Matriisi suodin) {
        for (int kanava = 0; kanava < this.getKanavienMaara(); kanava++) {
            Matriisi kohde = this.getKanava(kanava);
            suodin.kierra180Astetta();
            kohde.konvoloi(suodin);
            this.setKanava(kanava, kohde);
        }
    }
    
}
