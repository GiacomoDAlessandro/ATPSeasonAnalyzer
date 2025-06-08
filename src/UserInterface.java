import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javafx.stage.StageStyle;
import javafx.scene.control.ComboBox;

public class UserInterface extends Application{

  private Stage primaryWindow;

  private Scene homeScreen;

  private Scene sortingSelectionScreen;

  private Label homeScreenlabel = new Label("ATP Season Analyzer");

  private ComboBox<String> yearChosen;

  private HashMap<String, playerStats> playerFiles;

  private LinkedHashMap<String, playerStats> sortedPlayerFiles;

  private VBox homeScreenBox;

  private Button continueButton;

  private String selectedYear;

  private Scene metricsScene;

  private String selectedMetric;

  private Button sortingContinue;

  private Button backButton;



  private void setPrimaryWindow() {
    setHomeScreen();
    setupSortingScreen();
    this.primaryWindow.initStyle(StageStyle.DECORATED);
    this.primaryWindow.setTitle("ATP Season Analyzer");
    this.primaryWindow.setScene(homeScreen);
    this.primaryWindow.show();
  }

  private void setupDropDownYears() {
    this.yearChosen = new ComboBox<>();
    this.yearChosen.getItems().addAll("2024", "2023");
    this.yearChosen.setValue("2024");
    this.yearChosen.setPromptText("Select Year To Review");
    this.selectedYear = yearChosen.getValue();
    this.yearChosen.setOnAction(event -> {
      this.selectedYear = yearChosen.getValue();
    });
  }

  private void setHomeScreen() {
    setupDropDownYears();
    this.homeScreenBox = new VBox(15);
    this.homeScreen = new Scene(this.homeScreenBox, 1200, 1200);
    updateHomeScreenTitle();
    homeScreenBox.setAlignment(Pos.CENTER);
    updateButton();
    this.homeScreenBox.getChildren().addAll(this.homeScreenlabel, this.yearChosen, this.continueButton);
  }

  private void updateHomeScreenTitle() {
    this.homeScreenlabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
  }

  private void updateButton() {
    this.continueButton = new Button();
    this.continueButton.setText("Continue");
    this.continueButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    this.continueButton.setOnAction(event -> {
      this.primaryWindow.setScene(this.sortingSelectionScreen);
    });

    sortingContinue = new Button();
    sortingContinue.setText("Analyze");
    sortingContinue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    sortingContinue.setOnAction(event -> {
      setupMetricsScene();
      this.primaryWindow.setScene(metricsScene);
    });
  }

  private void setupSortingScreen() {
    ComboBox<String> sortingOption = new ComboBox<>();
    sortingOption.getItems().addAll("wins", "highest ranking points reached", "Losses" , "Age",
        "Aces",
        "Win" +
        "-Percentage");
    sortingOption.setValue("wins");
    this.selectedMetric = "wins";
    sortingOption.setOnAction(event -> {
      this.selectedMetric = sortingOption.getValue();
    });
    VBox sortingBox = new VBox(15);
    sortingBox.setAlignment(Pos.CENTER);
    Label sortingLabel = new Label("Choose Sorting Metric");
    sortingLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
    sortingBox.getChildren().addAll(sortingLabel, sortingOption, this.sortingContinue);
    this.sortingSelectionScreen = new Scene(sortingBox, 800, 800);
  }

  private void setupMetricsScene() {
    VBox metrics = new VBox(10);
    TextArea statsPrinting = new TextArea();
    metrics.setPrefSize(1200,1200);
    VBox.setVgrow(statsPrinting, Priority.ALWAYS);
    statsPrinting.setEditable(false);
    matchLoader loader = new matchLoader();
    try {
      playerFiles = loader.readFile("src/atp_matches_" + this.selectedYear + ".csv");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File containing year: " + this.selectedYear + " not " +
          "found");
    }
    playerSorter sorter = new playerSorter(this.playerFiles, this.selectedMetric);
    this.sortedPlayerFiles = sorter.getSortedMap();
    StringBuilder builder = new StringBuilder();
    int i = 1;
    builder.append("\n\n");
    builder.append(String.format("%-3s %-34s %-5s %-5s %-7s %-10s %-12s %-6s\n",
        "#", "Name", "Age", "Wins", "Losses", "Win%", "ATP Points", "Aces"));
    builder.append(
        "_______________________________________________________________________________________" +
            "\n\n");
    for (String name : this.sortedPlayerFiles.keySet()) {

      playerStats stats = sortedPlayerFiles.get(name);
      double displayStatistic = 0.0;

      builder.append(String.format("%-3s %-34s %-5s %-5s %-7s %-10s %-12s %-6s\n", i, name,
          stats.getAge(), stats.getWins(),
              stats.getLosses(), stats.getWinPercentage(), stats.getHighestRankingPts(),
          stats.getAces()));
      builder.append(
          "-----------------------------------" +
              "----------------------------------------------------\n");
      i++;
    }

    statsPrinting.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; " +
        "-fx-font-weight: bold;");
    statsPrinting.setText(builder.toString());

    metrics.getChildren().add(statsPrinting);

    this.metricsScene = new Scene(metrics, 850, 1100
    );

  }

  @Override
  public void start(Stage primaryWindow) {
    this.primaryWindow = primaryWindow;
    setPrimaryWindow();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
