package com.garfield.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

@Command(
        name = "hello",
        description = "Say hello to Garfield."
)
public class HelloCommand implements Runnable {
    @Option(names = {"-h", "--happy"},
            description = "Make Garfield happy")
    private boolean happy;

    @Option(names = {"-u", "--unhappy"},
            description = "Make Garfield unhappy")
    private boolean unhappy;
    
    @Option(names = {"-m", "--message"},
            description = "What Garfield says")
    private String message;
    
    // Use Runnable for commands that don't return a value
    @Override
    public void run() {
        // e.g.
        // garfield> hello --message "I love you!"
        // Garfield says: I love you!
        // garfield> hello --happy
        // Hello! Garfield is happy and ready for his lasagna! 😊
        // garfield> hello --unhappy
        // Hello... Garfield is not in the mood for lasagna today. 😢
        if (message != null) {
            System.out.println("Garfield says: " + message);
        } else if (happy) {
            System.out.println("Hello! Garfield is happy and ready for his lasagna! 😊");
        } else if (unhappy) {
            System.out.println("Hello... Garfield is not in the mood for lasagna today. 😢");
        } else {
            System.out.println("Hello! Garfield is ready for his lasagna.");
        }
    }
}