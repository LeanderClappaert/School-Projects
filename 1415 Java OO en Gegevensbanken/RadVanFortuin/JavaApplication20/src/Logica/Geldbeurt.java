package Logica;

/**
 * Deze klasse wordt aangeroepen indien er een geldbedrag geselecteerd werd op het rad.
 * @author Leander Clappaert
 * @version 24/05/2015
 */
public class Geldbeurt {
    private final SpelSolo t;
    private final TeamSpel tt;
    
    /**
     * Constructor voor klasse Geldbeurt. Dit wordt via SpelSolo geaccessed.
     * @param t speler (object) van klasse SpelSolo
     */
    public Geldbeurt(SpelSolo t) {
        this.t = t;
        this.tt = null;
    }
    
    /**
     * Constructor voor klasse Geldbeurt. Dit wordt via TeamSpel geaccessed.
     * @param tt speler (object) van klasse TeamSpel
     */
    public Geldbeurt(TeamSpel tt) {
        this.tt = tt;
        this.t = null;
    }
    
    /**
     * Stelt het te winnen bedrag in voor 1 ronde.
     * @param keuze geldbedrag random gekozen via klasse Rad
     */
    public void heeftGeldbeurt(int keuze) {
        if (t != null) {
            t.setBedragGekozen(keuze);
            t.setMagSpelen(true); 
        }
        
        else {
            tt.setBedragGekozen(keuze);
            tt.setMagSpelen(true);
        }
    }
    
    /**
     * Geld wordt vermenigvuldigd met het aantal keer dat de gegokte medeklinker voorkomt.
     * @param letterCount 
     */
    public void geldbeurtMultiply(int letterCount) {
        if (t != null) t.setHuidigSaldo(letterCount);  
        else tt.setHuidigSaldo(letterCount);
    }
}