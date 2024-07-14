package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.impl.RegistrationServiceImp;
import com.bank.profile.util.RegistrationUtil;
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
public class RegistrationServiceImpTest {

    @Mock
    private RegistrationMapper mapper;

    @Mock
    private RegistrationRepository repository;

    @InjectMocks
    private RegistrationServiceImp service;


    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationEntity registrationEntity = new RegistrationUtil().createRegistrationEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(registrationEntity));
        when(mapper.toDto(registrationEntity)).thenReturn(registrationDto);

        RegistrationDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(registrationDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(registrationEntity);
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
        List<RegistrationEntity> entityList = new RegistrationUtil().createRegistrationEntityList();
        List<RegistrationDto> dtoList = new RegistrationUtil().createRegistrationDtoList();

        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<RegistrationDto> resultList = service.findAllById(List.of(1L, 2L));

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
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationEntity registrationEntity = new RegistrationUtil().createRegistrationEntity();

        when(mapper.toEntity(registrationDto)).thenReturn(registrationEntity);
        when(repository.save(registrationEntity)).thenReturn(registrationEntity);
        when(mapper.toDto(registrationEntity)).thenReturn(registrationDto);

        RegistrationDto actualResult = service.save(registrationDto);

        assertThat(actualResult).isEqualTo(registrationDto);
        verify(mapper).toEntity(registrationDto);
        verify(repository).save(registrationEntity);
        verify(mapper).toDto(registrationEntity);
    }

    @Test
    @DisplayName("сохранение неподходящих данных, негативный сценарий")
    void saveNotSuitableDataNegativeTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();

        when(mapper.toEntity(any(RegistrationDto.class))).thenReturn(new RegistrationEntity());
        when(repository.save(any(RegistrationEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(registrationDto));

        verify(mapper).toEntity(any(RegistrationDto.class));
        verify(repository).save(any(RegistrationEntity.class));
    }


    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationDto updatedRegistrationDto = new RegistrationUtil().createUpdateRegistrationDto();
        RegistrationEntity preMergerEntity = new RegistrationUtil().createPreMergerRegistrationEntity();
        RegistrationEntity postMergerEntity = new RegistrationUtil().createPostMergerRegistrationEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(registrationDto, preMergerEntity)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updatedRegistrationDto);

        RegistrationDto actualResult = service.update(3L, registrationDto);


        assertThat(actualResult).isEqualTo(updatedRegistrationDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(registrationDto, preMergerEntity);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();

        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, registrationDto));
        verify(repository).findById(1L);
    }
}
