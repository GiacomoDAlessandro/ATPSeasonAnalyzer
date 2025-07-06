import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Uses the mergeSort algorithm to sort players on various different metrics, including: wins,
 * losses, aces, age, win-percentage, and highest ranking points reached.
 */
public class playerSorter {

  /**
   * Single entry from a hashmap of ATP Tennis players with a key of String, and content of
   * playerStats
   */
  private Map.Entry<String, playerStats>[] playerList;

  /**
   * Value that the players are being sorted by
   */
  private String comparableValue;

  /**
   * LinkedHashMap that has players in a certain order
   */
  private LinkedHashMap<String, playerStats> sortedMap;

  /**
   * Constructor that sorts players depending on a comparableValue that is given by the user
   *
   * @param initialList List of players to be sorted
   * @param comparableValue Value that the players are sorted by
   * @param increasing Whether the user wants to sort the list from highest to lowest
   *                   (decreasing) or lowest to highest (increasing)
   */
  public playerSorter(HashMap<String, playerStats> initialList,
      String comparableValue, boolean increasing) {

    //Initializes playerList with the list of players to be sorted
    this.playerList = initialList.entrySet().toArray(new Map.Entry[0]);

    this.comparableValue = comparableValue;

    this.playerList = mergeSortHelper(increasing);

    this.sortedMap = new LinkedHashMap<>();

    //Adds all players to a linkedHashMap after they are sorted
    for (Map.Entry<String, playerStats> entry: this.playerList) {
      this.sortedMap.put(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Accessor method for the sorted list of players
   *
   * @return sorted list of players in a LinkedHashMap
   */
  public LinkedHashMap<String, playerStats> getSortedMap() {return this.sortedMap;}

  /**
   * Helper method for mergesort
   * @param increasing boolean deciding whether the list will be increasing or decreasing
   * @return Map entries of type String, playerStats in an array that are sorted according to the
   * comparable value initialized in the constructor
   */
  public Map.Entry<String, playerStats>[] mergeSortHelper(boolean increasing) {

    mergeSort(playerList, increasing);

    return playerList;
  }

  /**
   * mergeSort algorithm that recursively sorts the playerList provided, either highest to lowest
   * or lowest to highest
   * @param listSort list of players to sort
   * @param increasing boolean deciding whether to sort the list from highest to lowest or lowest
   *                  to highest
   */
  public void mergeSort(Map.Entry<String, playerStats>[] listSort, boolean increasing) {
    int listLength = listSort.length;

    //Getting middle index of array
    int midIndex = listLength / 2;

    Map.Entry<String, playerStats>[] leftHalf = new Map.Entry[midIndex];
    Map.Entry<String, playerStats>[] rightHalf = new Map.Entry[listLength - midIndex];

    //Returns if array is of length 1
    if (listLength < 2) {
      return;
    }

    //For loop that adds the left half of the playerList provided to the leftHalf array
    for (int i = 0; i < leftHalf.length; i++) {
      leftHalf[i] = listSort[i];
    }

    //For loop that adds the right half of the playerList provided to the rightHalf array
    for (int i = midIndex; i < listLength; i++) {
      rightHalf[i - midIndex] = listSort[i];
    }

    //Recursively calling mergeSort with both the left and right halves of the playerList
    mergeSort(leftHalf, increasing);
    mergeSort(rightHalf, increasing);

    //Once all arrays are of length one, the merge method is called
    merge(listSort, leftHalf, rightHalf, increasing);

  }

  /**
   * Merges two arrays provided and sorts them accoridng to the increasing or decreasing value
   * and comparable Value
   *
   * @param listSort sorted list of players
   * @param leftHalf left half of array to be sorted
   * @param rightHalf right half of array to be sorted
   * @param increasing boolean deciding whether to sort the playerList in increasing or
   *                   decreasing fashion
   */
  private void merge(Map.Entry<String, playerStats>[] listSort,
      Map.Entry<String, playerStats>[] leftHalf, Map.Entry<String, playerStats>[] rightHalf,
      boolean increasing) {

    Comparator<Map.Entry<String, playerStats>> comparator = getComparator(this.comparableValue);

    int leftSize = leftHalf.length;
    int rightSize = rightHalf.length;

    int a = 0, b = 0, c = 0;

    while (a < leftSize && b < rightSize) {
      if (!increasing) {
        if (comparator.compare(leftHalf[a], rightHalf[b]) >= 0) {
          listSort[c] = leftHalf[a];
          a++;
        } else {
          listSort[c] = rightHalf[b];
          b++;
        }
        c++;
      } else {
        if (comparator.compare(leftHalf[a], rightHalf[b]) <= 0) {
          listSort[c] = leftHalf[a];
          a++;
        } else {
          listSort[c] = rightHalf[b];
          b++;
        }
        c++;
      }
    }

    while (a < leftSize) {
      listSort[c] = leftHalf[a];
      c++;
      a++;
    }

    while (b < rightSize) {
      listSort[c] = rightHalf[b];
      c++;
      b++;
    }

    this.playerList = listSort;

  }

  /**
   * Comparator which takes in the String that the user submits to sort the playerlist by and
   * returns the value to sort the list by
   *
   * @param comparableValue String signaling what value to sort the list by
   * @return Comparatpr of type map entry with the key of String and content of playerStats
   */
  public Comparator<Map.Entry<String, playerStats>> getComparator(String comparableValue) {
    switch (comparableValue.toLowerCase()) {
      case "wins":
        return Comparator.comparing(entry -> entry.getValue().getWins());
      case "losses":
        return Comparator.comparing(entry -> entry.getValue().getLosses());
      case "aces":
        return Comparator.comparing(entry -> entry.getValue().getAces());
      case "win-percentage":
        return Comparator.comparing(entry -> entry.getValue().getWinPercentage());
      case "age":
        return Comparator.comparing(entry -> entry.getValue().getAge());
      case "highest ranking points reached":
        return Comparator.comparing(entry -> entry.getValue().getHighestRankingPts());
      default:
        throw new IllegalArgumentException("Comparable value was not found");
    }
  }
}

