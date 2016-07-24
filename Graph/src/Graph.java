import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// TODO: The addEdge mechanism does not work as well now that we have
// introduced the directed property. In particular, if we add an edge from A to B,
// A will have B in its list of neighbors, but B will NOT have A in its
// list of neighbors. This leads to problems where removing B from the
// graph with the current removeEdge implementation will leave a dangling 
// edge from A to B, which interferes with the contraction algorithm.

public class Graph {
	private int size;
	private int numEdges;
	private boolean directed;
	
	private List<Vertex> graphVertices;
	private List<Edge> graphEdges;
	
	// Constructor
	
	public Graph(boolean directed) {
		size = 0;
		numEdges = 0;
		this.directed = directed;
		graphVertices = new ArrayList<Vertex>();
		graphEdges = new ArrayList<Edge>();
	}
	
	// Resets an existing graph
	
	public void reset() {
		setSize(0);
		setNumEdges(0);
		graphVertices.clear();
		graphEdges.clear();
	}
	
	private void resetVertices() {
		for (Vertex vertex : this.graphVertices) {
			vertex.setExplored(false);
			vertex.setLabel(0);
		}
	}
	
	
	public void reverseGraph() {
		if (!directed) return;
		
		for (Edge edge : graphEdges) {
			Vertex pointA = edge.getPointA();
			Vertex pointB = edge.getPointB();
			
			edge.setPointB(pointA);
			edge.setPointA(pointB);
			
			pointA.removeNeighbor(pointB);
			pointB.addNeighbor(pointA);
		}
	}
	
	// Standard getters
	
