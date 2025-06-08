
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

  Round(String code) {
    this.code = code;
  }

  private final String code;

  public static Round fromCode(String round) {
    for (Round newround: values()) {
      if (newround.code.equals(round)) {
        return newround;
      }
    }
    throw new IllegalArgumentException("Round: " + round + " not found");
  }

  public String getCode() {
    return code;
  }
}
