package kuvankasittely.domain;

import java.awt.image.*;

/**
 * Käyttäjälle näkyvä kuva.
 * <p>
 * Luokan sisäinen esitysmuoto kuvalle on puskuroitu kuva (BufferedImage), johon
 * liittyy kuvarasteri (WritableRaster). Rasteri koostuu pikseleistä, jotka
 * sisältävät yhden tai useamman RGB-kanavan. Värikuvan tapauksessa kanavien
 * määrä on yleensä kolme. Kutakin kanavaa vastaa oma matriisinsa, jonka alkiot
 * voivat saada kokonaislukuarvoja väliltä [0, 255].
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class Kuva {

    /**
     * Luokan sisäinen esitysmuoto kuvalle.
     */    
    
    private BufferedImage puskuroituKuva;
    
    /**
     * Muuutokset mahdollistava kuvarasteri.
     */        
    
    private WritableRaster rasteri;
          
    /**
     * Luo uuden kuvan puskuroidusta kuvasta.
     * 
     * @param puskuroituKuva Kuvarasterin sisältävä puskuroitu kuva.
     */            
    
    public Kuva(BufferedImage puskuroituKuva){
        this.puskuroituKuva = puskuroituKuva;
        this.rasteri = puskuroituKuva.getRaster();
    }

    /**
     * Luo uuden kuvan toisesta kuvasta.
     * 
     * @param alkuperainen Kuva, jonka sisältö kopioidaan uuteen kuvaan.
     */
    
    public Kuva(Kuva alkuperainen){
        rasteri = alkuperainen.getRasteri().createCompatibleWritableRaster();
        rasteri.setRect( alkuperainen.getRasteri() );
        puskuroituKuva = new BufferedImage( alkuperainen.getPuskuroituKuva().getColorModel(), rasteri, true, null); 
    }
    
    /** 
     * Palauttaa RGB-kanavan.
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
    
    /**
     * Palauttaa RGB-kanavien lukumäärän.
     * 
     * @return Ei-negatiivinen kokonaisluku, värikuvilla yleensä 3.
     */
    
    public int getKanavienMaara() {
        return rasteri.getNumBands();
    }
    
    /**
     * Palauttaa kuvan korkeuden pikseleinä.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getKuvanKorkeus() {
        return rasteri.getHeight();
    }
    
    /**
     * Palauttaa kuvan leveyden pikseleinä.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getKuvanLeveys() {
        return rasteri.getWidth();
    }

    /**
     * Palauttaa kuvan muodossa, joka mahdollistaa piirtämisen ja
     * tiedosto-operaatiot.
     * 
     * @return Kuva puskuroidussa muodossa.
     */
    
    public BufferedImage getPuskuroituKuva() {
        return puskuroituKuva;
    }
    
    /**
     * Palauttaa pikseleistä koostuvan kuvarasterin.
     * 
     * @return Muuutokset mahdollistava kuvarasteri.
     */
    
    public WritableRaster getRasteri() {
        return rasteri;
    }    
    
    /**
     * Konvoloi kuvan valitulla suotimella.
     * <p>
     * Kutakin RGB-kanavaa vastaava matriisi konvoloidaan erikseen. Varsinaiseen
     * laskentaan käytetään Matriisi-luokan metodia konvoloi. Konvoluution tuloksena
     * saatu matriisi asetetaan käsiteltävän kanavan matriisiksi.
     * 
     * @param suodin Suodinmatriisi.
     * @see Matriisi
     */
    
    public void konvoloi(Matriisi suodin) {
        for (int kanava = 0; kanava < this.getKanavienMaara(); kanava++) {            
            Matriisi alkuperainen = this.getKanava(kanava);
            Matriisi konvoloitu = alkuperainen.konvoloi(suodin);
            this.setKanava(kanava, konvoloitu);
        }
    }
    
    /**
     * Muuntaa matriisin alkion arvon pikseliin sopivaksi.
     * <p>
     * Pyöristää alkion arvon lähimpään kokonaislukuun ja muuntaa sen
     * kokonaislukutyyppiseksi. Jos saatu luku on negatiivinen, palautetaan 0.
     * Jos luku on suurempi kuin 255, palautetaan 255.
     * 
     * @param matriisinAlkio Muunnettava alkio.
     * @return Pikseliin sopiva arvo.
     */
           
    private int muunnaPikselinArvoksi(double matriisinAlkio) {
        int pikselinArvo = Math.round( (float) matriisinAlkio );
        if (pikselinArvo < 0) pikselinArvo = 0;
        if (pikselinArvo > 255) pikselinArvo = 255;
        return pikselinArvo;
    }

    /**
     * Asettaa RGB-kanavan.
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
       
    /**
     * Asettaa pikseleistä koostuvan kuvarasterin.
     * 
     * @param rasteri Muuutokset mahdollistava kuvarasteri.
     */
    
    public void setRasteri(WritableRaster rasteri) {
        this.rasteri = rasteri;
    }    
 
}