import java.util.logging.Level;

public enum TourneyLevel {
  ATP_FINALS("F"),
  GRAND_SLAM("G"),
  MASTERS_1000("M"),
  ATP_500("A"),
  ATP_250("B"),
  DAVIS_CUP("D"),
  OLYMPICS("O");

  private final String code;

  TourneyLevel(String code) {
    this.code = code;
  }

  public static TourneyLevel fromCode(String level) {
    for (TourneyLevel newLevel: values()) {
      if (newLevel.code.equals(level)) {
        return newLevel;
      }
    }
    throw new IllegalArgumentException("Unknown Tournament Level: " + level);
  }

  public String getCode() {
    return code;
  }
}
