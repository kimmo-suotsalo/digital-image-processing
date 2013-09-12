package kuvankasittely;

import kuvankasittely.logic.*;
import javax.swing.*;
import kuvankasittely.ui.*;

public class Main {

    public static void main(String[] args) {
        
        Logiikka logiikka = new Logiikka();
        Kayttoliittyma graafinenKayttoliittyma = new Kayttoliittyma(logiikka);
        SwingUtilities.invokeLater(graafinenKayttoliittyma);
        
    }
}