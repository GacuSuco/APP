package GrafenPaden;

import ListsStacksQueues.SingleLinkedList;


// also called nodes
public class Vertex {
    public String name;
    public SingleLinkedList<Edge> adj;
    public double dist;
    public Vertex prev;
    public int scratch;

    public Vertex (String nm){
        name = nm;
        adj = new SingleLinkedList<Edge>();
        reset();
    }

    public void reset() {
        dist = Graph.INFINITY;
        prev = null;
        scratch = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        for (int i = 0; i < adj.ListSize(); i++) {
            stringBuilder.append(" to ").append(adj.get(i).dest.name).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
