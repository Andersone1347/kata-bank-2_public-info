package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAuditServiceImplTest {
    @Mock
    private AccountAuditRepository repository;

    @Mock
    private AccountAuditMapper mapper;

    @Mock
    private ExceptionReturner exceptionReturner;

    @InjectMocks
    private AccountAuditServiceImpl service;

    @Test
    @DisplayName("Должен вернуть AuditDto: успешный запрос по id")
    void findById_shouldReturnAuditDto_whenIdExists() {
        AuditEntity auditEntity = new DataUtils().createAuditEntity();
        AuditDto auditDto = new DataUtils().createAuditDto();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(auditDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(auditEntity);
    }

    @Test
    @DisplayName("Должен выбросить EntityNotFoundException: id не существует")
    void findById_shouldThrowException_whenIdDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException("Не существующий id = 1"));

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(repository).findById(1L);
        verify(exceptionReturner).getEntityNotFoundException("Не существующий id = 1");
    }
}