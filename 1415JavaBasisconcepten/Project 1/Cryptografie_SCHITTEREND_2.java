/**
* Dit programma codeert en/of decodeert een ingegeven woord.
* De users kunnen bij het menu kiezen tussen 3 mogelijkheden, waarvan enkel
*	de laatste mogelijkheid het programma (= de loop) afsluit.
*
* @author Leander Clappaert
* @version 29 November 2014
*/
public class Cryptografie {
	final int N = 4; 					//code kan in een oogopslag aangepast worden
	final String SLEUTELWOORD = "java";

	public String input(String ingave) { //methode dat alle inputs inleest
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public String woordManipulatie(String ingave, int keuze) { //methode dat 'coderen' of 'decoderen' selecteert
		String woord = input(ingave);
		String nieuwWoord = "", coderen = "", decoderen = "";

		if (keuze == 1) { //coderen
			coderen = woordMetNBwerken(woord, 1);
			nieuwWoord = woordMetSleutelwoordBewerken(coderen, 1);
		}
		else if (keuze == 2) { //decoderen
			decoderen = woordMetSleutelwoordBewerken(woord, 2);
			nieuwWoord = woordMetNBwerken(decoderen, 2);
		}

		return nieuwWoord;
	}

	public String woordMetNBwerken(String ingave, int keuze) { //methode dat een woord met N verhoogt of verlaagt
		String woord = ingave;
		String nieuwWoord = "";
		int waardeNieuweLetter = 0;
		char nieuweLetter = 'a';

		for (int i = 0; i < woord.length(); i++) {
			char letter = woord.charAt(i);

			if (keuze == 1) {
				waardeNieuweLetter = (int)letter + N;

				while (waardeNieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
					waardeNieuweLetter = waardeNieuweLetter - 26;
				}
			}
			else if (keuze == 2) {
				waardeNieuweLetter = (int)letter - N;

				while (waardeNieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
					waardeNieuweLetter = waardeNieuweLetter + 26;
				}
			}

			nieuweLetter = (char)waardeNieuweLetter;
			nieuwWoord = nieuwWoord + nieuweLetter;
		}

		return nieuwWoord;
	}

	public String woordMetSleutelwoordBewerken(String ingave, int keuze) { //methode dat een woord met een sleutelwoord verhoogt of verlaagt
		String nieuwWoord = ingave;
		int waardeNieuweLetter = 0;
		String nieuwSleutelwoord = "", gecodeerdWoord = "";
		char letterWoord = 'a', letterSleutel, nieuweLetter;

		for (int i = 0; i < nieuwWoord.length(); i++) {
			while (nieuwSleutelwoord.length() < nieuwWoord.length()) {
				nieuwSleutelwoord = nieuwSleutelwoord + SLEUTELWOORD;
			}

			letterWoord = nieuwWoord.charAt(i);
			letterSleutel = nieuwSleutelwoord.charAt(i);

			if (keuze == 1) {
				waardeNieuweLetter = (int)letterWoord + (int)letterSleutel -97;

				if (waardeNieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
					waardeNieuweLetter = waardeNieuweLetter - 26;
				}
			}
			else if (keuze == 2) {
				waardeNieuweLetter = (int)letterWoord - (int)letterSleutel +97;

				if (waardeNieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
					waardeNieuweLetter = waardeNieuweLetter + 26;
				}
			}

			nieuweLetter = (char)waardeNieuweLetter;
			gecodeerdWoord = gecodeerdWoord + nieuweLetter;
		}

		return gecodeerdWoord;
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

			if (keuze.equals("1")) { //Methode coderen
				keuze1 = woordManipulatie("Geef de tekst die je wil omzetten, gebruik alleen kleine letters:", 1);
				System.out.println("Omgezet geeft dit: " + keuze1);
			}
			else if (keuze.equals("2")) { //Methode decoderen
				keuze2 = woordManipulatie("Geef het dedecodeerde woord:", 2);
				System.out.println("Omgezet geeft dit: " + keuze2);
			}
			else if (keuze.equals("3")) { //Programma afsluiten = loop beëindigen
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