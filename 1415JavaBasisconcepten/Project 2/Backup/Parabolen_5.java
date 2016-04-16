/**
*
* @author Leander Clappaert
* @version 25 December 2014
*/
public class Parabolen {
	final int COORDINATEN = 7;

	public String inputString(String ingave) {
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public double inputDouble(String ingave) {
		System.out.println(ingave);
		double input = Input.readDouble();
		return input;
	}

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

	public String snijpuntYas (double a, double alfa, double beta) { // x = 0
		double y = a * Math.pow((0 - alfa), 2) + beta;
		String snijpunt = "Snijpunt op de y-as = (0, " + y + ")";
		return snijpunt;
	}

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

	public double[][] coordinatenBepalen(double a, double alfa, double beta) {
		double[][] tabel = new double[COORDINATEN][2];
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

	public int aantalStreepjesKaderBepalen(double[][] tabel) {
		String controle = "";
		int streepjes = 0, lengte = 0, aantalStreepjes = 0, lengteSpaties = 0;

		for (int i = 0; i < tabel.length; i++) { //doel: langste waarde zoeken
			for (int j = 0; j < tabel[i].length; j++) {
				controle = "" + tabel[i][j];
				lengte = controle.length();

				if (lengte > streepjes) {
					streepjes = lengte;
				}
			}
		}

		aantalStreepjes = streepjes + 4; //2 spaties voor en na elke waarde
		return aantalStreepjes;
	}

	public void kaderAfprinten(double[][] tabel, int aantalStreepjes) {
		String streepjesRij = "", streepjesOutput = "", plusjes = "+";

		for (int i = 0; i < COORDINATEN; i++) { //doel: print boven- en onderzijde grafische kader
			streepjesRij = ""; //resetten

			for (int j = 0; j < aantalStreepjes; j++) {
				streepjesRij = streepjesRij + "-";
			}

			streepjesOutput = streepjesOutput + streepjesRij + plusjes;
		}

		System.out.println("\n+-----+" + streepjesOutput);
	}

	public void tabelAfprinten(double[][] tabel, int aantalStreepjes, String optie, int cijfer) {
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

	public void grafischeWeergaveXas(double[][] tabel) {
		System.out.print(" ");

		for (int i = 0; i < tabel.length; i++) {
			System.out.print("   ");
			System.out.print(Math.round(tabel[i][0]));
		}
	}

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
				tabelAfprinten(waardenTabel, aantalStreepjes, x, 0);
				tabelAfprinten(waardenTabel, aantalStreepjes, y, 1);
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

	public static void main(String[] args) {
		Parabolen x = new Parabolen();
		x.start();
	}
}