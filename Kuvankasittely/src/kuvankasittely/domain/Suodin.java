package kuvankasittely.domain;

/**
 * Kuvan muokkauksessa käytettävä suodin.
 * <p>
 * Suotimen ominaisuudet määrää matriisi, joka vastaa valittua suodintyyppiä.
 * 
 * @author      kimpe
 * @version     6.0
 * @since       2013-10-11
 */

public class Suodin {
    
    /**
     * Suotimen rivien lukumäärä.
     */    
    
    private int rivienMaara;
    
    /**
     * Suotimen sarakkeiden lukumäärä.
     */    
    
    private int sarakkeidenMaara;
    
    /**
     * Suotimen ominaisuudet määräävä matriisi.
     */    
    
    private Matriisi matriisi;
    
    /**
     * Suotimen tyyppi, esimerkiksi alipäästösuodin.
     */    
    
    private String tyyppi;

    /**
     * Luo uuden suotimen, jonka tyyppi ja säde on valittavissa.
     * <p>
     * Suotimen säde määrää siihen liittyvän matriisin koon. Jos säde on r, niin
     * matriisin koko on n x n, missä n = 2 * r + 1;
     * 
     * @param tyyppi Tyyppiä kuvaava merkkijono.
     * @param sade Ei-negatiivinen kokonaisluku. 
     */
    
    public Suodin(String tyyppi, int sade) {
        this.tyyppi = tyyppi;
        rivienMaara = (2 * sade) + 1;
        sarakkeidenMaara = rivienMaara;
        matriisi = new Matriisi(rivienMaara, sarakkeidenMaara);        
        alustaMatriisi(tyyppi);
    }

    /**
     * Palauttaa suotimen tyypin.
     * 
     * @return Tyyppiä kuvaava merkkijono.
     */
    
    public String getTyyppi() {
        return tyyppi;
    }
    
    /**
     * Palauttaa suotimen rivien lukumäärän.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getRivienMaara() {
        return rivienMaara;
    }
    
    /**
     * Palauttaa suotimen sarakkeiden lukumäärän.
     * 
     * @return Ei-negatiivinen kokonaisluku.
     */
    
    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
    /**
     * Palauttaa suotimeen liittyvän matriisin.
     * 
     * @return Matriisi, jonka alkiot ovat kaksoistarkkuuden liukulukuja.
     */
    
    public Matriisi getMatriisi() {
        return matriisi;
    }

    /**
     * Asettaa suotimen tyypin.
     * 
     * @param tyyppi Tyyppiä kuvaava merkkijono.
     */
    
    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    /**
     * Asettaa suotimen rivien lukumäärän.
     * 
     * @param rivienMaara Ei-negatiivinen kokonaisluku.
     */
    
    public void setRivienMaara(int rivienMaara) {
        this.rivienMaara = rivienMaara;
    }

    /**
     * Asettaa suotimen sarakkeiden lukumäärän.
     * 
     * @param sarakkeidenMaara Ei-negatiivinen kokonaisluku.
     */
    
    public void setSarakkeidenMaara(int sarakkeidenMaara) {
        this.sarakkeidenMaara = sarakkeidenMaara;
    }

    /**
     * Asettaa suotimeen liittyvän matriisin.
     * 
     * @param matriisi Matriisi, jonka alkiot ovat kaksoistarkkuuden liukulukuja.
     */
    
    public void setMatriisi(Matriisi matriisi) {
        this.matriisi = matriisi;
    }
    
    /**
     * Alustaa suotimeen liittyvän matriisin.
     * 
     * @param suotimenTyyppi Tyyppiä kuvaava merkkijono.
     */
    
    public void alustaMatriisi(String suotimenTyyppi) {
        switch (suotimenTyyppi) {
            case "Nolla":
                matriisi = luoNollamatriisi();
                break;
            case "Deltafunktio":
                matriisi = luoDeltafunktio();
                break;
            case "Alipäästö":
                matriisi = luoAlipaastosuodin();
                break;                
            case "Ylipäästö":
                matriisi = luoYlipaastosuodin();
                break;                
            case "Reunanetsintä":
                matriisi = luoReunanetsintasuodin();
        }
    }
    
    /**
     * Palauttaa matriisin, jonka kaikki alkiot ovat nollia.
     * 
     * @return Uusi matriisi, joka on kooltaan sama kuin suotimeen liittyvä matriisi.
     */
    
    public Matriisi luoNollamatriisi() {
        Matriisi nollamatriisi = new Matriisi(matriisi);
        nollamatriisi.setAlkiot(0.0);        
        return nollamatriisi;
    }
    
    /**
     * Palauttaa matriisin, jonka keskimmäinen alkio on 1.0 ja muut nollia.
     * 
     * @return Uusi matriisi, joka on kooltaan sama kuin suotimeen liittyvä matriisi.
     */
    
    public Matriisi luoDeltafunktio() {
        Matriisi delta = luoNollamatriisi();
        int keskimmainenRivi = (int) Math.floor( (double) delta.getRivienMaara() / 2.0);
        int keskimmainenSarake = (int) Math.floor( (double) delta.getSarakkeidenMaara() / 2.0);
        delta.setAlkio(keskimmainenRivi, keskimmainenSarake, 1.0);
        return delta;
    }
    
    /**
     * Palauttaa matriisin, jonka alkiot saadaan jakamalla 1.0 matriisin koolla.
     * 
     * @return Uusi matriisi, joka on kooltaan sama kuin suotimeen liittyvä matriisi.
     */
    
    public Matriisi luoAlipaastosuodin() {
        Matriisi alipaasto = new Matriisi(matriisi.getRivienMaara(), matriisi.getSarakkeidenMaara() );
        int alkioidenMaara = matriisi.getRivienMaara() * matriisi.getSarakkeidenMaara();        
        alipaasto.setAlkiot(1.0 / alkioidenMaara);
        return alipaasto;
    }

    /**
     * Palauttaa matriisin, jonka alkiot saadaan vähentämällä deltafunktion matriisista
     * reunanetsintäsuotimen matriisi.
     * 
     * @return Uusi matriisi, joka on kooltaan sama kuin suotimeen liittyvä matriisi.
     */
    
    public Matriisi luoYlipaastosuodin() {               
        Matriisi delta = luoDeltafunktio();
        Matriisi reunanetsinta = luoReunanetsintasuodin();        
        reunanetsinta.kerro(-1.0);
        delta.lisaa(reunanetsinta);
        return delta;        
    }

    /**
     * Palauttaa matriisin, jonka alkioiden arvot perustuvat diskreettiin
     * Laplace-operaattoriin.
     * 
     * @return Uusi matriisi, joka on kooltaan sama kuin suotimeen liittyvä matriisi.
     */
    
    public Matriisi luoReunanetsintasuodin() {
        Matriisi reunanetsinta = luoNollamatriisi();
        reunanetsinta.setAlkio(0, 1, 1.0);
        reunanetsinta.setAlkio(1, 0, 1.0);
        reunanetsinta.setAlkio(1, 1, -4.0);
        reunanetsinta.setAlkio(1, 2, 1.0);
        reunanetsinta.setAlkio(2, 1, 1.0);
        return reunanetsinta;
    }
    
}
