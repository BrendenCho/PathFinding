package pathfinding;

import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author Brenden Cho
 */
public class UserControl {

    private int numItems;
    private MainWindow mw;
    private Control c;

    private VBox vb = new VBox();
    private HBox startBox = new HBox();
    private HBox mainBox = new HBox();
    private HBox saveBox = new HBox();
    private HBox optionsBox = new HBox();
    private HBox optionsBoxTwo = new HBox();

    private Scene s = new Scene(vb, 450, 300);
    private Stage stage = new Stage();

    private TextField fileName = new TextField();

    private Button fill = new Button("Fill");
    private Button clearObstacles = new Button("Clear Obstacles");
    private Button randomBoth = new Button("Randomize");
    private Button placeStart = new Button("Entrance");
    private Button placeExit = new Button("Exit");
    private Button clear = new Button("Clear Board");
    private Button save = new Button("Save");
    private Button optionsApply = new Button("Apply Changes");
    private Button run = new Button("Run");
    private Button maze = new Button("Maze");

    private Text delayText = new Text("Delay");
    private Text start = new Text("Entrance and Exit Controls");
    private Text main = new Text("Main Controls");
    private Text saveText = new Text("Save as Png");
    private Text options = new Text("Options");

    private ComboBox optionsComboBox = new ComboBox();
    private ComboBox mainComboBox = new ComboBox();
    private ComboBox delayComboBox = new ComboBox();

    private CheckBox showSteps = new CheckBox();

    private ObservableList<String> mazeOptions = FXCollections.observableArrayList("5 x 5", "11 x 11", "15 x 15", "21 x 21");
    private ObservableList<String> mainOptions = FXCollections.observableArrayList("A*", "Dijkstra's", "Depth First", "Breadth First");
    private ObservableList<String> delayOptions = FXCollections.observableArrayList("1ms", "10ms", "100ms", "1000ms");

