package lec5;

public class Square extends Shape {
    private double side;

    public Square(double side) {
        super("Square");
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public void print() {
        System.out.println("shape type: " + Square.class.getName() +
                ", area: " + getArea() +
                ", side: " + side);
    }
}
