import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads files and reads the lines, turning them into match objects and eventually creating
 * playerStats objects with those matches
 */
public class matchLoader extends PlayerRegistry {

  /**
   * Hashmap of all players that played an ATP match in a single year
   */
  private HashMap<String, playerStats> registeredPlayers;

  /**
   * Reads a file given, splits up the lines into different arrays and returns the file as an
   * ArrayList of type match
   *
   * @param file file given to read
   * @return arrayList of type match that has all matches in a given year
   * @throws FileNotFoundException when file given is not found
   */
  public ArrayList<Match> readYearMatches(FileReader file) throws FileNotFoundException {

    //Make reader to read file that is called to be read
    BufferedReader reader = new BufferedReader(file);

    ArrayList<String> matchLines = new ArrayList<>();


    try {
      String line;
      //Skips header line
      reader.readLine();
      //Adds line to matchLines arrayList as long as there is a line left, done when file is empty
      while ((line = reader.readLine()) != null) {
        matchLines.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<Match> matches = new ArrayList<>();


    for (int i = 0; i < matchLines.size(); i++) {
      matches.add(new Match(matchLines.get(i).split(",", -1)));
    }

    return matches;
  }

  /**
   * Registers all matches and calls the recordMatch class with each match
   *
   * @param matches arrayList of type Match with all matches from a given year to register
   * @return HashMap with string as a key and playerStats as the value, of all players that
   * played in the ATP tour in a given year
   */
  public HashMap<String, playerStats> registerPlayers(ArrayList<Match> matches) {
    this.registeredPlayers = new HashMap<>();
    //Iterates through all matches in the matches arrayList and calls recordMatch with each Match
    for (Match player: matches) {
      this.registeredPlayers = recordMatch(player);
    }

    return registeredPlayers;
  }


  /**
   * Reads a file from the name given and calls registerPlayers which eventually gets all
   * statistics of each player that played on the ATP tour in a given year
   *
   * @param fileName name of file to be read
   * @return HashMap with key of String and value of playerStats that has all players' statistics
   * @throws FileNotFoundException thrown when file is not found
   */
  public HashMap<String, playerStats> readFile(String fileName) throws FileNotFoundException{
    try {
      FileReader file = new FileReader(fileName);

      return registerPlayers(readYearMatches(file));

    } catch (FileNotFoundException e) {
      System.out.println("File: " + fileName + " not found");
      throw new FileNotFoundException();
    }
  }

}

