import java.util.Scanner;
import java.util.Locale;

/**
 * Via deze klasse kan je informatie van de gebruiker inlezen. Er zijn in deze klasse volgende statische methoden
 * gedefinieerd:
 * <ul>
 * <li><tt>readString()</tt> voor het inlezen van een woord
 * <li><tt>readLine()</tt> voor het inlezen van een lijn tekst
 * <li><tt>readChar()</tt> voor het inlezen van een karakter
 * <li><tt>readByte(), readShort(), readInt()</tt> en <tt>readLong()</tt> voor het inlezen van een gehele waarde
 * <li><tt>readFloat()</tt> en <tt>readDouble()</tt> voor het inlezen van een re&euml;le waarde
 * <li><tt>readBoolean()</tt> voor het inlezen van een booleaanse waarde
 * </ul>.
 * Het gebruik van deze methoden wordt ge&iuml;llustreerd in het volgend stukje code:
 *
 * <pre>
 * public class Test {
 *    public void start() {
 *       String str = Input.readLine();
 *       String str = Input.readString();
 *       char	c = Input.readChar();
 *
 *       byte  	b = Input.readByte();
 *       short	s = Input.readShort();
 *       int	i = Input.readInt();
 *       long	l = Input.readLong();
 *
 *       float	f = Input.readFloat();
 *       double	d = Input.readDouble();
 *
 *       boolean bl = Input.readBoolean();
 *    }
 *
 *    public static void main(String args[]) {
 *       Test test = new Test();
 *       test.start();
 *    }
 * }
 * </pre>
 *
 * @author Kristien Van Assche
 * @version 28 september 2010
 */

public final class Input {
    /**
     * Leest een lijn tekst van de standaard invoer.
     *
     * @return De lijn tekst ingelezen van standaard invoer
     */
    public static String readLine() {
		return new Scanner(System.in).nextLine();
    }

    /**
     * Leest een woord van de standaard invoer.
     *
     * @return Het woord ingelezen van standaard invoer
     */
    public static String readString() {
		return new Scanner(System.in).next();
    }

	/**
     * Leest een karakter van de standaard invoer ; enkel het eerste karakter van een ingegeven lijn tekst wordt behouden, eventueel volgende karakters worden genegeerd.
     *
     * @return De karakter-waarde ingelezen van standaard invoer
     */
    public static char readChar() {
		return new Scanner(System.in).next().charAt(0);
	}

    /**
     * Leest een gehele waarde van type byte van de standaard invoer.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De byte-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static byte readByte() {
		return new Scanner(System.in).nextByte();
	}

    /**
     * Leest een gehele waarde van type short van de standaard invoer.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De short-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static short readShort() {
		return new Scanner(System.in).nextShort();
	}

	/**
     * Leest een gehele waarde van type int van de standaard invoer.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De int-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static int readInt() {
		return new Scanner(System.in).nextInt();
	}

	/**
     * Leest een gehele waarde van type long van de standaard invoer.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De long-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static long readLong() {
		return new Scanner(System.in).nextLong();
	}

	/**
     * Leest een re&euml;le waarde van type float van de standaard invoer <tt>(bv. 34.5)</tt>.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De float-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static float readFloat() {
		Scanner scan = new Scanner(System.in);
		scan.useLocale(new Locale("ENGLISH", "US"));
		return scan.nextFloat();
	}

	/**
     * Leest een re&euml;le waarde van type double van de standaard invoer  <tt>(bv. 34.5)</tt>.
     * Bij ongeldige ingave wordt een InputMismatchException gegenereerd
     *
     * @return De double-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static double readDouble() {
		Scanner scan = new Scanner(System.in);
		scan.useLocale(new Locale("ENGLISH", "US"));
		return scan.nextDouble();
	}

	/**
     * Leest een booleaanse waarde van de standaard invoer. Enkel de ingaves <tt>true</tt> en <tt>false</tt> zijn geldig.
     * Bij elke andere ingave wordt een InputMismatchException gegenereerd
     *
     * @return De boolean-waarde ingelezen van standaard invoer
     * @throws InputMismatchException
     */
    public static boolean readBoolean() {
		return new Scanner(System.in).nextBoolean();
	}

	private Input() {}
}

