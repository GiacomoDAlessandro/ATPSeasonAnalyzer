import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TESTER  {

  public static boolean matchLoaderTester() {
    try {
      FileReader file = new FileReader("src/ATPMATCHESTESTER");
      matchLoader loader = new matchLoader();
      playerStats Jannik = new playerStats("ITA", "Jannik Sinner", 3, 0,
          8710, 10, 22);
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


  public static boolean fileCorrectlyRead() {
    try {
      matchLoader loader = new matchLoader();
      loader.readFile("src/ATPMATCHESTESTER");
      return true;
    } catch (FileNotFoundException e) {
      return false;
    }
  }

  public static boolean fileDoesNotExist() {
    try {
      matchLoader loader = new matchLoader();
      loader.readFile("Apple");
      return false;
    } catch (FileNotFoundException e) {
      return true;
    }

  }

  public static boolean sortsByWins() {
    try {
      matchLoader loader = new matchLoader();
      HashMap<String, playerStats> players = loader.readFile("src/ATPMATCHESTESTER");
      playerSorter sorter = new playerSorter(players, "wins");
      LinkedHashMap<String, playerStats> sortedPlayers = sorter.getSortedMap();
      sortedPlayers = sorter.getSortedMap();

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
