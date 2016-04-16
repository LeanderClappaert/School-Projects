package Grafisch;
import Database.DatabaseConnectie;
import Logica.TeamSpel;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * De grafische layout van het teamspel zelf.
 * @author Leander Clappaert
 * @version 29/05/2015
 */
public class TeamSpelPanel extends javax.swing.JPanel {
    private final JFrame parentFrame;
    private TeamSpel[] record;
    private final int aantalSpelers;
    private final int[] leeftijden;
    private String[] themas; 
    private final String[] namen;
    private int rondeNummer; private int y;
    private boolean heeftGedraaid;
    private String input;
    private final DatabaseConnectie db;
    private ArrayList controleLijst;
    
    /**
     * Creates new form SpelSoloPanel
     */
    public TeamSpelPanel(JFrame frame, String[] namen, int[] leeftijden, int aantalSpelers, DatabaseConnectie db) {
        initComponents();
        this.parentFrame = frame;
        this.namen = namen;
        this.leeftijden = leeftijden;
        this.aantalSpelers = aantalSpelers;
        this.db = db;
        controleLijst = new ArrayList();
        record = new TeamSpel[aantalSpelers];
        for (int i = 0; i < aantalSpelers; i++) {
            record[i] = new TeamSpel(namen[i], leeftijden[i]);
        }
        y = 0; //beginnen met speler 1
        themas = new String[aantalSpelers];
        spelStart();
    }
    
    /**
     * Stelt de start parameters in voor een nieuw spel.
     */
    private void spelStart() {
        //kijken hoeveel de jongste leeftijd bedraagt
        int jongsteLeeftijd = 999;
        for (int i = 0; i < aantalSpelers; i++) {
            if (record[i].getLeeftijd() < jongsteLeeftijd) jongsteLeeftijd = record[i].getLeeftijd(); 
        }   
        //jongste speler overal instellen = niveau instellen alle spelers
        for (int i = 0; i < aantalSpelers; i++) {
            record[i].setLeeftijdJongsteSpeler(jongsteLeeftijd);   
        }
        //alles resetten + niveau bepalen
        for (int i = 0; i < aantalSpelers; i++) {
            record[i].start(record[i]);
        }
      
        //op het scherm weergeven
        rondeNummer = 0;   
        startPanelScherm();
    }
    
    /**
     * Het thema en woord wordt random uit de database gehaald.
     * Het thema moet voor elke ronde uniek zijn.
     * Indien thema goedgekeurd is, wordt deze samen met het woord in de andere spelers ingeladen.
     */
    private void setThemaEnWoord() {
        String thema;
        boolean geldig = false;
        do {
            thema = db.getRandomThema(record[0].getNiveau());
            geldig = record[0].checkKeuze(controleLijst, thema);
        } while(!geldig);
        
        String woord = db.getRandomWoord(record[0].getNiveau(), thema);
        record[0].setThema(thema);
        record[0].setWoord(woord);
        
        //indien goedgekeurd wordt alles gekopiëerd naar elke speler
        for (int i = 1; i < aantalSpelers; i++) {
            record[i].setThema(record[0].getThema());
            record[i].setWoord(record[0].getWoord());
        }
    }

    /**
     * Stelt het thema en woord in dat random uit de database werd gehaald.
     */
    private void rondeStart() {
        rondeNummer++;
        setThemaEnWoord();
        // alles resetten
        for (int i = 0; i < aantalSpelers; i++) {
            record[i].rondeReset();             
        }
        //op het scherm weergeven
        jLabelRondeNummer.setText("Rondenummer: " + rondeNummer);
        jLabelHuidigSaldo.setText("Huidig Saldo: " + record[y].getHuidigSaldo());
        jLabelThema.setText("Thema = " + record[0].getThema());
        jLabelMask.setText(record[0].getMask()); 
        jTextArea1.setText("We starten bij " + record[y].getSpelerNaam() + ". Spin het rad!");
    }
    