	public int getSize() {
		return size;
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public boolean isDirected() {
		return directed;
	}
	
	public List<Vertex> getVertices() {
		return graphVertices;
	}
	
	public List<Edge> getEdges() {
		return graphEdges;
	}

	// Standard setters - the ones that set lists will clear the existing list
	// and replace it with the given list
	
	private void setSize(int newSize) {
		this.size = newSize;
	}

	private void setNumEdges(int newEdges) {
		this.numEdges = newEdges;
	}
	
	public void setDirected(boolean directed) {
		if (this.size != 0) {
			System.out.println("You can only change this property while "
					+ "the graph is empty!");
			return;
		}
		
		this.directed = directed;
	}
	
	/*
	not sure if we need these right now
	
	private void setVertices(List<Vertex> newVertices) {
		graphVertices.clear();
		
		for (Vertex vertex : newVertices) {
			graphVertices.add(vertex);
		}
	}
	
	private void setEdges(List<Edge> newEdges) {
		graphEdges.clear();
		
		for (Edge edge : newEdges) {
			graphEdges.add(edge);
		}
	}
	*/
	
	// Checks if the graph is empty
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	// Checks if the graph contains a Vertex with the specified item
	
	public boolean contains(Object item) {
		for (Vertex vertex : graphVertices) {
			if (vertex.getItem().equals(item)) return true;
		}
		
		return false;
	}
	
	// Returns a Vertex with the specified item, or null if it does
	// not exist
	
	public Vertex getVertex(Object item) {
		for (Vertex vertex : graphVertices) {
			if (vertex.getItem().equals(item)) return vertex;
		}
		
		return null;
	}
	
	// Checks if two Vertices (given as their Objects) are adjacent. If there
	// is no Vertex containing either of the objects, it returns false
	
	public boolean isAdjacent(Object item1, Object item2) {
		if (!(this.contains(item1) || this.contains(item2))) return false;
		
		Vertex vertex1 = getVertex(item1);
		Vertex vertex2 = getVertex(item2);
		
		return vertex1.isNeighbor(vertex2);
	}
	
	// Adds a new Vertex with the given Object
	
	public void addVertex(Object item) {
		if (this.contains(item)) return;
		
		Vertex newVertex = new Vertex(item);
		graphVertices.add(newVertex);
		setSize(size + 1);
	}
	
	// Removes an existing Vertex
	
	public void removeVertex(Object item) {
		if (!this.contains(item)) return;
		
		Vertex toRemove = getVertex(item);
		List<Vertex> neighbors = new ArrayList<Vertex>();
		neighbors.addAll(neighbors(item));
		
		if (directed) {
			for (Vertex vertex : graphVertices) {
				if (vertex.isNeighbor(toRemove)) {
					removeEdge(vertex.getItem(), item);
				}
			}
		}
		
		for (Vertex neighbor : neighbors) {
			removeEdge(item, neighbor.getItem());
		}
		
		graphVertices.remove(toRemove);
		setSize(size - 1);
	}
		
	// Adds an Edge between two Vertices (given as Objects). Does nothing
	// if one or both Vertices don't exist, or if both Objects are the
	// same
	
	public void addEdge(Object item1, Object item2) {
		if (!(this.contains(item1) && this.contains(item2)) ||
			  item1.equals(item2)) return;
		
		Vertex vertex1 = getVertex(item1);
		Vertex vertex2 = getVertex(item2);
		
		if (directed) {
			Edge newEdge = new Edge(vertex1, vertex2);
			vertex1.addNeighbor(vertex2);
			graphEdges.add(newEdge);
			setNumEdges(numEdges + 1);
		} else {
			Edge newEdge1 = new Edge(vertex1, vertex2);
			Edge newEdge2 = new Edge(vertex2, vertex1);
			vertex1.addNeighbor(vertex2);
			vertex2.addNeighbor(vertex1);
			graphEdges.add(newEdge1);
			graphEdges.add(newEdge2);
			setNumEdges(numEdges + 2);
		}
	}
	
	public Edge getEdge(Object item1, Object item2) {
		if (!(this.contains(item1) && this.contains(item2))) return null;
		
		Vertex vertex1 = getVertex(item1);
		Vertex vertex2 = getVertex(item2);
		
		for (Edge edge : graphEdges) {
			if (edge.hasEndpoints(vertex1, vertex2)) {
				return edge;
			}
		}
		
		return null;
	}
	
	// Removes an existing Edge from the graph. Does nothing if one or both
	// Vertices don't exist, or if the corresponding Edge does not exist
	
	public void removeEdge(Object item1, Object item2) {
		if (!(this.contains(item1) && this.contains(item2))) return;
		
		Vertex vertex1 = getVertex(item1);
		Vertex vertex2 = getVertex(item2);
		
		if (directed) {
			vertex1.removeNeighbor(vertex2);
			Edge toRemove = getEdge(item1, item2);
			graphEdges.remove(toRemove);
			setNumEdges(numEdges - 1);
		} else {
			vertex1.removeNeighbor(vertex2);
			vertex2.removeNeighbor(vertex1);
			Edge toRemove1 = getEdge(item1, item2);
			Edge toRemove2 = getEdge(item2, item1);
			graphEdges.remove(toRemove1);
			graphEdges.remove(toRemove2);
			setNumEdges(numEdges - 2);
		}
	}
	
	// Returns the neighbors of a specified Vertex (given as Object) as a
	// list of Vertices
	
	public List<Vertex> neighbors(Object item) {
		if (!this.contains(item)) return null;
		
		Vertex vertex = getVertex(item);
		return vertex.getNeighbors();
	}
	
	// Prints the graph's list of vertices, e.g. [ A B C ]
	
	public void printVertices() {
		System.out.print("[ ");
		for (Vertex vertex : graphVertices) {
			System.out.print(vertex.getItem() + " ");
		}
		System.out.println("]");
	}
	
	// Prints the given list of vertices with the format [ A B C .. ]
	// for now, just for the neighbors function in the interface
	
	public void printVertices(List<Vertex> myVertices) {
		System.out.print("[ ");
		for (Vertex vertex : myVertices) {
			System.out.print(vertex.getItem() + " ");
		}
		System.out.println("]");
	}
	
	// Prints the graph's list of edges in the format 
	// [ (A->B) (C->D) .. ]
	
	public void printEdges() {
		System.out.print("[ ");
		for (Edge edge : graphEdges) {
			System.out.print("(" + edge.getPointA().getItem() + "->"
					+ edge.getPointB().getItem() + ") ");
		}
		System.out.println("]");
	}
	
	// Prints the current graph
	
	public void printGraph() {
		if (size == 0) {
			System.out.println("[ ]");
			return;
		}

		for (Vertex vertex : graphVertices) {
			vertex.printVertex();
		}
	}
	
	// Generates a random number within the specified bounds. Used
	// for the contract function
	
	private int randInt(int min, int max) {
		Random randGen = new Random();
		int random = randGen.nextInt(max - min + 1) + min;
		
		return random;
	}
	
	// Merges two adjacent vertices
	
	public void mergeVertex(Object item1, Object item2) {
		if (!(this.contains(item1) && this.contains(item2) &&
			  isAdjacent(item1, item2))) return;
		
		Vertex vertex1 = getVertex(item1);
		Vertex vertex2 = getVertex(item2);
		
		if (!vertex1.isMerged()) vertex1.setMerged(true);
		
		vertex1.addMergedVertices(vertex2);
		
		if (vertex2.isMerged()) {
			vertex1.addMergedVertices(vertex2.getMergedVertices());
		}
		
		List<Vertex> newNeighbors = neighbors(item2);
		
		for (Vertex newNeighbor : newNeighbors) {
			addEdge(item1, newNeighbor.getItem());
		}
		
		removeVertex(item2);
	}
	
	// Contracts the graph into one with just two vertices
	// BUG: CONTRACT NOW FAILS TO TERMINATE
	
	public void contract() {
		if (directed) {
			System.out.println("Sorry, directed graphs are currently "
					+ "unsupported.");
			return;
		}
		
		while (size > 2) {
			int random = randInt(0, this.numEdges - 1);
			Edge randEdge = graphEdges.get(random);
			
			Object pointA = randEdge.getPointA().getItem();
			Object pointB = randEdge.getPointB().getItem();
			
			mergeVertex(pointA, pointB);
		}
	}
	
	// BFS performs breadth-first search to find all connected vertices, starting
	// from the vertex containing the specified Object 'item'. It returns a list
	// of connected vertices, and prints each vertex, along with their degree of
	// separation, to standard output
	
	public List<Vertex> BFS(Object item) {
		if (!this.contains(item)) {
			System.out.println("The vertex does not exist!");
			return null;
		}
		
		Vertex first = getVertex(item);
		List<Vertex> pathList = new ArrayList<Vertex>();
		LinkedList<Vertex> searchList = new LinkedList<Vertex>();
		
		first.setExplored(true);
		searchList.add(first);
		
		while (!searchList.isEmpty()) {
			Vertex curr = searchList.removeFirst();
			List<Vertex> neighbors = neighbors(curr.getItem());
			for (Vertex next : neighbors) {
				if (!next.isExplored()) {
					pathList.add(next);
					searchList.add(next);
					next.setExplored(true);
					next.setLabel(curr.getLabel() + 1);
				}
			}
		}
		
		System.out.print("FOUND: [ ");
		for (Vertex vertex : pathList) {
			System.out.print("(" + vertex.getItem() + ", " + vertex.getLabel() + ") ");
		}
		System.out.println("]");
		
		resetVertices();
		return pathList;
	}
	
	// DFS performs depth-first search to find all connected vertices, starting
	// from the vertex containing the specified Object 'item'. It returns a list
	// of connected vertices, and prints each vertex, along with their topological
	// order, to standard output
	
	public List<Vertex> DFS(Object item) {
		if (!directed) {
			System.out.println("Sorry, we only support directed graphs right now.");
			return null;
		}
		
		int order = 1;
		Vertex first = getVertex(item);
		List<Vertex> pathList = new ArrayList<Vertex>();
		LinkedList<Vertex> searchList = new LinkedList<Vertex>();
		searchList.add(first);
		
		while (!searchList.isEmpty()) {
			Vertex curr = searchList.removeLast();
			if (!curr.isExplored()) {
				curr.setExplored(true);
				curr.setLabel(order);
				++order;
				pathList.add(curr);
				List<Vertex> neighbors = neighbors(curr.getItem());
				for (Vertex next : neighbors) {
					searchList.add(next);
				}
			}
		}
		
		System.out.print("FOUND: [ ");
		for (Vertex vertex : pathList) {
			System.out.print("(" + vertex.getItem() + ", " + vertex.getLabel() + ") ");
		}
		System.out.println("]");
		
		resetVertices();
		return pathList;
	}
	
	// Helper function using depth-first search (DFS) on each node for
	// Kosaraju's algorithm
	
	private void findSCC_DFS(Vertex vertex, int leader, boolean first, 
			LinkedList<Vertex> list) {
		vertex.setExplored(true);
		if (!first) vertex.setLabel(leader);
		List<Vertex> neighbors = neighbors(vertex.getItem());
		
		for (Vertex neighbor : neighbors) {
			if (!neighbor.isExplored()) {
				findSCC_DFS(neighbor, leader, first, list);
			}
		}
		
		list.add(vertex);
	}
	
	// Finds the strongly-connected components (SCC) of the graph with Kosaraju's
	// algorithm. Prints a list of all vertices of the graph - the vertices belonging
	// to the same SCC will have the same integer label
	
	public List<Vertex> findSCC() {
		if (!directed) return null;
		
		reverseGraph();
		LinkedList<Vertex> finList = new LinkedList<Vertex>();
		int leader = 0;
		
		for (Vertex vertex : graphVertices) {
			if (!vertex.isExplored()) {
				findSCC_DFS(vertex, leader, true, finList);
			}
		}
		
		reverseGraph();
		resetVertices();
		LinkedList<Vertex> resList = new LinkedList<Vertex>();

		while (!finList.isEmpty()) {
			Vertex curr = finList.removeLast();
			if (!curr.isExplored()) {
				++leader;
				findSCC_DFS(curr, leader, false, resList);
			}
		}
		
		System.out.print("FOUND: [ ");
		for (Vertex vertex : resList) {
			System.out.print("(" + vertex.getItem() + ", " + vertex.getLabel() + ") ");
		}
		System.out.println("]");
		
		resetVertices();
		return resList;
	}
	
	// MIN HEAP FUNCTIONS
	
	private void heapInsert(ArrayList<Vertex> heap, Vertex vertex) {
		heap.add(vertex);
		int index = heap.size() - 1;
		int parent = index / 2;
		
		while (index > 0 && 
				heap.get(index).getLabel() <= heap.get(parent).getLabel()) {
			Vertex temp = heap.get(index);
			heap.set(index, heap.get(parent));
			heap.set(parent, temp);
			
			index = parent;
			parent = index / 2;
		}
	}
	
	private Vertex extractMin(ArrayList<Vertex> heap) {
		Vertex root = heap.get(0);
		Vertex last = heap.get(heap.size() - 1);
		heap.set(0, last);
		
		int index = 0;
		int lchild = 2 * index;
		int rchild = 2 * index + 1;
		
		while (index < heap.size() && 
				lchild < heap.size() && 
				rchild < heap.size() &&
				(heap.get(index).getLabel() >= heap.get(lchild).getLabel() ||
				heap.get(index).getLabel() >= heap.get(rchild).getLabel())) {
			Vertex temp = heap.get(index);
			if (heap.get(lchild).getLabel() <= heap.get(rchild).getLabel()) {
				heap.set(index, heap.get(rchild));
				heap.set(rchild, temp);
				index = rchild;
			} else {
				heap.set(index, heap.get(lchild));
				heap.set(lchild, temp);
				index = lchild;
			}
			lchild = 2 * index;
			rchild = 2 * index + 1;
		}
		
		return root;
	}
	
	public void Dijkstra(Object item) {
		Vertex src = getVertex(item);
		src.setLabel(0);
		
		ArrayList<Vertex> heap = new ArrayList<Vertex>();
		for (Vertex vert : this.graphVertices) {
			if (!vert.equals(src)) {
				vert.setLabel(Integer.MAX_VALUE);
			}
			heapInsert(heap, vert);
		}
		
		while (!heap.isEmpty()) {
			Vertex curr = extractMin(heap);
			for (Vertex neighbor : curr.getNeighbors()) {
				Edge edge = getEdge(curr.getItem(), neighbor.getItem());
				int dist = curr.getLabel() + edge.getLen();
				if (neighbor.getLabel() > dist) {
					neighbor.setLabel(dist);
					heapInsert(heap, neighbor);
				}
			}
		}
		
		// Print results.
		System.out.println("SHORTEST PATHS FROM " + src.getItem() + ":");
		for (Vertex vert : graphVertices) {
			System.out.println(" [" + vert.getItem() + ", " + vert.getLabel() + "]");
		}
		
		resetVertices();
	}
	
	
	
	
}
