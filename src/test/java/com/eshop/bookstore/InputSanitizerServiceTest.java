package com.eshop.bookstore;

import com.eshop.bookstore.service.InputSanitizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputSanitizerServiceTest {

    private InputSanitizerService sanitizerService;

    @BeforeEach
    void setUp() {
        sanitizerService = new InputSanitizerService();
    }

    @Test
    void normalUsernameIsNotModified() {
        String username = "Antoine_01";

        assertEquals(
                username,
                sanitizerService.sanitizePlainText(username)
        );

        assertTrue(sanitizerService.isSafePlainText(username));
    }

    @Test
    void htmlUsernameIsModified() {
        String username = "<b>Antoine</b>";

        assertNotEquals(
                username,
                sanitizerService.sanitizePlainText(username)
        );

        assertFalse(sanitizerService.isSafePlainText(username));
    }

    @Test
    void scriptUsernameIsRejected() {
        String username = "<script>alert(1)</script>";

        assertFalse(sanitizerService.isSafePlainText(username));
    }
}