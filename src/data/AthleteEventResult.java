package data;

public class AthleteEventResult {

  //name des Athleten
  private final String name;
  //Geburtsdatum des Athleten
  private final String born;
  //Medaille ausgeschrieben, falls der Athlet eine erhalten hat
  private final String medal;
  //Groeße des Athleten
  private final float height;
  //Gewicht des Athleten
  private final int weight;
  //Spiele an denen der Athlet teilgenommen hat
  private final String games;


  public AthleteEventResult(String medal, String name, String born, float height, int weight, String games) {
    this.medal = medal;
    this.name = name;
    this.born = born;
    this.height = height;
    this.weight = weight;
    this.games = games;
  }

  public String getMedal() {
    return medal;
  }

  public int getWeight() {
    return weight;
  }

  public float getHeight() {
    return height;
  }

  public String getBorn() {
    return born;
  }

  public String getName() {
    return name;
  }

  public String getGames() {
    return games;
  }
}
