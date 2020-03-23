package pathfinding;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Brenden Cho
 */
public class Control {

    private boolean showSteps = true;
    private int delay = 10;
    private int numItems = 121;
    private int numPerRow = (int) Math.sqrt(numItems);
    private int currentX = 0;
    private int currentY = 0;
    private int rectLength;
    private int rectHeight;
    private Node start;
    private Node end;
    private Node[][] arr = new Node[numPerRow][numPerRow];
    private MainWindow mw;
    private UserControl uc;

    public Control(MainWindow mw) {
        this.mw = mw;
    }

    public void setupMaze() {

        manualStart();
        manualExit();

        for (int x = 0; x < numPerRow; x++) {

            for (int y = 0; y < numPerRow; y++) {

                if (x == 0 || x == numPerRow - 1) {
                    arr[x][y].setState(1);
                    arr[x][y].getRect().setFill(BLACK);
                } else if (y == 0 || y == numPerRow - 1) {
                    arr[x][y].setState(1);
                    arr[x][y].getRect().setFill(BLACK);
                }

            }

        }

        arr[0][1].setState(2);
        arr[numPerRow - 1][numPerRow - 2].setState(3);
        arr[0][1].getRect().setFill(CYAN);
        arr[numPerRow - 1][numPerRow - 2].getRect().setFill(RED);

        start = arr[0][1];
        end = arr[numPerRow - 1][numPerRow - 2];

    }

    public void newBoard() {

        for (int x = 0; x < numPerRow; x++) {

            for (int y = 0; y < numPerRow; y++) {

                mw.getPane().getChildren().remove(arr[x][y].getRect());
            }

        }

        numItems = uc.getNumItems();
        numPerRow = (int) Math.sqrt(numItems);
        currentX = 0;
        currentY = 0;

        arr = new Node[numPerRow][numPerRow];

        draw();
    }

    public void draw() {
        rectLength = mw.getSceneLength() / numPerRow;
        rectHeight = mw.getSceneHeight() / numPerRow;

        for (int i = 0; i < numPerRow; i++) {
            currentX = 0;
            if (i != 0) {
                currentY += rectHeight;
            }

            for (int a = 0; a < numPerRow; a++) {

                Rectangle rect = new Rectangle(currentX, currentY, rectLength, rectHeight);
                rect.setStroke(BLACK);
                rect.setFill(WHITE);
                mw.getPane().getChildren().add(rect);
                arr[i][a] = new Node(rect, 0, i, a);
                currentX += rectLength;
                addHandlers(i, a, rect);
            }
        }
        openAndClose();

    }