    /**
     * Begin van de ronde. Dit wordt geactiveerd na de 1e spinbeurt.
     */
    private void eenRonde() {
        //kijken of er op de spin button werd gedrukt.
        if (heeftGedraaid) {
            //spinknop opnieuw resetten.
            heeftGedraaid = false;
            //verliesbeurt of niet
            if (record[y].getMagSpelen()) {
                jButtonSpin.setVisible(false);
                jLabelRad.setVisible(false);
                //zijn er nog medeklinkers over om te raden?
                if (!record[y].controleResterendeMedeklinkers()) {
                    if (record[y].getBedragGekozen() > 0) {
                        jLabelMask.setText(record[y].getMask());
                        jTextArea1.setText
                        (
                            record[y].getSpelerNaam() + "(" + record[y].getLeeftijd() + "): " + record[y].getHuidigSaldo() + " euro in de pot.\n" +
                            ">> Geldbeurt: " + record[y].getBedragGekozen() + " euro.\n" +
                            record[y].getSpelerNaam() + ", je mag een medeklinker raden: "
                        );
                    }
                    else {
                        jTextArea1.setText(
                            "Helaas! Het rad toont BANKROET aan.\n" +
                            "Je kan deze ronde geen geld verdienen!\n" +
                            "Je huidig saldo wordt gereset naar " + record[y].getHuidigSaldo() + " euro.\n" +
                            "Je kan echter wel nog voortspelen.\n" + 
                            record[y].getSpelerNaam() + ", je mag een medeklinker raden: "
                        );
                        jLabelHuidigSaldo.setText("Huidig saldo: " + record[y].getHuidigSaldo());
                    }
                    //GOTO ingaveMedeklinkerIsGeldig() VIA BUTTON/TEXTFIELD
                }
                else {
                    jLabelMask.setText(record[y].getMask());
                    jTextArea1.setText
                    (
                        "Alle medeklinkers zijn reeds geraden!\n" +
                        "Je mag nog 1 keer raden! Wat is het antwoord denk je?\n"
                    );
                    jInputButtonFinal.setVisible(true);
                    jTextField1.setVisible(true);
                    jLabelInput.setVisible(true);
                    //GOTO gokMetEinde() VIA BUTTON/TEXTFIELD
                }
            }
            else {
                //huidige mask kopiëren naar andere
                String huidigeMask = record[y].getMask();      
                if (y <= (aantalSpelers-2)) y++;
                else y = 0;
                jTextArea1.setText
                (
                    "Helaas! Het rad toont een VERLIESBEURT aan.\n" +
                    "Het is nu aan " + record[y].getSpelerNaam() + ".\n" +
                    "Spinnen maar!"
                );
                record[y].setMask(huidigeMask);
                jLabelInput.setVisible(false);
                jInputButton.setVisible(false);
                jTextField1.setVisible(false);
            }
        }
    }
    
    /**
     * Vervolg van de ronde van zodra er een geldige medeklinker werd ingegeven door de speler.
     */
    private void ingaveMedeklinkerIsGeldig() {
        //kijken opdat de medeklinker juist gegokt is of niet
        boolean gok = record[y].gokValideren(input);
        //indien niet = beurt kwijtgespeeld en opnieuw gokken
        if (!gok) {
            String huidigeMask = record[y].getMask();      
            if (y <= (aantalSpelers-2)) y++;
            else y = 0;
            jTextArea1.setText
            (
                "Deze medeklinker zit er niet bij!\n" +
                "Het is nu aan " + record[y].getSpelerNaam() + ".\n" +
                "Spinnen maar!"
            );
            jLabelInput.setVisible(false);
            jTextField1.setVisible(false);
            jInputButton.setVisible(false);
            jLabelRad.setVisible(true);
            jButtonSpin.setVisible(true);
            record[y].setMask(huidigeMask);
            //labels opnieuw instellen
            resetLabels();
        }            
        //indien wel = opnieuw maskeren met de juiste medeklinker(s) zichtbaar
        else {
            record[y].maskeren(input);
            record[y].setHuidigSaldo(record[y].getLetterCount());
            jLabelHuidigSaldo.setText("Huidig Saldo: " + record[y].getHuidigSaldo());
            jLabelMask.setText(record[y].getMask());

            //je kan enkel klinker kopen indien huidig saldo >= 2000 euro en er nog klinkers te koop zijn.
            if (record[y].getHuidigSaldo() >= 2000) {
                //zijn er nog klinkers over?
                if (!record[y].controleResterendeKlinkers()) {
                    jTextArea1.setText("Wens je een klinker te kopen (kost 2000)?");
                    jButtonJa.setVisible(true);
                    jButtonNee.setVisible(true);
                    jLabelKlinker.setVisible(true);
                    jTextField1.setVisible(false);
                    jInputButton.setVisible(false);
                    jLabelInput.setVisible(false);
                    //GOTO klinkerKopen() VIA BUTTON/TEXTFIELD IF 'JA'
                }
                else {
                    jTextArea1.setText
                    (
                        "Je kan geen klinkers meer kopen: alle klinkers zijn \nreeds weergegeven!\n" +
                        "Wat is het antwoord, denk je?"
                    );
                    jInputButton.setVisible(false);
                    jInputButtonGok.setVisible(true);
                    jTextField1.setVisible(true); 
                    //GOTO gokZonderEinde() VIA BUTTON/TEXTFIELD
                }                
            }
            else {
                jTextArea1.setText("Wat is het antwoord, denk je? ");
                jInputButton.setVisible(false);
                jInputButtonGok.setVisible(true);
                jTextField1.setVisible(true);  
                //GOTO gokZonderEinde() VIA BUTTON/TEXTFIELD
            }
        }
    }
    
