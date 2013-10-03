package kuvankasittely;

import javax.swing.*;
import kuvankasittely.logic.*;
import kuvankasittely.ui.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Main {

    public static void main(String[] args) {
        
        Logiikka logiikka = new Logiikka();
        Kayttoliittyma graafinenKayttoliittyma = new Kayttoliittyma(logiikka);
        SwingUtilities.invokeLater(graafinenKayttoliittyma);
        
    }
}