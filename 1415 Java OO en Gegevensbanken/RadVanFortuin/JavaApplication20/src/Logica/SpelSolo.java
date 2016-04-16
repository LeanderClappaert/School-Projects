package Logica;

/**
 * Dit is de logische klasse voor het SoloSpel. 
 * @author Leander Clappaert
 * @version 31/05/2015
 */
public class SpelSolo extends Spel {
    private final int BEURTEN = 10;
    private int beurtenResterende = BEURTEN;
    private SpelSolo t; private Rad r; private Bankroet b; private Verliesbeurt v; private Geldbeurt g;
    
    /**
     * Constructor voor klasse SpelSolo.
     * @param spelerNaam naam van de speler
     * @param leeftijd leeftijd van de speler
     */
    public SpelSolo(String spelerNaam, int leeftijd) {
        super(spelerNaam, leeftijd);
    }

    /**
     * Niveau van de thema's wordt bepaald aan de hand van de leeftijd van de speler.
     */
    private void bepaalNiveau() {
        if (t.getLeeftijd() >= 6 && t.getLeeftijd() <= 12) t.setNiveau("kind");
        else if (t.getLeeftijd() >= 13 && t.getLeeftijd() <= 17) t.setNiveau("jeugd");
        else t.setNiveau("volwassene");
    }
    
    /**
     * Startmethode die bij aanvang van nieuw spel alles reset.
     * @param t object van de klasse TeamSpel
     */
    public void start(SpelSolo t) {
       this.t = t;
       this.r = new Rad();
       this.b = new Bankroet(t);
       this.v = new Verliesbeurt(t);
       this.g = new Geldbeurt(t);
       
       t.reset();                       //saldos + mask worden gereset
       bepaalNiveau();                  //niveau wordt bepaald (via leeftijd)
       beurtenResterende = BEURTEN;     //nieuwe beurten om mee te spelen
    }
    
    /**
     * Er wordt een random bedrag van het rad geselecteerd.
     */
    public void randomKeuzeRad() {
       int keuze = r.keuzeRad();        //geldbedrag wordt geselecteerd        
       if (keuze == -1) b.isBankroet();
       else if (keuze == -2) v.heeftVerliesbeurt();
       else g.heeftGeldbeurt(keuze);
    }

    /**
     * Getter die aantal spelers returned.
     * @return 1 speler
     */    
    public int getAantalSpelers() {
        return 1;
    }
    
    /**
     * Getter die aantal startbeurten returned.
     * @return vooraf bepaald aantal beurten
     */
    public int getBeurten() {
        return BEURTEN;
    } 
    
    /**
     * Getter die aantal resterende beurten returned.
     * @return aantal beurten
     */
    public int getBeurtenResterende() {
        return beurtenResterende;
    }
    
    /**
     * Setter die aantal resterende beurten instelt.
     * @param aftrekken aantal beurten dat er af gaan
     */
    public void setBeurtenResterende(int aftrekken) {
        beurtenResterende -= aftrekken;
    }
}
