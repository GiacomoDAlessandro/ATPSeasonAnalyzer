import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class TESTER {

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

  public static boolean statsAreCorrectlyAdded() {
    return false;
  }

  public static boolean statsIncorrectlyAdded() {
    return false;
  }

  public static boolean tourneyRoundsCorrect() {
    return false;
  }

  public static boolean fileCorrectlyRead() {
    return false;
  }

  public static boolean fileDoesNotExist() {
    return false;
  }

  public static void main(String[] args) {
    boolean allTestsPassed =
            matchLoaderTester() && statsAreCorrectlyAdded()
            && statsIncorrectlyAdded() && tourneyRoundsCorrect()
            && fileCorrectlyRead() && fileDoesNotExist();

    System.out.println("matchLoaderTester: " + matchLoaderTester());
    System.out.println("statsAreCorrectlyAdded: " + statsAreCorrectlyAdded());
    System.out.println("statsIncorrectlyAdded: " + statsIncorrectlyAdded());
    System.out.println("tourneyRoundsCorrect: " + tourneyRoundsCorrect());
    System.out.println("fileCorrectlyRead: " + fileCorrectlyRead());
    System.out.println("fileDoesNotExist: " + fileDoesNotExist());

    System.out.println("All Tests Passed: " + allTestsPassed);
  }

}
