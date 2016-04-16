/**
*
* @author Leander Clappaert
* @version 29 November 2014
*/
public class Cryptografie {
	final int N = 4; 					//code kan in een oogopslag aangepast worden
	final String SLEUTELWOORD = "java"; //code kan in een oogopslag aangepast worden

	public String input(String ingave) { //Methode die alle inputs inleest
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public String coderen(String ingave) {
		String woord = input(ingave);

		String nieuwWoord = woordMetNOptellen(woord);
		String gecodeerdWoord = woordMetSleutelwoordOptellen(nieuwWoord);
		return gecodeerdWoord;
	}

	public String decoderen(String ingave) {
		String codewoord = input(ingave);

		String nieuwWoord = woordMetSleutelwoordAftrekken(codewoord);
		String gedecodeerdWoord = woordMetNAftrekken(nieuwWoord);
		return gedecodeerdWoord;
	}

	public String woordMetNOptellen(String ingave) {
		String woord = ingave;
		String nieuwWoord = "";
		int waardeNieuweLetter = 0;
		char letter = 'a', nieuweLetter = 'a';

		for (int i = 0; i < woord.length(); i++) {
			letter = woord.charAt(i);
			waardeNieuweLetter = (int)letter + N;

			while (waardeNieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
				waardeNieuweLetter = waardeNieuweLetter - 26;
			}

			nieuweLetter = (char)waardeNieuweLetter;
			nieuwWoord = nieuwWoord + nieuweLetter;
		}

		return nieuwWoord;
	}

	public String woordMetNAftrekken(String ingave) {
		String woord = ingave;
		String nieuwWoord = "";
		int waardeNieuweLetter = 0;
		char letter = 'a', nieuweLetter = 'a';

		for (int i = 0; i < woord.length(); i++) {
			letter = woord.charAt(i);
			waardeNieuweLetter = (int)letter - N;

			while (waardeNieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
				waardeNieuweLetter = waardeNieuweLetter + 26;
			}

			nieuweLetter = (char)waardeNieuweLetter;
			nieuwWoord = nieuwWoord + nieuweLetter;
		}

		return nieuwWoord;
	}

	public String woordMetSleutelwoordOptellen(String ingave) {
		String nieuwWoord = ingave;
		int waardeNieuweLetter = 0;
		String gecodeerdWoord = "", nieuwSleutelwoord = "";
		char letterWoord = 'a', letterSleutel = 'a', nieuweLetter = 'a';

		for (int i = 0; i < nieuwWoord.length(); i++) {
			while (nieuwSleutelwoord.length() < nieuwWoord.length()) {
				nieuwSleutelwoord = nieuwSleutelwoord + SLEUTELWOORD;
			}

			letterWoord = nieuwWoord.charAt(i);
			letterSleutel = nieuwSleutelwoord.charAt(i);
			waardeNieuweLetter = (int)letterWoord + (int)letterSleutel -97;

			if (waardeNieuweLetter > 122) { //zodat we enkel a-z waarden bekomen
				waardeNieuweLetter = waardeNieuweLetter - 26;
			}

			nieuweLetter = (char)waardeNieuweLetter;
			gecodeerdWoord = gecodeerdWoord + nieuweLetter;
		}

		return gecodeerdWoord;
	}

	public String woordMetSleutelwoordAftrekken(String ingave) {
		String nieuwWoord = ingave;
		int waardeNieuweLetter = 0;
		String gecodeerdWoord = "", nieuwSleutelwoord = "";
		char letterWoord = 'a', letterSleutel = 'a', nieuweLetter = 'a';

		for (int i = 0; i < nieuwWoord.length(); i++) {
			while (nieuwSleutelwoord.length() < nieuwWoord.length()) {
				nieuwSleutelwoord = nieuwSleutelwoord + SLEUTELWOORD;
			}

			letterWoord = nieuwWoord.charAt(i);
			letterSleutel = nieuwSleutelwoord.charAt(i);
			waardeNieuweLetter = (int)letterWoord - (int)letterSleutel +97;

			if (waardeNieuweLetter < 97) { //zodat we enkel a-z waarden bekomen
				waardeNieuweLetter = waardeNieuweLetter + 26;
			}

			nieuweLetter = (char)waardeNieuweLetter;
			gecodeerdWoord = gecodeerdWoord + nieuweLetter;
		}

		return gecodeerdWoord;
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
				System.out.println(keuze1);
			}
			else if (keuze.equals("2")) { //Methode decoderen
				String keuze2 = decoderen("Geef het dedecodeerde woord:");
				System.out.println(keuze2);
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