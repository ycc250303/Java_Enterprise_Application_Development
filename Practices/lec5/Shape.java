package lec5;

public abstract class Shape implements Relatable {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 计算形状的面积
     * 
     * @return 面积值
     */
    public abstract double getArea();

    /**
     * 输出形状的信息
     */
    public abstract void print();

    /**
     * 实现 Relatable 接口的 compare 方法
     * 默认按面积进行比较
     */
    @Override
    public int compare(Relatable other) {
        if (other == null) {
            return 1; // 当前对象大于null
        }
        if (!(other instanceof Shape)) {
            throw new IllegalArgumentException("Cannot compare Shape with non-Shape object");
        }

        Shape otherShape = (Shape) other;
        double thisArea = this.getArea();
        double otherArea = otherShape.getArea();

        if (thisArea < otherArea) {
            return -1;
        } else if (thisArea > otherArea) {
            return 1;
        } else {
            return 0;
        }
    }
}
