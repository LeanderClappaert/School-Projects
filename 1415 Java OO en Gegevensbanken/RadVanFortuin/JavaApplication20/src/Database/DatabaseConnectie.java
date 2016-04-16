package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Deze klasse handelt alle interactie met de database.
 * Deze methodes worden op hun beurt geimplementeerd via de logische klasse(n) om zo gegevens uit te wisselen.
 * @author Leander Clappaert
 * @version 30/05/2015
 */
public class DatabaseConnectie {
    private String dbName = "dbrvff";
    private String login = "root";
    private String pass = "";
    private Connection con;

    /**
     * De connectie met de databank wordt gelegd.
     */
    public DatabaseConnectie() { 
	try {
	    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, login, pass);
	}
	catch(java.sql.SQLException e) {e.printStackTrace();}
    }
    
    /**
     * Deze methode sluit de connectie met de databank.
     */
    public void closeConnection() {
	try{
	    con.close();
	}
	catch(SQLException e) {e.printStackTrace();}
    }
    
    /**
     * Returned een lijst met onderwerpen voor een bepaald niveau.
     * @param niveau het niveau van de vragen en dus ook de thema's
     * @return lijst met thema's
     */
    public String getRandomThema(String niveau) {
        Statement stmt = null;
        ArrayList<String> themalijst = new ArrayList<>();
        int keuze = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery
                (
                    "select themas.naam from themas inner join niveaus_has_themas on " +
                    "themas.id=niveaus_has_themas.thema_id inner join niveaus on " +
                    "niveaus_has_themas.niveau_id=niveaus.id " +
                    "where niveaus.naam = '" + niveau + "';"
                );
            while (rs.next()) {
                String lijst = rs.getString("themas.naam");   
                themalijst.add(new String(lijst));
            }

            keuze = (int)(Math.random() * themalijst.size());
        }
        catch (SQLException e) {}
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}

        return themalijst.get(keuze);
    }
    
    /**
     * Er wordt een random gekozen woord uit de databank gehaald.
     * @param niveau het niveau van het mogelijke antwoord
     * @param thema het thema waarbij het woord hoort
     * @return het te raden woord
     */
    public String getRandomWoord(String niveau, String thema) {
        Statement stmt = null;
        ArrayList<String> woordlijst = new ArrayList<>();
        int keuze = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery
                (
                    "select antwoorden.woord from antwoorden inner join themas on " +
                    "antwoorden.thema_id=themas.id inner join niveaus on " +
                    "antwoorden.niveau_id=niveaus.id " + 
                    "where niveaus.naam = '" + niveau + "' and themas.naam = '" + thema + "';"
                );
            while (rs.next()) {
                String lijst = rs.getString("antwoorden.woord");   
                woordlijst.add(new String(lijst));
            }
            
            keuze = (int)(Math.random() * woordlijst.size());
        }
        catch (SQLException e) {
            System.out.println(e);
        }
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}

        return woordlijst.get(keuze);
    }
    
    /**
     * Er wordt aan de hand van de naam en de leeftijd van de speler gekeken of deze al in de databank zit.
     * @param naam naam van de speler
     * @param leeftijd leeftijd van de speler
     * @return het id van de speler. Indien 0 = zit er nog niet in
     */
    //kijken of deze reeds in database staat of niet
    public int checkID(String naam, int leeftijd) {
        PreparedStatement pstmt = null;
        int id = 0;

        try {
            pstmt = con.prepareStatement
            (
                "select id from spelers where spelers.naam = ? " +
                "and spelers.leeftijd = ?;"
            );
            pstmt.setString(1, naam);
            pstmt.setInt(2, leeftijd);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                id = rs.getInt("id");  
            }
        }
        catch (SQLException e) {}
	finally {
	    if(pstmt != null) {
		try{pstmt.close();} catch(Exception e) {}
	    }
	}
        
        return id;
    }
    
    /**
     * Een nieuwe speler wordt opgeslagen in de databank.
     * @param naam naam van de speler
     * @param leeftijd leeftijd van de speler
     */
    public void maakNieuweSpelerAan(String naam, int leeftijd) {
        PreparedStatement pstmt = null;
        
        //niveau instellen voor de speler.
        int niveau = 0;
        if (leeftijd >= 6 && leeftijd <= 12) niveau = 1;
        else if (leeftijd >= 13 && leeftijd <= 17) niveau = 2;
        else niveau = 3;
        
        try {
            pstmt = con.prepareStatement
            (
                "insert into spelers (niveau_id, naam, leeftijd, maxSaldo, overallSaldo) " +
                "values (?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, niveau);
            pstmt.setString(2, naam);
            pstmt.setInt(3, leeftijd);
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {System.out.println(e);}
	finally {
	    if(pstmt != null) {
		try{pstmt.close();} catch(Exception e) {}
	    }
	} 
    }
    
    /**
     * Haalt het groote bedrag ooit gewonnen in 1 ronde op vanuit de databank.
     * @param id het id van de speler wiens bedrag gereturned moet worden
     * @return het maximale saldo ooit behaald in 1 ronde
     */
    public int getMaxSaldo(int id) {
        Statement stmt = null;
        int saldo = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery
                (
                    "select spelers.maxSaldo from spelers where id = " + id + ";"
                );
            while (rs.next()) {
                saldo = rs.getInt("spelers.maxSaldo");   
            }
        }
        catch (SQLException e) {System.out.println(e);}
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}

        return saldo;  
    }
    
    /**
     * Haalt het totale bedrag, dat de speler over alle spellen heeft verdiend, op uit de databank.
     * @param id het id van de speler wiens bedrag gereturned moet worden
     * @return het saldo dat deze speler al verdiend heeft
     */
    public int getOverallSaldo(int id) {
        Statement stmt = null;
        int saldo = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery
                (
                    "select spelers.overallSaldo from spelers where id = " + id + ";"
                );
            while (rs.next()) {
                saldo = rs.getInt("spelers.overallSaldo");   
            }
        }
        catch (SQLException e) {System.out.println(e);}
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}

        return saldo;   
    }
    
    /**
     * Deze methode update het huidige maximale saldo ooit verdiend in 1 ronde met een hoger bedrag.
     * @param id het id van de speler
     * @param maxSaldo het nieuwe maximale saldo
     */
    public void updateMaxSaldo(int id, int maxSaldo) {
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql =
                    "UPDATE spelers " +
                    "SET maxSaldo = " + maxSaldo + " where id = " + id + ";";
            
            stmt.executeUpdate(sql);
        }
        catch (SQLException e) {System.out.println(e);}
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}
    }
    
    /**
     * Deze methode update zijn totale saldo door het huidig gewonnen bedrag bij zijn totaal op te tellen.
     * @param id het id van de speler
     * @param huidigSaldo het saldo dat deze ronde verdiend werd
     * @param overallSaldo het huidige totale saldo
     */
    public void updateOverallSaldo(int id, int huidigSaldo, int overallSaldo) {
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql =
                    "UPDATE spelers " +
                    "SET overallSaldo = " + (huidigSaldo + overallSaldo) + " where id = " + id + ";";
            
            stmt.executeUpdate(sql);
        }
        catch (SQLException e) {System.out.println(e);}
	finally {
	    if(stmt != null) {
		try{stmt.close();} catch(Exception e) {}
	    }
	}
    }
}