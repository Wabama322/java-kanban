package yandex.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskTest {
    @Test
    void subtaskAreEqualToEachOtherIfTheirIdIsEqual() {
        Subtask subtaskOne = new Subtask("Подготовка к отпуску", "бронирование билетов и отеля", 2,
                TaskStatus.IN_PROGRESS, 5);
        Subtask subtaskTwo = new Subtask("Купить продукты домой", "составить список покупок", 2,
                TaskStatus.NEW, 5);
        assertEquals(subtaskOne, subtaskTwo);
    }
}
