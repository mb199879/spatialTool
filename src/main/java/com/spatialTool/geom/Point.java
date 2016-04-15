package com.spatialTool.geom;

public final class Point implements Geometry {

	private final Rectangle mbr;

	private Point(double x, double y) {
		this.mbr = Rectangle.create(x, y, x, y);
	}

	static Point create(double x, double y) {
		return new Point(x, y);
	}

	@Override
	public Rectangle mbr() {
		return mbr;
	}

	//点到矩形的距离
	@Override
	public double distance(Rectangle r) {
		return mbr.distance(r);
	}

	//点到点的距离
	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}

	public double distanceSquared(Point p) {
		double dx = mbr().x1() - p.mbr().x1();
		double dy = mbr().y1() - p.mbr().y1();
		return dx * dx + dy * dy;
	}

	@Override
	public boolean intersects(Rectangle r) {
		return mbr.intersects(r);
	}

	public double x() {
		return mbr.x1();
	}

	public double y() {
		return mbr.y1();
	}

	@Override
	public String toString() {
		return "Point [x=" + x() + ", y=" + y() + "]";
	}

}