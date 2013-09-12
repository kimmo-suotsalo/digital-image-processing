package kuvankasittely.ui;

import java.awt.event.*;
import javax.swing.*;
import kuvankasittely.logic.*;

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
        if ( toiminto.equals("Lataa kuva") ) {
            logiikka.lataaKuva();
            logiikka.piirraKuva(paneeli);
        } else if ( toiminto.equals("Muokkaa kuvaa") ) {
            System.out.println("Painoit nappia \"Muokkaa kuvaa\" ");
        } else if ( toiminto.equals("Tallenna kuva") ) {
            logiikka.tallennaKuva();
        } else if ( toiminto.equals("Lopeta") ) {
            System.exit(0);
        }
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
