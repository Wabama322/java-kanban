package yandex.practicum.tracker.service;

public class Managers {

    public static ITaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static IHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
