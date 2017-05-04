package maxIndSet.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Lukas
 */

public class Graph{

    private ArrayList<Vertex> vertices = new ArrayList<>();
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(String vertexName) {
        for (Vertex v : vertices) {
            if (v.getName().equals(vertexName))
                return v;
        }
        return null;
    }

    public void addVertex(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public boolean hasEdge(String v1, String v2){
        for(Vertex v : vertices){
            for(Edge e : v.getEdges()){
                if((e.getFirst().getName().equals(v1) && e.getSecond().getName().equals(v2)))
                    return true;
            }
        }
        return false;
    }

    public void removeVertex(Vertex vertex) {

        for(int i = 0; i < vertices.size(); ++i){
            if(vertices.get(i).getName().equals(vertex.getName())){
                vertices.remove(i);
            }
        }

        for (Vertex v : vertices) {
            for (int i = 0; i < v.getEdges().size(); ++i) {
                if (v.getEdges().get(i).getSecond().getName().equals(vertex.getName())) {
                    v.getEdges().remove(i);
                }
            }
        }
    }

    public void removeWithNeighbours(Vertex vertex) {
        for(int i = 0; i < vertices.size(); ++i){
            if(vertices.get(i).getName().equals(vertex.getName())){
                vertices.remove(i);
            }
        }

        ArrayList<Vertex> toRemove = new ArrayList<>();

        for (Vertex v : vertices) {
            for (Edge e : v.getEdges()) {
                if (e.getSecond().getName().equals(vertex.getName())) {
                    toRemove.add(e.getFirst());
                }
            }
        }
        for (Vertex v : toRemove) {
            removeVertex(v);
        }
    }

    public void printVertices() {
        vertices.forEach(System.out::println);
    }

    public void printEdges() {
        for (Vertex v : this.vertices) {
            for (Edge e : v.getEdges()) {
                System.out.println(v.getName() + "---->" + e.getSecond().getName());
            }
        }
    }

    public int edgeCount() {
        return vertices.stream().mapToInt(v -> v.getEdges().size()).sum()/2;
    }

    public void clear() {
        vertices.forEach(v -> v.getEdges().clear());
        vertices.clear();
    }

    public Graph createCopy(){
        Graph g = new Graph();

        vertices.forEach(v -> g.vertices.add(new Vertex(v.getName())));
        for(Vertex v : vertices){
            for(Edge e : v.getEdges()){
                g.getVertex(v.getName()).getEdges().add(new Edge(g.getVertex(v.getName()), g.getVertex(e.getSecond().getName())));
            }
        }
        return g;
    }

    /**
     * Reads graph from file (saved as adjacency lists)
     * @param dataFile
     */
    public void getDataFromFile(String dataFile){
        // Vertex adjacency lists
        ArrayList<ArrayList<String>> adjacencyLists = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(dataFile))){
            String line;

            while((line = br.readLine()) != null){
                String[] vertices = line.split(" ");
                ArrayList<String> temp = new ArrayList<>();

                temp.addAll(Arrays.asList(vertices));
                adjacencyLists.add(temp);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Adding each vertex to graph by taking first vertex of every adjacency list
        for (ArrayList<String> vertices : adjacencyLists) {
            this.getVertices().add(new Vertex(vertices.get(0)));
        }

        for(int i = 0; i < adjacencyLists.size(); ++i){

            Vertex v = this.getVertex(adjacencyLists.get(i).get(0));
            if(v == null)
                throw new IllegalArgumentException("Vertex " + v.getName() + " was not found in graph");

            //Connecting vertex with other vertices it is adjacent with(specified in file)
            for(int j = 1; j < adjacencyLists.get(i).size(); ++j){

                if(this.getVertex(adjacencyLists.get(i).get(j)) == null)
                    throw new IllegalArgumentException("Vertex " + adjacencyLists.get(i).get(j) + " was not found in graph");

                v.addEdge(this.getVertex(adjacencyLists.get(i).get(j)));
            }
        }
    }

}
