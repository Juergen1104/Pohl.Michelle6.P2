package data;

public class Result implements Comparable<Result> {

  //Position des Athleten
  private final String position;
  //Name des Athleten
  private final String name;
  //Herkunftsland des Athleten
  private final String country;

  public Result(String position, String name, String country) {
    this.position = position;
    this.name = name;
    this.country = country;
  }

  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  public String getPosition() {
    return position;
  }

  /**
   * Unterteilt die Position, die der Athlet erreicht hat in fuenf Kategorien auf:
   * 1) regulaeres Ergebnis: dem Athleten wurde eine bestimmte Position zugeordnet
   * 2) kein genaues Ergebnis bekannt (AC: also competed, no definite place known or possibly
   * given)
   * 3) Wettkampf nicht beendet (DNF: did not finish)
   * 4) nicht angetreten (DNS: did not start)
   * 5) alle anderen Faelle
   *
   * @param position position des Athleten
   * @return Kategorie in welches das Ergebnis faellt
   */
  private int determineCategory(String position) {
    if (position.matches("[0-9]+.*")) {
      return 1;
    }
    if (position.matches("AC.*")) {
      return 2;
    }
    if (position.matches("DNF.*")) {
      return 3;
    }
    if (position.matches("DNS.*")) {
      return 4;
    }
    return 5;
  }

  /**
   * Vergleicht zwei regulaere Positionen (Athlet hat den Wettkampf beendet). Es werden nur
   * Positionen der gleichen Kategorie uebergeben.
   *
   * @param position1 position dieser Instanz
   * @param position2 position der uebergebenen Instanz
   * @return -1, wenn die Position dieser Instanz kleiner als die der Uebergebenen ist 0, wenn die
   * Position dieser Instanz gleich wie die der Uebergebenen ist 1, wenn die Position dieser Instanz
   * groeßer als die der Uebergebenen ist
   */
  private int determineRegularPlace(String position1, String position2) {
    String[] split1 = position1.split(" ");
    String[] split2 = position2.split(" ");
    if (split1[0].matches("DNS|DNF|AC")) {
      return determineIrregularPlace(position1, position2);
    }

    //die Laenge des splits ist kuerzer, wenn der Athlet nicht bereits in einer Vorrunde ausgeschieden ist
    if (split1.length == 1 || split2.length == 1) {
      if (split1.length < split2.length) {
        return -1;
      }
      if (split1.length > split2.length) {
        return 1;
      }
    }

    //Laenge des splits ist in den folgenden Faellen nun gleich

    //position ist eine Zahl
    if (split1.length == 1) {
      int i1 = Integer.parseInt(split1[0]);
      int i2 = Integer.parseInt(split2[0]);
      return Integer.compare(i1, i2);
    }

    //position hat folgende Form:
    //9 r1/2 = 9th in round 1 of 2 rounds
    //Je groeßer die Rundenzahl, desto besser ist der Athlet. Bei Gleichstand entscheidet der Platz
    if (split1.length == 2) {
      int round1 = Integer.parseInt(split1[1].split("/")[0].substring(1));
      int round2 = Integer.parseInt(split2[1].split("/")[0].substring(1));
      if (round1 < round2) {
        return 1;
      }
      if (round1 > round2) {
        return -1;
      }

      //Platz in der Runde vergleichen
      return Integer.compare(Integer.parseInt(split1[0]), Integer.parseInt(split2[0]));
    }

    //position hat folgende Form:
    //4 h3 r2/4 = 4th in heat 3, round 2 of 4 rounds (and similar abbreviations) (p3 = pool 3)
    //Je groeßer die Rundenzahl, desto besser ist der Athlet. Bei Gleichstand entscheidet erst
    //der Platz und dann heat/pool
    int round1 = Integer.parseInt(split1[2].split("/")[0].substring(1));
    int round2 = Integer.parseInt(split2[2].split("/")[0].substring(1));
    if (round1 < round2) {
      return 1;
    }
    if (round1 > round2) {
      return -1;
    }
    //Platz vergleichen
    int place1 = Integer.parseInt(split1[0]);
    int place2 = Integer.parseInt(split2[0]);
    if (place1 < place2) {
      return -1;
    }
    if (place1 > place2) {
      return 1;
    }
    int heat1 = Integer.parseInt(split1[1].substring(1));
    int heat2 = Integer.parseInt(split2[1].substring(1));
    return Integer.compare(heat1, heat2);
  }

