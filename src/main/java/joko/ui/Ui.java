package joko.ui;

import java.util.ArrayList;
import java.util.Scanner;
import joko.task.Task;

public class Ui {

    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

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

    public String readCommand() {
        return sc.nextLine();
    }

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

    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:\n  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:\n  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");

    }

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
