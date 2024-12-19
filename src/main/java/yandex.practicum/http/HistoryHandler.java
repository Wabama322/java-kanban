package yandex.practicum.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import yandex.practicum.tracker.service.ITaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHandler implements HttpHandler {
    ITaskManager taskManager;
    private final Gson gson;
    String response;

    public HistoryHandler(ITaskManager newTaskManager) {
        this.taskManager = newTaskManager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                getHistoryList(exchange);
                break;
            default:
                writeResponse(exchange, "Такой операции не существует", 404);
        }
    }

    private void getHistoryList(HttpExchange exchange) throws IOException {
        if (taskManager.getHistory().isEmpty()) {
            writeResponse(exchange, "История пуста!", 200);
        } else {
            response = gson.toJson(taskManager.getHistory());
            writeResponse(exchange, response, 200);
        }
    }
}