  /**
   * Vergleicht zwei irregulaere Positionen (Athlet hat den Wettkampf nicht beendet/angetreten oder
   * das Ergebnis ist nicht eindeutig bestimmt). Es werden nur Positionen der gleichen Kategorie
   * uebergeben
   *
   * @param position1 position dieser Instanz
   * @param position2 position der uebergebenen Instanz
   * @return -1, wenn die Position dieser Instanz kleiner als die der Uebergebenen ist 0, wenn die
   * Position dieser Instanz gleich wie die der Uebergebenen ist 1, wenn die Position dieser Instanz
   * groeßer als die der Uebergebenen ist
   */
  private int determineIrregularPlace(String position1, String position2) {
    String[] split1 = position1.split(" ");
    String[] split2 = position2.split(" ");

    //erstes Element aus dem Array entfernen
    split1 = removeFirstElement(split1);
    split2 = removeFirstElement(split2);
    //beide Elemente haben keinen zusätzlichen Eintrag in welcher Runde der Athlet ausgeschieden ist
    if (split1.length == 0 && split2.length == 0) {
      return 0;
    }
    //Nur das erste/zweite Element hat keinen zusaetzlichen Eintrag, in welcher Runde
    //der Athlet ausgeschieden ist
    if (split1.length == 0) {
      return 1;
    }
    if (split2.length == 0) {
      return -1;
    }

    //Athlet hat das Finale nicht angetreten/beendet
    if (split1[0].equals("final")) {
      return -1;
    }
    if (split2[0].equals("final")) {
      return 1;
    }

    //position hat folgende Form:
    //r1/2 = round 1 of 2 rounds
    //Je groeßer die Rundenzahl, desto besser ist der Athlet.
    if (split1.length == 1) {
      int round1 = Integer.parseInt(split1[0].split("/")[0].substring(1));
      int round2 = Integer.parseInt(split2[0].split("/")[0].substring(1));
      return Integer.compare(round2, round1);
    }

    //position hat folgende Form:
    //h3 r2/4 =heat 3, round 2 of 4 rounds (and similar abbreviations) (p3 = pool 3)
    //Je groeßer die Rundenzahl, desto besser ist der Athlet. Bei Gleichstand entscheidet heat/pool
    int round1 = Integer.parseInt(split1[1].split("/")[0].substring(1));
    int round2 = Integer.parseInt(split2[1].split("/")[0].substring(1));
    if (round1 < round2) {
      return 1;
    }
    if (round1 > round2) {
      return -1;
    }
    //heat/pool vergleichen
    int heat1 = Integer.parseInt(split1[0].substring(1));
    int heat2 = Integer.parseInt(split2[0].substring(1));
    return Integer.compare(heat1, heat2);
  }

  /**
   * Entfernt das erste Element aus einem String Array
   *
   * @param str Array aus dem das erste Element entfernt werden soll
   * @return Array aus dem das erste Element entfernt wurde
   */
  private String[] removeFirstElement(String[] str) {
    String[] help = new String[str.length - 1];
    System.arraycopy(str, 1, help, 0, str.length - 1);
    return help;
  }


  @Override
  public int compareTo(Result result) {
    //Je kleiner die Kategorie, desto besser
    if (determineCategory(position) < determineCategory(result.getPosition())) {
      return -1;
    }
    if (determineCategory(position) > determineCategory(result.getPosition())) {
      return 1;
    }

    //nach den Namen sortieren, falls Position in Kategorie 5 faellt
    if (determineCategory(position) == 5) {
      return this.name.compareTo(result.getName());
    }

    int res = determineRegularPlace(this.position, result.getPosition());
    //Bei Gleichstand nach dem Namen sortieren
    return res != 0 ? res : this.name.compareTo(result.getName());
  }
}