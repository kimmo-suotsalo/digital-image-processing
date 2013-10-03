package kuvankasittely.domain;

import java.awt.image.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Kuva {
    
    private BufferedImage puskuroituKuva;
    private WritableRaster rasteri;
    
    public Kuva(BufferedImage puskuroituKuva){
        this.puskuroituKuva = puskuroituKuva;
        this.rasteri = puskuroituKuva.getRaster();
    }
    
    public Kuva(Kuva alkuperainen){
        rasteri = alkuperainen.getRasteri().createCompatibleWritableRaster();
        rasteri.setRect( alkuperainen.getRasteri() );
        puskuroituKuva = new BufferedImage( alkuperainen.getPuskuroituKuva().getColorModel(), rasteri, true, null); 
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
    
    public WritableRaster getRasteri() {
        return rasteri;
    }    
    
    public BufferedImage getPuskuroituKuva() {
        return puskuroituKuva;
    }
    
    /** Palauttaa RGB-kanavan.
     * <p>
     * Metodi määrittelee matriisin, jonka rivien määrä vastaa kuvan korkeutta ja sarakkeiden
     * määrä kuvan leveyttä. Matriisin alkiot saadaan kuvarasterin pikselin arvoista valitulla
     * kanavalla.
     * <p>
     * Kuvarasterin pikselin arvot ovat kokonaislukuja väliltä [0, 255]. Matriisiin ne talletetaan
     * kaksoistarkkuuden liukulukuina.
     * 
     * @param kanava RGB-kanavan tunnus: 0-punainen, 1-vihreä, 2-sininen.
     * @return Parametrin yksilöimä kanava matriisimuodossa.
     */
    
    public Matriisi getKanava(int kanava) {
        if ( kanava < getKanavienMaara() ) {
            Matriisi matriisi = new Matriisi( getKuvanKorkeus(), getKuvanLeveys() );
            for (int rivi = 0; rivi < getKuvanKorkeus(); rivi++) {
                for (int sarake = 0; sarake < getKuvanLeveys(); sarake++) {                
                    matriisi.setAlkio(rivi, sarake, (double) rasteri.getPixel(sarake, rivi, new int[getKanavienMaara()])[kanava] );
                }
            }
            return matriisi;
        }
        return null;
    }
    
    /** Asettaa RGB-kanavan.
     * <p>
     * Metodi asettaa matriisin alkiot kuvarasterin pikselin arvoiksi valitulla RGB-kanavalla.
     * <p>
     * Matriisin alkiot ovat kaksoistarkkuuden liukulukuja, mutta kuvarasterin pikseleihin ne talletetaan
     * kokonaislukuina. Muunnoksen yhteydessä liukuluvun arvo pyöristetään lähimpään kokonaislukuun. Jos
     * saatu luku on negatiivinen, asetetaan pikselin arvoksi 0. Jos luku on suurempi kuin 255, asetetaan
     * pikselin arvoksi 255.
     * 
     * @param kanava RGB-kanavan tunnus: 0-punainen, 1-vihreä, 2-sininen.
     * @param matriisi Pikselin arvot sisältävä matriisi.
     */
    
    public void setKanava(int kanava, Matriisi matriisi) {
        if ( kanava < getKanavienMaara() ) {
            int[] kerrokset = new int[getKanavienMaara()];
            for (int rivi = 0; rivi < getKuvanKorkeus(); rivi++) {
                for (int sarake = 0; sarake < getKuvanLeveys(); sarake++) {
                    for (int indeksi = 0; indeksi < getKanavienMaara(); indeksi++) {
                        if ( indeksi == kanava) {
                            kerrokset[indeksi] = muunnaPikselinArvoksi( matriisi.getAlkio(rivi, sarake) );
                        } else {
                            kerrokset[indeksi] = rasteri.getPixel(sarake, rivi, new int[3])[indeksi];
                        }
                    }
                    rasteri.setPixel(sarake, rivi, kerrokset);
                }
            }
        }
    }
    
    /** Konvoloi kuvan valitulla suotimella.
     * <p>
     * Metodi määrittelee kohdematriisin, jonka alkiot saadaan kuvarasterin pikselin arvoista
     * valitulla kanavalla. Kohdematriisin ja suotimen välinen konvoluutio lasketaan Matriisi-luokan
     * metodilla konvoloi, mutta suodinmatriisin kierto tehdään jo tässä metodissa.
     * 
     * @param suodin Suodinmatriisi.
     * @see Matriisi
     */
    
    public void konvoloi(Matriisi suodin) {
        for (int kanava = 0; kanava < this.getKanavienMaara(); kanava++) {            
            suodin.kierra180Astetta();
            Matriisi kohde = this.getKanava(kanava).konvoloi(suodin);
            this.setKanava(kanava, kohde);
        }
    }
    
    private int muunnaPikselinArvoksi(double matriisinAlkio) {
        int pikselinArvo = Math.round( (float) matriisinAlkio );
        if (pikselinArvo < 0) pikselinArvo = 0;
        if (pikselinArvo > 255) pikselinArvo = 255;
        return pikselinArvo;
    }
    
}
