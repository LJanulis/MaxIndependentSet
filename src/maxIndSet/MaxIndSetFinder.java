package maxIndSet;

import maxIndSet.Graph.Graph;
import maxIndSet.Graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas
 */
public class MaxIndSetFinder {

    public static int maxSetRecursive(Graph g) {
        if (g.edgeCount() == 0)
            return g.getVertices().size();

        for (Vertex v : g.getVertices()) {
            if (v.getEdges().size() > 0) {

                Graph g1 = g.createCopy();
                g1.removeVertex(v);
                int n1 = maxSetRecursive(g1);

                Graph g2 = g.createCopy();
                g2.removeWithNeighbours(v);
                int n2 = maxSetRecursive(g2);

                return Math.max(n1, 1 + n2);
            }
        }
        return 0;
    }


    private static List<String> maxIndependentSet = new ArrayList<>();

    public static List<String> maxSetBacktracking(Graph g) {
        maxIndependentSet.clear();
        findMSBacktracking(g.getVertices(), 0, new ArrayList<>());
        return maxIndependentSet;
    }

    private static void findMSBacktracking(List<Vertex> vertices, int start, List<String> currSet) {
        for (int i = start; i < vertices.size(); ++i) {
            Vertex v = vertices.get(i);

            if (!currSet.contains(v.getName())) {
                boolean independent = true;
                for (String s : currSet) {
                    if (v.adjacent(s))
                        independent = false;
                }
                if (independent) {
                    currSet.add(v.getName());

                    if (currSet.size() > maxIndependentSet.size())
                        maxIndependentSet = new ArrayList<>(currSet);

                    findMSBacktracking(vertices, i, currSet);
                    currSet.remove(currSet.size() - 1);
                }
            }
        }
    }
}
