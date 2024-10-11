package yandex.practicum.tracker.service;

import java.util.List;

import yandex.practicum.model.Task;

public interface IHistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();
}
