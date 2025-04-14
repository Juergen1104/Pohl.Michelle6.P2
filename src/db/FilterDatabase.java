package db;

import data.AthleteEventResult;
import data.Result;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterDatabase extends Database {

  private PreparedStatement pstmOlympicYears, pstmOlympicEditionIds, pstmOlympicEvents, pstmOlympicResults, pstmOlympicSports, pstmOlympicAthletes;


  public FilterDatabase() {
    prepareStatementOlympicYears();
    prepareStatementOlympicEditionId();
    prepareStatementOlympicEvents();
    prepareStatementOlympicResult();
    prepareStatementOlympicSports();
    prepareStatementOlympicAthletes();
  }

  
  /************************************************************************************************
   *                                    Aufgabe 2a
   ************************************************************************************************/


  /**
   * Methode um alle verschiedenen Editionen der olympischen Spiele auszuwählen
   *
   * @return Gibt eine Liste mit allen verschiedenen Editionen zurueck (z.B. Sommer-/Winterspiele)
   */
  //1 Punkt
  public List<String> getOlympicEditions() {
    List<String> olympicEditions = new ArrayList<>();
    //TODO

    return olympicEditions;
  }

  /**
   * SQL Statement um die Jahre auszuwaehlen, in denen olympische Spiele stattfanden
   */
  // 1 Punkt
  private void prepareStatementOlympicYears() {
    //TODO

  }

  /**
   * Methode um die Jahre auszuwaehlen, in denen olympische Spiele stattfanden
   *
   * @return Liste mit allen Jahren
   */
  //1 Punkt
  public List<Integer> getOlympicYears(String edition) {
    List<Integer> olympicYears = new ArrayList<>();
    //TODO

    return olympicYears;
  }

  /**
   * SQL Statement um die id einer Ausgabe der Olympischen Spiele zu finden
   */
  // 1 Punkt
  private void prepareStatementOlympicEditionId() {
    //TODO

  }

  /**
   * Methode, um die id einer Ausgabe der Olympischen Spiele zu finden
   *
   * @param edition Edition des Spiels
   * @param year    Jahr in dem das Spiel stattgefunden hat
   * @return id des olympischen Spiels
   */
  //1 Punkt
  public int getOlympicEditionId(String edition, int year) {
    int id;
    //TODO

    return 0; //dummy return
  }

  /**
   * Methode um alle Sportarten der olympischen Spiele in einem bestimmten Jahr stattfanden zu
   * filtern
   *
   * @param edition_id id einer Ausgabe der Olympischen Spiele
   * @return Liste mit allen Sportarten (z.B. Athletics ...)
   */
  //2 Punkte
  public List<String> getOlympicSports(int edition_id) {
    List<String> olympicSports = new ArrayList<>();
    //TODO

    return olympicSports;
  }

  /**
   * SQL Statement um alle events zu einer Sportart in einem bestimmten Jahr zu filtern
   */
  // 1 Punkt
  private void prepareStatementOlympicEvents() {
    //TODO

  }

  /**
   * Methode um alle events zu einer Sportart in einem bestimmten Jahr zu filtern
   *
   * @param edition_id id einer Ausgabe der Olympischen Spiele
   * @param sport      Sportart (eine die es in diesem Jahr gab)
   * @return Liste mit allen events in einem bestimmten Jahr, die zu einer Sportart gehoeren z.B.
   * 100 m Sprint bei sport=Athletics
   */
  //1 Punkt
  public List<String> getOlympicEvents(int edition_id, String sport) {
    List<String> olympicEvents = new ArrayList<>();
    //TODO

    return olympicEvents;
  }

  /**
   * SQL Statement um die Ergebnisse eines Events zu filtern
   */
  // 1 Punkt
  private void prepareStatementOlympicResult() {
    //TODO

  }


  /**
   * Methode um die Ergebnisse eines Events zu filtern
   *
   * @param edition_id Jahr, in dem die olympischen Spiele stattfanden
   * @param sport      Sportart (eine die es in diesem Jahr gab)
   * @param event      event, das zu der uebergebenen Sportart gehoert
   * @return Liste mit Instanzen von {@link Result}
   */
  //2 Punkte
  public List<Result> getOlympicResults(int edition_id, String sport, String event) {
    List<Result> olympicResults = new ArrayList<>();
    //TODO

    return olympicResults;
  }

  /************************************************************************************************
   *                                    Aufgabe 2b
   ************************************************************************************************/


  /**
   * Methode um alle Laender auszuwaehlen
   *
   * @return Liste mit allen Laendern
   */
  //2 Punkte
  public List<String> getCountries() {
    List<String> countries = new ArrayList<>();
    //TODO

    return countries;
  }

  /**
   * SQL Statement um die Sportarten, an denen Athleten aus einem bestimmten Land teilgenommen haben
   * zu bestimmen
   */
  // 1 Punkt
  private void prepareStatementOlympicSports() {
    //TODO

  }

  /**
   * Methode um die Sportarten, an denen Athleten aus einem bestimmten Land teilgenommen haben zu
   * bestimmen
   *
   * @param country Land aus dem der/die Athlet/in kommt
   * @return Liste mit Sportarten, an denen die Athleten aus dem Land teilgenommen haben
   */
  //2 Punkte
  public List<String> getOlympicSports2(String country) {
    List<String> olympicSports = new ArrayList<>();
    //TODO

    return olympicSports;
  }


  /**
   * SQL Statement um Informationen ueber die Athleten zu bekommen
   */
  // 2
  private void prepareStatementOlympicAthletes() {
    //TODO

  }


  /**
   * Methode, um Informationen ueber die Athleten zu bekommen
   *
   * @param country Land aus dem der/die Athlet/in kommt
   * @param sport   Sportart
   * @param sex     Geschlecht des/der Athlet/in
   * @return Liste mit Instanzen von {@link AthleteEventResult}
   */
  //3 Punkte
  public List<AthleteEventResult> getOlympicAthletes(String country, String sport, String sex) {
    List<AthleteEventResult> olympicAthletes = new ArrayList<>();
    //TODO

    return olympicAthletes;
  }

  /************************************************************************************************
   *                                    Aufgabe 2c                                                *
   ************************************************************************************************/


  /**
   * Methode um den Austragungsort einer Ausgabe der Olympischen Spiele zu filtern
   *
   * @param edition_id id des olympischen Spiels
   * @return Austragungsort des olympischen Spiels
   */
  //1 Punkt
  public String getOlympicVenue(int edition_id) {
    String venue;
    //TODO

    return ""; //dummy return
  }

  /**
   * Methode um den aeltesten/juengsten Teilnehmer einer Ausgabe der Olympischen Spiele zu ermitteln
   *
   * @param edition_id id des olympischen Spiels
   * @param oldest     wenn wahr den aeltesten, sonst den juengsten Teilnehmer ermitteln
   * @return aeltesten oder juengsten Teilnehmer des olympischen Spiels
   */
  //3 Punkte
  public String[] getOldestOrYoungestAthlete(int edition_id, boolean oldest) {
    String[] athlete = new String[2];
    //TODO

    return athlete;
  }

  /**
   * Gibt die Anzahl an maennlichen/weiblichen Teilnehmern an
   *
   * @param male       wenn wahr dann maennliche Teilnehmer, sonst weibliche
   * @param edition_id id des olympischen Spiels
   * @return Anzahl der maennlichen/weiblichen Teilnehmern
   */
  //4 Punkte
  public int getMaleFemaleCount(boolean male, int edition_id) {
    int number;
    //TODO
    return -1; //dummy return
  }

  /************************************************************************************************
   *                                      Bonus
   ************************************************************************************************/


  /**
   * Bonus Aufgabe um einen ewigen Medaillenspiegel zu erstellen
   *
   * @return Liste mit den Laendern und den gewonnenen Medaillen
   */
  //5 Punkte
  public List<String[]> getOlympicMedalTally() {
    List<String[]> olympicMedalTally = new ArrayList<>();
    //TODO

    return olympicMedalTally;
  }
  
  /************************************************************************************************
   *                                    main Methode zum Testen ohne GUI                               *
   ************************************************************************************************/

  
  public static void main(String[] args) {
	    FilterDatabase fdb = new FilterDatabase();

	    ///Zum Testen von Aufgabe 2a
	    //Testen der Methode getOlympicEditions
	    System.out.println("olympic editions:");
	    List<String> editions = fdb.getOlympicEditions();
	    for (String edition : editions) {
	      System.out.println(edition);
	    }
	    System.out.println();

	    //Testen der Methode getOlympicYears
	    System.out.println("years:");
	    List<Integer> years = fdb.getOlympicYears("Summer Olympics");
	    for (int i = 0; i < 5; i++) {
	      System.out.println(years.get(i));
	    }
	    System.out.println();

	    //Testen der Methode getOlympicEditionId
	    int edition_id = fdb.getOlympicEditionId("Summer Olympics", 2016);
	    System.out.println("editionId: " + edition_id + "\n");

	    //Testen der Methode getOlympicSports
	    System.out.println("olympic sports:");
	    List<String> sports = fdb.getOlympicSports(54);
	    for (int i = 0; i < 5; i++) {
	      System.out.println(sports.get(i));
	    }
	    System.out.println();

	    //Testen der Methode getOlympicEvents
	    System.out.println("olympic events:");
	    List<String> events = fdb.getOlympicEvents(54, "Tennis");
	    for (int i = 0; i < 5; i++) {
	      System.out.println(events.get(i));
	    }
	    System.out.println();

	    //Testen der Methode getOlympicResults
	    System.out.println("olympic results:");
	    List<Result> results = fdb.getOlympicResults(54, "Tennis", "Singles, Women");
	    results = results.stream().sorted().toList();
	    for (int i = 0; i < 5; i++) {
	      Result result = results.get(i);
	      System.out.println(
	          result.getPosition() + "\t" + result.getName() + "\t" + result.getCountry());
	    }
	    System.out.println();

	    ///Zum Testen von Aufgabe 2b
	    //Testen der Methode getCountries
	    System.out.println("countries:");
	    List<String> countries = fdb.getCountries();
	    for (int i = 0; i < 5; i++) {
	      System.out.println(countries.get(i));
	    }
	    System.out.println();

	    //Testen der Methode getOlympicSports2
	    System.out.println("olympic sports:");
	    List<String> sport = fdb.getOlympicSports2("Germany");
	    for (int i = 0; i < 5; i++) {
	      System.out.println(sport.get(i));
	    }
	    System.out.println();

	    //Testen der Methode getOlympicAthletes
	    System.out.println("olympic athletes:");
	    List<AthleteEventResult> athletes = fdb.getOlympicAthletes("Germany", "Swimming", "Female");
	    for (int i = 0; i < 5; i++) {
	      AthleteEventResult athlete = athletes.get(i);
	      System.out.println(
	          athlete.getGames() + " " + athlete.getName() + " " + athlete.getBorn() + " "
	              + athlete.getHeight() + " " + athlete.getWeight() + " " + athlete.getMedal());
	    }
	    System.out.println();

	    ///Zum Testen von Aufgabe 2c
	    //Testen der Methode getOlympicVenue
	    System.out.println("venue:");
	    String venue = fdb.getOlympicVenue(1);
	    System.out.println(venue);
	    System.out.println();

	    //Testen der Methode getOldestOrYoungestAthlete
	    System.out.println("oldest athlete:");
	    String[] oldest = fdb.getOldestOrYoungestAthlete(1, true);
	    System.out.println(oldest[0] + "\t" + oldest[1]);
	    System.out.println();

	    //Testen der Methode getOldestOrYoungestAthlete
	    System.out.println("youngest athlete:");
	    String[] youngest = fdb.getOldestOrYoungestAthlete(1, false);
	    System.out.println(youngest[0] + "\t" + youngest[1]);
	    System.out.println();

	    //Testen der Methode getMaleFemaleProportion
	    System.out.println("female participants");
	    System.out.println(fdb.getMaleFemaleCount(false, 1));
	    System.out.println();

	    //Testen der Methode getMaleFemaleProportion
	    System.out.println("male participants");
	    System.out.println(fdb.getMaleFemaleCount(true, 1));
	    System.out.println();

	    ///Zum Testen der Bonusaufgabe
	    //Testen der Methode getOlympicMedalTally
	    System.out.println("medal tally:");
	    List<String[]> medalTally = fdb.getOlympicMedalTally();
	    for (int i = 0; i < 5; i++) {
	      String[] country = medalTally.get(i);
	      System.out.println(country[0] + "\t" + country[1] + "\t" + country[2] + "\t" + country[3]);
	    }

	    //Statements schließen und Verbindung zur Datenbank schließen
	    fdb.closeStatements();
	    fdb.disconnect();
	  }


  /************************************************************************************************
   *                                    Ab hier nichts mehr aendern                               *
   ************************************************************************************************/


  /**
   * Schliesst alle PreparedStatements
   */
  public void closeStatements() {
    for (PreparedStatement pstm : new PreparedStatement[]{pstmOlympicYears, pstmOlympicEditionIds,
        pstmOlympicEvents, pstmOlympicResults, pstmOlympicSports, pstmOlympicAthletes}) {
      if (pstm != null) {
        try {
          pstm.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
