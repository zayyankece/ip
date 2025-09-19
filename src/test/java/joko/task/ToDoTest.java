package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ToDoTest {

    @Test
    void constructor_shouldSetDescriptionAndNotDone() {
        ToDo todo = new ToDo("buy milk");
        assertEquals("buy milk", todo.getDesc());
        assertFalse(todo.isDone());
    }

    @Test
    void setDone_shouldChangeIsDoneStatus() {
        ToDo todo = new ToDo("buy milk");
        assertFalse(todo.isDone());
        todo.setDone(true);
        assertTrue(todo.isDone());
    }

    @Test
    void toString_shouldIncludeTypeAndStatus() {
        ToDo todo = new ToDo("buy milk");
        assertEquals("[T][ ] buy milk", todo.toString());
        todo.setDone(true);
        assertEquals("[T][X] buy milk", todo.toString());
    }

    @Test
    void equals_shouldReturnTrueForSameDescription() {
        ToDo t1 = new ToDo("read book");
        ToDo t2 = new ToDo("read book");
        assertEquals(t1, t2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentDescription() {
        ToDo t1 = new ToDo("read book");
        ToDo t2 = new ToDo("swim");
        assertNotEquals(t1, t2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        ToDo t = new ToDo("read book");
        Task tBase = new Task("read book");
        assertNotEquals(t, tBase);
    }
}
