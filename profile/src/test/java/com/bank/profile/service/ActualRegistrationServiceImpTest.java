package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;
import com.bank.profile.util.ActualRegistrationUtil;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActualRegistrationServiceImpTest {


    @Mock
    private ActualRegistrationMapper mapper;

    @Mock
    private ActualRegistrationRepository repository;

    @InjectMocks
    private ActualRegistrationServiceImp service;


    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {

        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationUtil().createActualRegistrationEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(actualRegistrationEntity));
        when(mapper.toDto(actualRegistrationEntity)).thenReturn(actualRegistrationDto);

        ActualRegistrationDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(actualRegistrationDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(actualRegistrationEntity);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeNest() {
        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("поиск списка по id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<ActualRegistrationEntity> entityList = new ActualRegistrationUtil().createActualRegistrationEntityList();
        List<ActualRegistrationDto> dtoList = new ActualRegistrationUtil().createActualRegistrationDtoList();

        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<ActualRegistrationDto> resultList = service.findAllById(List.of(1L, 2L));

        assertThat(resultList).isEqualTo(dtoList);
        verify(repository, times(1)).findAllById(anyList());
        verify(mapper, times(1)).toDtoList(entityList);
    }

    @Test
    @DisplayName("поиск списка по несуществующему id, негативный сценарий")
    void findAllNonExistIdNegativeTest() {
        when(repository.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());


        assertThrows(EntityNotFoundException.class, () -> service.findAllById(List.of(1L, 2L)));
        verify(repository, times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("сохранине новых данных, позитивный сценарий ")
    void saveNewDataPositiveTest() {

        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationUtil().createActualRegistrationEntity();

        when(mapper.toEntity(actualRegistrationDto)).thenReturn(actualRegistrationEntity);
        when(repository.save(actualRegistrationEntity)).thenReturn(actualRegistrationEntity);
        when(mapper.toDto(actualRegistrationEntity)).thenReturn(actualRegistrationDto);

        ActualRegistrationDto actualResult = service.save(actualRegistrationDto);

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
        verify(mapper).toEntity(actualRegistrationDto);
        verify(repository).save(actualRegistrationEntity);
        verify(mapper).toDto(actualRegistrationEntity);
    }

    @Test
    @DisplayName("сохранение неподходящих данных, негативный сценарий")
    void saveNotSuitableDataNegativeTest() {

        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();

        when(mapper.toEntity(any(ActualRegistrationDto.class))).thenReturn(new ActualRegistrationEntity());
        when(repository.save(any(ActualRegistrationEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(actualRegistrationDto));

        verify(mapper).toEntity(any(ActualRegistrationDto.class));
        verify(repository).save(any(ActualRegistrationEntity.class));
    }

    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationDto updateActualRegistrationDto = new ActualRegistrationUtil().createUpdateActualRegistrationEntity();
        ActualRegistrationEntity preMergerEntity = new ActualRegistrationUtil().createPreMergerActualRegistrationEntity();
        ActualRegistrationEntity postMergerEntity = new ActualRegistrationUtil().createPostMergerActualRegistrationEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(actualRegistrationDto, preMergerEntity)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updateActualRegistrationDto);

        ActualRegistrationDto actualResult = service.update(3L, actualRegistrationDto);


        assertThat(actualResult).isEqualTo(updateActualRegistrationDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(actualRegistrationDto, preMergerEntity);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();

        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, actualRegistrationDto));
        verify(repository).findById(1L);
    }
}
