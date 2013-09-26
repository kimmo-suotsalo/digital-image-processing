package kuvankasittely.domain;

/**
 * @author      kimpe
 * @version     4.1
 * @since       2013-09-26
 */

public class Matriisi {
    
    private int rivienMaara;
    private int sarakkeidenMaara;
    private double[][] alkiot;
    
    /** Luo uuden matriisin, jonka rivien ja sarakkeiden määrä annetaan parametreina.
     */
    
    public Matriisi(int rivienMaara, int sarakkeidenMaara) {
        if (rivienMaara > 0 && sarakkeidenMaara > 0) {
            this.rivienMaara = rivienMaara;
            this.sarakkeidenMaara = sarakkeidenMaara;
            this.alkiot = new double[rivienMaara][sarakkeidenMaara];
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
     * 
     * @param rivi Matriisin rivi, jolla käsiteltävä alkio sijaitsee.
     * @param sarake Matriisin sarake, jolla käsiteltävä alkio sijaitsee.
     * @param arvo Alkiolle asetettava arvo, jonka tulee olla kaksoistarkkuuden liukuluku.
     * @return True, jos annettu rivi ja sarake löytyvät matriisista. False, jos rivi tai sarake 
     * viittaa virheelliseen indeksiin. 
     */
    
    public boolean setAlkio(int rivi, int sarake, double arvo) {
        try {
            alkiot[rivi][sarake] = arvo;
            return true;
        } catch (IndexOutOfBoundsException poikkeus) {
            return false;
        }
    }
    
    /** Asettaa arvon matriisin kaikille alkioille.
     * 
     * @param arvo Alkioille asetettava arvo, jonka tulee olla kaksoistarkkuuden liukuluku.
     * @return True, jos kaikki rivit ja sarakkeet löytyvät matriisista. False, jos jokin rivi tai sarake 
     * viittaa virheelliseen indeksiin.
     */
    
    public boolean setAlkiot(double arvo) {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                if ( !setAlkio(rivi, sarake, arvo) ) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /** Palauttaa matriisin alkion arvon.
     * 
     * @param rivi Matriisin rivi, jolla haluttu alkio sijaitsee.
     * @param sarake Matriisin sarake, jolla haluttu alkio sijaitsee.
     * @return Alkion arvo, joka on kaksoistarkkuuden liukuluku. Jos rivi tai
     * sarake viittaa virheelliseen indeksiin, palautetaan -256.0.
     */
    
    public double getAlkio(int rivi, int sarake) {
        double alkio;
        try {
            return alkiot[rivi][sarake];
        } catch (IndexOutOfBoundsException poikkeus) {
            return -256.0;
        }
    }
    
    public double[][] getAlkiot() {
        return alkiot;
    }
    
    public int getRivienMaara() {
        return rivienMaara;
    }
    
    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
    /** Kääntää matriisin pystyakselinsa ympäri.
     * <p>
     * Kääntäminen tehdään siten, että jos sarake n on matriisin keskimmäinen,
     * niin sarakkeet n-1 ja n+1 vaihdetaan keskenään, samoin sarakkeet n-2 ja
     * n+2 ja niin edelleen. Jos sarakkeita on parillinen määrä, tehdään vaihdot
     * keskimmäisten sarakkeiden välisen linjan suhteen.
     */
    
    public void kaannaVasenOikea() {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < (int) Math.floor(sarakkeidenMaara/2.0); sarake++) {
                double kopio = getAlkio(rivi, sarake);
                setAlkio(rivi, sarake, getAlkio(rivi, sarakkeidenMaara - 1 - sarake) );
                setAlkio(rivi, sarakkeidenMaara - 1 - sarake, kopio);
            }
        }
    }
    
    /** Kääntää matriisin vaaka-akselinsa ympäri.
     * <p>
     * Kääntäminen tehdään siten, että jos rivi m on matriisin keskimmäinen,
     * niin rivit m-1 ja m+1 vaihdetaan keskenään, samoin rivit m-2 ja
     * m+2 ja niin edelleen. Jos rivejä on parillinen määrä, tehdään vaihdot
     * keskimmäisten rivien välisen linjan suhteen.
     */
    
    public void kaannaYlosAlas() {
        for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
            for (int rivi = 0; rivi < (int) Math.floor(rivienMaara/2.0); rivi++) {
                double kopio = getAlkio(rivi, sarake);
                setAlkio(rivi, sarake, getAlkio(rivienMaara - 1 - rivi, sarake) );
                setAlkio(rivienMaara - 1 - rivi, sarake, kopio);
            }
        }
    }
    
    /** Kiertää matriisia tasossa 180 astetta.
     */
    
    public void kierra180Astetta() {
        kaannaVasenOikea();
        kaannaYlosAlas();
    }
    
    /** Konvoloi kaksi matriisia.
     * <p>
     * Kohdematriisin reunoilla konvoluutio lasketaan siten kuin matriisi olisi
     * ympäröity nolla-alkioilla. Tämä aiheittaa vääristymiä niiden alkioiden
     * arvoissa, jotka sijaitsevat matriisin reunoilla.
     * 
     * @param suodin Suodinmatriisi eli konvoluutioydin.
     * @return Konvoluution tuloksena saatu matriisi. Rivien ja sarakkeiden määrä
     * on sama kuin alkuperäisessä matriisissa.
     */
    
    public Matriisi konvoloi(Matriisi suodin) {        
        Matriisi konvoloitu = new Matriisi( this.getRivienMaara(), this.getSarakkeidenMaara() );
        for (int pikselinRivi = 0; pikselinRivi < this.rivienMaara; pikselinRivi++) {                            
            for (int pikselinSarake = 0; pikselinSarake < this.sarakkeidenMaara; pikselinSarake++) {     
                double pikselinArvo = laskeKonvoluutiosumma(pikselinRivi, pikselinSarake, suodin);
                konvoloitu.setAlkio(pikselinRivi, pikselinSarake, pikselinArvo);
            }                        
        }
        return konvoloitu;
    }
    
    private double laskeKonvoluutiosumma(int pikselinRivi, int pikselinSarake, Matriisi suodin) {
        int suotimenSade = ( suodin.getRivienMaara() - 1 ) / 2; 
        double summa = 0.0;         
        for (int suotimenRivi = -suotimenSade; suotimenRivi <= suotimenSade; suotimenRivi++) {
            for (int suotimenSarake = -suotimenSade; suotimenSarake <= suotimenSade; suotimenSarake++) {
                double alkionArvo = this.getAlkio(pikselinRivi + suotimenRivi, pikselinSarake + suotimenSarake);
                if (alkionArvo >= 0.0) {
                    summa += alkionArvo * ( suodin.getAlkio(suotimenSarake + suotimenSade, suotimenRivi + suotimenSade) );
                }
            }                    
        }
        return summa;
    }
    
}
