package com.bank.authorization.service;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import com.bank.authorization.mapper.AuditMapper;
import com.bank.authorization.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    void findById_shouldReturnAuditDto_whenAuditFound() {

        Long id = 1L;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        AuditEntity auditEntity = new AuditEntity(
                id,
                "User",
                "CREATE",
                "test_user",
                "test_user",
                now,
                now,
                "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}",
                null
        );
        AuditDto expectedDto = new AuditDto(
                id,
                "User",
                "CREATE",
                "test_user",
                "test_user",
                now,
                now,
                "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}",
                null
        );
        when(repository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(expectedDto);

        AuditDto result = auditService.findById(id);

        assertEquals(expectedDto, result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(auditEntity);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenAuditNotFound() {

        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> auditService.findById(id));
        verify(repository, times(1)).findById(id);
        verifyNoInteractions(mapper);
    }

}