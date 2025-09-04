package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void setDone_shouldChangeIsDoneStatus() {
        Task t = new Task("swim");
        assertFalse(t.isDone()); // initially false
        t.setDone(true);
        assertTrue(t.isDone());  // should be true now
    }

    @Test
    void toString_shouldShowCorrectStatus() {
        Task t = new Task("swim");
        assertEquals("[ ] swim", t.toString());
        t.setDone(true);
        assertEquals("[X] swim", t.toString());
    }
}
