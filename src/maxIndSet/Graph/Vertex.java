package maxIndSet.Graph;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Lukas
 */
public class Vertex implements Serializable {

    private String name;

    private ArrayList<Edge> edges = new ArrayList<>();

    public Vertex(String name) {
        this.name = name;
    }

    public void addEdge(Vertex v2) {
        Edge newEdge = new Edge(this, v2);

        if(!edges.contains(newEdge)){
            edges.add(newEdge);
        }
    }

    public int getDegree(){
        return this.edges.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean adjacent(Vertex v2){
        return this.edges.stream().anyMatch( edge -> edge.getSecond().equals(v2));
    }

    public boolean adjacent(String v2){
        return this.edges.stream().anyMatch( edge -> edge.getSecond().getName().equals(v2));
    }

    @Override
    public String toString() {
        StringBuilder connections = new StringBuilder();
        for (Edge e : edges) {
            connections.append(e.getSecond().getName()).append(" ");
        }
        return "Vertex: " + this.name + " connections: " + connections.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (!getName().equals(vertex.getName())) return false;
        return getEdges().equals(vertex.getEdges());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getEdges().hashCode();
        return result;
    }
}