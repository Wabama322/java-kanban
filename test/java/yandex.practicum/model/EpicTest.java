package yandex.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void epicAreEqualToEachOtherIfTheirIdIsEqual() {
        Epic epicOne = new Epic("Подготовка к отпуску", "бронирование билетов и отеля"
        );
        Epic epicTwo = new Epic("Купить продукты домой", "составить список покупок"
        );
        assertEquals(epicOne, epicTwo);
    }
}