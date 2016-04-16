/**
* Dit programma codeert of decodeert een ingegeven woord.
* De users kunnen in het menu kiezen tussen 3 mogelijkheden (1/2/3), waarvan enkel
*	de laatste het programma (= de loop) afsluit.
*
* @author Leander Clappaert
* @version 29 November 2014
*/
public class Cryptografie {
	final int N = 4; 					 //code kan in een oogopslag aangepast worden
	final String SLEUTELWOORD = "java";

	public String input(String ingave) { //methode dat user input inleest
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public String woordManipulatie(String ingave, int keuze) { //methode dat de bewerkingsmethode(s) aanroept
		String woord = input(ingave);
		String nieuwWoord = "";

		for (int i = 0; i < woord.length(); i++) { //controle van de ingave (enkel kleine letters!)
			char letter = woord.charAt(i);

			if (letter >= 97 && letter <= 122) {
				nieuwWoord = woordBewerken(woord, keuze);
			}
			else {
				System.out.println("Gelieve een geldige mogelijkheid (a-z) in te geven!");
				nieuwWoord = "ONGELDIGE INGAVE!";
				break;
			}
		}

		return nieuwWoord;
	}

	public String woordBewerken(String ingave, int keuze) {   //methode dat het woord codeert of decodeert
		String woord = ingave;
		String nieuwWoord = "", nieuwSleutelwoord = "";
		int nieuweLetter = 0;
		char letter = 'a', letterSleutel;

		for (int i = 0; i < woord.length(); i++) { //SLEUTELWOORD vermenigvuldigen
			while (nieuwSleutelwoord.length() < woord.length()) {
				nieuwSleutelwoord = nieuwSleutelwoord + SLEUTELWOORD;
			}

			letter = woord.charAt(i);
			letterSleutel = nieuwSleutelwoord.charAt(i);

			if (keuze == 1) { //coderen = woord + N + SLEUTELWOORD
				nieuweLetter = (int)letter + N;

				if (nieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
					nieuweLetter = nieuweLetter - 26;
				}

				nieuweLetter = (int)nieuweLetter + (int)letterSleutel -97;

				if (nieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
					nieuweLetter = nieuweLetter - 26;
				}
			}
			else if (keuze == 2) { //decoderen = woord - SLEUTELWOORD - N
				nieuweLetter = (int)letter - (int)letterSleutel + 97;

				if (nieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
					nieuweLetter = nieuweLetter + 26;
				}

				nieuweLetter = (int)nieuweLetter - N;

				if (nieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
					nieuweLetter = nieuweLetter + 26;
				}
			}

			nieuwWoord = nieuwWoord + (char)nieuweLetter;
		}

		return nieuwWoord;
	}

	public void mainMenu() { //methode dat bepaalt welke control flow er uitgevoerd wordt
		String keuze = "", keuze1, keuze2;

		while (!keuze.equals("3")) {
			keuze = input
				(
					"\n" +
					"Welkom bij CRYPTOtechniek2\n" +
					"Wat wil je doen? Je kan:\n" +
					"\t1. Coderen\n" +
					"\t2. Decoderen\n" +
					"\t3. Dit programma verlaten\n" +
					"Geef je keuze: (1/2/3):"
				);

			if (keuze.equals("1")) { //methode coderen
				keuze1 = woordManipulatie("Geef de tekst die je wil omzetten, gebruik alleen kleine letters:", 1);
				System.out.println("Omgezet geeft dit: " + keuze1);
			}
			else if (keuze.equals("2")) { //methode decoderen
				keuze2 = woordManipulatie("Geef het dedecodeerde woord:", 2);
				System.out.println("Omgezet geeft dit: " + keuze2);
			}
			else if (keuze.equals("3")) { //programma afsluiten (= loop beëindigen)
				System.out.print("Tot de volgende keer!");
			}
			else {
				System.out.println("Gelieve een geldige mogelijkheid (1/2/3) in te geven!");
			}
		}
	}

	public static void main(String[] args) {
		Cryptografie x = new Cryptografie();
		x.mainMenu();
	}
}