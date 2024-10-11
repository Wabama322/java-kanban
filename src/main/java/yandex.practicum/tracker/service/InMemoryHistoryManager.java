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
        Node node = history.get(task.getId());
        if (node != null) {
            removeNode(node);
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
        final Node currentNodeLast = last;
        final Node newNode = new Node(currentNodeLast, task, null);
        last = newNode;
        if (currentNodeLast == null) {
            first = newNode;
        } else {
            currentNodeLast.next = newNode;
            newNode.prev = currentNodeLast;
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null && node.next != null) {
            if (node.prev.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        } else if (node.next == null && node.prev != null) {
            if (node.prev.next != null) {
                node.prev.next = null;
            }
            last = node.prev;
        } else if (node.prev == null && node.next != null) {
            if (node.next.prev != null) {
                node.next.prev = null;
            }
            first = node.next;
        }
        history.remove(node.item.getId());
        if (history.isEmpty()) {
            last = null;
            first = null;
        }
    }
}