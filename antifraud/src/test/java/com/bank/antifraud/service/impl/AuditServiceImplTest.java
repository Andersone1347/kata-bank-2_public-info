package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.service.TestAuditUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для {@link AuditServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl service;

    @Test
    @DisplayName("поиск AuditEntity по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AuditDto expectedDto = TestAuditUtil.createAuditDto();
        AuditEntity expectedEntity = TestAuditUtil.createAuditEntity();

        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedEntity));

        AuditDto foundDto = service.findById(1L);

        Mockito.verify(repository, Mockito.times(1)).findById(1L);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("поиск AuditEntity по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        String message = "AuditEntityTransfer по данному id не существует";

        Mockito.when(repository.findById(1L)).thenThrow(new EntityNotFoundException("Не найден аудит с ID  " + 1L));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });

        Mockito.verify(repository, Mockito.times(1)).findById(1L);

        Assertions.assertEquals("Не найден аудит с ID  " + 1L,
                exception.getMessage());
    }
}