    /**
     * Indien men een klinker wil kopen wordt deze methode geactiveerd.
     */
    private void klinkerKopen() {
        jTextField1.setVisible(true);
        jInputButton.setVisible(true);
        jLabelInput.setVisible(true);
        jInputButtonKlinker.setVisible(true);
        record[y].setKlinkerGekocht(); //-2000
        jLabelHuidigSaldo.setText("Huidig saldo: " + record[y].getHuidigSaldo());
        jTextArea1.setText("Welke klinker wil je kopen?");
        //GOTO klinkerValideren() via BUTTON/TEXTFIELD
    }
    
    /**
     * Bepaalt of de ingegeven medeklinker in het te raden woord voorkomt of niet.
     */
    private void klinkerValideren() {
        jInputButtonKlinker.setVisible(false);
        boolean gok = record[y].gokValideren(input); //kijken opdat de klinker juist gegokt is of niet
        
        if (!gok) { //indien niet
            jTextArea1.setText
            (
                "Deze klinker zit er niet bij!\n" +
                "Wat is het antwoord, denk je? "       
            );
            jInputButton.setVisible(false);
            jInputButtonGok.setVisible(true);
            jTextField1.setVisible(true); 
        }
        else { //indien wel = opnieuw maskeren met juiste klinker(s)
            record[y].maskeren(input);
            jLabelMask.setText(record[y].getMask());
            jTextArea1.setText("Wat is het antwoord, denk je? ");
            jInputButton.setVisible(false);
            jInputButtonGok.setVisible(true);
            jTextField1.setVisible(true); 
        }
    }
    
    /**
     * Deze methode evalueert de gok van de speler. Het spel EINDIGT NIET na een foute gok.
     * @param woordGok het woord dat door de speler werd gegokt
     */
    private void gokZonderEinde(boolean woordGok) {
        //het antwoord raden na goede ingave medeklinker
        //indien fout = beurt verminderen en opnieuw een medeklinker raden
        //input elementen weghalen
        jInputButtonGok.setVisible(false);
        jTextField1.setVisible(false);
        jLabelInput.setVisible(false);
        
        if(woordGok) juistGegokt();
        else {
            jTextArea1.setText
            (
                "Helaas! Fout geraden.\n" + 
                "Spinnen maar!"
            ); 
            jLabelRad.setVisible(true);
            jButtonSpin.setVisible(true);
        }
    }
    
    /**
     * Methode vraagt het grootste bedrag ooit behaald in 1 ronde over alle spellen op.
     * Vervolgens zal deze checken of het huidig gewonnen bedrag groter is dan dat grootste bedrag.
     * Het huidig gewonnen bedrag wordt bij het overall bedrag opgeteld.
     */
    private void updateMaxEnOverallSaldos() {
        int maxSaldo = record[y].getMaxSaldo(db, record[y].getSpelerNaam(), record[y].getLeeftijd());
        record[y].updateRecordsMaxSaldo(record[y].getHuidigSaldo(), db, record[y].getSpelerNaam(), record[y].getLeeftijd(), maxSaldo);   
        record[y].updateRecordsOverallSaldo(record[y].getHuidigSaldo(), db, record[y].getSpelerNaam(), record[y].getLeeftijd());
    }

