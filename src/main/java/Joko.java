import java.util.Scanner;
import java.util.ArrayList;

public class Joko {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Joko");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine();
            String[] inputParts = input.split(" ", 2);
            String command = inputParts[0];

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println((i + 1) + "." + taskList.get(i));
                }
                System.out.println("____________________________________________________________");
            } else if (command.equals("mark") || command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskList.size()) {
                        System.out.println("Invalid task number.");
                        continue;
                    }
                    if (command.equals("mark")) {
                        taskList.get(index).isDone = true;
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + taskList.get(index));
                        System.out.println("____________________________________________________________");
                    } else {
                        taskList.get(index).isDone = false;
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + taskList.get(index));
                        System.out.println("____________________________________________________________");
                    }
                } catch (Exception e) {
                    System.out.println("Please type a valid input: <mark/unmark> <task number>");
                }

            } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                Task newTask = null;

                if (command.equals("todo")) {
                    try {
                        String desc = input.substring(5).trim();
                        if (desc.isEmpty()) {
                            throw new IllegalArgumentException("Todo description cannot be empty!");
                        }
                        newTask = new ToDo(desc);
                    } catch (Exception e) {
                        System.out.println("Error adding Todo: " + e.getMessage());
                    }
                } else if (command.equals("deadline")) {
                    try {
                        String[] parts = input.substring(9).split(" /by", 2);
                        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                            throw new IllegalArgumentException("Deadline must have a description and a /by date/time.");
                        }
                        newTask = new Deadline(parts[0].trim(), parts[1].trim());
                    } catch (Exception e) {
                        System.out.println("Error adding Deadline: " + e.getMessage());
                    }
                } else if (command.equals("event")) {
                    try {
                        String[] part1 = input.substring(6).split(" /from ", 2);
                        if (part1.length < 2) {
                            throw new IllegalArgumentException("Event must have a description and a /from start time.");
                        }
                        String desc = part1[0].trim();
                        String[] part2 = part1[1].split(" /to ", 2);
                        if (part2.length < 2) {
                            throw new IllegalArgumentException("Event must have a /to end time.");
                        }
                        newTask = new Event(desc, part2[0].trim(), part2[1].trim());
                    } catch (Exception e) {
                        System.out.println("Error adding Event: " + e.getMessage());
                    }
                }

                if (newTask != null) {
                    taskList.add(newTask);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:\n  " + newTask);
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

            } else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskList.size()) {
                        System.out.println("Invalid task number.");
                        continue;
                    } else {
                        Task temp = taskList.get(index);
                        taskList.remove(index);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Noted. I've removed this task:\n  " + temp);
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (Exception e) {
                    System.out.println("Please type a valid input: <delete> <task number>");
                }
            } else {
                System.out.println("____________________________________________________________");
                System.out.println("sorry i could not understand your command :(");
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }

    static class Task {
        protected final String desc;
        protected boolean isDone;

        public Task(String desc) {
            this.desc = desc;
            this.isDone = false;
        }

        public String getStatus() {
            return (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String toString() {
            return getStatus() + desc;
        }
    }

    static class Deadline extends Task {
        protected String by;

        public Deadline(String desc, String by) {
            super(desc);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() +
                    "(by: " + by + ") ";
        }
    }

    static class ToDo extends Task {
        public ToDo(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    static class Event extends Task {
        protected String from;
        protected String to;

        public Event(String desc, String from, String to) {
            super(desc);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() +
                    "(from: " + from +
                    " to: " + to + ")";
        }
    }
}