    public UserControl(MainWindow mw, Control c) {
        this.mw = mw;
        this.c = c;
        stage.setTitle("Brenden Cho");
        stage.setResizable(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent e) {
                c.closeStage(getStage());
                c.closeStage(mw.getStage());
            }

        });

        EventHandler<ActionEvent> randomBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                c.openAndClose();
                c.reset(false);
            }
        };

        EventHandler<ActionEvent> startBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                try {
                    c.manualStart();
                    c.reset(false);
                } catch (NullPointerException np) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Please Click a Grid on the Screen to Place the Entrance");
                    a.show();
                }

            }

        };

        EventHandler<ActionEvent> exitBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                try {
                    c.manualExit();
                    c.reset(false);
                } catch (NullPointerException np) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Please Click a Grid on the Screen to Place the Exit");
                    a.show();
                }
            }

        };

        EventHandler<ActionEvent> clearBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                c.reset(false);
            }
        };

        EventHandler<ActionEvent> clearObstaclesBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                c.reset(true);
            }
        };

        EventHandler<ActionEvent> fillBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                c.fill();
            }
        };

        EventHandler<ActionEvent> saveBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                String text = getFileName().getText();
                c.toImage(mw.getPane(), text);
                getFileName().clear();
            }
        };

        EventHandler<ActionEvent> optionsBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                try {

                    Alert a = new Alert(AlertType.CONFIRMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Are you sure your maze will be lost");
                    Optional<ButtonType> result = a.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        String temp = getOptionsComboBox().getValue().toString();
                        setNumItems(temp);
                        c.newBoard();
                        c.setShowSteps(getShowSteps().isSelected());
                        getShowSteps().setSelected(c.getShowSteps());
                        getAndSetDelay();
                    }
                } catch (NullPointerException npe) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Please Choose a Grid Size");
                    a.show();
                }

            }
        };

        EventHandler<ActionEvent> mazeBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                c.reset(true);
                Prim p = new Prim(c);
            }
        };

        EventHandler<ActionEvent> runBttn = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                String s = "";
                try {
                    s = getMainComboBox().getValue().toString();
                } catch (NullPointerException nu) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Please pick an algorithim");
                    a.show();
                }

                try {
                    c.reset(false);
                    switch (s) {
                        case "A*":
                            AStar as = new AStar(c);
                            disable(true, 0);
                            disable(false, as.getTime() + 10);
                            break;
                        case "Dijkstra's":
                            Dijkstra d = new Dijkstra(c);
                            disable(true, 0);
                            disable(false, d.getTime() + 10);
                            break;
                        case "Depth First":
                            DepthFirstSearch df = new DepthFirstSearch(c);
                            disable(true, 0);
                            disable(false, df.getTime() + 10);
                            break;
                        case "Breadth First":
                            BreadthFirstSearch bf = new BreadthFirstSearch(c);
                            disable(true, 0);
                            disable(false, (int) bf.getTime() + 10);
                            break;
                    }
                } catch (NullPointerException npee) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("Please place the entrance/exit");
                    a.show();
                }
            }

        };

        optionsComboBox.setItems(mazeOptions);
        optionsComboBox.getSelectionModel().select(1);
        mainComboBox.setItems(mainOptions);
        mainComboBox.getSelectionModel().select(0);
        delayComboBox.setItems(delayOptions);
        delayComboBox.getSelectionModel().select(1);

        showSteps.setSelected(true);

        fill.setOnAction(fillBttn);
        clear.setOnAction(clearBttn);
        optionsApply.setOnAction(optionsBttn);
        save.setOnAction(saveBttn);
        clearObstacles.setOnAction(clearObstaclesBttn);
        randomBoth.setOnAction(randomBttn);
        placeStart.setOnAction(startBttn);
        placeExit.setOnAction(exitBttn);
        run.setOnAction(runBttn);
        maze.setOnAction(mazeBttn);

        startBox.setAlignment(Pos.CENTER);
        startBox.setSpacing(5);
        startBox.setPadding(new Insets(5, 5, 5, 5));
        startBox.getChildren().add(randomBoth);
        startBox.getChildren().add(placeStart);
        startBox.getChildren().add(placeExit);

        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(5);
        mainBox.setPadding(new Insets(5, 5, 5, 5));
        mainBox.getChildren().add(maze);
        mainBox.getChildren().add(fill);
        mainBox.getChildren().add(clearObstacles);
        mainBox.getChildren().add(clear);
        mainBox.getChildren().add(mainComboBox);
        mainBox.getChildren().add(run);

        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setSpacing(5);
        optionsBox.setPadding(new Insets(5, 5, 5, 5));
        optionsBox.getChildren().add(new Text("Maze Size"));
        optionsBox.getChildren().add(optionsComboBox);
        optionsBox.getChildren().add(new Text("Show Steps"));
        optionsBox.getChildren().add(showSteps);

        optionsBoxTwo.setAlignment(Pos.CENTER);
        optionsBoxTwo.setSpacing(5);
        optionsBoxTwo.setPadding(new Insets(5, 5, 5, 5));
        optionsBoxTwo.getChildren().add(delayText);
        optionsBoxTwo.getChildren().add(delayComboBox);
        optionsBoxTwo.getChildren().add(optionsApply);

        saveBox.setAlignment(Pos.CENTER);
        saveBox.setSpacing(5);
        saveBox.setPadding(new Insets(5, 5, 5, 5));
        saveBox.getChildren().add(fileName);
        saveBox.getChildren().add(save);

        vb.setAlignment(Pos.TOP_CENTER);
        vb.setPadding(new Insets(5, 5, 5, 5));
        vb.getChildren().add(start);
        vb.getChildren().add(startBox);
        vb.getChildren().add(main);
        vb.getChildren().add(mainBox);
        vb.getChildren().add(options);
        vb.getChildren().add(optionsBox);
        vb.getChildren().add(optionsBoxTwo);
        vb.getChildren().add(saveText);
        vb.getChildren().add(saveBox);

        stage.setX(mw.getStage().getX() + 1200);
        stage.setY(mw.getStage().getY());
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setScene(s);
        stage.show();

    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(String s) {

        switch (s) {

            case "5 x 5":
                numItems = 25;
                break;

            case "11 x 11":
                numItems = 121;
                break;

            case "15 x 15":
                numItems = 225;
                break;

            case "21 x 21":
                numItems = 441;
                break;
        }
    }

    public void disable(boolean b, int time) {

        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.millis(time));

        pt.setOnFinished(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                randomBoth.setDisable(b);
                placeStart.setDisable(b);
                placeExit.setDisable(b);
                run.setDisable(b);
                clear.setDisable(b);
                clearObstacles.setDisable(b);
                fill.setDisable(b);
                optionsApply.setDisable(b);
                maze.setDisable(b);
            }

        });
        pt.play();

    }

    public void getAndSetDelay() {

        try {
            String s = delayComboBox.getValue().toString();

            switch (s) {

                case "1ms":
                    c.setDelay(1);
                    break;
                case "10ms":
                    c.setDelay(10);
                    break;
                case "100ms":
                    c.setDelay(100);
                    break;
                case "1000ms":
                    c.setDelay(1000);
                    break;
            }

        } catch (NullPointerException e) {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Brenden Cho");
            a.setContentText("Please select a delay");
            a.show();

        }

    }

    public MainWindow getMw() {
        return mw;
    }

    public void setMw(MainWindow mw) {
        this.mw = mw;
    }

    public Control getC() {
        return c;
    }

    public void setC(Control c) {
        this.c = c;
    }

    public VBox getVb() {
        return vb;
    }

    public void setVb(VBox vb) {
        this.vb = vb;
    }

    public HBox getStartBox() {
        return startBox;
    }

    public void setStartBox(HBox startBox) {
        this.startBox = startBox;
    }

    public HBox getMainBox() {
        return mainBox;
    }

    public void setMainBox(HBox mainBox) {
        this.mainBox = mainBox;
    }

    public HBox getSaveBox() {
        return saveBox;
    }

    public void setSaveBox(HBox saveBox) {
        this.saveBox = saveBox;
    }

    public HBox getOptionsBox() {
        return optionsBox;
    }

    public void setOptionsBox(HBox optionsBox) {
        this.optionsBox = optionsBox;
    }

    public HBox getOptionsBoxTwo() {
        return optionsBoxTwo;
    }

    public void setOptionsBoxTwo(HBox optionsBoxTwo) {
        this.optionsBoxTwo = optionsBoxTwo;
    }

    public Scene getS() {
        return s;
    }

    public void setS(Scene s) {
        this.s = s;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextField getFileName() {
        return fileName;
    }

    public void setFileName(TextField fileName) {
        this.fileName = fileName;
    }

    public Button getFill() {
        return fill;
    }

    public void setFill(Button fill) {
        this.fill = fill;
    }

    public Button getClearObstacles() {
        return clearObstacles;
    }

    public void setClearObstacles(Button clearObstacles) {
        this.clearObstacles = clearObstacles;
    }

    public Button getRandomBoth() {
        return randomBoth;
    }

    public void setRandomBoth(Button randomBoth) {
        this.randomBoth = randomBoth;
    }

    public Button getPlaceStart() {
        return placeStart;
    }

    public void setPlaceStart(Button placeStart) {
        this.placeStart = placeStart;
    }

    public Button getPlaceExit() {
        return placeExit;
    }

    public void setPlaceExit(Button placeExit) {
        this.placeExit = placeExit;
    }

    public Button getClear() {
        return clear;
    }

    public void setClear(Button clear) {
        this.clear = clear;
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public Button getOptionsApply() {
        return optionsApply;
    }

    public void setOptionsApply(Button optionsApply) {
        this.optionsApply = optionsApply;
    }

    public Button getRun() {
        return run;
    }

    public void setRun(Button run) {
        this.run = run;
    }

    public Text getDelayText() {
        return delayText;
    }

    public void setDelayText(Text delayText) {
        this.delayText = delayText;
    }

    public Text getStart() {
        return start;
    }

    public void setStart(Text start) {
        this.start = start;
    }

    public Text getMain() {
        return main;
    }

    public void setMain(Text main) {
        this.main = main;
    }

    public Text getSaveText() {
        return saveText;
    }

    public void setSaveText(Text saveText) {
        this.saveText = saveText;
    }

    public Text getOptions() {
        return options;
    }

    public void setOptions(Text options) {
        this.options = options;
    }

    public ComboBox getOptionsComboBox() {
        return optionsComboBox;
    }

    public void setOptionsComboBox(ComboBox optionsComboBox) {
        this.optionsComboBox = optionsComboBox;
    }

    public ComboBox getMainComboBox() {
        return mainComboBox;
    }

    public void setMainComboBox(ComboBox mainComboBox) {
        this.mainComboBox = mainComboBox;
    }

    public ComboBox getDelayComboBox() {
        return delayComboBox;
    }

    public void setDelayComboBox(ComboBox delayComboBox) {
        this.delayComboBox = delayComboBox;
    }

    public CheckBox getShowSteps() {
        return showSteps;
    }

    public void setShowSteps(CheckBox showSteps) {
        this.showSteps = showSteps;
    }

    public ObservableList<String> getMazeOptions() {
        return mazeOptions;
    }

    public void setMazeOptions(ObservableList<String> mazeOptions) {
        this.mazeOptions = mazeOptions;
    }

    public ObservableList<String> getMainOptions() {
        return mainOptions;
    }

    public void setMainOptions(ObservableList<String> mainOptions) {
        this.mainOptions = mainOptions;
    }

    public ObservableList<String> getDelayOptions() {
        return delayOptions;
    }

    public void setDelayOptions(ObservableList<String> delayOptions) {
        this.delayOptions = delayOptions;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

}
