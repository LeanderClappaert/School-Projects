package Logica;
import Database.DatabaseConnectie;

/**
 * Hoofdklasse Spel die de basis legt voor de 2 subklasses: SpelSolo en TeamSpel.
 * @author Leander Clappaert
 * @version 31/05/2015
 */
public class Spel {
    private final int leeftijd;
    private final String spelerNaam;
    private int letterCount;
    private String niveau; private String teRadenWoord; private String teRadenThema; private String mask;
    private int huidigSaldo; private int totaalSaldo; private int bedragGekozen;
    private boolean isVerliesbeurt;
    
    /**
     * Constructor voor klasse Spel
     * @param spelerNaam naam van de speler
     * @param leeftijd leeftijd van de speler
     */
    protected Spel(String spelerNaam, int leeftijd) {
        this.spelerNaam = spelerNaam;
        this.leeftijd = leeftijd;
    }
    
    //
    //Oude methode die de themas, met mogelijke antwoorden, bevatte. Dit werd in de database opgenomen.
    //
    /*private String[][] themaMetAntwoorden() {
        String[][] themaMetVragen; 
        
        String[][] themaKind =
        {
            {"dier", "kikker", "giraf", "gorilla", "hamster", "kangoeroe"},
            {"animatiefilm", "cars", "aladdin", "finding nemo", "ice age"},
            {"TVserie", "sesamstraat", "barbapapa", "mega mindy", "tiktak"},
            {"kabouterfiguren", "plop", "kwebbel", "lui", "klus", "smal", "smul"}
        };
        
        String[][] themaJeugd = 
        {
            {"spelprogramma", "rad van fortuin", "blokken", "valkuil"},                                                      
            {"film", "lord of the rings", "avatar", "the hobbit", "fast and furious", "the hunger games", "lego the movie" },
            {"videospellen", "call of duty", "skyrim", "need for speed", "soldier of fortune", "grand theft auto"},
            {"automerken", "audi", "land rover", "suzuki", "toyota", "alfa romeo"},
            {"pokémon", "bulbasaur", "charmander", "pikachu", "mewto", "snorlax"}
        };
        
        String[][] themaVolwassene = 
        {
            {"TVserie", "greys anatomy", "scandal", "the mentalist", "castle", "grimm"},
            {"presidenten van Amerika", "george washington", "theodore roosevelt", "barack obama", "thomas jefferson", "john adams"},
            {"eerste ministers van België", "charles michel", "yves leterme", "wilfried martens", "leo tindermands", "gaston eyskens"},
            {"automerken", "subaru", "mitsubishi", "aston martin", "rolls royce", "bugatti veyron"}
        };
        
        if (niveau.equals("kind")) themaMetVragen = themaKind;
        else if (niveau.equals("jeugd")) themaMetVragen = themaJeugd;
        else themaMetVragen = themaVolwassene;        
        return themaMetVragen;
    }*/
    
    //
    // Vroegere methode die een random thema en woord koos.
    //
    /*protected void randomWoordPicker() {        
        String[][] keuze = themaMetAntwoorden();
        Random rand = new Random();
        
        int rangeThema = rand.nextInt(keuze.length);
        teRadenThema = keuze[rangeThema][0];
        int rangeWoord = rand.nextInt((keuze[rangeThema].length - 2) + 1) + 1;
        teRadenWoord = keuze[rangeThema][rangeWoord];
    }*/
    
    /**
     * Controleert opdat er nog medeklinkers te raden zijn van het te zoeken woord.
     * @return boolean ja of nee
     */
    public boolean controleResterendeMedeklinkers() {
        int aantalStartMedeklinkers = 0; int aantalMedeklinkersGebruikt = 0;
        
        for (int i = 0; i < teRadenWoord.length(); i++) {
            char controle = teRadenWoord.charAt(i);
            char maskControle = mask.charAt(i);
            if (controle != 97 && controle != 101 && controle != 105 && controle != 111 && controle != 117) aantalStartMedeklinkers++;
            if (maskControle != 97 && maskControle != 101 && maskControle != 105 && maskControle != 111 && maskControle != 117 && maskControle != 42) aantalMedeklinkersGebruikt++;
        }
        
        return aantalStartMedeklinkers == aantalMedeklinkersGebruikt;
    }
    
