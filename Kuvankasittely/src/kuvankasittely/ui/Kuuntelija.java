package kuvankasittely.ui;

import java.awt.event.*;
import javax.swing.*;
import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     4.1
 * @since       2013-09-26
 */

public class Kuuntelija implements ActionListener {

    String toiminto;
    Logiikka logiikka;
    Paneeli paneeli;
    
    public Kuuntelija (JButton painike, Logiikka logiikka, Paneeli paneeli) {
        super();
        this.toiminto = painike.getActionCommand();
        this.logiikka = logiikka;
        this.paneeli = paneeli;
    }
    
    @Override
    public void actionPerformed(ActionEvent tapahtuma) {        
        switch (toiminto) {
            case "Lataa kuva":                
                logiikka.lataaKuva();
                break;
            case "Tummenna":
                logiikka.tummennaKuvaa( logiikka.getKuvat().get("alkuperainen") );
                break;
            case "Vaalenna":
                logiikka.vaalennaKuvaa( logiikka.getKuvat().get("alkuperainen") );
                break;
            case "Suodata":
                logiikka.suodataKuva( logiikka.getKuvat().get("alkuperainen") );
                break;
            case "Tallenna kuva":
                logiikka.tallennaKuva();
                return;
            case "Lopeta":
                System.exit(0);
        }
        logiikka.piirraKuva(paneeli);        
    }
    
    public String getToiminto() {
        return toiminto;
    }
    
    public Logiikka getLogiikka() {
        return logiikka;
    }
    
    public Paneeli getPaneeli() {
        return paneeli;
    }
    
}
