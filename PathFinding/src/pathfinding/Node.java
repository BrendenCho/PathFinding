package pathfinding;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Brenden Cho
 */
public class Node {

    private Rectangle rect;
    private int state;
    private int x;
    private int y;
    private Node parent;
    //a*
    private int gCost;
    private int hCost;
    private int fCost;
    //Depth first
    private boolean visited = false;
    //Maze
    private boolean mazeVisited = false;

    public Node() {

    }

    public Node(Rectangle rect, int state, int x, int y) {
        this.rect = rect;
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isMazeVisited() {
        return mazeVisited;
    }

    public void setMazeVisited(boolean mazeVisited) {
        this.mazeVisited = mazeVisited;
    }

}
