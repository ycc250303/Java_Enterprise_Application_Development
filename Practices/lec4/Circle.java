public class Circle {
    private int radius;
    private Point center;

    public Circle() {

    }

    public Circle(int x, int y, int radius) {
        this.center = new Point(x, y);
        this.radius = radius;
    }

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return radius * radius * Math.PI;
    }

    @Override
    public String toString() {
        return "Circle@(" + center.getX() + "," + center.getY() + "),radius=" + radius;
    }
}