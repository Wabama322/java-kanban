package yandex.practicum.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void epicAreEqualToEachOtherIfTheirIdIsEqual() {
        Epic epicOne = new Epic("Подготовка к отпуску", "бронирование билетов и отеля", 2,
                TaskStatus.IN_PROGRESS, new ArrayList<>());
        Epic epicTwo = new Epic("Купить продукты домой", "составить список покупок", 2,
                TaskStatus.NEW, new ArrayList<>());
        assertEquals(epicOne, epicTwo);
    }
}