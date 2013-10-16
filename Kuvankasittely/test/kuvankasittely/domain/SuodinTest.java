package kuvankasittely.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class SuodinTest {
    
    Suodin suodin;
    double tarkkuus;
    
    public SuodinTest() {
        suodin = new Suodin("Deltafunktio", 1);
        tarkkuus = Math.pow(10.0, -4.0);
    }
    
    @Test
    public void luoAlipaastosuodinPalauttaaOikeanlaisenMatriisin() {                
        suodin.setMatriisi( suodin.luoAlipaastosuodin() );
        double[] referenssiarvot = new double[9];
        for (int i = 0; i < referenssiarvot.length; i++) {
            referenssiarvot[i] = 1.0 / 9;            
        }
        tarkistaAlkioidenArvot(referenssiarvot);        
    }
        
    @Test
    public void luoDeltafunktioPalauttaaOikeanlaisenMatriisin() {                
        suodin.setMatriisi( suodin.luoNollamatriisi() );
        suodin.setMatriisi( suodin.luoDeltafunktio() );
        double[] referenssiarvot = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        tarkistaAlkioidenArvot(referenssiarvot);        
    }
    
    @Test
    public void luoNollamatriisiPalauttaaOikeanlaisenMatriisin() {                
        suodin.setMatriisi( suodin.luoNollamatriisi() );
        double[] referenssiarvot = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        tarkistaAlkioidenArvot(referenssiarvot);        
    }
 
    @Test
    public void luoReunanetsintasuodinPalauttaaOikeanlaisenMatriisin() {                
        suodin.setMatriisi( suodin.luoReunanetsintasuodin() );
        double[] referenssiarvot = {0.0, 1.0, 0.0, 1.0, -4.0, 1.0, 0.0, 1.0, 0.0};
        tarkistaAlkioidenArvot(referenssiarvot);        
    }
    
    @Test
    public void luoYlipaastosuodinPalauttaaOikeanlaisenMatriisin() {                
        suodin.setMatriisi( suodin.luoYlipaastosuodin() );
        double[] referenssiarvot = {0.0, -1.0, 0.0, -1.0, 5.0, -1.0, 0.0, -1.0, 0.0};
        tarkistaAlkioidenArvot(referenssiarvot);        
    }
    
    private void tarkistaAlkioidenArvot(double[] referenssiarvot) {
        for (int rivi = 0; rivi < suodin.getRivienMaara(); rivi++) {
            for (int sarake = 0; sarake < suodin.getSarakkeidenMaara(); sarake++) {
                assertEquals("Suodinmatriisin alkiolla virheellinen arvo.", 
                             referenssiarvot[ (rivi * 3) + (sarake + 1) - 1],
                             suodin.getMatriisi().getAlkio(rivi, sarake), tarkkuus );
             }
        }
    }

}
