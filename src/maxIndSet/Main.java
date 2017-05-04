package maxIndSet;

import maxIndSet.Graph.Graph;
import maxIndSet.Graph.RandomGraphGenerator;
import maxIndSet.Graph.Vertex;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Graph g = new Graph();
        Scanner sc = new Scanner(System.in);
        if (args.length > 0) {
            g.getDataFromFile(args[0]);
        } else {
            getGraphFromUser(g, sc);
        }
        while (true) {
            System.out.println("1 - use recursive algorithm on current graph");
            System.out.println("2 - use backtracking algorithm on current graph");
            System.out.println("3 - test recursive algorithm on random graphs");
            System.out.println("4 - test backtracking algorithm on random graphs");
            System.out.println("0 - exit");
            System.out.println("Enter your choice:");
            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println(MaxIndSetFinder.maxSetRecursive(g));
                        break;
                    case 2:
                        List<String> set = MaxIndSetFinder.maxSetBacktracking(g);
                        System.out.println(set);
                        System.out.println("Size: " + set.size());
                        break;
                    case 3:
                        System.out.println("Enter number of repeats:");
                        int repeats = Integer.parseInt(sc.nextLine());

                        System.out.println("Testing recursive algorithm on " + repeats + " random graphs with 25 vertices and 0 - 60 edges");
                        testAlgorithms(15, 15, 0, 60, repeats, true);
                        System.out.println();
                        System.out.println("Testing recursive algorithm on " + repeats + " random graphs with 7 - 30 vertices and 20 edges");
                        testAlgorithms(7, 30, 20, 20, repeats, true);
                        break;
                    case 4:
                        System.out.println("Enter number of repeats:");
                        repeats = Integer.parseInt(sc.nextLine());

                        System.out.println("Testing backtracking algorithm on " + repeats + " random graphs with 15 vertices and 0 - 60 edges");
                        testAlgorithms(15, 15, 0, 60, repeats, false);
                        System.out.println();
                        System.out.println("Testing backtracking algorithm on " + repeats + " random graphs with 7 - 20 vertices and 20 edges");
                        testAlgorithms(7, 20, 20, 20, repeats, false);
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Incorrect input");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input");
            }
        }
    }

    public static void getGraphFromUser(Graph g, Scanner sc) {

        System.out.println("Enter number of vertices:");
        int vertexCount = Integer.parseInt(sc.nextLine());
        System.out.println("Enter " + vertexCount + " vertex names, each name in new line:");
        while (vertexCount > 0) {
            g.addVertex(new Vertex(sc.nextLine()));
            vertexCount--;
        }

        System.out.println("Enter number of edges:");
        int edgeCount = Integer.parseInt(sc.nextLine());
        System.out.println("Enter " + edgeCount + " edges in form of: Vertex1 Vertex2");
        while (edgeCount > 0) {
            String[] input = sc.nextLine().split(" ");
            g.getVertex(input[0]).addEdge(g.getVertex(input[1]));
            g.getVertex(input[1]).addEdge(g.getVertex(input[0]));
            edgeCount--;
        }
    }

    private static void testAlgorithms(int vertexMin, int vertexMax, int edgeMin, int edgeMax, int repeats, boolean recursive) {

        for (int vertexCount = vertexMin; vertexCount <= vertexMax; ++vertexCount) {
            for (int edgeCount = edgeMin; edgeCount <= edgeMax; ++edgeCount) {
                double timeSum = 0;
                for (int i = 0; i < repeats; ++i) {

                    Graph g = RandomGraphGenerator.generate(vertexCount, edgeCount);
                    long startTime = System.currentTimeMillis();

                    if (recursive) {
                        MaxIndSetFinder.maxSetRecursive(g);
                    }
                    else {
                        MaxIndSetFinder.maxSetBacktracking(g);
                    }
                    timeSum += (System.currentTimeMillis() - startTime);
                }
                System.out.println("Vertices: " + vertexCount + " edges: " + edgeCount + " average miliseconds: " + timeSum / repeats);
            }
        }
    }

}
