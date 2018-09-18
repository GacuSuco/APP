package ListsStacksQueues.ListNodes;

public class DoubleListNode<T> {
    public T element;
    public DoubleListNode<T> prev;
    public DoubleListNode<T> next;


    public DoubleListNode(){
        this.element = null;
        this.prev = null;
        this.next = null;
    }
    public DoubleListNode(T element, DoubleListNode<T> prev,DoubleListNode<T> next){
        this.element = element;
        this.prev = prev;
        this.next = next;
    }
}
