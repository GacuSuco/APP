package ListsStacksQueues;
import java.util.EmptyStackException;

public class LinkedListStack<T>  {
    private SingleLinkedList<T> items;

    private int TOPOFSTACK;

    public LinkedListStack(){
        items = new SingleLinkedList<T>();
        TOPOFSTACK = -1;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    public T pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        T restult = items.get(0);
        items.delete(0);
        this.TOPOFSTACK--;
        return restult;
    }
    public T top(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return items.get(TOPOFSTACK);
    }
    public void push(T x){
        items.insert(TOPOFSTACK, x);
        this.TOPOFSTACK++;
    }

    public int getSize(){
        return TOPOFSTACK;
    }

    @Override
    public String toString() {
        return items.toString();
    }

}
