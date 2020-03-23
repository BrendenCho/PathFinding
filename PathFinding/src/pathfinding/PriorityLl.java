/*

 */
package pathfinding;

/**
 *
 * @author Brenden Cho
 */
public class PriorityLl implements LlInterface{
    private LlNode overflow;
    private LlNode head;
    private LlNode tail;
    private LlNode curr;
    private LlNode prev;
    int numItems = 0;

    public PriorityLl(){
    }
    
    
    public PriorityLl(Node n){
    head = new LlNode(n);
    tail = head;
 
    }
  
    public boolean addInPosition(LlNode l,LlNode addNode){
    
    if (l.getPointer() == null){    
     
    l.setPointer(addNode);
    return true;
    }     
    else if(addNode.getNode().getgCost() <= head.getNode().getgCost()){
    addNode.setPointer(head);
    head = addNode;    
    return true;
    }else if(addNode.getNode().getgCost() <= l.getNode().getgCost()){
    findCurr(head,l.getNode());
    prev.setPointer(addNode);
    addNode.setPointer(curr);
    return true;
    }else{
    return addInPosition(l.getPointer(),addNode);
    }
        
    
    }
    
    
    public void removeNode(Node n){
    
    findCurr(head,n);
    prev.setPointer(curr.getPointer());
    curr.setPointer(null);   
    addInPosition(head,curr);
    
    }
    
    public void findCurr(LlNode n, Node desired){
       try{
        overflow = n;
        if(n.getNode() == desired){
        curr = n;
        }else{
        prev = n;
        findCurr(n.getPointer(),desired);
        }
       }catch(StackOverflowError e){
       findCurr(n,desired);
       }
     }
    
    
    @Override
    public void add(LlNode node) {
       if(head != null){
        tail.setPointer(node);
        tail = node;
        numItems++;
       }else{
       head = node;
       tail = head;
       }
    }

    @Override
    public void remove() {
       head = head.getPointer();
       numItems --;
       
    }

    @Override
    public boolean isEmpty() {
      if(numItems == 0){
          return true;
      }else{
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
    
    public void print(LlNode n){
    
    if(n != null){
        System.out.print(n.getNode().getgCost()+"("+n.getNode().getX()+","+n.getNode().getY()+")"+" ");
        print(n.getPointer());
    }
    
    }

    public LlNode getOverflow() {
        return overflow;
    }

    public void setOverflow(LlNode overflow) {
        this.overflow = overflow;
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

    public LlNode getCurr() {
        return curr;
    }

    public void setCurr(LlNode curr) {
        this.curr = curr;
    }

    public LlNode getPrev() {
        return prev;
    }

    public void setPrev(LlNode prev) {
        this.prev = prev;
    }
    
      
}
