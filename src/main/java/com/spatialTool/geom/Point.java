package com.spatialTool.geom;

public class Point {
	
	private double x;

	private double y;

	public Point() {
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "Point[" + x + ", " + y + "]";
	}
	
	public boolean equals(Object o){
		if (this == o) {
            return true;
        }
		if (o instanceof Point){
			return ((Point) o).getX()==this.getX()&&((Point) o).getY()==this.getY();
		}
		return false;
	}
}
