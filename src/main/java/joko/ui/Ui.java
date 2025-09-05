package joko.ui;


import java.util.ArrayList;
import java.util.Scanner;

import joko.task.Task;

/**
 * Handles the user interface for the Joko task manager application.
 * <p>
 * This class is responsible for:
 * <ul>
 *     <li>Displaying welcome messages and task lists.</li>
 *     <li>Reading user input commands.</li>
 *     <li>Displaying messages related to task addition, deletion, and status changes.</li>
 * </ul>
 * <p>
 * It uses {@link Scanner} to read input from the user.
 * </p>
 */
public class Ui {

    private final Scanner sc;

    /**
     * Constructs a new {@code Ui} instance and initializes the input scanner.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays a welcome message along with the current tasks in the list.
     *
     * @param tasks the list of tasks to display
     */
    public void showWelcome(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Joko");
        System.out.println("What can I do for you?");
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the input entered by the user as a {@code String}
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays all tasks currently in the list.
     *
     * @param tasks the list of tasks to display
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been added,
     * along with the total number of tasks.
     *
     * @param task the task that was added
     * @param size the new total number of tasks
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:\n  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been removed,
     * along with the total number of tasks.
     *
     * @param task the task that was removed
     * @param size the new total number of tasks
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:\n  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");

    }

    /**
     * Displays a message indicating that a task has been marked or unmarked as done.
     *
     * @param task the task whose status changed
     * @param isDone {@code true} if the task is marked as done, {@code false} otherwise
     */
    public void showTaskMarked(Task task, boolean isDone) {
        System.out.println("____________________________________________________________");
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
        System.out.println("____________________________________________________________");
    }

    public void close() {
        sc.close();
    }
}
