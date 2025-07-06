import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

/**
 * Graphic user interface for the ATP season analyzer
 */
public class UserInterface extends Application{

  /**
   * Window where all different screens are held
   */
  private Stage primaryWindow;

  /**
   * Initial screen, the home screen
   */
  private Scene homeScreen;

  /**
   * The screen where user chooses what statistic to sort the players by
   */
  private Scene sortingSelectionScreen;

  /**
   * Label for the home screen
   */
  private Label homeScreenlabel = new Label("ATP Season Analyzer");

  /**
   * Drop down box for the year to be chosen
   */
  private ComboBox<String> yearChosen;

  /**
   * Hashmap that holds all the players' files
   */
  private HashMap<String, playerStats> playerFiles;

  /**
   * Sorted LinkedHashMap that has all the players' files sorted by a certain statistic
   */
  private LinkedHashMap<String, playerStats> sortedPlayerFiles;

  /**
   * Vertical container for the homeScreen that holds all UI components and arranges them vertically
   */
  private VBox homeScreenBox;

  /**
   * Button to continue after choosing the year on the home screen
   */
  private Button continueButton;

  /**
   * Year chosen
   */
  private String selectedYear;

  /**
   * Screen where the user can see all the sorted statistics
   */
  private Scene metricsScene;

  /**
   * Metric to sort players by
   */
  private String selectedMetric;

  /**
   * Continue button after choosing what metric to sort players by
   */
  private Button sortingContinue;

  /**
   * Boolean signaling whether the user wants to sort the players from highest to lowest or
   * lowest to highest
   */
  private boolean increasingSort = false;

  /**
   * Text box where user can search a player they want to find the statistics on
   */
  private TextField searchField;

  /**
   * The playerStats object of a player searched
   */
  private playerStats playerSearched;

  /**
   * Screen for the page that pops up after a player is searched individually
   */
  private Scene individualSearch;


  /**
   * Setup for the primary window that holds all the different screens
   */
  private void setPrimaryWindow() {
    setHomeScreen();
    setupSortingScreen();
    this.primaryWindow.initStyle(StageStyle.DECORATED);
    this.primaryWindow.setTitle("ATP Season Analyzer");
    this.primaryWindow.setScene(homeScreen);
    this.primaryWindow.show();
  }

  /**
   * Setup for the dropdown box for the years to analyze
   */
  private void setupDropDownYears() {
    this.yearChosen = new ComboBox<>();
    this.yearChosen.getItems().addAll("2024", "2023", "2022", "2021", "2020", "2019", "2018",
        "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010");
    this.yearChosen.setValue("2024");
    this.yearChosen.setPromptText("Select Year To Review");
    this.selectedYear = yearChosen.getValue();
    this.yearChosen.setOnAction(event -> {
      this.selectedYear = yearChosen.getValue();
    });
  }

  /**
   * Setup for the home screen
   */
  private void setHomeScreen() {
    setupDropDownYears();
    this.homeScreenBox = new VBox(15);
    this.homeScreen = new Scene(this.homeScreenBox, 700, 700);
    updateHomeScreenTitle();
    homeScreenBox.setAlignment(Pos.CENTER);
    updateButton();
    this.homeScreenBox.getChildren().addAll(this.homeScreenlabel, this.yearChosen, this.continueButton);
  }

  /**
   * setup for the home screen title
   */
  private void updateHomeScreenTitle() {
    this.homeScreenlabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
  }

  /**
   * setup for different buttons needed in different scenes
   */
  private void updateButton() {
    this.continueButton = new Button();
    this.continueButton.setText("Continue");
    this.continueButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    this.continueButton.setOnAction(event -> {
      this.primaryWindow.setScene(this.sortingSelectionScreen);
    });

    this.sortingContinue = new Button();
    this.sortingContinue.setText("Analyze");
    this.sortingContinue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    this.sortingContinue.setOnAction(event -> {
      setupMetricsScene();
      this.primaryWindow.setScene(metricsScene);
    });
  }

  /**
   * setup for the scene where the user selects what metric to sort the players by
   */
  private void setupSortingScreen() {

    Button homeButton = new Button();
    homeButton.setText("Home");

    homeButton.setOnAction(event -> primaryWindow.setScene(this.homeScreen));

    VBox topBox = new VBox(homeButton);
    topBox.setAlignment(Pos.TOP_LEFT);
    topBox.setPadding(new Insets(12));
    ComboBox<String> sortingOption = new ComboBox<>();
    BorderPane layout = new BorderPane();
    sortingOption.getItems().addAll("wins", "highest ranking points reached", "Losses" , "Age",
        "Aces",
        "Win" +
        "-Percentage");
    sortingOption.setValue("wins");
    this.selectedMetric = "wins";
    sortingOption.setOnAction(event -> {
      this.selectedMetric = sortingOption.getValue();
    });

    CheckBox checkBox = new CheckBox("Invert Sort (Smallest to Largest)");

    checkBox.setOnAction(event -> {
      this.increasingSort = checkBox.isSelected();
    });

    VBox sortingBox = new VBox(15);
    sortingBox.setAlignment(Pos.CENTER);
    Label sortingLabel = new Label("Choose Sorting Metric");
    sortingLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
    sortingBox.getChildren().addAll(sortingLabel, sortingOption, this.sortingContinue, checkBox);
    layout.setTop(topBox);
    layout.setCenter(sortingBox);
    this.sortingSelectionScreen = new Scene(layout, 700, 700);
  }


