package lec5;

public interface Relatable {
    /**
     * 比较两个对象的大小关系
     * 
     * @param other 要比较的另一个对象
     * @return 负数表示当前对象小于other，0表示相等，正数表示当前对象大于other
     */
    int compare(Relatable other);
}
