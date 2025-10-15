import java.util.Iterator;
import java.util.LinkedList;

public class StackIterator implements Iterator<Integer> {
    private LinkedList<Integer> list;
    private int currentIndex;

    public StackIterator(Stack stack) {
        // 创建栈的副本以避免修改原栈
        this.list = stack.getListCopy();
        this.currentIndex = 0;
    }

    public void reset() {
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return this.currentIndex < this.list.size();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new RuntimeException("No more elements");
        }
        return this.list.get(this.currentIndex++);
    }
}