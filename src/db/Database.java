package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.Pragma;

public abstract class Database {

  protected Connection connection;
  private Properties properties;

  public Database() {
    init();
    connect();
  }

  private void connect() {
    init();
    try {
      Class.forName("org.sqlite.JDBC");
      SQLiteConfig config = new SQLiteConfig();
      config.setEncoding(SQLiteConfig.Encoding.UTF8);
      config.setPragma(Pragma.FOREIGN_KEYS, "ON");
      connection = DriverManager.getConnection("jdbc:sqlite:" + Parameters.dbFile, properties);
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Successfully connected to database");
  }

  public void disconnect() {
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        e.printStackTrace();
        System.exit(0);
      }
      System.out.println("Successfully disconnected from database");
    }
  }

  private void init() {
    properties = new Properties();
    properties.setProperty("PRAGMA foreign_keys", "ON");
  }
}
