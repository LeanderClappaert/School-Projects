package Logica;

//import java.util.Scanner;
/**
 * Console versie voor de singeplayer van Rad Van Fortuin
 * DEZE IS HELEMAAL NIET UP-TO-DATE.
 * DEZE KLASSE DIENDE LOUTER ALS BASIS VOOR DE GRAFISCHE INTERFACE.
 * VANDAAR DAT DE INHOUD VAN DE KLASSE OOK IN COMMENTAAR STAAT, OM EVENTUELE ERRORS TE VOORKOMEN.
 * @author Leander Clappaert
 * @version 27/05/2015
 */
public class ConsoleSolo {
    /*public void start() {
        Scanner s = new Scanner(System.in);
        System.out.print("Naam: ");
        
        String naam = s.next();
        int leeftijd = 0;
        do {
            System.out.print("Leeftijd: ");
            String test = s.next(); //via deze weg ontstaat er geen infinite exception loop
            boolean heeftException = false; //dit voorkomt een dubbele error melding: exception + foute leeftijd
            try {
                leeftijd = Integer.parseInt(test);
            }
            catch (Exception e) {
                heeftException = true;
                System.out.println("Gelieve een geldige waarde in te geven.");
            }
            if (!heeftException && leeftijd < 6) System.out.println("De minimum leeftijd is 6 jaar.");
        } while (leeftijd < 6);

        SpelSolo r = new SpelSolo(naam, leeftijd);
        //SpelSolo r = new SpelSolo("Leander", 7);
        r.start(r); //alle startelementen initialiseren
        
        System.out.println
        (
            "Aantal spelers = " + r.getAantalSpelers() + "\n" +
            "Naam speler: " + r.getSpelerNaam() + "\n" +
            "Leeftijd speler: " + r.getLeeftijd() + " jaar\n" +
            "SPELINFORMATIE: " + "\n" +
            "   Niveau = " + r.getNiveau() + "\n" +
            "   Thema = " + r.getThema() + "\n" +
            "------------------------------"
        );
        
        boolean woordGok = false, geldig = false, gok = false;
        do {
            //rad spint en er wordt een vakje random gekozen
            r.randomKeuzeRad();
            //verliesbeurt of niet
            if (r.getMagSpelen()) {
                //zijn er nog medeklinkers te raden?
                if (!r.controleResterendeMedeklinkers()) {
                    System.out.println("\n" + r.getSpelerNaam() + "(" + r.getLeeftijd() + "):" + r.getHuidigSaldo() + " euro in de pot");
                    if (r.getBedragGekozen() > 0) {
                        System.out.println
                        (
                            ">> Geldbeurt: " + r.getBedragGekozen() + " euro\n" +
                            "mask = " + r.getMask() + "\n\n" +   
                            r.getSpelerNaam() + ", je mag een medeklinker raden: "
                        );
                    }
                    else {
                        System.out.println(
                            "Helaas! Het rad toont BANKROET aan.\n" +
                            "Je kan deze ronde geen geld verdienen!\n" +
                            "Je huidig saldo wordt gereset naar " + r.getHuidigSaldo() + " euro.\n" +
                            "Je moet echter wel nog voortspelen.\n" +
                            "mask = " + r.getMask() + "\n\n" +   
                            r.getSpelerNaam() + ", je mag een medeklinker raden: "
                        );
                    }

                    //medeklinker wordt aangevraagd (indien geen verliesbeurt)
                    String medeklinker;
                    do {
                        medeklinker = s.next();
                        geldig = r.checkIngaveMedeklinker(medeklinker);
                        if (!geldig) System.out.println("Gelieve één geldige medeklinker in te geven!");
                    } while (!geldig);
                    
                    //enkel bij geldige medeklinker mag resterende beurten verminderen
                    r.setBeurtenResterende (1);
                    //kijken opdat de medeklinker juist gegokt is of niet
                    gok = r.gokValideren(medeklinker);
                    
                    //indien niet = beurt kwijtgespeeld en opnieuw gokken
                    if (!gok) {
                        System.out.println
                        (
                            "Deze medeklinker zit er niet bij!\n" +
                            "Er zijn nog " + r.getBeurtenResterende() + " beurten over."
                        );
                    }            
                    //indien wel = opnieuw maskeren met de juiste medeklinker(s) zichtbaar
                    else {
                        r.maskeren(medeklinker);
                        r.setHuidigSaldo(r.getLetterCount());
                        System.out.println(r.getMask());

                        //je kan enkel klinker kopen indien huidig saldo >= 2000 euro en er nog klinkers te koop zijn.
                        if (r.getHuidigSaldo() >= 2000) {
                            //zijn er nog klinkers over?
                            if (!r.controleResterendeKlinkers()) {
                                //klinker kopen of niet?
                                char antwoord;
                                do {
                                    System.out.println(r.getSpelerNaam() + ", wil je een klinker kopen? (j/n)");
                                    antwoord = s.next().toLowerCase().charAt(0);
                                } while (antwoord != 'j'&& antwoord != 'n');

                                //indien ja = klinker kiezen, indien nee = gewoon naar volgende sectie springen
                                if (antwoord == 'j') {
                                    r.setKlinkerGekocht();
                                    String klinker;
                                    System.out.println("Welke klinker wil je kopen?");
                                    do {
                                        klinker = s.next();
                                        geldig = r.checkIngaveKlinker(klinker);
                                        if (!geldig) System.out.println("Gelieve één geldige klinker in te geven!");
                                    } while (!geldig);

                                    //kijken opdat de klinker juist gegokt is of niet
                                    gok = r.gokValideren(klinker);
                                    //indien niet
                                    if (!gok) {
                                        System.out.println("Deze klinker zit er niet bij!");
                                        System.out.println("Mask = " + r.getMask());
                                    }
                                    //indien wel = opnieuw maskeren met juiste klinker(s)
                                    else {
                                        r.maskeren(klinker);
                                        System.out.println(r.getMask());
                                    }
                                }
                            }
                            else System.out.println("Je kan geen klinkers meer kopen: alle klinkers zijn reeds weergegeven!");
                        }
                        //het antwoord raden
                        System.out.print("Wat is het antwoord, denk je? ");
                        String special = s.nextLine(); //nextLine bug wordt hierdoor opgevangen
                        woordGok = r.woordGokken(s.nextLine());
                        //indien fout = beurt verminderen en opnieuw een medeklinker raden
                        if (!woordGok) System.out.println("Helaas! Fout geraden. Er zijn nog " + r.getBeurtenResterende() + " beurten over.");
                    }
                }
                else {
                    //resterende beurten naar 1 brengen
                    if (r.getBeurtenResterende() > 1) r.setBeurtenResterende(r.getBeurtenResterende() - 1);
                    System.out.println
                    (
                        "\nAlle medeklinkers zijn reeds geraden!\n" +
                        "Je mag nog 1 keer raden! Wat is het antwoord denk je?\n" +
                        "Mask = " + r.getMask() + "\n"
                    );
                    System.out.print("Antwoord = ");
                    woordGok = r.woordGokken(s.nextLine());
                    //resterende beurten nu definitief op 0
                    r.setBeurtenResterende(1);
                }
            }
            else {
                System.out.println
                (
                    "\nHelaas! Het rad toont een VERLIESBEURT aan.\n" +
                    "Er zijn nog " + r.getBeurtenResterende() + " beurten over."
                );                
            }
        }            
        while (!woordGok && r.getBeurtenResterende() != 0);

        if(woordGok) {
            r.setTotaalSaldo();
            System.out.println
            (
                "\nGoed Geraden!\n" +
                "Je hebt deze ronde " + r.getHuidigSaldo() + " euro verdiend.\n" +
                "Je totale saldo komt nu op " + r.getTotaalSaldo() + " euro."
            );
        }
        else {
            System.out.println("Helaas! Het juiste antwoord was: " + r.getWoord());
            r.setHuidigSaldoNaarNul();
            r.setTotaalSaldo();
        }
    }*/
}
