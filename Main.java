import Tasks.Task;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        Task taskOne = new Task("Сделать уборку дома", "пропылесосить, протереть пыль", 1, TaskStatus.DONE);
        Task taskTwo = new Task("Почитать книгу", "дойти до следующей главы", 2, TaskStatus.NEW);

        Epic epicOne = new Epic("Подготовка к отпуску", "бронирование билетов и отеля", 3,
                TaskStatus.IN_PROGRESS, new ArrayList<>());
        Subtask subtaskOne = new Subtask("Шоппинг", "покупка необходимых вещей и сбор аптечки", 4,
                TaskStatus.DONE, 0);
        Subtask subtaskTwo = new Subtask("Сбор чемодана", "сложить вещи и документы", 5, TaskStatus.NEW, 0);

        Epic epicTwo = new Epic("Купить продукты домой", "составить список покупок", 6,
                TaskStatus.NEW, new ArrayList<>());
        Subtask subTaskThree = new Subtask("Зайти после работы в магазин", "купить продукты согласно списка", 7,
                TaskStatus.NEW, 1);

        Task createdTaskOne = taskManager.createTask(taskOne);
        Task createdTaskTwo = taskManager.createTask(taskTwo);

        Epic createdEpicOne = taskManager.createEpic(epicOne);
        Epic createdEpicTwo = taskManager.createEpic(epicTwo);

        Subtask createdSubtaskOne = taskManager.createSubtask(subtaskOne);
        Subtask createdSubtaskTwo = taskManager.createSubtask(subtaskTwo);
        Subtask createdSubtaskThree = taskManager.createSubtask(subTaskThree);

        subtaskOne.setEpic(createdEpicOne.getId());
        subtaskTwo.setEpic(createdEpicOne.getId());
        subTaskThree.setEpic(createdEpicTwo.getId());

        createdEpicOne.setSubtasks(new ArrayList<>(List.of(createdSubtaskOne.getId(), createdSubtaskTwo.getId())));
        createdEpicTwo.setSubtasks(new ArrayList<>(List.of(createdSubtaskThree.getId())));

        taskManager.updateEpic(createdEpicOne);
        taskManager.updateEpic(createdEpicTwo);

        System.out.println("СПИСОК ЗАДАЧ: ");
        System.out.println(createdTaskOne);
        System.out.println(createdTaskTwo);

        System.out.println("СПИСОК ЭПИКОВ: ");
        System.out.println(createdEpicOne);
        System.out.println(createdEpicTwo);

        System.out.println("СПИСОК ПОДЗАДАЧ: ");
        System.out.println(createdSubtaskOne);
        System.out.println(createdSubtaskTwo);
        System.out.println(createdSubtaskThree);

        System.out.println("ПРОВЕРКА СТАТУСОВ: ");
        System.out.println("Статус задачи 1: " + createdTaskOne.getStatus());
        System.out.println("Статус задачи 2: " + createdTaskTwo.getStatus());
        System.out.println("Статус эпика 1: " + createdEpicOne.getStatus());
        System.out.println("Статус эпика 2: " + createdEpicTwo.getStatus());
        System.out.println("Статус подзадачи 1: " + createdSubtaskOne.getStatus());
        System.out.println("Статус подзадачи 2: " + createdSubtaskTwo.getStatus());
        System.out.println("Статус подзадачи 3: " + createdSubtaskThree.getStatus());

        System.out.println("СМЕНА СТАТУСА");
        createdSubtaskTwo.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(createdSubtaskTwo);
        createdTaskTwo.setStatus(TaskStatus.DONE);
        taskManager.updateTask(createdTaskTwo);

        System.out.println("ПРОВЕРКА НОВЫХ СТАТУСОВ: ");
        System.out.println("Статус подзадачи 2: " + createdSubtaskTwo.getStatus());
        System.out.println("Статус эпика 1: " + createdEpicOne.getStatus());
        System.out.println("Статус задачи 2: " + createdTaskTwo.getStatus());

        System.out.println("Количество задач: " + taskManager.getAllTasks().size());
        System.out.println("Количество эпиков: " + taskManager.getAllEpics().size());
        System.out.println("Количество подзадач: " + taskManager.getAllSubtasks().size());

        System.out.println("УДАЛЯЕМ ЗАДАЧУ И ЭПИК");
        taskManager.removeTaskById(createdTaskOne.getId());
        taskManager.removeEpicById(createdEpicTwo.getId());

        System.out.println("Количество задач: " + taskManager.getAllTasks().size());
        System.out.println("Количество эпиков: " + taskManager.getAllEpics().size());
    }


}
