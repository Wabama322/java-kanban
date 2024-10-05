package yandex.practicum.service;

import org.junit.jupiter.api.Test;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.IHistoryManager;
import yandex.practicum.tracker.service.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryHistoryManagerTest {
    IHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Test
    public void newTasksRetainThePreviousVersionOfTheTaskAndItsData() {
        Task taskOne = new Task("Сделать уборку дома", "пропылесосить, протереть пыль", 0, TaskStatus.DONE);

        inMemoryHistoryManager.add(taskOne);

        taskOne.setNameTask("Новое имя");
        taskOne.setDescription("Новое описание");
        taskOne.setStatus(TaskStatus.IN_PROGRESS);

        List<Task> history = inMemoryHistoryManager.getHistory();

        assertFalse(history.isEmpty());
        assertEquals(taskOne.getName(), history.getFirst().getName());
        assertEquals(taskOne.getDescription(), history.getFirst().getDescription());
        assertEquals(taskOne.getStatus(), history.getFirst().getStatus());
    }
}