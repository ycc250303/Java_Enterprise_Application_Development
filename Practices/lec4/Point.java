public class Point {
    private int xPos;
    private int yPos;

    public Point() {
    }

    public Point(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int y) {
        this.yPos = y;
    }
}