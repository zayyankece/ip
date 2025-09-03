package joko.task;

public class Task {
    protected final String desc;
    protected boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ");
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    @Override
    public String toString() {
        return getStatus() + desc;
    }
}

