package Logica;
import java.util.ArrayList;

/**
 * Dit is de logische klasse voor het TeamSpel.
 * @author Leander Clappaert
 * @version 31/05/2015
 */
public class TeamSpel extends Spel {
    private TeamSpel tt; private Rad r; private Bankroet b; private Verliesbeurt v; private Geldbeurt g;   
    private int jongsteSpeler;
    
    /**
     * Constructor voor klasse TeamSpel.
     * @param spelerNaam naam van de speler
     * @param leeftijd leeftijd van de speler
     */
    public TeamSpel(String spelerNaam, int leeftijd) {
        super (spelerNaam, leeftijd);
    }
    
    /**
     * Niveau van de thema's wordt bepaald aan de hand van de leeftijd van de jongste speler.
     */
    private void bepaalNiveau() {
        if (jongsteSpeler >= 6 && jongsteSpeler <= 12) tt.setNiveau("kind");
        else if (jongsteSpeler >= 13 && jongsteSpeler <= 17) tt.setNiveau("jeugd");
        else tt.setNiveau("volwassene"); 
    }
    
    /**
     * Startmethode die bij aanvang van nieuw spel alles reset.
     * @param tt object van de klasse TeamSpel
     */
    public void start(TeamSpel tt) {
       this.tt = tt;
       this.r = new Rad();
       this.b = new Bankroet(tt);
       this.v = new Verliesbeurt(tt);
       this.g = new Geldbeurt(tt);
       
       tt.reset();     //reset: huidigSaldo, totaalSaldo, bedragGekozen en mask
       bepaalNiveau(); //spelniveau wordt bepaald
    }

    /**
     * Controleert opdat het thema nog niet gespeeld werd.
     * @param controleLijst lijst die reeds gespeelde themas bevat
     * @param themakeuze het random gekozen thema
     * @return 
     */
    public boolean checkKeuze(ArrayList controleLijst, String themakeuze) {
        if (controleLijst.contains(themakeuze)) return false;
        else {
            controleLijst.add(new String(themakeuze));
            return true;
        }
    }
    
    /**
     * Er wordt een random bedrag van het rad geselecteerd.
     */
    public void randomKeuzeRad() {
       int keuze = r.keuzeRad();         //geldbedrag wordt geselecteerd   
       if (keuze == -1) b.isBankroet();
       else if (keuze == -2) v.heeftVerliesbeurt();
       else g.heeftGeldbeurt(keuze);
    }
    
    /**
     * Setter die de leeftijd van de jongste speler in een veldvariabele instelt.
     * @param leeftijd leeftijd van de jongste speler
     */
    public void setLeeftijdJongsteSpeler(int leeftijd) {
        jongsteSpeler = leeftijd;
    }
    
    /**
     * resetmethode aan het begin van een nieuw spel.
     */
    public void rondeReset() {
        tt.setHuidigSaldoNaarNul();
        tt.setBedragGekozen(0);
        tt.setMask("");
        tt.maskeren(".");
    }
}