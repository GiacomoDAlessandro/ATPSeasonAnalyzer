import java.util.HashMap;

/**
 * Registers players statistics into a Hasmap of type <String, playerStats>, registers players
 * from each match that is registered, registers both winners and losers
 */
public class PlayerRegistry {

  /**
   * Hashmap which contains individual player's stats that are accessed by a key of the player's
   * name in a String
   */
  private HashMap<String, playerStats> stats = new HashMap<>();

  /**
   * Registers individual player stats into a playerStats object for each winner and loser of a
   * specific match
   *
   * @param match match to register players from
   * @return HashMap with the player's name as the key and the player's stats as the content
   */
  public HashMap<String, playerStats> recordMatch(Match match) {
    boolean hasWinner = stats.containsKey(match.getWinnerName());
    boolean hasLoser = stats.containsKey(match.getLoserName());

    // Adds stats to a playerStats object for winner of the match if winner already has a
    // playerStats object created (already has a match recorded this season)
    if (hasWinner) {
      playerStats winnerStats = stats.get(match.getWinnerName());
      winnerStats.addAces(match.getWinnerAces());
      winnerStats.addWin();
      winnerStats.mostPoints(match.getWinnerRankingPts());
      winnerStats.addRoundPlayed(match.getRound(), match.getTourneyLevel());
      //New playerStats object created for winner
    } else {
      stats.put(match.getWinnerName(), new playerStats(match, true));
    }

    // Adds stats to a playerStats object for the loser of the match if loser already has a
    // playerStats object created (already has a match recorded this season)
    if (hasLoser) {
      playerStats loserStats = stats.get(match.getLoserName());
      loserStats.addAces(match.getLoserAces());
      loserStats.addLosses();
      loserStats.mostPoints(match.getLoserRankingPts());
      loserStats.addRoundPlayed(match.getRound(), match.getTourneyLevel());
      //New playerStats object created for loser
    } else {
      stats.put(match.getLoserName(), new playerStats(match, false));
    }

    return stats;
  }




}
