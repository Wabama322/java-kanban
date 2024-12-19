package yandex.practicum;

import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println("Поехали!");
        ITaskManager taskManager = new InMemoryTaskManager();

        Task taskOne = new Task("nameTask1", "description", 1, TaskStatus.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2023, Month.JUNE, 25, 12, 0));
        Task taskTwo = new Task("nameTask2", "description", 2, TaskStatus.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2023, Month.JUNE, 26, 12, 0));

        Epic epicOne = new Epic("nameEpic1", "description", 3,
                TaskStatus.IN_PROGRESS, new ArrayList<>());
        Subtask subtaskOne = new Subtask("nameSub1", "description", 4, TaskStatus.IN_PROGRESS,
                epicOne.getId());
        Subtask subtaskTwo = new Subtask("nameSub2", "description", 5, TaskStatus.NEW,
                epicOne.getId());
        Epic epicTwo = new Epic("nameEpic2", "description", 6,
                TaskStatus.IN_PROGRESS, new ArrayList<>());
        Subtask subtaskThree = new Subtask("nameSub3", "description", 7, TaskStatus.IN_PROGRESS,
                epicTwo.getId());
        Subtask subtaskFour = new Subtask("nameSub4", "description", 8, TaskStatus.NEW,
                epicTwo.getId());
        Epic epicThree = new Epic("nameEpic3", "description", 9,
                TaskStatus.IN_PROGRESS, new ArrayList<>());

        Task createdTaskOne = taskManager.createTask(taskOne);
        Task createdTaskTwo = taskManager.createTask(taskTwo);

        Epic createdEpicOne = taskManager.createEpic(epicOne);
        Epic createdEpicTwo = taskManager.createEpic(epicTwo);
        Epic createdEpicThree = taskManager.createEpic(epicThree);

        Subtask createdSubtaskOne = taskManager.createSubtask(subtaskOne);
        Subtask createdSubtaskTwo = taskManager.createSubtask(subtaskTwo);
        Subtask createdSubtaskThree = taskManager.createSubtask(subtaskThree);

        subtaskOne.setEpicId(createdEpicOne.getId());
        subtaskTwo.setEpicId(createdEpicOne.getId());
        subtaskThree.setEpicId(createdEpicTwo.getId());
        Subtask createdSubtaskFour = taskManager.createSubtask(subtaskFour);
        subtaskFour.setEpicId(createdEpicOne.getId());

        createdEpicOne.setSubtasks(new ArrayList<>(List.of(createdSubtaskOne.getId(), createdSubtaskTwo.getId())));
        createdEpicTwo.setSubtasks(new ArrayList<>(List.of(createdSubtaskThree.getId())));

        taskManager.updateEpic(createdEpicOne);
        taskManager.updateEpic(createdEpicTwo);

        System.out.println("СПИСОК ЗАДАЧ: ");
        System.out.println(createdTaskOne);
        System.out.println(createdTaskTwo);
        System.out.println("Количество задач: " + taskManager.getAllTasks().size());
        System.out.println();

        System.out.println("СПИСОК ЭПИКОВ: ");
        System.out.println(createdEpicOne);
        System.out.println(createdEpicTwo);
        System.out.println(createdEpicThree);

        System.out.println("Количество эпиков: " + taskManager.getAllEpics().size());
        System.out.println();

        System.out.println("СПИСОК ПОДЗАДАЧ: ");
        System.out.println(createdSubtaskOne);
        System.out.println(createdSubtaskTwo);
        System.out.println(createdSubtaskThree);
        System.out.println("Количество подзадач: " + taskManager.getAllSubtasks().size());
        System.out.println();

        System.out.println("ПРОВЕРКА СТАТУСОВ: ");
        System.out.println("Статус задачи 1: " + createdTaskOne.getStatus());
        System.out.println("Статус задачи 2: " + createdTaskTwo.getStatus());

        System.out.println("Статус эпика 1: " + createdEpicOne.getStatus());
        System.out.println("Статус эпика 2: " + createdEpicTwo.getStatus());
        System.out.println("Статус эпика 3: " + createdEpicThree.getStatus());

        System.out.println("Статус подзадачи 1: " + createdSubtaskOne.getStatus());
        System.out.println("Статус подзадачи 2: " + createdSubtaskTwo.getStatus());
        System.out.println("Статус подзадачи 3: " + createdSubtaskThree.getStatus());
        System.out.println();

        System.out.println("СМЕНА СТАТУСОВ");
        System.out.println();

        createdTaskTwo.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(createdTaskTwo);

        createdSubtaskTwo.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(createdSubtaskTwo);

        createdSubtaskThree.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(createdSubtaskThree);

        System.out.println("ПРОВЕРКА НОВЫХ СТАТУСОВ: ");
        System.out.println("Статус задачи 1: " + createdTaskOne.getStatus());
        System.out.println("Статус задачи 2: " + createdTaskTwo.getStatus());
        System.out.println("Статус эпика 1: " + createdEpicOne.getStatus());
        System.out.println("Статус эпика 2: " + createdEpicTwo.getStatus());
        System.out.println("Статус подзадачи 1: " + createdSubtaskOne.getStatus());
        System.out.println("Статус подзадачи 2: " + createdSubtaskTwo.getStatus());
        System.out.println("Статус подзадачи 3: " + createdSubtaskThree.getStatus());
        System.out.println();

        System.out.println("Количество задач: " + taskManager.getAllTasks().size());
        System.out.println("Количество эпиков: " + taskManager.getAllEpics().size());
        System.out.println("Количество подзадач: " + taskManager.getAllSubtasks().size());
        System.out.println();

        System.out.println("УДАЛЯЕМ ЗАДАЧУ, ЭПИК и ПОДЗАДАЧУ");

        taskManager.removeTaskById(createdTaskTwo.getId()); //удаление: задача 2
        taskManager.removeEpicById(createdEpicOne.getId()); //удаление: эпик 1
        taskManager.removeSubtaskById(createdSubtaskFour.getId());//удаление подзадачи 4

        System.out.println("Количество задач: " + taskManager.getAllTasks().size());
        System.out.println("Количество эпиков: " + taskManager.getAllEpics().size());
        System.out.println("Количество подзадач: " + taskManager.getAllSubtasks().size());

        System.out.println("ИСТОРИЯ");
        List<Task> history = taskManager.getHistory();
        System.out.println("В истории сохранено " + history.size() + " просмотров");

        taskManager.getTaskById(createdTaskOne.getId());
        taskManager.getSubtaskById(createdSubtaskOne.getId());

        history = taskManager.getHistory();
        System.out.println("В истории сохранено " + history.size() + " просмотров");
        System.out.println(history);
    }
}

