package ListsStacksQueues;

import ListsStacksQueues.ListNodes.SingleListNode;

public class LinkedListQueue<T> {
    private SingleListNode<T> front;
    private SingleListNode<T> back;

    public LinkedListQueue(){
        front = back = null;
    }

    public boolean isEmpty() {
        return front == null;
    }


    public void enqueue(T x){
        if(isEmpty()){
            back = front = new SingleListNode<T>(x,null);
        }else{
            back = back.next = new SingleListNode<T>(x, null);
        }
    }

    public T dequeue(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        T returnValue = front.element;
        front = front.next;
        return returnValue;
    }
    public T getFront(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return front.element;
    }

    private void makeEmpty() {
        front = null;
        back = null;
    }


    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        SingleListNode<T> tmp = front.next;

        while (tmp != null) {
            result.append(tmp.element + " ");
            tmp = tmp.next;
        }
        return result.toString();
    }
}


