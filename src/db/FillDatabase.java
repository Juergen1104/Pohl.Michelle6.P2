package db;

import io.CSVReader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

/**
 * Fuellt die Tabellen der Datenbank mit den entsprechenden Daten aus den CSV Dateien
 */
public class FillDatabase extends Database {

  private final CSVReader reader = new CSVReader(Parameters.resDir);
  private PreparedStatement pstmAthletes, pstmEventDetails, pstmGameSummary;

    public static void main(String[] args) throws SQLException {
    FillDatabase db = new FillDatabase();
    db.fillTables();
    db.disconnect();
  }


  /************************************************************************************************
   *                                    Aufgabe 1
   ************************************************************************************************/

  //3 Punkte
  private void prepareStatements() throws SQLException {
      pstmAthletes = connection.prepareStatement(
              "INSERT INTO athletes (athlete_id, name, sex, born, height, weight, country) VALUES (?, ?, ?, ?, ?, ?, ?)"
      );
      pstmEventDetails = connection.prepareStatement(
              "INSERT INTO event_details (edition_id, athlete_id, sport, event, pos, medal) VALUES (?, ?, ?, ?, ?, ?)"
      );
      pstmGameSummary = connection.prepareStatement(
              "INSERT INTO game_summary (edition_id, edition, year, city, country, reason_not_held) VALUES (?, ?, ?, ?, ?, ?)"
      );
  }

  /**
   * Fuellt die Tabelle athletes
   */
  //3 Punkte
  private void fillAthletesTable() throws SQLException {
    clearTable("athletes");
    List<String[]> athletes = reader.read("athletes.csv");
    //TODO
      for (String[] a : athletes) {
          pstmAthletes.setInt(1, Integer.parseInt(a[0])); // athlete_id
          pstmAthletes.setString(2, a[1]); // name
          pstmAthletes.setString(3, a[2]); // sex

          // born
          if (a[3].isEmpty()) {
              pstmAthletes.setNull(4, Types.VARCHAR);
          } else {
              pstmAthletes.setString(4, a[3]);
          }

          // height
          if (a[4].isEmpty()) {
              pstmAthletes.setNull(5, Types.NUMERIC);
          } else {
              pstmAthletes.setDouble(5, Double.parseDouble(a[4]));
          }

          // weight
          if (a[5].isEmpty()) {
              pstmAthletes.setNull(6, Types.INTEGER);
          } else {
              pstmAthletes.setInt(6, Integer.parseInt(a[5]));
          }

          pstmAthletes.setString(7, a[6]); // country

          pstmAthletes.addBatch();
      }

      pstmAthletes.executeBatch();





  }

  /**
   * Fuellt die Tabelle event_details
   */
  //2 Punkte
  private void fillEventDetailsTable() throws SQLException {
    clearTable("event_details");
    List<String[]> eventDetails = reader.read("event_details.csv");
      for (String[] e : eventDetails) {
          pstmEventDetails.setInt(1, Integer.parseInt(e[0])); // edition_id
          pstmEventDetails.setInt(1, Integer.parseInt(e[0])); // edition_id
          pstmEventDetails.setInt(2, Integer.parseInt(e[1])); // athlete_id
          pstmEventDetails.setString(3, e[2]); // sport
          pstmEventDetails.setString(4, e[3]); // event
          pstmEventDetails.setString(5, e[4]); // pos
          pstmEventDetails.setString(6, e[5]); // medal
          pstmEventDetails.addBatch();
      }
      pstmEventDetails.executeBatch();
  }

  /**
   * Fuellt die Tabelle game_summary
   */
  //2 Punkte
  private void fillGameSummaryTable() throws SQLException {
    clearTable("game_summary");
    List<String[]> gameSummary = reader.read("game_summary.csv");
      for (String[] g : gameSummary) {
          pstmGameSummary.setInt(1, Integer.parseInt(g[0])); // edition_id
          pstmGameSummary.setString(2, g[1]); // edition
          pstmGameSummary.setString(3, g[2]); // year
          pstmGameSummary.setString(4, g[3]); // city
          pstmGameSummary.setString(5, g[4]); // country

          if (g[5].isEmpty()) {
              pstmGameSummary.setNull(6, Types.VARCHAR); // reason_not_held
          } else {
              pstmGameSummary.setString(6, g[5]);
          }
          pstmGameSummary.addBatch();
      }
      pstmGameSummary.executeBatch();

  }

  /************************************************************************************************
   *                                    Ab hier nichts mehr aendern                               *
   ************************************************************************************************/


  /**
   * Fuellt alle Tabellen
   */
  private void fillTables() throws SQLException {
    try {
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    prepareStatements();
    fillAthletesTable();
    fillEventDetailsTable();
    fillGameSummaryTable();

    try {
      connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    closeStatements();
  }

  /**
   * Loescht die jeweilige Tabelle
   *
   * @param name Name der zu loeschenden Tabelle
   */
  private void clearTable(String name) {
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM " + name + ";");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Schliesst alle prepared Statements falls noetig/moeglich.
   */
  private void closeStatements() {
    try {
      if (pstmAthletes != null && !pstmAthletes.isClosed()) {
        pstmAthletes.close();
      }
      if (pstmEventDetails != null && !pstmEventDetails.isClosed()) {
        pstmEventDetails.close();
      }
      if (pstmGameSummary != null && !pstmGameSummary.isClosed()) {
        pstmGameSummary.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}