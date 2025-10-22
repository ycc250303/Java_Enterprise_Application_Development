package com.garfield;

import picocli.CommandLine;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GarfieldTaskApp {

    public static void main(String[] args) {
        // --- Create Picocli Command Hub ---
        GarfieldTaskCommand mainCommand = new GarfieldTaskCommand();
        CommandLine cmd = new CommandLine(mainCommand);

        // --- Register Subcommands ---
        cmd.addSubcommand("hello", new com.garfield.commands.HelloCommand());
        // Students will add their commands here:

        // --- Start REPL (Read-Evaluate-Print Loop) ---
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to GarfieldTask! Type 'help' for commands or 'exit' to quit.");
            while (true) {
                System.out.print("garfield> ");
                String line = scanner.nextLine();

                if ("exit".equalsIgnoreCase(line.trim())) {
                    System.out.println("Goodbye! (Garfield is going to nap)");
                    break;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Use a RegEx-based parser to correctly handle quoted arguments
                String[] arguments = smartSplit(line);

                // Print the parsed arguments for debugging
                System.out.println("Executing: " + String.join(" ", arguments));

                // Let Picocli parse and execute the command
                // works perfectly with quoted strings
                cmd.execute(arguments);
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * A robust command-line parser that handles quotes.
     * Students DO NOT need to modify this.
     * @param line The raw input line.
     * @return A String array of arguments.
     */
    private static String[] smartSplit(String line) {
        List<String> args = new ArrayList<>();
        // This regex matches quoted strings or non-space sequences
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Add content within double quotes
                args.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                // Add content within single quotes
                args.add(matcher.group(2));
            } else {
                // Add unquoted argument
                args.add(matcher.group());
            }
        }
        return args.toArray(new String[0]);
    }
}