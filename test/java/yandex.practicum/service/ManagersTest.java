package yandex.practicum.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yandex.practicum.tracker.service.Managers;

class ManagersTest {
    @Test
    void utilityClassAlwaysReturnsReadyToUseInstances() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    void methodGetDefaultHistoryNotGetNull() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}