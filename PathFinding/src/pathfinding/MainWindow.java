package pathfinding;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Brenden Cho
 */
public class MainWindow {

    private int sceneHeight = 1000;
    private int sceneLength = 1000;

    private Pane pane = new Pane();
    private Scene scene = new Scene(pane, sceneHeight, sceneLength);
    private Stage stage = new Stage();

    UserControl uc;
    Control c;

    public MainWindow() {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent e) {
                c.closeStage(stage);
                c.closeStage(uc.getStage());
            }

        });

        stage.setResizable(false);
        stage.setHeight(sceneHeight + 15);
        stage.setWidth(sceneLength - 5);
        stage.setTitle("Brenden Cho");
        stage.setScene(scene);
        stage.show();
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
    }

    public int getSceneLength() {
        return sceneLength;
    }

    public void setSceneLength(int sceneLength) {
        this.sceneLength = sceneLength;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public UserControl getUc() {
        return uc;
    }

    public void setUc(UserControl uc) {
        this.uc = uc;
    }

    public Control getC() {
        return c;
    }

    public void setC(Control c) {
        this.c = c;
    }

}
