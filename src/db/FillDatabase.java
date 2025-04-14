package db;

import io.CSVReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Fuellt die Tabellen der Datenbank mit den entsprechenden Daten aus den CSV Dateien
 */
public class FillDatabase extends Database {

  private final CSVReader reader = new CSVReader(Parameters.resDir);
  private PreparedStatement pstmAthletes, pstmEventDetails, pstmGameSummary;

  public static void main(String[] args) {
    FillDatabase db = new FillDatabase();
    db.fillTables();
    db.disconnect();
  }


  /************************************************************************************************
   *                                    Aufgabe 1
   ************************************************************************************************/

  //3 Punkte
  private void prepareStatements() {
    //TODO

  }

  /**
   * Fuellt die Tabelle athletes
   */
  //3 Punkte
  private void fillAthletesTable() {
    clearTable("athletes");
    List<String[]> athletes = reader.read("athletes.csv");
    //TODO

  }

  /**
   * Fuellt die Tabelle event_details
   */
  //2 Punkte
  private void fillEventDetailsTable() {
    clearTable("event_details");
    List<String[]> eventDetails = reader.read("event_details.csv");
    //TODO

  }

  /**
   * Fuellt die Tabelle game_summary
   */
  //2 Punkte
  private void fillGameSummaryTable() {
    clearTable("game_summary");
    List<String[]> gameSummary = reader.read("game_summary.csv");
    //TODO

  }

  /************************************************************************************************
   *                                    Ab hier nichts mehr aendern                               *
   ************************************************************************************************/


  /**
   * Fuellt alle Tabellen
   */
  private void fillTables() {
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