package com.bank.publicinfo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EntityNotFoundSupplierTest {

    @InjectMocks
    private EntityNotFoundSupplier supplier;

    @Test
    @DisplayName("Throw EntityNotFoundException")
    void getExceptionTest() {
        Exception suppliedException = supplier.getException("Test", 1L);

        assertThat(suppliedException.getClass()).isEqualTo(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException при сравнении листов id и entities")
    void checkForSizeAndLoggingTest() {
        assertThrows(EntityNotFoundException.class,
                () -> supplier.checkForSizeAndLogging("Test", List.of(1L,2L,3L), Collections.emptyList()));
    }
}