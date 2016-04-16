package Grafisch;
import Database.DatabaseConnectie;
import Logica.SpelSolo;
import javax.swing.JFrame;

/**
 * De grafische layout van het solospel zelf.
 * @author Leander Clappaert
 * @version 29/05/2015
 */
public class SpelSoloPanel extends javax.swing.JPanel {
    private final JFrame parentFrame;
    private SpelSolo r;
    private boolean heeftGedraaid;
    private String input;
    private DatabaseConnectie db;
    
    /**
     * Creates new form SpelSoloPanel
     */
    public SpelSoloPanel(JFrame frame, String naam, int leeftijd, DatabaseConnectie db) {
        initComponents();
        this.parentFrame = frame;
        this.db = db;
        r = new SpelSolo(naam, leeftijd);
        spelStart();
    }
    
    /**
     * Stelt de start parameters in voor een nieuw spel.
     */
    private void spelStart() {
        r.start(r);
        setThemaEnWoord();
        r.maskeren(".");
        startPanelScherm();
    }
    
    /**
     * Stelt het thema en woord in dat random uit de database werd gehaald.
     */
    protected void setThemaEnWoord() {
        String thema = db.getRandomThema(r.getNiveau());
        String woord = db.getRandomWoord(r.getNiveau(), thema);
        r.setThema(thema);
        r.setWoord(woord);
    }
    
