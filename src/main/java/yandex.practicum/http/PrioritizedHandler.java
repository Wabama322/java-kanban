package yandex.practicum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import yandex.practicum.tracker.service.DurationTypeAdapter;
import yandex.practicum.tracker.service.ITaskManager;
import yandex.practicum.tracker.service.LocalTimeTypeAdapter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PrioritizedHandler extends BaseHandler implements HttpHandler {
    ITaskManager taskManager;
    private final Gson gson;
    String response;

    public PrioritizedHandler(ITaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalTimeTypeAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            getPrioritizedTasks(exchange);
        } else {
            writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getPrioritizedTasks(HttpExchange exchange) throws IOException {
        response = gson.toJson(taskManager.getPrioritizedTasks());
        writeResponse(exchange, response, 200);
    }
}