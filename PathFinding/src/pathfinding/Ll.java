package pathfinding;

/**
 *
 * @author Brenden Cho
 */
public class Ll implements LlInterface {

    private int numItems = 0;
    private LlNode head;
    private LlNode tail;

    public Ll(Node n) {
        LlNode ln = new LlNode(n);
        head = ln;
        tail = ln;

    }

    @Override
    public void add(LlNode node) {
        if (tail != null) {
            tail.setPointer(node);
            tail = tail.getPointer();
            numItems++;
        }
    }

    @Override
    public void remove() {
        head = head.getPointer();
        numItems--;
    }

    @Override
    public boolean isEmpty() {
        if (numItems == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Node getNode() {
        return head.getNode();
    }

    @Override
    public int getNumItems() {
        return numItems;
    }

    public LlNode getHead() {
        return head;
    }

    public void setHead(LlNode head) {
        this.head = head;
    }

    public LlNode getTail() {
        return tail;
    }

    public void setTail(LlNode tail) {
        this.tail = tail;
    }

}
