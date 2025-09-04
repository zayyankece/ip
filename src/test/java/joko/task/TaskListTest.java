package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import joko.storage.Storage;

import java.util.ArrayList;

class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        Storage dummyStorage = new Storage("temp_tasks.txt"); // file won’t interfere
        taskList = new TaskList(new ArrayList<>(), dummyStorage);
    }

    @Test
    void addTask_shouldIncreaseSize() {
        int before = taskList.size();
        taskList.addTask(new Task("run"));
        assertEquals(before + 1, taskList.size());
    }

    @Test
    void deleteTask_shouldDecreaseSize() {
        Task t = new Task("swim");
        taskList.addTask(t);
        int before = taskList.size();
        Task removed = taskList.deleteTask(0);
        assertEquals(before - 1, taskList.size());
        assertEquals(t, removed);
    }

    @Test
    void markTask_shouldUpdateIsDone() {
        Task t = new Task("jump");
        taskList.addTask(t);
        assertFalse(t.isDone());
        taskList.markTask(0, true);
        assertTrue(t.isDone());
    }
}