    /**
     * Het woord werd geraden: labels worden ingesteld en databank wordt geupdate.
     */
    private void juistGegokt() {
        jLabelMask.setText(record[y].getWoord());
        record[y].setTotaalSaldo();
        jLabelHuidigSaldo.setText("Huidig saldo: " + record[y].getHuidigSaldo());
        jLabelTotaalSaldo.setText("Totaal saldo: " + record[y].getTotaalSaldo());
        //kijken of dit gewonnen saldo > winst bij andere rondes + gewonnen saldo toevoegen aan overallSaldo
        updateMaxEnOverallSaldos();

        if (rondeNummer < aantalSpelers) {
            jButtonRonde.setVisible(true);
            jTextArea1.setText
            (
                "Goed Geraden!\n" +
                "Je hebt deze ronde " + record[y].getHuidigSaldo() + " euro verdiend.\n" +
                "Je totale saldo komt nu op " + record[y].getTotaalSaldo() + " euro.\n" +
                "Druk op nextgame om volgende ronde te spelen."
            );
        }
        else {
            jButtonOpnieuw.setVisible(true);
            jButtonStats.setVisible(true);
            jTextArea1.setText
            (
                "Goed Geraden!\n" +
                "Je hebt deze ronde " + record[y].getHuidigSaldo() + " euro verdiend.\n" +
                "Je totale saldo komt nu op " + record[y].getTotaalSaldo() + " euro.\n" +
                "Einde van het spel!"
            );
        }   
    }
    
    private void gokMetEinde(boolean woordGok) {
        jInputButtonFinal.setVisible(false);
        jTextField1.setVisible(false);
        jLabelInput.setVisible(false);
        
        if(woordGok) juistGegokt();
        else {
            record[y].setHuidigSaldoNaarNul();
            record[y].setTotaalSaldo();
            jLabelHuidigSaldo.setText("Huidig saldo: " + record[y].getHuidigSaldo());
            jLabelTotaalSaldo.setText("Totaal saldo: " + record[y].getTotaalSaldo());
            
            if (rondeNummer < aantalSpelers) {
                jButtonRonde.setVisible(true);
                jTextArea1.setText
                (
                    "Helaas! Het juiste antwoord was: " + record[y].getWoord() + "\n" +
                    "Druk op nextgame om volgende ronde te spelen."
                );
            }
            else {
                jButtonOpnieuw.setVisible(true);
                jButtonStats.setVisible(true);
                jTextArea1.setText
                (
                    "Helaas! Het juiste antwoord was: " + record[y].getWoord() + "\n" +
                    "Einde van het spel!"
                );
            }
        }
    }
    
    /**
     * Gegevens worden per speler aangepast zodat men alle tijde hun score kan zien.
     */
    private void resetLabels() {
        jLabelNaam.setText("Speler" + (y+1) + ": " + record[y].getSpelerNaam());
        jLabelLeeftijd.setText("Leeftijd: " + record[y].getLeeftijd());
        jLabelHuidigSaldo.setText("Huidig Saldo: " + record[y].getHuidigSaldo());
        jLabelTotaalSaldo.setText("Totaal Saldo: " + record[y].getTotaalSaldo());
    }
    
