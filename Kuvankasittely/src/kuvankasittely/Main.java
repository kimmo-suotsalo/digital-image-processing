package kuvankasittely;

import javax.swing.*;
import kuvankasittely.logic.*;
import kuvankasittely.ui.*;

/**
 * @author      kimpe
 * @version     4.0
 * @since       2013-09-24
 */

public class Main {

    public static void main(String[] args) {
        
        Logiikka logiikka = new Logiikka();
        Kayttoliittyma graafinenKayttoliittyma = new Kayttoliittyma(logiikka);
        SwingUtilities.invokeLater(graafinenKayttoliittyma);
        
    }
}