    /**
     * Controleert opdat er nog klinkers te raden zijn van het te zoeken woord.
     * @return boolean ja of nee
     */
    public boolean controleResterendeKlinkers() {
        int aantalStartKlinkers = 0; int aantalKlinkersGebruikt = 0;
        
        for (int i = 0; i < teRadenWoord.length(); i++) {
            char controle = teRadenWoord.charAt(i);
            char maskControle = mask.charAt(i);
            if (controle == 97 || controle == 101 || controle == 105 || controle == 111 || controle == 117) aantalStartKlinkers++;
            if (maskControle == 97 || maskControle == 101 || maskControle == 105 || maskControle == 111 || maskControle == 117) aantalKlinkersGebruikt++;
        }
        
        return aantalStartKlinkers == aantalKlinkersGebruikt;
    }
    
    /**
     * Het woord wordt gemaskeerd en wordt in het veld 'mask' gestoken.
     * @param letter de letter die wordt gegokt door de speler
     */
    public void maskeren(String letter) {
        String huidigeMask = mask;
        letterCount = 0;

        if (letter.equals(".")) { //mask initialiseren
            for (int i = 0; i < teRadenWoord.length(); i++) {
                char spatieChecken = teRadenWoord.charAt(i);
                if (letter.toLowerCase().charAt(0) == teRadenWoord.charAt(i)) {
                    mask = mask + letter.toLowerCase();
                    //tellen hoeveel keer de medeklinker voorkomt
                    letterCount++;   
                }
                else if (spatieChecken == 32) mask = mask + " ";
                else mask = mask + "*";
            }
        }
        else { //andere keren masker overwriten
            mask = "";
            for (int i = 0; i < teRadenWoord.length(); i++) {
                if (huidigeMask.charAt(i) == teRadenWoord.charAt(i)) mask = mask + huidigeMask.charAt(i);
                else if (letter.toLowerCase().charAt(0) == teRadenWoord.charAt(i)) {
                    mask = mask + letter.toLowerCase();
                    //tellen hoeveel keer de medeklinker voorkomt
                    letterCount++;
                }
                else mask = mask + "*";
            }
        }
    }
    
    /**
     * checken ofdat de geldige (mede)klinker in het woord voorkomt.
     * @param ingave letter die wordt gegokt
     * @return juist of fout gegokt
     */
    public boolean gokValideren(String ingave) { 
        char letter = ingave.toLowerCase().charAt(0);
        
        for (int i = 0; i < teRadenWoord.length(); i++) {
            if (letter == teRadenWoord.charAt(i)) return true;
        }
        
        return false;
    }
    
    /**
     * Checken ofdat de speler juist gokt (=wint) of niet.
     * @param woord woord gegokt door de speler
     * @return juist of fout gegokt
     */    
    public boolean woordGokken(String woord) {
        return woord.toLowerCase().equals(teRadenWoord);
    }
    
    /**
     * Kijkt ofdat er wel een medeklinker ingegeven werd
     * @param ingave letter ingegeven door de gebruiker
     * @return geldige of ongeldige letter ingegeven
     */
    public boolean checkIngaveMedeklinker(String ingave) {
        //checken of er slechts 1 letter ingegeven wordt.
        if (ingave.length() != 1) return false;
        else {
            char medeklinker = ingave.toLowerCase().charAt(0);
            //checken of het een letter van het alfabet is
            if (medeklinker >= 97 && medeklinker <= 122) {
                //checken of medeklinker reeds in de mask staat
                for (int i = 0; i < teRadenWoord.length(); i++) {
                    if (medeklinker == mask.charAt(i)) return false;
                }
                //checken of het GEEN klinker is.
                return medeklinker != 97 && medeklinker != 101 && medeklinker != 105 && medeklinker != 111 && medeklinker != 117;
            }
        }
        
        return false;
    }
    
