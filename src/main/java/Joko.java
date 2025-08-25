import java.util.Scanner;

public class Joko {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int taskCount = 0;

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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + taskList[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (command.equals("mark") || command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskCount) {
                        System.out.println("Invalid task number.");
                        continue;
                    }
                    if (command.equals("mark")) {
                        taskList[index].isDone = true;
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + taskList[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        taskList[index].isDone = false;
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + taskList[index]);
                        System.out.println("____________________________________________________________");
                    }
                } catch (Exception e) {
                    System.out.println("Please type a valid input: <command> <task number>");
                }

            } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                try {
                    Task newTask;

                    if (command.equals("todo")) {
                        String desc = input.substring(5).trim();
                        newTask = new ToDo(desc);
                    } else if (command.equals("deadline")) {
                        String[] deadlineParts = input.substring(9).split(" /by", 2);
                        String desc = deadlineParts[0].trim();
                        String by = deadlineParts[1].trim();
                        newTask = new Deadline(desc, by);
                    } else {
                        String[] eventPart1 = input.substring(6).split(" /from ", 2);
                        String desc = eventPart1[0].trim();
                        String[] eventPart2 = eventPart1[1].split(" /to ", 2);
                        String from = eventPart2[0].trim();
                        String to = eventPart2[1].trim();
                        newTask = new Event(desc, from, to);
                    }

                    taskList[taskCount] = newTask;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:\n  " + newTask);
                    System.out.println("Now you have " + taskCount+ " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } catch (Exception e) {
                    System.out.println("Please type a valid input");
                }


            } else {
                taskList[taskCount] = new Task(input);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
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
