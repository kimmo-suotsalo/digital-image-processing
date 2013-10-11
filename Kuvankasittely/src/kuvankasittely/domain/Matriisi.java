package kuvankasittely.domain;

/**
 * Kuvan RGB-kanavaa tai suotimen alkioita esittävä matriisi. 
 * <p>
 * Matriisin alkiot talletetaan kaksoistarkkuuden liukulukuina, joille sallitaan
 * myös negatiiviset arvot. Tämä on tarpeen pyöristysvirheiden välttämiseksi ja
 * negatiivisia alkioita sisältävien suodintyyppien toteuttamiseksi.
 * 
 * @author      kimpe
 * @version     6.0
 * @since       2013-10-11
 */

public class Matriisi {
    
    /**
     * Matriisin rivien lukumäärä.
     */    
    
    private int rivienMaara;
    
    /**
     * Matriisin sarakkeiden lukumäärä.
     */    
    
    private int sarakkeidenMaara;
    
    /**
     * Kaksiulotteinen taulukko, johon matriisin alkiot talletetaan.
     */    
    
    private double[][] alkiot;
    
    /**
     * Luo uuden matriisin, jonka koko määräytyy parametrien perusteella.
     * 
     * @param rivienMaara Matriisin rivien lukumäärä.
     * @param sarakkeidenMaara Matriisin sarakkeiden lukumäärä.
     */
    
    public Matriisi(int rivienMaara, int sarakkeidenMaara) {
        if (rivienMaara > 0 && sarakkeidenMaara > 0) {
            this.rivienMaara = rivienMaara;
            this.sarakkeidenMaara = sarakkeidenMaara;
            this.alkiot = new double[rivienMaara][sarakkeidenMaara];
        }
    }
    
    /**
     * Luo uuden matriisin toisesta matriisista.
     * 
     * @param alkuperainen Matriisi, jonka sisältö kopioidaan uuteen matriisiin.
     */
    
