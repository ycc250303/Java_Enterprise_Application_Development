package lec5;

public class ShapeTest {
    public static void main(String[] args) {
        System.out.println("=== Shape 多态性和比较测试 ===\n");

        // 创建不同类型的形状对象
        Circle circle = new Circle(5.0);
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        Triangle triangle = new Triangle(3.0, 4.0, 5.0, 4.0);
        Square square = new Square(4.0);
        Ellipse ellipse = new Ellipse(3.0, 2.0);

        // 使用一个数组存储所有类型的形状
        Shape[] shapes = { circle, rectangle, triangle, square, ellipse };

        // 在循环中调用实现的功能
        System.out.println("1. 显示所有形状信息:");
        for (int i = 0; i < shapes.length; i++) {
            System.out.print("形状 " + (i + 1) + ": ");
            shapes[i].print();
        }

        System.out.println("\n2. 显示所有形状的面积:");
        for (Shape shape : shapes) {
            System.out.println(shape.getName() + " 的面积: " + String.format("%.2f", shape.getArea()));
        }

        // 测试 compare 方法
        System.out.println("\n3. 测试形状比较功能:");
        for (int i = 0; i < shapes.length; i++) {
            for (int j = i + 1; j < shapes.length; j++) {
                Shape shape1 = shapes[i];
                Shape shape2 = shapes[j];
                int result = shape1.compare(shape2);

                System.out.print(shape1.getName() + " vs " + shape2.getName() + ": ");
                if (result < 0) {
                    System.out.println(shape1.getName() + " 面积较小");
                } else if (result > 0) {
                    System.out.println(shape1.getName() + " 面积较大");
                } else {
                    System.out.println("面积相等");
                }
            }
        }

        // 测试与相同面积的形状比较
        System.out.println("\n4. 测试相同面积的形状比较:");
        Circle circle2 = new Circle(4.0);
        Rectangle square2 = new Rectangle(4.0, 4.0);
        int result = circle2.compare(square2);

        System.out.print("圆形(半径4.0) vs 正方形(4x4): ");
        if (result < 0) {
            System.out.println("圆形面积较小");
        } else if (result > 0) {
            System.out.println("圆形面积较大");
        } else {
            System.out.println("面积相等");
        }

        // 显示具体数值
        System.out.println("圆形面积: " + String.format("%.2f", circle2.getArea()));
        System.out.println("正方形面积: " + String.format("%.2f", square2.getArea()));

        // 测试异常情况
        System.out.println("\n5. 测试异常情况:");
        try {
            circle.compare(null);
            System.out.println("与null比较: 未抛出异常");
        } catch (Exception e) {
            System.out.println("与null比较: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}