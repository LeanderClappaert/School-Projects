/**
*
* @author Leander Clappaert
* @version 25 December 2014
*/
public class Parabolen {
	final int COORDINATEN = 11;

	public String input(String ingave) {
		System.out.println(ingave);
		String input = Input.readString();
		return input;
	}

	public String vormBepalen(String rico) {
		String vorm = "";
		double a = Double.parseDouble(rico);

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

	public String topBerekenen(String waardeAlfa, String waardeBeta) {
		double x = Double.parseDouble(waardeAlfa);
		double y = Double.parseDouble(waardeBeta);

		String top = "top = (" + x + ", " + y + ")";
		return top;
	}

	public String nulpuntenBerekenen(String rico, String waardeAlfa, String waardeBeta) { //y = 0
		double a = Double.parseDouble(rico);
		double alfa = Double.parseDouble(waardeAlfa);
		double beta = Double.parseDouble(waardeBeta);
		String nulpunten = "";

		double discriminant = (Math.pow((-2 * alfa * a), 2) - 4 * a * (a * Math.pow(alfa, 2) + beta)); // D = (b�-4ac)

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

		return nulpunten;
	}

	public String snijpuntYas (String rico, String waardeAlfa, String waardeBeta) { // x = 0
		double a = Double.parseDouble(rico);
		double alfa = Double.parseDouble(waardeAlfa);
		double beta = Double.parseDouble(waardeBeta);

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

	public double[][] coordinatenBepalen(String rico, String waardeAlfa, String waardeBeta) {
		double[][] tabel = new double[COORDINATEN][2];
		double a = Double.parseDouble(rico);
		double alfa = Double.parseDouble(waardeAlfa); //x-waarde top
		double beta = Double.parseDouble(waardeBeta); //y-waarde top
		double x = alfa - ((COORDINATEN - 1)/ 2.0);
		double y = 0.0;

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

	public void grafischeWeergaveYas(double[][] tabel, double max, String waardeBeta) {
		System.out.println("");
		int beta = (int)Math.round(Double.parseDouble(waardeBeta));
		int maxAfgerond = (int)Math.round(max);
		int counter = 0;

		for (int i = maxAfgerond; i >= beta; i--) {
			System.out.println("");
			System.out.print(maxAfgerond - counter);

			for (int j = 0; j < tabel.length; j++) {
				System.out.print("   ");

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
			}

			System.out.println("");
			counter++;
		}
	}

	public double grootsteWaardeYas(double[][] tabel, String rico, String waardeBeta) {
		double a = Double.parseDouble(rico);
		double beta = Double.parseDouble(waardeBeta);
		double max = 0.0;

		if (a > 0) {
			max = tabel[0][1];
		}
		else if (a < 0) {
			max = beta;
		}

		return max;
	}

	public void start() {
		String a = input("Geef een waarde voor a:");         //formule: y = a(x-alfa)�+b
		String alfa = input("Geef een waarde voor alfa:");	 //ook: y = (a)x�+(-2*a*alfa)*x+(a*alfa�+b)
		String beta = input("Geef een waarde voor beta:");
		String keuze = "", vorm = "", top = "" , nulpunten = "", snijpunt = "", x = "x", y = "y";
		int aantalStreepjes = 0;
		double maxYas = 0.0;
		double waardenTabel[][] = coordinatenBepalen(a, alfa, beta);

		do {
			keuze = input
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
				top = topBerekenen(alfa, beta);
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
				grafischeWeergaveYas(waardenTabel, maxYas, beta);
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