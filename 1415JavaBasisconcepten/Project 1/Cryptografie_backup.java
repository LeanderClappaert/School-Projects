/**
*
* @author Leander Clappaert
* @version 29 November 2014
*/
public class Cryptografie {
	public String input(String ingave) { //Methode die alle inputs inleest
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public String coderen(String ingave) {
		String woord = input(ingave);
		int N = 4;

		for (int i = 0; i < woord.length(); i++) {
			char letter = woord.charAt(i);
			int waardeNieuweLetter = (int)letter + N;
			char nieuweLetter = (char)waardeNieuweLetter;

			System.out.println(nieuweLetter);
		}

		return woord;
	}

	public String decoderen(String ingave) {
		String codewoord = input(ingave);
		return codewoord;
	}

	public void mainMenu() { //Methode die bepaalt welke methode er aangeroepen wordt (naargelang de input)
		String keuze = "";

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
				String keuze1 = coderen("Geef de tekst die je wil omzetten, gebruik alleen kleine letters:");
			}
			else if (keuze.equals("2")) { //Methode decoderen
				String keuze2 = decoderen("Geef het dedecodeerde woord:");
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