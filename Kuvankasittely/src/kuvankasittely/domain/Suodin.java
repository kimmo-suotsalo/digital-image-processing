package kuvankasittely.domain;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Suodin {
    
    private int rivienMaara;
    private int sarakkeidenMaara;
    private Matriisi matriisi;
    String tyyppi;

    public Suodin(String tyyppi, int sade) {
        this.tyyppi = tyyppi;
        rivienMaara = (2 * sade) + 1;
        sarakkeidenMaara = rivienMaara;
        matriisi = new Matriisi(rivienMaara, sarakkeidenMaara);        
        alustaMatriisi(tyyppi);
    }

    public String getTyyppi() {
        return tyyppi;
    }
    
    public int getRivienMaara() {
        return rivienMaara;
    }

    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
    public Matriisi getMatriisi() {
        return matriisi;
    }

    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    public void setRivienMaara(int rivienMaara) {
        this.rivienMaara = rivienMaara;
    }

    public void setSarakkeidenMaara(int sarakkeidenMaara) {
        this.sarakkeidenMaara = sarakkeidenMaara;
    }

    public void setMatriisi(Matriisi matriisi) {
        this.matriisi = matriisi;
    }
    
    /** Alustaa suodinta esittävän matriisin.
     * 
     * @param suotimenTyyppi "Nolla", "Deltafunktio", "Alipäästö", "Ylipäästö"
     * tai "Reunanetsintä".
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
    
    public Matriisi luoNollamatriisi() {
        Matriisi nollamatriisi = new Matriisi(matriisi);
        nollamatriisi.setAlkiot(0.0);        
        return nollamatriisi;
    }
    
    public Matriisi luoDeltafunktio() {
        Matriisi delta = luoNollamatriisi();
        int keskimmainenRivi = (int) Math.floor( (double) delta.getRivienMaara() / 2.0);
        int keskimmainenSarake = (int) Math.floor( (double) delta.getSarakkeidenMaara() / 2.0);
        delta.setAlkio(keskimmainenRivi, keskimmainenSarake, 1.0);
        return delta;
    }
    
    public Matriisi luoAlipaastosuodin() {
        Matriisi alipaasto = new Matriisi(matriisi.getRivienMaara(), matriisi.getSarakkeidenMaara() );
        int alkioidenMaara = matriisi.getRivienMaara() * matriisi.getSarakkeidenMaara();        
        alipaasto.setAlkiot(1.0 / alkioidenMaara);
        return alipaasto;
    }
    
    public Matriisi luoYlipaastosuodin() {
        Matriisi delta = luoDeltafunktio();
        Matriisi ylipaasto = luoReunanetsintasuodin();
        ylipaasto.kerro(-1.0);
        ylipaasto.lisaa(delta);
        return ylipaasto;        
    }

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
