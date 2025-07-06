import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Testing Class for the tennis player objects and functions
 */
public class TESTER  {

  /**
   * Tests the matchLoader class for loading a player's statistics and creating a playerStats
   * object. Checks that the correct rounds played are added, and all other statistics necessary.
   *
   * @return true if test passes false otherwise
   */
  public static boolean matchLoaderTester() {
    try {
      //Reads in testing file
      FileReader file = new FileReader("src/ATPMATCHESTESTER");
      matchLoader loader = new matchLoader();

      //Creates a playerStats object using the tester constructor
      playerStats Jannik = new playerStats("ITA", "Jannik Sinner", 3, 0,
          8710, 10, 22);
      //Adding rounds played to expected results for Jannik Sinner
      Jannik.addRoundPlayed(Round.QUARTERFINALS, TourneyLevel.MASTERS_1000);
      Jannik.addRoundPlayed(Round.ROUND_OF_16, TourneyLevel.MASTERS_1000);
      Jannik.addRoundPlayed(Round.ROUND_OF_32, TourneyLevel.MASTERS_1000);

      HashMap<String, playerStats> expected = new HashMap<>();
      expected.put("Jannik Sinner", Jannik);
      HashMap<String, playerStats> actual = loader.readFile("src/ATPMATCHESTESTER");
      if (!(actual.get("Jannik Sinner").equals(expected.get("Jannik Sinner")))) {
        return false;
      }
    } catch(FileNotFoundException ignore) {
      return false;
    }
    return true;
  }

  /**
   * Checks that a correct file read goes through without any exceptions being thrown
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean fileCorrectlyRead() {
    try {
      matchLoader loader = new matchLoader();
      loader.readFile("src/ATPMATCHESTESTER");
      return true;
    } catch (FileNotFoundException e) {
      return false;
    }
  }


  /**
   * Tests that a FileNotFoundException is thrown when the file does not exist
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean fileDoesNotExist() {
    try {
      matchLoader loader = new matchLoader();
      loader.readFile("Apple");
      return false;
    } catch (FileNotFoundException e) {
      return true;
    }

  }

  /**
   * Tests that the mergeSort algorithm sorting the player's by their wins is working correctly
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean sortsByWins() {
    try {
      matchLoader loader = new matchLoader();
      HashMap<String, playerStats> players = loader.readFile("src/ATPMATCHESTESTER");
      playerSorter sorter = new playerSorter(players, "wins", true);
      LinkedHashMap<String, playerStats> sortedPlayers = sorter.getSortedMap();

      Map.Entry<String, playerStats> firstEntry = sortedPlayers.entrySet().iterator().next();

      String topPlayer = firstEntry.getKey();
      playerStats topStats = firstEntry.getValue();

      if (!topPlayer.equals("Jannik Sinner") || topStats.getWins() != 3) {
        return false;
      }

      return true;
    } catch (FileNotFoundException ignore) {
      return false;
    }

  }

  /**
   * Output stating whether all tests pass or not
   *
   * @param args not used
   */
  public static void main(String[] args) {
    boolean allTestsPassed =
            matchLoaderTester() && fileCorrectlyRead()
                && fileDoesNotExist();

    System.out.println("matchLoaderTester: " + matchLoaderTester());
    System.out.println("fileCorrectlyRead: " + fileCorrectlyRead());
    System.out.println("fileDoesNotExist: " + fileDoesNotExist());
    System.out.println("sortsByWins: " + sortsByWins());
    System.out.println("All Tests Passed: " + allTestsPassed);
  }

}