    /**
     * Stelt alle beginwaarden van het spelpanel in (knoppen, buttons, labels...)
     */
    private void startPanelScherm() {
        //juiste elementen weergeven
        jButtonSpin.setVisible(false);
        jLabelRad.setVisible(false);
        jButtonStats.setVisible(false);
        jButtonOpnieuw.setVisible(false);
        jInputButton.setVisible(false);
        jInputButtonFinal.setVisible(false);
        jInputButtonGok.setVisible(false);
        jTextField1.setVisible(false);
        jLabelInput.setVisible(false);
        jButtonStart.setVisible(true);
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        jInputButtonKlinker.setVisible(false);
        jButtonRonde.setVisible(false);
        //labels aanpassen naargelang input
        jLabelAantalSpelers.setText("Aantal spelers: " + record.length);
        jLabelNaam.setText("Speler" + (y+1) + ": " + record[y].getSpelerNaam());
        jLabelLeeftijd.setText("Leeftijd: " + record[y].getLeeftijd());
        jLabelNiveau.setText("Niveau: " + record[y].getNiveau());  
        jLabelHuidigSaldo.setText("Huidig saldo: " + record[y].getHuidigSaldo());
        jLabelTotaalSaldo.setText("Totaal saldo: " + record[y].getTotaalSaldo());
        jLabelThema.setText("");
        jLabelMask.setText("");
        //welkomtekst
        jTextArea1.setText
        (
            "Beste spelers,\n" +
            "In dit veld komt het ganse spelverloop met alle\ninstructies." +
            " Have fun!"
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelSpelInfo = new javax.swing.JLabel();
        jLabelAantalSpelers = new javax.swing.JLabel();
        jLabelNaam = new javax.swing.JLabel();
        jLabelLeeftijd = new javax.swing.JLabel();
        jLabelNiveau = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabelInput = new javax.swing.JLabel();
        jInputButtonKlinker = new javax.swing.JButton();
        jInputButton = new javax.swing.JButton();
        jInputButtonFinal = new javax.swing.JButton();
        jInputButtonGok = new javax.swing.JButton();
        jLabelRondeNummer = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelRondeInfo = new javax.swing.JLabel();
        jLabelHuidigSaldo = new javax.swing.JLabel();
        jLabelRad = new javax.swing.JLabel();
        jButtonRonde = new javax.swing.JButton();
        jButtonNee = new javax.swing.JButton();
        jButtonJa = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jButtonOpnieuw = new javax.swing.JButton();
        jButtonSpin = new javax.swing.JButton();
        jLabelKlinker = new javax.swing.JLabel();
        jLabelTotaalSaldo = new javax.swing.JLabel();
        jButtonStats = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelThema = new javax.swing.JLabel();
        jLabelMask = new javax.swing.JLabel();
        jRadFoto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jBackground = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setMaximumSize(new java.awt.Dimension(580, 310));
        setMinimumSize(new java.awt.Dimension(580, 310));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(580, 310));
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPanel1.setLayout(null);

        jLabelSpelInfo.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabelSpelInfo.setText("SPELINFORMATIE");
        jPanel1.add(jLabelSpelInfo);
        jLabelSpelInfo.setBounds(11, 12, 89, 14);

        jLabelAantalSpelers.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelAantalSpelers.setText("Aantal spelers: 1");
        jPanel1.add(jLabelAantalSpelers);
        jLabelAantalSpelers.setBounds(11, 32, 202, 14);

        jLabelNaam.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelNaam.setText("Naam:");
        jPanel1.add(jLabelNaam);
        jLabelNaam.setBounds(11, 52, 202, 14);

        jLabelLeeftijd.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelLeeftijd.setText("Leeftijd:");
        jPanel1.add(jLabelLeeftijd);
        jLabelLeeftijd.setBounds(11, 72, 202, 14);

        jLabelNiveau.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelNiveau.setText("Niveau:");
        jPanel1.add(jLabelNiveau);
        jLabelNiveau.setBounds(11, 92, 202, 14);

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 210, 98, 30);

        jLabelInput.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelInput.setText("Geef hier uw input:");
        jPanel1.add(jLabelInput);
        jLabelInput.setBounds(10, 190, 202, 14);

