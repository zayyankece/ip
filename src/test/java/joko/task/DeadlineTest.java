package joko.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DeadlineTest {

    @Test
    void constructor_shouldSetDescriptionAndDeadline() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d = new Deadline("submit report", dt);
        assertEquals("submit report", d.getDesc());
        assertEquals(dt, d.getBy());
        assertFalse(d.isDone());
    }

    @Test
    void setDone_shouldChangeIsDoneStatus() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d = new Deadline("submit report", dt);
        assertFalse(d.isDone());
        d.setDone(true);
        assertTrue(d.isDone());
        d.setDone(false);
        assertFalse(d.isDone());
    }

    @Test
    void toString_shouldIncludeTypeStatusAndFormattedDeadline() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d = new Deadline("submit report", dt);
        assertEquals("[D][ ] submit report(by: Sep 19 2025 18:00) ", d.toString());
        d.setDone(true);
        assertEquals("[D][X] submit report(by: Sep 19 2025 18:00) ", d.toString());
    }

    @Test
    void equals_shouldReturnTrueForSameDescriptionAndDeadline() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d1 = new Deadline("submit report", dt);
        Deadline d2 = new Deadline("submit report", dt);
        assertEquals(d1, d2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentDescription() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d1 = new Deadline("submit report", dt);
        Deadline d2 = new Deadline("finish report", dt);
        assertNotEquals(d1, d2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentDeadline() {
        LocalDateTime dt1 = LocalDateTime.of(2025, 9, 19, 18, 0);
        LocalDateTime dt2 = LocalDateTime.of(2025, 9, 20, 18, 0);
        Deadline d1 = new Deadline("submit report", dt1);
        Deadline d2 = new Deadline("submit report", dt2);
        assertNotEquals(d1, d2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        LocalDateTime dt = LocalDateTime.of(2025, 9, 19, 18, 0);
        Deadline d = new Deadline("submit report", dt);
        Task t = new Task("submit report");
        assertNotEquals(d, t);
    }
}
