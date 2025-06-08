import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class matchLoader extends PlayerRegistry {

  private String[][] newData;

  private HashMap<String, playerStats> registeredPlayers;

  public String[][] getMatches() {return this.newData;}


  public ArrayList<Match> readYearMatches(FileReader file) throws FileNotFoundException {

    //Make reader to read file that is called to be read
    BufferedReader reader = new BufferedReader(file);

    ArrayList<String> matchLines = new ArrayList<>();


    try {
      String line;
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

  public HashMap<String, playerStats> registerPlayers(ArrayList<Match> matches) {
    for (Match player: matches) {
      this.registeredPlayers = recordMatch(player);
    }

    return registeredPlayers;
  }


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