    public Matriisi(Matriisi alkuperainen) {
        this( alkuperainen.getRivienMaara(), alkuperainen.getSarakkeidenMaara() );
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                this.setAlkio(rivi, sarake, alkuperainen.getAlkio(rivi, sarake) );
            }
        }
    }
    
    /** 
     * Asettaa arvon matriisin alkiolle.
     * 
     * @param rivi Matriisin rivi, jolla käsiteltävä alkio sijaitsee.
     * @param sarake Matriisin sarake, jolla käsiteltävä alkio sijaitsee.
     * @param arvo Alkiolle asetettava arvo, jonka tulee olla kaksoistarkkuuden liukuluku.
     */
    
    public void setAlkio(int rivi, int sarake, double arvo) {
        try {
            alkiot[rivi][sarake] = arvo;
        } catch (IndexOutOfBoundsException poikkeus) {
            return;
        }
    }
    
    /**
     * Asettaa arvon matriisin kaikille alkioille.
     * 
     * @param arvo Alkioille asetettava arvo, jonka tulee olla kaksoistarkkuuden liukuluku.
     */
    
    public void setAlkiot(double arvo) {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                setAlkio(rivi, sarake, arvo);
            }
        }
    }
    
    /**
     * Palauttaa matriisin alkion arvon.
     * 
     * @param rivi Matriisin rivi, jolla haluttu alkio sijaitsee.
     * @param sarake Matriisin sarake, jolla haluttu alkio sijaitsee.
     * @return Alkion arvo, joka on kaksoistarkkuuden liukuluku. Jos rivi tai
     * sarake viittaa virheelliseen indeksiin, palautetaan -256.0.
     */
    
    public double getAlkio(int rivi, int sarake) {
        try {
            return alkiot[rivi][sarake];
        } catch (IndexOutOfBoundsException poikkeus) {
            return -256.0;
        }
    }
    
    /**
     * Palauttaa matriisin alkiot sisältävän taulukon.
     * 
     * @return Kaksiulotteinen taulukko, johon matriisin alkiot on talletettu.
     */
    
    public double[][] getAlkiot() {
        return alkiot;
    }

    /**
     * Palauttaa matriisin rivien lukumäärän.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getRivienMaara() {
        return rivienMaara;
    }
    
    /**
     * Palauttaa matriisin sarakkeiden lukumäärän.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
    /**
     * Kertoo matriisin alkiot vakiolla.
     * <p>
     * Jos alkuperäinen matriisi A = (a_ij) ja vakio on c, niin kertomisen
     * yhteydessä kukin a_ij saa arvon c * a_ij.
     * 
     * @param vakio Luku, jolla alkiot kerrotaan.
     */
    
    public void kerro(double vakio) {
        for (int rivi = 0; rivi < this.getRivienMaara(); rivi++) {
             for (int sarake = 0; sarake < this.getSarakkeidenMaara(); sarake++) {
                setAlkio(rivi, sarake, vakio * getAlkio(rivi, sarake) );
             }
        }
    }
    
    /**
     * Lisää matriisin alkioihin toisen samankokoisen matriisin alkiot.
     * <p>
     * Jos alkuperäinen matriisi A = (a_ij) ja lisättävä matriisi B = (b_ij),
     * niin lisäyksen yhteydessä kukin a_ij saa arvon a_ij + b_ij.
     * 
     * @param matriisi Lisättävä matriisi.
     */
    
    public void lisaa(Matriisi matriisi) {
        if ( this.getRivienMaara() == matriisi.getRivienMaara() &&
             this.getSarakkeidenMaara() == matriisi.getSarakkeidenMaara() ) {
            for (int rivi = 0; rivi < this.getRivienMaara(); rivi++) {
                for (int sarake = 0; sarake < this.getSarakkeidenMaara(); sarake++) {
                    this.setAlkio(rivi, sarake, this.getAlkio(rivi, sarake) + matriisi.getAlkio(rivi, sarake) );
                }    
            }
        }
    }        
    
    /**
     * Kääntää matriisin pystyakselinsa ympäri.
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
    
    /**
     * Kääntää matriisin vaaka-akselinsa ympäri.
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
    
    /**
     * Kiertää matriisia tasossa 180 astetta.
     * <p>
     * Kierto tehdään kääntämällä matriisi ensin pysty- ja sitten
     * vaaka-akselinsa ympäri.
     */
    
    public void kierra180Astetta() {
        kaannaVasenOikea();
        kaannaYlosAlas();
    }
    
    /**
     * Konvoloi kaksi matriisia.
     * <p>
     * Ensimmäinen matriisi vastaa kuvapikselin kanavaa, toinen käytettävää suodinta.
     * 
     * @param suodin Suodinmatriisi eli konvoluutioydin.
     * @return Konvoluution tuloksena saatu matriisi. Rivien ja sarakkeiden määrä
     * on sama kuin ensimmäisessä matriisissa.
     */
    
    public Matriisi konvoloi(Matriisi suodin) {
        suodin.kierra180Astetta();
        Matriisi konvoloitu = new Matriisi( this.getRivienMaara(), this.getSarakkeidenMaara() );
        for (int pikselinRivi = 0; pikselinRivi < this.rivienMaara; pikselinRivi++) {                            
            for (int pikselinSarake = 0; pikselinSarake < this.sarakkeidenMaara; pikselinSarake++) {     
                double pikselinArvo = laskeKonvoluutiosumma(pikselinRivi, pikselinSarake, suodin);
                konvoloitu.setAlkio(pikselinRivi, pikselinSarake, pikselinArvo);
            }                        
        }
        return konvoloitu;
    }
    
    /**
     * Laskee konvoluutiosumman määrätyn pikselin kohdalla.
     * <p>
     * Konvoluutiosumma lasketaan kahden matriisin alkioista. Ensimmäinen eli 
     * kohdematriisi vastaa kuvapikselin kanavaa, toinen käytettävää suodinta. 
     * <p>
     * Kohdematriisin reunoilla konvoluutio lasketaan siten kuin matriisi olisi
     * ympäröity nolla-alkioilla.
     * 
     * @param pikselinRivi Käsiteltävän kuvapikselin rivi.
     * @param pikselinSarake Käsiteltävän kuvapikselin sarake.
     * @param suodin Suodinmatriisi eli konvoluutioydin.
     * @return Konvoluution tuloksena saatu matriisi. Rivien ja sarakkeiden määrä
     * on sama kuin kohdematriisissa.
     */
    
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
