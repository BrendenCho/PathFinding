package pathfinding;

import java.util.Arrays;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.PURPLE;
import static javafx.scene.paint.Color.WHITE;
import javafx.util.Duration;

/**
 *
 * @author Brenden Cho
 */
public class Prim {

    private Random r = new Random();
    private Control c;
    private Node[] arr;
    private int numNodes;
    private int numArrIndex = 0;
    private int time = 0;

    public Prim(Control c) {
        this.c = c;

        numNodes = (((c.getNumPerRow() - 2) / 2) + 1) * (((c.getNumPerRow() - 2) / 2) + 1);
        arr = new Node[numNodes];

        c.setupMaze();

        c.fill();

        addNeighbors(c.getArr()[1][1]);
        c.getArr()[1][1].setState(0);
        c.getArr()[1][1].getRect().setFill(WHITE);

        connect(arr[0]);

        generate();
    }

    public void generate() {
        int rand;

        while (numArrIndex > 0) {
            rand = r.nextInt(numArrIndex);

            Node temp = arr[rand];
            addNeighbors(temp);

            connect(temp);

            delete(rand);

            numArrIndex--;

        }

    }

    public void addNeighbors(Node n) {
        n.setMazeVisited(true);

        if (n.getX() - 2 >= 0 && c.getArr()[n.getX() - 2][n.getY()].isMazeVisited() == false) {
            arr[numArrIndex] = c.getArr()[n.getX() - 2][n.getY()];
            numArrIndex++;

            if (c.getShowSteps() == true) {
                PauseTransition ptt = new PauseTransition();
                ptt.setDuration(Duration.millis(time));
                ptt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        c.getArr()[n.getX() - 2][n.getY()].getRect().setFill(WHITE);
                    }

                });
                time += c.getDelay();
                ptt.play();
            } else {
                c.getArr()[n.getX() - 2][n.getY()].getRect().setFill(WHITE);
            }

        }

        if (n.getY() - 2 >= 0 && c.getArr()[n.getX()][n.getY() - 2].isMazeVisited() == false) {
            arr[numArrIndex] = c.getArr()[n.getX()][n.getY() - 2];
            numArrIndex++;

            if (c.getShowSteps() == true) {
                PauseTransition ptt = new PauseTransition();
                ptt.setDuration(Duration.millis(time));
                ptt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        c.getArr()[n.getX()][n.getY() - 2].getRect().setFill(WHITE);
                    }

                });
                time += c.getDelay();
                ptt.play();
            } else {
                c.getArr()[n.getX()][n.getY() - 2].getRect().setFill(WHITE);
            }

        }

        if (n.getX() + 2 < c.getNumPerRow() && c.getArr()[n.getX() + 2][n.getY()].isMazeVisited() == false) {
            arr[numArrIndex] = c.getArr()[n.getX() + 2][n.getY()];
            numArrIndex++;

            if (c.getShowSteps() == true) {
                PauseTransition ptt = new PauseTransition();
                ptt.setDuration(Duration.millis(time));
                ptt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        c.getArr()[n.getX() + 2][n.getY()].getRect().setFill(WHITE);
                    }

                });
                time += c.getDelay();
                ptt.play();
            } else {
                c.getArr()[n.getX() + 2][n.getY()].getRect().setFill(WHITE);
            }

        }

        if (n.getY() + 2 < c.getNumPerRow() && c.getArr()[n.getX()][n.getY() + 2].isMazeVisited() == false) {
            arr[numArrIndex] = c.getArr()[n.getX()][n.getY() + 2];
            numArrIndex++;

            if (c.getShowSteps() == true) {
                PauseTransition ptt = new PauseTransition();
                ptt.setDuration(Duration.millis(time));
                ptt.setOnFinished(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        c.getArr()[n.getX()][n.getY() + 2].getRect().setFill(WHITE);
                    }

                });
                time += c.getDelay();
                ptt.play();
            } else {
                c.getArr()[n.getX()][n.getY() + 2].getRect().setFill(WHITE);
            }

        }

    }

    public void connect(Node n) {
        boolean done = false;
        int rand;
        PauseTransition ptt = new PauseTransition();
        ptt.setDuration(Duration.millis(time));
        ptt.setOnFinished(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                n.getRect().setFill(WHITE);
            }

        });
        while (done == false) {

            rand = r.nextInt(4);

            if (rand == 0) {
                if (n.getX() - 2 >= 0 && c.getArr()[n.getX() - 2][n.getY()].isMazeVisited() == true) {

                    c.getArr()[n.getX()][n.getY()].setState(0);
                    c.getArr()[n.getX()][n.getY()].setMazeVisited(true);

                    if (c.getShowSteps() == true) {

                        time += c.getDelay();
                        ptt.play();
                    } else {
                        n.getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX() - 1][n.getY()].setState(0);

                    if (c.getShowSteps() == true) {
                        PauseTransition pttt = new PauseTransition();
                        ptt.setDuration(Duration.millis(time));
                        ptt.setOnFinished(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent e) {
                                c.getArr()[n.getX() - 1][n.getY()].getRect().setFill(WHITE);
                            }

                        });
                        time += c.getDelay();
                        pttt.play();
                    } else {
                        c.getArr()[n.getX() - 1][n.getY()].getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX() - 1][n.getY()].setMazeVisited(true);
                    done = true;

                }
            } else if (rand == 1) {
                if (n.getY() - 2 >= 0 && c.getArr()[n.getX()][n.getY() - 2].isMazeVisited() == true) {
                    c.getArr()[n.getX()][n.getY()].setState(0);

                    if (c.getShowSteps() == true) {

                        time += c.getDelay();
                        ptt.play();
                    } else {
                        n.getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX()][n.getY()].setMazeVisited(true);
                    c.getArr()[n.getX()][n.getY() - 1].setState(0);

                    if (c.getShowSteps() == true) {
                        PauseTransition pttt = new PauseTransition();
                        ptt.setDuration(Duration.millis(time));
                        ptt.setOnFinished(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent e) {
                                c.getArr()[n.getX()][n.getY() - 1].getRect().setFill(WHITE);
                            }

                        });
                        time += c.getDelay();
                        pttt.play();
                    } else {
                        c.getArr()[n.getX()][n.getY() - 1].getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX()][n.getY() - 1].setMazeVisited(true);
                    done = true;

                }
            } else if (rand == 2) {
                if (n.getX() + 2 < c.getNumPerRow() && c.getArr()[n.getX() + 2][n.getY()].isMazeVisited() == true) {
                    c.getArr()[n.getX()][n.getY()].setState(0);

                    if (c.getShowSteps() == true) {

                        time += c.getDelay();
                        ptt.play();
                    } else {
                        n.getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX()][n.getY()].setMazeVisited(true);
                    c.getArr()[n.getX() + 1][n.getY()].setState(0);

                    if (c.getShowSteps() == true) {
                        PauseTransition pttt = new PauseTransition();
                        ptt.setDuration(Duration.millis(time));
                        ptt.setOnFinished(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent e) {
                                c.getArr()[n.getX() + 1][n.getY()].getRect().setFill(WHITE);
                            }

                        });
                        time += c.getDelay();
                        pttt.play();
                    } else {
                        c.getArr()[n.getX() + 1][n.getY()].getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX() + 1][n.getY()].setMazeVisited(true);
                    done = true;
                }

            } else {
                if (n.getY() + 2 < c.getNumPerRow() && c.getArr()[n.getX()][n.getY() + 2].isMazeVisited() == true) {
                    c.getArr()[n.getX()][n.getY()].setState(0);

                    if (c.getShowSteps() == true) {

                        time += c.getDelay();
                        ptt.play();
                    } else {
                        n.getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX()][n.getY()].setMazeVisited(true);
                    c.getArr()[n.getX()][n.getY() + 1].setState(0);

                    if (c.getShowSteps() == true) {
                        PauseTransition pttt = new PauseTransition();
                        ptt.setDuration(Duration.millis(time));
                        ptt.setOnFinished(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent e) {
                                c.getArr()[n.getX()][n.getY() + 1].getRect().setFill(WHITE);
                            }

                        });
                        time += c.getDelay();
                        pttt.play();
                    } else {
                        c.getArr()[n.getX()][n.getY() + 1].getRect().setFill(WHITE);
                    }

                    c.getArr()[n.getX()][n.getY() + 1].setMazeVisited(true);
                    done = true;
                }

            }

        }

    }

    public void delete(int r) {
        for (int x = r; x < numArrIndex; x++) {
            arr[x] = arr[x + 1];
        }
    }

}
