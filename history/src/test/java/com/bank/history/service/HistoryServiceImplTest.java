package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapperImpl;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.util.DataHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl historyService;
    @Mock
    private HistoryRepository repository;
    @Mock
    private HistoryMapperImpl mapper;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        HistoryDto historyDto = DataHistory.createDto();
        HistoryEntity historyEntity = DataHistory.createEntity();
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(historyEntity));
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto actual = historyService.readById(1L);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(historyDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(historyEntity);
    }

    @Test
    @DisplayName("поиск по несуществующему id, неготивный сценарий")
    void findByNonExistIdNegativeTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyService.readById(1L));

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("поиск по списку, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<HistoryDto> dtos = DataHistory.listDto();
        List<HistoryEntity> entities = DataHistory.listEntity();
        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entities);
        when(mapper.toListDto(entities)).thenReturn(dtos);

        List<HistoryDto> actual = historyService.readAllById(List.of(1L, 2L));

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(dtos);
        verify(repository).findAllById(List.of(1L, 2L));
        verify(mapper).toListDto(entities);
    }

    @Test
    @DisplayName("поиск по списку, негативный сценарий")
    void findAllByNonExistIdNegativeNest() {
        when(repository.findAllById(List.of(1L, 2L))).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> historyService.readAllById(List.of(1L, 2L)));

        verify(repository).findAllById(List.of(1L, 2L));

    }


    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() {
        HistoryDto historyDto = DataHistory.createDto();
        HistoryEntity historyEntity = DataHistory.createEntity();
        when(mapper.toEntity(historyDto)).thenReturn(historyEntity);
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);
        when(repository.save(historyEntity)).thenReturn(historyEntity);

        HistoryDto actual = historyService.create(historyDto);

        assertThat(actual).isEqualTo(historyDto);

        verify(mapper).toEntity(historyDto);
        verify(repository).save(historyEntity);
        verify(mapper).toDto(historyEntity);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createNegativeTest() {
        HistoryDto historyDto = DataHistory.createDto();
        when(historyService.create(historyDto)).thenThrow(new DataIntegrityViolationException("не корректные данные"));
        assertThrows(DataIntegrityViolationException.class, () -> historyService.create(historyDto));

        verify(repository, never()).save(any(HistoryEntity.class));

    }


    @Test
    @DisplayName("обновление по id, позитивный тест")
    void update_ReturnHistoryDto_WhenUpdateSuccessful() {
        HistoryDto historyDto = DataHistory.createDto();
        HistoryDto updateHistoryDto = DataHistory.updateHistoryDto();
        HistoryEntity preHistoryEntity = DataHistory.preEntityForMerge();
        HistoryEntity updateHistoryEntity = DataHistory.postEntityForMerge();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preHistoryEntity));
        when(mapper.mergeToEntity(historyDto, preHistoryEntity)).thenReturn(updateHistoryEntity);
        when(repository.save(updateHistoryEntity)).thenReturn(updateHistoryEntity);
        when(mapper.toDto(updateHistoryEntity)).thenReturn(updateHistoryDto);

        HistoryDto actual = historyService.update(anyLong(), historyDto);

        assertThat(actual).isEqualTo(updateHistoryDto);

        verify(repository).findById(anyLong());
        verify(mapper).mergeToEntity(historyDto, preHistoryEntity);
        verify(repository).save(updateHistoryEntity);
        verify(mapper).toDto(updateHistoryEntity);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный тест")
    void updateNonExistIdNegativeNest() {
        HistoryDto historyDto = DataHistory.createDto();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> historyService.update(anyLong(), historyDto));
        verify(repository).findById(anyLong());
    }


}