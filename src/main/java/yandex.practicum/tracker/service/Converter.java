package yandex.practicum.tracker.service;

import yandex.practicum.model.*;

import java.util.ArrayList;

public class Converter {
    public static Task fromStringToTask(String value) {
        String[] taskLines = value.split(",", 6);
        int id = Integer.parseInt(taskLines[0]);
        String name = taskLines[2];
        String type = taskLines[1];
        TaskStatus status = TaskStatus.valueOf(taskLines[3]);
        String description = taskLines[4];
        if (type.equals("EPIC")) {
            return new Epic(name, description, id, status, new ArrayList<>());
        } else if (type.equals("SUBTASK") && taskLines.length >= 6) {
            int epicId = Integer.parseInt(taskLines[5].trim());
            return new Subtask(name, description, id, status, epicId);
        } else {
            return new Task(name, description, id, status);
        }
    }

    public static String taskToString(Task task) {
        StringBuilder builder = new StringBuilder();
        builder.append(task.getId()).append(",");
        builder.append(task.getType()).append(",");
        builder.append(task.getName()).append(",");
        builder.append(task.getStatus()).append(",");
        builder.append(task.getDescription());
        if (task.getType() == Types.SUBTASK) {
            builder.append(",").append(((Subtask) task).getEpicId());
        }
        builder.append("\n");
        return builder.toString();
    }
}
