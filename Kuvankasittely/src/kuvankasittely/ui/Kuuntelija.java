package kuvankasittely.ui;

import java.awt.event.*;
import javax.swing.*;
import kuvankasittely.logic.*;

/**
 * @author      kimpe
 * @version     5.0
 * @since       2013-10-03
 */

public class Kuuntelija implements ActionListener {

    Logiikka logiikka;
    JButton painike;
    Paneeli paneeli;
    Ikkuna edeltaja, seuraaja;
    
    public Kuuntelija (Logiikka logiikka, JButton painike, Paneeli paneeli, Ikkuna edeltaja, Ikkuna seuraaja) {
        super();
        this.logiikka = logiikka;
        this.painike = painike;
        this.paneeli = paneeli;
        this.edeltaja = edeltaja;
        this.seuraaja = seuraaja;
    }
        
    @Override
    public void actionPerformed(ActionEvent tapahtuma) {
        if (edeltaja == null) {
            kasittelePaaikkunanPainike();
        } else {
            kasitteleSuodatusikkunanPainike();
        }                
    }
    
    private void kasittelePaaikkunanPainike() {
        switch ( painike.getActionCommand() ) {
            case "Lataa kuva":
                this.lataaKuva();
                break;
            case "Tummenna":
                logiikka.tummennaKuvaa( logiikka.getKuvat().get("muokattu") );
                break;
            case "Vaalenna":
                logiikka.vaalennaKuvaa( logiikka.getKuvat().get("muokattu") );
                break;
            case "Suodata":
                if (seuraaja != null) seuraaja.nayta();                
                break;
            case "Palauta":
                logiikka.palautaAlkuperainenKuva(paneeli);
                return;
            case "Tallenna kuva":
                logiikka.tallennaKuva();
                return;
            case "Lopeta":
                System.exit(0);
        }
        if (paneeli != null) logiikka.piirraKuva("muokattu", paneeli);   
    }

    private void lataaKuva() {
        JFileChooser tiedostovalitsin = new JFileChooser();
        if ( tiedostovalitsin.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            logiikka.lataaKuva( tiedostovalitsin.getSelectedFile() );
        }        
    }
    
    private void kasitteleSuodatusikkunanPainike() {
        switch ( painike.getActionCommand() ) {
            case "Nollaa":
                logiikka.setSuodin("Nolla", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Deltafunktio":
                logiikka.setSuodin("Deltafunktio", 1);                
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Alipäästö":
                logiikka.setSuodin("Alipäästö", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Ylipäästö":
                logiikka.setSuodin("Ylipäästö", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;
            case "Reunanetsintä":
                logiikka.setSuodin("Reunanetsintä", 1);
                edeltaja.getSeuraaja().paivitaSuodin( logiikka.getSuodin() );
                break;                
            case "Suodata":
                logiikka.suodataKuva( logiikka.getKuvat().get("muokattu") );
                if (paneeli != null) logiikka.piirraKuva("muokattu", paneeli);                   
                break;
            case "Sulje":
                edeltaja.getSeuraaja().piilota();
        }
    }
    
}
