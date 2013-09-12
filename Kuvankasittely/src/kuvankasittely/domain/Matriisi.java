package kuvankasittely.domain;

public class Matriisi {
    
    private int rivienMaara;
    private int sarakkeidenMaara;
    private int[][] alkiot;
    
    public Matriisi(int rivienMaara, int sarakkeidenMaara) {
        if (rivienMaara > 0 && sarakkeidenMaara > 0) {
            this.rivienMaara = rivienMaara;
            this.sarakkeidenMaara = sarakkeidenMaara;
            this.alkiot = new int[rivienMaara][sarakkeidenMaara];
        }
    }
    
    public boolean setAlkio(int rivi, int sarake, int arvo) {
        try {
            alkiot[rivi][sarake] = arvo;
            return true;
        } catch (IndexOutOfBoundsException poikkeus) {
            return false;
        }
    }
    
    public boolean setAlkiot(int arvo) {
        for (int rivi = 0; rivi < rivienMaara; rivi++) {
            for (int sarake = 0; sarake < sarakkeidenMaara; sarake++) {
                if ( !setAlkio(rivi, sarake, arvo) ) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getAlkio(int rivi, int sarake) {
        int alkio;
        try {
            return alkiot[rivi][sarake];
        } catch (IndexOutOfBoundsException poikkeus) {
            return -1;
        }
    }
    
    public int[][] getAlkiot() {
        return alkiot;
    }
    
    public int getRivienMaara() {
        return rivienMaara;
    }
    
    public int getSarakkeidenMaara() {
        return sarakkeidenMaara;
    }
    
}
