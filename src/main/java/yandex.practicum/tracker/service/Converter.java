package yandex.practicum.tracker.service;

import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Task fromStringToTask(String value) {
        String[] taskLines = value.split(",", 9);
        int id = Integer.parseInt(taskLines[0]);
        String name = taskLines[2];
        String type = taskLines[1];
        TaskStatus status = null;
       if (!taskLines[3].isEmpty()) {
           status = TaskStatus.valueOf(taskLines[3]);
       }
        String description = taskLines[4];
        Duration duration =null;
        if(!taskLines[5].isEmpty()) {
            duration = Duration.parse(taskLines[5]);
        }
        LocalDateTime startTime = null;
        if(!taskLines[6].isEmpty()) {
            startTime = LocalDateTime.parse(taskLines[6],formatter);
        }
        if (type.equals("EPIC")) {
            LocalDateTime endTime = null;
            if(!taskLines[8].isEmpty()) {
                startTime = LocalDateTime.parse(taskLines[8],formatter);
            }
            return new Epic(name, description);
        } else if (type.equals("SUBTASK") && taskLines.length >= 9) {
            int epicId = 0;
            if(!taskLines[7].isEmpty()) {
                epicId = Integer.parseInt(taskLines[7].trim());
            }
            return new Subtask(name, description, id, status, epicId, startTime, duration);
        } else {
            return new Task(name, description, id, status, startTime, duration);
        }
    }

    public static String taskToString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",");
        sb.append(task.getType().name()).append(",");
        sb.append(task.getName()).append(",");
        if(task.getStatus() != null) {
            sb.append(task.getStatus().toString()).append(",");
        }else {
            sb.append(",");
        }
        sb.append(task.getDescription()).append(",");

        if (task.getDuration() != null) {
            sb.append(task.getDuration().toString());
            sb.append(",");
        } else {
            sb.append(",");
        }
        if (task.getStartTime() != null) {
            sb.append(task.getStartTime().format(formatter).toString());
            sb.append(",");
        } else {
            sb.append(",");
        }
        if (task instanceof Subtask) {
            sb.append(",").append(((Subtask) task).getEpicId());
        }
        if (task instanceof Epic) {
            if (((Epic) task).getEndTime() != null) {
                sb.append(",").append(((Epic) task).getEndTime().toString());
            } else {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}