        jInputButtonKlinker.setText("OK");
        jInputButtonKlinker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInputButtonKlinkerActionPerformed(evt);
            }
        });
        jPanel1.add(jInputButtonKlinker);
        jInputButtonKlinker.setBounds(10, 250, 100, 23);

        jInputButton.setText("OK");
        jInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInputButtonActionPerformed(evt);
            }
        });
        jPanel1.add(jInputButton);
        jInputButton.setBounds(10, 250, 100, 23);

        jInputButtonFinal.setText("OK");
        jInputButtonFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInputButtonFinalActionPerformed(evt);
            }
        });
        jPanel1.add(jInputButtonFinal);
        jInputButtonFinal.setBounds(10, 250, 100, 23);

        jInputButtonGok.setText("OK");
        jInputButtonGok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInputButtonGokActionPerformed(evt);
            }
        });
        jPanel1.add(jInputButtonGok);
        jInputButtonGok.setBounds(10, 250, 100, 23);

        jLabelRondeNummer.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelRondeNummer.setText("Rondenummer:");
        jPanel1.add(jLabelRondeNummer);
        jLabelRondeNummer.setBounds(10, 120, 90, 14);

        add(jPanel1);
        jPanel1.setBounds(10, 10, 120, 288);

        jPanel2.setBackground(new java.awt.Color(102, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(null);

        jLabelRondeInfo.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabelRondeInfo.setText("RONDEINFORMATIE");
        jPanel2.add(jLabelRondeInfo);
        jLabelRondeInfo.setBounds(11, 12, 98, 14);

        jLabelHuidigSaldo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelHuidigSaldo.setText("Huidig saldo:");
        jPanel2.add(jLabelHuidigSaldo);
        jLabelHuidigSaldo.setBounds(11, 32, 100, 14);

        jLabelRad.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelRad.setText("Draai aan het rad:");
        jPanel2.add(jLabelRad);
        jLabelRad.setBounds(11, 233, 85, 14);

        jButtonRonde.setText("NEXT GAME");
        jButtonRonde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRondeActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonRonde);
        jButtonRonde.setBounds(10, 250, 100, 23);

        jButtonNee.setText("NEE");
        jButtonNee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNeeActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonNee);
        jButtonNee.setBounds(10, 200, 100, 23);

        jButtonJa.setText("JA");
        jButtonJa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJaActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonJa);
        jButtonJa.setBounds(10, 170, 100, 23);

        jButtonStart.setText("START");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonStart);
        jButtonStart.setBounds(10, 250, 100, 23);

        jButtonOpnieuw.setText("RESTART");
        jButtonOpnieuw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpnieuwActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonOpnieuw);
        jButtonOpnieuw.setBounds(10, 250, 100, 23);

        jButtonSpin.setText("SPIN");
        jButtonSpin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSpinActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSpin);
        jButtonSpin.setBounds(10, 250, 98, 23);

        jLabelKlinker.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelKlinker.setText("Klinker kopen?");
        jPanel2.add(jLabelKlinker);
        jLabelKlinker.setBounds(10, 150, 110, 14);

        jLabelTotaalSaldo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelTotaalSaldo.setText("Totaal saldo:");
        jPanel2.add(jLabelTotaalSaldo);
        jLabelTotaalSaldo.setBounds(11, 52, 100, 14);

        jButtonStats.setText("STATS");
        jButtonStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatsActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonStats);
        jButtonStats.setBounds(10, 220, 100, 23);

        add(jPanel2);
        jPanel2.setBounds(450, 11, 120, 290);

        jPanel3.setOpaque(false);

        jLabelThema.setBackground(new java.awt.Color(0, 204, 153));
        jLabelThema.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelThema.setForeground(new java.awt.Color(255, 255, 255));
        jLabelThema.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelThema.setText("THEMA: AUTOMERKEN");
        jLabelThema.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jLabelThema.setOpaque(true);

        jLabelMask.setBackground(new java.awt.Color(0, 204, 153));
        jLabelMask.setFont(new java.awt.Font("Miriam Fixed", 1, 14)); // NOI18N
        jLabelMask.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMask.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMask.setText("*l*dd**");
        jLabelMask.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jLabelMask.setOpaque(true);

        jRadFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafisch/images/rad4.png"))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(0, 204, 153));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jScrollPane1.setWheelScrollingEnabled(false);

        jTextArea1.setBackground(new java.awt.Color(0, 204, 153));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(2);
        jTextArea1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addComponent(jLabelMask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelThema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabelThema, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMask, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel3);
        jPanel3.setBounds(134, 11, 310, 288);

        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafisch/images/did-a-wheel-of-fortune-contestant-lose-unfairly.jpg"))); // NOI18N
        add(jBackground);
        jBackground.setBounds(0, 0, 580, 310);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSpinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSpinActionPerformed
        record[y].randomKeuzeRad();
        heeftGedraaid = true;
        jLabelInput.setVisible(true);
        jInputButton.setVisible(true);
        jTextField1.setVisible(true);
        eenRonde();
    }//GEN-LAST:event_jButtonSpinActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        jButtonStart.setVisible(false);
        jLabelRad.setVisible(true);
        jButtonSpin.setVisible(true);
        rondeStart();
    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonOpnieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpnieuwActionPerformed
        // Restart button die ervoor zorgt dat je het spel opnieuw kan spelen
        jButtonOpnieuw.setVisible(false);
        jLabelRad.setVisible(true);
        jButtonSpin.setVisible(true);
        spelStart();
    }//GEN-LAST:event_jButtonOpnieuwActionPerformed

    private void jInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonActionPerformed
        input = jTextField1.getText();
        boolean geldig = record[y].checkIngaveMedeklinker(input);
        if (geldig) ingaveMedeklinkerIsGeldig();
        else jTextArea1.setText("Gelieve één geldige medeklinker in te geven!");
    }//GEN-LAST:event_jInputButtonActionPerformed

    private void jInputButtonFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonFinalActionPerformed
        input = jTextField1.getText();
        gokMetEinde(record[y].woordGokken(input));
    }//GEN-LAST:event_jInputButtonFinalActionPerformed

    private void jInputButtonGokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonGokActionPerformed
        input = jTextField1.getText();
        gokZonderEinde(record[y].woordGokken(input));
    }//GEN-LAST:event_jInputButtonGokActionPerformed

    private void jButtonJaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJaActionPerformed
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        klinkerKopen();
    }//GEN-LAST:event_jButtonJaActionPerformed

    private void jButtonNeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNeeActionPerformed
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        jTextArea1.setText("Wat is het antwoord, denk je? ");
        jLabelInput.setVisible(true);
        jInputButton.setVisible(false);
        jInputButtonGok.setVisible(true);
        jTextField1.setVisible(true); 
    }//GEN-LAST:event_jButtonNeeActionPerformed

    private void jInputButtonKlinkerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonKlinkerActionPerformed
        input = jTextField1.getText();
        boolean geldig = record[y].checkIngaveKlinker(input);
        if (!geldig) jTextArea1.setText("Gelieve één geldige klinker in te geven!");
        else klinkerValideren();
    }//GEN-LAST:event_jInputButtonKlinkerActionPerformed

    private void jButtonRondeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRondeActionPerformed
        jButtonRonde.setVisible(false);
        jButtonSpin.setVisible(true);
        jLabelRad.setVisible(true);
        rondeStart();
    }//GEN-LAST:event_jButtonRondeActionPerformed

    private void jButtonStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatsActionPerformed
        parentFrame.setContentPane(new TeamStatsPanel (parentFrame, namen, leeftijden, record, db));
        parentFrame.validate();
    }//GEN-LAST:event_jButtonStatsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jBackground;
    private javax.swing.JButton jButtonJa;
    private javax.swing.JButton jButtonNee;
    private javax.swing.JButton jButtonOpnieuw;
    private javax.swing.JButton jButtonRonde;
    private javax.swing.JButton jButtonSpin;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStats;
    private javax.swing.JButton jInputButton;
    private javax.swing.JButton jInputButtonFinal;
    private javax.swing.JButton jInputButtonGok;
    private javax.swing.JButton jInputButtonKlinker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAantalSpelers;
    private javax.swing.JLabel jLabelHuidigSaldo;
    private javax.swing.JLabel jLabelInput;
    private javax.swing.JLabel jLabelKlinker;
    private javax.swing.JLabel jLabelLeeftijd;
    private javax.swing.JLabel jLabelMask;
    private javax.swing.JLabel jLabelNaam;
    private javax.swing.JLabel jLabelNiveau;
    private javax.swing.JLabel jLabelRad;
    private javax.swing.JLabel jLabelRondeInfo;
    private javax.swing.JLabel jLabelRondeNummer;
    private javax.swing.JLabel jLabelSpelInfo;
    private javax.swing.JLabel jLabelThema;
    private javax.swing.JLabel jLabelTotaalSaldo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jRadFoto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
