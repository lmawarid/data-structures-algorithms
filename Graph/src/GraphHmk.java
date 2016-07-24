import java.util.Scanner;

public class GraphHmk {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Graph myGraph = new Graph(false);
		String line;
		
		do {
			line = in.nextLine();
			String[] parsedLine = line.split("\t");
			int len = parsedLine.length;
			
			int vertex = Integer.parseInt(parsedLine[0]);
			myGraph.addVertex(vertex);
			
			for (int i = 1; i < len; ++i) {
				int neighbor = Integer.parseInt(parsedLine[i]);
				if (!myGraph.contains(neighbor)) {
					myGraph.addVertex(neighbor);
				}
				myGraph.addEdge(vertex, neighbor);
			}
		} while (in.hasNextInt());
		
		myGraph.contract();
		System.out.println("GRAPH: ");
		System.out.println("VERTICES: " + myGraph.getSize());
		System.out.println("EDGES: " + myGraph.getNumEdges());
		
		in.close();
	}
}
