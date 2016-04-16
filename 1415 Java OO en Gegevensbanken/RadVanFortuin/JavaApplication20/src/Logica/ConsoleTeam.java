package Logica;

//import java.util.Scanner;
/**
 * Console versie voor de multiplayer van Rad Van Fortuin
 * DEZE IS HELEMAAL NIET UP-TO-DATE.
 * DEZE KLASSE DIENDE LOUTER ALS BASIS VOOR DE GRAFISCHE INTERFACE.
 * VANDAAR DAT DE INHOUD VAN DE KLASSE OOK IN COMMENTAAR STAAT, OM EVENTUELE ERRORS TE VOORKOMEN.
 * @author Leander Clappaert
 * @version 27/05/2015
 */
public class ConsoleTeam {
    /*public void start(int aantalSpelers) {
        Scanner s = new Scanner(System.in);   
        
        //aanmaken spelers
        TeamSpel[] record = new TeamSpel[aantalSpelers];
        int[] leeftijden = new int[aantalSpelers];
        String[] namen = new String[aantalSpelers];
        for (int i = 0; i < aantalSpelers; i++) {           
            System.out.print("Naam speler " + (i+1) + ": ");
            namen[i] = s.next();
            //kijken of er dubbele namen voorkomen (om zo verwarring tijdens het spelverloop te voorkomen)
            if (i == 1 && namen[i].equals(namen[0])) {
                do {
                    System.out.print
                    (
                        "Deze naam is reeds ingenomen! Gelieve een andere naam te kiezen.\n" +
                        "Naam speler " + (i+1) + ": "
                    );
                    namen[i] = s.next();
                } while (namen[i].equals(namen[0]));
            }
            else if (i == 2 && (namen[i].equals(namen[0]) || namen[i].equals(namen[1]))) {
                do {
                    System.out.print
                    (
                        "Deze naam is reeds ingenomen! Gelieve een andere naam te kiezen.\n" +
                        "Naam speler " + (i+1) + ": "
                    );
                    namen[i] = s.next();
                } while (namen[i].equals(namen[0]) || namen[i].equals(namen[1]));
            }
            else if (i == 3 && (namen[i].equals(namen[0]) || namen[i].equals(namen[1]) || namen[i].equals(namen[2]))) {
                do {
                    System.out.print
                    (
                        "Deze naam is reeds ingenomen! Gelieve een andere naam te kiezen.\n" +
                        "Naam speler " + (i+1) + ": "
                    );
                    namen[i] = s.next();
                } while (namen[i].equals(namen[0]) || namen[i].equals(namen[1]) || namen[i].equals(namen[2]));   
            }
            
            do {
                System.out.print("Leeftijd speler " + (i+1) + ": ");
                String test = s.next(); //via deze weg ontstaat er geen infinite exception loop
                boolean heeftException = false; //dit voorkomt een dubbele error melding: exception + foute leeftijd
                try {
                    leeftijden[i] = Integer.parseInt(test);
                }
                catch (Exception e) {
                    heeftException = true;
                    System.out.println("Gelieve een geldige waarde in te geven.");
                }
                if (!heeftException && leeftijden[i] < 6) System.out.println("De minimum leeftijd is 6 jaar.");
            } while (leeftijden[i] < 6);
            record[i] = new TeamSpel(namen[i], leeftijden[i]);
        }

        //kijken hoeveel de jongste leeftijd bedraagt
        int jongsteLeeftijd = 999;
        for (int i = 0; i < aantalSpelers; i++) {
            if (leeftijden[i] < jongsteLeeftijd) jongsteLeeftijd = leeftijden[i]; 
        }        

        //jongste speler overal inzitten = niveau instellen alle spelers
        for (int i = 0; i < aantalSpelers; i++) {
            record[i].setLeeftijdJongsteSpeler(jongsteLeeftijd);   
        }

        //alles resetten + niveau bepalen
        for (int i = 0; i < aantalSpelers; i++) {
            record[i].start(record[i]);
        }
        
        boolean nieuwSpel = true;
        do {
            System.out.println
            (
                "\nAantal spelers = " + aantalSpelers + "\n" +
                "SPELINFORMATIE: " + "\n" +
                "   Niveau = " + record[0].getNiveau()
            );
            //start nieuwe ronde
            int rondeNummer = 0;
            boolean nieuweRonde = true;
            String[] themas = new String[aantalSpelers];
            do {
                if (nieuweRonde) {
                    rondeNummer++;
                    String thema;
                    boolean geldig = false;
                    do {
                        thema = db.getRandomThema(record[0].getNiveau());
                        geldig = record[0].checkKeuze(controleLijst, thema);
                    } while(!geldig);

                    String woord = db.getRandomWoord(record[0].getNiveau(), thema);
                    record[0].setThema(thema);
                    record[0].setWoord(woord);
                    //alles resetten
                    for (int i = 0; i < aantalSpelers; i++) {
                        record[i].rondeReset(); 
                    }

                    System.out.println
                    (
                        "\nRAAD ITEM " + rondeNummer + "\n" +
                        "Thema = " + record[0].getThema() + "\n" +
                        "------------------------------"
                    );
                }

                String medeklinker= "";
                boolean woordGok = false, geldig = false, medeklinkerGok = false; nieuweRonde = false;
                int i = 0;
                //Blijft lopen totdat het woord geraden is of zolang er nog medeklinkers beschikbaar zijn.
                do {
                    //rad spint en er wordt een vakje random gekozen
                    record[i].randomKeuzeRad();
                    //verliesbeurt of niet
                    if (record[i].getMagSpelen()) {
                        //zijn er nog medeklinkers te raden?
                        if (!record[i].controleResterendeMedeklinkers()) {
                            System.out.println("\n" + record[i].getSpelerNaam() + "(" + record[i].getLeeftijd() + "):" + record[i].getHuidigSaldo() + " euro in de pot");
                            if (record[i].getBedragGekozen() > 0) {
                                System.out.println
                                (
                                    ">> Geldbeurt: " + record[i].getBedragGekozen() + " euro\n" +
                                    "mask = " + record[i].getMask() + "\n\n" +   
                                    record[i].getSpelerNaam() + ", je mag een medeklinker raden: "
                                );
                            }
                            else {
                                System.out.println(
                                    ">> Geldbeurt: BANKROET\n" +
                                    "Helaas! Je kan deze ronde geen geld verdienen!\n" +
                                    "Je huidig saldo wordt gereset naar " + record[i].getHuidigSaldo() + " euro.\n" +
                                    "Je moet echter wel nog voortspelen.\n" +
                                    "mask = " + record[i].getMask() + "\n\n" +   
                                    record[i].getSpelerNaam() + ", je mag een medeklinker raden: "
                                );
                            }

                            //medeklinker wordt aangevraagd (indien geen verliesbeurt)
                            do {
                                medeklinker = s.next();
                                geldig = record[i].checkIngaveMedeklinker(medeklinker);
                                if (!geldig) System.out.println("Gelieve één geldige medeklinker in te geven!");
                            } while (!geldig);

                            //kijken opdat de medeklinker juist gegokt is of niet
                            medeklinkerGok = record[i].gokValideren(medeklinker);
                            //indien niet = beurt aan volgende speler
                            if (!medeklinkerGok) {
                                //huidige mask kopiëren naar andere
                                String huidigeMask = record[i].getMask();                            
                                if (i <= (aantalSpelers-2)) i++;
                                else i = 0;

                                System.out.println
                                (
                                    "Deze medeklinker zit er niet bij!\n" + 
                                    "Het is nu aan " + record[i].getSpelerNaam() + "."
                                );                            
                                record[i].setMask(huidigeMask);
                            }
                            else {        
                                //indien goede medeklinker gegokt = opnieuw maskeren met de juiste medeklinker(s) zichtbaar
                                record[i].maskeren(medeklinker);
                                record[i].setHuidigSaldo(record[i].getLetterCount());
                                System.out.println(record[i].getMask());

                                //je kan enkel klinker kopen indien huidig saldo >= 2000 euro en er nog klinkers te koop zijn.
                                if (record[i].getHuidigSaldo() >= 2000) {
                                    //zijn er nog klinkers over?
                                    if (!record[i].controleResterendeKlinkers()) {
                                        //klinker kopen of niet?
                                        char antwoord;
                                        do {
                                            System.out.println(record[i].getSpelerNaam() + ", wil je een klinker kopen? (j/n)");
                                            antwoord = s.next().toLowerCase().charAt(0);
                                        } while (antwoord != 'j'&& antwoord != 'n');

                                        //indien ja = klinker kiezen, indien nee = gewoon naar volgende sectie springen
                                        if (antwoord == 'j') {
                                            record[i].setKlinkerGekocht();
                                            String klinker;
                                            System.out.println("Welke klinker wil je kopen?");
                                            do {
                                                klinker = s.next();
                                                geldig = record[i].checkIngaveKlinker(klinker);
                                                if (!geldig) System.out.println("Gelieve één geldige klinker in te geven!");
                                            } while (!geldig);

                                            //kijken opdat de klinker juist gegokt is of niet
                                            boolean klinkerGok = record[i].gokValideren(klinker);
                                            //indien niet
                                            if (!klinkerGok) {
                                                System.out.println("Deze klinker zit er niet bij!");
                                                System.out.println("Mask = " + record[i].getMask());
                                            }
                                            //indien wel = opnieuw maskeren met juiste klinker(s)
                                            else {
                                                record[i].maskeren(klinker);
                                                System.out.println(record[i].getMask());
                                            }
                                        }
                                    }
                                    else System.out.println("Je kan geen klinkers meer kopen: alle klinkers zijn reeds weergegeven!");
                                }
     
                                //het antwoord raden
                                System.out.print("Wat is het antwoord, denk je? ");
                                String special = s.nextLine(); //nextLine bug wordt hierdoor opgevangen
                                woordGok = record[i].woordGokken(s.nextLine());
                                //indien fout = opnieuw een medeklinker raden                         
                                if (!woordGok) System.out.println("Helaas! Fout geraden.");
                            }
                        }
                        else {
                            System.out.println
                            (
                                "\nAlle medeklinkers zijn reeds geraden!\n" +
                                "Iedereen mag nu elk om beurt raden!\n"
                            );
                            do {
                                System.out.println
                                (
                                    record[i].getSpelerNaam() + ", wat is het antwoord denk je?\n" +
                                    "Mask = " + record[i].getMask()
                                );
                                System.out.print("Antwoord = ");
                                woordGok = record[i].woordGokken(s.nextLine());
                                if (!woordGok) {
                                    //huidige mask kopiëren naar andere
                                    String huidigeMask = record[i].getMask();      
                                    if (i <= (aantalSpelers-2)) i++;
                                    else i = 0;
                                    System.out.println
                                    (
                                        "Helaas!\n" +
                                        "Het is nu aan " + record[i].getSpelerNaam() + "."
                                    );
                                    record[i].setMask(huidigeMask);
                                }
                            } while (!woordGok); 
                        }    
                    }
                    else {
                        System.out.println("\n" + record[i].getSpelerNaam() + "(" + record[i].getLeeftijd() + "):" + record[i].getHuidigSaldo() + " euro in de pot");
                        //huidige mask kopiëren naar andere
                        String huidigeMask = record[i].getMask();      
                        if (i <= (aantalSpelers-2)) i++;
                        else i = 0;
                        System.out.println
                        (
                                ">> Geldbeurt: VERLIESBEURT\n" +
                                "Het is nu aan " + record[i].getSpelerNaam() + "."
                        );
                        record[i].setMask(huidigeMask);
                    }
                } while(!woordGok);     

                if(woordGok) {
                    record[i].setTotaalSaldo();
                    System.out.println
                    (
                        "\nGoed Geraden!\n" +
                        "Je hebt deze ronde " + record[i].getHuidigSaldo() + " euro verdiend.\n" +
                        "Je totale saldo komt nu op " + record[i].getTotaalSaldo() + " euro."
                    );
                    record[i].setMaxSaldo(record[i].getHuidigSaldo());
                    //overallSaldo bij iedereen aanpassen.
                    for (int y = 0; y < aantalSpelers; y++) {
                        record[y].setOverallSaldo(record[y].getHuidigSaldo());
                    }
                    nieuweRonde = true;
                }  
            } while(rondeNummer != aantalSpelers);

            System.out.println("\nSPELRESULTAAT:");
            //overzicht gewonnen geld bij alle spellen te samen.
            for (int i = 0; i < aantalSpelers; i++) {
                System.out.println(record[i].getSpelerNaam() + " heeft " + record[i].getTotaalSaldo() + " euro bij dit spel gewonnen.");
            }

            //checken wie gewonnen heeft.
            int temp = -1, winnaar = -1;
            for (int i = 0; i < aantalSpelers; i++) {
                int resultaat = record[i].getTotaalSaldo();
                if (resultaat > temp) {
                    temp = resultaat;
                    winnaar = i;
                }
            }
            
            //winnaar weergeven
            System.out.println
            (
                "\nDe winnaar(s) van dit spel:\n" +
                "\t" + record[winnaar].getSpelerNaam() + "(" + record[winnaar].getLeeftijd() + "):" + record[winnaar].getTotaalSaldo() + " euro in de pot.\n"
            );
            
            //extra spelstatistieken weergeven
            for (int i = 0; i < aantalSpelers; i++) {
                System.out.println
                (
                        "Extra info over speler " + record[i].getSpelerNaam() + "\n" +
                        "\tTotaal verworven geldsom: " + record[i].getOverallSaldo() + " euro.\n" +
                        "\tMax geldsom behaald in 1 spel: " + record[i].getMaxSaldo() + " euro.\n"
                );
            }
            
            //bepalen of er nog een spel gespeeld wordt
            System.out.println("Nog een spel samen spelen (j/n)?");
            char antwoord = s.next().toLowerCase().charAt(0);
            do {
                if (antwoord != 'j'&& antwoord != 'n') System.out.println("Gelieve j/n in te geven!");
                antwoord = s.next().toLowerCase().charAt(0);
            } while (antwoord != 'j'&& antwoord != 'n');  

            if (antwoord == 'j') nieuwSpel = true;
            else nieuwSpel = false;
        } while(nieuwSpel);
    }*/
}
