package yandex.practicum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest {

    ITaskManager taskManager = Managers.getDefault();

    Task taskOne = taskManager.createTask(new Task("nameTaskOne", "description", 1, TaskStatus.NEW,
            LocalDateTime.of(2022,11,27,22,0), Duration.ofHours(1)));
    Epic epicOne = taskManager.createEpic(new Epic("nameEpicOne", "description", 2, TaskStatus.NEW, new ArrayList<>()));
    Subtask subtaskOne = taskManager.createSubtask(new Subtask("nameSubOne", "description", 3, TaskStatus.NEW,
            epicOne.getId(), LocalDateTime.of(2023,11,27,23,0), Duration.ofHours(1)));
    Subtask subtaskTwo = taskManager.createSubtask(new Subtask("nameSubTwo", "description", 4, TaskStatus.NEW,
            epicOne.getId(), LocalDateTime.of(2021,11,27,22,0), Duration.ofHours(1)));
    Subtask subtaskThree = taskManager.createSubtask(new Subtask("nameSubThree", "description", 5, TaskStatus.NEW,
            epicOne.getId()));

    @Test
    public void deleteEpicAndSubtaskHistory() {
        taskManager.getTaskById(taskOne.getId());
        taskManager.getSubtaskById(subtaskOne.getId());
        taskManager.getSubtaskById(subtaskTwo.getId());
        taskManager.getSubtaskById(subtaskThree.getId());
        taskManager.removeEpicById(epicOne.getId());
        assertEquals(taskManager.getHistory(), List.of(taskOne));
    }

    @Test
    public void clearHistory() {
        taskManager.getTaskById(taskOne.getId());
        taskManager.getEpicById(epicOne.getId());
        taskManager.getSubtaskById(subtaskOne.getId());
        taskManager.getSubtaskById(subtaskTwo.getId());
        taskManager.getSubtaskById(subtaskThree.getId());
        taskManager.removeTaskById(taskOne.getId());
        taskManager.removeEpicById(epicOne.getId());
        assertEquals(taskManager.getHistory(), List.of());
    }
}
