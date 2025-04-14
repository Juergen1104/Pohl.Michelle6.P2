package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse um CSV Dateien einzulesen
 */
public class CSVReader {

  private final String path;

  public CSVReader(String path) {
    this.path = path;
  }

  /**
   * Liest die entsprechende CSV Datei
   *
   * @param fileName Name der einzulesenden Datei
   * @return Liste, welche String Arrays enthaelt. Ein Array enthaelt alle Daten der jeweiligen
   * Zeile.
   */
  public List<String[]> read(String fileName) {
    List<String[]> list = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(path + "/" + fileName));
      br.readLine(); //skip first line
      String line = br.readLine();
      while (line != null) {
        //eingelesene Zeile nach Komma aufteilen
        //Text in Anfuehrungszeichen wird nicht aufgeteilt
        String[] target = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        //Anfuehrungszeichen entfernen
        for (int i = 0; i < target.length; i++) {
          target[i] = target[i].replace("\"", "");
        }
        list.add(target);

        line = br.readLine();
      }
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
