package com.bank.profile.service;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.impl.AuditServiceImpl;
import com.bank.profile.util.AuditUtil;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {

    @Mock
    private AuditMapper mapper;

    @Mock
    private AuditRepository repository;

    @InjectMocks
    private AuditServiceImpl service;


    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {

        AuditEntity auditDto = new AuditUtil().createAuditEntity();
        AuditDto auditDtoDto = new AuditUtil().createAuditDto();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(auditDto));
        when(mapper.toDto(auditDto)).thenReturn(auditDtoDto);

        AuditDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(auditDtoDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(auditDto);

    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeNest() {
        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository).findById(1L);
    }
}
