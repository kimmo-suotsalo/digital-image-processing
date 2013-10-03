package kuvankasittely.ui;

import javax.swing.*;
import kuvankasittely.domain.*;
import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public interface Ikkuna {
    
    public void teeAlustukset();

    public void lisaaKuuntelijat(Logiikka logiikka);
    
    public void setKehys(JFrame kehys);
    
    public void setEdeltaja(Ikkuna edeltaja);        
    
    public void setSeuraaja(Ikkuna seuraaja);        
    
    public void setPaneeli(Paneeli paneeli);

    public JFrame getKehys();
    
    public Ikkuna getEdeltaja();
    
    public Ikkuna getSeuraaja();
    
    public Paneeli getPaneeli();
    
    public void paivitaSuodin(Suodin suodin);    
    
    public void nayta();    
    
    public void piilota();
    
}