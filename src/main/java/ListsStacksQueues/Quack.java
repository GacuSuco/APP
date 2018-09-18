package ListsStacksQueues;

import ListsStacksQueues.ListNodes.DoubleListNode;

import java.util.EmptyStackException;

public class Quack<T> {
    private DoubleListNode<T> front;
    private DoubleListNode<T> back;

    private int TOPOFSTACK;

    public Quack (){
        front = back = null;
        TOPOFSTACK = 0;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    public int getSize() {
        return TOPOFSTACK;
    }
    public void enqueue(T x) {
        if(isEmpty()){
            back = front = new DoubleListNode<T>(x,null,null);
        }else{
            back = back.next = new DoubleListNode<T>(x, back,null);
        }
        TOPOFSTACK++;
    }

    public T dequeue(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        T returnValue = front.element;
        front = front.prev;
        TOPOFSTACK--;
        return returnValue;
    }

    public T pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        T returnValue = back.element;
        back = back.next;
        TOPOFSTACK--;
        return returnValue;
    }
    public void push(T x) {
        if(isEmpty()){
            back = front = new DoubleListNode<T>(x,null,null);
        }else{
            front = front.prev = new DoubleListNode<T>(x, null,front);
        }
        this.TOPOFSTACK++;
    }

    public T top(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return back.element;
    }


    public T getFront(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return front.element;
    }
}



////
//public class Quack<T> {
//    HANLinkedList<T> list;
//    public Quack(){
//        list = new HANLinkedList<T>();
//    }
//
//    public T Pop(){
//        T item = list.Get(0);
//        list.RemoveFirst();
//        return item;
//    }
//
//    public T Top(){
//        return list.Get(0);
//    }
//
//    public void Push(T value){
//        list.AddFirst(value);
//    }
//
//    public int GetSize(){
//        return list.Count;
//    }
//
//    public String toString(){
//        return list.toString();
//    }
//
//    public T Dequeue(){
//        T temp =  list.Get(list.Count);
//        list.Delete(list.Count);
//        return temp;
//    }
//}