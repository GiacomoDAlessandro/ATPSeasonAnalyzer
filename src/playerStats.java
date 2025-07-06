import java.util.EnumMap;
import java.util.Map;


/**
 * ATP player statistics holder
 */
public class playerStats {

  /**
   * Name of player
   */
  private final String playerName;

  /**
   * Number of wins of player
   */
  private int numWins;

  /**
   * Number of losses of player
   */
  private int numLosses;

  /**
   * Highest number of ATP ranking points player achieved that calendar year
   */
  private int numRankingPts;

  /**
   * Number of aces of player
   */
  private int numAces;

  /**
   * Win percentage of player
   */
  private double winPercentage;

  /**
   * Origin of player in abbreviated form (ITA, USA, AUS, etc.)
   */
  private final String playerOrigin;

  /**
   * Age of player
   */
  private int playerAge;

  /**
   * Nested Map with tourneyLevel as a key and Map<Round, Integer> as the value which shows how
   * many times each round was reached in each different level of tournament
   */
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

  /**
   * Constructor for playerStats that initializes all the player's statistics
   *
   * @param match match to take statistics from
   * @param won whether the player won or not, true if won, false if not
   */
  public playerStats(Match match, boolean won) {

    //Adds a win to the player's stats if the player won
    if (won) {
      this.numRankingPts = match.getWinnerRankingPts();
      addAces(match.getWinnerAces());
      numWins++;
      this.playerName = match.getWinnerName();
      this.playerOrigin = match.getWinnerOrigin();
      this.playerAge = match.getWinnerAge();

      //If player lost adds a loss to the player's stats
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

  /**
   * Adds a win to the player's statistics
   */
  public void addWin() {
    this.numWins++;
    updateWinPercentage();
  }

  /**
   * Adds a loss to the player's statistics
   */
  public void addLosses() {
    this.numLosses++;
    updateWinPercentage();;
  }

  /**
   * Update's the player's win percentage when a win or a loss is added
   */
  public void updateWinPercentage() {
    int totalGms = this.numWins + this.numLosses;
    if (totalGms == 0) {
      this.winPercentage = 0.0;
    } else {
      this.winPercentage = ((double) this.numWins / totalGms) * 100.0;
      this.winPercentage = Math.round(this.winPercentage * 100.0) / 100.0;
    }
  }

  /**
   * Updates the highest ranking points achieved in a single calendar year if the ranking points
   * of a player at a certain point is higher than the last
   *
   * @param rankingPts amount of ranking points the player has at a certain point
   */
  public void mostPoints(int rankingPts) {
    if (this.numRankingPts >= rankingPts) {
    } else {
      this.numRankingPts = rankingPts;
    }
  }

  /**
   * Adds aces to the player statistics
   *
   * @param numAces number of aces a player hit in a match
   */
  public void addAces(int numAces) {
    this.numAces += numAces;
  }

  /**
   * Adds the round a player played
   *
   * @param round round of tournament the player played (R32, SF, F, etc.)
   * @param tourneyLevel level of tournament the player played (ATP 500, ATP 1000, Grand Slam, etc.)
   */
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

  /**
   * Checks if two players are equal if their stats are equal
   *
   * @param toCompare player statistics that are being compared
   * @return true if both player's statistics are equal
   */
  public boolean equals(playerStats toCompare) {
    return toCompare.numAces == this.numAces && toCompare.numRankingPts == this.numRankingPts
        && toCompare.numWins == this.numWins && toCompare.numLosses == this.numLosses
        && toCompare.roundsReached.equals(this.roundsReached);
  }

  /**
   * Accessor method for the number of wins a player has
   *
   * @return number of wins a player has
   */
  public int getWins() {
    return this.numWins;
  }

  /**
   * Accessor method for the number of losses a player has
   *
   * @return number of losses a player has
   */
  public int getLosses() {return this.numLosses;}

  /**
   * Accessor method for the age of a player
   *
   * @return Age of a player
   */
  public int getAge() {return this.playerAge;}

  /**
   * Accessor method for the win percentage of a player
   *
   * @return win percentage of a player
   */
  public double getWinPercentage() {return this.winPercentage;}

  /**
   * Accessor method for the number of aces a player has
   *
   * @return number of aces a player has
   */
  public int getAces() {return this.numAces;}

  /**
   * Accessor method for the highest number of ATP ranking points a player reached in one year
   *
   * @return highest number of ATP ranking points a player reached in one year
   */
  public int getHighestRankingPts() {return this.numRankingPts;}

  /**
   * Accessor method for the name of the player
   *
   * @return name of player
   */
  public String getPlayerName() {return this.playerName;}

  /**
   * Accessor method for the origin of the player
   */
  public String getPlayerOrigin() {return "(" + this.playerOrigin + ")";}

  /**
   * Accessor method for number of finals the player reached
   *
   * @return number of finals a player reached
   */
  public int getTotalFinalsReached() {
    int totalFinals = 0;

    for (Map<Round, Integer> roundMap : roundsReached. values()) {
      if (roundMap.containsKey(Round.FINAL)) {
        totalFinals += roundMap.get(Round.FINAL);
      }
    }
    return totalFinals;
  }


}
