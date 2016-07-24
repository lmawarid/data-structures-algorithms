import java.util.ArrayList;
import java.util.List;

public class Vertex{
	private Object item;
	
	private boolean merged; // for contraction algorithms
	private boolean explored; // for searching algorithms
	private int label; // for searching algorithms
	
	private List<Vertex> mergedVertices;
	private List<Vertex> neighbors;
	
	
	// Default constructor
	
	public Vertex() {
		item = null;
		merged = false;
		explored = false;
		label = 0;
		mergedVertices = new ArrayList<Vertex>();
		neighbors = new ArrayList<Vertex>();
	}
	
	// Constructs a Vertex with the given Object as its item
	
	public Vertex(Object item) {
		this.item = item;
		merged = false;
		explored = false;
		label = 0;
		mergedVertices = new ArrayList<Vertex>();
		neighbors = new ArrayList<Vertex>();
	}
	
	// The normal equals would usually work, but this override
	// is here just in case
		
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Vertex)) {
			return false;
		} else if (other == this) {
			return true;
		} else {
			Vertex otherVertex = (Vertex) other;
			return item.equals(otherVertex.getItem());
		}
	}
	
	// Getters - default and custom
	
	public Object getItem() {
		return item;
	}
	
	public boolean isMerged() {
		return merged;
	}
	
	public boolean isExplored() {
		return explored;
	}
	
	public int getLabel() {
		return label;
	}
	
	public List<Vertex> getMergedVertices() {
		return mergedVertices;
	}
	
	// Default - returns this Vertex's neighbors as a list of incident edges
	
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
		
	// Setters - default and custom
	
	public void setItem(Object item) {
		this.item = item;
	}
	
	public void setMerged(boolean newStatus) {
		this.merged = newStatus;
	}
	
	public void setExplored(boolean newStatus) {
		this.explored = newStatus;
	}
	
	public void setLabel(int label) {
		this.label = label;
	}
	
	// Adds a Vertex to this Vertex's list of merged vertices
	
	public void addMergedVertices(Vertex point) {
		mergedVertices.add(point);
	}
	
	// Adds a list of Vertices to this Vertex's list of merged vertices
	
	public void addMergedVertices(List<Vertex> vertexList) {
		mergedVertices.addAll(vertexList);
	}
	
	// Checks if this Vertex is adjacent to a specified Vertex - uses
	// getEdge in the process
	
	public boolean isNeighbor(Vertex point) {
		return neighbors.contains(point);
	}
	
	// Adds a specified vertex to this vertices's list of neighbors
	// If already neighbors, nothing is done
	
	public void addNeighbor(Vertex point) {
		if (isNeighbor(point)) return;
		neighbors.add(point);
	}
	
	// Removes an existing neighbor between this vertex and the
	// specified vertex. Does nothing if they are not neighbors
	
	public void removeNeighbor(Vertex point) {
		neighbors.remove(point);
	}
	
	// Prints this Vertex is the following format:
	// ITEM [ LIST OF MERGED VERTICES] [ LIST OF NEIGHBORS ]
	// EXAMPLE: A [ B C ] [ D E F ]
	
	public void printVertex() {
		System.out.print(item + " ");
		
		if (merged) {
			System.out.print("[ ");
			for (Vertex vertex : mergedVertices) {
				System.out.print(vertex.getItem() + " ");
			}
			System.out.print("] ");
		}
		
		System.out.print("[ ");
		
		for (Vertex vertex : neighbors) {
			System.out.print(vertex.getItem() + " ");
		}
		
		System.out.println("]");
	}
}
