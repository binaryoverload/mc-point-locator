package es.esy.williamoldham.mcpointlocator;

public class Point {

	private String name;
	private int x;
	private int z;
	
	public Point(String name, int x, int z) {
		this.name = name;
		this.x = x;
		this.z = z;
	}
	
	public String getName(){
		return name;
	}
	
	public int getX(){
		return x;
	}
	
	public int getZ(){
		return z;
	}
	
}
