package pathfinding;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Brenden Cho
 */
public class PathFinding extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mw = new MainWindow();
        Control c = new Control(mw);
        UserControl uc = new UserControl(mw, c);

        c.setUc(uc);
        mw.setUc(uc);
        mw.setC(c);
        c.draw();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
