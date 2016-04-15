package com.spatialTool.geom;

public final class Rectangle{
    private final double x1, y1, x2, y2;

    private Rectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    static Rectangle create(double x1, double y1, double x2, double y2) {
        return new Rectangle(x1, y1, x2, y2);
    }

    public double x1() {
        return x1;
    }

    public double y1() {
        return y1;
    }

    public double x2() {
        return x2;
    }

    public double y2() {
        return y2;
    }

    public double area() {
        return (x2 - x1) * (y2 - y1);
    }

    //添加一个矩形，形成一个更大的矩形
    public Rectangle add(Rectangle r) {
        return new Rectangle(min(x1, r.x1), min(y1, r.y1), max(x2, r.x2), max(y2, r.y2));
    }

    public boolean contains(double x, double y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    //两个矩形是否相交
    public boolean intersects(Rectangle r) {
        return r.x2 >= x1 && r.x1 <= x2 && r.y2 >= y1 && r.y1 <= y2;
    }

    public double distance(Rectangle r) {
        if (intersects(r))
            return 0;
        else {
            Rectangle mostLeft = x1 < r.x1 ? this : r;
            Rectangle mostRight = x1 > r.x1 ? this : r;
            double xDifference = max(0,
                    mostLeft.x1 == mostRight.x1 ? 0 : mostRight.x1 - mostLeft.x2);

            Rectangle upper = y1 < r.y1 ? this : r;
            Rectangle lower = y1 > r.y1 ? this : r;

            double yDifference = max(0, upper.y1 == lower.y1 ? 0 : lower.y1 - upper.y2);

            return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
        }
    }

    public Rectangle mbr() {
        return this;
    }

    @Override
    public String toString() {
        return "Rectangle [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
    }


    public double intersectionArea(Rectangle r) {
        if (!intersects(r))
            return 0;
        else
            return create(max(x1, r.x1), max(y1, r.y1), min(x2, r.x2), min(y2, r.y2)).area();
    }

    public double perimeter() {
        return 2 * (x2 - x1) + 2 * (y2 - y1);
    }

    private static double max(double a, double b) {
        if (a < b)
            return b;
        else
            return a;
    }

    private static double min(double a, double b) {
        if (a < b)
            return a;
        else
            return b;
    }

}