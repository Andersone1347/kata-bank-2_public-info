package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.impl.PassportServiceImp;
import com.bank.profile.util.PassportUtil;
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
public class PassportServiceImpTest {


    @Mock
    private PassportMapper mapper;

    @Mock
    private PassportRepository repository;

    @InjectMocks
    private PassportServiceImp service;


    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {

        PassportEntity passportEntity = new PassportUtil().createPassportEntity();
        PassportDto passportDto = new PassportUtil().createPassportDto();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(passportEntity));
        when(mapper.toDto(passportEntity)).thenReturn(passportDto);

        PassportDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(passportDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(passportEntity);
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
        List<PassportEntity> entityList = new PassportUtil().createPassportEntityList();
        List<PassportDto> dtoList = new PassportUtil().createPassportDtoList();

        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<PassportDto> resultList = service.findAllById(List.of(1L, 2L));

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

        PassportEntity passportEntity = new PassportUtil().createPassportEntity();
        PassportDto passportDto = new PassportUtil().createPassportDto();

        when(mapper.toEntity(passportDto)).thenReturn(passportEntity);
        when(repository.save(passportEntity)).thenReturn(passportEntity);
        when(mapper.toDto(passportEntity)).thenReturn(passportDto);

        PassportDto actualResult = service.save(passportDto);

        assertThat(actualResult).isEqualTo(passportDto);
        verify(mapper).toEntity(passportDto);
        verify(repository).save(passportEntity);
        verify(mapper).toDto(passportEntity);
    }

    @Test
    @DisplayName("сохранение неподходящих данных, негативный сценарий")
    void saveNotSuitableDataNegativeTest() {

        PassportDto passportDto = new PassportUtil().createPassportDto();

        when(mapper.toEntity(any(PassportDto.class))).thenReturn(new PassportEntity());
        when(repository.save(any(PassportEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(passportDto));

        verify(mapper).toEntity(any(PassportDto.class));
        verify(repository).save(any(PassportEntity.class));
    }

    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() {

        PassportDto passportDto = new PassportUtil().createPassportDto();
        PassportDto updatedPassportDto = new PassportUtil().updatePassportDto();
        PassportEntity preMergerEntity = new PassportUtil().createPreMergerPassportEntity();
        PassportEntity postMergerEntity = new PassportUtil().createPostMergerPassportEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(passportDto, preMergerEntity)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updatedPassportDto);

        PassportDto actualResult = service.update(3L, passportDto);


        assertThat(actualResult).isEqualTo(updatedPassportDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(passportDto, preMergerEntity);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        PassportDto passportDto = new PassportUtil().createPassportDto();

        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, passportDto));
        verify(repository).findById(1L);
    }
}
