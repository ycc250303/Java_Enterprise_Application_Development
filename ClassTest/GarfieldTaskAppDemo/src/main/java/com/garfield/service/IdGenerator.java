package com.garfield.service;

import java.util.concurrent.atomic.AtomicInteger;


// id 生成类
public class IdGenerator {
    private static final AtomicInteger Counter = new AtomicInteger(1000);

    public static String nextId(){
        return String.valueOf(Counter.getAndIncrement());
    }
}
