package com.spatialTool.geom;

/**
 *   参考java.awt.geom.Line2D类
 */
public  class Line  {

	public double x1;

	public double y1;

	public double x2;

	public double y2;

	public Line() {
	}

	public Line(double x1, double y1, double x2, double y2) {
		setLine(x1, y1, x2, y2);
	}

	public Line(Point p1, Point p2) {
		setLine(p1, p2);
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public Point getP1() {
		return Point.create(x1, y1);
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	public Point getP2() {
		return Point.create(x2, y2);
	}

	public void setLine(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void setLine(Point p1, Point p2) {
		setLine(p1.x(), p1.y(), p2.x(), p2.y());
	}
	/**
	 * @param x1,y1  线段的一个点
	 * @param x2,y2  线段的另一个点
	 * @param px,py  已知点
	 * @return返回"点到线段所在直线最短距离"的平方
	 */
	public static double ptLineDistSq(double x1, double y1, double x2,
			double y2, double px, double py) {
		// Adjust vectors relative to x1,y1
		// x2,y2 becomes relative vector from x1,y1 to end of segment
		x2 -= x1;
		y2 -= y1;
		// px,py becomes relative vector from x1,y1 to test point
		px -= x1;
		py -= y1;
		double dotprod = px * x2 + py * y2;
		// dotprod is the length of the px,py vector
		// projected on the x1,y1=>x2,y2 vector times the
		// length of the x1,y1=>x2,y2 vector
		double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
		// Distance to line is now the length of the relative point
		// vector minus the length of its projection onto the line
		double lenSq = px * px + py * py - projlenSq;
		if (lenSq < 0) {
			lenSq = 0;
		}
		return lenSq;
	}
	
	public double ptLineDistSq(double px, double py){
		return ptSegDistSq(getX1(),getY1(),getX2(),getY1(),px,py);
	}
	/**
	 * @param x1,y1  线段的一个点
	 * @param x2,y2  线段的另一个点
	 * @param px,py  已知点
	 * @return返回"点到线段最短距离"的平方
	 */
	public static double ptSegDistSq(double x1, double y1, double x2,
			double y2, double px, double py) {
		// Adjust vectors relative to x1,y1
		// x2,y2 becomes relative vector from x1,y1 to end of segment
		x2 -= x1;
		y2 -= y1;
		// px,py becomes relative vector from x1,y1 to test point
		px -= x1;
		py -= y1;
		double dotprod = px * x2 + py * y2;
		double projlenSq;
		if (dotprod <= 0.0) {
			// px,py is on the side of x1,y1 away from x2,y2
			// distance to segment is length of px,py vector
			// "length of its (clipped) projection" is now 0.0
			projlenSq = 0.0;
		} else {
			// switch to backwards vectors relative to x2,y2
			// x2,y2 are already the negative of x1,y1=>x2,y2
			// to get px,py to be the negative of px,py=>x2,y2
			// the dot product of two negated vectors is the same
			// as the dot product of the two normal vectors
			px = x2 - px;
			py = y2 - py;
			dotprod = px * x2 + py * y2;
			if (dotprod <= 0.0) {
				// px,py is on the side of x2,y2 away from x1,y1
				// distance to segment is length of (backwards) px,py vector
				// "length of its (clipped) projection" is now 0.0
				projlenSq = 0.0;
			} else {
				// px,py is between x1,y1 and x2,y2
				// dotprod is the length of the px,py vector
				// projected on the x2,y2=>x1,y1 vector times the
				// length of the x2,y2=>x1,y1 vector
				projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
			}
		}
		// Distance to line is now the length of the relative point
		// vector minus the length of its projection onto the line
		// (which is zero if the projection falls outside the range
		// of the line segment).
		double lenSq = px * px + py * py - projlenSq;
		if (lenSq < 0) {
			lenSq = 0;
		}
		return lenSq;
	}
	
	public double ptSegDistSq(double px, double py){
		return ptSegDistSq(getX1(),getY1(),getX2(),getY1(),px,py);
	}

}