    /**
     * Begin van de ronde. Dit wordt geactiveerd na de 1e spinbeurt.
     */
    private void rondeStart() {
        if(r.getBeurtenResterende() != 0) {
            //kijken of er op de spin button werd gedrukt.
            if (heeftGedraaid) {
                //spinknop opnieuw resetten.
                heeftGedraaid = false;
                //verliesbeurt of niet
                if (r.getMagSpelen()) {
                    jButtonSpin.setVisible(false);
                    jLabelRad.setVisible(false);
                    //zijn er nog medeklinkers over om te raden?
                    if (!r.controleResterendeMedeklinkers()) {
                        if (r.getBedragGekozen() > 0) { //geldig bedrag te winnen
                            jLabelMask.setText(r.getMask());
                            jTextArea1.setText
                            (
                                r.getSpelerNaam() + "(" + r.getLeeftijd() + "): " + r.getHuidigSaldo() + " euro in de pot.\n" +
                                ">> Geldbeurt: " + r.getBedragGekozen() + " euro.\n" +
                                r.getSpelerNaam() + ", je mag een medeklinker raden: "
                            );
                        }
                        else { //bankroet
                            jTextArea1.setText(
                                "Helaas! Het rad toont BANKROET aan.\n" +
                                "Je kan deze ronde geen geld verdienen!\n" +
                                "Je huidig saldo wordt gereset naar " + r.getHuidigSaldo() + " euro.\n" +
                                "Je kan echter wel nog voortspelen.\n" + 
                                r.getSpelerNaam() + ", je mag een medeklinker raden: "
                            );
                            jLabelHuidigSaldo.setText("Huidig saldo: " + r.getHuidigSaldo());
                        }
                        jTextField1.setVisible(true);
                        jInputButton.setVisible(true);
                        jLabelInput.setVisible(true);
                        //GOTO ingaveMedeklinkerIsGeldig() VIA BUTTON/TEXTFIELD
                    }
                    else {
                        //resterende beurten naar 1 brengen
                        if (r.getBeurtenResterende() > 1) r.setBeurtenResterende(r.getBeurtenResterende() - 1);
                        jLabelBeurten.setText("Beurten: " + r.getBeurtenResterende());
                        jLabelMask.setText(r.getMask());
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
                else { //verliesbeurt
                    jTextArea1.setText
                    (
                        "Helaas! Het rad toont een VERLIESBEURT aan.\n" +
                        "Er zijn nog " + r.getBeurtenResterende() + " beurten over.\n" +
                        "Spinnen maar!"
                    );    
                    jLabelBeurten.setText("Beurten: " + r.getBeurtenResterende());
                }
            }
        }
        else { // beurten == 0
            jLabelRad.setVisible(false);
            jButtonSpin.setVisible(false);
            jButtonOpnieuw.setVisible(true);
            jButtonExit.setVisible(true);
            jTextArea1.setText
            (
              "Helaas, je hebt het woord niet kunnen raden binnen \nde " + r.getBeurten() + " beurten." +
              "\nHet juiste antwoord was: " + r.getWoord()
            );
        }
    }
    
    /**
     * Vervolg van de ronde van zodra er een geldige medeklinker werd ingegeven door de speler.
     */
    private void ingaveMedeklinkerIsGeldig() {
        //enkel bij geldige medeklinker mag resterende beurten verminderen
        r.setBeurtenResterende (1);
        jLabelBeurten.setText("Beurten: " + r.getBeurtenResterende());
        //kijken opdat de medeklinker juist gegokt is of niet
        boolean gok = r.gokValideren(input);
        //indien niet = beurt kwijtgespeeld en opnieuw gokken
        if (!gok) {
            jTextArea1.setText
            (
                "Deze medeklinker zit er niet bij!\n" +
                "Er zijn nog " + r.getBeurtenResterende() + " beurten over.\n" +
                "Spinnen maar!"
            );
            jLabelInput.setVisible(false);
            jTextField1.setVisible(false);
            jInputButton.setVisible(false);
            jLabelRad.setVisible(true);
            jButtonSpin.setVisible(true);
        }            
        //indien wel = opnieuw maskeren met de juiste medeklinker(s) zichtbaar
        else {
            r.maskeren(input);
            r.setHuidigSaldo(r.getLetterCount());
            jLabelHuidigSaldo.setText("Huidig Saldo: " + r.getHuidigSaldo());
            jLabelMask.setText(r.getMask());

            //je kan enkel klinker kopen indien huidig saldo >= 2000 euro en er nog klinkers te koop zijn.
            if (r.getHuidigSaldo() >= 2000) {
                //zijn er nog klinkers over?
                if (!r.controleResterendeKlinkers()) {
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
                    //GOTO gokZonderEinde() VIA BUTTON/TEXTFIELD
                    jInputButton.setVisible(false);
                    jInputButtonGok.setVisible(true);
                    jTextField1.setVisible(true); 
                }                
            }
            else {
                //GOTO gokZonderEinde() VIA BUTTON/TEXTFIELD
                jTextArea1.setText("Wat is het antwoord, denk je? ");
                jInputButton.setVisible(false);
                jInputButtonGok.setVisible(true);
                jTextField1.setVisible(true);    
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
        r.setKlinkerGekocht(); //-2000
        jLabelHuidigSaldo.setText("Huidig saldo: " + r.getHuidigSaldo());
        jTextArea1.setText("Welke klinker wil je kopen?");
        //GOTO klinkerValideren() via BUTTON/TEXTFIELD
    }
    
    /**
     * Bepaalt of de ingegeven medeklinker in het te raden woord voorkomt of niet.
     */
    private void klinkerValideren() {
        jInputButtonKlinker.setVisible(false);
        //kijken opdat de klinker juist gegokt is of niet
        boolean gok = r.gokValideren(input);
        //indien niet
        if (!gok) {
            jTextArea1.setText
            (
                "Deze klinker zit er niet bij!" +
                "Wat is het antwoord, denk je? "       
            );
            jInputButton.setVisible(false);
            jInputButtonGok.setVisible(true);
            jTextField1.setVisible(true); 
        }
        //indien wel = opnieuw maskeren met juiste klinker(s)
        else {
            r.maskeren(input);
            jLabelMask.setText(r.getMask());
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
        if(woordGok) {
            jLabelMask.setText(r.getWoord());
            //input elementen weghalen
            jInputButtonGok.setVisible(false);
            jTextField1.setVisible(false);
            jLabelInput.setVisible(false);
            //restart button enabled
            jButtonOpnieuw.setVisible(true);
            juistGegokt();
        }
        else {
            jTextArea1.setText
            (
                "Helaas! Fout geraden.\n" + 
                "Er zijn nog " + r.getBeurtenResterende() + " beurten over.\n" +
                "Spinnen maar!"
            ); 
            jLabelBeurten.setText("Beurten: " + r.getBeurtenResterende()); 
            jLabelInput.setVisible(false);
            jTextField1.setVisible(false);
            jInputButtonGok.setVisible(false);
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
        int maxSaldo = r.getMaxSaldo(db, r.getSpelerNaam(), r.getLeeftijd());
        r.updateRecordsMaxSaldo(r.getHuidigSaldo(), db, r.getSpelerNaam(), r.getLeeftijd(), maxSaldo);   
        r.updateRecordsOverallSaldo(r.getHuidigSaldo(), db, r.getSpelerNaam(), r.getLeeftijd());
    }
    
    /**
     * Het woord werd geraden: labels worden ingesteld en databank wordt geupdate.
     */
    private void juistGegokt() {
        r.setTotaalSaldo();
        jButtonExit.setVisible(true);
        jTextArea1.setText
        (
            "Goed Geraden!\n" +
            "Je hebt deze ronde " + r.getHuidigSaldo() + " euro verdiend.\n" +
            "Je totale saldo komt nu op " + r.getTotaalSaldo() + " euro.\n" +
            "Druk op restart button indien je opnieuw wilt spelen."
        );
        jLabelHuidigSaldo.setText("Huidig saldo: " + r.getHuidigSaldo());
        jLabelTotaalSaldo.setText("Totaal saldo: " + r.getTotaalSaldo());
        updateMaxEnOverallSaldos();
        jLabelMaxSaldo.setVisible(true);
        jLabelMaxSaldo.setText("Max saldo: " + r.getMaxSaldo(db, r.getSpelerNaam(), r.getLeeftijd()));
        jLabelOverallSaldo.setVisible(true);
        jLabelOverallSaldo.setText("Overall saldo: " + r.getOverallSaldo(db, r.getSpelerNaam(), r.getLeeftijd()));
    }   
    
    /**
     * Deze methode evalueert de gok van de speler. Het spel EINDIGT SOWIESO, ongeacht het gegokte woord.
     * @param woordGok het woord dat door de speler werd gegokt
     */
    private void gokMetEinde(boolean woordGok) {
        r.setBeurtenResterende(1); //resterende beurten nu definitief op 0
        jInputButtonFinal.setVisible(false);
        jTextField1.setVisible(false);
        jLabelInput.setVisible(false);
        jButtonOpnieuw.setVisible(true);
        
        if(woordGok) juistGegokt();
        else {
            jButtonExit.setVisible(true);
            jTextArea1.setText
            (
                "Helaas! Het juiste antwoord was: " + r.getWoord() + "\n" +
                "Druk op restart button indien je opnieuw wilt spelen."
            );
            r.setHuidigSaldoNaarNul();
            r.setTotaalSaldo();   
            jLabelMaxSaldo.setVisible(true);
            jLabelMaxSaldo.setText("Max saldo: " + r.getMaxSaldo(db, r.getSpelerNaam(), r.getLeeftijd()));
            jLabelOverallSaldo.setVisible(true);
            jLabelOverallSaldo.setText("Overall saldo: " + r.getOverallSaldo(db, r.getSpelerNaam(), r.getLeeftijd()));
        }
    }
    
    /**
     * Stelt alle beginwaarden van het spelpanel in (knoppen, buttons, labels...).
     */
    private void startPanelScherm() {
        //juiste elementen weergeven
        jButtonExit.setVisible(false);
        jButtonSpin.setVisible(false);
        jButtonOpnieuw.setVisible(false);
        jInputButton.setVisible(false);
        jInputButtonFinal.setVisible(false);
        jInputButtonGok.setVisible(false);
        jTextField1.setVisible(false);
        jLabelInput.setVisible(false);
        jButtonEersteSpin.setVisible(true);
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        jInputButtonKlinker.setVisible(false);
        jLabelOverallSaldo.setVisible(false);
        jLabelMaxSaldo.setVisible(false);
        //labels aanpassen naargelang input
        jLabelAantalSpelers.setText("Aantal spelers: " + r.getAantalSpelers());
        jLabelNaam.setText("Naam: " + r.getSpelerNaam());
        jLabelLeeftijd.setText("Leeftijd: " + r.getLeeftijd());
        jLabelNiveau.setText("Niveau: " + r.getNiveau());
        jLabelHuidigSaldo.setText("Huidig saldo: " + r.getHuidigSaldo());
        jLabelTotaalSaldo.setText("Totaal saldo: " + r.getTotaalSaldo());
        jLabelThema.setText("Thema = " + r.getThema());
        jLabelMask.setText(r.getMask());
        jLabelBeurten.setText("Beurten over: " + r.getBeurtenResterende());
        //welkomtekst
        jTextArea1.setText
        (
            "Beste " + r.getSpelerNaam() + ",\n" +
            "In dit veld komt het ganse spelverloop met alle\ninstructies." +
            "Have fun!\n" +
            "Spin het rad!"
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
        jPanel2 = new javax.swing.JPanel();
        jLabelRondeInfo = new javax.swing.JLabel();
        jLabelHuidigSaldo = new javax.swing.JLabel();
        jLabelTotaalSaldo = new javax.swing.JLabel();
        jLabelBeurten = new javax.swing.JLabel();
        jLabelRad = new javax.swing.JLabel();
        jButtonExit = new javax.swing.JButton();
        jButtonNee = new javax.swing.JButton();
        jButtonJa = new javax.swing.JButton();
        jButtonEersteSpin = new javax.swing.JButton();
        jButtonOpnieuw = new javax.swing.JButton();
        jButtonSpin = new javax.swing.JButton();
        jLabelKlinker = new javax.swing.JLabel();
        jLabelMaxSaldo = new javax.swing.JLabel();
        jLabelOverallSaldo = new javax.swing.JLabel();
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
        jLabelHuidigSaldo.setBounds(10, 30, 100, 20);

        jLabelTotaalSaldo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelTotaalSaldo.setText("Totaal saldo:");
        jPanel2.add(jLabelTotaalSaldo);
        jLabelTotaalSaldo.setBounds(10, 50, 100, 20);

        jLabelBeurten.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelBeurten.setText("Beurten:");
        jPanel2.add(jLabelBeurten);
        jLabelBeurten.setBounds(10, 70, 100, 20);

        jLabelRad.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelRad.setText("Draai aan het rad:");
        jPanel2.add(jLabelRad);
        jLabelRad.setBounds(11, 233, 85, 14);

        jButtonExit.setText("AFSLUITEN");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonExit);
        jButtonExit.setBounds(10, 200, 100, 23);

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

        jButtonEersteSpin.setText("SPIN");
        jButtonEersteSpin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEersteSpinActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonEersteSpin);
        jButtonEersteSpin.setBounds(10, 250, 100, 23);

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

        jLabelMaxSaldo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelMaxSaldo.setText("Max saldo:");
        jPanel2.add(jLabelMaxSaldo);
        jLabelMaxSaldo.setBounds(10, 90, 100, 20);

        jLabelOverallSaldo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabelOverallSaldo.setText("Overall saldo:");
        jPanel2.add(jLabelOverallSaldo);
        jLabelOverallSaldo.setBounds(10, 110, 100, 20);

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
                .addComponent(jRadFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        //draaien aan het rad
        r.randomKeuzeRad();
        heeftGedraaid = true;
        rondeStart();
    }//GEN-LAST:event_jButtonSpinActionPerformed

    private void jButtonEersteSpinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEersteSpinActionPerformed
        //code nog aanpassen naar een goede startmethode, nu fungeert deze knop hetzelfde als de andere SPIN knop.
        r.randomKeuzeRad();
        heeftGedraaid = true;
        jButtonEersteSpin.setVisible(false);
        jButtonSpin.setVisible(true);
        rondeStart();
    }//GEN-LAST:event_jButtonEersteSpinActionPerformed

    private void jButtonOpnieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpnieuwActionPerformed
        // Restart button die ervoor zorgt dat je het spel opnieuw kan spelen
        spelStart();
        jButtonOpnieuw.setVisible(false);
    }//GEN-LAST:event_jButtonOpnieuwActionPerformed

    private void jInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonActionPerformed
        //input van een medeklinker
        input = jTextField1.getText();
        boolean geldig = r.checkIngaveMedeklinker(input);
        if (geldig) ingaveMedeklinkerIsGeldig();
        else jTextArea1.setText("Gelieve één geldige medeklinker in te geven!");
    }//GEN-LAST:event_jInputButtonActionPerformed

    private void jInputButtonFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonFinalActionPerformed
        //speler mag nog 1 keer een woord gokken
        input = jTextField1.getText();
        gokMetEinde(r.woordGokken(input));
    }//GEN-LAST:event_jInputButtonFinalActionPerformed

    private void jInputButtonGokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonGokActionPerformed
        //input van een woord dat de speler denkt juist te zijn
        input = jTextField1.getText();
        gokZonderEinde(r.woordGokken(input));
    }//GEN-LAST:event_jInputButtonGokActionPerformed

    private void jButtonJaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJaActionPerformed
        //Speler wilt een klinker kopen
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        klinkerKopen();
    }//GEN-LAST:event_jButtonJaActionPerformed

