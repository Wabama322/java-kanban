package yandex.practicum.tracker.service;

import yandex.practicum.model.Task;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.TaskStatus;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class InMemoryTaskManager implements ITaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final IHistoryManager historyManager;

    public static int count = 0;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.setStatus(TaskStatus.NEW);
            epic.getSubtasks().clear();
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpic());
        if (epic != null) {
            int id = generateId();
            subtask.setId(id);
            subtasks.put(subtask.getId(), subtask);
            epic.getSubtasks().add(subtask.getId());
            changeEpicStatus(epic);
        }
        return subtask;
    }

    @Override
    public void updateTask(Task task) { //обновление Таска
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        final Epic oldEpic = epics.get(epic.getId());
        if (oldEpic != null) {
            oldEpic.setNameTask(epic.getName());
            oldEpic.setDescription(epic.getDescription());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpic());
            changeEpicStatus(epic);
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        List<Integer> epicSubtasks = epic.getSubtasks();
        for (Integer subtask : epicSubtasks) {
            subtasks.remove(subtask);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpic());
            List<Integer> epicSubtasks = epic.getSubtasks();
            epicSubtasks.remove((Integer) id);
            changeEpicStatus(epic);
        }
    }

    @Override
    public List<Subtask> getSubtaskList(Epic epic) {
        List<Integer> epicSubtasks = epic.getSubtasks();
        List<Subtask> subtaskList = new ArrayList<>();
        for (Integer id : epicSubtasks) {
            Subtask subtask = subtasks.get(id);
            subtaskList.add(subtask);
        }
        return subtaskList;
    }

    @Override
    public void changeEpicStatus(Epic epic) {
        List<Subtask> subtasks1 = getSubtaskList(epic);

        int countDone = 0;
        int countNew = 0;

        TaskStatus status = TaskStatus.NEW;

        for (Subtask subtask : subtasks1) {
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
            if (subtask.getStatus().equals(TaskStatus.NEW)) {
                countNew += 1;
            }
            if (subtask.getStatus().equals(TaskStatus.DONE)) {
                countDone += 1;
            }
        }
        if (countNew == subtasks1.size()) {
            status = TaskStatus.NEW;
        } else if (countDone == subtasks1.size()) {
            status = TaskStatus.DONE;
        } else {
            status = TaskStatus.IN_PROGRESS;
        }
        epic.setStatus(status);
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int generateId() {
        return count++;
    }
}