    /**
     * Kijkt ofdat er wel een klinker ingegeven werd
     * @param ingave letter ingegeven door de gebruiker
     * @return geldige of ongeldige letter ingegeven
     */
    public boolean checkIngaveKlinker(String ingave) {
        //checken of er slechts 1 letter ingegeven wordt.
        if (ingave.length() != 1) return false;
        else {
            char klinker = ingave.toLowerCase().charAt(0);
            //checken of het een letter van het alfabet is + checken of het GEEN klinker is.
            if (klinker >= 97 && klinker <= 122) {
                //checken of klinker reeds in de mask staat
                for (int i = 0; i < teRadenWoord.length(); i++) {
                    if (klinker == mask.charAt(i)) return false;
                }
                //checken of het WEL een klinker is.
                return klinker == 97 || klinker == 101 || klinker == 105 || klinker == 111 || klinker == 117;
            }
        }
        
        return false;
    }
    
    /**
     * Getter die een woord returned.
     * @return veld met het te raden woord
     */
    public String getWoord() {
        return teRadenWoord;
    }
    
    /**
     * Setter die het te raden woord in een veldvariabele instelt.
     * @param woord het bepaalde te raden woord
     */
    public void setWoord(String woord) {
        teRadenWoord = woord;
    }
    
    /**
     * Getter die een thema returned.
     * @return veld met het te raden thema
     */
    public String getThema() {
        return teRadenThema;
    }
    
    /**
     * Setter die het te raden thema in een veldvariabele instelt.
     * @param thema het bepaalde te raden thema
     */
    public void setThema(String thema) {
        teRadenThema = thema; 
    }
    
    /**
     * Getter die de naam van de speler returned.
     * @return de naam van de speler
     */
    public String getSpelerNaam() {
        return spelerNaam;
    }
    
    /**
     * Getter die de leeftijd van de speler returned.
     * @return de leeftijd van de speler
     */
    public int getLeeftijd() {
        return leeftijd;
    }
    
    /**
     * Getter die het niveau returned.
     * @return het niveau van de themas
     */
    public String getNiveau() {
        return niveau;
    }
    
    /**
     * Setter die het niveau in een veldvariabele instelt.
     * @param ingave het bepaalde niveau
     */
    protected void setNiveau(String ingave) {
        niveau = ingave;
    }
    
    /**
     * Getter die de mask returned.
     * @return het gemaskeerde woord
     */
    public String getMask() {
        return mask;
    }
    
    /**
     * Setter die de mask in een veldvariabele instelt. 
     * @param mask de bepaalde mask
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
    
    /**
     * Getter die het aantal letters van het te zoeken woord returned. 
     * @return aantal letters van het woord
     */
    public int getLetterCount() {
        return letterCount;
    }
    
    /**
     * Getter die huidig saldo van de huidige beurt returned.
     * @return saldo voolopig verdient deze ronde.
     */
    public int getHuidigSaldo() {
        return huidigSaldo;
    }
    
    /**
     * Setter die saldo toevoegt aan het huidige ronde saldo indien goed gegokt.
     * @param multiply aantal keren dat het ingegeven letter voorkomt
     */
    public void setHuidigSaldo(int multiply) {
        huidigSaldo += (bedragGekozen * multiply);
    }

    /**
     * Setter die huidig saldo reset naar 0.
     */
    public void setHuidigSaldoNaarNul() {
        huidigSaldo = 0;
    }
    
    /**
     * Setter die huidig saldo vermindert indien er een klinker gekocht werd.
     */
    public void setKlinkerGekocht() {
        huidigSaldo -= 2000;
    }
    
