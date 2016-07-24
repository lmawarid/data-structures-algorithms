
public class Vector {
	
	public int x;
	public int y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void print() {
		System.out.print("(" + x + ", " + y + ")");
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
