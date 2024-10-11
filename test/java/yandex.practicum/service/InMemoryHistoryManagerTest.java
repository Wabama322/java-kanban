package yandex.practicum.service;

import org.junit.jupiter.api.Test;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.IHistoryManager;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.Managers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    IHistoryManager historyManager = Managers.getDefaultHistory();
    ITaskManager taskManager = Managers.getDefault();

    Task taskOne = taskManager.createTask(new Task("nameTaskOne", "description", 0, TaskStatus.DONE));
    Task taskTwo = taskManager.createTask(new Task("nameTaskTwo", "description", 0, TaskStatus.NEW));
    Epic epicOne = taskManager.createEpic(new Epic("nameEpicOne", "description", 0, TaskStatus.NEW, new ArrayList<>()));

    @Test
    public void addToHistory() {
        historyManager.add(taskOne);
        historyManager.add(taskTwo);
        historyManager.add(epicOne);
        assertEquals(historyManager.getHistory(), List.of(taskOne, taskTwo, epicOne));
    }

    @Test
    public void removeFirstHistory() {
        historyManager.add(taskOne);
        historyManager.add(taskTwo);
        historyManager.add(epicOne);
        historyManager.remove(taskOne.getId());
        assertEquals(historyManager.getHistory(), List.of(taskTwo, epicOne));
    }

    @Test
    public void removeLastHistory() {
        historyManager.add(taskOne);
        historyManager.add(taskTwo);
        historyManager.add(epicOne);
        historyManager.remove(epicOne.getId());
        assertEquals(historyManager.getHistory(), List.of(taskOne, taskTwo));
    }

    @Test
    public void removeMiddleHistory() {
        historyManager.add(taskOne);
        historyManager.add(taskTwo);
        historyManager.add(epicOne);
        historyManager.remove(taskTwo.getId());
        assertEquals(historyManager.getHistory(), List.of(taskOne, epicOne));
    }
}
