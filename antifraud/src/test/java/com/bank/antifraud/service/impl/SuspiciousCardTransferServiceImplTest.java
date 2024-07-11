package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.util.service.TestSuspiciousCardTransferUtil;
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
 * Тестовый класс для {@link SuspiciousCardTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class SuspiciousCardTransferServiceImplTest {
    @Mock
    private SuspiciousCardTransferRepository repository;

    @Mock
    private SuspiciousCardTransferMapper mapper;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousCardTransferServiceImpl service;

    @Test
    @DisplayName("создание (сохранение) SuspiciousCard, позитивный сценарий")
    void saveSuspiciousCardPositiveTest() {
        SuspiciousCardTransferEntity expectedEntity =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();
        SuspiciousCardTransferDto expectedDto =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();

        Mockito.when(repository.save(expectedEntity)).thenReturn(expectedEntity);
        Mockito.when(mapper.toEntity(expectedDto)).thenReturn(expectedEntity);
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousCardTransferDto foundDto = service.save(expectedDto);

        Mockito.verify(repository, times(1)).save(expectedEntity);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("создание (сохранение)  SuspiciousCard, негативный сценарий")
    void saveSuspiciousCardNegativeTest() {
        SuspiciousCardTransferDto savingDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(savingDto));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(SuspiciousCardTransferEntity.class));
    }

    @Test
    @DisplayName("поиск SuspiciousCard по id, позитивный сценарий")
    void findByIdPositiveTest() {
        SuspiciousCardTransferEntity expectedEntity =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();
        SuspiciousCardTransferDto expectedDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedEntity));
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousCardTransferDto foundDto = service.findById(1L);
        Mockito.verify(repository, times(1)).findById(1L);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("поиск SuspiciousCard по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        String message = "SuspiciousCardTransfer по данному id не существует";

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousCardTransfer по данному id не существует",
                exception.getMessage());
    }

    @Test
    @DisplayName("чтение SuspiciousCard по ряду id, позитивный сценарий")
    void findByAllIdPositiveTest() {
        List<SuspiciousCardTransferEntity> expectedEntities =
                TestSuspiciousCardTransferUtil.createListOfSuspiciousCardTransferEntities();
        List<SuspiciousCardTransferDto> expectedDtos =
                TestSuspiciousCardTransferUtil.createListOfSuspiciousCardTransferDtos();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(expectedEntities.get(0)));
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(expectedEntities.get(1)));
        Mockito.when(mapper.toListDto(expectedEntities)).thenReturn(expectedDtos);

        List<SuspiciousCardTransferDto> foundDtos = service.findAllById(List.of(1L, 2L));

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verify(repository, times(1)).findById(2L);

        Assertions.assertEquals(expectedDtos, foundDtos);
    }

    @Test
    @DisplayName("чтение SuspiciousCard по ряду несуществующих id, негативный сценарий")
    void findByNonExistListIdNegativeTest() {
        String message = "SuspiciousCardTransfer по данному id не существует";

        List<SuspiciousCardTransferEntity> expectedEntities =
                TestSuspiciousCardTransferUtil.createListOfSuspiciousCardTransferEntities();

        Mockito.when(repository.findById(expectedEntities.get(0).getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(expectedEntities.get(0).getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousCardTransfer по данному id не существует", exception.getMessage());
    }

    @Test
    @DisplayName("обновить поля SuspiciousCard по id, позитивный сценарий")
    void updateSuspiciousCardTransferByIdPositiveTest() {
        SuspiciousCardTransferEntity beforeMergeEntity =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();
        SuspiciousCardTransferEntity afterMergeEntity =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntityAfterMerge();

        SuspiciousCardTransferDto incomingDto =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();
        SuspiciousCardTransferDto afterMergeDto =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();
        afterMergeDto.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeMergeEntity));
        Mockito.when(mapper.mergeToEntity(incomingDto, beforeMergeEntity)).thenReturn(afterMergeEntity);
        Mockito.when(repository.save(afterMergeEntity)).thenReturn(afterMergeEntity);
        Mockito.when(mapper.toDto(afterMergeEntity)).thenReturn(afterMergeDto);

        SuspiciousCardTransferDto foundDto = service.update(1L, incomingDto);

        Mockito.verify(repository, times(1)).findById(1L);
        Assertions.assertEquals(afterMergeDto, foundDto);
    }

    @Test
    @DisplayName("обновить поля SuspiciousCard по несуществующему id, негативный сценарий")
    void updateSuspiciousCardTransferByNonExistIdNegativeTest() {
        String message = "SuspiciousCardTransfer по данному id не существует";
        SuspiciousCardTransferEntity entityBeforeMerge =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();

        Mockito.when(repository.findById(entityBeforeMerge.getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(entityBeforeMerge.getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousCardTransfer по данному id не существует",
                exception.getMessage());
    }
}