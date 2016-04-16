/**
* Dit programma berekent de karakteristieken, de waardentabel en de grafiek van een parabool.
* De gebruiker bepaalt de parameters van de parabool.
* Het aantal coördinaten kan telkens aangepast worden en zal dynamisch in alle methodes verwerkt worden (waar nodig).
* Vervolgens kan de gebruiker, via een menu, kiezen wat hij te weten wil komen van de parabool.
*
* @author Leander Clappaert
* @version 5 Januari 2014
*/
public class Parabolen {
	final int COORDINATEN = 7;

	/**
	* Enkele parameters worden gevraagd aan de gebruiker.
	* @param ingave de vraag die wordt gesteld
	* @return String het antwoord op de vraag
	*/
	public String inputString(String ingave) {
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	/**
	* String wordt ingelezen en geconverteerd naar een double, als antwoord op een vraag.
	* Bij foute ingave moet er een goede waarde ingegeven worden.
	* @param ingave	de vraag die wordt gesteld
	* @return String het antwoord op de vraag
	*/
	public double inputDouble(String ingave) {
		System.out.println(ingave);
		String input = Input.readString();

		for (int i = 0; i < input.length(); i++) {
			if (!(input.charAt(i) >= '0' && input.charAt(i) <= '9') && !(input.charAt(0) == '-') && (input.charAt(0) == '.')) {
				System.out.println("Gelieve een geldige waarde in te geven:");
				input = Input.readString();
				i = -1;
			}
		}

		double nieuweInput = Double.parseDouble(input);
		return nieuweInput;
	}

	/**
	* Aan de hand van de richtingscoëfficiënt wordt de vorm van de grafiek bepaald.
	* @param a de richtingscoëffiënt van de grafiek
	* @return String de vorm van de grafiek
	*/
	public String vormBepalen(double a) {
		String vorm = "";

		if (a > 0) {
			vorm = "Dalparabool";
		}
		else if (a < 0) {
			vorm = "Bergparabool";
		}
		else {
			vorm = "Horizontale rechte";
		}

		return vorm;
	}

	/**
	* Er wordt bepaald of er al dan niet een top is. Indien wel, worden de coördinaten berekend.
	* @param a de richtingscoëffiënt van de grafiek. Deze bepaalt of er een top is.
	* @param alfa bepaalt hoeveel de grafiek uitwijkt tov de y-as. Tevens de x-coördinaat van de top.
	* @param beta bepaalt hoeveel de grafiek uitwijkt tov de x-as. Tevens de y-coördinaat van de top.
	* @return String de topwaarde van de grafiek
	*/
	public String topBerekenen(double a, double alfa, double beta) {
		String top = "";

		if (a == 0) {
			top = "Er is geen top.";
		}
		else {
			top = "top = (" + alfa + ", " + beta + ")";
		}

		return top;
	}

	/**
	* De nulpunten worden berekend aan de hand van de vierkantsvergelijking.
	* @param a de richtingscoëffiënt van de grafiek. Deze bepaalt het aantal nulpunten.
	* @param alfa nodig om de nulpunten te berekenen
	* @param beta nodig om de nulpunten te berekenen
	* @return String de nulpunten van de grafiek
	*/
	public String nulpuntenBerekenen(double a, double alfa, double beta) { //y = 0
		String nulpunten = "";

		if (a == 0) {
			nulpunten = "Er zijn geen nulpunten.";
		}
		else {
			double discriminant = (Math.pow((-2 * alfa * a), 2) - 4 * a * (a * Math.pow(alfa, 2) + beta)); // D = (b²-4ac)

			if (discriminant > 0) {
				double xEen = -Math.sqrt(-beta / a) + alfa;
				double xTwee = Math.sqrt(-beta / a) + alfa;
				nulpunten =
					"Nulpunt 1 = (" + (Math.round(xEen * 100.0) / 100.0) + ", 0)\n" +
					"Nulpunt 2 = (" + (Math.round(xTwee * 100.0) / 100.0) + ", 0)";
			}
			else if (discriminant < 0) {
				double xEenTwee = (-(-2 * alfa * a)) / (2 * a);
				nulpunten = "nulpunt 1 = nulpunt 2 = (" + xEenTwee + ", 0)";
			}
			else {
				nulpunten = "Er zijn geen nulpunten.";
			}
		}

		return nulpunten;
	}

	/**
	* Als x = 0 kan het snijpunt met de y-as berekend worden.
	* @param a nodig om het snijpunt te berekenen
	* @param alfa nodig om het snijpunt te berekenen
	* @param beta nodig om het snijpunt te berekenen
	* @return String het snijpunt met de y-as
	*/
	public String snijpuntYas (double a, double alfa, double beta) { // x = 0
		double y = a * Math.pow((0 - alfa), 2) + beta;
		String snijpunt = "Snijpunt op de y-as = (0, " + y + ")";
		return snijpunt;
	}

	/**
	* De karakteristieken worden verzameld en afgeprint.
	* @param vorm vermeldt aan de gebruiker welke vorm de grafiek heeft
	* @param top geeft aan de gebruiker de coördinaten van de top
	* @param nulpunten geeft aan de gebruiker de coördinaten van de snijpunten met de x-as
	* @param snijpunt geeft aan de gebruiker de coördinaten van het snijpunt met de y-as
	* @return /
	*/
	public void karakteristiekenAfprinten(String vorm, String top, String nulpunten, String snijpunt) {
		System.out.println
		(
			"\n" +
			vorm + "\n" +
			top + "\n" +
			nulpunten + "\n" +
			snijpunt
		);
	}

	/**
	* Deze methode bepaalt de waarden in de tabel en dus tevens de punten die aangeduid worden op de grafiek.
	* @param a nodig om x- en y-coördinaten te bepalen
	* @param alfa nodig om de y-coördinaten te bepalen. Als a != 0, tevens gebruikt om x-coördinaten te bepalen.
	* @param beta nodig om de y-coördinaten te bepalen
	* @return double[][] de coördinatenkoppels nodig voor de tabel en grafiek
	*/
	public double[][] coordinatenBepalen(double a, double alfa, double beta) {
		double[][] tabel = new double[COORDINATEN][2]; //[aantal waarden][een x en een y rij]
		double x = 0.0, y = 0.0;

		if (a == 0) {
			x = 0 - (COORDINATEN - 1)/ 2.0;
		}
		else {
			x = alfa - ((COORDINATEN - 1)/ 2.0);
		}

		for (int i = 0; i < tabel.length; i++) {
			for (int j = 0; j < tabel[i].length; j++) {
				if (j == 0) { //x-waarde
					tabel[i][j] = x;
				}
				if (j == 1) { //y-waarde
					y = a * Math.pow(x, 2) + ( -2 * a * alfa) * x + ( a * Math.pow(alfa, 2) + beta);
					tabel[i][j] = Math.round(y * 10.0) / 10.0;
				}
			}

			x++;
		}

		return tabel;
	}

	/**
	* Deze methode berekent de grootte van elk vakje van de tabel (allen dezelfde grootte).
	* @param tabel het getal met de grootste lengte wordt gezocht door de lijst van waarden
	* @return int aantal streepjes die de breedte van elk vakje van de tabel bepaalt
	*/
	public int aantalStreepjesKaderBepalen(double[][] tabel) {
		String controle = "";
		int streepjes = 0, lengte = 0, aantalStreepjes = 0, lengteSpaties = 0;

		for (int i = 0; i < tabel.length; i++) {
			for (int j = 0; j < tabel[i].length; j++) {
				controle = "" + tabel[i][j];
				lengte = controle.length();

				if (lengte > streepjes) {
					streepjes = lengte;
				}
			}
		}

		aantalStreepjes = streepjes + 4; //2 spaties voor en na die langste waarde
		return aantalStreepjes;
	}

	/**
	* Deze methode print de omlijsting van de tabel af.
	* @param tabel bepaalt het aantal vakjes (en dus de grootte van de tabel)
	* @param aantalStreepjes bepaalt de (gelijke) breedte van elk vakje van de tabel
	* @return /
	*/
	public void kaderAfprinten(double[][] tabel, int aantalStreepjes) {
		String streepjesRij = "", streepjesOutput = "", plusjes = "+";

		for (int i = 0; i < COORDINATEN; i++) {
			streepjesRij = ""; //resetten

			for (int j = 0; j < aantalStreepjes; j++) {
				streepjesRij = streepjesRij + "-";
			}

			streepjesOutput = streepjesOutput + streepjesRij + plusjes;
		}

		System.out.println("\n+-----+" + streepjesOutput);
	}

	/**
	* Deze methode print alle waarden van de tabel af.
	* @param tabel bevat alle waarden
	* @param aantalStreepjes bepaalt de breedte van de vakjes waarin de waarden komen
	* @param optie print ofwel x, ofwel y af
	* @param cijfer bepaalt of de x-waarden of de y-waarden worden afgeprint
	* @return /
	*/
	public void waardenTabelAfprinten(double[][] tabel, int aantalStreepjes, String optie, int cijfer) {
		String controle = "", output = "", spatie = " ";
		int lengteSpaties = 0;
		System.out.print("| " + optie + " = |");

		for (int i = 0; i < tabel.length; i++) {
			controle = "" + tabel[i][cijfer];
			lengteSpaties = aantalStreepjes - controle.length();
			output = ""; //resetten

			for (int j = 0; j < (lengteSpaties / 2); j++) {
				output = output + spatie ;
			}

			if (lengteSpaties % 2 == 0) {
				System.out.print(output + tabel[i][cijfer] + output + "|");
			}
			else {
				System.out.print(output + spatie + tabel[i][cijfer] + output + "|");
			}
		}

		if (cijfer == 0) {
			System.out.print("\n");
		}
	}

	/**
	* Deze methode print de x-as af.
	* @param tabel bepaalt de waarden van de x-as
	* @return /
	*/
	public void grafischeWeergaveXas(double[][] tabel) {
		System.out.print(" ");

		for (int i = 0; i < tabel.length; i++) {
			System.out.print("   ");
			System.out.print(Math.round(tabel[i][0]));
		}
	}

	/**
	* Deze methode bepaalt de grootste waarde op de y-as.
	* @param tabel nodig indien a > 0
	* @param a nodig om de juiste parameters te selecteren
	* @param beta nodig indien a <= 0
	* @return double max waarde op de y-as
	*/
	public double grootsteWaardeYas(double[][] tabel, double a, double beta) {
		double max = 0.0;

		if (a > 0) {
			max = tabel[0][1];
		}
		else if (a < 0) {
			max = beta;
		}
		else {
			if (beta >= 0) {
				max = beta + 3.0;
			}
			else {
				max = 0;
			}
		}

		return max;
	}

	/**
	* Deze methode print de ganse grafiek af: x-as, y-as en de coördinaten van de waarden in de tabel.
	* @param tabel bevat alle gegevens om de coördinaten uit te zetten
	* @param a nodig om de laagste waarde van de y-as te bepalen
	* @param max bepaalt de 'witte ruimte' tussen alle waarden en coördinaten (en lijnt dus alles uit)
	* @param waardeBeta is de laagste waarde indien a >= 0
	* @return /
	*/
	public void grafischeWeergaveTotaal(double[][] tabel, double a, double max, double waardeBeta) {
		System.out.println("");
		int beta = (int)Math.round(waardeBeta);
		int maxAfgerond = (int)Math.round(max);
		int counter = 0, laagsteWaarde = 0;

		if (a >= 0) {
			laagsteWaarde = beta;
		}
		else {
			laagsteWaarde = (int)Math.round(tabel[0][1]);
		}

		for (int i = laagsteWaarde; i <= maxAfgerond; i++) {
			System.out.println("");
			System.out.print(maxAfgerond - counter);

			if ((maxAfgerond - counter >= 10) || ((maxAfgerond - counter < 0) && (maxAfgerond - counter > -10))) {
				System.out.print("  ");
			}
			else if (maxAfgerond - counter >= 100 || ((maxAfgerond - counter <= -10) && (maxAfgerond - counter > -100))) {
				System.out.print(" ");
			}
			else {
				System.out.print("   ");
			}

			for (int j = 0; j < tabel.length; j++) {
				if ((int)(Math.round(tabel[j][1])) == (maxAfgerond - counter)) {
					System.out.print("X");
				}
				else if (((maxAfgerond - counter) == 0) && ((int)(Math.round(tabel[j][0])) == 0)) {
					System.out.print("+");
				}
				else if ((maxAfgerond - counter) == 0) {
					System.out.print("-");
				}
				else if ((int)(Math.round(tabel[j][0])) == 0) {
					System.out.print("|");
				}
				else {
					System.out.print(" ");
				}

				String lengteTabel = "" + tabel[j][0];

				if (lengteTabel.length() >= 4) {
					System.out.print("    ");
				}
				else if (lengteTabel.length() >= 5) {
					System.out.print("     ");
				}
				else {
					System.out.print("   ");
				}
			}

			System.out.println("");
			counter++;
		}
	}

	/**
	* Deze methode is het hart van het programma. Deze schakelt tussen de verschillende methodes.
	* De parameters worden aan de gebruiker gevraagd en in verschillende methodes verwerkt.
	* De gebruiker kiest in de menu wat er afgeprint moet worden, of laat het programma afsluiten.
	* @param /
	* @return /
	*/
	public void start() {
		double a = inputDouble("Geef een waarde voor a:");         //formule: y = a(x-alfa)²+b
		double alfa = inputDouble("Geef een waarde voor alfa:");	 //ook: y = (a)x²+(-2*a*alfa)*x+(a*alfa²+b)
		double beta = inputDouble("Geef een waarde voor beta:");
		double maxYas = 0.0;
		String keuze = "", vorm = "", top = "" , nulpunten = "", snijpunt = "", x = "x", y = "y";
		int aantalStreepjes = 0;
		double waardenTabel[][] = coordinatenBepalen(a, alfa, beta);

		do {
			keuze = inputString
			(
				"\n" +
				"Wat wens je te doen? Je kan:\n" +
				"\t1. De karakteristieken weergeven\n" +
				"\t2. De waardentabel weergeven\n" +
				"\t3. De grafiek afdrukken\n" +
				"Geef je keuze (1/2/3/stop):"
			);

			if (keuze.equals("1")) {
				vorm = vormBepalen(a);
				top = topBerekenen(a, alfa, beta);
				nulpunten = nulpuntenBerekenen(a, alfa, beta);
				snijpunt = snijpuntYas(a, alfa, beta);
				karakteristiekenAfprinten(vorm, top, nulpunten, snijpunt); //printmethode
			}
			else if(keuze.equals("2")) {
				aantalStreepjes = aantalStreepjesKaderBepalen(waardenTabel);
				kaderAfprinten(waardenTabel, aantalStreepjes);
				waardenTabelAfprinten(waardenTabel, aantalStreepjes, x, 0);
				waardenTabelAfprinten(waardenTabel, aantalStreepjes, y, 1);
				kaderAfprinten(waardenTabel, aantalStreepjes);
			}
			else if(keuze.equals("3")) {
				grafischeWeergaveXas(waardenTabel);
				maxYas = grootsteWaardeYas(waardenTabel, a, beta);
				grafischeWeergaveTotaal(waardenTabel, a, maxYas, beta);
			}
			else if(keuze.equals("stop")) {
				System.out.println("Tot de volgende keer!");
			}
			else {
				System.out.println("Gelieve een geldige waarde (1/2/3/stop) in te geven!");
			}

		} while(!keuze.equals("stop"));
	}

	/**
	* De main methode selecteert het hoofdmenu.
	* @param args ongebruikt
	* @return /
	*/
	public static void main(String[] args) {
		Parabolen x = new Parabolen();
		x.start();
	}
}