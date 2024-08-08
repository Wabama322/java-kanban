package Tasks;

public class Subtask extends Task {
    private Integer epic;

    public Subtask(String nameTask, String description, int id, TaskStatus status, Integer epic) {
        super(nameTask, description, id, status);
        this.epic = epic;
    }

    public Integer getEpic() {
        return epic;
    }

    public void setEpic(Integer epic) {
        this.epic = epic;
    }
}
