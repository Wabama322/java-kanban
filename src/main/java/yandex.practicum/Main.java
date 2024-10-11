package yandex.practicum;

import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.InMemoryTaskManager;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println("Поехали!");
        ITaskManager taskManager = new InMemoryTaskManager();

        Task taskOne = new Task("Сделать уборку дома", "пропылесосить, протереть пыль", 0, TaskStatus.DONE);
        Task taskTwo = new Task("Почитать книгу", "дойти до следующей главы", 1, TaskStatus.NEW);

        Epic epicOne = new Epic("Подготовка к отпуску", "бронирование билетов и отеля", 2,
                TaskStatus.IN_PROGRESS, new ArrayList<>());
        Subtask subtaskOne = new Subtask("Шоппинг", "покупка необходимых вещей и сбор аптечки", 5,
                TaskStatus.DONE, 2);
        Subtask subtaskTwo = new Subtask("Сбор чемодана", "сложить вещи и документы", 7, TaskStatus.IN_PROGRESS, 2);
        Epic epicTwo = new Epic("Купить продукты домой", "составить список покупок", 3,
                TaskStatus.NEW, new ArrayList<>());
        Subtask subtaskThree = new Subtask("Зайти после работы в магазин", "купить продукты согласно списка", 8,
                TaskStatus.NEW, 3);
        Subtask subtaskFour = new Subtask();
        Epic epicThree = new Epic("Сходить в библиотеку", "сдать книгу", 10,
                TaskStatus.NEW, new ArrayList<>());

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

