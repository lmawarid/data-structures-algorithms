public class Edge {
	private Vertex pointA;
	private Vertex pointB;
	private int len; // for Dijkstra
	
	// Constructor
	
	public Edge(Vertex pointA, Vertex pointB) {
		this.pointA = pointA;
		this.pointB = pointB;
		this.len = 1;
	}
	
	// Needed to override equals for Edge since ArrayList.remove wasn't
	// removing edges properly
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Edge)) {
			return false;
		} if (other == this) {
			return true;
		} else { 
			Edge otherEdge = (Edge) other;
			return otherEdge.getPointA().equals(this.pointA) &&
				   otherEdge.getPointB().equals(this.pointB);
		}
	}
	
	// Getters
	
	public Vertex getPointA() {
		return pointA;
	}
	
	public Vertex getPointB() {
		return pointB;
	}
	
	public int getLen() {
		return len;
	}
	
	// Setters
	
	public void setPointA(Vertex myPoint) {
		this.pointA = myPoint;
	}
	
	public void setPointB(Vertex myPoint) {
		this.pointB = myPoint;
	}
	
	public void setLen(int len) {
		this.len = len;
	}
	
	// Checks if an Edge points to a specified Vertex
	
	public boolean pointsTo(Vertex point) {
		return pointA.equals(point) || pointB.equals(point);
	}
	
	// Checks if an Edge has two vertices as endpoints, in order
	
	public boolean hasEndpoints(Vertex pointA, Vertex pointB) {
		return pointA.equals(this.pointA) && pointB.equals(this.pointB);
	}
}