    public void addHandlers(int i, int a, Rectangle rect) {
        final Node n = arr[i][a];

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent e) {

                if (getStart() == null && e.isPrimaryButtonDown() == true) {
                    n.setState(2);
                    n.getRect().setFill(CYAN);
                    setStart(n);

                } else if (getEnd() == null && e.isPrimaryButtonDown() == true) {
                    n.setState(3);
                    n.getRect().setFill(RED);
                    setEnd(n);

                } else if (e.isPrimaryButtonDown() == true) {
                    if (n.getState() == 0) {
                        n.setState(1);
                        n.getRect().setFill(BLACK);
                    }
                } else if (e.isSecondaryButtonDown() == true) {
                    if (n.getState() == 1) {
                        n.setState(0);
                        n.getRect().setFill(WHITE);
                    }
                }
            }

        };

        EventHandler<MouseEvent> handle = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent e) {

                if (e.isControlDown() == true) {

                    if (n.getState() == 1) {
                        n.setState(0);
                        n.getRect().setFill(WHITE);
                    }

                }

                if (e.isShiftDown() == true) {

                    if (n.getState() == 0) {
                        n.setState(1);
                        n.getRect().setFill(BLACK);
                    }

                }

            }
        };

        rect.setOnMousePressed(handler);
        rect.setOnMouseEntered(handle);

    }

    public void openAndClose() {

        if (start != null && end != null) {
            start.getRect().setFill(WHITE);
            end.getRect().setFill(WHITE);
            start.setState(0);
            end.setState(0);
            start = null;
            end = null;
        } else if (start != null) {
            start.getRect().setFill(WHITE);
            start.setState(0);
            start = null;
        } else if (end != null) {
            end.getRect().setFill(WHITE);
            end.setState(0);
            end = null;
        }

        Random r = new Random();
        int x = r.nextInt(numPerRow);
        int y = r.nextInt(numPerRow);

        arr[x][y].setState(2);
        arr[x][y].getRect().setFill(CYAN);
        start = arr[x][y];
        int a = 1;
        while (a != 0) {

            x = r.nextInt(numPerRow);
            y = r.nextInt(numPerRow);

            if (arr[x][y].getState() != 2) {
                arr[x][y].setState(3);
                arr[x][y].getRect().setFill(RED);
                end = arr[x][y];
                a = 0;
            }

        }
    }

    public void reset(boolean obstacles) {

        for (int x = 0; x < numPerRow; x++) {

            for (int y = 0; y < numPerRow; y++) {

                if (obstacles == false) {
                    if (arr[x][y].getState() != 1 && arr[x][y].getState() != 2 && arr[x][y].getState() != 3) {
                        arr[x][y].getRect().setFill(WHITE);
                        arr[x][y].setState(0);
                    }
                } else {
                    if (arr[x][y].getState() != 2 && arr[x][y].getState() != 3) {
                        arr[x][y].getRect().setFill(WHITE);
                        arr[x][y].setState(0);
                    }
                }
                arr[x][y].setVisited(false);
                arr[x][y].setMazeVisited(false);

            }

        }

    }

    public void manualStart() {

        start.setState(0);
        start.getRect().setFill(WHITE);
        start = null;

    }

    public void manualExit() {

        end.setState(0);
        end.getRect().setFill(WHITE);
        end = null;

    }

    public void closeStage(Stage s) {
        s.close();
    }

    public void toImage(final Pane pane, String fileName) {

        final WritableImage wi = pane.snapshot(new SnapshotParameters(), null);

        String s = "";
        s = System.getProperty("user.home");
        s += "\\mazeImages";
        System.out.println(s);

        File f = new File(s);

        f.mkdirs();

        if (fileName.equals("")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Brenden Cho");
            a.setContentText("Please Enter a Name for the file.");
            a.show();
        } else {
            final File file = new File(s + "\\" + fileName + ".png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Brenden Cho");
                a.setContentText("An error has occured.");
                a.show();
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Brenden Cho");
            a.setContentText("Image can be found at: " + s);
            a.show();

        }

    }

    public void fill() {
        for (int x = 0; x < numPerRow; x++) {

            for (int y = 0; y < numPerRow; y++) {

                if (arr[x][y].getState() == 0) {
                    arr[x][y].setState(1);
                    arr[x][y].getRect().setFill(BLACK);
                }
            }

        }

    }

    public void setUc(UserControl uc) {
        this.uc = uc;
    }

    public void setShowSteps(boolean showSteps) {
        this.showSteps = showSteps;
    }

    public boolean getShowSteps() {
        return showSteps;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public int getNumPerRow() {
        return numPerRow;
    }

    public void setNumPerRow(int numPerRow) {
        this.numPerRow = numPerRow;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getRectLength() {
        return rectLength;
    }

    public void setRectLength(int rectLength) {
        this.rectLength = rectLength;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void setRectHeight(int rectHeight) {
        this.rectHeight = rectHeight;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public Node[][] getArr() {
        return arr;
    }

    public void setArr(Node[][] arr) {
        this.arr = arr;
    }

    public MainWindow getMw() {
        return mw;
    }

    public void setMw(MainWindow mw) {
        this.mw = mw;
    }

}
