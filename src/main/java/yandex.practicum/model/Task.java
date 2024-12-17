package yandex.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable {
    private String nameTask;
    private String description;
    private int id;
    private TaskStatus status;
    private Types type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(int i, String description, String testingTaskHttp, TaskStatus aNew, LocalDateTime now,
                Duration duration) {
    }

    public Task(String nameTask, String taskDescription, TaskStatus status, LocalDateTime startTime,
                Duration duration) {
        this.nameTask = nameTask;
        this.description = taskDescription;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String nameTask, String description) {
        this.id = id;
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
    }

    public Task(String nameTask, String description, int id, TaskStatus status, LocalDateTime startTime,
                Duration duration) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String nameTask, String description, int id, TaskStatus status) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.status = status;
        setType();
    }

    public Task(String nameTask, String description, int id, TaskStatus status, Duration duration,
                LocalDateTime startTime) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        setType();
    }

    public Task() {
        setType();
    }

    public Task(String nameTask, String description, TaskStatus status) {
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
        setType();
    }

    public String getName() {
        return nameTask;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Types getType() {
        return Types.TASK;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            throw new IllegalStateException("Время начала и продолжительность не заданы...");
        }
        return startTime.plus(duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + "," + type.name() + "," + nameTask + ","
                + status.name() + "," + description + "," + duration + "," + startTime + ",";
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        return startTime.compareTo(task.startTime);
    }

    private void setType() {
        for (Types type : Types.values()) {
            if (this.getClass().equals(type.getType())) {
                this.type = type;
            }
        }
    }
}














