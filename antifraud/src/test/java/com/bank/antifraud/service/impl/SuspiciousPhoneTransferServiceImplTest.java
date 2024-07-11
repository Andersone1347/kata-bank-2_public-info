package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.util.service.TestSuspiciousPhoneTransferUtil;
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

/**
 * Тестовый класс для {@link SuspiciousPhoneTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class SuspiciousPhoneTransferServiceImplTest {

    @Mock
    private SuspiciousPhoneTransferRepository repository;

    @Mock
    private SuspiciousPhoneTransferMapper mapper;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl service;

    @Test
    @DisplayName("создание (сохранение) SuspiciousPhone, позитивный сценарий")
    void saveSuspiciousPhonePositiveTest() {
        SuspiciousPhoneTransferEntity expectedEntity =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto expectedDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();

        Mockito.when(repository.save(expectedEntity)).thenReturn(expectedEntity);
        Mockito.when(mapper.toEntity(expectedDto)).thenReturn(expectedEntity);
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto foundDto = service.save(expectedDto);

        Mockito.verify(repository, Mockito.times(1)).save(expectedEntity);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("создание (сохранение)  SuspiciousPhone, негативный сценарий")
    void saveSuspiciousPhoneNegativeTest() {
        SuspiciousPhoneTransferDto savingDto =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(savingDto));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(SuspiciousPhoneTransferEntity.class));
    }

    @Test
    @DisplayName("поиск SuspiciousPhone по id, позитивный сценарий")
    void findByIdPositiveTest() {
        SuspiciousPhoneTransferEntity expectedEntity =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto expectedDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedEntity));
        Mockito.when(mapper.toDto(expectedEntity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto foundDto = service.findById(1L);

        Mockito.verify(repository, Mockito.times(1)).findById(1L);

        Assertions.assertEquals(expectedDto, foundDto);
    }

    @Test
    @DisplayName("поиск SuspiciousPhone по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        String message = "SuspiciousPhoneTransfer по данному id не существует";

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousPhoneTransfer по данному id не существует",
                exception.getMessage());
    }

    @Test
    @DisplayName("чтение SuspiciousPhone по ряду id, позитивный сценарий")
    void findByAllIdPositiveTest() {
        List<SuspiciousPhoneTransferEntity> expectedEntities =
                TestSuspiciousPhoneTransferUtil.createListOfSuspiciousPhoneTransferEntities();
        List<SuspiciousPhoneTransferDto> expectedDtos =
                TestSuspiciousPhoneTransferUtil.createListOfSuspiciousPhoneTransferDtos();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(expectedEntities.get(0)));
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(expectedEntities.get(1)));
        Mockito.when(mapper.toListDto(expectedEntities)).thenReturn(expectedDtos);

        List<SuspiciousPhoneTransferDto> foundDtos = service.findAllById(List.of(1L, 2L));

        Mockito.verify(repository, Mockito.times(1)).findById(1L);
        Mockito.verify(repository, Mockito.times(1)).findById(2L);

        Assertions.assertEquals(expectedDtos, foundDtos);
    }

    @Test
    @DisplayName("чтение SuspiciousPhone по ряду несуществующих id, негативный сценарий")
    void findByNonExistListIdNegativeTest() {
        String message = "SuspiciousPhoneTransfer по данному id не существует";

        List<SuspiciousPhoneTransferEntity> expectedEntities =
                TestSuspiciousPhoneTransferUtil.createListOfSuspiciousPhoneTransferEntities();

        Mockito.when(repository.findById(expectedEntities.get(0).getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(expectedEntities.get(0).getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousPhoneTransfer по данному id не существует", exception.getMessage());
    }

    @Test
    @DisplayName("обновить поля SuspiciousPhone по id, позитивный сценарий")
    void updateSuspiciousPhoneTransferByIdPositiveTest() {
        SuspiciousPhoneTransferEntity entityBeforeMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto dtoForMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();

        SuspiciousPhoneTransferEntity entityAfterMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntityAfterMerge();
        SuspiciousPhoneTransferDto dtoAfterMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();

        dtoAfterMerge.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(entityBeforeMerge));
        Mockito.when(mapper.mergeToEntity(dtoForMerge, entityBeforeMerge)).thenReturn(entityAfterMerge);
        Mockito.when(repository.save(entityAfterMerge)).thenReturn(entityAfterMerge);
        Mockito.when(mapper.toDto(entityAfterMerge)).thenReturn(dtoAfterMerge);

        SuspiciousPhoneTransferDto foundDto = service.update(1L, dtoForMerge);

        Mockito.verify(repository, Mockito.times(1)).save(entityAfterMerge);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());

        Assertions.assertEquals(dtoAfterMerge, foundDto);
    }

    @Test
    @DisplayName("обновить поля SuspiciousPhone по несуществующему id, негативный сценарий")
    void updateSuspiciousPhoneTransferByNonExistIdNegativeTest() {
        String message = "SuspiciousPhoneTransfer по данному id не существует";
        SuspiciousPhoneTransferEntity entityBeforeMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();

        Mockito.when(repository.findById(entityBeforeMerge.getId())).thenReturn(Optional.empty());
        Mockito.when(returner.getEntityNotFoundException(message)).thenReturn(new EntityNotFoundException(message));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(entityBeforeMerge.getId());
        });

        Mockito.verify(returner, Mockito.times(1)).getEntityNotFoundException(message);

        Assertions.assertEquals("SuspiciousPhoneTransfer по данному id не существует",
                exception.getMessage());
    }
}