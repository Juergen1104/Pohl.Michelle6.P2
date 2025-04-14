package gui;

import data.AthleteEventResult;
import data.Result;
import db.FilterDatabase;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class GUI extends JFrame {

  //Instanz, um die gesuchten Werte aus der Datenbank zu filtern
  private final FilterDatabase fdb = new FilterDatabase();
  //Breite der GUI
  private final int w = 1000;
  //Hoehe der GUI
  private final int h = 500;
  //Button um das panel mit den Ergebnissen anzuzeigen
  private JToggleButton resultsButton;
  //Button um das panel mit den Informationen zu den Athleten anzuzeigen
  private JToggleButton athletesButton;
  //Button um das panel mit dem Medaillenspiegel anzuzeigen
  private JToggleButton medalTallyButton;


  public GUI() {
    super.setTitle("Olympische Spiele");
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        fdb.closeStatements();
        fdb.disconnect();
        System.exit(0);
      }
    });
    createComponents();
    this.getContentPane().setLayout(new BoxLayout(super.getContentPane(), BoxLayout.Y_AXIS));
    this.getContentPane().setPreferredSize(new Dimension(w, h));
    this.pack();
  }

  public static void main(String[] args) {
    GUI gui = new GUI();
    gui.setVisible(true);
  }

  /**
   * Erstellt die Komponenten fuer die GUI
   */
  private void createComponents() {
    Container container = this.getContentPane();

    //panel um das Thema zu waehlen
    JPanel topPanel = new JPanel();
    createTopPanel(topPanel);
    container.add(topPanel);

    //panel um die Ergebnisse anzuzeigen
    JPanel resultsPanel = new JPanel();
    fillResultPanel(resultsPanel);
    resultsPanel.setVisible(true);
    container.add(resultsPanel);

    //panel um Informationen ueber die Athleten anzuzeigen
    JPanel athletesPanel = new JPanel();
    fillAthletesPanel(athletesPanel);
    athletesPanel.setVisible(false);
    container.add(athletesPanel);

    //panel um den Medaillenspiegel anzuzeigen
    JPanel medalPanel = new JPanel();
    fillMedalPanel(medalPanel);
    medalPanel.setVisible(false);
    container.add(medalPanel);

    addListener(resultsPanel, athletesPanel, medalPanel);
  }

  /**
   * Erstellt das oberste Panel, welches die Buttons zum auswaehlen eines Themas (Ergebnisse,
   * Athleten oder Medaillenspiegel) enthaelt
   *
   * @param panel Panel, das als Erstes in der gui angezeigt werden soll
   */
  private void createTopPanel(JPanel panel) {
    panel.setPreferredSize(new Dimension(1000, 80));
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 15));

    //button um die Ergebnisse anzuzeigen
    resultsButton = new JToggleButton("Ergebnisse");
    resultsButton.setPreferredSize(new Dimension(120, 50));
    resultsButton.setForeground(Color.BLUE);
    panel.add(resultsButton);

    //button um Informationen ueber die Athleten anzuzeigen
    athletesButton = new JToggleButton("Athleten");
    athletesButton.setPreferredSize(new Dimension(120, 50));
    panel.add(athletesButton);

    //button um den Medaillenspiegel anzuzeigen
    medalTallyButton = new JToggleButton("Medaillenspiegel");
    medalTallyButton.setPreferredSize(new Dimension(120, 50));
    panel.add(medalTallyButton);

    panel.setBackground(Color.BLUE);
  }

  /**
   * Fuegt den Buttons results, athletes und medalTally Action Listener hinzu und sorgt dafuer, dass
   * das korrekte Panel auf der GUI angezeigt wird
   *
   * @param resultsPanel  panel um die Ergebnisse anzuzeigen
   * @param athletesPanel panel um Informationen zu den Athleten anzuzeigen
   * @param medalPanel    panel um den Medaillenspiegel anzuzeigen
   */
  private void addListener(JPanel resultsPanel, JPanel athletesPanel, JPanel medalPanel) {
    //Action listener fuer den result button
    resultsButton.addActionListener(e -> {
      //result panel auswaehlen
      resultsPanel.setVisible(true);
      athletesPanel.setVisible(false);
      medalPanel.setVisible(false);

      //button auf ausgewaehlt setzen
      resultsButton.setSelected(true);
      athletesButton.setSelected(false);
      medalTallyButton.setSelected(false);

      //button farblich als ausgewaehlt markieren, andere farblich zurueck setzen
      resultsButton.setForeground(Color.BLUE);
      athletesButton.setForeground(Color.BLACK);
      medalTallyButton.setForeground(Color.BLACK);
    });

    //Action listener fuer den athletes button
    athletesButton.addActionListener(e -> {
      //athletes panel auswaehlen
      athletesPanel.setVisible(true);
      medalPanel.setVisible(false);
      resultsPanel.setVisible(false);

      //button auf ausgewaehlt setzen
      athletesButton.setSelected(true);
      medalTallyButton.setSelected(false);
      resultsButton.setSelected(false);

      //button farblich als ausgewaehlt markieren, andere farblich zurueck setzen
      resultsButton.setForeground(Color.BLACK);
      athletesButton.setForeground(Color.BLUE);
      medalTallyButton.setForeground(Color.BLACK);
    });

    //Action listener fuer den medalTally button
    medalTallyButton.addActionListener(e -> {
      //medal panel auswaehlen
      medalPanel.setVisible(true);
      resultsPanel.setVisible(false);
      athletesPanel.setVisible(false);

      //button auf ausgewaehlt setzen
      medalTallyButton.setSelected(true);
      resultsButton.setSelected(false);
      athletesButton.setSelected(false);

      //button farblich als ausgewaehlt markieren, andere farblich zurueck setzen
      resultsButton.setForeground(Color.BLACK);
      athletesButton.setForeground(Color.BLACK);
      medalTallyButton.setForeground(Color.BLUE);
    });
  }

  /**
   * Befuellt das entsprechende Panel mit den Ergebnissen der Athleten und allgemeinen Informationen
   * zu den Spielen
   *
   * @param panel zu befuellendes JPanel
   */
  private void fillResultPanel(JPanel panel) {
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setPreferredSize(new Dimension(1000, 420));
    panel.setBackground(Color.WHITE);

    //panel mit allgemeinen Informationen zu den ausgewaehlten Spielen
    JPanel generalInformationPanel = new JPanel();
    generalInformationPanel.setPreferredSize(new Dimension(300, 350));
    generalInformationPanel.setBackground(Color.WHITE);

    //Text area um die Informationen anzuzeigen
    JTextArea resultsTextArea = new JTextArea();
    resultsTextArea.setLineWrap(true);
    resultsTextArea.setWrapStyleWord(true);
    resultsTextArea.setPreferredSize(new Dimension(300, 350));
    generalInformationPanel.add(resultsTextArea);

    //panel um die Ergebnisse anzuzeigen
    JPanel resultsInformationPanel = new JPanel();
    resultsInformationPanel.setPreferredSize(new Dimension(700, 350));
    resultsInformationPanel.setBackground(Color.WHITE);

    //Feld um die Ergebnisse anzuzeigen
    TableModel model = new DefaultTableModel(new String[]{"position", "name", "country"}, 0);
    JTable resultsInformationTable = new JTable(model);
    JTableHeader header = resultsInformationTable.getTableHeader();
    header.setReorderingAllowed(false);
    JScrollPane scrollPane = new JScrollPane(resultsInformationTable);
    scrollPane.setPreferredSize(new Dimension(650, 340));
    resultsInformationPanel.add(scrollPane);

    //enthaelt das panel mit den allgemeinen Informationen sowie das panel mit den Ergebnissen
    JPanel completeResultsPanel = new JPanel();
    completeResultsPanel.setLayout(new BoxLayout(completeResultsPanel, BoxLayout.X_AXIS));
    completeResultsPanel.setPreferredSize(new Dimension(1000, 350));
    completeResultsPanel.add(generalInformationPanel);
    completeResultsPanel.add(resultsInformationPanel);

    //panel das die Combo-Boxen, mit welchen man die Edition, das Jahr die Sportart und die Disziplin
    //auswaehlen kann, enthaelt
    JPanel chooseInformation = new JPanel();
    chooseInformation.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 25));
    chooseInformation.setPreferredSize(new Dimension(1000, 70));
    chooseInformation.setBackground(Color.WHITE);

    //Combo Boxen (um die passenden Athleten zu filtern) mit:
    //allen Editionen (z.B. Sommer/Winterspiele)
    JComboBox<String> editionBox = new JComboBox<>();
    editionBox.setPreferredSize(new Dimension(160, 25));
    //allen Jahren in denen Spiele stattfanden
    JComboBox<String> yearBox = new JComboBox<>(new String[]{"Choose Year"});
    yearBox.setPreferredSize(new Dimension(160, 25));
    //allen Sportarten
    JComboBox<String> sportBox = new JComboBox<>(new String[]{"Choose Sport"});
    sportBox.setPreferredSize(new Dimension(280, 25));
    //allen Disziplinen der Sportarten
    JComboBox<String> eventBox = new JComboBox<>(new String[]{"Choose Event"});
    eventBox.setPreferredSize(new Dimension(280, 25));

    //Combo-Boxen dem panel chooseInformation hinzufuegen
    chooseInformation.add(editionBox);
    chooseInformation.add(yearBox);
    chooseInformation.add(sportBox);
    chooseInformation.add(eventBox);

    //edition Box initial befuellen
    List<String> editions = fdb.getOlympicEditions();
    if (editions.isEmpty()) {
      editionBox.addItem("Aufgabe 2a bearbeiten");
    } else {
      editionBox.addItem("Choose Edition");
    }
    for (String edition : editions) {
      editionBox.addItem(edition);
    }

    // eindeutige id fuer ein olympisches Spiel
    int[] editionId = {-1};

    //Action Listener fuer die Combo-Boxen

    editionBox.addActionListener(e -> {
      //alle anderen Combo-Boxen leeren und in ihren initialen Zustand setzen
      yearBox.removeAllItems();
      yearBox.addItem("Choose Year");
      sportBox.removeAllItems();
      sportBox.addItem("Choose Sport");
      eventBox.removeAllItems();
      eventBox.addItem("Choose Event");

      //Werte aus table entfernen falls noetig
      DefaultTableModel dtm = new DefaultTableModel(new String[]{"position", "name", "country"}, 0);
      resultsInformationTable.setModel(dtm);

      //Falls keine Edition ausgewaehlt wurde nichts tun
      if (Objects.requireNonNull(editionBox.getSelectedItem()).equals("Aufgabe 2a bearbeiten")) {
        return;
      }
      if (editionBox.getSelectedItem().equals("Choose Edition")) {
        resultsTextArea.setText("");
        return;
      }

      //Die Combo-Box years mit den Jahren, in denen Spiele stattfanden fuellen
      List<Integer> years = fdb.getOlympicYears(editionBox.getSelectedItem().toString());
      for (Integer year : years) {
        yearBox.addItem(String.valueOf(year));
      }

      //Text aus der Text area entfernen, falls noetig
      resultsTextArea.setText("");
    });

    yearBox.addActionListener(e -> {
      //Fall abfangen, dass der Inhalt der Combobox entfernt wurde
      if (yearBox.getSelectedItem() == null) {
        return;
      }

      //Werte aus table entfernen falls noetig
      DefaultTableModel dtm = new DefaultTableModel(new String[]{"position", "name", "country"}, 0);
      resultsInformationTable.setModel(dtm);

      //Inhalt von sport und event entfernen und in ihren initialen Zustand setzen
      sportBox.removeAllItems();
      sportBox.addItem("Choose Sport");
      eventBox.removeAllItems();
      eventBox.addItem("Choose Event");
      if (yearBox.getSelectedItem().equals("Choose Year")) {
        return;
      }

      //edition und year aus den jeweiligen Combo-Boxen waehlen um die editionId zu bekommen
      String edition = Objects.requireNonNull(editionBox.getSelectedItem()).toString();
      int year = Integer.parseInt(yearBox.getSelectedItem().toString());
      editionId[0] = fdb.getOlympicEditionId(edition, year);

      //Die Combo-Box sport mit den entsprechenden Werten fuellen
      List<String> sports = fdb.getOlympicSports(editionId[0]);
      for (String sport : sports) {
        sportBox.addItem(sport);
      }

      //allgemeine Informationen ueber die olympischen Spiele aus der Datenbank lesen
      String venue = fdb.getOlympicVenue(editionId[0]);
      int femaleCount = fdb.getMaleFemaleCount(false, editionId[0]);
      int maleCount = fdb.getMaleFemaleCount(true, editionId[0]);
      int sum = femaleCount + maleCount;
      String[] oldestAthlete = fdb.getOldestOrYoungestAthlete(editionId[0], true);
      String[] youngestAthlete = fdb.getOldestOrYoungestAthlete(editionId[0], false);
      DecimalFormat decimalFormat = new DecimalFormat("0.00");

      //Text (der allgemeinen Informationen) zusammensetzen
      String builder =
          "Venue:\n" + venue + "\n\n" + "Number of participants:\n" + "Female participants: "
              + femaleCount + " (" + decimalFormat.format((double) femaleCount / (double) sum * 100)
              + "%)\n" + "Male participants: " + maleCount + " (" + decimalFormat.format(
              (double) maleCount / (double) sum * 100) + "%)\n\n" + "Oldest athlete:\n"
              + oldestAthlete[0] + "  (born: " + oldestAthlete[1] + ")\n\n" + "Youngest athlete:\n"
              + youngestAthlete[0] + "  (born: " + youngestAthlete[1] + ")";

      //Text in die text area schreiben
      resultsTextArea.setText(builder);
    });

    sportBox.addActionListener(e -> {
      //Fall abfangen, dass der Inhalt der Combobox entfernt wurde
      if (sportBox.getSelectedItem() == null) {
        return;
      }
      //Werte aus table entfernen falls noetig
      DefaultTableModel dtm = new DefaultTableModel(new String[]{"position", "name", "country"}, 0);
      resultsInformationTable.setModel(dtm);

      //Inhalt von event entfernen und in initialen Zustand setzen
      eventBox.removeAllItems();
      eventBox.addItem("Choose Event");
      if (Objects.requireNonNull(sportBox.getSelectedItem()).equals("Choose Sport")) {
        return;
      }
      List<String> events = fdb.getOlympicEvents(editionId[0],
          Objects.requireNonNull(sportBox.getSelectedItem()).toString());
      for (String event : events) {
        eventBox.addItem(event);
      }
    });

    eventBox.addActionListener(e -> {
      //Fall abfangen, dass der Inhalt der Combobox entfernt wurde
      if (eventBox.getSelectedItem() == null) {
        return;
      }
      if (eventBox.getSelectedItem().equals("Choose Event")) {
        return;
      }

      //Ergebnisse in den table einfuegen
      DefaultTableModel dtm = new DefaultTableModel(new String[]{"position", "name", "country"}, 0);
      String sport = Objects.requireNonNull(sportBox.getSelectedItem()).toString();
      String event = Objects.requireNonNull(eventBox.getSelectedItem()).toString();
      List<Result> olympicResults = fdb.getOlympicResults(editionId[0], sport, event);

      //Versuche die Liste zu sortieren
      try {
        olympicResults = olympicResults.stream().sorted().toList();
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
      Object[] row = new Object[3];
      for (Result result : olympicResults) {
        row[0] = result.getPosition();
        row[1] = result.getName();
        row[2] = result.getCountry();
        dtm.addRow(row);
      }
      resultsInformationTable.setModel(dtm);
    });

    panel.add(chooseInformation);
    panel.add(completeResultsPanel);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
  }

  /**
   * Befuellt das entsprechende Panel mit Auswahlmoeglichkeiten/Informationen zu den Athleten
   *
   * @param panel zu befuellendes JPanel
   */
  private void fillAthletesPanel(JPanel panel) {
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setPreferredSize(new Dimension(1000, 420));
    panel.setBackground(Color.WHITE);

    //panel das die Combo boxen, mit welchen man das Land, die Sportart, das Geschlecht und die
    //Bonusaufgabe auswaehlen kann, enthaelt
    JPanel chooseInformation = new JPanel();
    chooseInformation.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
    chooseInformation.setPreferredSize(new Dimension(1000, 70));
    chooseInformation.setBackground(Color.WHITE);

    //Combo Boxen (um die passenden Athleten zu filtern) mit:
    //allen Laendern
    JComboBox<String> countryBox = new JComboBox<>();
    countryBox.setPreferredSize(new Dimension(300, 25));
    //allen Sportarten
    JComboBox<String> sportBox = new JComboBox<>(new String[]{"Choose Sport"});
    sportBox.setPreferredSize(new Dimension(300, 25));
    //Geschlecht (maennlich/weiblich)
    JComboBox<String> genderBox = new JComboBox<>(new String[]{"Choose Gender", "Male", "Female"});

    //Laender in die Combo Box einfuegen
    List<String> countries = fdb.getCountries();
    if (countries.isEmpty()) {
      countries.add("Aufgabe 2b bearbeiten");
    } else {
      countries.add(0, "Choose Country");
    }
    for (String countryName : countries) {
      countryBox.addItem(countryName);
    }

    //Combo Boxen dem Information panel hinzufuegen
    chooseInformation.add(countryBox);
    chooseInformation.add(sportBox);
    chooseInformation.add(genderBox);

    //Feld um Informationen ueber die Athleten anzeigen
    TableModel model = new DefaultTableModel(
        new String[]{"games", "name", "born", "height", "weight", "medal"}, 0);
    JTable athleteInformationTable = new JTable(model);
    JTableHeader header = athleteInformationTable.getTableHeader();
    header.setReorderingAllowed(false);
    JScrollPane scrollPane = new JScrollPane(athleteInformationTable);

    //Fuellt die Sportbox mit entsprechenden Eintraegen
    countryBox.addActionListener(e -> {
      //Informationen ueber die Athleten entfernen, falls noetig
      DefaultTableModel dtm = (DefaultTableModel) athleteInformationTable.getModel();
      dtm.setRowCount(0);

      //Sportarten entfernen
      sportBox.removeAllItems();
      List<String> sports = fdb.getOlympicSports2(
          Objects.requireNonNull(countryBox.getSelectedItem()).toString());

      //Falls Sportarten fuer das Land verfuegbear, diese hinzufuegen
      if (sports.isEmpty()) {
        sportBox.addItem("No results");
      } else {
        sportBox.addItem("Choose Sport");
      }
      for (String sport : sports) {
        sportBox.addItem(sport);
      }
    });

    //Fuellt die Tabelle mit den Informationen zu den Athleten, falls moeglich
    sportBox.addActionListener(e -> {
      //Abfangen des Falls, dass durch das Auswaehlen eines neuen Landes die Elemente entfernt wurden
      //(durch Action Listener der countryBox)
      if (sportBox.getSelectedItem() == null) {
        return;
      }
      updateAthleteInformation(countryBox, sportBox, genderBox, athleteInformationTable);
    });
    genderBox.addActionListener(
        e -> updateAthleteInformation(countryBox, sportBox, genderBox, athleteInformationTable));

    //alles zum athlete panel hinzufuegen
    panel.add(chooseInformation);
    panel.add(scrollPane);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
  }


  /**
   * Liest die aktuell ausgewaehlten Eintraege aus den Combo-Boxen und aktualisiert die GUI
   * entsprechend
   *
   * @param countryBox              Combo-Box, um das Land auszuwaehlen
   * @param sportBox                Combo-Box, um die Sportart auszuwaehlen
   * @param genderBox               Combo-Box, um das Geschlecht auszuwaehlen
   * @param athleteInformationTable JTable mit den Informationen ueber die Athleten
   */
  private void updateAthleteInformation(JComboBox<String> countryBox, JComboBox<String> sportBox,
      JComboBox<String> genderBox, JTable athleteInformationTable) {

    //Ausgewaehlte Werte aus den Combo-Boxen auslesen
    String country = Objects.requireNonNull(countryBox.getSelectedItem()).toString();
    String sport = Objects.requireNonNull(sportBox.getSelectedItem()).toString();
    String gender = Objects.requireNonNull(genderBox.getSelectedItem()).toString();

    //In den folgenden Faellen nichts aktualisieren
    if (country.equals("Choose Country")) {
      return;
    } else if (sport.equals("Choose Sport") || sport.equals("No results")) {
      return;
    } else if (gender.equals("Choose Gender")) {
      //Informationen ueber die Athleten entfernen
      DefaultTableModel dtm = (DefaultTableModel) athleteInformationTable.getModel();
      dtm.setRowCount(0);
      return;
    }

    //Feld mit den Informationen ueber die Athleten fuellen
    fillAthleteInformation(country, sport, gender, athleteInformationTable);
  }


  /**
   * Fuellt den JTable mit den Informationen ueber die Athleten
   *
   * @param country Land der Athleten
   * @param sport   Sportart der Athleten
   * @param gender  Geschlecht der Athleten
   * @param table   JTable in den die Informationen geschrieben werden
   */
  private void fillAthleteInformation(String country, String sport, String gender, JTable table) {
    DefaultTableModel model;
    //Informationen in JTable einfuegen
    model = new DefaultTableModel(
        new String[]{"games", "name", "born", "height", "weight", "medal"}, 0);
    List<AthleteEventResult> athletes = fdb.getOlympicAthletes(country, sport, gender);
    Object[] row = new Object[8];
    for (AthleteEventResult athlete : athletes) {
      row[0] = athlete.getGames();
      row[1] = athlete.getName();
      row[2] = athlete.getBorn();
      row[3] = athlete.getHeight();
      row[4] = athlete.getWeight();
      row[5] = athlete.getMedal();
      model.addRow(row);
    }
    table.setModel(model);
  }


  /**
   * Befuellt das entsprechende Panel mit Informationen zum ewigen Medaillenspiegel
   *
   * @param panel zu befuellendes JPanel
   */
  private void fillMedalPanel(JPanel panel) {
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setPreferredSize(new Dimension(1000, 420));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    //optischer Platzhalter
    JPanel space = new JPanel();
    space.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
    space.setPreferredSize(new Dimension(1000, 70));
    space.setBackground(Color.WHITE);

    //Ueberschrift
    JTextArea headline = new JTextArea();
    headline.setEditable(false);
    headline.setBackground(Color.WHITE);
    String text = "Ewiger Medaillenspiegel der olympischen Spiele von 1896 bis 2022";
    headline.setText(text);
    space.add(headline);

    //Feld um den ewigen Medaillenspiegel anzuzeigen
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"country", "gold", "silver", "bronze"}, 0);
    JTable medalTable = new JTable(model);
    JTableHeader header = medalTable.getTableHeader();
    header.setReorderingAllowed(false);
    JScrollPane scrollPane = new JScrollPane(medalTable);

    //Werte aus der Datenbank auslesen und einfuegen
    List<String[]> medalTally = fdb.getOlympicMedalTally();
    Object[] row = new Object[4];
    for (String[] array : medalTally) {
      row[0] = array[0];
      row[1] = array[1];
      row[2] = array[2];
      row[3] = array[3];
      model.addRow(row);
    }

    panel.add(space);
    panel.add(scrollPane);
  }
}
