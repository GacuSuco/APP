package ListsStacksQueues.ListNodes;

public class SingleListNode<T> {
    public T element;
    public SingleListNode<T> next;

    public SingleListNode(){
      this.element = null;
      this.next = null;
    }
    public SingleListNode(T element, SingleListNode<T> next){
        this.element = element;
        this.next = next;
    }
}