  /**
   * Setup for the screen where the user can look at all the statistics of the players
   */
  private void setupMetricsScene() {
    BorderPane layout = new BorderPane();

    Button backButton = new Button("Back");
    backButton.setStyle("-fx-background-radius: 10;");
    backButton.setOnAction(event -> primaryWindow.setScene(sortingSelectionScreen));

    Button searchButton = new Button("Search");

    this.searchField = new TextField();
    this.searchField.setPromptText("Enter Player Name");
    this.searchField.setPrefWidth(15);

    searchButton.setOnAction(event -> {
      if (playerFiles.get(this.searchField.getText()) == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Player not Found");
        alert.setHeaderText(null);
        alert.setContentText("The player \"" + this.searchField.getText() + "\" was not found. Please " +
            "try again.");
        alert.showAndWait();
      } else {
        this.playerSearched = playerFiles.get(this.searchField.getText());
        setupIndividualScreen();
        this.primaryWindow.setScene(individualSearch);
      }
    });

    VBox topBack = new VBox(10);
    topBack.setPadding(new Insets(12));
    topBack.setAlignment(Pos.TOP_LEFT);
    topBack.getChildren().addAll(backButton, this.searchField, searchButton);

    VBox metrics = new VBox(10);
    TextArea statsPrinting = new TextArea();
    metrics.setPrefSize(1200,1200);
    VBox.setVgrow(statsPrinting, Priority.ALWAYS);
    statsPrinting.setEditable(false);
    matchLoader loader = new matchLoader();
    try {
      this.playerFiles = loader.readFile("ATP Season Files/atp_matches_" + this.selectedYear +
          ".csv");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File containing year: " + this.selectedYear + " not " +
          "found");
    }
    playerSorter sorter = new playerSorter(this.playerFiles, this.selectedMetric,
        this.increasingSort);
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

      playerStats stats = this.sortedPlayerFiles.get(name);

      builder.append(String.format("%-3s %-34s %-5s %-5s %-7s %-10s %-12s %-6s\n",
          i,
          String.format("%s %s", name, stats.getPlayerOrigin()),  // Puts origin right next to name
          stats.getAge(),
          stats.getWins(),
          stats.getLosses(),
          stats.getWinPercentage(),
          stats.getHighestRankingPts(),
          stats.getAces()));
      builder.append(
          "---------------------------------------------------------------------------------------\n");
      i++;
    }

    statsPrinting.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; " +
        "-fx-font-weight: bold;");
    statsPrinting.setText(builder.toString());

    metrics.getChildren().add(statsPrinting);

    layout.setTop(topBack);
    layout.setCenter(metrics);
    this.metricsScene = new Scene(layout, 850, 1100
    );

  }

  /**
   * Setup for the screen where the user can see the statistics for a single player
   */
  private void setupIndividualScreen() {
    VBox topLabel = new VBox(10);
    Button backButton = new Button("Back");
    backButton.setOnAction(event -> {
      this.primaryWindow.setScene(this.metricsScene);
    });

    TextArea individualStats = new TextArea();

    StringBuilder sb = new StringBuilder();

    Label nameLabel = new Label(this.playerSearched.getPlayerName());
    nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    topLabel.setAlignment(Pos.TOP_LEFT);
    topLabel.setPadding(new Insets(10));
    topLabel.getChildren().addAll(backButton, nameLabel);

    BorderPane layout = new BorderPane();

    layout.setTop(topLabel);

    sb.append(String.format("%-15s %-15s\n\n", "Age: " + playerSearched.getAge(), "Aces: " +  +
      playerSearched.getAces()));
    sb.append(String.format("%-15s %-15s\n\n", "Wins: " + playerSearched.getWins(),
        "Losses: " + playerSearched.getLosses()));
    sb.append(String.format("%-15s %-15s\n\n", "Win%: " + playerSearched.getWinPercentage(),
        "Highest ATP Points Reached: " + playerSearched.getHighestRankingPts()));
    sb.append(String.format("Finals Reached: "
        + playerSearched.getTotalFinalsReached()));

    individualStats.setStyle("-fx-font-size: 18px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;");

    individualStats.setText(sb.toString());
    individualStats.setEditable(false);

    layout.setCenter(individualStats);

    this.individualSearch = new Scene(layout, 700, 700);
  }


  /**
   * Starts the UI
   *
   * @param primaryWindow main window where all different scenes are held
   */
  @Override
  public void start(Stage primaryWindow) {
    this.primaryWindow = primaryWindow;
    setPrimaryWindow();
  }

  /**
   * Main
   *
   * @param args Not used
   */
  public static void main(String[] args) {
    launch(args);
  }
}
