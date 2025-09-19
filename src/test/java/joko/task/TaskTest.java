package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void constructor_shouldSetDescriptionAndNotDone() {
        Task t = new Task("swim");
        assertEquals("swim", t.getDesc());
        assertFalse(t.isDone(), "New task should not be marked as done by default");
    }

    @Test
    void setDone_shouldChangeIsDoneStatus() {
        Task t = new Task("swim");
        assertFalse(t.isDone());
        t.setDone(true);
        assertTrue(t.isDone());
        t.setDone(false);
        assertFalse(t.isDone());
    }

    @Test
    void getStatus_shouldReturnCorrectSymbol() {
        Task t = new Task("swim");
        assertEquals("[ ] ", t.getStatus());
        t.setDone(true);
        assertEquals("[X] ", t.getStatus());
    }

    @Test
    void toString_shouldShowStatusAndDescription() {
        Task t = new Task("swim");
        assertEquals("[ ] swim", t.toString());
        t.setDone(true);
        assertEquals("[X] swim", t.toString());
    }

    @Test
    void emptyDescription_shouldStillWork() {
        Task t = new Task("");
        assertEquals("[ ] ", t.toString());
    }
}
