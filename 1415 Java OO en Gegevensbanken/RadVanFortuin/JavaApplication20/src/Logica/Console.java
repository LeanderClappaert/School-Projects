package Logica;

//import java.util.Scanner;
/**
 * Dit is een overkoepelende Console, die de juiste Console selecteert naargelang het aantal spelers.
 * DEZE IS HELEMAAL NIET UP-TO-DATE.
 * DEZE KLASSE DIENDE LOUTER ALS BASIS VOOR DE GRAFISCHE INTERFACE.
 * VANDAAR DAT DE INHOUD VAN DE KLASSE OOK IN COMMENTAAR STAAT, OM EVENTUELE ERRORS TE VOORKOMEN.
 * @author Leander Clappaert
 * @version 25/05/2015
 */
public class Console {
    /*public void start() {
        Scanner s = new Scanner(System.in);
        ConsoleSolo solo = new ConsoleSolo();
        ConsoleTeam team = new ConsoleTeam();
        System.out.print("Met hoeveel mensen wordt er gespeeld (1 tot en met 4 spelers)? ");
        
        int aantalSpelers = 0;
        do {
            String test = s.next(); //via deze weg ontstaat er geen infinite exception loop
            boolean heeftException = false; //dit voorkomt een dubbele error melding: exception + fout aantal spelers
            try {
                aantalSpelers = Integer.parseInt(test);
            }
            catch (Exception e) {
                heeftException = true;
                System.out.println("Gelieve een geldige waarde in te geven.");
            }
            if (!heeftException) {
                if (aantalSpelers == 1) solo.start();
                else if (aantalSpelers > 1 && aantalSpelers <= 4) team.start(aantalSpelers);
                else System.out.println("Gelieve een geldig aantal spelers in te geven (1 tot en met 4 spelers)!");
            }
        } while (!(aantalSpelers >= 1 && aantalSpelers <= 4));
    }
    
    public static void main(String[] args) {
        Console x = new Console();
        x.start();
    }*/
}
