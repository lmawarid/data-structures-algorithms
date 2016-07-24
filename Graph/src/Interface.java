import java.util.Scanner;
import java.util.List;
import java.util.Random;

public class Interface {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random randGen = new Random();
		System.out.println("Welcome to the graph module!");
		System.out.println("Type \"create D\" to initialize a directed graph!");
		System.out.println("Or, just type \"create\" to initialize an undirected graph!");
		String cmd;
		Graph myGraph = null;

		do {
			cmd = in.nextLine();
			
			if (cmd.contains("create")) {
				if (myGraph != null) {
					System.out.println("You already have a graph!");
				} else if (cmd.split(" ").length > 1 && 
						   cmd.split(" ")[1].compareTo("D") == 0) {
					myGraph = new Graph(true);
					System.out.println("Your directed graph has been created!");
				} else {
					myGraph = new Graph(false);
					System.out.println("Your undirected graph has been created!");
				}
			} else if (myGraph == null) {
				System.out.println("Please \"create\" your graph first!");
			} else {
				if (cmd.compareTo("ct") == 0) {
					myGraph.setDirected(!myGraph.isDirected());
					if (myGraph.getSize() == 0) {
						System.out.println("Your graph type has been changed!");
					}
				} else if (cmd.contains("contains")) {
					String item = cmd.split(" ")[1];
					System.out.println("Graph contains " + item + ": " 
							+ myGraph.contains(item));
				} else if (cmd.contains("addv")) {
					String item = cmd.split(" ")[1];
					myGraph.addVertex(item);
					System.out.println(item + " has been added!");
				} else if (cmd.contains("adde")) {
					String pointA = cmd.split(" ")[1];
					String pointB = cmd.split(" ")[2];
					myGraph.addEdge(pointA, pointB);
					System.out.println("An edge has been added between " 
							+ pointA + " and " + pointB + "!");
				} else if (cmd.compareTo("empty") == 0) {
					System.out.println("Is graph empty? " + myGraph.isEmpty());
				} else if (cmd.contains("remv")) {
					String item = cmd.split(" ")[1];
					myGraph.removeVertex(item);
					System.out.println(item + " has been removed!");
				} else if (cmd.contains("reme")) {
					String pointA = cmd.split(" ")[1];
					String pointB = cmd.split(" ")[2];
					myGraph.removeEdge(pointA, pointB);
					System.out.println("An edge has been removed between " 
							+ pointA + " and " + pointB + "!");
				} else if (cmd.contains("nb")) {
					String item = cmd.split(" ")[1];
					List<Vertex> neighbors = myGraph.neighbors(item);
					System.out.println("Here are " + item + "'s neighbors:");
					myGraph.printVertices(neighbors);
				} else if (cmd.compareTo("size") == 0) {
					System.out.println("This graph has " + myGraph.getSize()
							+ " vertices and " + myGraph.getNumEdges() 
							+ " edges!");
				} else if (cmd.contains("contains")) {
					String item = cmd.split(" ")[1];
					System.out.println("Graph contains " + item + ": " 
							+ myGraph.contains(item));
				} else if (cmd.contains("merge")) {
					String item1 = cmd.split(" ")[1];
					String item2 = cmd.split(" ")[2];
					myGraph.mergeVertex(item1, item2);
					System.out.println("Merged vertices " + item1 + " and " 
							+ item2 + "!");
				} else if (cmd.compareTo("contract") == 0) {
					myGraph.contract();
					System.out.println("Here's the contracted graph!");
					myGraph.printGraph();
				} else if (cmd.compareTo("print") == 0) {
					System.out.println("Here's your graph!");
					System.out.println("VERTICES = " + myGraph.getSize());
					myGraph.printVertices();
					System.out.println("EDGES = " + myGraph.getNumEdges());
					myGraph.printEdges();
					System.out.println("GRAPH:");
					myGraph.printGraph();
				} else if (cmd.compareTo("test1") == 0) {
					myGraph.addVertex("A");
					myGraph.addVertex("B");
					myGraph.addVertex("C");
					myGraph.addVertex("D");
					myGraph.addVertex("E");
					myGraph.addVertex("F");
					myGraph.addEdge("A", "B");
					myGraph.addEdge("C", "B");
					myGraph.addEdge("B", "E");
					myGraph.addEdge("E", "D");
					myGraph.addEdge("E", "F");
					System.out.println("Test graph created.");
				} else if (cmd.compareTo("test2") == 0) {
					myGraph.addVertex("A");
					myGraph.addVertex("B");
					myGraph.addVertex("C");
					myGraph.addVertex("D");
					myGraph.addVertex("E");
					myGraph.addVertex("F");
					myGraph.addVertex("G");
					myGraph.addVertex("H");
					myGraph.addVertex("I");
					myGraph.addEdge("I", "G");
					myGraph.addEdge("G", "H");
					myGraph.addEdge("H", "I");
					myGraph.addEdge("F", "I");
					myGraph.addEdge("F", "A");
					myGraph.addEdge("A", "E");
					myGraph.addEdge("E", "F");
					myGraph.addEdge("D", "E");
					myGraph.addEdge("D", "B");
					myGraph.addEdge("B", "C");
					myGraph.addEdge("C", "D");
					System.out.println("Test graph created.");
				} else if (cmd.compareTo("reset") == 0) {
					myGraph.reset();
					System.out.println("The graph has been reset.");
				} else if (cmd.contains("BFS")) {
					String vertex = cmd.split(" ")[1];
					myGraph.BFS(vertex);
				} else if (cmd.contains("DFS")) {
					String vertex = cmd.split(" ")[1];
					myGraph.DFS(vertex);
				} else if (cmd.contains("SCC")) {
					myGraph.findSCC();
				} else if (cmd.contains("DSP")) {
					String src = cmd.split(" ")[1];
					myGraph.Dijkstra(src);
				}
					else if (cmd.compareTo("reverse") == 0) {
					myGraph.reverseGraph();
					System.out.println("Your graph has been reversed!");
				} else if (cmd.compareTo("random") == 0) {
					for (int i = 1; i <= 200; ++i) {
						myGraph.addVertex(i);
					}
					for (int j = 1; j <= 200; ++j) {
						int rand = randGen.nextInt(21);
						for (int k = 1; k <= rand; ++k) {
							int vrand = randGen.nextInt(211);
							myGraph.addEdge(j, vrand);
						}
					}
					System.out.println("Your random graph has been generated!");
				} else if (cmd.compareTo("quit") == 0) {
					break;
				} else {
					System.out.println("Sorry, invalid command!");
				}
			}
		} while (in.hasNextLine());
		
		in.close();
		System.out.println("Goodbye!");
	}
}
