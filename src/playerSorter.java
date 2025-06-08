import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class playerSorter {

  private Map.Entry<String, playerStats>[] playerList;

  private String comparableValue;

  private LinkedHashMap<String, playerStats> sortedMap;

  public playerSorter(HashMap<String, playerStats> initialList, String comparableValue) {
    playerList = initialList.entrySet().toArray(new Map.Entry[0]);
    this.comparableValue = comparableValue;
    playerList = mergeSortHelper();

    sortedMap = new LinkedHashMap<>();

    for (Map.Entry<String, playerStats> entry: playerList) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
  }

  public LinkedHashMap<String, playerStats> getSortedMap() {return this.sortedMap;}


  public Map.Entry<String, playerStats>[] mergeSortHelper() {
    mergeSort(playerList);
    return playerList;
  }

  public void mergeSort(Map.Entry<String, playerStats>[] listSort) {
    int listLength = listSort.length;

    int midIndex = listLength / 2;
    Map.Entry<String, playerStats>[] leftHalf = new Map.Entry[midIndex];
    Map.Entry<String, playerStats>[] rightHalf = new Map.Entry[listLength - midIndex];

    if (listLength < 2) {
      return;
    }

    for (int i = 0; i < leftHalf.length; i++) {
      leftHalf[i] = listSort[i];
    }

    for (int i = midIndex; i < listLength; i++) {
      rightHalf[i - midIndex] = listSort[i];
    }

    mergeSort(leftHalf);
    mergeSort(rightHalf);

    merge(listSort, leftHalf, rightHalf);

  }

  private void merge(Map.Entry<String, playerStats>[] listSort,
      Map.Entry<String, playerStats>[] leftHalf, Map.Entry<String, playerStats>[] rightHalf) {

    Comparator<Map.Entry<String, playerStats>> comparator = getComparator(this.comparableValue);

    int leftSize = leftHalf.length;
    int rightSize = rightHalf.length;

    int a = 0, b = 0, c = 0;

    while (a < leftSize && b < rightSize) {
      if (comparator.compare(leftHalf[a], rightHalf[b]) >= 0) {
        listSort[c] = leftHalf[a];
        a++;
      } else {
        listSort[c] = rightHalf[b];
        b++;
      }
      c++;
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

