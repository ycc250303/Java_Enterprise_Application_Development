package com.garfield;

import com.garfield.repository.GarfieldTaskRepository;
import com.garfield.repository.InMemoryGarfieldTaskRepository;
import com.garfield.service.IdGenerator;
import com.garfield.service.PomodoroTimer;
import com.garfield.service.TaskExporter;
import com.garfield.task.DeadlineTask;
import com.garfield.task.SimpleTask;
import com.garfield.task.Task;
import com.garfield.task.TaskException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// This is the main command. Students will add subcommands to it.
@Command(
    name = "GarfieldTask",
    version = "GarfieldTask 1.0",
    mixinStandardHelpOptions = true, // Adds --help and --version options
    description = "A command-line tool to help Garfield manage his tasks."
)
// 任务命令行控制类
public class GarfieldTaskCommand implements Runnable{
    // This class acts as a container for all other commands.
    // Students will need to pass the repository and other services
    // into the constructors of their real command classes.

    private final GarfieldTaskRepository repository;
    private final PomodoroTimer timer;
    private final TaskExporter exporter;

    public GarfieldTaskCommand(){
        this.repository = new InMemoryGarfieldTaskRepository();
        this.timer = new PomodoroTimer();
        this.exporter = new TaskExporter();
    }
    @Command(name = "add", description = "Add a new task")
    public void add(
            @Option(names = {"--id"}, description = "Task id (auto-generated if omitted)") String id,
            @Option(names = {"--title"}, required = true, description = "Title") String title,
            @Option(names = {"--priority"}, description = "Priority: LOW|MEDIUM|HIGH") Task.Priority priority,
            @Option(names = {"--deadline"}, description = "Deadline 'yyyy-MM-dd HH:mm:ss'") String deadline
    ){
        try {
            if (title == null || title.trim().isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }
            String tid = (id == null || id.isBlank()) ? IdGenerator.nextId() : id;

            if (deadline != null && !deadline.isBlank()) {
                // 没写出来
            } else {
                SimpleTask st = new SimpleTask(tid, title, priority == null ? Task.Priority.MEDIUM : priority);
                repository.save(st);
                System.out.println("Added simple task: " + st.describe());
            }
        } catch (TaskException te) {
            System.out.println("Error adding task: " + te.getMessage());
        }
    }

    @Command(name = "list", description = "List tasks")
    public void list(
            @Option(names = {"--status"}, description = "Filter by status OPEN|DONE") Task.Status status,
            @Option(names = {"--priority"}, description = "Filter by priority LOW|MEDIUM|HIGH") Task.Priority priority
    ) {
        List<Task> tasks;
        if (status != null) {
            tasks = repository.findByStatus(status);
        } else if (priority != null) {
            tasks = repository.findByPriority(priority);
        } else {
            tasks = repository.findAll();
        }
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        for(Task t : tasks){
            System.out.println(t.describe());
        }
    }

    @Command(name = "done", description = "Mark a task as done")
    public void done(
            @Option(names = {"--id"}, required = true, description = "Task id") String id
    ) {
        try {
            Task t = repository.findById(id);
            t.markDone();
            repository.update(t);
            System.out.println("Marked as done: " + t.describe());
        } catch (TaskException te) {
            System.out.println("Error: " + te.getMessage());
        }
    }

    @Command(name = "remove", description = "Remove a task")
    public void remove(
            @Option(names = {"--id"}, required = true, description = "Task id") String id
    ) {
        try {
            repository.deleteById(id);
            System.out.println("Removed task: " + id);
        } catch (TaskException te) {
            System.out.println("Error: " + te.getMessage());
        }
    }

    @Command(name = "timer", description = "Start pomodoro timer")
    public void timer(
            @Option(names = {"--work"}, required = true, description = "Work seconds") int work,
            @Option(names = {"--break"}, defaultValue = "0", description = "Break seconds") int brk,
            @Option(names = {"--cycles"}, defaultValue = "1", description = "Number of cycles") int cycles
    ) {
        timer.startTimer(work, brk, cycles);
    }

    @Command(name = "export", description = "Export tasks to JSON file")
    public void export(
            @Option(names = {"--file"}, required = true, description = "Output JSON file path") String file
    ) {
        try {
            exporter.exportTasks(repository.findAll(), file);
            System.out.println("Exported tasks to: " + file);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    @Command(name = "exit", description = "Exit the application")
    public void exit() {
        System.out.println("Goodbye!");
    }

    @Override
    public void run() {

    }

}
