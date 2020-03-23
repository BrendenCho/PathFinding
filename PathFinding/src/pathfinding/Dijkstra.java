package pathfinding;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.YELLOW;
import javafx.util.Duration;

/**
 *
 * @author Brenden Cho
 */
public class Dijkstra {

    private boolean path = false;
    private Control c;
    private int time;
    private PriorityLl unsearched;
    private Node[] searched;
    private int searchedLength = 0;
    int counter = 0;

    public Dijkstra(Control c) {
        this.c = c;
        unsearched = new PriorityLl(c.getStart());
        searched = new Node[c.getNumItems()];
        addAll();
        c.getStart().setgCost(0);
        search();

        if (path == true) {
            trace(c.getEnd());
        } else {
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.millis(time));

            pt.setOnFinished(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent e) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Brenden Cho");
                    a.setContentText("No Path!");
                    a.show();
                }

            });
            pt.play();
        }

    }

    private void addAll() {

        for (int x = 0; x < c.getNumPerRow(); x++) {

            for (int y = 0; y < c.getNumPerRow(); y++) {

                if (c.getArr()[x][y].getState() != 1) {

                    c.getArr()[x][y].setgCost(Integer.MAX_VALUE);
                    unsearched.add(new LlNode(c.getArr()[x][y]));
                }

            }

        }

    }

    private void search() {
        try {
            for (;;) {
                LlNode n = unsearched.getHead();

                if (n.getNode().getgCost() == Integer.MAX_VALUE) {
                    throw new NullPointerException();
                } else if (n == null) {
                    throw new NullPointerException();
                } else if (n.getNode() == c.getEnd()) {
                    path = true;
                    throw new NullPointerException();
                }

                if (n.getNode() != c.getStart() && n.getNode() != c.getEnd() && c.getShowSteps() == true) {
                    time += c.getDelay();
                    PauseTransition pt = new PauseTransition();
                    pt.setDuration(Duration.millis(time));
                    final Node temp = n.getNode();
                    pt.setOnFinished(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent e) {
                            temp.getRect().setFill(GREEN);
                        }

                    });
                    pt.play();
                }

                unsearched.remove();

                evaluateNeighbors(n.getNode());

                searched[searchedLength] = n.getNode();
                searchedLength++;

            }
        } catch (NullPointerException e) {

        }
    }

    private int gCost(Node n, Node parent) {
        int cost = parent.getgCost();

        if (n.getX() == parent.getX() || n.getY() == parent.getY()) {
            cost += 10;
        } else {
            cost += 14;
        }
        return cost;
    }

    private void evaluateNeighbors(Node n) {

        for (int x = n.getX() - 1; x < n.getX() + 2; x++) {

            for (int y = n.getY() - 1; y < n.getY() + 2; y++) {

                if (x >= 0 && x < c.getNumPerRow() && y >= 0 && y < c.getNumPerRow()) {

                    if (c.getArr()[x][y].getState() != 1) {

                        if (gCost(c.getArr()[x][y], n) < c.getArr()[x][y].getgCost()) {

                            c.getArr()[x][y].setgCost(gCost(c.getArr()[x][y], n));
                            c.getArr()[x][y].setParent(n);

                            unsearched.removeNode(c.getArr()[x][y]);
                        }

                    }
                }
            }

        }

    }

    private void trace(Node n) {

        if (n != c.getStart()) {

            if (n != c.getStart() && n != c.getEnd()) {
                time += c.getDelay();
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.millis(time));
                final Node temp = n;
                pt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        temp.getRect().setFill(YELLOW);
                    }

                });
                pt.play();
            }

            trace(n.getParent());
        }

    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public Control getC() {
        return c;
    }

    public void setC(Control c) {
        this.c = c;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
