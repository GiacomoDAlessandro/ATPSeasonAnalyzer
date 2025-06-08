public class Match {

  /**
   * index 0, unique id for a tournament that the match was in
   */
  private final String tourney_id;

  /**
   * index 1, name of tournament
   */
  private final String tourneyName;

  /**
   * index 2, Surface that the match was being played on, (clay, grass, hard court)
   */
  private final String surfaceType;

  /**
   * index 4, what level tournament was
   * G - Grand Slam, M - ATP Masters 1000, A - ATP 500, B - ATP 250
   */
  private final TourneyLevel tourney_Level;

  /**
   * index 5, date tournament started
   */
  private final String dateStart;

  /**
   * index 7, unique ID for the winner of the match
   */
  private final String winnerID;

  /**
   * index 10, Name of winner
   */
  private final String winnerName;

  /**
   * index 13, country winner is from in form (ESP, USA, ITA)
   */
  private final String winnerOrigin;

  /**
   * index 14, age at time of match of winner
   */
  private final int winnerAge;

  /**
   * index 15, unique id for loser
   */
  private final String loserID;

  /**
   * index 18, name of loser
   */
  private final String loserName;

  /**
   * index 21, country loser is from in form (ESP, USA, ITA)
   */
  private final String loserOrigin;

  /**
   * index 22, age at time of match of loser
   */
  private final int loserAge;

  /**
   * index 23, score of match
   */
  private final String matchScore;

  /**
   * index 25, round in tournament match was
   */
  private final Round round;

  /**
   * index 27, number of aces winner hit
   */
  private final int winnerAce;

  /**
   * index 28, number of double faults occurred by winner
   */
  private final int winnerDoubleFaults;

  /**
   * index 33, number of service games played by winner
   */
  private final int winnerServiceGms;

  /**
   * index 36, number of aces served by loser
   */
  private final int loserAce;

  /**
   * index 37, number of double faults served by loser
   */
  private final int loserDoubleFaults;

  /**
   * index 42, number of service games played by loser
   */
  private final int loserServiceGames;

  /**
   * index 46, number of ATP points winner had at time of the match
   */
  private final int winnerRankingPts;

  /**
   * index 48, number of ATP points loser had at time of the match
   */
  private final int loserRankingPts;



  public Match(String[] matchData) {

    //Adding all info from the matchData that was read in, in MatchLoader, into specified
    // variables, all hardcoded as the data will always be in the same spot according to the
    // files that it is being read from
    this.tourney_id = matchData[0];
    this.tourneyName = matchData[1];
    this.surfaceType = matchData[2];
    this.tourney_Level = TourneyLevel.fromCode(matchData[4]);
    this.dateStart = matchData[5];
    this.winnerID = matchData[7];
    this.winnerName = matchData[10];
    this.winnerOrigin = matchData[13];

    //Checks that the age is data that is provided
    if (!matchData[14].isEmpty()) {
      this.winnerAge = (int) Math.floor(Double.parseDouble(matchData[14]));
    } else {
      this.winnerAge = 0;
    }
    this.loserID = matchData[15];
    this.loserName = matchData[18];
    this.loserOrigin = matchData[21];

    //Checks that the age is data that is provided
    if (!matchData[22].isEmpty()) {
      this.loserAge = (int) Math.floor(Double.parseDouble(matchData[22]));
    } else {
      this.loserAge = 0;
    }
    this.matchScore = matchData[23];
    this.round = Round.fromCode(matchData[25]);

    if (!matchData[27].isEmpty()) {
      this.winnerAce = Integer.parseInt(matchData[27]);
    } else {
      this.winnerAce = 0;
    }

    if (!matchData[28].isEmpty()) {
      this.winnerDoubleFaults = Integer.parseInt(matchData[28]);
    } else {
      this.winnerDoubleFaults = 0;
    }

    if (!matchData[33].isEmpty()) {
      this.winnerServiceGms = Integer.parseInt(matchData[33]);
    } else {
      this.winnerServiceGms = 0;
    }

    if (!matchData[36].isEmpty()) {
      this.loserAce =  Integer.parseInt(matchData[36]);
    } else {
      this.loserAce = 0;
    }

    if (!matchData[37].isEmpty()) {
      this.loserDoubleFaults = Integer.parseInt(matchData[37]);
    } else {
      this.loserDoubleFaults = 0;
    }

    if (!matchData[42].isEmpty()) {
      this.loserServiceGames = Integer.parseInt(matchData[42]);
    } else {
      this.loserServiceGames = 0;
    }

    if (!matchData[46].isEmpty()) {
      this.winnerRankingPts = Integer.parseInt(matchData[46]);
    } else {
      this.winnerRankingPts = 0;
    }

    if (!matchData[47].isEmpty()) {
      this.loserRankingPts = Integer.parseInt(matchData[47]);
    } else {
      this.loserRankingPts = 0;
    }

  }

  /**
   * Accessor method for tournament ID
   *
   * @return unique ID for tournament match is in
   */
  public String getTourneyID() {return this.tourney_id;}

  /**
   * Accessor method for tournament name
   *
   * @return tournament name match is being held in
   */
  public String getTourneyName() {return this.tourneyName;}

  /**
   * Accessor method for surface the match is being played on
   *
   * @return surface type the match is being played on
   */
  public String getSurface() {return this.surfaceType;}

  /**
   * Accessor method for the level the tournament is (Grand Slam, masters 1000, ATP 500, ATP 250)
   *
   * @return Letter that represents the level the tournament is being played
   */
  public TourneyLevel getTourneyLevel() {return this.tourney_Level;}

  /**
   * Accessor method for the start  ing date of the tournament
   *
   * @return starting date of the tournament
   */
  public String getStart() {return this.dateStart;}

  /**
   * Accessor method for unique ID of winner of match
   *
   * @return Unique id for winner of match
   */
  public String getWinnerID() {return this.winnerID;}

  /**
   * Accessor method for name of winner
   *
   * @return name of winner
   */
  public String getWinnerName() {return this.winnerName;}

  /**
   * Accessor method for origin of winner
   *
   * @return origin of winner
   */
  public String getWinnerOrigin() {return this.winnerOrigin;}

  /**
   * Accessor method for age of winner
   *
   * @return age of winner
   */
  public int getWinnerAge() {return this.winnerAge;}

  /**
   * Accessor method for unique ID for the loser of the match
   *
   * @return unique ID for loser of the match
   */
  public String getLoserID() {return this.loserID;}

  /**
   * Accessor method for the name of the loser of the match
   *
   * @return name of the loser of the match
   */
  public String getLoserName() {return this.loserName;}

  /**
   * Accessor method for the origin of the loser of the match
   *
   * @return origin of the loser of the match
   */
  public String getLoserOrigin() {return this.loserOrigin;}

  /**
   * Accessor method for the age of the loser of the match
   *
   * @return age of loser
   */
  public int getLoserAge() {return this.loserAge;}

  /**
   * Accessor method for score of the match
   *
   * @return score of the match in form (6-4, 6-4)
   */
  public String getMatchScore() {return this.matchScore;}

  /**
   * Accessor method for the round of a tournament the match was being held in
   *
   * @return round of tournament the match was (Final, semifinal, R32)
   */
  public Round getRound() {return this.round;}

  /**
   * Accessor method for number of aces hit by winner
   *
   * @return number of aces hit by winner
   */
  public int getWinnerAces() {return this.winnerAce;}

  /**
   * Accessor method for number of double faults hit by the winner
   *
   * @return number of double faults hit by the winner
   */
  public int getWinnerDoubleFaults() {return this.winnerDoubleFaults;}

  /**
   * Accessor method for number of service games played by the winner
   *
   * @return number of service games played by the winner
   */
  public int getWinnerServiceGms() {return this.winnerServiceGms;}

  /**
   * Accessor method for number of aces hit by the loser
   *
   * @return number of aces hit by the loser
   */
  public int getLoserAces() {return this.loserAce;}

  /**
   * Accessor method for number of double faults hit by the loser
   *
   * @return number of aces hit by the loser
   */
  public int getLoserDoubleFaults() {return this.loserDoubleFaults;}

  /**
   * Accessor method for number of service games played by the loser
   *
   * @return number of service games played by the loser
   */
  public int getLoserServiceGames() {return this.loserServiceGames;}

  /**
   * Accessor method for the official number of ATP ranking points of winner when the tournament
   * began
   *
   * @return number of ATP ranking points of the winner
   */
  public int getWinnerRankingPts() {return this.winnerRankingPts;}

  /**
   * Accessor method for the official number of ATP ranking points of loser when the tournament
   * began
   *
   * @return number of ATP ranking points of the loser
   */
  public int getLoserRankingPts() {return this.loserRankingPts;}


}
