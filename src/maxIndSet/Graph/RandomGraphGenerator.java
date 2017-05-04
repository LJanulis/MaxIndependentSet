package maxIndSet.Graph;

import java.util.Random;

/**
 * @author Lukas
 */
public class RandomGraphGenerator {

    public static Graph generate(int vertices, int edges) {

        Graph g = new Graph();
        g.addVertex(new Vertex("1"));

        Random rand = new Random();

        //adding vertices to graph
        for (int i = 2; i <= vertices; ++i) {
            Vertex v = new Vertex(Integer.toString(i));
            g.addVertex(v);
        }

        int maxEdges = vertices*(vertices-1)/2;

        for (int i = 0; i < edges; ++i) {

            //if graph has maximum possible amount of edges, return it
            if(g.edgeCount() == maxEdges)
                return g;

            //selecting two random vertices from graph
            Vertex v1 = g.getVertex(Integer.toString(rand.nextInt(g.getVertices().size()) + 1));
            Vertex v2 = g.getVertex(Integer.toString(rand.nextInt(g.getVertices().size()) + 1));

            //if vertices are not the same and they dont share common edge, add edge, else try again
            if (v1 != v2 && !g.hasEdge(v1.getName(), v2.getName())) {
                g.getVertex(v1.getName()).addEdge(g.getVertex(v2.getName()));
                g.getVertex(v2.getName()).addEdge(g.getVertex(v1.getName()));
            } else {
                edges++;
            }
        }
        return g;
    }
}
