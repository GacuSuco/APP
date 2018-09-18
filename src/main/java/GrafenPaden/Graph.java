package GrafenPaden;

import ListsStacksQueues.ArrayList;
import ListsStacksQueues.LinkedListQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Graph {
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

    public void addEdge(String SourceName, String destName, double cost){
        Vertex v = getVertex( SourceName);
        Vertex w = getVertex(destName);
        v.adj.addFirst(new Edge(w, cost));
    }
    private Vertex getVertex( String vertexName ){
        Vertex v = vertexMap.get(vertexName);
        if(v == null){
            v = new Vertex(vertexName);
            vertexMap.put(vertexName,v);
        }
        return v;
    }

    public void unweighted(String startName){
        clearAll();
        Vertex start = vertexMap.get(startName);
        if(start == null){
            throw new NoSuchElementException();
        }
        LinkedListQueue<Vertex> q = new LinkedListQueue<Vertex>();
        q.enqueue(start);
        start.dist = 0;

        while (!q.isEmpty()){
            Vertex v = q.dequeue();

            for (int i = 0; i < v.adj.ListSize(); i++) {
                Vertex w = v.adj.get(i).dest;
                if (w.dist == INFINITY){
                    w.dist = v.dist +1;
                    w.prev = v;
                    q.enqueue(w);
                }
            }
        }

    }
    public void dijkstra(String startName){
        PriorityQueue<Path> pq = new PriorityQueue<Path>();

        Vertex start = vertexMap.get(startName);
        if(start == null) {
            throw new NoSuchElementException();
        }
        clearAll();
        pq.add(new Path(start, 0));
        start.dist = 0;

        int nodeSeen =0;
        while(!pq.isEmpty() && nodeSeen < vertexMap.size()){
            Path vrec = pq.remove();
            Vertex v = vrec.dest;
            if(v.scratch != 0){
                continue;
            }
            v.scratch = 1;
            nodeSeen++;

            for (int i = 0; i < v.adj.ListSize(); i++) {
                Vertex w = v.adj.get(i).dest;
                double cvw = v.adj.get(i).cost;

                if(cvw < 0){
                    throw new NullPointerException();
                }
                if(w.dist > v.dist + cvw){
                    w.dist = v.dist + cvw;
                    w.prev = v;
                    pq.add(new Path(w,w.dist));
                }
            }
        }

    }

    public boolean isConnected(String startName){
        for(String key : vertexMap.keySet()) {
            dijkstra(key);

            for (Vertex v : vertexMap.values()) {
                if(v.scratch != 1) return false;
            }
        }
        return true;
    }



    private void printPath( Vertex dest ){
        if(dest.prev != null){
            printPath(dest.prev);
            System.out.println(" to ");
        }
        System.out.println(dest.name);
    }
    private void clearAll( ){
        for (Vertex v :vertexMap.values()) {
            v.reset();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Vertex v :vertexMap.values()) {
            stringBuilder.append(v.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
