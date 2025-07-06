/**
 * Enum that holds the number of matches a player played in each level of a tournament (Grand
 * Slam, ATP 250, etc.)
 */
public enum TourneyLevel {
  ATP_FINALS("F"),
  GRAND_SLAM("G"),
  MASTERS_1000("M"),
  ATP_500("A"),
  ATP_250("B"),
  DAVIS_CUP("D"),
  OLYMPICS("O");

  /**
   * String of what type of tourneyLevel the match was played in
   */
  private final String matchLevel;

  /**
   * Initializes the matchLevel of the match
   *
   * @param code string describing what tourneyLevel the match was played in
   */
  TourneyLevel(String code) {
    this.matchLevel = code;
  }

  /**
   * Matches the string from the matchLevel to what tourneyLevel it matches up to
   *
   * @param level string representing level of tournamnet the match was played in
   * @return tourneyLevel representing the type of tournament the match was played in
   * (ATP_FINALS, GRAND_SLAM, etc.)
   */
  public static TourneyLevel fromCode(String level) {
    for (TourneyLevel newLevel: values()) {
      if (newLevel.matchLevel.equals(level)) {
        return newLevel;
      }
    }
    throw new IllegalArgumentException("Unknown Tournament Level: " + level);
  }

  /**
   * Accessor method for getting the matchLevel
   *
   * @return String of tourneyLevel
   */
  public String getMatchLevel() {
    return matchLevel;
  }
}
