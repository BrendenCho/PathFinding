package pathfinding;

import java.util.LinkedList;
import java.util.Queue;
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
public class BreadthFirstSearch {

    private Node t;
    private Queue<Node> queue = new LinkedList<Node>();
    private Control c;
    private boolean path = false;
    private double time = 0;
    private Ll ll;

    public BreadthFirstSearch(Control c) {

        this.c = c;

        ll = new Ll(c.getStart());
        llSearch();

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

    private void search() {
        try {
            while (queue.isEmpty() == false) {

                Node n = queue.peek();

                if (n == null) {
                    queue.clear();

                } else if (n == c.getEnd()) {
                    path = true;
                    throw new NullPointerException();
                }

                if (n.isVisited() == false && c.getShowSteps() == true) {
                    display(n);
                }

                n.setVisited(true);

                if (queue.isEmpty() == false) {

                    queue.remove();
                }

                int x = n.getX();
                int y = n.getY();

                if (x - 1 >= 0 && c.getArr()[x - 1][y].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x - 1][y].setParent(n);
                    queue.add(c.getArr()[x - 1][y]);
                }
                if (y + 1 < c.getNumPerRow() && c.getArr()[x][y + 1].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x][y + 1].setParent(n);
                    queue.add(c.getArr()[x][y + 1]);
                }
                if (x + 1 < c.getNumPerRow() && c.getArr()[x + 1][y].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x + 1][y].setParent(n);
                    queue.add(c.getArr()[x + 1][y]);
                }
                if (y - 1 >= 0 && c.getArr()[x][y - 1].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x][y - 1].setParent(n);
                    queue.add(c.getArr()[x][y - 1]);
                }

            }
        } catch (NullPointerException e) {

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
                time += c.getDelay();
                pt.play();
                t = n.getParent();
                trace(n.getParent());
            }

        } catch (StackOverflowError e) {
            trace(t);
        }
    }

    public void display(Node n) {
        time += c.getDelay();
        PauseTransition ptt = new PauseTransition();
        ptt.setDuration(Duration.millis(time));
        ptt.setOnFinished(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                n.getRect().setFill(GREEN);
            }

        });

        if (n != c.getStart() && n != c.getEnd() && n.getState() != 1 && c.getShowSteps() == true) {

            ptt.play();
        }
    }

    public void llSearch() {
        boolean done = false;
        try {
            while (done == false) {

                Node n = ll.getNode();

                if (n == null) {
                    done = true;
                } else if (n == c.getEnd()) {
                    path = true;
                    done = true;
                }

                if (n.isVisited() == false && c.getShowSteps() == true) {
                    display(n);
                }

                n.setVisited(true);

                int x = n.getX();
                int y = n.getY();

                if (x - 1 >= 0 && c.getArr()[x - 1][y].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x - 1][y].setParent(n);
                    ll.add(new LlNode(c.getArr()[x - 1][y]));
                }
                if (y + 1 < c.getNumPerRow() && c.getArr()[x][y + 1].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x][y + 1].setParent(n);
                    ll.add(new LlNode(c.getArr()[x][y + 1]));
                }
                if (x + 1 < c.getNumPerRow() && c.getArr()[x + 1][y].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x + 1][y].setParent(n);
                    ll.add(new LlNode(c.getArr()[x + 1][y]));
                }
                if (y - 1 >= 0 && c.getArr()[x][y - 1].isVisited() == false && n.getState() != 1) {
                    c.getArr()[x][y - 1].setParent(n);
                    ll.add(new LlNode(c.getArr()[x][y - 1]));
                }
                ll.remove();

            }
        } catch (NullPointerException e) {
        }
    }

    public Queue<Node> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Node> queue) {
        this.queue = queue;
    }

    public Node getT() {
        return t;
    }

    public void setT(Node t) {
        this.t = t;
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

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}
