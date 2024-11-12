package yandex.practicum.tracker.service;

import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;

import java.util.ArrayList;

import static yandex.practicum.model.Types.EPIC;
import static yandex.practicum.model.Types.SUBTASK;

public class Converter {
    public static Task fromStringToTask(String value) {
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
    public static String taskToString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription() + ","
                + task.getEpicId() + "\n";

    }
}
