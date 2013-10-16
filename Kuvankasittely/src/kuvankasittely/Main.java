package kuvankasittely;

import javax.swing.*;
import kuvankasittely.logic.*;
import kuvankasittely.ui.*;

/**
 * Ohjelman pääluokka.
 * <p>
 * Luo ensin ohjelmalogiikan ja graafisen käyttöliittymän, minkä jälkeen
 * käynnistää käyttöliittymän.
 * 
 * @author      kimpe
 * @version     6.1
 * @since       2013-10-16
 */

public class Main {

    public static void main(String[] args) {
        
        Logiikka logiikka = new Logiikka();
        Kayttoliittyma graafinenKayttoliittyma = new Kayttoliittyma(logiikka);
        SwingUtilities.invokeLater(graafinenKayttoliittyma);
        
    }
}