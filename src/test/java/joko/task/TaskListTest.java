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
        Storage dummyStorage = new Storage("temp_tasks.txt"); // won’t interfere with real file
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
    void deleteTask_withInvalidIndex_shouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
    }

    @Test
    void markTask_shouldUpdateIsDone() {
        Task t = new Task("jump");
        taskList.addTask(t);
        assertFalse(t.isDone());
        taskList.markTask(0, true);
        assertTrue(t.isDone());
    }

    @Test
    void markTask_withInvalidIndex_shouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.markTask(5, true));
    }

    @Test
    void findTasks_shouldReturnMatchingTasks() {
        Task t1 = new Task("read book");
        Task t2 = new Task("swim");
        taskList.addTask(t1);
        taskList.addTask(t2);

        var results = taskList.findTasks("read");
        assertEquals(1, results.size());
        assertEquals(t1, results.get(0));
    }

    @Test
    void findTasks_withNoMatches_shouldReturnEmptyList() {
        taskList.addTask(new Task("run"));
        var results = taskList.findTasks("swim");
        assertTrue(results.isEmpty());
    }

    @Test
    void getTasks_shouldReturnLiveList() {
        Task t = new Task("dance");
        taskList.addTask(t);
        assertTrue(taskList.getTasks().contains(t));
    }

    @Test
    void contains_shouldReturnTrueIfTaskExists() {
        Task t = new Task("yoga");
        taskList.addTask(t);
        assertTrue(taskList.contains(t));
        assertFalse(taskList.contains(new Task("nonexistent")));
    }
}
