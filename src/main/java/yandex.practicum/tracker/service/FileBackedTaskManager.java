package yandex.practicum.tracker.service;

import yandex.practicum.exception.SaveManagerException;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.util.Objects.isNull;
import static yandex.practicum.model.Types.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;

    public FileBackedTaskManager(Path file) {
        super();
        this.file = file;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file.toFile(), false)) {
            writer.write("id, type, name, status, description, epic\n");
            for (Integer key : tasks.keySet()) {
                writer.write(tasks.get(key).toString() + "\n");
            }
            for (Integer key : epics.keySet()) {
                writer.write(epics.get(key).toString() + "\n");
            }
            for (Integer key : subtasks.keySet()) {
                writer.write(subtasks.get(key).toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // заменить название
        }
    }

    private static Task fromString(String value) {
        String[] taskLines = value.split(",", 6);
        int id = Integer.parseInt(taskLines[0]);
        String name = taskLines[2];
        String type = taskLines[1];
        TaskStatus status = TaskStatus.valueOf(taskLines[3]);
        String description = taskLines[4];
        if (type.equals(EPIC.name())) {
            return new Epic(name, description, id, status, new ArrayList<>());
        } else if (type.equals(SUBTASK.name())) {
            int epic = Integer.parseInt(taskLines[5]);
            return new Subtask(name, description, id, status, epic);
        } else {
            return new Task(name, description, id, status);
        }
    }

    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toString()))) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (isNull(line) || line.isEmpty()) {
                    break;
                }
                Task task = fromString(line);
                if (task.getType().equals(TASK)) {
                    tasks.put(task.getId(), task);
                } else if (task.getType().equals(EPIC)) {
                    epics.put(task.getId(), (Epic) task);
                } else {
                    Subtask subtask = (Subtask) task;
                    subtasks.put(task.getId(), subtask);
                    Epic epic = epics.get(subtask.getEpicId());
                    epic.getSubtasks().add(subtask.getId());
                }
                count += 1;
            }
        } catch (FileNotFoundException e) {
            throw new SaveManagerException("Файфл не найден...");
        } catch (IOException e) {
            throw new SaveManagerException("Ошибка чтения файла...");
        }
    }
}
