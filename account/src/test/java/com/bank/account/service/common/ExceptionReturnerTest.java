package com.bank.account.service.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionReturnerTest {

    @Test
    @DisplayName("Должен возвращать EntityNotFoundException")
    void getEntityNotFoundException_shouldReturnCorrectException() {
        ExceptionReturner exceptionReturner = new ExceptionReturner();
        String expectedMessage = "Entity not found";

        EntityNotFoundException exception = exceptionReturner.getEntityNotFoundException(expectedMessage);

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
}