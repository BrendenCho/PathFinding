package pathfinding;

/**
 *
 * @author Brenden Cho
 */
public class LlNode {

    private Node node;
    private LlNode pointer;

    LlNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public LlNode getPointer() {
        return pointer;
    }

    public void setPointer(LlNode pointer) {
        this.pointer = pointer;
    }

}
