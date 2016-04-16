package Logica;

/**
 * Deze klasse wordt opgeroepen indien 'verliesbeurt' geselecteerd werd op het rad.
 * @author Leander Clappaert
 * @version 24/05/2015
 */
public class Verliesbeurt {
    private final SpelSolo t;
    private final TeamSpel tt;

    /**
     * Constructor voor klasse Verliesbeurt. Dit wordt via SpelSolo geaccessed.
     * @param t speler (object) van klasse SpelSolo
     */
    public Verliesbeurt(SpelSolo t) {
        this.t = t;
        this.tt = null;
    }
    
    /**
     * Constructor voor klasse Verliesbeurt. Dit wordt via TeamSpel geaccessed.
     * @param tt speler (object) van klasse TeamSpel
     */
    public Verliesbeurt(TeamSpel tt) {
        this.tt = tt;
        this.t = null;
    }
    
    /**
     * Zorgt ervoor dat de speler 1 ronde niet kan spelen.
     */
    public void heeftVerliesbeurt() {
        if (t != null) {
            t.setBedragGekozen(0);
            t.setMagSpelen(false);
            t.setBeurtenResterende(1);
        }
        else {
            tt.setBedragGekozen(0);
            tt.setMagSpelen(false);
        }
    }
}