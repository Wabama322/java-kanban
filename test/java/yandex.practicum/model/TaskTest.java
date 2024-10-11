package yandex.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    @Test
    void taskAreEqualToEachOtherIfTheirIdIsEqual() {
        Task task1 = new Task("Сделать уборку дома", "пропылесосить, протереть пыль", 1, TaskStatus.DONE);
        Task task2 = new Task("Почитать книгу", "дойти до следующей главы", 1, TaskStatus.NEW);
        assertEquals(task1, task2);
    }
}