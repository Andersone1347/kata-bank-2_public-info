package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mappers.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.util.service.TestSuspiciousAccountTransferUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

/**
 * Тестовый класс для {@link SuspiciousAccountTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class SuspiciousAccountTransferServiceImplTest {

    @Mock
    private SuspiciousAccountTransferRepository repository;

    @Mock
    private SuspiciousAccountTransferMapper mapper;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousAccountTransferServiceImpl service;

    @Test
    @DisplayName("создание (сохранение) SuspiciousAccount, позитивный сценарий")
    void saveSuspiciousAccountPositiveTest() {
        SuspiciousAccountTransferEntity expectedEntity =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();
        SuspiciousAccountTransferDto expectedDto =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();

        Mockito.when(repository.save(expectedEntity)).thenReturn(expectedEntity);
        Mockito.when(mapper.toEntity(expectedDto)).thenReturn(expectedEntity);
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousAccountTransferDto foundDto = service.save(expectedDto);

        Mockito.verify(repository, Mockito.times(1)).save(expectedEntity);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousAccount, негативный сценарий")
    void saveSuspiciousAccountNegativeTest() {
        SuspiciousAccountTransferDto savingDto = TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(savingDto));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(SuspiciousAccountTransferEntity.class));
    }

    @Test
    @DisplayName("поиск SuspiciousAccount по id, позитивный сценарий")
    void findByIdPositiveTest() {
        SuspiciousAccountTransferEntity expectedEntity =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();
        SuspiciousAccountTransferDto expectedDto =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedEntity));
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousAccountTransferDto foundDto = service.findById(1L);
        Mockito.verify(repository, times(1)).findById(1L);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("поиск SuspiciousAccount по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        String message = "SuspiciousAccountTransfer по данному id не существует";

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousAccountTransfer по данному id не существует",
                exception.getMessage());
    }

    @Test
    @DisplayName("чтение SuspiciousAccount по ряду id, позитивный сценарий")
    void findByAllIdPositiveTest() {
        List<SuspiciousAccountTransferEntity> expectedEntities =
                TestSuspiciousAccountTransferUtil.createListOfSuspiciousAccountTransferEntities();
        List<SuspiciousAccountTransferDto> expectedDtos =
                TestSuspiciousAccountTransferUtil.createListOfSuspiciousAccountTransferDtos();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(expectedEntities.get(0)));
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(expectedEntities.get(1)));
        Mockito.when(mapper.toListDto(expectedEntities)).thenReturn(expectedDtos);

        List<SuspiciousAccountTransferDto> foundDtos = service.findAllById(List.of(1L, 2L));

        Mockito.verify(repository, Mockito.times(1)).findById(1L);
        Mockito.verify(repository, Mockito.times(1)).findById(2L);

        Assertions.assertEquals(expectedDtos, foundDtos);
    }

    @Test
    @DisplayName("чтение SuspiciousAccount по ряду несуществующих id, негативный сценарий")
    void findByNonExistListIdNegativeTest() {
        String message = "SuspiciousAccountTransfer по данному id не существует";

        List<SuspiciousAccountTransferEntity> expectedEntities =
                TestSuspiciousAccountTransferUtil.createListOfSuspiciousAccountTransferEntities();

        Mockito.when(repository.findById(expectedEntities.get(0).getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(expectedEntities.get(0).getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousAccountTransfer по данному id не существует",
                exception.getMessage());
    }

    @Test
    @DisplayName("обновить поля SuspiciousAccount по id, позитивный сценарий")
    void updateSuspiciousAccountTransferByIdPositiveTest() {
        SuspiciousAccountTransferEntity entityBeforeMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();
        SuspiciousAccountTransferEntity entityAfterMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntityAfterMerge();

        SuspiciousAccountTransferDto dtoForMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();
        SuspiciousAccountTransferDto dtoAfterMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();
        dtoAfterMerge.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(entityBeforeMerge));
        Mockito.when(mapper.mergeToEntity(dtoForMerge, entityBeforeMerge)).thenReturn(entityAfterMerge);
        Mockito.when(repository.save(entityAfterMerge)).thenReturn(entityAfterMerge);
        Mockito.when(mapper.toDto(entityAfterMerge)).thenReturn(dtoAfterMerge);

        SuspiciousAccountTransferDto foundDto = service.update(1L, dtoForMerge);

        Mockito.verify(repository, Mockito.times(1)).save(entityAfterMerge);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());

        Assertions.assertEquals(dtoAfterMerge, foundDto);
    }

    @Test
    @DisplayName("обновить поля SuspiciousAccount по несуществующему id, негативный сценарий")
    void updateSuspiciousAccountTransferByNonExistIdNegativeTest() {
        String message = "SuspiciousAccountTransfer по данному id не существует";
        SuspiciousAccountTransferEntity entityBeforeMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();

        Mockito.when(repository.findById(entityBeforeMerge.getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(entityBeforeMerge.getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousAccountTransfer по данному id не существует",
                exception.getMessage());
    }
}