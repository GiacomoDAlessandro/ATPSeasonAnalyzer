import java.util.EnumMap;
import java.util.Map;


public class playerStats implements Comparable<playerStats> {

  private final String playerName;

  private int numWins;

  private int numLosses;

  private int numRankingPts;

  private int numAces;

  private double winPercentage;

  private final String playerOrigin;

  private int playerAge;

  private Map<TourneyLevel, Map<Round, Integer>> roundsReached = new EnumMap<>(TourneyLevel.class);

  /**
   * Use only for testing reasons
   */
  public playerStats(String origin, String name, int Wins, int Losses, int rankingPts, int Aces,
      int Age) {
    this.playerOrigin = origin;
    this.playerName = name;
    this.numWins = Wins;
    this.numLosses = Losses;
    this.numRankingPts = rankingPts;
    this.numAces = Aces;
    this.playerAge = Age;
    this.winPercentage = 100.0;
  }

  public playerStats(Match match, boolean won) {
    if (won) {
      this.numRankingPts = match.getWinnerRankingPts();
      addAces(match.getWinnerAces());
      numWins++;
      this.playerName = match.getWinnerName();
      this.playerOrigin = match.getWinnerOrigin();
      this.playerAge = match.getWinnerAge();
    } else {
      numLosses++;
      this.numRankingPts = match.getLoserRankingPts();
      addAces(match.getLoserAces());
      this.playerName = match.getLoserName();
      this.playerOrigin = match.getLoserOrigin();
      this.playerAge = match.getLoserAge();
    }
    addRoundPlayed(match.getRound(), match.getTourneyLevel());
  }

  public void addWin() {
    this.numWins++;
    updateWinPercentage();
  }

  public void addLosses() {
    this.numLosses++;
    updateWinPercentage();;
  }

  public void updateWinPercentage() {
    int totalGms = this.numWins + this.numLosses;
    if (totalGms == 0) {
      this.winPercentage = 0.0;
    } else {
      this.winPercentage = ((double) this.numWins / totalGms) * 100.0;
      this.winPercentage = Math.round(this.winPercentage * 100.0) / 100.0;
    }
  }

  public void mostPoints(int rankingPts) {
    if (this.numRankingPts >= rankingPts) {
    } else {
      this.numRankingPts = rankingPts;
    }
  }

  public void addAces(int numAces) {
    this.numAces += numAces;
  }

  public void addRoundPlayed(Round round, TourneyLevel tourneyLevel) {
    Map<Round, Integer> innerMap = roundsReached.get(tourneyLevel);

    if (innerMap == null) {
      innerMap = new EnumMap<>(Round.class);
      innerMap.put(round, 1);
      roundsReached.put(tourneyLevel, innerMap);
    } else {
      int oldCount = innerMap.getOrDefault(round, 0);
      innerMap.put(round, oldCount + 1);
    }



  }

  @Override
  public int compareTo(playerStats o) {
    return 0;
  }

  public boolean equals(playerStats toCompare) {
    return toCompare.numAces == this.numAces && toCompare.numRankingPts == this.numRankingPts
        && toCompare.numWins == this.numWins && toCompare.numLosses == this.numLosses
        && toCompare.roundsReached.equals(this.roundsReached);
  }
}
