package kuvankasittely.domain;

/**
 * @author      kimpe
 * @version     4.0
 * @since       2013-09-24
 */

public class Matriisi {
    
    private int rivienMaara;
    private int sarakkeidenMaara;
    private int[][] alkiot;
    
    /** Luo uuden matriisin, jonka rivien ja sarakkeiden määrä annetaan parametreina.
     */
    
    public Matriisi(int rivienMaara, int sarakkeidenMaara) {
        if (rivienMaara > 0 && sarakkeidenMaara > 0) {
            this.rivienMaara = rivienMaara;
            this.sarakkeidenMaara = sarakkeidenMaara;
            this.alkiot = new int[rivienMaara][sarakkeidenMaara];
        }
    }
    
    /** Luo uuden matriisin, jonka sisältö kopioidaan parametrina annetusta matriisista.
     */
    
    public Matriisi(Matriisi alkuperainen) {
        this( alkuperainen.getRivienMaara(), alkuperainen.getSarakkeidenMaara() );
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                this.setAlkio(rivi, sarake, alkuperainen.getAlkio(rivi, sarake) );
            }
        }
    }
    
    /** Asettaa arvon matriisin alkiolle.
     * <p>
     * 
     * @param rivi Matriisin rivi, jolla käsiteltävä alkio sijaitsee.
     * @param sarake Matriisin sarake, jolla käsiteltävä alkio sijaitsee.
     * @param arvo Alkiolle asetettava arvo, jonka tulee olla kokonaisluku väliltä [0, 255]. Jos arvo on liian pieni,
     * asetetaan alkioksi nolla. Jos arvo on liian suuri, asetetaan alkioksi 255.
     * @return {@see True}, jos annettu rivi ja sarake löytyvät matriisista. {@see False}, jos rivi tai sarake 
     * viittaa virheelliseen indeksiin. 
     */
    
    public boolean setAlkio(int rivi, int sarake, int arvo) {
        try {
            if (arvo < 0) {
                alkiot[rivi][sarake] = 0;
            } else if (255 < arvo) {
                alkiot[rivi][sarake] = 255;
            } else {
                alkiot[rivi][sarake] = arvo;
            }
            return true;
        } catch (IndexOutOfBoundsException poikkeus) {
            return false;
        }
    }
    
    public boolean setAlkiot(int arvo) {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                if ( !setAlkio(rivi, sarake, arvo) ) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getAlkio(int rivi, int sarake) {
        int alkio;
        try {
            return alkiot[rivi][sarake];
        } catch (IndexOutOfBoundsException poikkeus) {
            return -1;
        }
    }
    
    public int[][] getAlkiot() {
        return alkiot;
    }
    
    public int getRivienMaara() {
        return rivienMaara;
    }
    
    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
    public void kaannaVasenOikea() {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < (int) Math.floor(sarakkeidenMaara/2.0); sarake++) {
                int kopio = getAlkio(rivi, sarake);
                setAlkio(rivi, sarake, getAlkio(rivi, sarakkeidenMaara - 1 - sarake) );
                setAlkio(rivi, sarakkeidenMaara - 1 - sarake, kopio);
            }
        }
    }
    
    public void kaannaYlosAlas() {
        for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
            for (int rivi = 0; rivi < (int) Math.floor(rivienMaara/2.0); rivi++) {
                int kopio = getAlkio(rivi, sarake);
                setAlkio(rivi, sarake, getAlkio(rivienMaara - 1 - rivi, sarake) );
                setAlkio(rivienMaara - 1 - rivi, sarake, kopio);
            }
        }
    }
    
    public void kierra180Astetta() {
        kaannaVasenOikea();
        kaannaYlosAlas();
    }
      
    public void konvoloi(Matriisi suodin) {        
        for (int pikselinRivi = 0; pikselinRivi < this.rivienMaara; pikselinRivi++) {                            
            for (int pikselinSarake = 0; pikselinSarake < this.sarakkeidenMaara; pikselinSarake++) {     
                double pikselinArvo = laskeKonvoluutiosumma(pikselinRivi, pikselinSarake, suodin);
                this.setAlkio(pikselinRivi, pikselinSarake, (int) pikselinArvo);
            }                        
        }
    }
    
    private double laskeKonvoluutiosumma(int pikselinRivi, int pikselinSarake, Matriisi suodin) {
        int suotimenSade = ( suodin.getRivienMaara() - 1 ) / 2; 
        double summa = 0.0;         
        for (int suotimenRivi = -suotimenSade; suotimenRivi <= suotimenSade; suotimenRivi++) {
            for (int suotimenSarake = -suotimenSade; suotimenSarake <= suotimenSade; suotimenSarake++) {
                double alkionArvo = (double) this.getAlkio(pikselinRivi + suotimenRivi, pikselinSarake + suotimenSarake);
                summa += alkionArvo * ( (double) suodin.getAlkio(suotimenSarake + suotimenSade, suotimenRivi + suotimenSade) / 255.0);
            }                    
        }
        return summa;
    }
    
}
