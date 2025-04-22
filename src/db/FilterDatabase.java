package db;

import data.AthleteEventResult;
import data.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println(result.getPosition() + "\t" + result.getName() + "\t" + result.getCountry());
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
            System.out.println(athlete.getGames() + " " + athlete.getName() + " " + athlete.getBorn() + " " + athlete.getHeight() + " " + athlete.getWeight() + " " + athlete.getMedal());
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

    /**
     * Methode um alle verschiedenen Editionen der olympischen Spiele auszuwählen
     *
     * @return Gibt eine Liste mit allen verschiedenen Editionen zurueck (z.B. Sommer-/Winterspiele)
     */
    //1 Punkt
    public List<String> getOlympicEditions() {
        List<String> olympicEditions = new ArrayList<>();
        String sql = "SELECT DISTINCT edition FROM game_summary";

        try (Statement stm = connection.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                olympicEditions.add(rs.getString("edition"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic editions: " + e.getMessage());
        }
        return olympicEditions;
    }

    /**
     * SQL Statement um die Jahre auszuwaehlen, in denen olympische Spiele stattfanden
     */
    // 1 Punkt
    private void prepareStatementOlympicYears() {
        try {
            pstmOlympicYears = connection.prepareStatement("SELECT year FROM game_summary WHERE edition = ? AND reason_not_held IS NULL ORDER BY year ASC");
        } catch (SQLException e) {
            System.err.println("Error preparing statement for Olympic years: " + e.getMessage());
        }
    }

    /**
     * Methode um die Jahre auszuwaehlen, in denen olympische Spiele stattfanden
     *
     * @return Liste mit allen Jahren
     */
    //1 Punkt
    public List<Integer> getOlympicYears(String edition) {
        List<Integer> olympicYears = new ArrayList<>();
        try {
            pstmOlympicYears.setString(1, edition);
            try (ResultSet rs = pstmOlympicYears.executeQuery()) {
                while (rs.next()) {
                    olympicYears.add(Integer.valueOf(rs.getString("year")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic years: " + e.getMessage());
        }
        return olympicYears;
    }

    /**
     * SQL Statement um die id einer Ausgabe der Olympischen Spiele zu finden
     */
    // 1 Punkt
    private void prepareStatementOlympicEditionId() {
        try {
            pstmOlympicEditionIds = connection.prepareStatement("SELECT edition_id FROM game_summary WHERE edition = ? AND year = ?");
        } catch (SQLException e) {
            System.err.println("Error preparing statement for edition ID: " + e.getMessage());
        }
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

        try {
            pstmOlympicEditionIds.setString(1, edition);
            pstmOlympicEditionIds.setString(2, String.valueOf(year));
            try (ResultSet rs = pstmOlympicEditionIds.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("edition_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic edition ID: " + e.getMessage());
        }

        return -1;


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
        String sql = "SELECT DISTINCT sport FROM event_details WHERE edition_id = ? ORDER BY sport ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, edition_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    olympicSports.add(rs.getString("sport"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic sports: " + e.getMessage());
        }
        return olympicSports;
    }

    /**
     * SQL Statement um alle events zu einer Sportart in einem bestimmten Jahr zu filtern
     */
    // 1 Punkt
    private void prepareStatementOlympicEvents() {
        try {
            pstmOlympicEvents = connection.prepareStatement(
                    "SELECT DISTINCT event FROM event_details WHERE edition_id = ? AND sport = ? ORDER BY event ASC"
            );
        } catch (SQLException e) {
            System.err.println("Error preparing statement for Olympic events: " + e.getMessage());
        }
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
        try {
            pstmOlympicEvents.setInt(1, edition_id);
            pstmOlympicEvents.setString(2, sport);
            try (ResultSet rs = pstmOlympicEvents.executeQuery()) {
                while (rs.next()) {
                    olympicEvents.add(rs.getString("event"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic events: " + e.getMessage());
        }
        return olympicEvents;
    }

    /**
     * SQL Statement um die Ergebnisse eines Events zu filtern
     */
    // 1 Punkt
    private void prepareStatementOlympicResult() {
        try {
            pstmOlympicResults = connection.prepareStatement(
                    "SELECT ed.pos, a.name, a.country " +
                            "FROM event_details ed " +
                            "JOIN athletes a ON ed.athlete_id = a.athlete_id " +
                            "WHERE ed.edition_id = ? AND ed.sport = ? AND ed.event = ?"
            );
        } catch (SQLException e) {
            System.err.println("Error preparing statement for Olympic results: " + e.getMessage());
        }
    }

    /************************************************************************************************
     *                                    Aufgabe 2b
     ************************************************************************************************/

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
        try {
            pstmOlympicResults.setInt(1, edition_id);
            pstmOlympicResults.setString(2, sport);
            pstmOlympicResults.setString(3, event);

            try (ResultSet rs = pstmOlympicResults.executeQuery()) {
                while (rs.next()) {
                    String pos = rs.getString("pos");
                    String name = rs.getString("name");
                    String country = rs.getString("country");
                    olympicResults.add(new Result(pos, name, country));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic results: " + e.getMessage());
        }
        return olympicResults;
    }

    /**
     * Methode um alle Laender auszuwaehlen
     *
     * @return Liste mit allen Laendern
     */
    //2 Punkte
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        String sql = "SELECT DISTINCT country FROM athletes ORDER BY country ASC";

        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                countries.add(rs.getString("country"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching countries: " + e.getMessage());
        }
        return countries;
    }

    /**
     * SQL Statement um die Sportarten, an denen Athleten aus einem bestimmten Land teilgenommen haben
     * zu bestimmen
     */
    // 1 Punkt
    private void prepareStatementOlympicSports() {
        try {
            pstmOlympicSports = connection.prepareStatement(
                    "SELECT DISTINCT ed.sport " +
                            "FROM event_details ed " +
                            "JOIN athletes a ON ed.athlete_id = a.athlete_id " +
                            "WHERE a.country = ? " +
                            "ORDER BY ed.sport ASC"
            );
        } catch (SQLException e) {
            System.err.println("Error preparing statement for Olympic sports by country: " + e.getMessage());
        }
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
        try {
            pstmOlympicSports.setString(1, country);
            try (ResultSet rs = pstmOlympicSports.executeQuery()) {
                while (rs.next()) {
                    olympicSports.add(rs.getString("sport"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sports for country: " + e.getMessage());
        }
        return olympicSports;
    }

    /**
     * SQL Statement um Informationen ueber die Athleten zu bekommen
     */
    // 2
    private void prepareStatementOlympicAthletes() {
        try {
            pstmOlympicAthletes = connection.prepareStatement(
                    "SELECT ed.medal, a.name, a.born, a.height, a.weight, g.edition, g.year " +
                            "FROM event_details ed " +
                            "JOIN athletes a ON ed.athlete_id = a.athlete_id " +
                            "JOIN game_summary g ON ed.edition_id = g.edition_id " +
                            "WHERE a.country = ? AND ed.sport = ? AND a.sex = ? " +
                            "ORDER BY g.year DESC"
            );
        } catch (SQLException e) {
            System.err.println("Error preparing statement for Olympic athletes: " + e.getMessage());
        }
    }

    /************************************************************************************************
     *                                    Aufgabe 2c                                                *
     ************************************************************************************************/

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
        try {
            pstmOlympicAthletes.setString(1, country);
            pstmOlympicAthletes.setString(2, sport);
            pstmOlympicAthletes.setString(3, sex);

            try (ResultSet rs = pstmOlympicAthletes.executeQuery()) {
                while (rs.next()) {
                    String medal = rs.getString("medal");
                    String name = rs.getString("name");
                    String birthdate = rs.getString("born");
                    float height = rs.getFloat("height");
                    //Float height = rs.getString("height");
                    int weight = rs.getInt("weight");
                    String edition = rs.getString("edition");
                    String year = rs.getString("year");

                    String games = edition + " " + year;

                    AthleteEventResult athlete = new AthleteEventResult(medal, name, birthdate, height, weight, games);
                    olympicAthletes.add(athlete);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic athletes: " + e.getMessage());
        }
        return olympicAthletes;
    }

    /**
     * Methode um den Austragungsort einer Ausgabe der Olympischen Spiele zu filtern
     *
     * @param edition_id id des olympischen Spiels
     * @return Austragungsort des olympischen Spiels
     */
    //1 Punkt
    public String getOlympicVenue(int edition_id) {
        String venue = "";
        String sql = "SELECT venue FROM game_summary WHERE edition_id = " + edition_id;

        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                venue = rs.getString("venue");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Olympic venue: " + e.getMessage());
        }
        return venue;
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
        String comparison = oldest ? "MIN" : "MAX";

        String sql = "SELECT name, born FROM athletes WHERE born = (" +
                "SELECT " + comparison + "(a.born) " +
                "FROM event_details ed " +
                "JOIN athletes a ON ed.athlete_id = a.athlete_id " +
                "WHERE ed.edition_id = ?" +
                ")";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, edition_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    athlete[0] = rs.getString("name");
                    athlete[1] = rs.getString("born");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching oldest/youngest athlete: " + e.getMessage());
        }

        return athlete;
    }

    /************************************************************************************************
     *                                      Bonus
     ************************************************************************************************/

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

        String sex = male ? "Male" : "Female";

        String sql = "SELECT COUNT(DISTINCT a.athlete_id) AS total " +
                "FROM event_details ed " +
                "JOIN athletes a ON ed.athlete_id = a.athlete_id " +
                "WHERE ed.edition_id = ? AND a.sex = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, edition_id);
            pstmt.setString(2, sex);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    number = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching male/female count: " + e.getMessage());
        }
        return -1; //dummy return
    }

    /**
     * Bonus Aufgabe um einen ewigen Medaillenspiegel zu erstellen
     *
     * @return Liste mit den Laendern und den gewonnenen Medaillen
     */
    //5 Punkte
    public List<String[]> getOlympicMedalTally() {
        List<String[]> olympicMedalTally = new ArrayList<>();

        String sql = "SELECT country, " +
                "SUM(gold) AS gold, " +
                "SUM(silver) AS silver, " +
                "SUM(bronze) AS bronze " +
                "FROM medal_history " +
                "GROUP BY country " +
                "HAVING SUM(gold) > 0 AND SUM(silver) > 0 AND SUM(bronze) > 0 " +
                "ORDER BY SUM(gold) DESC, SUM(silver) DESC, SUM(bronze) DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] entry = new String[4];
                entry[0] = rs.getString("country");
                entry[1] = String.valueOf(rs.getInt("gold"));
                entry[2] = String.valueOf(rs.getInt("silver"));
                entry[3] = String.valueOf(rs.getInt("bronze"));
                olympicMedalTally.add(entry);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching medal tally: " + e.getMessage());
        }

        return olympicMedalTally;
    }

    /************************************************************************************************
     *                                    Ab hier nichts mehr aendern                               *
     ************************************************************************************************/

    /**
     * Schliesst alle PreparedStatements
     */
    public void closeStatements() {
        for (PreparedStatement pstm : new PreparedStatement[]{pstmOlympicYears, pstmOlympicEditionIds, pstmOlympicEvents, pstmOlympicResults, pstmOlympicSports, pstmOlympicAthletes}) {
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
