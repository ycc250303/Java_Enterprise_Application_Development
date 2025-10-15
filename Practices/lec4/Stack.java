import java.util.LinkedList;

public class Stack {
    private LinkedList<Integer> list;

    public Stack() {
        this.list = new LinkedList<>();
    }

    public void push(int x) {
        this.list.addFirst(x);
    }

    public int pop() {
        if (this.list.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return this.list.removeFirst();
    }

    public int size() {
        return this.list.size();
    }

    public void clear() {
        this.list.clear();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    // 创建迭代器的方法
    public StackIterator iterator() {
        return new StackIterator(this);
    }

    // 获取内部列表的副本（用于迭代器）
    public LinkedList<Integer> getListCopy() {
        return new LinkedList<>(this.list);
    }
}