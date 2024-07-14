package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
import com.bank.profile.util.ProfileUtil;
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
public class ProfileServiceImpTest {


    @Mock
    private ProfileMapper mapper;

    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileServiceImp service;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {

        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        ProfileEntity profileEntity = new ProfileUtil().createProfileEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(profileEntity));
        when(mapper.toDto(profileEntity)).thenReturn(profileDto);

        ProfileDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(profileDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(profileEntity);
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
        List<ProfileEntity> entityList = new ProfileUtil().createProfileEntityList();
        List<ProfileDto> dtoList = new ProfileUtil().createProfileDtoList();

        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<ProfileDto> resultList = service.findAllById(List.of(1L, 2L));

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

        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        ProfileEntity profileEntity = new ProfileUtil().createProfileEntity();

        when(mapper.toEntity(profileDto)).thenReturn(profileEntity);
        when(repository.save(profileEntity)).thenReturn(profileEntity);
        when(mapper.toDto(profileEntity)).thenReturn(profileDto);

        ProfileDto actualResult = service.save(profileDto);

        assertThat(actualResult).isEqualTo(profileDto);
        verify(mapper).toEntity(profileDto);
        verify(repository).save(profileEntity);
        verify(mapper).toDto(profileEntity);
    }

    @Test
    @DisplayName("сохранение неподходящих данных, негативный сценарий")
    void saveNotSuitableDataNegativeTest() {

        ProfileDto profileDto = new ProfileUtil().createProfileDto();

        when(mapper.toEntity(any(ProfileDto.class))).thenReturn(new ProfileEntity());
        when(repository.save(any(ProfileEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(profileDto));

        verify(mapper).toEntity(any(ProfileDto.class));
        verify(repository).save(any(ProfileEntity.class));
    }

    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        ProfileDto updatedProfileDto = new ProfileUtil().createUpdateProfileDto();
        ProfileEntity preMergerEntity = new ProfileUtil().createPreMergerProfileEntity();
        ProfileEntity postMergerEntity = new ProfileUtil().createPostMergerProfileEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(profileDto, preMergerEntity)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updatedProfileDto);

        ProfileDto actualResult = service.update(3L, profileDto);


        assertThat(actualResult).isEqualTo(updatedProfileDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(profileDto, preMergerEntity);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();

        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, profileDto));
        verify(repository).findById(1L);
    }
}
