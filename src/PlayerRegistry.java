import java.util.HashMap;

public class PlayerRegistry {

  private HashMap<String, playerStats> stats = new HashMap<>();


  public HashMap<String, playerStats> recordMatch(Match match) {
    boolean hasWinner = stats.containsKey(match.getWinnerName());
    boolean hasLoser = stats.containsKey(match.getLoserName());

    if (hasWinner) {
      playerStats winnerStats = stats.get(match.getWinnerName());
      winnerStats.addAces(match.getWinnerAces());
      winnerStats.addWin();
      winnerStats.mostPoints(match.getWinnerRankingPts());
      winnerStats.addRoundPlayed(match.getRound(), match.getTourneyLevel());
    } else {
      stats.put(match.getWinnerName(), new playerStats(match, true));
    }

    if (hasLoser) {
      playerStats loserStats = stats.get(match.getLoserName());
      loserStats.addAces(match.getLoserAces());
      loserStats.addLosses();
      loserStats.mostPoints(match.getLoserRankingPts());
      loserStats.addRoundPlayed(match.getRound(), match.getTourneyLevel());
    } else {
      stats.put(match.getLoserName(), new playerStats(match, false));
    }

    return stats;
  }




}
