package yandex.practicum.tracker.service;

import yandex.practicum.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements IHistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final List<Task> history = new LinkedList<>();


    @Override
    public void add(Task task) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(history);
    }
}