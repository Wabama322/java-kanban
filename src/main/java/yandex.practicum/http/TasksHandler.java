package yandex.practicum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import yandex.practicum.exception.ValidationException;
import yandex.practicum.model.Task;
import yandex.practicum.tracker.service.DurationTypeAdapter;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.LocalTimeTypeAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

public class TasksHandler extends BaseHandler implements HttpHandler {
    ITaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;
    String response;

    public TasksHandler(ITaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class,new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalTimeTypeAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = String.valueOf(exchange.getRequestURI());

        System.out.println("Обрабатывается запрос " + path + " с методом " + method);
        switch (method) {
            case "GET":
                getTask(exchange);
                break;
            case "POST":
                addTask(exchange);
                break;
            case "DELETE":
                deleteTask(exchange);
                break;
            default:
                writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getTask(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            response = gson.toJson(taskManager.getAllTasks());
            writeResponse(exchange, response, 200);
            return;
        }

        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор " + getTaskId(exchange), 400);
            return;
        }

        int id = getTaskId(exchange).get();
        Task taskById = taskManager.getTaskById(id);
        if (isNull(taskById)) {
            writeResponse(exchange, "Задач с id " + id + " не найдено!", 404);
            return;
        }
        response = gson.toJson(taskById);
        writeResponse(exchange, response, 200);
    }

    private void addTask(HttpExchange exchange) throws IOException {
        try {
            InputStream json = exchange.getRequestBody();
            String jsonTask = new String(json.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(jsonTask, Task.class);
            if (task == null) {
                writeResponse(exchange, "Задача не должна быть пустой!", 400);
                return;
            }
            Task taskById = taskManager.getTaskById(task.getId());
            if (taskById == null) {
                taskManager.createTask(task);
                writeResponse(exchange, "Задача успешно добавлена!", 201);
                return;
            }
            taskManager.updateTask(task);
            writeResponse(exchange, "Задача обновлена", 200);

        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
        } catch (ValidationException exp) {
            writeResponse(exchange, "Найдено пересечение по времени!", 406);
        }
    }

    private void deleteTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();

        if (query == null) {
            writeResponse(exchange, "Не указан id задачи ", 404);
            return;
        }
        if (getTaskId(exchange).isEmpty()) {
            writeResponse(exchange, "Не указан id задачи ", 404);
            return;
        }
        int id = getTaskId(exchange).get();
        if (taskManager.getTaskById(id) == null) {
            writeResponse(exchange, "Задач с таким id " + id + " не найдено!", 404);
            return;
        }
        taskManager.removeTaskById(id);
        writeResponse(exchange, "Задача успешно удалена!", 200);
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getQuery().split("=");
        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
