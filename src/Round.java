/**
 * Represents the different stages of a tournament, different rounds in a tournament
 * Each round uses a shorthand to describe it, ex. ("F" - Final, "SF" - SemiFinal, etc.)
 */
public enum Round {

  FINAL("F"),
  SEMIFINAL("SF"),
  QUARTERFINALS("QF"),
  ROUND_OF_16("R16"),
  ROUND_OF_32("R32"),
  ROUND_OF_64("R64"),
  ROUND_OF_128("R128"),
  ROUND_ROBIN("RR"),
  BRONZE("BR");

  /**
   * Gets the string given to decipher what round the match was played in
   *
   * @param code string describing what round the match was played in
   */
  Round(String code) {
    this.roundPlayed = code;
  }

  /**
   * String describing what round was played
   */
  private final String roundPlayed;

  /**
   * From the code given to describe the round played, the correct round is assigned
   *
   * @param round String describing round played
   * @return round played
   */
  public static Round fromCode(String round) {
    for (Round newround: values()) {
      if (newround.roundPlayed.equals(round)) {
        return newround;
      }
    }
    throw new IllegalArgumentException("Round: " + round + " not found");
  }
}
