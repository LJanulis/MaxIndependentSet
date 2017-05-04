package maxIndSet.Graph;

import java.io.Serializable;

/**
 * @author Lukas
 */
public class Edge implements Serializable {

    private Vertex v1;
    private Vertex v2;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getFirst() {
        return this.v1;
    }

    public Vertex getSecond() {
        return this.v2;
    }

    public String toString() {
        return "First: " + v1.getName() + " second: " + v2.getName();
    }

    public void setFirst(Vertex v1) {
        this.v1 = v1;
    }

    public void setSecond(Vertex v2) {
        this.v2 = v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if(v1.getName().equals(edge.getFirst().getName()) && v2.getName().equals(edge.getSecond().getName())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        return result;
    }
}