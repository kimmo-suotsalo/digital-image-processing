package kuvankasittely.logic;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import kuvankasittely.ui.*;

public class Logiikka {
    
    private HashMap<String,File> tiedostot;
    private HashMap<String,BufferedImage> kuvat;
    
    public Logiikka() {
        this.tiedostot = new HashMap<String,File>();
        this.kuvat = new HashMap<String,BufferedImage>();
    }
    
    public boolean lataaKuva() {
        tiedostot.put("lukutiedosto", new File("../dokumentointi/luokkakaavio.PNG") );
        try {
            kuvat.put("alkuperainen", ImageIO.read( tiedostot.get("lukutiedosto") ) );
            return true;
        } catch (IOException poikkeus) {
            return false;
        }
    }
    
    public void piirraKuva(Paneeli paneeli) {
        paneeli.asetaKuva( kuvat.get("alkuperainen") );
        paneeli.repaint();
    }

    public boolean tallennaKuva() {
        tiedostot.put("kirjoitustiedosto", new File("../../muokattuKuva.png") );        
        try {
            tiedostot.get("kirjoitustiedosto").createNewFile();
            ImageIO.write(kuvat.get("alkuperainen"), "PNG", tiedostot.get("kirjoitustiedosto") );
            return true;
        } catch (IOException poikkeus) {
            return false;
        }
    }
    
    public HashMap<String,File> getTiedostot() {
        return tiedostot;
    }
    
    public HashMap<String,BufferedImage> getKuvat() {
        return kuvat;
    }
    
}
