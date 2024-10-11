package yandex.practicum.model;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String nameTask, String description, int id, TaskStatus status, int epicId) {
        super(nameTask, description, id, status);
        this.epicId = epicId;
    }

    public Subtask() {
    }

    public int getEpicId() {
        return this.epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
