package yandex.practicum.model;

import java.util.Objects;

public class Task {

    private String nameTask;
    private String description;
    private int id;
    private TaskStatus status;
    private Types type;

    public Task(String nameTask, String description, int id, TaskStatus status) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = Types.TASK;
    }

    public Task() {
        this.type = Types.TASK;
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
                + status.name() + "," + description + ",";

    }
}


