package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
import com.bank.profile.util.AccountDetailsIdUtil;
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
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsIdServiceImpTest {

    @Mock
    private AccountDetailsIdMapper mapper;

    @Mock
    private AccountDetailsIdRepository repository;

    @InjectMocks
    private AccountDetailsIdServiceImp service;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {

        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdUtil().createAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(accountDetailsIdEntity));
        when(mapper.toDto(accountDetailsIdEntity)).thenReturn(accountDetailsIdDto);

        AccountDetailsIdDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(accountDetailsIdEntity);
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
        List<AccountDetailsIdEntity> entityList = new AccountDetailsIdUtil().createAccountDetailsIdEntityList();
        List<AccountDetailsIdDto> dtoList = new AccountDetailsIdUtil().createAccountDetailsIdDtoList();

        when(repository.findAllById(List.of(1L, 2L))).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<AccountDetailsIdDto> resultList = service.findAllById(List.of(1L, 2L));

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

        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdUtil().createAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();

        when(mapper.toEntity(accountDetailsIdDto)).thenReturn(accountDetailsIdEntity);
        when(repository.save(accountDetailsIdEntity)).thenReturn(accountDetailsIdEntity);
        when(mapper.toDto(accountDetailsIdEntity)).thenReturn(accountDetailsIdDto);

        AccountDetailsIdDto actualResult = service.save(accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
        verify(mapper).toEntity(accountDetailsIdDto);
        verify(repository).save(accountDetailsIdEntity);
        verify(mapper).toDto(accountDetailsIdEntity);
    }

    @Test
    @DisplayName("сохранение неподходящих данных, негативный сценарий")
    void saveNotSuitableDataNegativeTest() {

        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();

        when(mapper.toEntity(any(AccountDetailsIdDto.class))).thenReturn(new AccountDetailsIdEntity());
        when(repository.save(any(AccountDetailsIdEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(accountDetailsIdDto));

        verify(mapper).toEntity(any(AccountDetailsIdDto.class));
        verify(repository).save(any(AccountDetailsIdEntity.class));
    }


    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        AccountDetailsIdDto updatedAccountDetailsIdDto = new AccountDetailsIdUtil().createUpdatedAccountDetailsIdDto();
        AccountDetailsIdEntity preMergerEntity = new AccountDetailsIdUtil().createPreMergerAccountDetailsIdEntity();
        AccountDetailsIdEntity postMergerEntity = new AccountDetailsIdUtil().createPostMergerAccountDetailsIdEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(accountDetailsIdDto, preMergerEntity)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updatedAccountDetailsIdDto);

        AccountDetailsIdDto actualResult = service.update(3L, accountDetailsIdDto);


        assertThat(actualResult).isEqualTo(updatedAccountDetailsIdDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(accountDetailsIdDto, preMergerEntity);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();

        when(repository.findById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, accountDetailsIdDto));
        verify(repository).findById(1L);
    }


}
