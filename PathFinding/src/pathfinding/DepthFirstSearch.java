/*

 */
package pathfinding;

import java.util.EmptyStackException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.scene.paint.Color.YELLOW;
import javafx.util.Duration;
import java.util.Stack;
import javafx.scene.control.Alert;
import static javafx.scene.paint.Color.GREEN;

/**
 *
 * @author Brenden Cho
 */
public class DepthFirstSearch {

    private Node t;
    private Node no;
    private Control c;
    private boolean path = false;
    private int time = 0;
    private Stack<Node> stack = new Stack<>();

    public DepthFirstSearch(Control c) {
        this.c = c;

        search(c.getStart(), null);

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

    private void search(Node n, Node parent) {

        PauseTransition ptt = new PauseTransition();
        ptt.setDuration(Duration.millis(time));
        ptt.setOnFinished(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                n.getRect().setFill(GREEN);
            }

        });
        try {
            try {
                n.setParent(parent);
                if (n == c.getEnd()) {
                    path = true;
                    throw new EmptyStackException();
                }
                no = n;
                n.setVisited(true);

                if (n != c.getStart() && n != c.getEnd() && n.getState() != 1 && c.getShowSteps() == true) {
                    time += c.getDelay();
                    ptt.play();
                }

                stack.push(n);
                int x = n.getX();
                int y = n.getY();

                if (x - 1 >= 0 && c.getArr()[x - 1][y].isVisited() == false && n.getState() != 1) {
                    search(c.getArr()[x - 1][y], n);
                } else if (y + 1 < c.getNumPerRow() && c.getArr()[x][y + 1].isVisited() == false && n.getState() != 1) {
                    search(c.getArr()[x][y + 1], n);
                } else if (x + 1 < c.getNumPerRow() && c.getArr()[x + 1][y].isVisited() == false && n.getState() != 1) {
                    search(c.getArr()[x + 1][y], n);
                } else if (y - 1 >= 0 && c.getArr()[x][y - 1].isVisited() == false && n.getState() != 1) {
                    search(c.getArr()[x][y - 1], n);
                } else {
                    stack.pop();
                    Node node = stack.pop();
                    search(node, node.getParent());
                }

            } catch (EmptyStackException e) {

            }
        } catch (StackOverflowError se) {
            search(no, no.getParent());
        }
    }

    private void trace(Node n) {
        try {
            if (n == c.getStart()) {
                return;
            } else if (n == c.getEnd()) {
                trace(c.getEnd().getParent());
            } else {
                time += c.getDelay();
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.millis(time));
                pt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        n.getRect().setFill(YELLOW);
                    }

                });
                pt.play();
                t = n.getParent();
                trace(n.getParent());
            }

        } catch (StackOverflowError e) {
            trace(t);
        }
    }

    public Control getC() {
        return c;
    }

    public void setC(Control c) {
        this.c = c;
    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
