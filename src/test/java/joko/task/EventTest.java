package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void constructor_shouldSetAllFields() {
        Event e = new Event("meeting", "10:00", "12:00");
        assertEquals("meeting", e.getDesc());
        assertEquals("10:00", e.getFrom());
        assertEquals("12:00", e.getTo());
        assertFalse(e.isDone());
    }

    @Test
    void setDone_shouldChangeIsDoneStatus() {
        Event e = new Event("meeting", "10:00", "12:00");
        assertFalse(e.isDone());
        e.setDone(true);
        assertTrue(e.isDone());
        e.setDone(false);
        assertFalse(e.isDone());
    }

    @Test
    void toString_shouldIncludeTypeStatusAndTime() {
        Event e = new Event("meeting", "10:00", "12:00");
        assertEquals("[E][ ] meeting(from: 10:00 to: 12:00)", e.toString());
        e.setDone(true);
        assertEquals("[E][X] meeting(from: 10:00 to: 12:00)", e.toString());
    }

    @Test
    void equals_shouldReturnTrueForSameDescriptionAndTime() {
        Event e1 = new Event("meeting", "10:00", "12:00");
        Event e2 = new Event("meeting", "10:00", "12:00");
        assertEquals(e1, e2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentDescription() {
        Event e1 = new Event("meeting", "10:00", "12:00");
        Event e2 = new Event("call", "10:00", "12:00");
        assertNotEquals(e1, e2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentTime() {
        Event e1 = new Event("meeting", "10:00", "12:00");
        Event e2 = new Event("meeting", "11:00", "12:00");
        Event e3 = new Event("meeting", "10:00", "13:00");
        assertNotEquals(e1, e2);
        assertNotEquals(e1, e3);
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        Event e = new Event("meeting", "10:00", "12:00");
        Task t = new Task("meeting");
        assertNotEquals(e, t);
    }
}
