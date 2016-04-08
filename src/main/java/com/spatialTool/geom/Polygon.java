package com.spatialTool.geom;

import java.util.ArrayList;
import java.util.List;

/**
 * 多边行
 */
public class Polygon {

	private List<Point> points;  //多边形的顶点

	public List<Point> getCorner() {
		return points;
	}

	public void setCorner(List<Point> points) {
		this.points = points;
	}
	/**
	 * 点跟多边形的关系。多边形包含点则返回true
	 * @param point
	 * @return
	 */
	public boolean searchPoint(Point point) {
		double x = point.getX();
		double y = point.getY();
		int npoints = points.size();
		int hits = 0;

		double lastx = points.get(npoints - 1).getX();
		double lasty = points.get(npoints - 1).getY();
		double curx, cury;

		// Walk the edges of the polygon
		for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
			curx = points.get(i).getX();
			cury = points.get(i).getY();

			if (cury == lasty) {
				continue;
			}

			double leftx;
			if (curx < lastx) {
				if (x >= lastx) {
					continue;
				}
				leftx = curx;
			} else {
				if (x >= curx) {
					continue;
				}
				leftx = lastx;
			}

			double test1, test2;
			if (cury < lasty) {
				if (y < cury || y >= lasty) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - curx;
				test2 = y - cury;
			} else {
				if (y < lasty || y >= cury) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - lastx;
				test2 = y - lasty;
			}

			if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
				hits++;
			}
		}
		return ((hits & 1) != 0);
	}

	public static int relativeCCW(double x1, double y1, double x2, double y2,
			double px, double py) {
		x2 -= x1;
		y2 -= y1;
		px -= x1;
		py -= y1;
		double ccw = px * y2 - py * x2;
		if (ccw == 0.0) {
			ccw = px * x2 + py * y2;
			if (ccw > 0.0) {
				px -= x2;
				py -= y2;
				ccw = px * x2 + py * y2;
				if (ccw < 0.0) {
					ccw = 0.0;
				}
			}
		}
		return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
	}
	/**
	 * 判断两个线段是否相交
	 */
	public static boolean linesIntersect(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		return ((relativeCCW(x1, y1, x2, y2, x3, y3)
				* relativeCCW(x1, y1, x2, y2, x4, y4) <= 0) && (relativeCCW(x3,
				y3, x4, y4, x1, y1) * relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
	}

	/**
	 * 判断两个范围是否相交
	 */
	public boolean polygonsIntersect(Polygon p) {
		// 如果一个范围包含另一个范围，则返回true;
		double x = p.points.get(p.points.size() - 1).getX();
		double y = p.points.get(p.points.size() - 1).getY();
		if (searchPoint(new Point(x, y)))
			return true;
		x = this.points.get(this.points.size() - 1).getX();
		y = this.points.get(this.points.size() - 1).getY();
		if (p.searchPoint(new Point(x, y)))
			return true;
		for (int i = 0; i < p.points.size() - 1; i++) {
			for (int j = 0; j < this.points.size() - 1; j++) {
				if (linesIntersect(p.points.get(i).getX(), p.points.get(i)
						.getY(), p.points.get(i + 1).getX(), p.points
						.get(i + 1).getY(), this.points.get(j).getX(),
						this.points.get(j).getY(), this.points.get(j + 1)
								.getX(), this.points.get(j + 1).getY()))
					return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String str1 = "116.385603,39.937328,116.38616,39.93638,116.387422,39.936712,116.387476,39.93758,116.386371,39.937621";
		String str2 = "116.385805,39.937649,116.385379,39.937003,116.384894,39.937452,116.385055,39.93776";
		Polygon p1 = convertToPoly(str1);
		Polygon p2 = convertToPoly(str2);
		boolean result = p1.polygonsIntersect(p2);
		System.out.println("是否相交：" + result);
	}

	public static Polygon convertToPoly(String str) {
		Polygon p = new Polygon();
		String[] array = str.split(",");
		List<Point> corner = new ArrayList<Point>();
		for (int i = 0; i < array.length; i += 2) {
			Point c = new Point(Double.parseDouble(array[i]),
					Double.parseDouble(array[i + 1]));
			corner.add(c);
			c = null;
		}
		p.setCorner(corner);
		return p;
	}
}