    /**
     * Getter die het saldo, dat reeds verdient werd door ronde(n) te winnen, returned.
     * @return het totale saldo
     */
    public int getTotaalSaldo() {
        return totaalSaldo;
    }
    
    /**
     * Setter die het totale saldo update bij winst van een ronde.
     */
    public void setTotaalSaldo() {
        totaalSaldo += huidigSaldo;
    }

    /**
     * Getter die het bedrag, random gekozen door het rad, returned.
     * @return het mogelijks te winnen bedrag
     */
    public int getBedragGekozen() {
        return bedragGekozen;
    }
    
    /**
     * Setter die het door het rad random gekozen bedrag op een veldvariabele instelt.
     * @param keuze 
     */
    protected void setBedragGekozen(int keuze) {
        bedragGekozen = keuze;
    }
    
    /**
     * Getter die bepaalt of er een verliesbeurt gekozen werd.
     * @return verliesbeurt: ja of nee
     */
    public boolean getMagSpelen() {
        return isVerliesbeurt;
    }
      
    /**
     * Setter die de veldvariabele verliesbeurt instelt.
     * @param optie boolean: is een verliesbeurt of niet
     */
    protected void setMagSpelen(boolean optie) {
        isVerliesbeurt = optie;
    }
    
    /**
     * resetmethode aan het begin van een nieuw spel.
     */
    protected void reset() {
        huidigSaldo = 0;
        totaalSaldo = 0;
        bedragGekozen = 0;
        mask = "";
    }
    
    /**
     * Methode returned grootste saldo ooit behaald in één ronde over alle spellen.
     * @param db de huidige database (connectie) 
     * @param spelernaam naam van de speler wiens records upgeload moeten worden
     * @param leeftijd leeftijd van de speler
     * @return het grootste saldo
     */
    public int getMaxSaldo(DatabaseConnectie db, String spelernaam, int leeftijd) {
        int id = db.checkID(spelernaam, leeftijd);
        return db.getMaxSaldo(id);
    }
    
    /**
     * Deze methode returned het huidige overall saldo (=winst van alle spellen ooit gespeeld). 
     * @param db de huidige database (connectie) 
     * @param spelernaam naam van de speler wiens records upgeload moeten worden
     * @param leeftijd leeftijd van de speler
     * @return het huidige overall saldo
     */
    public int getOverallSaldo(DatabaseConnectie db, String spelernaam, int leeftijd) {
        int id = db.checkID(spelernaam, leeftijd);
        return db.getOverallSaldo(id);
    }
    
    /**
     * Methode checkt of het huidig gewonnen bedrag het grootste bedrag ooit gewonnen is.
     * @param huidigSaldo het saldo dat werd verdiend deze ronde
     * @param db de huidige database (connectie) 
     * @param spelernaam naam van de speler wiens records upgeload moeten worden
     * @param leeftijd leeftijd van de speler
     * @param maxSaldo het huidige, grootste bedrag ooit behaald.
     */
    public void updateRecordsMaxSaldo(int huidigSaldo, DatabaseConnectie db, String spelernaam, int leeftijd, int maxSaldo) {
        if (huidigSaldo > maxSaldo) {
            int id = db.checkID(spelernaam, leeftijd);
            db.updateMaxSaldo(id, huidigSaldo); 
        }
    }
    
    /**
     * Deze methode update het overallSaldo (=winst van alle spellen ooit gespeeld).
     * @param huidigSaldo het saldo dat werd verdiend deze ronde
     * @param db de huidige database (connectie) 
     * @param spelernaam naam van de speler wiens records upgeload moeten worden
     * @param leeftijd leeftijd van de speler
     */
    public void updateRecordsOverallSaldo(int huidigSaldo, DatabaseConnectie db, String spelernaam, int leeftijd) {
        int id = db.checkID(spelernaam, leeftijd);
        int overall = db.getOverallSaldo(id);
        db.updateOverallSaldo(id, huidigSaldo, overall);
    }
}
