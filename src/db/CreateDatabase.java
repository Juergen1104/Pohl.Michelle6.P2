package db;

import java.sql.SQLException;
import java.sql.Statement;


/**
 * Klasse, um die Tabellen der Datenbank zu erstellen
 */
public class CreateDatabase extends Database {

  public static void main(String[] args) {
    CreateDatabase db = new CreateDatabase();
    db.createTables();
    db.disconnect();
  }


  /**
   * erstellt alle Tabellen
   */
  private void createTables() {
    createAthletes();
    createEventDetails();
    createGameSummary();
  }


  /**
   * Entfernt die entsprechende Tabelle aus der Datenbank um ggf. alte Tabellen zu loeschen
   *
   * @param name Name der Tabelle, die entfernt werden soll
   */
  private void dropTables(String name) {
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DROP TABLE IF EXISTS " + name);
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Erstellt die Tabelle, welche die Informationen ueber die Athleten enthaelt
   */
  private void createAthletes() {
    dropTables("athletes");
    try {
      Statement statement = connection.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS athletes (athlete_id INTEGER PRIMARY KEY NOT NULL,"
          + "name TEXT NOT NULL, sex TEXT NOT NULL, born DATE, height NUMERIC, weight INTEGER, "
          + "country TEXT NOT NULL)";
      statement.executeUpdate(sql);
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Erstellt die Tabelle, welche die Ergebnisse aller Wettkaempfe bei den olympischen Spielen
   * enthaelt
   */
  private void createEventDetails() {
    dropTables("event_details");
    try {
      Statement statement = connection.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS event_details "
          + "(edition_id INTEGER NOT NULL REFERENCES game_summary (edition_id),"
          + "athlete_id INTEGER NOT NULL REFERENCES athletes (athlete_id),"
          + "sport TEXT NOT NULL,event TEXT NOT NULL,pos TEXT NOT NULL,medal Text)";
      statement.executeUpdate(sql);
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Erstellt die Tabelle, welche die Informationen zum jeweiligen Austragungsort der olympischen
   * Spiele enthaelt
   */
  private void createGameSummary() {
    dropTables("game_summary");
    try {
      Statement statement = connection.createStatement();
      String sql =
          "CREATE TABLE IF NOT EXISTS game_summary (edition_id INTEGER PRIMARY KEY NOT NULL,"
              + "edition TEXT NOT NULL, year TEXT NOT NULL,city TEXT NOT NULL,"
              + "country TEXT NOT NULL, reason_not_held TEXT)";
      statement.executeUpdate(sql);
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