    private void jButtonNeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNeeActionPerformed
        //Speler wilt geen klinker kopen
        jButtonJa.setVisible(false);
        jButtonNee.setVisible(false);
        jLabelKlinker.setVisible(false);
        jTextArea1.setText("Wat is het antwoord, denk je? ");
        jInputButton.setVisible(false);
        jInputButtonGok.setVisible(true);
        jTextField1.setVisible(true); 
    }//GEN-LAST:event_jButtonNeeActionPerformed

    private void jInputButtonKlinkerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInputButtonKlinkerActionPerformed
        //Speler gokt een klinker
        input = jTextField1.getText();
        boolean geldig = r.checkIngaveKlinker(input);
        if (!geldig) jTextArea1.setText("Gelieve één geldige klinker in te geven!");
        else klinkerValideren();
    }//GEN-LAST:event_jInputButtonKlinkerActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        //Stoppen met spelen
        System.exit(0);
        db.closeConnection();
    }//GEN-LAST:event_jButtonExitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jBackground;
    private javax.swing.JButton jButtonEersteSpin;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonJa;
    private javax.swing.JButton jButtonNee;
    private javax.swing.JButton jButtonOpnieuw;
    private javax.swing.JButton jButtonSpin;
    private javax.swing.JButton jInputButton;
    private javax.swing.JButton jInputButtonFinal;
    private javax.swing.JButton jInputButtonGok;
    private javax.swing.JButton jInputButtonKlinker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAantalSpelers;
    private javax.swing.JLabel jLabelBeurten;
    private javax.swing.JLabel jLabelHuidigSaldo;
    private javax.swing.JLabel jLabelInput;
    private javax.swing.JLabel jLabelKlinker;
    private javax.swing.JLabel jLabelLeeftijd;
    private javax.swing.JLabel jLabelMask;
    private javax.swing.JLabel jLabelMaxSaldo;
    private javax.swing.JLabel jLabelNaam;
    private javax.swing.JLabel jLabelNiveau;
    private javax.swing.JLabel jLabelOverallSaldo;
    private javax.swing.JLabel jLabelRad;
    private javax.swing.JLabel jLabelRondeInfo;
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
