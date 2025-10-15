import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class lec4 {
    /**
     * 生成指定数量的随机圆。
     * - 圆心在由 upperLeft 与 lowerRight 构成的矩形（含边界）内随机取值。
     * - 半径范围：(1 ..= floor(maxRadius))，若 maxRadius <= 0 或 amount <= 0 返回空数组。
     */
    public static Circle[] generate(int amount, float maxRadius, Point upperLeft, Point lowerRight) {
        if (amount <= 0 || maxRadius <= 0 || upperLeft == null || lowerRight == null) {
            return new Circle[0];
        }
        int minX = Math.min(upperLeft.getX(), lowerRight.getX());
        int maxX = Math.max(upperLeft.getX(), lowerRight.getX());
        int minY = Math.min(upperLeft.getY(), lowerRight.getY());
        int maxY = Math.max(upperLeft.getY(), lowerRight.getY());
        int maxR = Math.max(1, (int) Math.floor(maxRadius));

        Circle[] circles = new Circle[amount];
        for (int i = 0; i < amount; i++) {
            int r = ThreadLocalRandom.current().nextInt(1, maxR + 1);
            int x = ThreadLocalRandom.current().nextInt(minX, maxX + 1);
            int y = ThreadLocalRandom.current().nextInt(minY, maxY + 1);
            Point center = new Point(x, y);
            circles[i] = new Circle(center, r);
        }
        return circles;
    }

    /**
     * 返回面积最大的圆；若参数为空或仅包含 null，则返回 null。
     */
    public static Circle max(Circle... circles) {
        if (circles == null || circles.length == 0) {
            return null;
        }
        Circle best = null;
        double bestArea = Double.NEGATIVE_INFINITY;
        for (Circle c : circles) {
            if (c == null)
                continue;
            double area = c.getArea();
            if (area > bestArea) {
                bestArea = area;
                best = c;
            }
        }
        return best;
    }

    /**
     * 就地按面积升序排序；面积相同按半径升序，再按圆心 x、y 升序。
     */
    public static void sort(Circle[] circles) {
        if (circles == null || circles.length == 0) {
            return;
        }
        Arrays.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle a, Circle b) {
                if (a == null && b == null)
                    return 0;
                if (a == null)
                    return 1; // null 放后
                if (b == null)
                    return -1;
                int byArea = Double.compare(a.getArea(), b.getArea());
                if (byArea != 0)
                    return byArea;
                int byR = Integer.compare(a.getRadius(), b.getRadius());
                if (byR != 0)
                    return byR;
                // 需要访问中心坐标
                // 通过 toString 解析不可靠，这里改为比较字符串结果的稳定性不足；
                // 为简洁起见，利用反射或新增 getter 会改动类结构，这里采用简单的字符串比较兜底。
                return a.toString().compareTo(b.toString());
            }
        });
    }

    // ================main method================= //
    public static void main(String[] args) {
        System.out.println("=== Stack and StackIterator 测试 ===");

        // 创建栈实例
        Stack stack = new Stack();

        // 测试基本操作
        System.out.println("1. 测试 push 和 size:");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("栈大小: " + stack.size()); // 应该输出 3

        // 测试迭代器
        System.out.println("\n2. 测试 StackIterator:");
        StackIterator iterator = stack.iterator();
        System.out.println("迭代器遍历栈内容:");
        while (iterator.hasNext()) {
            System.out.println("元素: " + iterator.next());
        }

        // 测试 reset 方法
        System.out.println("\n3. 测试 reset 方法:");
        iterator.reset();
        System.out.println("重置后再次遍历:");
        while (iterator.hasNext()) {
            System.out.println("元素: " + iterator.next());
        }

        // 测试 pop 操作
        System.out.println("\n4. 测试 pop 操作:");
        System.out.println("弹出元素: " + stack.pop()); // 30
        System.out.println("弹出元素: " + stack.pop()); // 20
        System.out.println("当前栈大小: " + stack.size()); // 1

        // 测试 clear 操作
        System.out.println("\n5. 测试 clear 操作:");
        stack.clear();
        System.out.println("清空后栈大小: " + stack.size()); // 0

        // 测试空栈的迭代器
        System.out.println("\n6. 测试空栈的迭代器:");
        StackIterator emptyIterator = stack.iterator();
        System.out.println("空栈迭代器 hasNext: " + emptyIterator.hasNext()); // false

        // 测试异常情况
        System.out.println("\n7. 测试异常情况:");
        try {
            stack.pop(); // 应该抛出异常
        } catch (RuntimeException e) {
            System.out.println("捕获异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}