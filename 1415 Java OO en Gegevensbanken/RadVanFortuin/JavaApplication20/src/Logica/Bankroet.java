package Logica;

/**
 * Deze klasse wordt opgeroepen indien 'bankroet' geselecteerd werd op het rad.
 * @author Leander Clappaert
 * @version 25/05/2015
 */
public class Bankroet {
    private final SpelSolo t;
    private final TeamSpel tt;
    
    /**
     * Constructor voor klasse Bankroet. Dit wordt via SpelSolo geaccessed.
     * @param t speler (object) van klasse SpelSolo
     */
    public Bankroet(SpelSolo t) {
        this.t = t;
        this.tt = null;
    }
    
    /**
     * Constructor voor klasse Bankroet. Dit wordt via TeamSpel geaccessed.
     * @param tt speler (object) van klasse TeamSpel
     */
    public Bankroet(TeamSpel tt) {
        this.t = null;
        this.tt = tt;
    }
    
    /**
     * Stelt de saldo van de speler(s) opnieuw in.
     */
    public void isBankroet() {
        if (t != null) {
            t.setBedragGekozen(0);
            t.setHuidigSaldoNaarNul();
            t.setMagSpelen(true);   
        }
        else {
            tt.setBedragGekozen(0);
            tt.setHuidigSaldoNaarNul();
            tt.setMagSpelen(true);  
        }
    }
}