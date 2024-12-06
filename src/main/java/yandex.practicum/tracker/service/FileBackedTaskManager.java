package yandex.practicum.tracker.service;

import yandex.practicum.exception.LoadFromFileException;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;

import java.io.*;
import java.nio.file.Path;

import static java.util.Objects.isNull;
import static yandex.practicum.model.Types.EPIC;
import static yandex.practicum.model.Types.TASK;

public class FileBackedTaskManager extends InMemoryTaskManager implements ITaskManager {
    private Path file;
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    public FileBackedTaskManager(Path file) {
        super();
        this.file = file;
    }

    public FileBackedTaskManager() {
        super();
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
    public Task createTask(Task task) {
        Task task1 = super.createTask(task);
        save();
        return task1;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic epic1 = super.createEpic(epic);
        save();
        return epic1;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask subtask1 = super.createSubtask(subtask);
        save();
        return subtask1;
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

    public void save() {
        final String HEADER = "id,type,name,status,description,duration,startTime,epic, endTime\n";
        try (FileWriter writer = new FileWriter(file.toFile(), false)) {
            writer.write(HEADER);
            for (Integer key : tasks.keySet()) {
                writer.write(Converter.taskToString(tasks.get(key)) + "\n");
            }
            for (Integer key : epics.keySet()) {
                writer.write(Converter.taskToString(epics.get(key)) + "\n");
            }
            for (Integer key : subtasks.keySet()) {
                writer.write(Converter.taskToString(subtasks.get(key)) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromFile(File file) {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toString()))) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (isNull(line) || line.isEmpty()) {
                    break;
                }
                Task task = Converter.fromStringToTask(line);
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

                if (task.getId() > maxId) {
                    maxId = task.getId();
                }
                count = maxId;
            }
        } catch (FileNotFoundException e) {
            throw new LoadFromFileException("Файл не найден...");
        } catch (IOException e) {
            throw new LoadFromFileException("Ошибка чтения файла...");
        }
    }
}





