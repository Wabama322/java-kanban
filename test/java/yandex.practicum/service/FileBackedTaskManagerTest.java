package yandex.practicum.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.exception.SaveManagerException;
import yandex.practicum.model.Epic;
import yandex.practicum.model.Subtask;
import yandex.practicum.model.Task;
import yandex.practicum.tracker.service.FileBackedTaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static yandex.practicum.model.TaskStatus.IN_PROGRESS;
import static yandex.practicum.model.TaskStatus.NEW;

class FileBackedTaskManagerTest {
    File tempFile;
    FileBackedTaskManager fileManager;

    @BeforeEach
    public void init() {
        try {
            tempFile = File.createTempFile("test", "csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileManager = new FileBackedTaskManager(tempFile.toPath());
    }

    @Test
    public void saveAndLoadOfEmptyFilesTest() {
        assertNotNull(tempFile);
        fileManager.save();
        fileManager.loadFromFile(tempFile);
    }

    @Test
    public void shouldSaveFewTasks() {
        createTasks();
        assertEquals(1, fileManager.getAllEpics().size());
        assertEquals(2, fileManager.getAllTasks().size());
        assertEquals(1, fileManager.getAllSubtasks().size());
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toString()))) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (isNull(line) || line.isEmpty()) {
                    break;
                }
                count += 1;
            }
        } catch (FileNotFoundException e) {
            throw new SaveManagerException("Файл не найден...");
        } catch (IOException e) {
            throw new SaveManagerException("Ошибка чтения файла...");
        }
        assertEquals(4, count);
    }

    @Test
    public void shouldLoadFewTasks() {
        createTasks();
        Assertions.assertEquals(1, fileManager.getAllEpics().size());
        Assertions.assertEquals(2, fileManager.getAllTasks().size());
        Assertions.assertEquals(1, fileManager.getAllSubtasks().size());

        File copyFile = createCopyFile(tempFile);
        fileManager.removeAllTasks();
        fileManager.removeAllEpic();
        fileManager.removeAllSubtasks();
        Assertions.assertEquals(0, fileManager.getAllEpics().size());
        Assertions.assertEquals(0, fileManager.getAllTasks().size());
        Assertions.assertEquals(0, fileManager.getAllSubtasks().size());

        fileManager.loadFromFile(copyFile);
        Assertions.assertEquals(1, fileManager.getAllEpics().size());
        Assertions.assertEquals(2, fileManager.getAllTasks().size());
        Assertions.assertEquals(1, fileManager.getAllSubtasks().size());
    }

    private void createTasks() {
        Task taskOne = new Task("TaskOne", "DescrTaskOne", 1, NEW);
        Task taskTwo = new Task("TaskTwo", "DescrTaskTwo", 2, NEW);

        Epic epicOne = new Epic("EpicOne", "DescrEpicOne", 3, IN_PROGRESS, new ArrayList<>());
        fileManager.createEpic(epicOne);
        List<Epic> allEpics = fileManager.getAllEpics();
        Subtask subtaskOne = new Subtask("SubtaskOne", "DescrSubOne", 5, NEW, allEpics.get(0).getId());

        fileManager.createTask(taskOne);
        fileManager.createTask(taskTwo);
        fileManager.createSubtask(subtaskOne);
    }

    private File createCopyFile(File tempFile) {
        File copied;
        try {
            copied = File.createTempFile("file2", "csv");
            Files.copy(tempFile.toPath(), copied.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copied;
    }
}
