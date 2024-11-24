package yandex.practicum.model;

public enum Types {
    TASK("Обычная"),
    SUBTASK("Подзадача"),
    EPIC("Составная");

    private final String name;

    Types(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
