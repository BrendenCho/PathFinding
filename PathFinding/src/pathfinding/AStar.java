package pathfinding;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.ORANGE;
import static javafx.scene.paint.Color.YELLOW;
import javafx.util.Duration;

/**
 *
 * @author Brenden Cho
 */
public class AStar {

    private Control c;
    private int numOpenItems = 1;
    private int numClosedItems = 0;
    private Node[] open;
    private Node[] closed;
    private int time = 0;

    public AStar(Control c) {

        this.c = c;
        open = new Node[c.getNumItems()];
        closed = new Node[c.getNumItems()];

        boolean found;

        found = run();

        if (found == true) {
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
        c.getStart().setState(2);
        c.getEnd().setState(3);

    }

    private void setHCosts() {

        for (int x = 0; x < c.getNumPerRow(); x++) {

            for (int y = 0; y < c.getNumPerRow(); y++) {
                c.getArr()[x][y].sethCost(hCost(c.getArr()[x][y], c.getEnd()));

            }
        }

    }

    private void evaluateNeighbors(Node n) {

        for (int x = n.getX() - 1; x < n.getX() + 2; x++) {

            for (int y = n.getY() - 1; y < n.getY() + 2; y++) {

                if (x >= 0 && x < c.getNumPerRow() && y >= 0 && y < c.getNumPerRow()) {

                    if (c.getArr()[x][y].getState() != 4 && c.getArr()[x][y].getState() != 1) {

                        open[numOpenItems] = c.getArr()[x][y];
                        c.getArr()[x][y].setParent(n);
                        c.getArr()[x][y].setgCost(gCost(c.getArr()[x][y], n));
                        c.getArr()[x][y].setfCost(fCost(c.getArr()[x][y]));

                        if (c.getArr()[x][y] != c.getStart() && c.getArr()[x][y] != c.getEnd() && c.getShowSteps() == true) {
                            time += c.getDelay();
                            PauseTransition pt = new PauseTransition();
                            pt.setDuration(Duration.millis(time));
                            final Node temp = c.getArr()[x][y];
                            pt.setOnFinished(new EventHandler<ActionEvent>() {

                                public void handle(ActionEvent e) {
                                    temp.getRect().setFill(ORANGE);
                                }

                            });
                            pt.play();
                        }

                        numOpenItems++;

                        c.getArr()[x][y].setState(4);
                    }
                }
            }

        }

    }

    private Node selectMovetoClosed() {
        int value = open[0].getfCost();
        int index = 0;

        for (int x = 0; x < numOpenItems; x++) {
            if (open[x].getfCost() < value) {
                index = x;
                value = open[x].getfCost();
            }
        }
        if (open[index] != c.getStart() && open[index] != c.getEnd() && c.getShowSteps() == true) {
            time += c.getDelay();
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.millis(time));
            final Node temp = open[index];
            pt.setOnFinished(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent e) {
                    temp.getRect().setFill(GREEN);
                }

            });
            pt.play();
        }
        closed[numClosedItems] = open[index];
        numClosedItems++;

        for (int x = index; x < numOpenItems; x++) {
            open[x] = open[x + 1];
        }
        numOpenItems--;
        return closed[numClosedItems - 1];
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

    private int hCost(Node n, Node exit) {
        return 10 * (Math.abs((n.getY() - exit.getY())) + Math.abs((n.getX() - exit.getX()))) + (14 - 2 * 10)
                * Math.min(Math.abs((n.getY() - exit.getY())), Math.abs((n.getX() - exit.getX())));
    }

    private int fCost(Node n) {
        return n.getgCost() + n.gethCost();
    }

    private boolean run() {

        c.getStart().setState(4);
        c.getStart().setgCost(0);
        open[0] = c.getStart();
        setHCosts();
        do {
            Node n = selectMovetoClosed();

            if (n == c.getEnd()) {
                return true;
            }

            evaluateNeighbors(n);

        } while (numOpenItems != 0);

        return false;
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

    public Control getC() {
        return c;
    }

    public void setC(Control c) {
        this.c = c;
    }

    public int getNumOpenItems() {
        return numOpenItems;
    }

    public void setNumOpenItems(int numOpenItems) {
        this.numOpenItems = numOpenItems;
    }

    public int getNumClosedItems() {
        return numClosedItems;
    }

    public void setNumClosedItems(int numClosedItems) {
        this.numClosedItems = numClosedItems;
    }

    public Node[] getOpen() {
        return open;
    }

    public void setOpen(Node[] open) {
        this.open = open;
    }

    public Node[] getClosed() {
        return closed;
    }

    public void setClosed(Node[] closed) {
        this.closed = closed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
