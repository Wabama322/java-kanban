package yandex.practicum.exception;

public class TaskValidationException extends RuntimeException {
    public TaskValidationException(String massage) {
        super(massage);
    }

    public TaskValidationException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
