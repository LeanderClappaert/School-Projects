package Logica;
import java.util.*;

/**
 * Deze klasse stelt het grote draairad voor met allerlei te winnen bedragen.
 * @author Leander Clappaert
 * @version 23/05/2015
 */
public class Rad {
    
    /**
     * Het rad bestaat uit 24 waarden die random gekozen kunnen worden.
     * @return een random gekozen waarde
     */
    private String radPicker() {
        String[] waarden = 
        {
            "50", "100", "150", "200", "250", "300", "350", "400",
            "50", "100", "150", "200", "250", "300", "350", "400",
            "100", "200", "300", "300", "4000", "bankroet", "bankroet", "verliesbeurt"
        };
        
        int random = new Random().nextInt(waarden.length);
        return waarden[random];
    }
    
    /**
     * Een random bedrag wordt van het rad gekozen.
     * @return het gekozen bedrag of een symbolische waarde die bankroet/verliesbeurt voorstelt.
     */
    protected int keuzeRad() {
        String keuze = radPicker();
        if (keuze.charAt(0) == 'b') return -1;      //bankroet
        else if (keuze.charAt(0) == 'v') return -2; //verliesbeurt
        else return Integer.parseInt(keuze);
    }
}