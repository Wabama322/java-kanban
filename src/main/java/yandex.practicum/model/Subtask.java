package yandex.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private Integer epic;

    public Subtask(String nameTask, String description, int id, TaskStatus status, Integer epic) {
        super(nameTask, description, id, status);
        this.epic = epic;
    }

    public Subtask(String nameTask, String description, int id, TaskStatus status, Integer epic,
                   LocalDateTime startTime, Duration duration) {
        super(nameTask, description, id, status, startTime, duration);
        this.epic = epic;
    }

    public Subtask() {
    }

    public Integer getEpicId() {
        return epic;
    }

    public void setEpicId(Integer epic) {
        this.epic = epic;
    }


    @Override
    public Types getType() {
        return Types.SUBTASK;
    }

    @Override
    public String toString() {
        return super.toString() + epic;
    }
}