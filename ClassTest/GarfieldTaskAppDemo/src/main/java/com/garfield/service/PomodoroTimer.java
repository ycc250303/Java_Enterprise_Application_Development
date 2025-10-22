package com.garfield.service;

import com.garfield.service.IdGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;

// 番茄钟类
public class PomodoroTimer{
    private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void startTimer(int workSeconds,int breakSeconds,int cycles){

        if(workSeconds <= 0 || breakSeconds < 0 || cycles <= 0){
            System.out.println("Invalid input");
            return;
        }

        for (int i = 1; i <= cycles; i++) {
            final int currentCycle = i;

            // 工作时间任务
            Runnable workStartTask = () -> {
                System.out.println(df.format(new Date()) + " [Cycle " + currentCycle + "/" + cycles + "] Work time started!");
            };

            Runnable workEndTask = () -> {
                System.out.println(df.format(new Date()) + " [Cycle " + currentCycle + "/" + cycles + "] Work time ended!");
            };

            // 休息时间任务（如果不是最后一轮）
            Runnable breakStartTask = () -> {
                System.out.println(df.format(new Date()) + " [Cycle " + currentCycle + "/" + cycles + "] Break time started!");
            };

            Runnable breakEndTask = () -> {
                System.out.println(df.format(new Date()) + " [Cycle " + currentCycle + "/" + cycles + "] Break time ended!");
            };

            // 工作时间开始
            executor.schedule(workStartTask, (long) (currentCycle - 1) * (workSeconds + breakSeconds), TimeUnit.SECONDS);
            // 工作时间结束
            executor.schedule(workEndTask, (long) (currentCycle - 1) * (workSeconds + breakSeconds) + workSeconds, TimeUnit.SECONDS);

            if (i < cycles && breakSeconds > 0) {
                // 休息时间开始
                executor.schedule(breakStartTask, (long) (currentCycle - 1) * (workSeconds + breakSeconds) + workSeconds, TimeUnit.SECONDS);
                // 休息时间结束
                executor.schedule(breakEndTask, (long) (currentCycle - 1) * (workSeconds + breakSeconds) + workSeconds + breakSeconds, TimeUnit.SECONDS);
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
