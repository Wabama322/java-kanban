package yandex.practicum.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.InMemoryTaskManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    Task taskOne;
    Task taskTwo;
    Epic epicOne;
    Subtask subtaskOne;

    @BeforeEach
    public void beforeEach() {
        taskOne = new Task("Сделать уборку дома", "пропылесосить, протереть пыль", 0, TaskStatus.DONE);
        taskTwo = new Task("Почитать книгу", "дойти до следующей главы", 0, TaskStatus.NEW);
        epicOne = new Epic("Подготовка к отпуску", "бронирование билетов и отеля", 0,
                TaskStatus.NEW, new ArrayList<>());
        subtaskOne = new Subtask("Шоппинг", "покупка необходимых вещей и сбор аптечки", 0,
                TaskStatus.NEW, 50);
        manager.createTask(taskOne);
        manager.createTask(taskTwo);
        manager.createEpic(epicOne);
        manager.createSubtask(subtaskOne);
    }
    @Test
    public void epicStatusShouldBeNew() {

        Epic epic = new Epic("name", "Description", 0, TaskStatus.DONE, new ArrayList<>());
        manager.createEpic(epic);

        int id = epic.getId();

        Subtask subtask = new Subtask("name", "disc", 1, TaskStatus.NEW, id);
        Subtask subtask2 = new Subtask("name2", "disc2", 2, TaskStatus.NEW, id);

        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);

        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void epicStatusShouldBeDone() {

        Epic epic = new Epic("name", "Description", 0, TaskStatus.DONE, new ArrayList<>());
        manager.createEpic(epic);

        int id = epic.getId();

        Subtask subtask = new Subtask("name", "Description", 1, TaskStatus.DONE, id);
        Subtask subtask2 = new Subtask("name2", "Description2", 2, TaskStatus.DONE, id);

        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);

        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void epicStatusShouldBeInProgress() {
        Epic epic = new Epic("name", "Description", 0, TaskStatus.DONE, new ArrayList<>());
        manager.createEpic(epic);

        int id = epic.getId();

        Subtask subtask = new Subtask("name", "Description", 1, TaskStatus.DONE, id);
        Subtask subtask2 = new Subtask("name2", "Description2", 2, TaskStatus.NEW, id);

        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }
    @Test
    public void checkHistorySize() {
        Task newTask = manager.createTask(new Task());
        Epic newEpic = manager.createEpic(new Epic());

        for (int i = 0; i < 10; i++) {
            manager.getTaskById(newTask.getId());
            manager.getEpicById(newEpic.getId());
        }

        Assertions.assertEquals(10, manager.getHistory().size(), "Размер списка истории не превышает 10");
    }

    @Test
    public void createTaskAndCheckThatItIsNotEmpty() {
        Task createdTask1 = manager.createTask(taskOne);
        assertNotNull(createdTask1);
    }

    @Test
    public void createEpicAndCheckThatItIsNotEmpty() {
        Epic createdEpic1 = manager.createEpic(epicOne);
        assertNotNull(createdEpic1);
    }

    @Test
    public void createSubtaskAndCheckThatItIsNotEmpty() {
        Subtask createdSubtask1 = manager.createSubtask(subtaskOne);
        assertNotNull(createdSubtask1);
    }

    @Test
    public void canFindATaskByItsId() {
        Task foundTask = manager.getTaskById(taskOne.getId());
        assertEquals(taskOne, foundTask);
    }

    @Test
    public void canFindAEpicByItsId() {
        Epic foundEpic = manager.getEpicById(epicOne.getId());
        assertEquals(epicOne, foundEpic);
    }

    @Test
    public void canFindASubtaskByItsId() {
        Subtask foundSubtask = manager.getSubtaskById(subtaskOne.getId());
        assertEquals(subtaskOne, foundSubtask);
    }

    @Test
    public void givenAndGeneratedIdDoNotConflict() {
        Task task1 = new Task();
        task1.setId(3);
        Task task2 = new Task();
        manager.createTask(task1);
        manager.createTask(task2);
        assertEquals(4, manager.getAllTasks().size());
    }

    @Test
    public void taskUnchangedAfterAdding() {
        Task taskFromManager = manager.getTaskById(taskOne.getId());

        assertNotNull(taskFromManager);
        assertEquals(taskOne.getName(), taskFromManager.getName());
        assertEquals(taskOne.getDescription(), taskFromManager.getDescription());
        assertEquals(taskOne.getStatus(), taskFromManager.getStatus());

    }
}