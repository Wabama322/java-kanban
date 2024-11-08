package yandex.practicum.model;

public enum Types {
    TASK("Обычная", Task.class),
    SUBTASK("Подзадача", Subtask.class),
    EPIC("Составная", Epic.class);

    private final String name;
    private final Class<? extends Task> type;

    Types(String name, Class<? extends Task> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Task> getType() {
        return type;
    }



}
