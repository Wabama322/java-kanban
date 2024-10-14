package yandex.practicum.tracker.service;

import yandex.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements IHistoryManager {
    private static class Node {
        public Task item;
        public Node next;
        public Node prev;

        public Node(Node prev, Task element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    HashMap<Integer, Node> history = new HashMap<>();
    Node first;
    Node last;


    @Override
    public void add(Task task) {
        if (history.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
        history.put(task.getId(), last);
    }


    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node != null) {
            removeNode(node);
        }
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private List<Task> getTasks() {
        ArrayList<Task> task = new ArrayList<>();
        Node current = first;
        while (current != null) {
            task.add(current.item);
            current = current.next;
        }
        return task;
    }

    private void linkLast(Task task) {
        final Node newNode = new Node(last, task, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
    }

    private void removeNode(Node node) {
        if (node.prev == null) {
            if (node.next != null) {
                node.next.prev = null;
            }
            first = node.next;
        } else if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        history.remove(node.item.getId());
        if (history.isEmpty()) {
            last = null;
            first = null;
        }
    }
}